import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class EchoServerGoodDanny {

	public static void main(String[] args) {
		final String[] answers = {"good danny is good!", "the danny is very good indeed!",
				"i believe the good danny to be the best", "good danny is excellent!", "everyone should listen to the good danny",
				"the most clever person i know is good danny", "good danny is very kind to the tally", "danny tries his best",
				"it makes the danny happy to see the positive replies", "woof!", "the good danny is almost as happy as a happy face"};
		Random random = new Random();
		try (
				// Create a ServerSocket that listens on a specific port (one that is not already dedicated to another service)
				// ServerSocket constructor throws exception if unable to connect
				ServerSocket serverSocket = new ServerSocket(EchoCommon.PORT_NUMBER);
				// accept method waits until a client starts up and requests a connection on host and port of this server
				// Once connection is successfully established, a Socket object is returned
				// This Socket is bound to the same local port and has its remote address and port set to that of the client
				Socket clientSocket = serverSocket.accept();
				// Get socket's input and output stream and open readers and writers on them
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader((new InputStreamReader(clientSocket.getInputStream())));
		) {
			String inputLine;
			// communicate with client by reading from and writing to the socket
			// as long as client and server have something to say to each other, messages continue to be sent back and forth
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.toLowerCase().contains("good danny")) {
					out.println(answers[random.nextInt(answers.length)]);
				} else {
					out.println("Sorry - I only understand questions about the good danny");
				}
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + EchoCommon.PORT_NUMBER +
					" or listening for a connection");
			e.printStackTrace();
		}
	}
}
