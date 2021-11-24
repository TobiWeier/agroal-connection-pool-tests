package org.acme.getting.agroal;

import io.agroal.api.AgroalPoolInterceptor;
import io.agroal.pool.wrapper.ConnectionWrapper;
import io.quarkus.arc.Unremovable;
import java.sql.Connection;
import java.util.concurrent.atomic.AtomicLong;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.metrics.annotation.Gauge;

@Unremovable
@ApplicationScoped
public class IoTOSAgroalReadOnlyPoolInterceptor implements AgroalPoolInterceptor {

    final AtomicLong readOnlyConnectionCnt = new AtomicLong();

    @Override
    public void onConnectionAcquire(Connection connection) {
        try {
            if (connection.isClosed()) {
                System.out.println("IoTOSAgroalReadOnlyPoolInterceptor.onConnectionAcquire() Got closed Connection....");
            } else if (connection.isReadOnly()) {
                readOnlyConnectionCnt.incrementAndGet();
                System.out.println("IoTOSAgroalReadOnlyPoolInterceptor.onConnectionAcquire() Got readOnly Connection...");
            }
        } catch (Exception ex) {
            System.err.println("IoTOSAgroalReadOnlyPoolInterceptor.onConnectionAcquire():: Exception occured. Msg:" + ex.getMessage());
        }
    }
    
    @Gauge(
      absolute = true,
      name = "IoTOSAgroalReadOnlyPoolInterceptor.getTotalReadOnlyConnection",
      unit = "Count",
      displayName = "Amount of readOnly Connections found during returning Connections into ConnectionPool.")
    public Long getTotalReadOnlyConnection() {
        return readOnlyConnectionCnt.get();
    }
}
