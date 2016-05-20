/*
 * This file is part of QLearner.
 *
 * QLearner is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * QLearner is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with QLearner. If not,
 * see <http://www.gnu.org/licenses/>.
 */

package qlearning.domain.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import qlearning.client.Action;
import qlearning.client.State;

import java.io.Serializable;
import java.util.Comparator;
import java.util.PriorityQueue;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Signed;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * The learned value of a {@link State}-{@link Action} pair's potential for future {@link Reward}s.
 */
@Immutable
@ThreadSafe
public final class Quality extends Number implements Comparable<Quality> {
  private static final class ReverseOrder implements Comparator<Quality>, Serializable {
    private static final long serialVersionUID = -7048183549162821204L;

    @SuppressFBWarnings(
        value = {"NP_PARAMETER_MUST_BE_NONNULL_BUT_MARKED_AS_NULLABLE",
            "WEM_WEAK_EXCEPTION_MESSAGING"},
        justification = "We are overriding compare, which is defined as @Nullable. This is a "
            + "false positive. There is also no context to add to the null-check exception "
            + "messages.")
    @Override
    @Signed
    public int compare(@Nullable Quality o1, @Nullable Quality o2) {
      if (o1 == null) {
        throw new NullPointerException(
            "First argument to this comparator was null, which is not allowed.");
      }
      if (o2 == null) {
        throw new NullPointerException(
            "First second to this comparator was null, which is not allowed.");
      }
      return o2.compareTo(o1);
    }
  }

  private static final long serialVersionUID = -6538970599397750791L;
  private static final double EQUALITY_DELTA = 0.0000001;

  /**
   * A {@link Comparator} sorts {@link Quality} objects in reverse order from their natural
   * ordering.
   * 
   * <p>
   * When used in a {@link PriorityQueue}, then the highest quality will be at the top of the queue.
   * </p>
   */
  @Nonnull
  public static final Comparator<Quality> DESCENDING_ORDER = new ReverseOrder();

  /**
   * A {@code Quality} value of zero.
   */
  @Nonnull
  public static final Quality ZERO = new Quality(0.0);

  /**
   * The most negative possible {@code Quality} value.
   */
  @Nonnull
  public static final Quality MIN = new Quality(Double.NEGATIVE_INFINITY);

  /**
   * The most positive possible {@code Quality} value.
   */
  @Nonnull
  public static final Quality MAX = new Quality(Double.POSITIVE_INFINITY);

  @Signed
  private final double value;
  @Signed
  private final int hashCode;

  /**
   * Create a {@code Quality} object with the given value.
   * 
   * @param value the value of this quality object.
   */
  @SuppressFBWarnings(value = "WEM_WEAK_EXCEPTION_MESSAGING",
      justification = "There is no additional context to give this exception")
  public Quality(@Signed Number value) {
    double doubleValue = value.doubleValue();
    if (Double.isNaN(doubleValue)) {
      throw new IllegalArgumentException("Got a NaN for a Quality value, which is invalid.");
    }
    this.value = doubleValue;
    this.hashCode = calculateHashCode(doubleValue);
  }

  @Override
  @Nonnegative
  public int intValue() {
    return (int) value;
  }

  @Override
  @Nonnegative
  public long longValue() {
    return (long) value;
  }

  @Override
  @Nonnegative
  public float floatValue() {
    return (float) value;
  }

  @Override
  @Nonnegative
  public double doubleValue() {
    return value;
  }

  @Override
  public String toString() {
    String stringValue = Double.toString(value);
    if (stringValue == null) {
      throw new AssertionError("Double.toString returned null");
    }
    return stringValue;
  }

  @Override
  @SuppressFBWarnings(
      value = {"NP_PARAMETER_MUST_BE_NONNULL_BUT_MARKED_AS_NULLABLE",
          "WEM_WEAK_EXCEPTION_MESSAGING"},
      justification = "We are overriding compareTo, which is defined "
          + "as @Nullable. This is a false positive. Also, there "
          + "is no addition contex to add to the exception message.")
  @Signed
  public int compareTo(@Nullable Quality other) {
    if (other == null) {
      throw new NullPointerException("Tried to compare Quality to null value.");
    }
    return Double.compare(value, other.value);
  }

  @Override
  @Signed
  public int hashCode() {
    return hashCode;
  }

  @Signed
  private int calculateHashCode(double value) {
    long temp = Double.doubleToLongBits(value);
    return (int) (temp ^ (temp >>> 32));
  }

  @SuppressFBWarnings(value = "NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION",
      justification = "We are overriding equals, which is defined "
          + "as @Nullable. This is a false positive.")
  @Override
  public boolean equals(@Nullable Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj.getClass() != getClass()) {
      return false;
    }
    Quality rhs = (Quality) obj;

    return Math.abs(rhs.value - value) < EQUALITY_DELTA;
  }
}
