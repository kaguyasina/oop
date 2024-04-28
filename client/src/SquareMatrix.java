public class SquareMatrix<T extends Rational> extends Matrix<T> {
    private static final long serialVersionUID = 1783879263198769307L; // Замените на актуальное значение serialVersionUID

    public SquareMatrix(T[][] matrix) {
        super(matrix);
    }
}