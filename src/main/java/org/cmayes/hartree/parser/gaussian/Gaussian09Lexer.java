// $ANTLR 3.4 /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g 2011-11-20 11:13:13

  package org.cmayes.hartree.parser.gaussian;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class Gaussian09Lexer extends Lexer {
    public static final int EOF=-1;
    public static final int ANUM=4;
    public static final int CPUDAYS=5;
    public static final int CPUFLOAT=6;
    public static final int CPUHOURS=7;
    public static final int CPUINT=8;
    public static final int CPUMINS=9;
    public static final int CPUSECS=10;
    public static final int CPUTAG=11;
    public static final int CPUTIME=12;
    public static final int DATE=13;
    public static final int ELECENG=14;
    public static final int FLOAT=15;
    public static final int FREQTAG=16;
    public static final int FREQVAL=17;
    public static final int INT=18;
    public static final int LETTER=19;
    public static final int MULT=20;
    public static final int MULTTAG=21;
    public static final int NACTIVE=22;
    public static final int NATOMS=23;
    public static final int NATOMSTAG=24;
    public static final int PARTITIONTAG=25;
    public static final int REDMASS=26;
    public static final int ROTPART=27;
    public static final int ROTTAG=28;
    public static final int SCFTAG=29;
    public static final int TERM=30;
    public static final int TERMDATE=31;
    public static final int TERMEND=32;
    public static final int TERMINT=33;
    public static final int TERMTAG=34;
    public static final int TRANSPART=35;
    public static final int TRANSTAG=36;
    public static final int WORD=37;
    public static final int WS=38;

        boolean cpuCtx = false;
        boolean termCtx = false;
        boolean partCtx = false;
        boolean transCtx = false;
        boolean rotCtx = false;
        boolean multCtx = false;
        boolean natomsFound = false;
        boolean natomsCtx = false;
        boolean freqCtx = false;
        boolean elecEngCtx = false;


    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public Gaussian09Lexer() {} 
    public Gaussian09Lexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public Gaussian09Lexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g"; }

    public Token nextToken() {
        while (true) {
            if ( input.LA(1)==CharStream.EOF ) {
                Token eof = new CommonToken((CharStream)input,Token.EOF,
                                            Token.DEFAULT_CHANNEL,
                                            input.index(),input.index());
                eof.setLine(getLine());
                eof.setCharPositionInLine(getCharPositionInLine());
                return eof;
            }
            state.token = null;
    	state.channel = Token.DEFAULT_CHANNEL;
            state.tokenStartCharIndex = input.index();
            state.tokenStartCharPositionInLine = input.getCharPositionInLine();
            state.tokenStartLine = input.getLine();
    	state.text = null;
            try {
                int m = input.mark();
                state.backtracking=1; 
                state.failed=false;
                mTokens();
                state.backtracking=0;
                if ( state.failed ) {
                    input.rewind(m);
                    input.consume(); 
                }
                else {
                    emit();
                    return state.token;
                }
            }
            catch (RecognitionException re) {
                // shouldn't happen in backtracking mode, but...
                reportError(re);
                recover(re);
            }
        }
    }

    public void memoize(IntStream input,
    		int ruleIndex,
    		int ruleStartIndex)
    {
    if ( state.backtracking>1 ) super.memoize(input, ruleIndex, ruleStartIndex);
    }

    public boolean alreadyParsedRule(IntStream input, int ruleIndex) {
    if ( state.backtracking>1 ) return super.alreadyParsedRule(input, ruleIndex);
    return false;
    }
    // $ANTLR start "MULTTAG"
    public final void mMULTTAG() throws RecognitionException {
        try {
            int _type = MULTTAG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:31:8: ( 'Multiplicity' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:31:10: 'Multiplicity'
            {
            match("Multiplicity"); if (state.failed) return ;



            if ( state.backtracking==1 ) { multCtx = true; _channel = HIDDEN; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MULTTAG"

    // $ANTLR start "MULT"
    public final void mMULT() throws RecognitionException {
        try {
            int _type = MULT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:32:5: ({...}? => INT )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:32:7: {...}? => INT
            {
            if ( !((multCtx)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "MULT", "multCtx");
            }

            mINT(); if (state.failed) return ;


            if ( state.backtracking==1 ) { multCtx = false; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MULT"

    // $ANTLR start "NATOMSTAG"
    public final void mNATOMSTAG() throws RecognitionException {
        try {
            int _type = NATOMSTAG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:35:10: ({...}? => 'NAtoms' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:35:12: {...}? => 'NAtoms'
            {
            if ( !((!natomsFound)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "NATOMSTAG", "!natomsFound");
            }

            match("NAtoms"); if (state.failed) return ;



            if ( state.backtracking==1 ) { natomsCtx = true; _channel = HIDDEN; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NATOMSTAG"

    // $ANTLR start "NATOMS"
    public final void mNATOMS() throws RecognitionException {
        try {
            int _type = NATOMS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:36:7: ({...}? => INT )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:36:9: {...}? => INT
            {
            if ( !(((natomsCtx && (!natomsFound)))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "NATOMS", "(natomsCtx && (!natomsFound))");
            }

            mINT(); if (state.failed) return ;


            if ( state.backtracking==1 ) { natomsFound = true; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NATOMS"

    // $ANTLR start "NACTIVE"
    public final void mNACTIVE() throws RecognitionException {
        try {
            int _type = NACTIVE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:37:8: ({...}? => 'NActive' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:37:10: {...}? => 'NActive'
            {
            if ( !((natomsCtx)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "NACTIVE", "natomsCtx");
            }

            match("NActive"); if (state.failed) return ;



            if ( state.backtracking==1 ) { natomsCtx = false; _channel = HIDDEN; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NACTIVE"

    // $ANTLR start "SCFTAG"
    public final void mSCFTAG() throws RecognitionException {
        try {
            int _type = SCFTAG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:39:7: ( 'SCF Done' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:39:9: 'SCF Done'
            {
            match("SCF Done"); if (state.failed) return ;



            if ( state.backtracking==1 ) { elecEngCtx = true; _channel = HIDDEN; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SCFTAG"

    // $ANTLR start "ELECENG"
    public final void mELECENG() throws RecognitionException {
        try {
            int _type = ELECENG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:40:8: ({...}? => FLOAT )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:40:10: {...}? => FLOAT
            {
            if ( !((elecEngCtx)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "ELECENG", "elecEngCtx");
            }

            mFLOAT(); if (state.failed) return ;


            if ( state.backtracking==1 ) { elecEngCtx = false; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ELECENG"

    // $ANTLR start "FREQTAG"
    public final void mFREQTAG() throws RecognitionException {
        try {
            int _type = FREQTAG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:42:8: ( 'Frequencies' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:42:10: 'Frequencies'
            {
            match("Frequencies"); if (state.failed) return ;



            if ( state.backtracking==1 ) { freqCtx = true; _channel = HIDDEN; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FREQTAG"

    // $ANTLR start "FREQVAL"
    public final void mFREQVAL() throws RecognitionException {
        try {
            int _type = FREQVAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:43:8: ({...}? => FLOAT )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:43:10: {...}? => FLOAT
            {
            if ( !((freqCtx)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "FREQVAL", "freqCtx");
            }

            mFLOAT(); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FREQVAL"

    // $ANTLR start "REDMASS"
    public final void mREDMASS() throws RecognitionException {
        try {
            int _type = REDMASS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:44:8: ({...}? => 'Red. masses' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:44:10: {...}? => 'Red. masses'
            {
            if ( !((freqCtx)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "REDMASS", "freqCtx");
            }

            match("Red. masses"); if (state.failed) return ;



            if ( state.backtracking==1 ) { freqCtx = false; _channel = HIDDEN;}

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "REDMASS"

    // $ANTLR start "PARTITIONTAG"
    public final void mPARTITIONTAG() throws RecognitionException {
        try {
            int _type = PARTITIONTAG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:47:13: ( 'Q' ( WS )+ 'Log10(Q)' ( WS )+ 'Ln(Q)' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:47:15: 'Q' ( WS )+ 'Log10(Q)' ( WS )+ 'Ln(Q)'
            {
            match('Q'); if (state.failed) return ;

            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:47:19: ( WS )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '\t' && LA1_0 <= '\n')||(LA1_0 >= '\f' && LA1_0 <= '\r')||LA1_0==' ') ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:47:19: WS
            	    {
            	    mWS(); if (state.failed) return ;


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            match("Log10(Q)"); if (state.failed) return ;



            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:47:34: ( WS )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0 >= '\t' && LA2_0 <= '\n')||(LA2_0 >= '\f' && LA2_0 <= '\r')||LA2_0==' ') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:47:34: WS
            	    {
            	    mWS(); if (state.failed) return ;


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            match("Ln(Q)"); if (state.failed) return ;



            if ( state.backtracking==1 ) { partCtx = true; _channel = HIDDEN; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PARTITIONTAG"

    // $ANTLR start "TRANSTAG"
    public final void mTRANSTAG() throws RecognitionException {
        try {
            int _type = TRANSTAG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:48:9: ({...}? => 'Translational' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:48:11: {...}? => 'Translational'
            {
            if ( !((partCtx)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "TRANSTAG", "partCtx");
            }

            match("Translational"); if (state.failed) return ;



            if ( state.backtracking==1 ) { transCtx = true; _channel = HIDDEN; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TRANSTAG"

    // $ANTLR start "TRANSPART"
    public final void mTRANSPART() throws RecognitionException {
        try {
            int _type = TRANSPART;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:49:10: ({...}? => FLOAT )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:49:12: {...}? => FLOAT
            {
            if ( !((transCtx)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "TRANSPART", "transCtx");
            }

            mFLOAT(); if (state.failed) return ;


            if ( state.backtracking==1 ) { transCtx = false; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TRANSPART"

    // $ANTLR start "ROTTAG"
    public final void mROTTAG() throws RecognitionException {
        try {
            int _type = ROTTAG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:50:7: ({...}? => 'Rotational' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:50:9: {...}? => 'Rotational'
            {
            if ( !((partCtx)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "ROTTAG", "partCtx");
            }

            match("Rotational"); if (state.failed) return ;



            if ( state.backtracking==1 ) { rotCtx = true; partCtx = false; _channel = HIDDEN; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ROTTAG"

    // $ANTLR start "ROTPART"
    public final void mROTPART() throws RecognitionException {
        try {
            int _type = ROTPART;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:51:8: ({...}? => FLOAT )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:51:10: {...}? => FLOAT
            {
            if ( !((rotCtx)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "ROTPART", "rotCtx");
            }

            mFLOAT(); if (state.failed) return ;


            if ( state.backtracking==1 ) { rotCtx = false; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ROTPART"

    // $ANTLR start "CPUTAG"
    public final void mCPUTAG() throws RecognitionException {
        try {
            int _type = CPUTAG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:54:7: ( 'Job cpu time:' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:54:9: 'Job cpu time:'
            {
            match("Job cpu time:"); if (state.failed) return ;



            if ( state.backtracking==1 ) { cpuCtx = true; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CPUTAG"

    // $ANTLR start "CPUDAYS"
    public final void mCPUDAYS() throws RecognitionException {
        try {
            int _type = CPUDAYS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:55:8: ( 'days' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:55:10: 'days'
            {
            match("days"); if (state.failed) return ;



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CPUDAYS"

    // $ANTLR start "CPUHOURS"
    public final void mCPUHOURS() throws RecognitionException {
        try {
            int _type = CPUHOURS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:56:9: ( 'hours' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:56:11: 'hours'
            {
            match("hours"); if (state.failed) return ;



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CPUHOURS"

    // $ANTLR start "CPUMINS"
    public final void mCPUMINS() throws RecognitionException {
        try {
            int _type = CPUMINS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:57:8: ( 'minutes' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:57:10: 'minutes'
            {
            match("minutes"); if (state.failed) return ;



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CPUMINS"

    // $ANTLR start "CPUSECS"
    public final void mCPUSECS() throws RecognitionException {
        try {
            int _type = CPUSECS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:58:8: ( 'seconds.' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:58:10: 'seconds.'
            {
            match("seconds."); if (state.failed) return ;



            if ( state.backtracking==1 ) { cpuCtx = false; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CPUSECS"

    // $ANTLR start "CPUFLOAT"
    public final void mCPUFLOAT() throws RecognitionException {
        try {
            int _type = CPUFLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:59:9: ({...}? => FLOAT )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:59:11: {...}? => FLOAT
            {
            if ( !((cpuCtx)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "CPUFLOAT", "cpuCtx");
            }

            mFLOAT(); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CPUFLOAT"

    // $ANTLR start "CPUINT"
    public final void mCPUINT() throws RecognitionException {
        try {
            int _type = CPUINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:60:7: ({...}? => INT )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:60:9: {...}? => INT
            {
            if ( !((cpuCtx)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "CPUINT", "cpuCtx");
            }

            mINT(); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CPUINT"

    // $ANTLR start "TERMTAG"
    public final void mTERMTAG() throws RecognitionException {
        try {
            int _type = TERMTAG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:63:8: ( 'Normal termination of Gaussian 09 at' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:63:10: 'Normal termination of Gaussian 09 at'
            {
            match("Normal termination of Gaussian 09 at"); if (state.failed) return ;



            if ( state.backtracking==1 ) { termCtx = true; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TERMTAG"

    // $ANTLR start "TERMINT"
    public final void mTERMINT() throws RecognitionException {
        try {
            int _type = TERMINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:64:8: ({...}? => INT )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:64:10: {...}? => INT
            {
            if ( !((termCtx)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "TERMINT", "termCtx");
            }

            mINT(); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TERMINT"

    // $ANTLR start "TERMDATE"
    public final void mTERMDATE() throws RecognitionException {
        try {
            int _type = TERMDATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:65:9: ({...}? => DATE )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:65:11: {...}? => DATE
            {
            if ( !((termCtx)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "TERMDATE", "termCtx");
            }

            mDATE(); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TERMDATE"

    // $ANTLR start "TERMEND"
    public final void mTERMEND() throws RecognitionException {
        try {
            int _type = TERMEND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:66:8: ({...}? => '.' )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:66:10: {...}? => '.'
            {
            if ( !((termCtx)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "TERMEND", "termCtx");
            }

            match('.'); if (state.failed) return ;

            if ( state.backtracking==1 ) { termCtx = false; natomsFound = false; }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TERMEND"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:70:15: ( ( '-' )? ( '0' .. '9' )+ '.' ( '0' .. '9' )+ ( ( 'e' | 'E' | 'D' | 'd' ) ( '+' | '-' )? ( '0' .. '9' )+ )? )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:70:17: ( '-' )? ( '0' .. '9' )+ '.' ( '0' .. '9' )+ ( ( 'e' | 'E' | 'D' | 'd' ) ( '+' | '-' )? ( '0' .. '9' )+ )?
            {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:70:17: ( '-' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='-') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:70:18: '-'
                    {
                    match('-'); if (state.failed) return ;

                    }
                    break;

            }


            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:70:24: ( '0' .. '9' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            match('.'); if (state.failed) return ;

            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:70:40: ( '0' .. '9' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0 >= '0' && LA5_0 <= '9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:70:52: ( ( 'e' | 'E' | 'D' | 'd' ) ( '+' | '-' )? ( '0' .. '9' )+ )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0 >= 'D' && LA8_0 <= 'E')||(LA8_0 >= 'd' && LA8_0 <= 'e')) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:70:53: ( 'e' | 'E' | 'D' | 'd' ) ( '+' | '-' )? ( '0' .. '9' )+
                    {
                    if ( (input.LA(1) >= 'D' && input.LA(1) <= 'E')||(input.LA(1) >= 'd' && input.LA(1) <= 'e') ) {
                        input.consume();
                        state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:70:71: ( '+' | '-' )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0=='+'||LA6_0=='-') ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:
                            {
                            if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                                input.consume();
                                state.failed=false;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return ;}
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;
                            }


                            }
                            break;

                    }


                    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:70:82: ( '0' .. '9' )+
                    int cnt7=0;
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( ((LA7_0 >= '0' && LA7_0 <= '9')) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	        state.failed=false;
                    	    }
                    	    else {
                    	        if (state.backtracking>0) {state.failed=true; return ;}
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt7 >= 1 ) break loop7;
                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(7, input);
                                throw eee;
                        }
                        cnt7++;
                    } while (true);


                    }
                    break;

            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:71:13: ( ( '-' )? ( '0' .. '9' )+ )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:71:15: ( '-' )? ( '0' .. '9' )+
            {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:71:15: ( '-' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='-') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:71:16: '-'
                    {
                    match('-'); if (state.failed) return ;

                    }
                    break;

            }


            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:71:22: ( '0' .. '9' )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0 >= '0' && LA10_0 <= '9')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "ANUM"
    public final void mANUM() throws RecognitionException {
        try {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:72:14: ( ( '0' .. '9' | 'A' .. 'Z' | 'a' .. 'z' )+ )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:72:16: ( '0' .. '9' | 'A' .. 'Z' | 'a' .. 'z' )+
            {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:72:16: ( '0' .. '9' | 'A' .. 'Z' | 'a' .. 'z' )+
            int cnt11=0;
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0 >= '0' && LA11_0 <= '9')||(LA11_0 >= 'A' && LA11_0 <= 'Z')||(LA11_0 >= 'a' && LA11_0 <= 'z')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ANUM"

    // $ANTLR start "DATE"
    public final void mDATE() throws RecognitionException {
        try {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:73:14: ( ( LETTER )+ ( WS )+ ( LETTER )+ ( WS | ':' | INT )+ )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:73:16: ( LETTER )+ ( WS )+ ( LETTER )+ ( WS | ':' | INT )+
            {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:73:16: ( LETTER )+
            int cnt12=0;
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0 >= 'A' && LA12_0 <= 'Z')||LA12_0=='_'||(LA12_0 >= 'a' && LA12_0 <= 'z')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:
            	    {
            	    if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt12 >= 1 ) break loop12;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        throw eee;
                }
                cnt12++;
            } while (true);


            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:73:24: ( WS )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0 >= '\t' && LA13_0 <= '\n')||(LA13_0 >= '\f' && LA13_0 <= '\r')||LA13_0==' ') ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:73:24: WS
            	    {
            	    mWS(); if (state.failed) return ;


            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
            } while (true);


            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:73:28: ( LETTER )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0 >= 'A' && LA14_0 <= 'Z')||LA14_0=='_'||(LA14_0 >= 'a' && LA14_0 <= 'z')) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:
            	    {
            	    if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);


            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:73:36: ( WS | ':' | INT )+
            int cnt15=0;
            loop15:
            do {
                int alt15=4;
                switch ( input.LA(1) ) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    {
                    alt15=1;
                    }
                    break;
                case ':':
                    {
                    alt15=2;
                    }
                    break;
                case '-':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    {
                    alt15=3;
                    }
                    break;

                }

                switch (alt15) {
            	case 1 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:73:37: WS
            	    {
            	    mWS(); if (state.failed) return ;


            	    }
            	    break;
            	case 2 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:73:42: ':'
            	    {
            	    match(':'); if (state.failed) return ;

            	    }
            	    break;
            	case 3 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:73:48: INT
            	    {
            	    mINT(); if (state.failed) return ;


            	    }
            	    break;

            	default :
            	    if ( cnt15 >= 1 ) break loop15;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(15, input);
                        throw eee;
                }
                cnt15++;
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DATE"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:74:16: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "WORD"
    public final void mWORD() throws RecognitionException {
        try {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:75:14: ( ( LETTER )+ )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:75:16: ( LETTER )+
            {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:75:16: ( LETTER )+
            int cnt16=0;
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0 >= 'A' && LA16_0 <= 'Z')||LA16_0=='_'||(LA16_0 >= 'a' && LA16_0 <= 'z')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:
            	    {
            	    if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt16 >= 1 ) break loop16;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(16, input);
                        throw eee;
                }
                cnt16++;
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WORD"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:76:12: ( ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+ )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:76:14: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
            {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:76:14: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
            int cnt17=0;
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0 >= '\t' && LA17_0 <= '\n')||(LA17_0 >= '\f' && LA17_0 <= '\r')||LA17_0==' ') ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:
            	    {
            	    if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||(input.LA(1) >= '\f' && input.LA(1) <= '\r')||input.LA(1)==' ' ) {
            	        input.consume();
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt17 >= 1 ) break loop17;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(17, input);
                        throw eee;
                }
                cnt17++;
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:39: ( MULTTAG | MULT | NATOMSTAG | NATOMS | NACTIVE | SCFTAG | ELECENG | FREQTAG | FREQVAL | REDMASS | PARTITIONTAG | TRANSTAG | TRANSPART | ROTTAG | ROTPART | CPUTAG | CPUDAYS | CPUHOURS | CPUMINS | CPUSECS | CPUFLOAT | CPUINT | TERMTAG | TERMINT | TERMDATE | TERMEND )
        int alt18=26;
        int LA18_0 = input.LA(1);

        if ( (LA18_0=='M') ) {
            int LA18_1 = input.LA(2);

            if ( (synpred1_Gaussian09Lexer()) ) {
                alt18=1;
            }
            else if ( (((synpred25_Gaussian09Lexer()&&synpred25_Gaussian09Lexer())&&(termCtx))) ) {
                alt18=25;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 1, input);

                throw nvae;

            }
        }
        else if ( (LA18_0=='-') ) {
            int LA18_2 = input.LA(2);

            if ( (((synpred2_Gaussian09Lexer()&&synpred2_Gaussian09Lexer())&&(multCtx))) ) {
                alt18=2;
            }
            else if ( (((((natomsCtx && (!natomsFound)))&&((natomsCtx && (!natomsFound))))&&synpred4_Gaussian09Lexer())) ) {
                alt18=4;
            }
            else if ( ((((elecEngCtx)&&(elecEngCtx))&&synpred7_Gaussian09Lexer())) ) {
                alt18=7;
            }
            else if ( (((synpred9_Gaussian09Lexer()&&synpred9_Gaussian09Lexer())&&(freqCtx))) ) {
                alt18=9;
            }
            else if ( ((((transCtx)&&(transCtx))&&synpred13_Gaussian09Lexer())) ) {
                alt18=13;
            }
            else if ( (((synpred15_Gaussian09Lexer()&&synpred15_Gaussian09Lexer())&&(rotCtx))) ) {
                alt18=15;
            }
            else if ( ((((cpuCtx)&&(cpuCtx))&&synpred21_Gaussian09Lexer())) ) {
                alt18=21;
            }
            else if ( (((synpred22_Gaussian09Lexer()&&synpred22_Gaussian09Lexer())&&(cpuCtx))) ) {
                alt18=22;
            }
            else if ( (((synpred24_Gaussian09Lexer()&&synpred24_Gaussian09Lexer())&&(termCtx))) ) {
                alt18=24;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 2, input);

                throw nvae;

            }
        }
        else if ( ((LA18_0 >= '0' && LA18_0 <= '9')) ) {
            int LA18_3 = input.LA(2);

            if ( (((synpred2_Gaussian09Lexer()&&synpred2_Gaussian09Lexer())&&(multCtx))) ) {
                alt18=2;
            }
            else if ( (((((natomsCtx && (!natomsFound)))&&((natomsCtx && (!natomsFound))))&&synpred4_Gaussian09Lexer())) ) {
                alt18=4;
            }
            else if ( ((((elecEngCtx)&&(elecEngCtx))&&synpred7_Gaussian09Lexer())) ) {
                alt18=7;
            }
            else if ( (((synpred9_Gaussian09Lexer()&&synpred9_Gaussian09Lexer())&&(freqCtx))) ) {
                alt18=9;
            }
            else if ( ((((transCtx)&&(transCtx))&&synpred13_Gaussian09Lexer())) ) {
                alt18=13;
            }
            else if ( (((synpred15_Gaussian09Lexer()&&synpred15_Gaussian09Lexer())&&(rotCtx))) ) {
                alt18=15;
            }
            else if ( ((((cpuCtx)&&(cpuCtx))&&synpred21_Gaussian09Lexer())) ) {
                alt18=21;
            }
            else if ( (((synpred22_Gaussian09Lexer()&&synpred22_Gaussian09Lexer())&&(cpuCtx))) ) {
                alt18=22;
            }
            else if ( (((synpred24_Gaussian09Lexer()&&synpred24_Gaussian09Lexer())&&(termCtx))) ) {
                alt18=24;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 3, input);

                throw nvae;

            }
        }
        else if ( (LA18_0=='N') ) {
            int LA18_4 = input.LA(2);

            if ( (((synpred3_Gaussian09Lexer()&&synpred3_Gaussian09Lexer())&&(!natomsFound))) ) {
                alt18=3;
            }
            else if ( ((((natomsCtx)&&(natomsCtx))&&synpred5_Gaussian09Lexer())) ) {
                alt18=5;
            }
            else if ( (synpred23_Gaussian09Lexer()) ) {
                alt18=23;
            }
            else if ( (((synpred25_Gaussian09Lexer()&&synpred25_Gaussian09Lexer())&&(termCtx))) ) {
                alt18=25;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 4, input);

                throw nvae;

            }
        }
        else if ( (LA18_0=='S') ) {
            int LA18_5 = input.LA(2);

            if ( (synpred6_Gaussian09Lexer()) ) {
                alt18=6;
            }
            else if ( (((synpred25_Gaussian09Lexer()&&synpred25_Gaussian09Lexer())&&(termCtx))) ) {
                alt18=25;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 5, input);

                throw nvae;

            }
        }
        else if ( (LA18_0=='F') ) {
            int LA18_6 = input.LA(2);

            if ( (synpred8_Gaussian09Lexer()) ) {
                alt18=8;
            }
            else if ( (((synpred25_Gaussian09Lexer()&&synpred25_Gaussian09Lexer())&&(termCtx))) ) {
                alt18=25;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 6, input);

                throw nvae;

            }
        }
        else if ( (LA18_0=='R') ) {
            int LA18_7 = input.LA(2);

            if ( (((synpred10_Gaussian09Lexer()&&synpred10_Gaussian09Lexer())&&(freqCtx))) ) {
                alt18=10;
            }
            else if ( ((((partCtx)&&(partCtx))&&synpred14_Gaussian09Lexer())) ) {
                alt18=14;
            }
            else if ( (((synpred25_Gaussian09Lexer()&&synpred25_Gaussian09Lexer())&&(termCtx))) ) {
                alt18=25;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 7, input);

                throw nvae;

            }
        }
        else if ( (LA18_0=='Q') ) {
            int LA18_8 = input.LA(2);

            if ( (synpred11_Gaussian09Lexer()) ) {
                alt18=11;
            }
            else if ( (((synpred25_Gaussian09Lexer()&&synpred25_Gaussian09Lexer())&&(termCtx))) ) {
                alt18=25;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 8, input);

                throw nvae;

            }
        }
        else if ( (LA18_0=='T') ) {
            int LA18_9 = input.LA(2);

            if ( ((((partCtx)&&(partCtx))&&synpred12_Gaussian09Lexer())) ) {
                alt18=12;
            }
            else if ( (((synpred25_Gaussian09Lexer()&&synpred25_Gaussian09Lexer())&&(termCtx))) ) {
                alt18=25;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 9, input);

                throw nvae;

            }
        }
        else if ( (LA18_0=='J') ) {
            int LA18_10 = input.LA(2);

            if ( (synpred16_Gaussian09Lexer()) ) {
                alt18=16;
            }
            else if ( (((synpred25_Gaussian09Lexer()&&synpred25_Gaussian09Lexer())&&(termCtx))) ) {
                alt18=25;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 10, input);

                throw nvae;

            }
        }
        else if ( (LA18_0=='d') ) {
            int LA18_11 = input.LA(2);

            if ( (synpred17_Gaussian09Lexer()) ) {
                alt18=17;
            }
            else if ( (((synpred25_Gaussian09Lexer()&&synpred25_Gaussian09Lexer())&&(termCtx))) ) {
                alt18=25;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 11, input);

                throw nvae;

            }
        }
        else if ( (LA18_0=='h') ) {
            int LA18_12 = input.LA(2);

            if ( (synpred18_Gaussian09Lexer()) ) {
                alt18=18;
            }
            else if ( (((synpred25_Gaussian09Lexer()&&synpred25_Gaussian09Lexer())&&(termCtx))) ) {
                alt18=25;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 12, input);

                throw nvae;

            }
        }
        else if ( (LA18_0=='m') ) {
            int LA18_13 = input.LA(2);

            if ( (synpred19_Gaussian09Lexer()) ) {
                alt18=19;
            }
            else if ( (((synpred25_Gaussian09Lexer()&&synpred25_Gaussian09Lexer())&&(termCtx))) ) {
                alt18=25;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 13, input);

                throw nvae;

            }
        }
        else if ( (LA18_0=='s') ) {
            int LA18_14 = input.LA(2);

            if ( (synpred20_Gaussian09Lexer()) ) {
                alt18=20;
            }
            else if ( (((synpred25_Gaussian09Lexer()&&synpred25_Gaussian09Lexer())&&(termCtx))) ) {
                alt18=25;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 14, input);

                throw nvae;

            }
        }
        else if ( ((LA18_0 >= 'A' && LA18_0 <= 'E')||(LA18_0 >= 'G' && LA18_0 <= 'I')||(LA18_0 >= 'K' && LA18_0 <= 'L')||(LA18_0 >= 'O' && LA18_0 <= 'P')||(LA18_0 >= 'U' && LA18_0 <= 'Z')||LA18_0=='_'||(LA18_0 >= 'a' && LA18_0 <= 'c')||(LA18_0 >= 'e' && LA18_0 <= 'g')||(LA18_0 >= 'i' && LA18_0 <= 'l')||(LA18_0 >= 'n' && LA18_0 <= 'r')||(LA18_0 >= 't' && LA18_0 <= 'z')) && ((termCtx))) {
            alt18=25;
        }
        else if ( (LA18_0=='.') && ((termCtx))) {
            alt18=26;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 18, 0, input);

            throw nvae;

        }
        switch (alt18) {
            case 1 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:41: MULTTAG
                {
                mMULTTAG(); if (state.failed) return ;


                }
                break;
            case 2 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:49: MULT
                {
                mMULT(); if (state.failed) return ;


                }
                break;
            case 3 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:54: NATOMSTAG
                {
                mNATOMSTAG(); if (state.failed) return ;


                }
                break;
            case 4 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:64: NATOMS
                {
                mNATOMS(); if (state.failed) return ;


                }
                break;
            case 5 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:71: NACTIVE
                {
                mNACTIVE(); if (state.failed) return ;


                }
                break;
            case 6 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:79: SCFTAG
                {
                mSCFTAG(); if (state.failed) return ;


                }
                break;
            case 7 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:86: ELECENG
                {
                mELECENG(); if (state.failed) return ;


                }
                break;
            case 8 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:94: FREQTAG
                {
                mFREQTAG(); if (state.failed) return ;


                }
                break;
            case 9 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:102: FREQVAL
                {
                mFREQVAL(); if (state.failed) return ;


                }
                break;
            case 10 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:110: REDMASS
                {
                mREDMASS(); if (state.failed) return ;


                }
                break;
            case 11 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:118: PARTITIONTAG
                {
                mPARTITIONTAG(); if (state.failed) return ;


                }
                break;
            case 12 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:131: TRANSTAG
                {
                mTRANSTAG(); if (state.failed) return ;


                }
                break;
            case 13 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:140: TRANSPART
                {
                mTRANSPART(); if (state.failed) return ;


                }
                break;
            case 14 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:150: ROTTAG
                {
                mROTTAG(); if (state.failed) return ;


                }
                break;
            case 15 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:157: ROTPART
                {
                mROTPART(); if (state.failed) return ;


                }
                break;
            case 16 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:165: CPUTAG
                {
                mCPUTAG(); if (state.failed) return ;


                }
                break;
            case 17 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:172: CPUDAYS
                {
                mCPUDAYS(); if (state.failed) return ;


                }
                break;
            case 18 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:180: CPUHOURS
                {
                mCPUHOURS(); if (state.failed) return ;


                }
                break;
            case 19 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:189: CPUMINS
                {
                mCPUMINS(); if (state.failed) return ;


                }
                break;
            case 20 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:197: CPUSECS
                {
                mCPUSECS(); if (state.failed) return ;


                }
                break;
            case 21 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:205: CPUFLOAT
                {
                mCPUFLOAT(); if (state.failed) return ;


                }
                break;
            case 22 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:214: CPUINT
                {
                mCPUINT(); if (state.failed) return ;


                }
                break;
            case 23 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:221: TERMTAG
                {
                mTERMTAG(); if (state.failed) return ;


                }
                break;
            case 24 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:229: TERMINT
                {
                mTERMINT(); if (state.failed) return ;


                }
                break;
            case 25 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:237: TERMDATE
                {
                mTERMDATE(); if (state.failed) return ;


                }
                break;
            case 26 :
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:246: TERMEND
                {
                mTERMEND(); if (state.failed) return ;


                }
                break;

        }

    }

    // $ANTLR start synpred1_Gaussian09Lexer
    public final void synpred1_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:41: ( MULTTAG )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:41: MULTTAG
        {
        mMULTTAG(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred1_Gaussian09Lexer

    // $ANTLR start synpred2_Gaussian09Lexer
    public final void synpred2_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:49: ( MULT )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:49: MULT
        {
        mMULT(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred2_Gaussian09Lexer

    // $ANTLR start synpred3_Gaussian09Lexer
    public final void synpred3_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:54: ( NATOMSTAG )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:54: NATOMSTAG
        {
        mNATOMSTAG(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred3_Gaussian09Lexer

    // $ANTLR start synpred4_Gaussian09Lexer
    public final void synpred4_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:64: ( NATOMS )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:64: NATOMS
        {
        mNATOMS(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred4_Gaussian09Lexer

    // $ANTLR start synpred5_Gaussian09Lexer
    public final void synpred5_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:71: ( NACTIVE )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:71: NACTIVE
        {
        mNACTIVE(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred5_Gaussian09Lexer

    // $ANTLR start synpred6_Gaussian09Lexer
    public final void synpred6_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:79: ( SCFTAG )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:79: SCFTAG
        {
        mSCFTAG(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred6_Gaussian09Lexer

    // $ANTLR start synpred7_Gaussian09Lexer
    public final void synpred7_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:86: ( ELECENG )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:86: ELECENG
        {
        mELECENG(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred7_Gaussian09Lexer

    // $ANTLR start synpred8_Gaussian09Lexer
    public final void synpred8_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:94: ( FREQTAG )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:94: FREQTAG
        {
        mFREQTAG(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred8_Gaussian09Lexer

    // $ANTLR start synpred9_Gaussian09Lexer
    public final void synpred9_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:102: ( FREQVAL )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:102: FREQVAL
        {
        mFREQVAL(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred9_Gaussian09Lexer

    // $ANTLR start synpred10_Gaussian09Lexer
    public final void synpred10_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:110: ( REDMASS )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:110: REDMASS
        {
        mREDMASS(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred10_Gaussian09Lexer

    // $ANTLR start synpred11_Gaussian09Lexer
    public final void synpred11_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:118: ( PARTITIONTAG )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:118: PARTITIONTAG
        {
        mPARTITIONTAG(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred11_Gaussian09Lexer

    // $ANTLR start synpred12_Gaussian09Lexer
    public final void synpred12_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:131: ( TRANSTAG )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:131: TRANSTAG
        {
        mTRANSTAG(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred12_Gaussian09Lexer

    // $ANTLR start synpred13_Gaussian09Lexer
    public final void synpred13_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:140: ( TRANSPART )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:140: TRANSPART
        {
        mTRANSPART(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred13_Gaussian09Lexer

    // $ANTLR start synpred14_Gaussian09Lexer
    public final void synpred14_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:150: ( ROTTAG )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:150: ROTTAG
        {
        mROTTAG(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred14_Gaussian09Lexer

    // $ANTLR start synpred15_Gaussian09Lexer
    public final void synpred15_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:157: ( ROTPART )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:157: ROTPART
        {
        mROTPART(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred15_Gaussian09Lexer

    // $ANTLR start synpred16_Gaussian09Lexer
    public final void synpred16_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:165: ( CPUTAG )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:165: CPUTAG
        {
        mCPUTAG(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred16_Gaussian09Lexer

    // $ANTLR start synpred17_Gaussian09Lexer
    public final void synpred17_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:172: ( CPUDAYS )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:172: CPUDAYS
        {
        mCPUDAYS(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred17_Gaussian09Lexer

    // $ANTLR start synpred18_Gaussian09Lexer
    public final void synpred18_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:180: ( CPUHOURS )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:180: CPUHOURS
        {
        mCPUHOURS(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred18_Gaussian09Lexer

    // $ANTLR start synpred19_Gaussian09Lexer
    public final void synpred19_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:189: ( CPUMINS )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:189: CPUMINS
        {
        mCPUMINS(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred19_Gaussian09Lexer

    // $ANTLR start synpred20_Gaussian09Lexer
    public final void synpred20_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:197: ( CPUSECS )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:197: CPUSECS
        {
        mCPUSECS(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred20_Gaussian09Lexer

    // $ANTLR start synpred21_Gaussian09Lexer
    public final void synpred21_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:205: ( CPUFLOAT )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:205: CPUFLOAT
        {
        mCPUFLOAT(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred21_Gaussian09Lexer

    // $ANTLR start synpred22_Gaussian09Lexer
    public final void synpred22_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:214: ( CPUINT )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:214: CPUINT
        {
        mCPUINT(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred22_Gaussian09Lexer

    // $ANTLR start synpred23_Gaussian09Lexer
    public final void synpred23_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:221: ( TERMTAG )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:221: TERMTAG
        {
        mTERMTAG(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred23_Gaussian09Lexer

    // $ANTLR start synpred24_Gaussian09Lexer
    public final void synpred24_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:229: ( TERMINT )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:229: TERMINT
        {
        mTERMINT(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred24_Gaussian09Lexer

    // $ANTLR start synpred25_Gaussian09Lexer
    public final void synpred25_Gaussian09Lexer_fragment() throws RecognitionException {
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:237: ( TERMDATE )
        // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Lexer.g:1:237: TERMDATE
        {
        mTERMDATE(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred25_Gaussian09Lexer

    public final boolean synpred17_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred17_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred21_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred21_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred24_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred24_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred22_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred22_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred19_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred25_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred25_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred23_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred23_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_Gaussian09Lexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_Gaussian09Lexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

}