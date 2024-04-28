import java.io.*;
import java.net.Socket;

public class ClientCommunicator {
    private String serverAddress;
    private int serverPort;

    public ClientCommunicator(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public Rational calculateDeterminant(Matrix<Rational> matrix) {
        try (
                Socket socket = new Socket(serverAddress, serverPort);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())
        ) {
            outputStream.writeObject("determinant");
            outputStream.writeObject(matrix);
            return (Rational) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Rational[][] getTransposedMatrix(Matrix<Rational> matrix) {
        try (
                Socket socket = new Socket(serverAddress, serverPort);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())
        ) {
            outputStream.writeObject("transpose");
            outputStream.writeObject(matrix);
            return (Rational[][]) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Rational calculateRank(Matrix<Rational> matrix) {
        try (
                Socket socket = new Socket(serverAddress, serverPort);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())
        ) {
            outputStream.writeObject("rank");
            outputStream.writeObject(matrix);
            return (Rational) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
