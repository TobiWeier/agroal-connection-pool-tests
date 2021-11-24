//package org.acme.getting.agroal;
//
//import io.agroal.api.AgroalDataSource;
//import io.agroal.api.AgroalPoolInterceptor;
//import io.agroal.pool.ConnectionHandler;
//import io.agroal.pool.wrapper.ConnectionWrapper;
//import io.quarkus.arc.Unremovable;
//import java.sql.Connection;
//import java.util.concurrent.atomic.AtomicLong;
//import javax.enterprise.context.ApplicationScoped;
//import javax.enterprise.inject.spi.CDI;
//import org.eclipse.microprofile.metrics.annotation.Counted;
//import org.eclipse.microprofile.metrics.annotation.Gauge;
//
//@Unremovable
//@ApplicationScoped
//public class IoTOSAgroalReadOnlyPoolInterceptor implements AgroalPoolInterceptor {
//
//    final AtomicLong readOnlyConnectionCnt = new AtomicLong();    
//    
//    public enum ReadOnlyFlushMode {NONE, COMPLETE_POOL, SINGLE_CONNECTION};
//    
//    private ReadOnlyFlushMode flushMode = ReadOnlyFlushMode.COMPLETE_POOL;
//    
//    public ReadOnlyFlushMode getFlushMode() {
//        return flushMode;
//    }
//    
//    public void setFlushMode(ReadOnlyFlushMode aFlushMode) {
//        flushMode = aFlushMode;
//    }
//
//    @Override
//    public void onConnectionReturn(Connection connection) {
//        String action = "check isReadOnly-Connection";
//        try {
//            if (connection.isClosed()) {
//                System.out.println("IoTOSAgroalReadOnlyPoolInterceptor.onConnectionReturn() Found unclosed Connection. Flush single ConnectionPool ...");
////                ConnectionWrapper cw = (ConnectionWrapper) connection;
////                flushSingleConnection(cw);
//            } else if (connection.isReadOnly() ) {
//                readOnlyConnectionCnt.incrementAndGet();
//                 if (flushMode.equals(ReadOnlyFlushMode.NONE)) {
//                     System.out.println("IoTOSAgroalReadOnlyPoolInterceptor.onConnectionReturn() Found readOnly Connection. No Flush-Action configured");                    
//                 } else if (flushMode.equals(ReadOnlyFlushMode.SINGLE_CONNECTION) && connection instanceof ConnectionWrapper) {
//                    System.out.println("IoTOSAgroalReadOnlyPoolInterceptor.onConnectionReturn() Found unexcpected readOnly Connection. Flush single Connection ...");                    
////                    ConnectionWrapper  cw = (ConnectionWrapper)connection;                    
////                    flushSingleConnection(cw);
//                } else {    
//                    System.out.println("IoTOSAgroalReadOnlyPoolInterceptor.onConnectionReturn() Found unexcpected readOnly Connection. Flush ConnectionPool ...");
////                    flushCompletePool();
//                }
//            }
//        } catch (Exception ex) {
//            System.err.println("IoTOSAgroalReadOnlyPoolInterceptor.onConnectionReturn()::Got Exception during "  + action 
//                    +". Msg:" + ex.getMessage());
//        }
//    }
//    
//    
//    @Counted(
//        absolute = true,
//        name = "IoTOSAgroalReadOnlyPoolInterceptor.flushSingleConnection", 
//        displayName = "Amount of single flushed Connections triggered by returning a readOnly Connection")    
//    public void flushSingleConnection(ConnectionWrapper cw) {
//        String action = "check ConnectionWrapper";
//        try {
//            ConnectionHandler  ch = cw.getHandler();
//            action = "set ReadOnly Connection to be flushed";
//            ch.setFlushOnly();
//
//        } catch (Exception ex) {
//            System.err.println("IoTOSAgroalReadOnlyPoolInterceptor.flushSingleConnection()::Got Exception during "  + action 
//                    +". Msg:" + ex.getMessage());
//        }
//    }
//
//    
//    
//    @Counted(
//        absolute = true,
//        name = "IoTOSAgroalReadOnlyPoolInterceptor.flushCompletePool", 
//        displayName = "Amount of graceful flushes of the complete ConnectionPool triggered by returning a readOnly Connection")    
//    public void flushCompletePool() {
//        String action = "check isReadOnly-Connection";
//        try {
//            action = "prepare GRACEFUL-Flush of ConnectionPool";
//            AgroalDataSource ads = CDI.current().select(AgroalDataSource.class).get();
//            if (ads != null) {
//                ads.flush(AgroalDataSource.FlushMode.GRACEFUL);                
//                action = "GRACEFUL-Flush of ConnectionPool";
//            }
//        } catch (Exception ex) {
//            System.err.println("IoTOSAgroalReadOnlyPoolInterceptor.flushCompletePool()::Got Exception during "  + action 
//                    +". Msg:" + ex.getMessage());
//        }
//    }    
//
//    @Gauge(
//        absolute = true,
//        name = "IoTOSAgroalReadOnlyPoolInterceptor.getTotalReadOnlyConnection", 
//        unit = "Count",
//        displayName = "Amount of readOnly Connections found during returning Connections into ConnectionPool.")
//    public Long getTotalReadOnlyConnection() {
//        return readOnlyConnectionCnt.get();
//    }  
//}
