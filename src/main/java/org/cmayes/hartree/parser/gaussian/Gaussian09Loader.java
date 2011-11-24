package org.cmayes.hartree.parser.gaussian;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.cmayes.hartree.parser.DefaultCalculationResult;
import org.cmayes.hartree.parser.ParseException;
import org.cmayes.hartree.parser.Parser;
import org.cmayes.hartree.parser.gaussian.antlr.Gaussian09Lexer;
import org.cmayes.hartree.parser.gaussian.antlr.Gaussian09Parser;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.exception.EnvironmentException;

/**
 * Fills a CalculationResult from data parsed from the given reader.
 * 
 * @author cmayes
 */
public class Gaussian09Loader implements Parser<DefaultCalculationResult> {
    private static final int SEC_IDX = 3;
    private static final int MIN_IDX = 2;
    private static final int HOUR_IDX = 1;
    private static final int DAY_IDX = 0;
    private static final String TERM_DATE_PAT = "E MMM dd HH:mm:ss yyyy";
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.Parser#parse(java.io.Reader)
     */
    public DefaultCalculationResult parse(final Reader reader) {
        return extractCalcThermData(extractAst(reader));
    }

    /**
     * Parses the data from the reader into an abstract syntax tree.
     * 
     * @param reader
     *            The source of the data to parse.
     * @return The abstract syntax tree pulled from the reader.
     */
    private CommonTree extractAst(final Reader reader) {
        try {
            final Gaussian09Lexer lexer = new Gaussian09Lexer(
                    new ANTLRReaderStream(reader));
            final Gaussian09Parser parser = new Gaussian09Parser(
                    new CommonTokenStream(lexer));
            return (CommonTree) parser.script().getTree();
        } catch (final IOException e) {
            throw new EnvironmentException("Problems reading file", e);
        } catch (final RecognitionException e) {
            throw new ParseException("Problems parsing file", e);
        }
    }

    /**
     * Fills a {@link DefaultCalculationResult} instance with data from the AST.
     * 
     * @param ast
     *            The AST to traverse.
     * @return The filled result instance.
     */
    private DefaultCalculationResult extractCalcThermData(final CommonTree ast) {
        final DefaultCalculationResult result = new DefaultCalculationResult();
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
            default:
                break;
            }
        }
        return result;
    }

    /**
     * Parses value into an Integer.
     * 
     * @param strVal
     *            The string value to convert.
     * @return The integer value or null if the parse fails.
     */
    private Integer toInt(final String strVal) {
        try {
            return Integer.valueOf(strVal);
        } catch (final NumberFormatException e) {
            logger.warn("Couldn't parse integer " + strVal);
            return null;
        }
    }

    /**
     * Parses value into a Double.
     * 
     * @param strVal
     *            The string value to convert.
     * @return The double value or null if the parse fails.
     */
    private Double toDouble(final String strVal) {
        try {
            final String properSciNot = strVal.replace('D', 'E');
            return Double.valueOf(properSciNot);
        } catch (final NumberFormatException e) {
            logger.warn("Couldn't parse double " + strVal);
            return null;
        }
    }

    /**
     * Pulls and parses the date string from the node, returning the filled
     * {@link Date} instance.
     * 
     * @param curNode
     *            The date node.
     * @return The filled date instance.
     */
    private Date processTermDate(final CommonTree curNode) {
        String rawDate = null;
        try {
            rawDate = curNode.getChild(0).getText();
            return new SimpleDateFormat(TERM_DATE_PAT).parse(rawDate);
        } catch (final java.text.ParseException e) {
            logger.warn(String.format("Parse exception on date %s", rawDate));
            return null;
        }
    }

    /**
     * Pulls and parses the duration data from the CPU time node.
     * 
     * @param curNode
     *            The node to parse.
     * @return The duration or null if the parse fails.
     */
    private Duration processCpuTime(final CommonTree curNode) {
        try {
            final Duration dur = Duration
                    .standardDays(
                            Long.valueOf(curNode.getChild(DAY_IDX).getText()))
                    .plus(Duration.standardHours(Long.valueOf(curNode.getChild(
                            HOUR_IDX).getText())))
                    .plus(Duration.standardMinutes(Long.valueOf(curNode
                            .getChild(MIN_IDX).getText())));
            final BigDecimal seconds = new BigDecimal(curNode.getChild(SEC_IDX)
                    .getText());
            final BigDecimal millis = seconds.multiply(new BigDecimal(1000));
            return dur.plus(Duration.millis(millis.longValue()));
        } catch (final NumberFormatException e) {
            logger.warn("Problems processing numbers for CPU time", e);
            return null;
        }
    }
}