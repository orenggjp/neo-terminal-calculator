package math;

import parser.ExpressionParser;

public class CalculusUtils {
    private static final double H = 1e-5;

    // Central difference approximation for f'(x)
    public static double derivative(String expr, double x) {
        return (ExpressionParser.eval(expr, x + H) - ExpressionParser.eval(expr, x - H)) / (2 * H);
    }

    // Simpson's Rule for definite integral from a to b
    public static double integrate(String expr, double a, double b, int n) {
        if (n % 2 != 0) n++; // n must be even
        double h = (b - a) / n;
        double sum = ExpressionParser.eval(expr, a) + ExpressionParser.eval(expr, b);

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            sum += (i % 2 == 0 ? 2 : 4) * ExpressionParser.eval(expr, x);
        }
        return (h / 3.0) * sum;
    }

    // Newton's Method to find a root near initialGuess
    public static double findRoot(String expr, double initialGuess) {
        double x = initialGuess;
        for (int i = 0; i < 100; i++) {
            double y = ExpressionParser.eval(expr, x);
            if (Math.abs(y) < 1e-7) return x;
            double dy = derivative(expr, x);
            if (Math.abs(dy) < 1e-10) break;
            x = x - y / dy;
        }
        return x;
    }
}

