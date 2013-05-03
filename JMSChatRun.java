/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmsChat;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.Thread.MIN_PRIORITY;
import java.net.URI;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;


/**
 *
 * @author Paradox
 */
public class JMSChatRun extends Thread{

  private static String user = null;
  private static String password = ActiveMQConnection.DEFAULT_PASSWORD;
  private static String url = null;//"failover://tcp://192.168.1.101:61616";
  private static String subject = null;//"VSDBChat";
  private boolean go = true;
    
    public JMSChatRun (String ip, String user, String subject){
        this.user=user;
        this.url="failover://tcp://"+ip+":61616";
        this.subject=subject;
        this.start();
        
        
    }
    
    @Override
    public void run() {
        Session session = null;
	Connection connection = null;
	MessageConsumer consumer = null;
	Destination destination = null;
	MessageProducer producer = null;
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
            connection = connectionFactory.createConnection();
            connection.start();
            // Create the session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic( subject );
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            destination = session.createTopic( subject );
		  
            // Create the producer.
            producer = session.createProducer(destination);
            producer.setDeliveryMode( DeliveryMode.PERSISTENT );
            while (go){
		// Create the consumer
		consumer = session.createConsumer( destination );
		// Start receiving
		TextMessage message = (TextMessage) consumer.receive(100);
                if ( message != null ) {
                    System.out.println("Message received: " + message.getText() );
                    message.acknowledge();
                 }
                 if(br.ready()){
                     String m = br.readLine();
                     if(m!="EXIT"){
                        TextMessage messageSend = session.createTextMessage(m);
                        producer.send(messageSend);
                     }else{
                         go=false;
                     }
                 }
            }
         } catch (Exception e) {
	      System.out.println("[MessageConsumer] Caught: " + e);
	      e.printStackTrace();
	} finally {
		try { consumer.close(); } catch ( Exception e ) {}
		try { session.close(); } catch ( Exception e ) {}
		try { connection.close(); } catch ( Exception e ) {}
	}
  
        
        
		
    
    
    }
    
    
    
    
}
