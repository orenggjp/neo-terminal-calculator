package ui;

import parser.ExpressionParser;

public class AsciiGrapher {
  public static void plot(String expr, double xMin, double xMax, double yMin, double yMax) {
    int width = 60;
    int height = 20;
    char[][] grid = new char [height][width];

    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
      grid[r][c] = ' ';
      }
    }

    // draws axes
    int xAxisRow = (int) Math.round((yMax / (yMax - yMin)) * (height - 1));
    int yAxisCol = (int) Math.round((-xMin / (xMax - xMin)) * (width - 1));

    if (xAxisRow >= 0 && xAxisRow < height) {
        for (int c = 0; c < width; c++) grid[xAxisRow][c] = '-';
      }
    
    if (yAxisCol >= 0 && yAxisCol < width) {
        for (int r = 0; r < height; r++) grid[r][yAxisCol] = '|';
      }

    if (xAxisRow >= 0 && xAxisRow < height && yAxisCol >= 0 && yAxisCol < width) {
        grid[xAxisRow][yAxisCol] = '+';
      }

    // plot points
    for (int c = 0; c < width; c++) {
      double x = xMin + (c / (double) (width - 1)) * (xMax - xMin);
      double y = ExpressionParser.eval(expr, x);
      int r = (int) Math.round(((yMax - y) / (yMax - yMin)) * (height - 1));
      if (r >= 0 && r < height) {
        grid[r][c] = '*';
      }
    }

    for (char[] row : grid) {
      System.out.println(new String(row));
    }
  }
}
