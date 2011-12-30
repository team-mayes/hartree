package org.cmayes.hartree.loader.gaussian;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.antlr.runtime.tree.CommonTree;
import org.cmayes.hartree.model.Atom;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.chem.AtomicElement;

/**
 * Logic common to Gaussian AST loaders.
 * 
 * @author cmayes
 */
public class BaseGaussianLoader {
    /** The number of columns in the atom info table "Input orientation". */
    public static final int ATOM_COL_COUNT = 6;
    public static final int SEC_IDX = 3;
    public static final int MIN_IDX = 2;
    public static final int HOUR_IDX = 1;
    public static final int DAY_IDX = 0;
    public static final String TERM_DATE_PAT = "E MMM dd HH:mm:ss yyyy";
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Adds data to the current atom.
     * 
     * @param nodeText
     *            The text value of the current node.
     * @param curAtom
     *            The atom we are currently filling.
     * @param atomColCount
     *            The number of columns processed for XYZ atom data.
     */
    protected void handleAtom(final String nodeText, final Atom curAtom,
            final int atomColCount) {
        switch (atomColCount % ATOM_COL_COUNT) {
        case 0:
            curAtom.setId(toInt(nodeText));
            break;
        case 1:
            curAtom.setType(AtomicElement.valueOf(toInt(nodeText)));
            break;
        case 2:
            // Atomic type? Skip it; we don't know what it is.
            break;
        case 3:
            curAtom.setxPos(toDouble(nodeText));
            break;
        case 4:
            curAtom.setyPos(toDouble(nodeText));
            break;
        case 5:
            curAtom.setyPos(toDouble(nodeText));
            break;
        default:
            logger.warn("Unlikely atom mod %d", atomColCount);
            break;
        }
    }

    /**
     * Parses value into an Integer.
     * 
     * @param strVal
     *            The string value to convert.
     * @return The integer value or null if the parse fails.
     */
    protected Integer toInt(final String strVal) {
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
    protected Double toDouble(final String strVal) {
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
    protected Date processTermDate(final CommonTree curNode) {
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
    protected Duration processCpuTime(final CommonTree curNode) {
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
