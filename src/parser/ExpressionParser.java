package parser;

public class ExpressionParser {
  private final String str;
  private int pos = -1, ch;
  private final double xVal;

  public ExpressionParser(String str, double xVal) {
    this.str = str;
    this.xVal = xVal;
  }

  public static double eval(String expr, double x) {
    return new ExpressionParser(expr, x).parse();
  }

  private void nextChar() {
    ch = (++pos < str.length()) ? str.charAt(pos) : -1;
  }

  private boolean eat(int charToEat) {
    while (ch == ' ') nextChar();
    if (ch == charToEat) {
      nextChar();
      return true;
    }
    return false;
  }

  public double parse() {
    nextChar();
    double x = parseExpression();
    if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
    return x;
  }

  private double parseExpression() {
    double x = parseTerm();
    for (;;) {
      if (eat('+')) x += parseTerm();
      else if (eat('-')) x -= parseTerm();
      else return x;
    }
  }

  private double parseTerm() {
    double x = parseFactor();
    for (;;) {
      if (eat('*')) x *= parseFactor();
      else if (eat('/')) x /= parseFactor();
      else return x;
    }
  }

  private double parseFactor() {
    if (eat('+')) return parseFactor();
    if (eat('-')) return -parseFactor();

    double x;
    int startPos = this.pos;
    if (eat('(')) {
      x = parseExpression();
      eat(')');
    } else if ((ch >= '0' && ch <= '9') || ch == '.') {
      while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
      x = Double.parseDouble(str.substring(startPos, this.pos));
    } else if (ch >= 'a' && ch <= 'z') {
      while (ch >= 'a' && ch <= 'z') nextChar();
      String func = str.substring(startPos, this.pos);
      if (func.equals("x")) {
        x = xVal;
      } else {
        x = parseFactor();
        x = switch (func) {
          case "sqrt" -> Math.sqrt(x);
          case "sin" -> Math.sin(x);
          case "cos" -> Math.cos(x);
          case "tan" -> Math.tan(x);
          case "ln" -> Math.log(x);
          default -> throw new RuntimeException("Unknown function: " + func);
        };
      }
    } else {
      throw new RuntimeException("Unexpected: " + (char) ch);
    }

    if (eat('^')) x = Math.pow(x, parseFactor());
    return x;
  }
}
