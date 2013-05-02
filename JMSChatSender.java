package jmsChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.JOptionPane;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSChatSender {

	private static String user = ActiveMQConnection.DEFAULT_USER;
	private static String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static String subject = "VSDBChat";

	public static void main(String[] args) {

		// Create the connection.
		Session session = null;
		Connection connection = null;
		MessageProducer producer = null;
		Destination destination = null;

		try {

			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					user, password, url);
			connection = connectionFactory.createConnection();
			connection.start();

			// Create the session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createTopic(subject);

			// Create the producer.
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			// Read the message from command line
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Message: ");
			String msg = br.readLine();
			
			// Create the message
			TextMessage message = session
					.createTextMessage(msg);
			producer.send(message);
			System.out.println(message.getText());

		} catch (Exception e) {
			System.out.println("[MessageProducer] Caught: " + e);
			e.printStackTrace();
		} finally {
			try {
				producer.close();
			} catch (Exception e) {
			}
			try {
				session.close();
			} catch (Exception e) {
			}
			try {
				connection.close();
			} catch (Exception e) {
			}
		}

	}

}