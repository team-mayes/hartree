package org.cmayes.hartree.loader.gaussian;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.cmayes.hartree.loader.Loader;
import org.cmayes.hartree.loader.ParseException;
import org.cmayes.hartree.model.CalculationResult;
import org.cmayes.hartree.model.CalculationSnapshot;
import org.cmayes.hartree.model.def.DefaultCalculationSnapshot;
import org.cmayes.hartree.parser.gaussian.antlr.SnapshotLexer;
import org.cmayes.hartree.parser.gaussian.antlr.SnapshotParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.exception.EnvironmentException;

/**
 * Fills a CalculationSnapshot from data parsed from the given reader.
 * 
 * @author cmayes
 */
public class SnapshotLoader extends BaseGaussianLoader implements
        Loader<CalculationSnapshot> {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.loader.Loader#load(java.io.Reader)
     */
    public CalculationSnapshot load(final Reader reader) {
        return extractSnapshotData(extractAst(reader));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.loader.Loader#load(java.io.Reader,
     *      java.lang.String)
     */
    public CalculationSnapshot load(final Reader reader, String fileName) {
        CalculationSnapshot result = extractSnapshotData(extractAst(reader));
        result.setFileName(fileName);
        return result;
    }

    /**
     * Fills a {@link CalculationResult} instance with data from the AST.
     * 
     * @param ast
     *            The AST to traverse.
     * @return The filled result instance.
     */
    private CalculationSnapshot extractSnapshotData(final CommonTree ast) {
        final CalculationSnapshot result = new DefaultCalculationSnapshot();
        @SuppressWarnings("unchecked")
        final List<CommonTree> eventList = ast.getChildren();
        if (eventList == null) {
            logger.error("Parse failed: no AST children found");
            return result;
        }
        for (CommonTree curNode : eventList) {
            switch (curNode.getType()) {
            case SnapshotLexer.EOF:
                break;
            case SnapshotLexer.CPUTIME:
                result.getCpuTimes().add(processCpuTime(curNode));
                break;
            case SnapshotLexer.TERM:
                result.getTerminationDates().add(processTermDate(curNode));
                break;
            case SnapshotLexer.MULT:
                result.setMult(toInt(curNode.getText()));
                break;
            case SnapshotLexer.FREQVAL:
                final Double freqVal = toDouble(curNode.getText());
                if (freqVal != null) {
                    result.getFrequencyValues().add(freqVal);
                }
                break;
            case SnapshotLexer.ELECENG:
                result.setElecEn(toDouble(curNode.getText()));
                break;
            case SnapshotLexer.FUNCSET:
                String[] funcSetSplit = curNode.getText().split("/");
                result.setFunctional(funcSetSplit[0]);
                result.setBasisSet(funcSetSplit[1]);
                break;
            case SnapshotLexer.SOLVENT:
                String[] solvSplit = curNode.getText().split("=");
                result.setSolvent(solvSplit[1]);
                break;
            case SnapshotLexer.ZPECORR:
                result.setZpeCorrection(toDouble(curNode.getText()));
                break;
            case SnapshotLexer.CHARGE:
                result.setCharge(toInt(curNode.getText()));
                break;
            case SnapshotLexer.STOI:
                result.setStoichiometry(curNode.getText());
                break;
            case SnapshotLexer.DIPTOT:
                result.setDipoleMomentTotal(toDouble(curNode.getText()));
                break;
            default:
                logger.warn(String.format("Unhandled data %s %s",
                        curNode.getType(), curNode.getText()));
                break;
            }
        }
        return result;
    }

    /**
     * Parses the data from the reader into an abstract syntax tree.
     * 
     * @param reader
     *            The source of the data to parse.
     * @return The abstract syntax tree pulled from the reader.
     */
    protected CommonTree extractAst(final Reader reader) {
        try {
            final SnapshotLexer lexer = new SnapshotLexer(
                    new ANTLRReaderStream(reader));
            final SnapshotParser parser = new SnapshotParser(
                    new CommonTokenStream(lexer));
            return (CommonTree) parser.script().getTree();
        } catch (final IOException e) {
            throw new EnvironmentException("Problems reading file", e);
        } catch (final RecognitionException e) {
            throw new ParseException("Problems parsing file", e);
        }
    }
}