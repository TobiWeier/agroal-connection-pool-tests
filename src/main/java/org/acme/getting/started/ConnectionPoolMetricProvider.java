package org.acme.getting.started;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

@ApplicationScoped
public class ConnectionPoolMetricProvider {

    @Inject
    ConnectionPoolInfoController cpiController;

    @Gauge(name = "ConnectionPoolConfiguration.maxPoolSize",
            displayName = "Maximum number of connections on the pool. Must not be negative. Required.",
            unit = "count",
            absolute = true)
    public int maxPoolSize() {
        return cpiController.getConfig().getMaxSize();
    }

    @Gauge(name = "ConnectionPoolConfiguration.minPoolSize",
            displayName = "Minimum number of connections on the pool. Must not be negative and smaller than max. Default is zero.",
            unit = "count", absolute = true)
    public Integer minPoolSize() {
        return cpiController.getConfig().getMinSize();
    }

    @Gauge(name = "ConnectionPoolConfiguration.initialPoolSize",
            displayName = "Number of connections when the pool starts. Must not be negative. Default is zero.",
            unit = "count", absolute = true)
    public Integer initialPoolSize() {
        return cpiController.getConfig().getInitialSize();
    }

    @Gauge(name = "ConnectionPoolConfiguration.maxLifeTime",
            displayName = "Duration(sec) for the lifetime of connections. Default is {@link Duration#ZERO} meaning that this feature is disabled.",
            unit = MetricUnits.SECONDS, absolute = true)
    public Long maxLifeTime() {
        return cpiController.getConfig().getMaxLifeTime().getSeconds();
    }

    @Gauge(name = "ConnectionPoolConfiguration.maxLeakTimeout",
            displayName = "Duration(sec) of the leak timeout detection. Default is {@link Duration#ZERO} meaning that this feature is disabled.",
            unit = MetricUnits.SECONDS, absolute = true)
    public Long maxLeakTimeout() {
        return cpiController.getConfig().getLeakTimeout().getSeconds();
    }

    @Gauge(name = "ConnectionPoolConfiguration.acquisitionTimeout",
            displayName = "Duration(sec) of the acquisition timeout. Default is {@link Duration#ZERO} meaning that a thread will wait indefinitely.",
            unit = MetricUnits.SECONDS, absolute = true)
    public Long acquisitionTimeout() {
        return cpiController.getConfig().getAcquisitionTimeout().getSeconds();
    }

    @Gauge(name = "ConnectionPoolConfiguration.idlevalidationTimeout",
            displayName = "Duration(sec) of idle time for foreground validation to be executed. Default is {@link Duration#ZERO} meaning that this feature is disabled.",
            unit = MetricUnits.SECONDS, absolute = true)
    public Long idleValidationTimeout() {
        return cpiController.getConfig().getIdleValidationTimeout().getSeconds();
    }

    @Gauge(name = "ConnectionPoolConfiguration.validationTimeout",
            displayName = "Duration(sec) of background validation interval. Default is {@link Duration#ZERO} meaning that this feature is disabled.",
            unit = MetricUnits.SECONDS, absolute = true)
    public Long validationTimeout() {
        return cpiController.getConfig().getValidationTimeout().getSeconds();
    }

    @Gauge(name = "ConnectionPoolConfiguration.reapTimeout",
            displayName = "Duration(sec) for eviction of idle connections. Default is {@link Duration#ZERO} meaning that this feature is disabled.",
            unit = MetricUnits.SECONDS, absolute = true)
    public Long reapTimeout() {
        return cpiController.getConfig().getReapTimeout().getSeconds();
    }

    /**
     * "dummy" method to init the expose <BR>
     * when the first method is called, the annotoated gauges get initialized
     * and expose the metrics
     */
    void exposeMetrics() {
        //when switching gauges via annototion to programmatic gauges then do the initialization here
    }

    @Gauge(name = "ConnectionPoolMetrics.poolUsage",
            displayName = "Usage of connections from the Pool",
            unit = MetricUnits.PERCENT, absolute = true)
    public Double poolUsage() {
        Double result = 0.0d;
        double maxSize = ((Integer) cpiController.getConfig().getMaxSize()).doubleValue();
        double activeCount = ((Long) cpiController.getConnectionPoolMetrics().getActiveCount()).doubleValue();
        if (maxSize > 0.0d) {
            result = activeCount / maxSize;
        }
        return result;
    }

    @Gauge(name = "ConnectionPoolMetrics.acquireCount",
            displayName = "Number of acquired connections from the Pool",
            unit = "count", absolute = true)
    public Long acquireCount() {
        return cpiController.getConnectionPoolMetrics().getAcquireCount();
    }

    @Gauge(name = "ConnectionPoolMetrics.activeCount",
            displayName = "Number of active connections on the Pool",
            unit = "count", absolute = true)
    public Long activeCount() {
        return cpiController.getConnectionPoolMetrics().getActiveCount();
    }

    @Gauge(name = "ConnectionPoolMetrics.maxUsedCount",
            displayName = "Number of maxUsed connections since Pool- Lifetime",
            unit = "count", absolute = true)
    public Long maxUsedCount() {
        return cpiController.getConnectionPoolMetrics().getMaxUsedCount();
    }

