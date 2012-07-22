package org.cmayes.hartree.loader.gaussian;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.cmayes.hartree.loader.Loader;
import org.cmayes.hartree.loader.ParseException;
import org.cmayes.hartree.model.LowestEnergyMapper;
import org.cmayes.hartree.parser.gaussian.antlr.SnapshotLexer;
import org.cmayes.hartree.parser.gaussian.antlr.SnapshotParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.exception.EnvironmentException;
import com.cmayes.common.model.Atom;
import com.cmayes.common.model.impl.DefaultAtom;

/**
 * Fills a {@link LowestEnergyMapper} with data parsed from the given reader.
 * 
 * @author cmayes
 */
public class LowestEnergyLoader extends BaseGaussianLoader implements
        Loader<LowestEnergyMapper> {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.loader.Loader#load(java.io.Reader)
     */
    public LowestEnergyMapper load(final Reader reader) {
        return extractSnapshotData(extractAst(reader));
    }

    /**
     * Fills a {@link LowestEnergyMapper} instance with data from the AST.
     * 
     * @param ast
     *            The AST to traverse.
     * @return The filled result instance.
     */
    private LowestEnergyMapper extractSnapshotData(final CommonTree ast) {
        final LowestEnergyMapper result = new LowestEnergyMapper();
        int atomColCount = 0;
        Atom curAtom = new DefaultAtom();
        List<Atom> curAtomList = new ArrayList<Atom>();
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
            case SnapshotLexer.XYZINT:
            case SnapshotLexer.XYZFLOAT:
                handleAtom(curNode.getText(), curAtom, atomColCount);
                atomColCount++;
                if (atomColCount % ATOM_COL_COUNT == 0) {
                    curAtomList.add(curAtom);
                    curAtom = new DefaultAtom();
                }
                break;
            case SnapshotLexer.ELECENG:
                result.add(toDouble(curNode.getText()), curAtomList);
                curAtomList = new ArrayList<Atom>();
                break;
            default:
                logger.debug(String.format(
                        "Stuff not used in lowest energy fills %s %s",
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