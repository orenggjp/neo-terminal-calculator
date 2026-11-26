import parser.ExpressionParser;
import math.CalculusUtils;
import ui.AsciiGrapher;

public class Main {
  public static void main (String[] args) {
    String func = "sin(x) * x";

    System.out.println("Function: f(x) = " + func);
    System.out.printf("f(2)      = %.4f%n", ExpressionParser.eval(func, 2.0));
    System.out.printf("f'(2)     = %.4f%n", CalculusUtils.derivative(func, 2.0));
    System.out.printf("∫[0, 3.14]= %.4f%n", CalculusUtils.integrate(func, 0, Math.PI, 100));
    System.out.printf("Root ~3   = %.4f%n%n", CalculusUtils.findRoot(func, 3.0));

    AsciiGrapher.plot(func, -6.5, 6.5, -5.0, 5.0);
  }
}

