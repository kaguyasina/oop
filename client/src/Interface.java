import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface {
    private ClientCommunicator communicator;
    private Matrix<?> matrix;

    public Interface(String serverAddress, int serverPort) {
        communicator = new ClientCommunicator(serverAddress, serverPort);

        JFrame frame = new JFrame("Matrix Operations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1));

        // Combo box for selecting matrix data type
        String[] matrixTypes = {"Real", "Complex", "Rational"};
        JComboBox<String> typeComboBox = new JComboBox<>(matrixTypes);

        // Labels and fields for entering complex or rational numbers
        JLabel realLabel = new JLabel("Real Part:");
        JTextField realField = new JTextField();
        JLabel imaginaryLabel = new JLabel("Imaginary Part:");
        JTextField imaginaryField = new JTextField();
        JLabel numeratorLabel = new JLabel("Numerator:");
        JTextField numeratorField = new JTextField();
        JLabel denominatorLabel = new JLabel("Denominator:");
        JTextField denominatorField = new JTextField();

        // Initially hide the fields for complex and rational numbers
        realLabel.setVisible(false);
        realField.setVisible(false);
        imaginaryLabel.setVisible(false);
        imaginaryField.setVisible(false);
        numeratorLabel.setVisible(false);
        numeratorField.setVisible(false);
        denominatorLabel.setVisible(false);
        denominatorField.setVisible(false);

        // Listener for type selection combo box
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = typeComboBox.getSelectedItem().toString();
                // Show or hide fields based on selected type
                switch (selectedType) {
                    case "Real":
                        realLabel.setVisible(true);
                        realField.setVisible(true);
                        imaginaryLabel.setVisible(false);
                        imaginaryField.setVisible(false);
                        numeratorLabel.setVisible(false);
                        numeratorField.setVisible(false);
                        denominatorLabel.setVisible(false);
                        denominatorField.setVisible(false);
                        break;
                    case "Complex":
                        realLabel.setVisible(true);
                        realField.setVisible(true);
                        imaginaryLabel.setVisible(true);
                        imaginaryField.setVisible(true);
                        numeratorLabel.setVisible(false);
                        numeratorField.setVisible(false);
                        denominatorLabel.setVisible(false);
                        denominatorField.setVisible(false);
                        break;
                    case "Rational":
                        realLabel.setVisible(false);
                        realField.setVisible(false);
                        imaginaryLabel.setVisible(false);
                        imaginaryField.setVisible(false);
                        numeratorLabel.setVisible(true);
                        numeratorField.setVisible(true);
                        denominatorLabel.setVisible(true);
                        denominatorField.setVisible(true);
                        break;
                }
            }
        });

        JButton enterButton = new JButton("Enter matrix values");
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = typeComboBox.getSelectedItem().toString();
                enterMatrix(selectedType);
            }
        });

        JButton determinantButton = new JButton("Calculate matrix determinant");
        determinantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (matrix != null) {
                    // Отправить матрицу на сервер для вычисления определителя
                    Rational determinant = communicator.calculateDeterminant((Matrix<Rational>) matrix);
                    if (determinant != null) {
                        JOptionPane.showMessageDialog(frame, "Matrix determinant: " + determinant);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to calculate determinant.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter matrix values first.");
                }
            }
        });

        JButton transposedButton = new JButton("Get the transposed matrix");
        transposedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (matrix != null) {
                    if (matrix instanceof Matrix) {
                        Rational[][] transposedMatrix = communicator.getTransposedMatrix((Matrix<Rational>) matrix);
                        if (transposedMatrix != null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Transposed matrix:\n");
                            for (Rational[] row : transposedMatrix) {
                                for (Rational element : row) {
                                    sb.append(element.getRealPart());
                                    if (element.getDenominator() != 1) {
                                        sb.append("/").append(element.getDenominator());
                                    }
                                    if (element.getImaginaryPart() != 0) {
                                        if (element.getImaginaryPart() > 0) {
                                            sb.append("+").append(element.getImaginaryPart());
                                        } else {
                                            sb.append(element.getImaginaryPart());
                                        }
                                        sb.append("i ");
                                    } else {
                                        sb.append(" ");
                                    }
                                }
                                sb.append("\n");
                            }
                            JOptionPane.showMessageDialog(frame, sb.toString());
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to get transposed matrix.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Transposed matrix is available only for general matrices.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter matrix values first.");
                }
            }
        });

        JButton rankButton = new JButton("Calculate matrix rank");
        rankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (matrix != null) {
                    Rational rank = communicator.calculateRank((Matrix<Rational>) matrix);
                    if (rank != null) {
                        JOptionPane.showMessageDialog(frame, "Matrix rank: " + rank);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to calculate rank.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter matrix values first.");
                }
            }
        });

        JButton printButton = new JButton("Print matrix");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (matrix != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Matrix:\n");
                    for (Rational[] row : matrix.getMatrix()) {
                        for (Rational element : row) {
                            if (element instanceof Rational) {
                                sb.append(element.getRealPart());
                                if (element.getDenominator() != 1) {
                                    sb.append("/").append(element.getDenominator());
                                }
                                if (element.getImaginaryPart() != 0) {
                                    if (element.getImaginaryPart() > 0) {
                                        sb.append("+").append(element.getImaginaryPart());
                                    } else {
                                        sb.append(element.getImaginaryPart());
                                    }
                                    sb.append("i ");
                                } else {
                                    sb.append(" ");
                                }
                            } else {
                                // Если элемент вещественный, просто выводим его как вещественную часть
                                sb.append(element.getRealPart()).append(" ");
                            }
                        }
                        sb.append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, sb.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter matrix values first.");
                }
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(typeComboBox);
        panel.add(realLabel);
        panel.add(realField);
        panel.add(imaginaryLabel);
        panel.add(imaginaryField);
        panel.add(numeratorLabel);
        panel.add(numeratorField);
        panel.add(denominatorLabel);
        panel.add(denominatorField);
        panel.add(enterButton);
        panel.add(determinantButton);
        panel.add(transposedButton);
        panel.add(rankButton);
        panel.add(printButton);
        panel.add(exitButton);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
    }

    private void enterMatrix(String type) {
        JFrame frame = new JFrame("Enter Matrix Values");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel rowsLabel = new JLabel("Number of rows:");
        JTextField rowsField = new JTextField();
        JLabel colsLabel = new JLabel("Number of columns:");
        JTextField colsField = new JTextField();

        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rows = Integer.parseInt(rowsField.getText());
                int cols = Integer.parseInt(colsField.getText());
                Rational[][] values = new Rational[rows][cols];
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        String inputMessage;
                        if (type.equals("Real")) {
                            inputMessage = "Enter value of element [" + (i + 1) + "][" + (j + 1) + "]:";
                            int realPart = Integer.parseInt(JOptionPane.showInputDialog(frame, inputMessage));
                            values[i][j] = new Rational(realPart); // Using the constructor for Real numbers
                        } else if (type.equals("Complex")) {
                            inputMessage = "Enter real part of element [" + (i + 1) + "][" + (j + 1) + "]:";
                            int realPart = Integer.parseInt(JOptionPane.showInputDialog(frame, inputMessage));
                            inputMessage = "Enter imaginary part of element [" + (i + 1) + "][" + (j + 1) + "]:";
                            int imaginaryPart = Integer.parseInt(JOptionPane.showInputDialog(frame, inputMessage));
                            values[i][j] = new Rational(realPart, imaginaryPart); // Using the constructor for Complex numbers
                        } else if (type.equals("Rational")) {
                            values[i][j] = new Rational(
                                    Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter numerator of element [" + (i + 1) + "][" + (j + 1) + "]:")),
                                    Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter denominator of element [" + (i + 1) + "][" + (j + 1) + "]:"))
                            ); // Using the constructor for Rational numbers
                        }
                    }
                }
                switch (type) {
                    case "Real":
                    case "Complex":
                    case "Rational":
                        matrix = new Matrix(values);
                        break;
                    default:
                        break;
                }
                frame.dispose();
            }
        });

        panel.add(rowsLabel);
        panel.add(rowsField);
        panel.add(colsLabel);
        panel.add(colsField);
        panel.add(enterButton);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
    }
}
