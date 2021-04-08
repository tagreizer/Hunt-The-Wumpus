package model;

/**
 * Represents a position in 2D space.
 */
public class Position {
  private final int row;
  private final int col;

  /**
   * Creates a position.
   *
   * @param row the row Loc
   * @param col the col Loc
   */
  public Position(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Gets the row location.
   *
   * @return the row location.
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets the col location.
   *
   * @return the col location.
   */
  public int getCol() {
    return col;
  }

  /**
   * Returns the whole position.
   *
   * @return the whole position.
   */
  public Position getPosition() {
    return new Position(this.row, this.col);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Position) {
      Position otherPos = (Position) other;

      return otherPos.getRow() == this.row && otherPos.getCol() == this.col;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(this.row) + Integer.hashCode(this.col);
  }
}
