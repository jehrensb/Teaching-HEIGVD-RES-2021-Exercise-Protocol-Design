import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 * @author Delphine Scherler & Alois Christen
 */

public class Server {
    final static Logger LOG = Logger.getLogger(Server.class.getName());

    int port;

    public Server() {
        this.port = 2021;
    }

    public void serveClients() {
        ServerSocket serverSocket;
        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return;
        }

        while (true) {
            try {

                LOG.log(Level.INFO, "Waiting (blocking) for a new client on port {2021}");
                clientSocket = serverSocket.accept();
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream());
                String line;
                boolean shouldRun = true;
                boolean errorOccured = false;

                out.println("Welcome to Calculator. Send me an operation. To quit enter QUIT command.");
                out.flush();
                LOG.info("Reading until client sends QUIT or closes the connection...");

                    while (shouldRun && ((line = in.readLine()) != null)) {
                        errorOccured = false;
                        if (line.equalsIgnoreCase("QUIT")) {
                            shouldRun = false;
                            out.println("QUITTING...");
                            out.flush();
                            continue;
                        }

                        String splited[] = line.split(" ");
                        String operand = splited[0];
                        if (splited.length != 3) {
                            out.println("ERROR : BAD OPERAND NUMBER");
                            errorOccured = true;
                        }

                        //Do the operation and return the result
                        double result = 0;
                        double number1 = 0;
                        double number2 = 0;
                        if (!errorOccured) {
                            try {
                                number1 = Double.parseDouble(splited[1]);
                                number2 = Double.parseDouble(splited[2]);
                            } catch (NumberFormatException e) {
                                out.println("ERROR : BAD NUMBER FORMAT");
                                errorOccured = true;
                            }
                        }
                        if (!errorOccured) {
                            switch (operand) {
                                case "ADD":
                                    result = number1 + number2;
                                    break;
                                case "SUB":
                                    result = number1 - number2;
                                    break;
                                case "MULT":
                                    result = number1 * number2;
                                    break;
                                default:
                                    out.println("ERROR : BAD OP");
                                    errorOccured = true;
                            }
                        }
                        if (!errorOccured) {
                            out.println("RESPONSE : " + result);
                        }
                        out.flush();
                    }

                LOG.info("Cleaning up resources...");
                clientSocket.close();
                in.close();
                out.close();

            } catch (IOException ex) {
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
    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");

        Server single = new Server();
        single.serveClients();
    }
}