    @Gauge(name = "ConnectionPoolMetrics.awaitingCount",
            displayName = "Number of awaiting connections on the Pool",
            unit = "count", absolute = true)
    public Long awaitingCount() {
        return cpiController.getConnectionPoolMetrics().getAwaitingCount();
    }

    @Gauge(name = "ConnectionPoolMetrics.availableCount",
            displayName = "Number of availableCount connections on the Pool",
            unit = "count", absolute = true)
    public Long availableCount() {
        return cpiController.getConnectionPoolMetrics().getAvailableCount();
    }

    @Gauge(name = "ConnectionPoolMetrics.creationCount",
            displayName = "Number of created connections on the Pool",
            unit = "count", absolute = true)
    public Long creationCount() {
        return cpiController.getConnectionPoolMetrics().getCreationCount();
    }

    @Gauge(name = "ConnectionPoolMetrics.destroyCount",
            displayName = "Number of connections destroyed from the Pool",
            unit = "count", absolute = true)
    public Long destroyCount() {
        return cpiController.getConnectionPoolMetrics().getDestroyCount();
    }

    @Gauge(name = "ConnectionPoolMetrics.flushCount",
            displayName = "Number of connections flushed on the Pool",
            unit = "count", absolute = true)
    public Long flushCount() {
        return cpiController.getConnectionPoolMetrics().getFlushCount();
    }

    @Gauge(name = "ConnectionPoolMetrics.invalidCount",
            displayName = "Number of invalid connections on the Pool",
            unit = "count", absolute = true)
    public Long invalidCount() {
        return cpiController.getConnectionPoolMetrics().getInvalidCount();
    }

    @Gauge(name = "ConnectionPoolMetrics.reapCount",
            displayName = "Number of reap connections of the Pool",
            unit = "count", absolute = true)
    public Long reapCount() {
        return cpiController.getConnectionPoolMetrics().getReapCount();
    }

    @Gauge(name = "ConnectionPoolMetrics.leakDetectionCount",
            displayName = "Number of leakDetection connections found in the Pool",
            unit = "count", absolute = true)
    public Long leakDetectionCount() {
        return cpiController.getConnectionPoolMetrics().getLeakDetectionCount();
    }

    @Gauge(name = "ConnectionPoolMetrics.blockingTimeAverage",
            displayName = "Average Blocking time (sec)",
            unit = MetricUnits.SECONDS, absolute = true)
    public Long blockingTimeAverage() {
        return cpiController.getConnectionPoolMetrics().getBlockingTimeAverage().getSeconds();
    }

    @Gauge(name = "ConnectionPoolMetrics.blockingTimeMax",
            displayName = "Max Blocking time (sec)",
            unit = MetricUnits.SECONDS, absolute = true)
    public Long blockingTimeMax() {
        return cpiController.getConnectionPoolMetrics().getBlockingTimeMax().getSeconds();
    }

    @Gauge(name = "ConnectionPoolMetrics.blockingTimeTotal",
            displayName = "Total Blocking time (sec)",
            unit = MetricUnits.SECONDS, absolute = true)
    public Long blockingTimeTotal() {
        return cpiController.getConnectionPoolMetrics().getBlockingTimeTotal().getSeconds();
    }

    @Gauge(name = "ConnectionPoolMetrics.creationTimeAverage",
            displayName = "Average Creation time (sec)",
            unit = MetricUnits.SECONDS, absolute = true)
    public Long creationTimeAverage() {
        return cpiController.getConnectionPoolMetrics().getCreationTimeAverage().getSeconds();
    }

    @Gauge(name = "ConnectionPoolMetrics.creationTimeMax",
            displayName = "Max Creation time (sec)",
            unit = MetricUnits.SECONDS, absolute = true)
    public Long creationTimeMax() {
        return cpiController.getConnectionPoolMetrics().getCreationTimeMax().getSeconds();
    }

    @Gauge(name = "ConnectionPoolMetrics.creationTimeTotal",
            displayName = "Total Creation time (sec)",
            unit = MetricUnits.SECONDS, absolute = true)
    public Long creationTimeTotal() {
        return cpiController.getConnectionPoolMetrics().getCreationTimeTotal().getSeconds();
    }

    //DB
    @Gauge(name = "ConnectionPoolMetrics.availableCountDB",
            displayName = "Number of availableCount connections on the DB",
            unit = "count", absolute = true)
    public Long availableCountDB() {
        return cpiController.getConnectionPoolMetrics().getAvailableCountDB();
    }
    
    @Gauge(name = "ConnectionPoolMetrics.idleCountDB",
            displayName = "Number of idle connections on the DB",
            unit = "count", absolute = true)
    public Long idleCountDB() {
        return cpiController.getConnectionPoolMetrics().getIdleCountDB();
    }
    
    @Gauge(name = "ConnectionPoolMetrics.activeCountDB",
            displayName = "Number of active connections on the DB",
            unit = "count", absolute = true)
    public Long activeCountDB() {
        return cpiController.getConnectionPoolMetrics().getActiveCountDB();
    }
}
