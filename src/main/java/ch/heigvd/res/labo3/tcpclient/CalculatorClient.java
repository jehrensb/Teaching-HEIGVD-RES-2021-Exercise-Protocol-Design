/**
 * Laboratory   : 03
 * File         : CalculatorClient.java
 * Author(s)    : Alexis Allemann, Hakim Balestrieri
 * Date         : 22.03.2021 - 10.04.2021
 * Compiler     : javac 11.0.4
 */

package ch.heigvd.res.labo3.tcpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator client implementation
 */
public class CalculatorClient {

    private static final Logger LOG = Logger.getLogger(CalculatorClient.class.getName());
    private static final int PORT = 9999;

    /**
     * Main function to run client
     *
     * @param args no args required
     */
    public static void main(String[] args) {
        new CalculatorClient().calculate();
    }

    /**
     * Calculate method of the calculator to send requests to the TCP server
     */
    public void calculate() {

        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {

            // Connecting to the server
            clientSocket = new Socket("localhost", PORT);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

            String response;
            String line;

            // Send requests until QUIT is sent or client is closed
            while (!clientSocket.isClosed()) {
                System.out.println("Type you query :");

                // Reading user input
                if ((line = stdin.readLine()) != null) {
                    out.println(line);
                    out.flush();

                    if (line.equals("QUIT"))
                        break;
                }

                // Waiting for server response
                if ((response = in.readLine()) != null) {
                    if (response.contains("ERROR"))
                        System.out.println(response.substring(6));
                    else
                        System.out.println(response.substring(7));
                }
            }

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            // Cleaning up resources
            try {
                assert in != null;
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(CalculatorClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            assert out != null;
            out.close();
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(CalculatorClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
