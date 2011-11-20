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
import org.cmayes.hartree.EnvironmentException;
import org.cmayes.hartree.parser.CalculationResult;
import org.cmayes.hartree.parser.ParseException;
import org.cmayes.hartree.parser.gaussian.antlr.Gaussian09Lexer;
import org.cmayes.hartree.parser.gaussian.antlr.Gaussian09Parser;
import org.joda.time.Duration;

/**
 * Fills a CalculationResult from data parsed from the given reader.
 * 
 * @author cmayes
 */
public class Gaussian09Loader {
    private static final String TERM_DATE_PAT = "E MMM dd HH:mm:ss yyyy";
    private final Reader input;
    private CommonTree ast;
    private boolean isInitialized = false;

    /**
     * Creates a loader for the given reader.
     * 
     * @param input
     */
    public Gaussian09Loader(Reader input) {
        this.input = input;
    }

    public CalculationResult extractCalcThermData() {
        if (!isInitialized) {
            initialize();
        }
        CalculationResult result = new CalculationResult();
        @SuppressWarnings("unchecked")
        List<CommonTree> eventList = ast.getChildren();
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
                result.getFrequencyValues().add(toDouble(curNode.getText()));
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

    private Integer toInt(String strVal) {
        try {
            return Integer.valueOf(strVal);
        } catch (NumberFormatException e) {
            System.err.println("Couldn't parse integer " + strVal);
            return null;
        }
    }

    private Double toDouble(String strVal) {
        try {
            strVal = strVal.replace('D', 'E');
            return Double.valueOf(strVal);
        } catch (NumberFormatException e) {
            System.err.println("Couldn't parse double " + strVal);
            return null;
        }
    }

    private Date processTermDate(CommonTree curNode) {
        String rawDate = null;
        try {
            rawDate = curNode.getChild(0).getText();
            return new SimpleDateFormat(TERM_DATE_PAT).parse(rawDate);
        } catch (java.text.ParseException e) {
            System.err.println(String.format("Parse exception on date %s",
                    rawDate));
            return null;
        }
    }

    private Duration processCpuTime(CommonTree curNode) {
        Duration dur = Duration
                .standardDays(Long.valueOf(curNode.getChild(0).getText()))
                .plus(Duration.standardHours(Long.valueOf(curNode.getChild(1)
                        .getText())))
                .plus(Duration.standardMinutes(Long.valueOf(curNode.getChild(2)
                        .getText())));
        BigDecimal seconds = new BigDecimal(curNode.getChild(3).getText());
        BigDecimal millis = seconds.multiply(new BigDecimal(1000));
        return dur.plus(Duration.millis(millis.longValue()));
    }

    public synchronized void initialize() {
        try {
            Gaussian09Lexer lexer = new Gaussian09Lexer(new ANTLRReaderStream(
                    input));
            Gaussian09Parser parser = new Gaussian09Parser(
                    new CommonTokenStream(lexer));
            ast = (CommonTree) parser.script().getTree();
        } catch (IOException e) {
            throw new EnvironmentException("Problems reading file", e);
        } catch (RecognitionException e) {
            throw new ParseException("Problems parsing file", e);
        }
        isInitialized = true;
    }

    public boolean isInitialized() {
        return isInitialized;
    }
}