package qlearning;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import qlearning.domain.DiscountFactor;
import qlearning.domain.LearningRate;


public class AgentTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock private Environment environment;
    @Mock private ExplorationStrategy explorationStrategy;
    @Mock private LearningRate learningRate;
    @Mock private DiscountFactor discountFactor;
    @Mock private QualityMap qualityMap;

    @Test
    public void testNullEnvironmentIsIllegal() {
        exception.expect(NullPointerException.class);
        
        new Agent((Environment) null, explorationStrategy, learningRate, discountFactor, qualityMap);
    }
}
