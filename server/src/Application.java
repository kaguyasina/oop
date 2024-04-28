import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        int defaultPort = 5000;

        ServerCommunicator communicator;
        try {
            communicator = new ServerCommunicator(defaultPort);
            communicator.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
