package jmsChat;
/**
 * Main class
 * starts the program
 * @author nanak
 */
public class JMSChatStart {
	private static String message_broker_ip;
	private static String username;
	private static String subject;

	/**
	 * Takes three arguments:
	 * 	<message_broker_ip> ... ip of the message broker
	 * 	<username> ............ name you want to use in the chatroom
	 * 	<subject> ............. chatroom you want to connect to
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		if (args.length == 3) {
			message_broker_ip = args[0];
			username = args[1];
			subject = args[2];
		}
		if (args.length != 3) {
			System.out.println("Synopsis: jmschat <message_broker_ip> <username> <chatroom>");
			System.exit(0);
		}
		JMSChatRun jmsc = new JMSChatRun(message_broker_ip, username, subject);
	}
}
