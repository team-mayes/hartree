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
import org.cmayes.hartree.parser.gaussian.antlr.Gaussian09Lexer;
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
     * @see org.cmayes.hartree.loader.Loader#load(java.io.Reader)
     */
    public BaseResult load(final Reader reader) {
        return extractCalcThermData(extractAst(reader));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.loader.Loader#load(java.io.Reader,
     *      java.lang.String)
     */
    public BaseResult load(final Reader reader, final String fileName) {
        final BaseResult result = extractCalcThermData(extractAst(reader));
        result.setFileName(fileName);
        return result;
    }

    /**
     * Fills a {@link BaseResult} instance with data from the AST.
     * 
     * @param ast
     *            The AST to traverse.
     * @return The filled result instance.
     */
    private BaseResult extractCalcThermData(final CommonTree ast) {
        final BaseResult result = new DefaultBaseResult();
        int atomColCount = 0;
        Atom curAtom = new DefaultAtom();
        @SuppressWarnings("unchecked")
        final List<CommonTree> eventList = ast.getChildren();
        if (eventList == null) {
            logger.error("Parse failed: no AST children found");
            return result;
        }
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

    /**
     * Parses the data from the reader into an abstract syntax tree.
     * 
     * @param reader
     *            The source of the data to parse.
     * @return The abstract syntax tree pulled from the reader.
     */
    protected CommonTree extractAst(final Reader reader) {
        try {
            final Gaussian09Lexer lexer = new Gaussian09Lexer(
                    new ANTLRReaderStream(reader));
            final CalcResultParser parser = new CalcResultParser(
                    new CommonTokenStream(lexer));
            return (CommonTree) parser.script().getTree();
        } catch (final IOException e) {
            throw new EnvironmentException("Problems reading file", e);
        } catch (final RecognitionException e) {
            throw new ParseException("Problems parsing file", e);
        }
    }
}