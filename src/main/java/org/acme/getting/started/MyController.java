package org.acme.getting.started;

import io.quarkus.scheduler.Scheduled;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private AtomicLong inserts = new AtomicLong();
    private AtomicLong insertFailures = new AtomicLong();
    
    private AtomicLong selects = new AtomicLong();
    private AtomicLong selectFailures = new AtomicLong();
    
   
    @Scheduled(cron = "*/1 * * * * ?")
    public void scheduleInsert() {
        long start = System.currentTimeMillis();
        while (true) {
            try {
                List<Future<Object>> invokeAll = Executors.newFixedThreadPool(1).invokeAll(List.of(
                  () -> persistEntity(),
                  () -> persistEntity(),
                  () -> persistEntity(),
                  () -> persistEntity(),
                  () -> persistEntity(),
                  () -> persistEntity(),
                  () -> persistEntity(),
                  () -> persistEntity(),
                  () -> persistEntity(),
                  () -> persistEntity()
                ));
                invokeAll.forEach( f -> {
                    try {
                        f.get(5, TimeUnit.SECONDS);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (TimeoutException ex) {
                        Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                
            } catch (Throwable th) {
                insertFailures.incrementAndGet();
                th.printStackTrace();
            }
            if (System.currentTimeMillis() > (start + 1000L)) {
                break;
            }
        }
    }

    @Transactional
    public MyEntity persistEntity() {
        Random random = new Random(System.currentTimeMillis());
        MyEntity entity = new MyEntity().setName(UUID.randomUUID().toString());
        try {
            em.persist(entity);
            for (int i=0; i<10; i++) {
                random.nextInt(10);
                long size = em.createQuery("select count(e) from MyEntity e", Long.class).getSingleResult();
            }
            inserts.incrementAndGet();
        } catch (Throwable th) {
            insertFailures.incrementAndGet();
            th.printStackTrace();
        }
        return entity;
    }
    
    @Transactional
    @Scheduled(cron = "*/1 * * * * ?")
    @Timed(name = "checksTimer", description = "A measure of how long it takes to perform select.", unit = MetricUnits.MILLISECONDS)
    public void scheduleSelect() {
        long start = System.currentTimeMillis();
        Random random = new Random(start);
        while (true) {
            try {
                Thread.sleep(10L);
                em.createQuery("select e from MyEntity e order by e.name " + (random.nextBoolean() ? "asc" : "desc") + ", e.created " + (random.nextBoolean() ? "asc" : "desc")).setMaxResults(1000).getResultList();
                selects.incrementAndGet();
            } catch (Throwable th) {
                selectFailures.incrementAndGet();
            }
            if (System.currentTimeMillis() > (start + 1000L)) {
                break;
            }
        }
    }
 
    @Gauge(name = "inserts", unit = MetricUnits.NONE, description = "Number of successful inserts")
    public Long getInserts() {
        return inserts.get();
    }
    
    @Gauge(name = "insertFailures", unit = MetricUnits.NONE, description = "Number of failed inserts")
    public Long getInsertFailures() {
        return insertFailures.get();
    }
    
    @Gauge(name = "selects", unit = MetricUnits.NONE, description = "Number of successful selects")
    public Long getSelects() {
        return selects.get();
    }

    @Gauge(name = "selectFailures", unit = MetricUnits.NONE, description = "Number of failed selects")
    public Long getSelectFailures() {
        return selectFailures.get();
    }
    
}
