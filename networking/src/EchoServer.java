import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static void main(String[] args) {
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
			BufferedReader in = new BufferedReader((new InputStreamReader(clientSocket.getInputStream())))
		) {
			String inputLine;
			// communicate with client by reading from and writing to the socket
			// as long as client and server have something to say to each other, messages continue to be sent back and forth
			while ((inputLine = in.readLine()) != null) {
				out.println(inputLine);
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + EchoCommon.PORT_NUMBER +
					" or listening for a connection");
			e.printStackTrace();
		}
	}
}
