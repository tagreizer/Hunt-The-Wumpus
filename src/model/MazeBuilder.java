package model;

import java.util.Random;


/**
 * A builder for a maze object, used by the main function to build a maze object on the fly.
 */
public final class MazeBuilder {
  private int rows;
  private int cols;
  private int wallsRemaining;
  private boolean perfect;
  private boolean isWrapping;
  private int sRow;
  private int sCol;
  private int gRow;
  private int gCol;
  private int batsPercentage;
  private int pitsPercentage;
  private long seed;
  private int arrowCount;
  private int playerCount;


  /**
   * Creates the builder object with default parameters.
   */
  public MazeBuilder() {
    this.rows = 3;
    this.cols = 3;
    this.wallsRemaining = 0;
    this.isWrapping = false;
    this.perfect = false;
    this.sRow = 0;
    this.sCol = 0;
    this.gRow = 1;
    this.gCol = 1;
    this.batsPercentage = 20;
    this.pitsPercentage = 10;
    this.seed = new Random().nextLong();
    this.arrowCount = 2;
    this.playerCount = 1;
  }

  /**
   * Sets the row.
   *
   * @param rows the row
   */
  public void setRows(int rows) {
    this.rows = rows;
  }

  /**
   * Sets the col.
   *
   * @param cols the col
   */
  public void setCols(int cols) {
    this.cols = cols;
  }

  /**
   * Randomizes the row from 2 to 15.
   */
  public void randomizeRows() {
    this.rows = new Random().nextInt(14) + 2;
  }

  /**
   * Randomizes the cols from 2 to 15.
   */
  public void randomizeCols() {
    this.cols = new Random().nextInt(14) + 2;
  }

  /**
   * Sets how many walls remaining.
   *
   * @param wallsRemaining walls remaining.
   */
  public void setWallsRemaining(int wallsRemaining) {
    this.wallsRemaining = wallsRemaining;
  }

  /**
   * Sets if it should wrap.
   *
   * @param wrapping if it wraps.
   */
  public void setWrapping(boolean wrapping) {
    isWrapping = wrapping;
  }

  /**
   * Sets if its a perfect maze.
   *
   * @param perfect if its perfect.
   */
  public void setPerfect(boolean perfect) {
    this.perfect = perfect;
  }

  /**
   * Sets the starting row.
   *
   * @param sRow the starting row
   */
  public void setsRow(int sRow) {
    this.sRow = sRow;
  }

  /**
   * Sets the starting col.
   *
   * @param sCol the starting col
   */
  public void setsCol(int sCol) {
    this.sCol = sCol;
  }

  /**
   * Sets the goal row.
   *
   * @param gRow the goal row
   */
  public void setgRow(int gRow) {
    this.gRow = gRow;
  }

  /**
   * Sets the goal col.
   *
   * @param gCol the goal col
   */
  public void setgCol(int gCol) {
    this.gCol = gCol;
  }

  /**
   * Randomizes the starting row. Should be called after setting the row, or a valid start is not
   * guaranteed.
   */
  public void randomizeSRow() {
    this.sRow = new Random().nextInt(this.rows);
  }

  /**
   * Randomizes the starting col. Should be called after setting the col, or a valid start is not
   * guaranteed.
   */
  public void randomizeSCol() {
    this.sCol = new Random().nextInt(this.cols);

  }

  /**
   * Randomizes the goal row. Should be called after setting the starting row, or a valid start is
   * not * guaranteed.
   */
  public void randomizeGRow() {
    if (rows == 1) {
      this.gRow = 0;
      return;
    }

    do {
      this.gRow = new Random().nextInt(this.rows);
    }
    while (this.gRow == sRow);

  }

  /**
   * Randomizes the goal column. Should be called after setting the starting col, or a valid start
   * is not guaranteed.
   */
  public void randomizeGCol() {
    if (cols == 1) {
      this.gCol = 0;
      return;
    }
    do {
      this.gCol = new Random().nextInt(this.cols);
    }
    while (this.gCol == sCol);
  }

  /**
   * Sets the seed.
   *
   * @param seed the seed
   */
  public void setSeed(long seed) {
    this.seed = seed;
  }

  /**
   * Sets the bats percentage.
   *
   * @param batsPercentage the bats percentage
   */
  public void setBatsPercentage(int batsPercentage) {
    this.batsPercentage = batsPercentage;
  }

  /**
   * Sets the pits percentage.
   *
   * @param pitsPercentage the pits percentage
   */
  public void setPitsPercentage(int pitsPercentage) {
    this.pitsPercentage = pitsPercentage;
  }

  /**
   * Randomizes the seed.
   */
  public void randomizeSeed() {
    this.seed = new Random().nextLong();
  }

  /**
   * Randomizes the bats percentage between 0 and 30%.
   */
  public void randomizeBatsPercentage() {
    this.batsPercentage = new Random().nextInt(30);
  }

  /**
   * Randomizes the pits percentage between 0 and 30%.
   */
  public void randomizePitsPercentage() {
    this.pitsPercentage = new Random().nextInt(30);
  }

  /**
   * Sets the arrow count for players.
   *
   * @param arrowCount the arrow count.
   */
  public void setArrowCount(int arrowCount) {
    this.arrowCount = arrowCount;
  }

  /**
   * Sets the player amount.
   *
   * @param playerCount the amount of players.
   */
  public void setPlayerCount(int playerCount) {
    this.playerCount = playerCount;
  }

  /**
   * Builds the maze object based off of the mazebuilder's specifications.
   *
   * @return the maze object
   */
  public IMaze build() {
    if (perfect) {
      return new Maze(this.rows, this.cols,
              this.isWrapping, this.sRow, this.sCol, this.gRow, this.gCol, batsPercentage,
              pitsPercentage, this.seed, this.arrowCount, playerCount);
    } else {
      return new Maze(this.rows, this.cols, this.wallsRemaining,
              this.isWrapping, this.sRow, this.sCol, this.gRow, this.gCol, batsPercentage,
              pitsPercentage, this.seed, this.arrowCount, this.playerCount);
    }

  }
}
