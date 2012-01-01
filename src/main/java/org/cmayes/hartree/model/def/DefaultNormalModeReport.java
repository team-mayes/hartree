package org.cmayes.hartree.model.def;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.util.Map;
import java.util.TreeMap;

import org.cmayes.hartree.model.DihedralPair;
import org.cmayes.hartree.model.NormalMode;
import org.cmayes.hartree.model.NormalModeReport;
import org.cmayes.hartree.model.NormalModeSummary;

/**
 * Creates a normal mode report using the given modes and summaries.
 * 
 * @author cmayes
 */
public class DefaultNormalModeReport implements NormalModeReport {
    private final Map<NormalMode, NormalModeSummary> summaries;

    /**
     * Creates a report using the given modes and summaries.
     * 
     * @param sums
     *            The summaries to report upon.
     */
    public DefaultNormalModeReport(final Map<NormalMode, NormalModeSummary> sums) {
        this.summaries = asNotNull(sums, "Summary map is null");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.cmayes.hartree.model.NormalModeReport#findHighestDihedrals()
     */
    @Override
    public Map<DihedralPair, NormalMode> findHighestDihedrals() {
        final TreeMap<DihedralPair, NormalMode> hiMap = new TreeMap<DihedralPair, NormalMode>();
        for (Map.Entry<NormalMode, NormalModeSummary> sumEntry : summaries
                .entrySet()) {
            for (Map.Entry<DihedralPair, Double> weights : sumEntry.getValue()
                    .getDihedralPairWeights().entrySet()) {
                final DihedralPair curPair = weights.getKey();
                final NormalMode existingMode = hiMap.get(curPair);
                if (existingMode == null) {
                    hiMap.put(curPair, sumEntry.getKey());
                    continue;
                }
                final NormalModeSummary existingSum = summaries
                        .get(existingMode);
                if (weights.getValue() > existingSum.getDihedralPairWeights()
                        .get(curPair)) {
                    hiMap.put(curPair, sumEntry.getKey());
                }
            }
        }
        return hiMap;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.cmayes.hartree.model.NormalModeReport#getSummaries()
     */
    @Override
    public Map<NormalMode, NormalModeSummary> getSummaries() {
        return summaries;
    }
}
