package listrpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ListClient {
	private final PrintWriter out;
	private final BufferedReader in;

	public ListClient(String address, int portNum) throws IOException {
		Socket socket = new Socket(address, portNum);
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	private String call(String methodName, String argument) throws IOException {
		out.println(methodName);
		out.println(argument);
		String s = in.readLine();
		System.out.println(String.format("%s(%s) --> %s", methodName, argument, s));
		return s;
	}

	public void add(String s) throws IOException {
		call("add", s);
	}

	public void remove(String s) throws IOException {
		call("remove", s);
	}

	public void clear() throws IOException {
		call("clear", "");
	}

	public String get(int i) throws IOException {
		return call("get", Integer.toString(i));
	}

	public void printList() throws IOException {
		call("printList", "");
	}


	public static void main(String[] args) throws Exception {
		ListClient client = new ListClient("localhost", ListCommon.PORT_NUMBER);

		client.add("tally");
		client.printList();

		client.add("danny");
		client.add("happyface");
		client.add("dogubby");
		client.add("worm");
		client.printList();

		client.remove("tally2");
		client.printList();

		client.remove("tally");
		client.printList();

		client.get(2);
		client.get(20);

		client.clear();
		client.printList();
	}
}

