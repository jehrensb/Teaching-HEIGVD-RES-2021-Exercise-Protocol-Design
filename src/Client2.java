import java.net.*;
import java.io.*;
import java.util.Scanner;


public class Client2 {

    public static void main(String[] args) {
        if (args.length < 2) return;

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        try (Socket socket = new Socket(hostname, port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String text;

            do {

                String result = reader.readLine();
                System.out.println(result);

                Scanner sc = new Scanner(System.in);

                String a = sc.nextLine();
                text = a;

                writer.println(text);

            } while (!text.equals("QUIT"));

            socket.close();

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}