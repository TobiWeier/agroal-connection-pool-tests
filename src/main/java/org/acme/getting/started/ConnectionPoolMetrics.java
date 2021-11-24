package org.acme.getting.started;

import io.agroal.api.AgroalDataSourceMetrics;
import java.time.Duration;

/**
 *
 * @author michaelm
 */
public class ConnectionPoolMetrics {

    private final long acquireCount;
    private final long activeCount;
    private final long availableCount;
    private final long awaitingCount;
    private final Duration blockingTimeAverage;
    private final Duration blockingTimeMax;
    private final Duration blockingTimeTotal;
    private final long creationCount;
    private final long reapCount;
    private final long maxUsedCount;
    private final long leakDetectionCount;
    private final long invalidCount;
    private final long flushCount;
    private final long destroyCount;
    private final Duration creationTimeTotal;
    private final Duration creationTimeMax;
    private final Duration creationTimeAverage;
    
    private final long availableCountDB;
    private final long idleCountDB;
    private final long activeCountDB;

    public ConnectionPoolMetrics(AgroalDataSourceMetrics aMetrics, long aAvailableCountDb, long aIdelCountDB, long aActiveCountDB) {
        acquireCount = aMetrics.acquireCount();
        availableCount = aMetrics.availableCount();
        activeCount = aMetrics.activeCount();
        awaitingCount = aMetrics.awaitingCount();
        blockingTimeAverage = aMetrics.blockingTimeAverage();
        blockingTimeMax = aMetrics.blockingTimeMax();
        blockingTimeTotal = aMetrics.blockingTimeTotal();
        creationCount = aMetrics.creationCount();
        creationTimeAverage = aMetrics.creationTimeAverage();
        creationTimeMax = aMetrics.creationTimeMax();
        creationTimeTotal = aMetrics.creationTimeTotal();
        destroyCount = aMetrics.destroyCount();
        flushCount = aMetrics.flushCount();
        invalidCount = aMetrics.invalidCount();
        leakDetectionCount= aMetrics.leakDetectionCount();
        maxUsedCount = aMetrics.maxUsedCount();
        reapCount = aMetrics.reapCount();
        availableCountDB = aAvailableCountDb;
        idleCountDB = aIdelCountDB;
        activeCountDB = aActiveCountDB;
    }

    public long getAcquireCount() {
        return acquireCount;
    }

    public long getActiveCount() {
        return activeCount;
    }

    public long getAvailableCount() {
        return availableCount;
    }
    
    public long getAwaitingCount() {
        return awaitingCount;
    }

    public Duration getBlockingTimeAverage() {
        return blockingTimeAverage;
    }

    public Duration getBlockingTimeMax() {
        return blockingTimeMax;
    }

    public Duration getBlockingTimeTotal() {
        return blockingTimeTotal;
    }

    public long getCreationCount() {
        return creationCount;
    }

    public long getReapCount() {
        return reapCount;
    }

    public long getMaxUsedCount() {
        return maxUsedCount;
    }

    public long getLeakDetectionCount() {
        return leakDetectionCount;
    }

    public long getInvalidCount() {
        return invalidCount;
    }

    public long getFlushCount() {
        return flushCount;
    }

    public long getDestroyCount() {
        return destroyCount;
    }

    public Duration getCreationTimeTotal() {
        return creationTimeTotal;
    }

    public Duration getCreationTimeMax() {
        return creationTimeMax;
    }

    public Duration getCreationTimeAverage() {
        return creationTimeAverage;
    }

    public long getAvailableCountDB() {
        return availableCountDB;
    }

    public long getIdleCountDB() {
        return idleCountDB;
    }

    public long getActiveCountDB() {
        return activeCountDB;
    }
    
    @Override
    public String toString() {
        return "ConnectionPoolMetrics{" 
                + " activeCount=" + activeCount 
                + ", availableCount=" + availableCount
                + ", awaitingCount=" + awaitingCount 
                + ", creationCount=" + creationCount 
                + ", reapCount=" + reapCount 
                + ", maxUsedCount=" + maxUsedCount 
                + ", acquireCount=" + acquireCount 
                + ", leakDetectionCount=" + leakDetectionCount 
                + ", invalidCount=" + invalidCount 
                + ", flushCount=" + flushCount 
                + ", destroyCount=" + destroyCount 
                + ", blockingTimeAverage=" + blockingTimeAverage 
                + ", blockingTimeMax=" + blockingTimeMax 
                + ", blockingTimeTotal=" + blockingTimeTotal 
                + ", creationTimeTotal=" + creationTimeTotal 
                + ", creationTimeMax=" + creationTimeMax 
                + ", creationTimeAverage=" + creationTimeAverage 
                + ", availableCountDB=" + availableCountDB 
                + ", idleCountDB=" + idleCountDB 
                + ", activeCountDB=" + activeCountDB 
                + '}';
    }
}
