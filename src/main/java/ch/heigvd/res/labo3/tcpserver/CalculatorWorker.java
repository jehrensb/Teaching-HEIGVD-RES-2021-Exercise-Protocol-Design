/**
 * Laboratory   : 03
 * File         : CalculatorWorker.java
 * Author(s)    : Alexis Allemann, Hakim Balestrieri
 * Date         : 22.03.2021 - 10.04.2021
 * Compiler     : javac 11.0.4
 */

package ch.heigvd.res.labo3.tcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator worker implementation
 */
public class CalculatorWorker implements Runnable {

    private final static Logger LOG = Logger.getLogger(CalculatorWorker.class.getName());
    private final static int NB_PARAMETERS = 3;

    private Socket clientSocket;
    private BufferedReader in = null;
    private PrintWriter out = null;

    /**
     * Instantiation of a new worker mapped to a socket
     *
     * @param clientSocket connected to worker
     */
    public CalculatorWorker(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(CalculatorServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            String line;

            // Reading until client sends QUIT or closes the connection
            while ((line = in.readLine()) != null) {

                if (line.equals("QUIT"))
                    break;

                // Processing the request

                String[] arguments = line.split(" ");

                if (arguments.length != NB_PARAMETERS)
                    out.println("ERROR Invalid number of arguments");
                else {
                    String operation = line.substring(0, 3);
                    int firstNumber = 0;
                    int secondNumber = 0;
                    boolean validNumber = true;

                    // Try to parse integers
                    try {
                        firstNumber = Integer.parseInt(arguments[1]);
                        secondNumber = Integer.parseInt(arguments[2]);
                    } catch (NumberFormatException ex) {
                        out.println("ERROR Invalid number detected");
                        validNumber = false;
                    }

                    if (validNumber) {
                        switch (operation) {
                            case "ADD":
                                out.println("RESULT " + (firstNumber + secondNumber));
                                break;
                            case "SUB":
                                out.println("RESULT " + (firstNumber - secondNumber));
                                break;
                            case "MUL":
                                out.println("RESULT " + (firstNumber * secondNumber));
                                break;
                            case "DIV":
                                if (secondNumber == 0)
                                    out.println("ERROR Division by 0");
                                else
                                    out.println("RESULT " + (firstNumber / secondNumber));
                                break;
                            default:
                                out.println("ERROR Invalid operation");
                                break;
                        }
                    }
                }

                out.flush();
            }

            // Cleaning up resources
            clientSocket.close();
            in.close();
            out.close();

        } catch (IOException ex) {
            // Cleaning up resources
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            if (out != null) {
                out.close();
            }
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
