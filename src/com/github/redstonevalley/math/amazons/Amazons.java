package com.github.redstonevalley.math.amazons;

import java.util.Arrays;

/** Adapted from
 * http://www.java.achchuthan.org/2012/02/n-queens-problem-in-java.html
 * 
 * Solves a variant of the N-queens puzzle for amazons (pieces that move as a
 * queen or knight), on an M-by-M board.
 * 
 * @author cryoc */
public class Amazons {

  int[] rowCoord;
  int[] colCoord;
  int m;
  int offset;

  public Amazons(int N, int M, int offset) {
    rowCoord = new int[N];
    colCoord = new int[N];
    for (int i = 0; i < N; i++) {
      rowCoord[i] = -1;
      colCoord[i] = -1;
    }
    m = M;
    this.offset = offset;
  }

  public boolean canPlaceQueen(int r, int c, int placed) {
    /** Returns TRUE if a queen can be placed in row r and column c.
     * Otherwise it returns FALSE. x[] is a global array whose first {@code placed}
     * values have been set. */
    for (int i = 0; i < placed; i++) {
      int rowsApart = Math.abs(rowCoord[i] - r);
      int colsApart = Math.abs(colCoord[i] - c);
      if (rowsApart == 0
          || colsApart == 0
          || rowsApart == colsApart
          || (rowsApart == 2 && colsApart == 1)
          || (rowsApart == 1 && colsApart == 2)) {
        return false;
      }
    }
    return true;
  }

  public void printQueens() {
    char[][] queens = new char[m][m];
    for (int i = 0; i < m; i++) {
      Arrays.fill(queens[i], '*');
    }
    int N = rowCoord.length;
    for (int i = 0; i < N; i++) {
      if (rowCoord[i] >= 0) {
        queens[rowCoord[i]][colCoord[i]] = 'Q';
      }
    }
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < m; j++) {
        System.out.format("%c ", queens[i][j]);
      }
      System.out.println();
    }
  }

  public int placeNqueens(int r, int c, int n, int alreadyPlaced) {
    /** Using backtracking this method prints all possible placements of n
     * amazons on an m x m chessboard so that they are non-attacking. */
    while (true) { // this while loop was used to manually eliminate tail calls
      if (canPlaceQueen(r, c, alreadyPlaced)) {
        rowCoord[alreadyPlaced] = r;
        colCoord[alreadyPlaced] = c;
        alreadyPlaced++;
        c++;
        r = 0;
        if (alreadyPlaced == n) {
          return n;
        }
      } else {
        r++;
        if (r >= m) {
          c++;
          r = 0;
        }
      }
      if (c >= m) {
        return alreadyPlaced;
      }
    }
  }

  public int callplaceNqueens() {
    return placeNqueens(offset, 0, rowCoord.length, 0);
  }

  public static void main(String args[]) {
    int bestValue = 0;
    int bestOffset = -1;
    for (int N = 1; N <= 1000; N++) {
      Amazons[] solutions = new Amazons[N];
      for (int i = 0; i < N; i++) {
        solutions[i] = new Amazons(N, N, i);
        int numPlaced = solutions[i].callplaceNqueens();
        if (numPlaced > bestValue) {
          bestValue = numPlaced;
          bestOffset = i;
        }
      }
      //solutions[bestOffset].printQueens();
      //System.out.format("%d amazons placed on %dx%d board\n\n", bestValue, N, N);
      System.out.format("%d,", bestValue);
    }
  }
}
