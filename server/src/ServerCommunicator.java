import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCommunicator {
    private ServerSocket serverSocket;

    public ServerCommunicator(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() {
        try {
            System.out.println("Server is running...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                handleClient(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClient(Socket socket) {
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            while (true) {
                String operationType = (String) inputStream.readObject();
                if (operationType == null) break;

                switch (operationType) {
                    case "determinant":
                        handleDeterminantOperation(inputStream, outputStream);
                        break;
                    case "transpose":
                        handleTransposeOperation(inputStream, outputStream);
                        break;
                    case "rank":
                        handleRankOperation(inputStream, outputStream);
                        break;
                    default:
                        System.out.println("Unknown operation type: " + operationType);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) outputStream.close();
                if (inputStream != null) inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void handleDeterminantOperation(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        Matrix<Rational> matrix = (Matrix<Rational>) inputStream.readObject();
        Rational determinant;
        try {
            determinant = matrix.calculateDeterminant();
        } catch (IllegalArgumentException e) {
            determinant = null;
        }
        outputStream.writeObject(determinant);
    }

    private void handleTransposeOperation(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        Matrix<Rational> matrix = (Matrix<Rational>) inputStream.readObject();
        Rational[][] transposedMatrix = matrix.getTransposedMatrix();
        outputStream.writeObject(transposedMatrix);
    }

    private void handleRankOperation(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        Matrix<Rational> matrix = (Matrix<Rational>) inputStream.readObject();
        Rational rank = matrix.calculateRank();
        outputStream.writeObject(rank);
    }
}