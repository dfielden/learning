package danrpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RpcClient {
	private final PrintWriter out;
	private final BufferedReader in;

	public RpcClient(String address, int portNum) throws IOException {
		Socket socket = new Socket(address, portNum);
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public String call(String methodName, String argument) throws IOException {
		out.println(methodName);
		out.println(argument);
		return in.readLine();
	}


	public static void main(String[] args) throws IOException {
		RpcClient client = new RpcClient("localhost", RpcCommon.PORT_NUMBER);

		String tallyAnswer = client.call("tally", "hello");
		System.out.println("tally answer: " + tallyAnswer);

		String wormAnswer = client.call("worm", "hello");
		System.out.println("worm answer: " + wormAnswer);

		String dogubbyAnswer = client.call("dogubby", "hello");
		System.out.println("dogubby answer: " +dogubbyAnswer);

		for (int i=0; i<20; i++) {
			System.out.println("i = " + i + ": " + client.call("tally", "i="+i));
		}
	}
}

