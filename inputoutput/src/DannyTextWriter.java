import java.io.*;

public class DannyTextWriter {
	public static void main(String[] args) throws IOException {
		try (
				BufferedWriter dannyWriter = new BufferedWriter(new FileWriter("dannytxt.txt", true));
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in))
		) {
			String userInput;
			while ((userInput = input.readLine()) != null) {
				System.out.println(userInput);
				dannyWriter.write(userInput + "\n");
				dannyWriter.flush();
			}
		}
	}
}
