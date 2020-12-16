import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DannyTextReader {
	public static void main(String[] args) throws IOException {
		try (
				BufferedReader dannyReader = new BufferedReader(new FileReader("dannytxt.txt"))
		) {
			String line;
			while ((line = dannyReader.readLine()) != null) {
				System.out.println(line);
			}
		}
	}
}
