package org.acme.getting.started;

import io.quarkus.scheduler.Scheduled;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;

@ApplicationScoped
public class MyController {
    
    @Inject
    EntityManager em;
    
    private long inserts;
    private long insertFailures;
    
    private long selects;
    private long selectFailures;
    
    private long lostRecords;
    
    private Long lastId;
    
    @Scheduled(cron = "*/1 * * * * ?")
    public void scheduleInsert() {
        
        long start = System.currentTimeMillis();
        while (true) {
            try {
                Thread.sleep(100L);
                MyEntity entity = persistEntity();
                lastId = entity.getId();
                inserts++;
            } catch (Throwable th) {
                insertFailures++;
                th.printStackTrace();
            }
            if (System.currentTimeMillis() > (start + 1000L)) {
                break;
            }
        }
    }

    @Transactional
    public MyEntity persistEntity() {
        MyEntity entity = new MyEntity().setName(UUID.randomUUID().toString()).setLastId(lastId);
        em.persist(entity);
        return entity;
    }
    
    @Scheduled(cron = "*/5 * * * * ?")
    @Timed(name = "checksTimer", description = "A measure of how long it takes to perform select.", unit = MetricUnits.MILLISECONDS)
    public void scheduleSelect() {
        try {
            Thread.sleep(100L);
            em.createQuery("select e from MyEntity e order by e.name asc, e.created desc").setMaxResults(1000).getResultList();
            selects++;
        } catch (Throwable th) {
            selectFailures++;
        }
        try {
            lostRecords = em.createQuery("select count(e) from MyEntity e where e.lastId not in (select f.id from MyEntity f)", Long.class).setMaxResults(1).getResultList().get(0);
        } catch (Throwable th) {
            
        }
    }
 
    @Gauge(name = "inserts", unit = MetricUnits.NONE, description = "Number of successful inserts")
    public Long getInserts() {
        return inserts;
    }
    
    @Gauge(name = "insertFailures", unit = MetricUnits.NONE, description = "Number of failed inserts")
    public Long getInsertFailures() {
        return insertFailures;
    }
    
    @Gauge(name = "selects", unit = MetricUnits.NONE, description = "Number of successful selects")
    public Long getSelects() {
        return selects;
    }

    @Gauge(name = "selectFailures", unit = MetricUnits.NONE, description = "Number of failed selects")
    public Long getSelectFailures() {
        return selectFailures;
    }
    
    @Gauge(name = "lostRecords", unit = MetricUnits.NONE, description = "Number of lost records")
    public Long getLostRecords() {
        return lostRecords;
    }
}
