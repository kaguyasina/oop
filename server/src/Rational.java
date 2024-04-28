import java.io.Serializable;

public class Rational extends Number implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numerator;
    private int denominator;
    private int realPart;
    private int imaginaryPart;

    // Constructor for Real numbers
    public Rational(int realPart) {
        this.numerator = realPart;
        this.denominator = 1;
        this.realPart = realPart;
        this.imaginaryPart = 0;
    }

    // Constructor for Complex numbers
    public Rational(int realPart, int imaginaryPart) {
        this.numerator = realPart;
        this.denominator = 1;
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }


    // Constructor for Rational numbers without a specified real part
    public Rational(int numerator, int denominator, int realPart) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.realPart = numerator;
        this.imaginaryPart = 0;
    }



    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public int getRealPart() {
        return realPart;
    }

    public int getImaginaryPart() {
        return imaginaryPart;
    }

    @Override
    public int intValue() {
        return realPart / denominator;
    }

    @Override
    public long longValue() {
        return (long) intValue();
    }

    @Override
    public float floatValue() {
        return (float) realPart / denominator;
    }

    @Override
    public double doubleValue() {
        return (double) realPart / denominator;
    }

    @Override
    public String toString() {
        String realPartStr = realPart + "/" + denominator;
        String imaginaryPartStr = "";
        if (imaginaryPart != 0) {
            imaginaryPartStr = (imaginaryPart > 0 ? "+" : "-") + Math.abs(imaginaryPart) + "i";
        }
        return numerator + "(" + realPartStr + imaginaryPartStr + ")";
    }
}
