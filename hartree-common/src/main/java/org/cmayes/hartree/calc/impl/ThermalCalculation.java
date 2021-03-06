package org.cmayes.hartree.calc.impl;

import static com.cmayes.common.chem.PhysicalConstants.AVOGADRO;
import static com.cmayes.common.chem.PhysicalConstants.BOLTZ;
import static com.cmayes.common.chem.PhysicalConstants.GAS_KCAL;
import static com.cmayes.common.chem.PhysicalConstants.KELVIN_25C;
import static com.cmayes.common.chem.PhysicalConstants.LIGHT_CM;
import static com.cmayes.common.chem.PhysicalConstants.PLANCK;
import static com.cmayes.common.chem.PhysicalConstants.Conversions.CALTH_TO_JOULE;
import static com.cmayes.common.chem.PhysicalConstants.Conversions.HARTREE_TO_KCALTH;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.pow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cmayes.hartree.calc.Calculation;
import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.ThermalResult;
import org.cmayes.hartree.model.def.DefaultThermalResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Calculates enthalpy, entropy, and heat capacity needed for calculating Gibbs
 * free energy and kinetic parameters.
 * 
 * @author cmayes
 */
public class ThermalCalculation implements Calculation {
    // Scaling Factors //
    /** 3/2 is peppered throughout, so we just set it once here. */
    private static final double SCALE_RATIO = 3 / 2;
    /** Scaled gas constant. */
    private static final double SCALED_GAS_KCAL = SCALE_RATIO * GAS_KCAL;
    /** The temperatures (Kelvin) to use for calculations. */
    private static final List<Double> TEMPS = Arrays.asList(298.15, 300.0,
            400.0, 500.0, 600.0, 700.0, 800.0, 900.0, 1000.0, 1100.0, 1200.0,
            1300.0, 1400.0, 1500.0, 6000.0, 773.15);
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Default values are for the M052X/6-31G(2df,p) level of theory. HSF and
     * SSF are for 600K.
     */
    /** Zero-point energy. */
    private double zpe = 0.9657;
    /** Entropy. */
    private double ssf = 0.9450;
    /** Enthalpy. */
    private double hsf = 0.9440;

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.calc.impl.Calculation#calculate(T)
     */
    public Object calculate(final Object calcResult) {
        final List<ThermalResult> results = new ArrayList<ThermalResult>();
        final SymAdj symAdj = evalSym((BaseResult) calcResult);
        for (Double curTemp : TEMPS) {
            double entropyTemp = scaleEntropyTemp((BaseResult) calcResult, symAdj, curTemp);
            double heatCap = scaleHeatCap(symAdj);
            double enthalpy = scaleEnthalpyTemp(symAdj, curTemp);
            double zpeVal = 0;
            for (double curFreq : ((BaseResult) calcResult).getFrequencyValues()) {
                if (curFreq < 0) {
                    logger.debug("Skipping negative frequency " + curFreq);
                    continue;
                }

                entropyTemp += GAS_KCAL
                        * (((PLANCK * curFreq * ssf * LIGHT_CM / BOLTZ) / curTemp)
                                / (exp(PLANCK) - 1) - Math.log(1 - exp(-(PLANCK
                                * curFreq * ssf * LIGHT_CM / BOLTZ)
                                / curTemp)));
                heatCap += GAS_KCAL
                        * pow(exp(-(PLANCK * curFreq * ssf * LIGHT_CM / BOLTZ)
                                / curTemp) - 1, 2);
                enthalpy += GAS_KCAL
                        * (PLANCK * curFreq * hsf * LIGHT_CM / BOLTZ)
                        * (0 + 1 / (exp(PLANCK * curFreq * hsf * LIGHT_CM
                                / BOLTZ / curTemp) - 1));
                zpeVal += .5 * PLANCK * curFreq * LIGHT_CM * AVOGADRO
                        / CALTH_TO_JOULE / 1000 / HARTREE_TO_KCALTH * zpe;
            }
            final ThermalResult curResult = new DefaultThermalResult(curTemp);
            curResult.setEnthalpy(zpeVal
                    + ((BaseResult) calcResult).getElecEn()
                    + (GAS_KCAL * curTemp + enthalpy));
            curResult.setEntropy(entropyTemp * 1000);
            curResult.setHeatCapacity((GAS_KCAL + heatCap) * 1000);
            results.add(curResult);
        }

        return null;
    }

