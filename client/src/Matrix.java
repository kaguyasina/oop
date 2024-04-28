import java.io.Serializable;

public class Matrix<T extends Rational> implements Serializable {
    private static final long serialVersionUID = 1L;

    private T[][] matrix;

    public Matrix(T[][] matrix) {
        this.matrix = matrix;
    }

    public T[][] getMatrix() {
        return matrix;
    }
}
