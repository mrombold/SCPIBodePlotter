package instrumentation;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Instrument {
    private static final Logger LOGGER = Logger.getLogger(Instrument.class.getName());
    private final String ipAddress;
    private final int port;

    private Socket instrumentSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Instrument(String ipAddress) {
        this.ipAddress = ipAddress;
        this.port = 5025;
    }

    public void connect() {
        try {
            this.instrumentSocket = new Socket(ipAddress, port);
            this.out = new PrintWriter(instrumentSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(instrumentSocket.getInputStream()));
            LOGGER.log(Level.INFO, "Connected to instrument at {0}", ipAddress);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to establish connection to {0}", new Object[]{ipAddress, e});
        }
    }

    public void disconnect() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (instrumentSocket != null) instrumentSocket.close();
            LOGGER.log(Level.INFO, "Disconnected from instrument at {0}", ipAddress);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to close connection", e);
        }
    }

    public void sendCommand(String command) {
        try {
            out.println(command);  // Send command to the instrument
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to send command to {0}", new Object[]{ipAddress, e});
        }
    }

    public String readMeasurement() {
        try {
            String response = in.readLine();  // Read response from the instrument
            return response;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to read measurement from {0}", new Object[]{ipAddress, e});
            return null;
        }
    }
}

