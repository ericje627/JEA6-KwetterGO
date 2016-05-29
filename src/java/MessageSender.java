/**
 *
 * @author Eric
 */
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@ManagedBean(name="KwetterGO")
public class MessageSender{  
    
    private String name;
    private String message;
    
    private static final Logger LOG = Logger.getLogger(MessageSender.class.getName());
    /**
     * the preconfigured GlassFish-default connection factory
     */
    private static final String JNDI_CONNECTION_FACTORY = "jms/__defaultConnectionFactory";
    /**
     * the JNDI name under which your {@link Topic} should be: you have to
     * create the topic before running this class
     */
    private static final String JNDI_QUEUE = "jms/MyQueue";
    
    private static <T> T lookup(Class<T> retvalClass, String jndi) {
        try {
            return retvalClass.cast(InitialContext.doLookup(jndi));
        } catch (NamingException ex) {
            throw new IllegalArgumentException("failed to lookup instance of " + retvalClass + " at " + jndi, ex);
        }
    }
    
    public void sendMessage() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final ConnectionFactory connectionFactory = lookup(ConnectionFactory.class, JNDI_CONNECTION_FACTORY);
        final Queue queue = lookup(Queue.class, JNDI_QUEUE);

        JMSContext jmsContext = connectionFactory.createContext();
        final JMSProducer producer = jmsContext.createProducer();
        
        // Prepare data for sending
        Map messageMap = new HashMap();
        messageMap.put("Message", message);
        messageMap.put("SenderName", name);
        
        producer.send(queue, messageMap);
        LOG.log(Level.INFO, "sent {0} to {1}", new Object[]{messageMap, JNDI_QUEUE});
    }
    
    public String getSenderName(){  return name; }
    
    public void setSenderName(String senderName){ this.name = senderName; }
    
    public String getMessage(){ return message; }
    
    public void setMessage(String message){ this.message = message; }
}   

