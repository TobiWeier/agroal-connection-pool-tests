
package org.acme.getting.agroal;

import io.quarkus.arc.Arc;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.Bean;
import javax.sql.DataSource;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;


@Liveness
@ApplicationScoped
public class AgroalConnectionPoolHealthCheck implements HealthCheck{
    
    private final String ACQUISITION_TIMEOUT_MESSAGE = "Sorry, acquisition timeout";
    private static final String DEFAULT_DS = "__default__";
    private final Map<String, DataSource> dataSources = new HashMap<>();

    @PostConstruct
    protected void init() {
        Set<Bean<?>> beans = Arc.container().beanManager().getBeans(DataSource.class);
        for (Bean<?> bean : beans) {
            if (bean.getName() == null) {
                // this is the default DataSource: retrieve it by type
                DataSource defaultDs = Arc.container().instance(DataSource.class).get();
                dataSources.put(DEFAULT_DS, defaultDs);
            } else {
                DataSource ds = (DataSource) Arc.container().instance(bean.getName()).get();
                dataSources.put(bean.getName(), ds);
            }
        }
    }

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.named("AgroalConnectionPool - ConnectionPool health check").up();
        for (Map.Entry<String, DataSource> dataSource : dataSources.entrySet()) {
            boolean isDefault = DEFAULT_DS.equals(dataSource.getKey());
            try (Connection con = dataSource.getValue().getConnection()) {
                try (Statement stmt = con.createStatement()) {
                    stmt.execute("select 1");
                }
            } catch (Exception e) {
                if(e instanceof SQLException){
                    SQLException ex = (SQLException)e;
                    if(ex.getMessage().startsWith(ACQUISITION_TIMEOUT_MESSAGE)){
                        String data = isDefault ? "Unable to execute the validation check for the default DataSource: "
                            : "Unable to execute the validation check for DataSource '" + dataSource.getKey() + "': ";
                        String dsName = isDefault ? "default" : dataSource.getKey();
                        builder.down().withData(dsName, data + ex.getMessage()  + " - SQLError:" + ex.getErrorCode());
                    }
                }
            }
        }
        return builder.build();
    }
}
