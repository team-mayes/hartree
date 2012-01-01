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

import com.google.common.base.Strings;

/**
 * Writes the normal mode data to the given writer.
 * 
 * @author cmayes
 */
public class NormalModeTextDisplay implements Display<NormalModeCalculation> {
    public static final String HEADSEP = Strings.repeat("-", 10);

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#write(java.io.Writer,
     *      java.lang.Object)
     */
    public void write(final Writer writer, final NormalModeCalculation calc) {
        write(writer, null, calc);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#write(java.io.Writer,
     *      java.lang.Object)
     */
    @Override
    public void write(final Writer writer, final String headerInfo,
            final NormalModeCalculation calc) {
        final PrintWriter printWriter = new PrintWriter(writer);
        if (headerInfo != null) {
            printWriter.println(headerInfo);
            printWriter.println();
        }

        printWriter.println("Highest DoF percentages by dihedral (pair: mode# (value)):");

        final NormalModeReport normReport = calc.generateReport();

        for (Map.Entry<DihedralPair, NormalMode> hiEntry : normReport
                .findHighestDihedrals().entrySet()) {
            final DihedralPair dhPair = hiEntry.getKey();
            final NormalMode topMode = hiEntry.getValue();
            printWriter.printf("        (%3d, %3d) :%3d (%.2f)%s",
                    dhPair.getLower(), dhPair.getHigher(), calc
                            .getNormalModes().indexOf(topMode) + 1, normReport
                            .getSummaries().get(topMode)
                            .getDihedralPairWeights().get(dhPair), NL);
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
                printWriter.printf("         (%3d, %3d): %.2f%s", key.getLower(),
                        key.getHigher(), dhPair.getValue(), NL);
            }
            i++;
        }
        printWriter.flush();
    }
}
