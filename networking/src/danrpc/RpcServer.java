package danrpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcServer {

	public void start() throws IOException {
		try (
				ServerSocket serverSocket = new ServerSocket(RpcCommon.PORT_NUMBER);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
		) {
			while (true) {
				String methodName = in.readLine();  // Read first line from client - will be method name.
				String argument = in.readLine();  // Read second line from client - will be method arg.
				if (methodName == null) {
					return; // Client has disconnected.
				}
				if (methodName.equals("dogubby")) {
					out.println(dogubby(argument));
				} else if (methodName.equals("tally")) {
					out.println(tally(argument));
				} else {
					out.println("that is not an accepted critter!");
				}
			}
		}
	}

	public String dogubby(String argument) {
		return "I am a dogubby! Thank you for saying: " + argument;
	}

	public String tally(String argument) {
		return "I am a tallysheep! BAAAA: " + argument;
	}


	public static void main(String[] args) throws IOException {
		RpcServer rpcServer = new RpcServer();
		rpcServer.start();
	}
}
