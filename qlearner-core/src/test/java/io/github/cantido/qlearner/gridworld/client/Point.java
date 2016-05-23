package io.github.cantido.qlearner.gridworld.client;

import javax.annotation.Nonnegative;
import javax.annotation.Nullable;

/**
 * Classic!.
 */
public class Point {
  @Nonnegative public final int horizontalIndex;
  @Nonnegative public final int verticalIndex;

  /**
   * Create a new point.
   * @param horizontalIndex the horizontal position, must be greater than 0
   * @param verticalIndex the vertical position, must be greater than 0
   */
  public Point(int horizontalIndex, int verticalIndex) {
    assertValidIndices(horizontalIndex, verticalIndex);

    this.horizontalIndex = horizontalIndex;
    this.verticalIndex = verticalIndex;
  }

  private void assertValidIndices(int horizontalIndex, int verticalIndex) {
    if (horizontalIndex < 0) {
      throw new IllegalArgumentException("Cannot create a point with a negative horizontal"
          + " index, but got " + horizontalIndex);
    }
    if (verticalIndex < 0) {
      throw new IllegalArgumentException(
          "Cannot create a point with a negative vertical" + " index, but got " + verticalIndex);
    }
  }

  /**
   * Returns a new point with a position of one space to the right.
   * 
   * @return a new point
   */
  public Point movedRight() {
    return new Point(horizontalIndex + 1, verticalIndex);
  }

  /**
   * Returns a new point with a position of one space to the left.
   * 
   * @return a new point
   */
  public Point movedLeft() {
    return new Point(horizontalIndex - 1, verticalIndex);
  }

  /**
   * Returns a new point with a position of one space upward.
   * 
   * @return a new point
   */
  public Point movedUp() {
    return new Point(horizontalIndex, verticalIndex + 1);
  }

  /**
   * Returns a new point with a position of one space below.
   * 
   * @return a new point
   */
  public Point movedDown() {
    return new Point(horizontalIndex, verticalIndex - 1);
  }

  public boolean isLeftOf(Point other) {
    return this.horizontalIndex < other.horizontalIndex;
  }

  public boolean isRightOf(Point other) {
    return this.horizontalIndex > other.horizontalIndex;
  }

  public boolean isAbove(Point other) {
    return this.verticalIndex > other.verticalIndex;
  }

  public boolean isBelow(Point other) {
    return this.verticalIndex < other.verticalIndex;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + horizontalIndex;
    result = prime * result + verticalIndex;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Point other = (Point) obj;
    
    return  (horizontalIndex == other.horizontalIndex)
        && (verticalIndex == other.verticalIndex);

  }

}
