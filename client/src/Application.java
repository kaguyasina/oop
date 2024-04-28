public class Application {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 5000;
        new Interface(serverAddress, serverPort);
    }
}