    /**
     * Scales the enthalpy using the SCE adjustment.
     * 
     * @param symAdj
     *            Symmetry adjustment settings.
     * @param curTemp
     *            The temperature to scale to.
     * @return The scaled enthalpy.
     */
    private Double scaleEnthalpyTemp(final SymAdj symAdj, final Double curTemp) {
        double scaledVal = SCALED_GAS_KCAL * curTemp;
        scaledVal += SCALED_GAS_KCAL * curTemp + symAdj.getSceAdj() * curTemp;
        return scaledVal;
    }

    /**
     * Scales the heat capacity using the SCE adjustment.
     * 
     * @param symAdj
     *            The symmetry adjustment.
     * @return The scaled heat capacity.
     */
    private Double scaleHeatCap(final SymAdj symAdj) {
        return SCALED_GAS_KCAL + SCALED_GAS_KCAL + symAdj.getSceAdj();
    }

    /**
     * Scales the translational value of the partition function.
     * 
     * @param transPart
     *            The translational value of the transition function.
     * @param tempK
     *            The temperature in Kelvin to scale to.
     * @return The scaled value.
     */
    private double scaleTransPart(final double transPart, final double tempK) {
        return transPart * pow(tempK / KELVIN_25C, 5 / 2);
    }

    /**
     * Scales the rotational value of the partition function.
     * 
     * @param result
     *            The result to scale.
     * @param symAdj
     *            The symmetry adjustment.
     * @param tempK
     *            The temperature in Kelvin to scale to.
     * @return The scaled value.
     */
    private double scaleRotPart(final BaseResult result, final SymAdj symAdj,
            final double tempK) {
        return result.getRotPart()
                * pow(tempK / KELVIN_25C, symAdj.getFlagAdj());
    }

    /**
     * Scales the entropy temperature.
     * 
     * @param result
     *            The result to scale.
     * @param symAdj
     *            The symmetry adjustment.
     * @param tempK
     *            The temperature in Kelvin to scale to.
     * @return The scaled value.
     */
    private double scaleEntropyTemp(final BaseResult result,
            final SymAdj symAdj, final double tempK) {
        // Translational
        double scaledVal = GAS_KCAL
                * (log(scaleTransPart(result.getTransPart(), tempK)) + 1 + SCALE_RATIO);
        // add the contribution of electronic motion to the entropy, no
        // effect on E or Cv
        scaledVal += GAS_KCAL * log(result.getMult());
        // Rotational
        scaledVal += GAS_KCAL
                * (log(scaleRotPart(result, symAdj, tempK)) + SCALE_RATIO)
                + symAdj.getSceAdj();

        return scaledVal;
    }

    /**
     * Evaluates the symmetry of the result model.
     * 
     * @param result
     *            The result to evaluate.
     * @return The adjustment settings.
     */
    private SymAdj evalSym(final BaseResult result) {
        // the SCE_adj matrix is to adjust in case there are linear molecules
        // which have a different contribution to rotational energy/Cv/S. See
        // Ochterski.
        // TODO: This does not cover every symmetry case.
        if (result.isSymmetricTop()
                && Integer.valueOf(2).equals(result.getAtomCount())) {
            return new SymAdj(1.0, -GAS_KCAL / 2);
        }
        return new SymAdj(1.5, 0);
    }

    /**
     * Wrapper for symmetry adjustment values.
     */
    private static class SymAdj {
        private final double flagAdj;
        private final double sceAdj;

        /**
         * @param flag
         *            The flag adjustment.
         * @param sce
         *            The sce adjustment.
         */
        public SymAdj(final double flag, final double sce) {
            flagAdj = flag;
            sceAdj = sce;
        }

        /**
         * @return the flagAdj
         */
        public double getFlagAdj() {
            return flagAdj;
        }

        /**
         * @return the sceAdj
         */
        public double getSceAdj() {
            return sceAdj;
        }
    }
}
