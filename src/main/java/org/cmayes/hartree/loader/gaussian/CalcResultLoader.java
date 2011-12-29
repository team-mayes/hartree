package org.cmayes.hartree.loader.gaussian;

import java.io.Reader;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.cmayes.hartree.loader.Loader;
import org.cmayes.hartree.model.Atom;
import org.cmayes.hartree.model.CalculationResult;
import org.cmayes.hartree.model.def.DefaultAtom;
import org.cmayes.hartree.model.def.DefaultCalculationResult;
import org.cmayes.hartree.parser.gaussian.antlr.Gaussian09Lexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fills a CalculationResult from data parsed from the given reader.
 * 
 * @author cmayes
 */
public class CalcResultLoader extends BaseGaussianLoader implements
        Loader<CalculationResult> {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.loader.Loader#load(java.io.Reader)
     */
    public CalculationResult load(final Reader reader) {
        return extractCalcThermData(extractAst(reader));
    }

    /**
     * Fills a {@link CalculationResult} instance with data from the AST.
     * 
     * @param ast
     *            The AST to traverse.
     * @return The filled result instance.
     */
    private CalculationResult extractCalcThermData(final CommonTree ast) {
        final CalculationResult result = new DefaultCalculationResult();
        int atomColCount = 0;
        Atom curAtom = new DefaultAtom();
        @SuppressWarnings("unchecked")
        final List<CommonTree> eventList = ast.getChildren();
        for (CommonTree curNode : eventList) {
            switch (curNode.getType()) {
            case Gaussian09Lexer.EOF:
                break;
            case Gaussian09Lexer.CPUTIME:
                result.getCpuTimes().add(processCpuTime(curNode));
                break;
            case Gaussian09Lexer.TERM:
                result.getTerminationDates().add(processTermDate(curNode));
                break;
            case Gaussian09Lexer.TRANSPART:
                result.setTransPart(toDouble(curNode.getText()));
                break;
            case Gaussian09Lexer.ROTPART:
                result.setRotPart(toDouble(curNode.getText()));
                break;
            case Gaussian09Lexer.MULT:
                result.setMult(toInt(curNode.getText()));
                break;
            case Gaussian09Lexer.NATOMS:
                result.setAtomCount(toInt(curNode.getText()));
                break;
            case Gaussian09Lexer.FREQVAL:
                final Double freqVal = toDouble(curNode.getText());
                if (freqVal != null) {
                    result.getFrequencyValues().add(freqVal);
                }
                break;
            case Gaussian09Lexer.ELECENG:
                result.setElecEn(toDouble(curNode.getText()));
                break;
            case Gaussian09Lexer.ASYM:
                result.setSymmetricTop(false);
                break;
            case Gaussian09Lexer.XYZINT:
            case Gaussian09Lexer.XYZFLOAT:
                handleAtom(curNode.getText(), curAtom, atomColCount);
                atomColCount++;
                if (atomColCount % ATOM_COL_COUNT == 0) {
                    result.getAtoms().add(curAtom);
                    curAtom = new DefaultAtom();
                }
                break;
            default:
                logger.warn(String.format("Unhandled data %s %s",
                        curNode.getType(), curNode.getText()));
                break;
            }
        }
        return result;
    }
}