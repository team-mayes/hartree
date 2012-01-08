package org.cmayes.hartree;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Tests for {@link HandlingType}.
 * 
 * @author cmayes
 */
public class TestHandlingType {
    /**
     * Tests that an unknown command throws an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValueOfCommandBad() {
        HandlingType.valueOfCommand("BAD_CMD_NAME");
    }

    /**
     * Tests the command name contents.
     */
    @Test
    public void testCommandName() {
        assertThat(HandlingType.NORMAL_MODE.getCommandName(), equalTo("norm"));
    }

    /**
     * Tests the command name contents.
     */
    @Test
    public void testDesc() {
        assertThat(HandlingType.NORMAL_MODE.getDescription(),
                containsString("normal mode"));
    }

    /**
     * Tests valueOf.
     */
    @Test
    public void testValueOf() {
        assertThat(HandlingType.valueOf(HandlingType.THERM.name()),
                equalTo(HandlingType.THERM));
    }
}
