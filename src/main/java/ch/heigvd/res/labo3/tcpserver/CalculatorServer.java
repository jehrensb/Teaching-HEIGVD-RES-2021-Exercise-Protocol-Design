/**
 * Laboratory   : 03
 * File         : CalculatorServer.java
 * Author(s)    : Alexis Allemann, Hakim Balestrieri
 * Date         : 22.03.2021 - 10.04.2021
 * Compiler     : javac 11.0.4
 */

package ch.heigvd.res.labo3.tcpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator server implementation
 */
public class CalculatorServer implements Runnable {

    private final static Logger LOG = Logger.getLogger(CalculatorServer.class.getName());
    private static final int PORT = 9999;

    /**
     * Main function to run server
     *
     * @param args no args required
     */
    public static void main(String[] args) {
        new Thread(new CalculatorServer()).start();
    }

    @Override
    public void run() {
        ServerSocket serverSocket;

        // Creating server
        try {
            serverSocket = new ServerSocket(CalculatorServer.PORT);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return;
        }

        // Waiting for new clients to connect
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();

                // Start to answer client queries in a new thread
                new Thread(new CalculatorWorker(clientSocket)).start();
            } catch (IOException ex) {
                Logger.getLogger(CalculatorServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
