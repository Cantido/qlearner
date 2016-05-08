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

package qlearning.domain.quality;

import java.io.Serializable;
import java.util.Comparator;

import javax.annotation.Nullable;
import javax.annotation.Signed;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@Immutable
@ThreadSafe
/* package private */ class ReverseOrder implements Comparator<Quality>, Serializable {
	private static final long serialVersionUID = -7048183549162821204L;

    @SuppressFBWarnings(
    		value = "NP_PARAMETER_MUST_BE_NONNULL_BUT_MARKED_AS_NULLABLE",
    		justification = "We are overriding compare, which is defined " +
    						"as @Nullable. This is a false positive.")
	@Override
    @Signed public int compare(@Nullable Quality o1, @Nullable Quality o2) {
		if(o1 == null) { throw new NullPointerException("First argument to this comparator was null."); }
		if(o2 == null) { throw new NullPointerException("First second to this comparator was null."); }
        return o2.compareTo(o1);
    }
}