import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;

public class NonDecreasing {

  /**
   * Take a rectangular grid of numbers and find the length
   * of the longest sub-sequence.
   *
   * @return the length as an integer.
   */
  public static int longestSequence(int[][] grid) {
    int longest = 0;
    for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
      for (int colIndex = 0; colIndex < grid[0].length; colIndex++) {
        int currLength = longestSequence(grid, rowIndex, colIndex, -1, new ArrayList<>());
        if (currLength > longest) {
          longest = currLength;
        }
      }
    }
    return longest;
  }

  /**
   * Recursively explore every possible path from a given grid index
   * and find the length of the longest path from that index
   *
   * @return the length as an integer
   */
  public static int longestSequence(int[][] grid, int rowIndex, int colIndex, int previousValue, List<Pair<Integer, Integer>> path) {

    if (rowIndex < 0 || rowIndex >= grid.length || colIndex < 0 || colIndex >= grid[0].length) {
      return 0;
    }
    int currentValue = grid[rowIndex][colIndex];
    Pair<Integer, Integer> currentCell = new Pair<>(rowIndex, colIndex);
    if (path.contains(currentCell) || (currentValue <= previousValue + 3 && currentValue >= previousValue - 3)) {
      return 0;
    }
    path.add(currentCell);
    int pathFromHere = max(longestSequence(grid, rowIndex, colIndex + 1, currentValue, path),
        longestSequence(grid, rowIndex + 1, colIndex + 1, currentValue, path),
        longestSequence(grid, rowIndex + 1, colIndex, currentValue, path),
        longestSequence(grid, rowIndex + 1, colIndex - 1, currentValue, path),
        longestSequence(grid, rowIndex, colIndex - 1, currentValue, path),
        longestSequence(grid, rowIndex - 1, colIndex - 1, currentValue, path),
        longestSequence(grid, rowIndex - 1, colIndex, currentValue, path),
        longestSequence(grid, rowIndex - 1, colIndex + 1, currentValue, path));
    path.remove(currentCell);
    return 1 + pathFromHere;
  }

  private static int max(int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
    return Math.max(
        Math.max(
            Math.max(i, i1),
            Math.max(i2, i3)
        ),
        Math.max(
            Math.max(i4, i5),
            Math.max(i6, i7)
        )
    );
  }

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int numRows = 0;
    int numCols = 0;
    String[] firstLine = reader.readLine().split("\\s+");
    numRows = Integer.parseInt(firstLine[0]);
    numCols = Integer.parseInt(firstLine[1]);

    int[][] grid = new int[numRows][numCols];

    for (int row = 0; row < numRows; row++) {
      String[] inputRow = reader.readLine().split("\\s+");

      for (int col = 0; col < numCols; col++) {
        grid[row][col] = Integer.parseInt(inputRow[col]);
      }
    }
    int length = longestSequence(grid);
    System.out.println(length);
  }
}