package qlearning.domain.quality;

import java.io.Serializable;
import java.util.Comparator;

import javax.annotation.Nullable;
import javax.annotation.Signed;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

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