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

    public enum ReadOnlyFlushMode {
        NONE, COMPLETE_POOL, SINGLE_CONNECTION
    };

    private ReadOnlyFlushMode flushMode = ReadOnlyFlushMode.COMPLETE_POOL;

    public ReadOnlyFlushMode getFlushMode() {
        return flushMode;
    }

    public void setFlushMode(ReadOnlyFlushMode aFlushMode) {
        flushMode = aFlushMode;
    }
    
    @Override
    public void onConnectionReturn(Connection connection) {
        String action = "check isReadOnly-Connection";
        try {
            if (connection.isClosed()) {
                System.out.println("IoTOSAgroalReadOnlyPoolInterceptor.onConnectionReturn() Found unclosed Connection. Flush single ConnectionPool ...");
            } else if (connection.isReadOnly()) {
                readOnlyConnectionCnt.incrementAndGet();
                if (flushMode.equals(ReadOnlyFlushMode.NONE)) {
                    System.out.println("IoTOSAgroalReadOnlyPoolInterceptor.onConnectionReturn() Found readOnly Connection. No Flush-Action configured");
                } else if (flushMode.equals(ReadOnlyFlushMode.SINGLE_CONNECTION) && connection instanceof ConnectionWrapper) {
                    System.out.println("IoTOSAgroalReadOnlyPoolInterceptor.onConnectionReturn() Found unexcpected readOnly Connection. Flush single Connection ...");
                } else {
                    System.out.println("IoTOSAgroalReadOnlyPoolInterceptor.onConnectionReturn() Found unexcpected readOnly Connection. Flush ConnectionPool ...");
                }
            }
        } catch (Exception ex) {
            System.err.println("IoTOSAgroalReadOnlyPoolInterceptor.onConnectionReturn()::Got Exception during " + action
              + ". Msg:" + ex.getMessage());
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
