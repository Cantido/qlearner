package qlearning;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class AgentTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Agent agent;

    @Before
    public void setUp() {
        agent = new Agent();
    }

    @Test
    public void testNullEnvironmentIsIllegal() {
        exception.expect(NullPointerException.class);
        agent.setEnvironment(null);
    }
}
