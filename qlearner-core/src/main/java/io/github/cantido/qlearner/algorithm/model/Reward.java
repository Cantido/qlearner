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

package io.github.cantido.qlearner.algorithm.model;

import javax.annotation.Signed;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * The desirability of a certain state.
 * 
 * <p>
 * The q-learning algorithm as a whole is about maximizing reward. Agents will learn to take actions
 * that will lead them to states with higher reward values, and they will learn to avoid states with
 * lower reward values.
 * </p>
 * 
 * <p>
 * Agents only care about the relative position of reward values, so values can be as far or as
 * close to zero as you see fit. Values that are dramatically greater than others will be sought out
 * much, much more.
 * </p>
 * 
 * <p>
 * An agent's "greediness" or "far-sightedness" is controlled by the {@link DiscountFactor} value.
 * An agent's memory of rewards is controlled by its {@link LearningRate} value.
 * </p>
 */
@Immutable
@ThreadSafe
public final class Reward extends Number {
  private static final long serialVersionUID = 8968547494739709536L;
  private final double value;

  /**
   * Create a {@code Reward} with the given value. Higher values are more desirable to an agent.
   * 
   * @param value the value of this reward.
   */
  public Reward(Number value) {
    this.value = value.doubleValue();
  }

  @Override
  @Signed
  public int intValue() {
    return (int) value;
  }

  @Override
  @Signed
  public long longValue() {
    return (long) value;
  }

  @Override
  @Signed
  public float floatValue() {
    return (float) value;
  }

  @Override
  @Signed
  public double doubleValue() {
    return value;
  }

  @Override
  public int hashCode() {
    long temp = Double.doubleToLongBits(value);
    return 31 * 1 + (int) (temp ^ (temp >>> 32));
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

    Reward other = (Reward) obj;
    return Double.valueOf(value).equals(other.value);
  }

  @Override
  public String toString() {
    return "Reward[" + value + "]";
  }
}
