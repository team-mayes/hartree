package org.cmayes.hartree;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import org.cmayes.hartree.model.NormalModeCalculation;
import org.cmayes.hartree.model.def.DefaultBaseResult;

/**
 * The type of handling to perform.
 * 
 * @author cmayes
 */
public enum HandlingType {
    NORMAL_MODE("norm", "Handles normal mode evaluation",
            NormalModeCalculation.class), SNAPSHOT("snap",
            "Provides a snapshot of calculation data", DefaultBaseResult.class), CPSNAPSHOT(
            "cpsnap",
            "Provides a snapshot of calculation data including Cremer-Pople coordinates",
            DefaultBaseResult.class), TEST("test",
            "Test mode (no handling performed)", Object.class), THERM("therm",
            "Handles thermo calculations", DefaultBaseResult.class);

    private final String commandName;
    private final String description;
    private final Class<?> valueType;

    /**
     * Create the handling type.
     * 
     * @param cmdName
     *            The name of the command to use (lower case).
     * @param desc
     *            The description of the handling type.
     * @param valType
     *            The class type that will contain the processed value.
     */
    private HandlingType(final String cmdName, final String desc,
            final Class<?> valType) {
        this.commandName = cmdName.toLowerCase();
        this.description = desc;
        this.valueType = valType;
    }

    /**
     * @return the commandName
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Look up an element by command name.
     * 
     * @param cmdName
     *            The name of the command.
     * @return The type with the given command name.
     * @throws IllegalArgumentException
     *             If no elements match the given symbol.
     */
    public static HandlingType valueOfCommand(final String cmdName) {
        for (HandlingType val : HandlingType.values()) {
            if (val.getCommandName().equals(
                    asNotNull(cmdName, "Symbol is null").toLowerCase())) {
                return val;
            }
        }
        throw new IllegalArgumentException("No handling type with name "
                + cmdName);
    }

    /**
     * @return the valueType
     */
    public Class<?> getValueClass() {
        return valueType;
    }
}
