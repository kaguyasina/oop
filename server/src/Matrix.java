import java.io.Serializable;
import java.util.Arrays;

public class Matrix<T extends Rational> implements Serializable {
    private static final long serialVersionUID = 1L;

    protected T[][] matrix;

    public Matrix(T[][] matrix) {
        this.matrix = matrix;
    }

    public Rational calculateDeterminant() {
        if (!isSquare(matrix)) {
            throw new IllegalArgumentException("Matrix must be square to calculate determinant.");
        }

        boolean isComplex = false;
        boolean isRational = false;

        // Проверяем типы чисел во входной матрице
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].getImaginaryPart() != 0) {
                    // Если встречается комплексное число, устанавливаем флаг isComplex в true
                    isComplex = true;
                } else if (matrix[i][j].getDenominator() != 1) {
                    // Если встречается рациональное число, устанавливаем флаг isRational в true
                    isRational = true;
                }
            }
        }

        if (matrix.length == 1) {
            return matrix[0][0];
        } else if (matrix.length == 2) {
            return subtract(multiply(matrix[0][0], matrix[1][1]), multiply(matrix[0][1], matrix[1][0]));
        } else {
            Rational det = new Rational(0, 1);
            for (int i = 0; i < matrix.length; i++) {
                det = add(det, multiply(new Rational((int) Math.pow(-1, i), 1), multiply(matrix[0][i], calculateMinor(0, i))));
            }
            // Проверяем, является ли результат рациональным, комплексным или целым числом
            if (det.getImaginaryPart() != 0 || isComplex) {
                // Если результат комплексный или в матрице есть комплексные числа, возвращаем комплексное число
                return new Rational(det.getRealPart(), det.getImaginaryPart());
            } else if (det.getDenominator() != 1 || isRational) {
                // Если результат рациональный или в матрице есть рациональные числа, возвращаем рациональное число
                return new Rational(det.getNumerator(), det.getDenominator());
            } else {
                // В противном случае результат - целое число
                return new Rational(det.getRealPart());
            }
        }
    }






    public Rational add(Rational a, Rational b) {
        int numerator = a.getNumerator() * b.getDenominator() + b.getNumerator() * a.getDenominator();
        int denominator = a.getDenominator() * b.getDenominator();
        return new Rational(numerator, denominator);
    }

    private Rational[][] createMinorMatrix(T[][] matrix, int row, int col) {
        int size = matrix.length - 1;
        Rational[][] minorMatrix = new Rational[size][size];
        for (int i = 0, m = 0; i < matrix.length; i++) {
            if (i == row) continue;
            for (int j = 0, n = 0; j < matrix.length; j++) {
                if (j == col) continue;
                minorMatrix[m][n] = matrix[i][j];
                n++;
            }
            m++;
        }
        return minorMatrix;
    }


    public Rational[][] getTransposedMatrix() {
        int rows = matrix.length;
        int cols = matrix[0].length;
        Rational[][] transposed = new Rational[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                transposed[i][j] = (Rational) matrix[j][i];
            }
        }
        return transposed;
    }

    public Rational calculateRank() {
        int rank = 0;
        int rows = matrix.length;
        int cols = matrix[0].length;
        T[][] copy = Arrays.copyOf(matrix, rows);

        for (int row = 0; row < rows; row++) {
            if (rank == cols) break;
            int pivot = row;
            while (pivot < rows && copy[pivot][row].doubleValue() == 0) {
                pivot++;
            }
            if (pivot < rows) {
                T[] temp = copy[row];
                copy[row] = copy[pivot];
                copy[pivot] = temp;
                for (int i = row + 1; i < rows; i++) {
                    int factor = copy[i][row].getNumerator() / copy[row][row].getNumerator();
                    for (int j = row; j < cols; j++) {
                        copy[i][j] = (T) subtract(copy[i][j], multiply(copy[row][j], new Rational(factor, 1)));
                    }
                }
                rank++;
            }
        }
        return new Rational(rank, 1);
    }

    public void printMatrix() {
        for (T[] row : matrix) {
            for (T element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

    private Rational[][] createMinorMatrix(int row, int col) {
        int size = matrix.length - 1;
        Rational[][] minorMatrix = new Rational[size][size];
        for (int i = 0, m = 0; i < matrix.length; i++) {
            if (i == row) continue;
            for (int j = 0, n = 0; j < matrix.length; j++) {
                if (j == col) continue;
                minorMatrix[m][n] = (Rational) matrix[i][j];
                n++;
            }
            m++;
        }
        return minorMatrix;
    }

    private boolean isSquare(T[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        return rows == cols;
    }



    protected Rational subtract(Rational a, Rational b) {
        int numerator = a.getNumerator() * b.getDenominator() - b.getNumerator() * a.getDenominator();
        int denominator = a.getDenominator() * b.getDenominator();
        return new Rational(numerator, denominator);
    }

    protected Rational multiply(Rational a, Rational b) {
        int numerator = a.getNumerator() * b.getNumerator();
        int denominator = a.getDenominator() * b.getDenominator();
        return new Rational(numerator, denominator);
    }
    public Rational calculateMinor(int row, int col) {
        Rational[][] minorMatrix = createMinorMatrix(row, col);
        return new SquareMatrix<>(minorMatrix).calculateDeterminant();
    }


}
