package org.cmayes.hartree.disp.txt;

import static com.cmayes.common.CommonConstants.NL;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

import org.cmayes.hartree.disp.Display;
import org.cmayes.hartree.model.DihedralPair;
import org.cmayes.hartree.model.NormalMode;
import org.cmayes.hartree.model.NormalModeCalculation;
import org.cmayes.hartree.model.NormalModeReport;
import org.cmayes.hartree.model.NormalModeSummary;

import com.cmayes.common.CommonConstants;
import com.cmayes.common.MediaType;
import com.google.common.base.Strings;

/**
 * Writes the normal mode data to the given writer.
 * 
 * @author cmayes
 */
public class NormalModeTextDisplay implements Display<NormalModeCalculation> {
    private static final MediaType TYPE = MediaType.TEXT;
    private volatile boolean writeMulti = false;

    /**
     * {@inheritDoc}
     *
     * @see org.cmayes.hartree.disp.Display#isWriteMulti()
     */
    @Override
    public boolean isWriteMulti() {
        return this.writeMulti;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.cmayes.hartree.disp.Display#setWriteMulti(boolean)
     */
    @Override
    public void setWriteMulti(final boolean wMulti) {
        this.writeMulti = wMulti;
    }

    /**
     * Returns the media type of the generated display.
     * 
     * @return The media type of the generated display.
     */
    @Override
    public MediaType getMediaType() {
        return TYPE;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#write(java.io.Writer,
     *      java.lang.Object)
     */
    @Override
    public void write(final Writer writer, final NormalModeCalculation calc) {
        final PrintWriter printWriter = new PrintWriter(writer);
        printWriter.printf("Normal mode summary for file %s%s",
                calc.getSourceName(), CommonConstants.NL);
        printWriter.println();

        if (calc.getNormalModes().size() == 0) {
            printWriter.println("No normal mode data found.");
            printWriter.flush();
            return;
        }

        printWriter.println("Highest DoF percentages by dihedral:");
        printWriter.println("    pair   | mode |   %   | freq ");
        printWriter.println(Strings.repeat("-", 34));

        final NormalModeReport normReport = calc.generateReport();

        for (Map.Entry<DihedralPair, NormalMode> hiEntry : normReport
                .findHighestDihedrals().entrySet()) {
            final DihedralPair dhPair = hiEntry.getKey();
            final NormalMode topMode = hiEntry.getValue();
            final int modeIdx = calc.getNormalModes().indexOf(topMode);
            final double pairPercent = normReport.getSummaries().get(topMode)
                    .getDihedralPairWeights().get(dhPair);
            printWriter.printf("(%3d, %3d) | %3d  | %5.2f | %.2f%s",
                    dhPair.getLower(), dhPair.getHigher(), modeIdx + 1,
                    pairPercent, calc.getFrequencyValues().get(modeIdx), NL);
        }

        int i = 1;
        for (Map.Entry<NormalMode, NormalModeSummary> entries : normReport
                .getSummaries().entrySet()) {
            final NormalModeSummary sum = entries.getValue();
            printWriter.printf("%s=== Normal mode %3d ===%s", NL, i, NL);
            printWriter.printf("Frequency          : %.2f%s", calc
                    .getFrequencyValues().get(i - 1), NL);
            printWriter.printf("Angle Bending   (A): %.2f%s",
                    sum.getAngleBendingWeight(), NL);
            printWriter.printf("Bond Stretching (R): %.2f%s",
                    sum.getBondStretchingWeight(), NL);
            printWriter.println("Dihedral pairs  (D):");
            for (Map.Entry<DihedralPair, Double> dhPair : sum
                    .getDihedralPairWeights().entrySet()) {
                final DihedralPair key = dhPair.getKey();
                printWriter.printf("         (%3d, %3d): %.2f%s",
                        key.getLower(), key.getHigher(), dhPair.getValue(), NL);
            }
            i++;
        }
        printWriter.flush();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#finish(Writer)
     */
    @Override
    public void finish(final Writer writer) {
        // This display is stateless
    }
}
