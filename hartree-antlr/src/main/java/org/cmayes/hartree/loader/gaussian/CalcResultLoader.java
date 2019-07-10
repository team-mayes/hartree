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
import org.cmayes.hartree.parser.gaussian.antlr.CalcResultParser;
import org.cmayes.hartree.parser.gaussian.antlr.GaussianLexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.exception.EnvironmentException;
import com.cmayes.common.model.Atom;
import com.cmayes.common.model.impl.DefaultAtom;

/**
 * Fills a BaseResult from data parsed from the given reader.
 * 
 * @author cmayes
 */
/**
 * @author cmayes
 * 
 */
public class CalcResultLoader extends BaseGaussianLoader implements
        Loader<BaseResult> {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.loader.Loader#load(String, java.io.Reader)
     */
    public BaseResult load(final String srcName, final Reader reader) {
        return extractCalcThermData(srcName, extractAst(srcName, reader));
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
    private BaseResult extractCalcThermData(final String srcName,
            final CommonTree ast) {
        final BaseResult result = new DefaultBaseResult(srcName);
        int atomColCount = 0;
        Atom curAtom = new DefaultAtom();
        @SuppressWarnings("unchecked")
        final List<CommonTree> eventList = (List<CommonTree>) ast.getChildren();
        if (eventList == null) {
            logger.error("Parse failed: no AST children found for source "
                    + srcName);
            return result;
        }
        for (CommonTree curNode : eventList) {
            switch (curNode.getType()) {
            case GaussianLexer.EOF:
                break;
            case GaussianLexer.CPUTIME:
                result.getCpuTimes().add(processCpuTime(curNode));
                break;
            case GaussianLexer.TERM:
                result.getTerminationDates().add(processTermDate(curNode));
                break;
            case GaussianLexer.TRANSPART:
                result.setTransPart(toDouble(curNode.getText()));
                break;
            case GaussianLexer.ROTPART:
                result.setRotPart(toDouble(curNode.getText()));
                break;
            case GaussianLexer.MULT:
                result.setMult(toInt(curNode.getText()));
                break;
            case GaussianLexer.NATOMS:
                result.setAtomCount(toInt(curNode.getText()));
                break;
            case GaussianLexer.FREQVAL:
                final Double freqVal = toDouble(curNode.getText());
                if (freqVal != null) {
                    result.getFrequencyValues().add(freqVal);
                }
                break;
            case GaussianLexer.ELECENG:
                result.setElecEn(toDouble(curNode.getText()));
                break;
            case GaussianLexer.ASYM:
                result.setSymmetricTop(false);
                break;
            case GaussianLexer.XYZINT:
            case GaussianLexer.XYZFLOAT:
                handleAtom(curNode.getText(), curAtom, atomColCount);
                atomColCount++;
                if (atomColCount % ATOM_COL_COUNT == 0) {
                    result.addAtom(curAtom);
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
            final GaussianLexer lexer = new GaussianLexer(
                    new ANTLRReaderStream(reader));
            final CalcResultParser parser = new CalcResultParser(
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