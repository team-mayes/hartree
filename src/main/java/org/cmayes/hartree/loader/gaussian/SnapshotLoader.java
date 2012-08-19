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
import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.def.DefaultBaseResult;
import org.cmayes.hartree.parser.gaussian.antlr.SnapshotLexer;
import org.cmayes.hartree.parser.gaussian.antlr.SnapshotParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.exception.EnvironmentException;
import com.cmayes.common.model.Atom;
import com.cmayes.common.model.impl.DefaultAtom;

/**
 * Fills a {@link BaseResult} with data parsed from the given reader.
 * 
 * @author cmayes
 */
public class SnapshotLoader extends BaseGaussianLoader implements
        Loader<BaseResult> {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.loader.Loader#load(String, java.io.Reader)
     */
    public BaseResult load(final String srcName, final Reader reader) {
        return extractSnapshotData(srcName, extractAst(srcName, reader));
    }

    /**
     * Fills a {@link BaseResult} instance with data from the AST.
     * 
     * @param srcName
     *            The identifier for the source of the data.
     * @param ast
     *            The AST to traverse.
     * 
     * @return The filled result instance.
     */
    private BaseResult extractSnapshotData(final String srcName,
            final CommonTree ast) {
        final BaseResult result = new DefaultBaseResult(srcName);
        int atomColCount = 0;
        Atom curAtom = new DefaultAtom();
        @SuppressWarnings("unchecked")
        final List<CommonTree> eventList = ast.getChildren();
        if (eventList == null) {
            logger.error("Parse failed: no AST children found for source "
                    + srcName);
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
            case SnapshotLexer.XYZINT:
            case SnapshotLexer.XYZFLOAT:
                handleAtom(curNode.getText(), curAtom, atomColCount);
                atomColCount++;
                if (atomColCount % ATOM_COL_COUNT == 0) {
                    result.addAtom(curAtom);
                    curAtom = new DefaultAtom();
                }
                break;
            case SnapshotLexer.ELECENG:
                result.setElecEn(toDouble(curNode.getText()));
                break;
            case SnapshotLexer.FUNCSET:
                final String[] funcSetSplit = curNode.getText().split("/");
                result.setFunctional(funcSetSplit[0]);
                result.setBasisSet(funcSetSplit[1]);
                break;
            case SnapshotLexer.SOLVENT:
                final String[] solvSplit = curNode.getText().split("=");
                result.setSolvent(solvSplit[1]);
                break;
            case SnapshotLexer.ZPECORR:
                result.setZpeCorrection(toDouble(curNode.getText()));
                break;
            case SnapshotLexer.G298:
                result.setGibbs298(toDouble(curNode.getText()));
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
            case SnapshotLexer.NATOMS:
                result.setAtomCount(toInt(curNode.getText()));
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
     * @param srcName
     *            The identifier for the source of the data.
     * @param reader
     *            The source of the data to parse.
     * 
     * @return The abstract syntax tree pulled from the reader.
     */
    protected CommonTree extractAst(final String srcName, final Reader reader) {
        try {
            final SnapshotLexer lexer = new SnapshotLexer(
                    new ANTLRReaderStream(reader));
            final SnapshotParser parser = new SnapshotParser(
                    new CommonTokenStream(lexer));
            return (CommonTree) parser.script().getTree();
        } catch (final IOException e) {
            throw new EnvironmentException("Problems reading from " + srcName,
                    e);
        } catch (final RecognitionException e) {
            throw new ParseException("Problems parsing data from " + srcName, e);
        }
    }
}