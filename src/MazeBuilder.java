import java.util.Random;

import model.IMaze;
import model.Maze;

/**
 * A builder for a maze object, used by the main function to build a maze object on the fly.
 */
public class MazeBuilder {
  private int rows;
  private int cols;
  private int wallsRemaining;
  private boolean isWrapping;
  private int sRow;
  private int sCol;
  private int gRow;
  private int gCol;
  private long seed;

  /**
   * Creates the builder object with default parameters.
   */
  public MazeBuilder() {
    this.rows = 3;
    this.cols = 3;
    this.wallsRemaining = 0;
    this.isWrapping = false;
    this.sRow = 0;
    this.sCol = 0;
    this.gRow = 1;
    this.gCol = 1;
    this.seed = new Random().nextLong();
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

  public IMaze build() {
    return new Maze(this.rows, this.cols, this.wallsRemaining,
            this.isWrapping, this.sRow, this.sCol, this.gRow, this.gCol, this.seed);
  }
}
