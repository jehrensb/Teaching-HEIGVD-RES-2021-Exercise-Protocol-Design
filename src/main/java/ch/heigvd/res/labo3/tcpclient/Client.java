package ch.heigvd.res.labo3.tcpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    final static Logger LOG = Logger.getLogger(Client.class.getName());
    final int PORT = 9999;

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");

        Client client = new Client();
        client.sendHttpRequest();
    }

    public void sendHttpRequest() {
        Socket clientSocket = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            clientSocket = new Socket("localhost", PORT);
            os = clientSocket.getOutputStream();
            is = clientSocket.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter request : ");
        String httpRequest = scanner.nextLine();

        while (httpRequest != "QUIT") {
            try {
                os.write(httpRequest.getBytes());

                ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
                byte[] buffer = new byte[10000];
                int newBytes;

                while ((newBytes = is.read(buffer)) != -1) {
                    responseBuffer.write(buffer, 0, newBytes);
                }
                LOG.log(Level.INFO, "Response sent by the server:   ");
                LOG.log(Level.INFO, responseBuffer.toString());

                String response = responseBuffer.toString();
                String clearFormat = null;
                if (response.contains("ERROR")) {
                    clearFormat = response.substring(5);
                }
                else if (response.contains("RESULT")) {
                    clearFormat = response.substring(6);
                }

                System.out.println(clearFormat);
            }
            catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
            finally {
                try {
                    is.close();
                    os.close();
                    clientSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Enter request : ");
                httpRequest = scanner.nextLine();
            }
        }

    }

}
