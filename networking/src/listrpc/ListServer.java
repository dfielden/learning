package listrpc;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ListServer {
	private final LinkedList<String> list = new LinkedList<>();


	public void start() throws IOException {
		try (
				ServerSocket serverSocket = new ServerSocket(ListCommon.PORT_NUMBER);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
		) {
			while (true) {
				String methodName = in.readLine();  // Read first line from client - will be method name.
				String string = in.readLine();  // Read second line from client - will be method arg.
				if (methodName == null) {
					out.println("method name is null");
					return; // Client has disconnected.
				}
				switch(methodName) {
					case "add":
						list.add(string);
						out.println("added: " + string);
						break;
					case "remove":
						out.println(list.remove(string) ? "removed: " + string : string + " not in list");
						break;
					case "clear":
						list.clear();
						out.println("list cleared");
						break;
					case "get":
						out.println(list.get(Integer.parseInt(string)));
						break;
					case "printList":
						StringBuilder sb = new StringBuilder();
						sb.append("List:");
						for (int i = 0; i < list.size(); i++) {
							sb.append("	").append(list.get(i));
						}
						out.println(sb.toString());
						break;
					default:
						out.println("method name: " + methodName + " not recognised");
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		ListServer listServer = new ListServer();
		listServer.start();
	}
}
