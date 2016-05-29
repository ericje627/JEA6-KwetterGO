import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.*;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Named;

@Named
@MessageDriven(mappedName = "jms/MyQueue", activationConfig = {@ActivationConfigProperty(propertyName = "acknowledgeMode",
                                  propertyValue = "Auto-acknowledge"), @ActivationConfigProperty(propertyName = "destinationType",
                                  propertyValue = "javax.jms.Queue")})
public class MessageReceiver implements Serializable, MessageListener{
    @Resource
    private MessageDrivenContext mdc;
    
    private static final Logger LOG = Logger.getLogger(MessageReceiver.class.getName());
    
    @Override
    public void onMessage(Message message) {
        try {
            	Map messageMap = message.getBody(Map.class);
                LOG.log(Level.INFO, String.format("Received message from %s containing %s",messageMap.get("SenderName"),messageMap.get("Message")));
                } catch (JMSException ex) {
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            	}
    	}
    
}
