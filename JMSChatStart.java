package jmsChat;
/**
 * Main class
 * starts the program
 * @author nanak
 */
public class JMSChatStart {
	private static String message_broker_ip;
	private static String username;
	private static String chatroom;

	/**
	 * Takes three arguments:
	 * 	<message_broker_ip> ... ip of the message broker
	 * 	<username> ............ name you want to use in the chatroom
	 * 	<chatroom> ............ chatroom you want to connect to
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		if (args.length == 3) {
			message_broker_ip = args[0];
			username = args[1];
			chatroom = args[2];
		}
		if (args.length != 3) {
			System.out.println("Synopsis: jmschat <message_broker_ip> <username> <password>");
		}
	}
}
