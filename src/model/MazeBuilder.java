package model;

import java.util.Random;


/**
 * A builder for a maze object, used by the main function to build a maze object on the fly.
 */
public class MazeBuilder {
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
  }


  public void setRows(int rows) {
    this.rows = rows;
  }

  public void setCols(int cols) {
    this.cols = cols;
  }

  public void setWallsRemaining(int wallsRemaining) {
    this.wallsRemaining = wallsRemaining;
  }

  public void setWrapping(boolean wrapping) {
    isWrapping = wrapping;
  }

  public void setPerfect(boolean perfect) {
    this.perfect = perfect;
  }

  public void setsRow(int sRow) {
    this.sRow = sRow;
  }

  public void setsCol(int sCol) {
    this.sCol = sCol;
  }

  public void setgRow(int gRow) {
    this.gRow = gRow;
  }

  public void setgCol(int gCol) {
    this.gCol = gCol;
  }

  public void setSeed(long seed) {
    this.seed = seed;
  }

  public void setBatsPercentage(int batsPercentage) {
    this.batsPercentage = batsPercentage;
  }

  public void setPitsPercentage(int pitsPercentage) {
    this.pitsPercentage = pitsPercentage;
  }

  public void setArrowCount(int arrowCount) {
    this.arrowCount = arrowCount;
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
              pitsPercentage, this.seed, this.arrowCount);
    } else {
      return new Maze(this.rows, this.cols, this.wallsRemaining,
              this.isWrapping, this.sRow, this.sCol, this.gRow, this.gCol, batsPercentage,
              pitsPercentage, this.seed, this.arrowCount);
    }

  }
}
