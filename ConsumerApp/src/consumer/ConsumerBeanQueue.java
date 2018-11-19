package consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import interceptor.Logged;

/**
 * Message-Driven Bean implementation class for: ConsumerBeanQueue
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "DLQ"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "DLQ")
public class ConsumerBeanQueue implements MessageListener {
  private static final Logger logger =
      Logger.getLogger(ConsumerBeanQueue.class.getCanonicalName());  
     
    /**
     * Default constructor. 
     */
    public ConsumerBeanQueue() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    @Logged
    public void onMessage(Message message) {
      String msg ="";
      List<String> list = new ArrayList<>();
        try {
          msg = message.getBody(String.class);
        } catch (JMSException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        logger.log(Level.INFO, msg);
    }
    

}
