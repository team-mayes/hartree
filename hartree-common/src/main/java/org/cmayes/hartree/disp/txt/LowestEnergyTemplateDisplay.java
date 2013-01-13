package org.cmayes.hartree.disp.txt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.cmayes.hartree.disp.Display;
import org.cmayes.hartree.model.LowestEnergyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.CommonConstants;
import com.cmayes.common.MediaType;
import com.cmayes.common.exception.EnvironmentException;
import com.cmayes.common.model.Atom;
import com.cmayes.common.util.EnvUtils;

/**
 * Writes a template with the atoms formatted for Gaussian.
 * 
 * @author cmayes
 */
public class LowestEnergyTemplateDisplay implements Display<LowestEnergyMapper> {
    private static final MediaType TYPE = MediaType.TEXT;
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private volatile boolean writeMulti = false;
    private volatile String tplName = "lowtpl";

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#finish(java.io.Writer)
     */
    @Override
    public void finish(final Writer writer) {
        // Nothing to do.
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#write(java.io.Writer,
     *      java.lang.Object)
     */
    @Override
    public void write(final Writer writer, final LowestEnergyMapper valToDisp) {
        final HashMap<String, String> valueMap = new HashMap<String, String>();
        try {
            valueMap.put("atoms", formatAtoms(valToDisp.getLowestEnergy()));
            writer.write(StrSubstitutor.replace(
                    EnvUtils.getStringFromReader(new FileReader(tplName), true),
                    valueMap));
        } catch (final FileNotFoundException e) {
            final String errMsg = "Couldn't find file " + tplName;
            logger.error(errMsg, e);
            throw new EnvironmentException(errMsg);
        } catch (final IOException e) {
            final String errMsg = "problems writing template from def in file "
                    + tplName;
            logger.error(errMsg, e);
            throw new EnvironmentException(errMsg);
        } catch (final IllegalStateException e) {
            final String errMsg = "No atoms found";
            logger.error(errMsg, e);
        }
    }

    /**
     * Formats atoms to match the Gaussian format.
     * 
     * @param atoms
     *            The atoms to write.
     * @return A Gaussian-formatted input string.
     */
    String formatAtoms(final List<Atom> atoms) {
        final StringBuilder sb = new StringBuilder();
        for (Atom atom : atoms) {
            sb.append(String.format("%s   % f   % f   % f%s", atom.getType()
                    .getSymbol(), atom.getX(), atom.getY(), atom.getZ(),
                    CommonConstants.NL));
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#getMediaType()
     */
    @Override
    public MediaType getMediaType() {
        return TYPE;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#isWriteMulti()
     */
    @Override
    public boolean isWriteMulti() {
        return writeMulti;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#setWriteMulti(boolean)
     */
    @Override
    public void setWriteMulti(final boolean writeMult) {
        this.writeMulti = writeMult;
    }
}
