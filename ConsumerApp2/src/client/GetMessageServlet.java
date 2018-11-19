package client;

import java.io.IOException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class SendMessageServlet
 */
@WebServlet("/get")
public class GetMessageServlet extends HttpServlet {
 
  @Resource(lookup="java:/ConnectionFactory")
  private ConnectionFactory connectionFactory;
  @Resource(lookup="java:/jms/queue/DLQ")
  private Queue queue;
 
  
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	  JMSContext context = connectionFactory.createContext();
	  context.recover();
		response.setContentType("text/plain");
		JMSConsumer consumer = context.createConsumer(queue);
		String message = consumer.receiveBody(String.class);
		consumer.close();
		ServletOutputStream out = response.getOutputStream();
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
