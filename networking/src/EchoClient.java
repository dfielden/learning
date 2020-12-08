import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

	public static void main(String[] args) {
		final String address = "localhost";

		try (
				// create a Socket
				Socket socket = new Socket(address, EchoCommon.PORT_NUMBER);
				// get the socket's output stream and open a PrintWriter on it. Boolean autoflush set to true
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				// Get the socket's input stream and open a Buffered Reader on it
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// Create Buffered Reader to read the server's response
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		) {
			String userInput;
			System.out.println("ask me about good danny");

			// Read the standard input stream one line at a time and immediately send to server by writing it to the
			// Print Writer connected to the socket
			while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				// Reads a line of information from the Buffered Reader connected to the socket.
				// readLine method waits until the server echoes the info back to the client
				// When readLine returns, the client prints the info to the standard output
				System.out.println("answer: " + in.readLine());
				// while loop continues until user enters an end-of-input character (Ctrl-C)
				// Then Java runtime closes readers, writers and socket connections automatically as try-with-resources used.
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + address);
			System.exit(1); // "By default a non-zero status indicates an abnormal termination"
		} catch (IOException e) {
			System.err.println("Couldn't get I/O connection to: " + address);
			System.exit(1);
		}
	}
}


// General workflow:
// 1) Open a Socket
// 2) Open an input stream and output stream to the socket
// 3) Read from and write to the stream according to the server's protocol - only this step differs from client to client - depending on the server
// 4) Close the streams
// 5) Close the socket

// NB: Try-with-resources closes resources in reverse order they were created