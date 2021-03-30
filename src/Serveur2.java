import java.io.*;
import java.net.*;

public class Serveur2 {

    public static void main(String[] args) {
        if (args.length < 1) return;

        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");


                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                writer.println("WELCOME");
                /*
                writer.println("- OPERATIONS");
                writer.println("- ADD 2");
                writer.println("- MULT 2");
                writer.println("- ENDOPERATIONS");
                 */


                String text;
                int resultat = 0;

                do {

                    text = reader.readLine();

                    System.out.println(text);

                    if(!text.equals("QUIT")) {

                        if (text != null) {
                            String[] arrOfStr = text.split(" ");

                            if (arrOfStr.length == 3) {

                                boolean operationValide;

                                if (arrOfStr[0].equals("ADD")) {
                                    resultat = Integer.parseInt(arrOfStr[1]) + Integer.parseInt(arrOfStr[2]);
                                    operationValide = true;

                                } else if (arrOfStr[0].equals("MULT")) {
                                    resultat = Integer.parseInt(arrOfStr[1]) * Integer.parseInt(arrOfStr[2]);
                                    operationValide = true;
                                } else {
                                    writer.println("ERROR 300 UNKNOWN OPERATION ");
                                    operationValide = false;
                                }

                                if (operationValide) {
                                    text = "" + resultat;
                                    writer.println("RESULT " + text);
                                }

                            } else {
                                writer.println("ERROR 400 SYNTAX ERROR");
                            }
                        }
                    }

                } while (!text.equals("QUIT"));

                socket.close();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}