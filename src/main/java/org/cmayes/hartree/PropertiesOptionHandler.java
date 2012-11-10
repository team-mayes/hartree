package org.cmayes.hartree;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

public class PropertiesOptionHandler extends OptionHandler<Properties> {
    public PropertiesOptionHandler(CmdLineParser clParser, OptionDef option,
            Setter<? super Properties> setter) {
        super(clParser, option, setter);
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(params.getParameter(0)));
        } catch (IOException e) {
            throw new CmdLineException(owner, "Couldn't read properties", e);
        }
        setter.addValue(properties);
        return 1;
    }

    @Override
    public String getDefaultMetaVariable() {
        return "PROPS";
    }
}
