import java.io.Serializable;
import java.util.Arrays;

public class SquareMatrix<T extends Rational> extends Matrix<T>{
    private static final long serialVersionUID = 1783879263198769307L; // Замените на актуальное значение serialVersionUID

    public SquareMatrix(T[][] matrix) {
        super(matrix);
    }
    public Rational calculateMinor(int row, int col) {
        Rational[][] minorMatrix = createMinorMatrix(row, col);
        return new SquareMatrix<>(minorMatrix).calculateDeterminant();
    }

    public Rational[][] createMinorMatrix(int row, int col) {
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
    public Rational calculateDeterminant() {
        if (matrix.length == 1) {
            return matrix[0][0];
        } else if (matrix.length == 2) {
            return subtract(multiply(matrix[0][0], matrix[1][1]), multiply(matrix[0][1], matrix[1][0]));
        } else {
            Rational det = new Rational(0, 1);
            for (int i = 0; i < matrix.length; i++) {
                det = add(det, multiply(new Rational((int) Math.pow(-1, i), 1), multiply(matrix[0][i], calculateMinor(0, i))));
            }
            return det;
        }
    }

}