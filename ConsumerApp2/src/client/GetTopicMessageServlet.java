package client;

import java.io.IOException;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.InvalidClientIDRuntimeException;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetTopicMessageServlet
 */
@WebServlet("/get2")
public class GetTopicMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup="java:/ConnectionFactory")
  private ConnectionFactory connectionFactory;
  @Resource(lookup="java:/jms/topic/test")
  private Topic topic;    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTopicMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  JMSContext context = connectionFactory.createContext();
	  
	  if(context.getClientID() == "" || context.getClientID() == null) {
	    context.setClientID("hallo");
	  } 

    response.setContentType("text/plain");
    JMSConsumer consumer1 = context.createDurableConsumer(topic,"hallo");
    
    String message = "Received from consumer 1: " + consumer1.receiveBody(String.class);
    try {
      consumer1.receive().clearBody();
    } catch (JMSException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    consumer1.close();
    
    JMSConsumer consumer2 = context.createDurableConsumer(topic,"hallo");
    message += "Read from consumer 2: " + consumer2.receiveBody(String.class);
    ServletOutputStream out = response.getOutputStream();
    consumer2.close();
    
    context.unsubscribe("hallo");
    out.write(message.getBytes());
    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
