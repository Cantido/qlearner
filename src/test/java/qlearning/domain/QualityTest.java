/**
 * This file is part of qlearner
 *
 *  Qlearner is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Qlearner is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Qlearner.  If not, see <http://www.gnu.org/licenses/>.
 */

package qlearning.domain;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import qlearning.quality.Quality;

public class QualityTest {

    Quality lower = new Quality(0.0);
    Quality higher = new Quality(1.0);

    @Test
    public void compareLessThan() {
        assertThat(lower.compareTo(higher), lessThan(0));
    }

    @Test
    public void compareHigherThan() {
        assertThat(higher.compareTo(lower), greaterThan(0));
    }
    
    @Test
    public void compareEquals() {
        Quality first = new Quality(0.0);
        Quality second = new Quality(0.0);
        assertThat(first.compareTo(second), is(0));
    }
}
