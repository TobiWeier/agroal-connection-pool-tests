package org.acme.getting.started;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.AgroalDataSourceConfiguration;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.StartupEvent;
import java.time.Duration;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class ConnectionPoolInfoController {

    @Inject
    ConnectionPoolMetricProvider provider;

    @Inject
    AgroalDataSource ads;

    private ConnectionPoolConfig config = null;

    final Object configLock = new Object();

    public ConnectionPoolConfig getConfig() {
        synchronized (configLock) {
            if (config == null) {
                config = new ConnectionPoolConfig("default", ads.getConfiguration());
            }
        }
        return config;
    }

    /**
     * reload the connectionPool Configuration
     *
     * @return
     */
    public ConnectionPoolConfig reloadConnectionPoolConfiguration() {
        return (config == null) ? getConfig() : config.update(ads.getConfiguration());
    }

    public ConnectionPoolMetrics getConnectionPoolMetrics() {
        ConnectionPoolMetrics metrics = null;
        AgroalDataSourceConfiguration cfg = ads.getConfiguration();
        if (cfg.metricsEnabled()) {
            metrics = new ConnectionPoolMetrics(ads.getMetrics());
        }
        return metrics;
    }

    public boolean enableConnectionPoolMetrics(boolean aEnable) {
        AgroalDataSourceConfiguration cfg = ads.getConfiguration();
        if (cfg.metricsEnabled() != aEnable) {
            cfg.setMetricsEnabled(aEnable);
        }
        getConfig().update(cfg);
        if (aEnable) {
            //make sure connectionPoolConfig metrics get exposed
            provider.exposeMetrics();
        }
        return cfg.metricsEnabled();
    }

    public void setMinPoolSize(int minSize) {
        synchronized (configLock) {
            AgroalDataSourceConfiguration cfg = ads.getConfiguration();
            cfg.connectionPoolConfiguration().setMinSize(minSize);
            getConfig().update(cfg);
        }
    }

    public void setMaxPoolSize(int maxSize) {
        synchronized (configLock) {
            AgroalDataSourceConfiguration cfg = ads.getConfiguration();
            cfg.connectionPoolConfiguration().setMaxSize(maxSize);
            getConfig().update(cfg);
        }
    }

    public void setAcquisitionTimeout(Duration timeOut) {
        synchronized (configLock) {
            AgroalDataSourceConfiguration cfg = ads.getConfiguration();
            cfg.connectionPoolConfiguration().setAcquisitionTimeout(timeOut);
            getConfig().update(cfg);
        }
    }

    //automatic expose connectionPool metrics only if is this configured via application Properties
    @Inject
    LaunchMode launchMode;

    void onStart(@Observes StartupEvent ev) {
        if (!launchMode.equals(LaunchMode.TEST)) {
            reloadConnectionPoolConfiguration();
            if (config.isMetricsEnabled()) {
                provider.exposeMetrics();
            }
        }
    }
}
