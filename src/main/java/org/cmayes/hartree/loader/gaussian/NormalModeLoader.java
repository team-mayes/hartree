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
import org.cmayes.hartree.model.Atom;
import org.cmayes.hartree.model.InternalMotion;
import org.cmayes.hartree.model.NormalMode;
import org.cmayes.hartree.model.NormalModeCalculation;
import org.cmayes.hartree.model.def.DefaultAtom;
import org.cmayes.hartree.model.def.DefaultInternalMotion;
import org.cmayes.hartree.model.def.DefaultNormalMode;
import org.cmayes.hartree.model.def.DefaultNormalModeCalculation;
import org.cmayes.hartree.parser.gaussian.antlr.Gaussian09Lexer;
import org.cmayes.hartree.parser.gaussian.antlr.NormalModeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.chem.InternalMotionType;
import com.cmayes.common.exception.EnvironmentException;

/**
 * Fills a CalculationResult from data parsed from the given reader.
 * 
 * @author cmayes
 */
public class NormalModeLoader extends BaseGaussianLoader implements
        Loader<NormalModeCalculation> {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.loader.Loader#load(java.io.Reader)
     */
    public NormalModeCalculation load(final Reader reader) {
        return extractNormalModeData(extractAst(reader));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.loader.Loader#load(java.io.Reader,
     *      java.lang.String)
     */
    public NormalModeCalculation load(final Reader reader, String fileName) {
        NormalModeCalculation result = extractNormalModeData(extractAst(reader));
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
    private NormalModeCalculation extractNormalModeData(final CommonTree ast) {
        final NormalModeCalculation result = new DefaultNormalModeCalculation();
        int atomColCount = 0;
        Atom curAtom = new DefaultAtom();
        NormalMode curNormal = null;
        InternalMotion curMotion = null;
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
            case Gaussian09Lexer.FREQVAL:
                final Double freqVal = toDouble(curNode.getText());
                if (freqVal != null) {
                    result.getFrequencyValues().add(freqVal);
                }
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
            case Gaussian09Lexer.NORMTAG:
                curNormal = new DefaultNormalMode();
                result.getNormalModes().add(curNormal);
                break;
            case Gaussian09Lexer.NORMOPEN:
                curMotion = new DefaultInternalMotion();
                curNormal.getMotions().add(curMotion);
                curMotion.setType(InternalMotionType.valueOfSymbol(curNode
                        .getText().substring(0, 1)));
                break;
            case Gaussian09Lexer.NORMATOM:
                curMotion.getMembers().add(toInt(curNode.getText()));
                break;
            case Gaussian09Lexer.NORMFLOAT:
                if (curMotion.getValue() == null) {
                    curMotion.setValue(toDouble(curNode.getText()));
                } else {
                    curMotion.setWeight(toDouble(curNode.getText()));
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
            final NormalModeParser parser = new NormalModeParser(
                    new CommonTokenStream(lexer));
            return (CommonTree) parser.script().getTree();
        } catch (final IOException e) {
            throw new EnvironmentException("Problems reading file", e);
        } catch (final RecognitionException e) {
            throw new ParseException("Problems parsing file", e);
        }
    }
}