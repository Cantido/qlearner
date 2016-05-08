/*
 * This file is part of QLearner.
 *
 * QLearner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QLearner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QLearner.  If not, see <http://www.gnu.org/licenses/>.
 */

package qlearning.domain.learning;

import javax.annotation.Nonnegative;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

@Immutable
@ThreadSafe
public final class Reward extends Number {
	private static final long serialVersionUID = 8968547494739709536L;
	private final double value;
    
    public Reward(double value) {
        this.value = value;
    }

    @Override
    @Nonnegative
    public int intValue() {
        return Double.valueOf(value).intValue();
    }

    @Override
    @Nonnegative
    public long longValue() {
        return Double.valueOf(value).longValue();
    }

    @Override
    @Nonnegative
    public float floatValue() {
        return Double.valueOf(value).floatValue();
    }

    @Override
    @Nonnegative
    public double doubleValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Reward["+value+"]";
    }
}
