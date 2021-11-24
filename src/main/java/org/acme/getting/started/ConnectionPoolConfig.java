package org.acme.getting.started;

import io.agroal.api.configuration.AgroalConnectionPoolConfiguration;
import io.agroal.api.configuration.AgroalDataSourceConfiguration;
import java.time.Duration;
import java.time.OffsetDateTime;

/**
 *
 * @author michaelm
 */
public class ConnectionPoolConfig {

    private int minSize;
    private int maxSize;
    private int initialSize;
    private Duration maxLifeTime;
    private Duration acquisitionTimeout;
    private Duration leakTimeout;
    private Duration idleValidationTimeout;
    private Duration validationTimeout;
    private Duration reapTimeout;
    private String initialSQL;
    private String principal;    
    private Boolean flushOnClose;
    private Boolean autoCommit;    
    private OffsetDateTime stamp;
    private final String poolName;
    private Boolean metricsEnabled;

    public ConnectionPoolConfig(String aPoolName, AgroalDataSourceConfiguration aDsCfg) {
        this.poolName = aPoolName;
        this.stamp = OffsetDateTime.now();
        this.metricsEnabled = aDsCfg.metricsEnabled();
        AgroalConnectionPoolConfiguration aPoolCfg = aDsCfg.connectionPoolConfiguration();
        if (aPoolCfg != null) {
            this.minSize = aPoolCfg.minSize();
            this.maxSize = aPoolCfg.maxSize();
            this.initialSize = aPoolCfg.initialSize();
            this.acquisitionTimeout = aPoolCfg.acquisitionTimeout();
            this.maxLifeTime = aPoolCfg.maxLifetime();
            this.leakTimeout = aPoolCfg.leakTimeout();
            this.idleValidationTimeout = aPoolCfg.idleValidationTimeout();
            this.validationTimeout = aPoolCfg.validationTimeout();
            this.reapTimeout = aPoolCfg.reapTimeout();
            this.flushOnClose = aPoolCfg.flushOnClose();
            if (aPoolCfg.connectionFactoryConfiguration() != null) {
                this.autoCommit = aPoolCfg.connectionFactoryConfiguration().autoCommit();
                this.initialSQL  = aPoolCfg.connectionFactoryConfiguration().initialSql();
                if (aPoolCfg.connectionFactoryConfiguration().principal() != null) {
                    this.principal  = aPoolCfg.connectionFactoryConfiguration().principal().getName();
                }
            }        
        }
    }

    public int getMinSize() {
        return minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public Duration getMaxLifeTime() {
        return maxLifeTime;
    }

    public Duration getAcquisitionTimeout() {
        return acquisitionTimeout;
    }

    public Duration getLeakTimeout() {
        return leakTimeout;
    }

    public Duration getIdleValidationTimeout() {
        return idleValidationTimeout;
    }

    public Duration getReapTimeout() {
        return reapTimeout;
    }

    public String getInitialSQL() {
        return initialSQL;
    }

    public Duration getValidationTimeout() {
        return validationTimeout;
    }

    public String getPrincipal() {
        return principal;
    }

    public Boolean getFlushOnClose() {
        return flushOnClose;
    }

    public Boolean getAutoCommit() {
        return autoCommit;
    }

    public OffsetDateTime getStamp() {
        return stamp;
    }

    public String getPoolName() {
        return poolName;
    }

    public Boolean isMetricsEnabled() {
        return metricsEnabled;
    }
    
    @Override
    public String toString() {
        return "ConnectionPoolConfig{" + "minSize=" + minSize + ", maxSize=" + maxSize + ", initialSize=" + initialSize + ", maxLifeTime=" + maxLifeTime + ", acquisitionTimeout=" + acquisitionTimeout + ", leakTimeout=" + leakTimeout + ", idleValidationTimeout=" + idleValidationTimeout + ", validationTimeout=" + validationTimeout + ", reapTimeout=" + reapTimeout + ", initialSQL=" + initialSQL + ", principal=" + principal + ", flushOnClose=" + flushOnClose + ", autoCommit=" + autoCommit + ", stamp=" + stamp + ", poolName=" + poolName + '}';
    }

    public ConnectionPoolConfig update(AgroalDataSourceConfiguration aDsCfg) {
        if (aDsCfg != null) {
            metricsEnabled = aDsCfg.metricsEnabled();
            AgroalConnectionPoolConfiguration aPoolCfg = aDsCfg.connectionPoolConfiguration();
            if (aPoolCfg != null) {
                this.stamp = OffsetDateTime.now();
                this.minSize = aPoolCfg.minSize();
                this.maxSize = aPoolCfg.maxSize();
                this.initialSize = aPoolCfg.initialSize();
                this.acquisitionTimeout = aPoolCfg.acquisitionTimeout();
                this.maxLifeTime = aPoolCfg.maxLifetime();
                this.leakTimeout = aPoolCfg.leakTimeout();
                this.idleValidationTimeout = aPoolCfg.idleValidationTimeout();
                this.validationTimeout = aPoolCfg.validationTimeout();
                this.reapTimeout = aPoolCfg.reapTimeout();
                this.flushOnClose = aPoolCfg.flushOnClose();
                if (aPoolCfg.connectionFactoryConfiguration() != null) {
                    this.autoCommit = aPoolCfg.connectionFactoryConfiguration().autoCommit();
                    this.initialSQL  = aPoolCfg.connectionFactoryConfiguration().initialSql();
                    if (aPoolCfg.connectionFactoryConfiguration().principal() != null) {
                        this.principal  = aPoolCfg.connectionFactoryConfiguration().principal().getName();
                    }
                }        
            }
        }
        return this;
    }
    
    
}
