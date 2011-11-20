// $ANTLR 3.4 /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g 2011-11-20 11:13:12

package org.cmayes.hartree.parser.gaussian;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class Gaussian09Parser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ANUM", "CPUDAYS", "CPUFLOAT", "CPUHOURS", "CPUINT", "CPUMINS", "CPUSECS", "CPUTAG", "CPUTIME", "DATE", "ELECENG", "FLOAT", "FREQTAG", "FREQVAL", "INT", "LETTER", "MULT", "MULTTAG", "NACTIVE", "NATOMS", "NATOMSTAG", "PARTITIONTAG", "REDMASS", "ROTPART", "ROTTAG", "SCFTAG", "TERM", "TERMDATE", "TERMEND", "TERMINT", "TERMTAG", "TRANSPART", "TRANSTAG", "WORD", "WS"
    };

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

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public Gaussian09Parser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public Gaussian09Parser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return Gaussian09Parser.tokenNames; }
    public String getGrammarFileName() { return "/home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g"; }


    public static class cputime_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "cputime"
    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:15:1: cputime : CPUTAG d= CPUINT CPUDAYS h= CPUINT CPUHOURS m= CPUINT CPUMINS s= CPUFLOAT CPUSECS -> ^( CPUTIME $d $h $m $s) ;
    public final Gaussian09Parser.cputime_return cputime() throws RecognitionException {
        Gaussian09Parser.cputime_return retval = new Gaussian09Parser.cputime_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token d=null;
        Token h=null;
        Token m=null;
        Token s=null;
        Token CPUTAG1=null;
        Token CPUDAYS2=null;
        Token CPUHOURS3=null;
        Token CPUMINS4=null;
        Token CPUSECS5=null;

        Object d_tree=null;
        Object h_tree=null;
        Object m_tree=null;
        Object s_tree=null;
        Object CPUTAG1_tree=null;
        Object CPUDAYS2_tree=null;
        Object CPUHOURS3_tree=null;
        Object CPUMINS4_tree=null;
        Object CPUSECS5_tree=null;
        RewriteRuleTokenStream stream_CPUINT=new RewriteRuleTokenStream(adaptor,"token CPUINT");
        RewriteRuleTokenStream stream_CPUFLOAT=new RewriteRuleTokenStream(adaptor,"token CPUFLOAT");
        RewriteRuleTokenStream stream_CPUMINS=new RewriteRuleTokenStream(adaptor,"token CPUMINS");
        RewriteRuleTokenStream stream_CPUTAG=new RewriteRuleTokenStream(adaptor,"token CPUTAG");
        RewriteRuleTokenStream stream_CPUDAYS=new RewriteRuleTokenStream(adaptor,"token CPUDAYS");
        RewriteRuleTokenStream stream_CPUHOURS=new RewriteRuleTokenStream(adaptor,"token CPUHOURS");
        RewriteRuleTokenStream stream_CPUSECS=new RewriteRuleTokenStream(adaptor,"token CPUSECS");

        try {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:16:3: ( CPUTAG d= CPUINT CPUDAYS h= CPUINT CPUHOURS m= CPUINT CPUMINS s= CPUFLOAT CPUSECS -> ^( CPUTIME $d $h $m $s) )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:17:3: CPUTAG d= CPUINT CPUDAYS h= CPUINT CPUHOURS m= CPUINT CPUMINS s= CPUFLOAT CPUSECS
            {
            CPUTAG1=(Token)match(input,CPUTAG,FOLLOW_CPUTAG_in_cputime62);  
            stream_CPUTAG.add(CPUTAG1);


            d=(Token)match(input,CPUINT,FOLLOW_CPUINT_in_cputime66);  
            stream_CPUINT.add(d);


            CPUDAYS2=(Token)match(input,CPUDAYS,FOLLOW_CPUDAYS_in_cputime68);  
            stream_CPUDAYS.add(CPUDAYS2);


            h=(Token)match(input,CPUINT,FOLLOW_CPUINT_in_cputime72);  
            stream_CPUINT.add(h);


            CPUHOURS3=(Token)match(input,CPUHOURS,FOLLOW_CPUHOURS_in_cputime74);  
            stream_CPUHOURS.add(CPUHOURS3);


            m=(Token)match(input,CPUINT,FOLLOW_CPUINT_in_cputime78);  
            stream_CPUINT.add(m);


            CPUMINS4=(Token)match(input,CPUMINS,FOLLOW_CPUMINS_in_cputime80);  
            stream_CPUMINS.add(CPUMINS4);


            s=(Token)match(input,CPUFLOAT,FOLLOW_CPUFLOAT_in_cputime84);  
            stream_CPUFLOAT.add(s);


            CPUSECS5=(Token)match(input,CPUSECS,FOLLOW_CPUSECS_in_cputime86);  
            stream_CPUSECS.add(CPUSECS5);


            // AST REWRITE
            // elements: d, h, m, s
            // token labels: d, s, m, h
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_d=new RewriteRuleTokenStream(adaptor,"token d",d);
            RewriteRuleTokenStream stream_s=new RewriteRuleTokenStream(adaptor,"token s",s);
            RewriteRuleTokenStream stream_m=new RewriteRuleTokenStream(adaptor,"token m",m);
            RewriteRuleTokenStream stream_h=new RewriteRuleTokenStream(adaptor,"token h",h);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 18:5: -> ^( CPUTIME $d $h $m $s)
            {
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:19:7: ^( CPUTIME $d $h $m $s)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(CPUTIME, "CPUTIME")
                , root_1);

                adaptor.addChild(root_1, stream_d.nextNode());

                adaptor.addChild(root_1, stream_h.nextNode());

                adaptor.addChild(root_1, stream_m.nextNode());

                adaptor.addChild(root_1, stream_s.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "cputime"


    public static class term_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "term"
    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:22:1: term : TERMTAG d= TERMDATE TERMEND -> ^( TERM $d) ;
    public final Gaussian09Parser.term_return term() throws RecognitionException {
        Gaussian09Parser.term_return retval = new Gaussian09Parser.term_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token d=null;
        Token TERMTAG6=null;
        Token TERMEND7=null;

        Object d_tree=null;
        Object TERMTAG6_tree=null;
        Object TERMEND7_tree=null;
        RewriteRuleTokenStream stream_TERMEND=new RewriteRuleTokenStream(adaptor,"token TERMEND");
        RewriteRuleTokenStream stream_TERMDATE=new RewriteRuleTokenStream(adaptor,"token TERMDATE");
        RewriteRuleTokenStream stream_TERMTAG=new RewriteRuleTokenStream(adaptor,"token TERMTAG");

        try {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:23:3: ( TERMTAG d= TERMDATE TERMEND -> ^( TERM $d) )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:24:3: TERMTAG d= TERMDATE TERMEND
            {
            TERMTAG6=(Token)match(input,TERMTAG,FOLLOW_TERMTAG_in_term129);  
            stream_TERMTAG.add(TERMTAG6);


            d=(Token)match(input,TERMDATE,FOLLOW_TERMDATE_in_term133);  
            stream_TERMDATE.add(d);


            TERMEND7=(Token)match(input,TERMEND,FOLLOW_TERMEND_in_term135);  
            stream_TERMEND.add(TERMEND7);


            // AST REWRITE
            // elements: d
            // token labels: d
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_d=new RewriteRuleTokenStream(adaptor,"token d",d);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 25:5: -> ^( TERM $d)
            {
                // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:26:7: ^( TERM $d)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(TERM, "TERM")
                , root_1);

                adaptor.addChild(root_1, stream_d.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "term"


    public static class script_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "script"
    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:29:1: script : ( MULT NATOMS ( ELECENG )+ ( ( FREQVAL )+ TRANSPART ROTPART )? cputime ( term )? )+ EOF ;
    public final Gaussian09Parser.script_return script() throws RecognitionException {
        Gaussian09Parser.script_return retval = new Gaussian09Parser.script_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token MULT8=null;
        Token NATOMS9=null;
        Token ELECENG10=null;
        Token FREQVAL11=null;
        Token TRANSPART12=null;
        Token ROTPART13=null;
        Token EOF16=null;
        Gaussian09Parser.cputime_return cputime14 =null;

        Gaussian09Parser.term_return term15 =null;


        Object MULT8_tree=null;
        Object NATOMS9_tree=null;
        Object ELECENG10_tree=null;
        Object FREQVAL11_tree=null;
        Object TRANSPART12_tree=null;
        Object ROTPART13_tree=null;
        Object EOF16_tree=null;

        try {
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:30:3: ( ( MULT NATOMS ( ELECENG )+ ( ( FREQVAL )+ TRANSPART ROTPART )? cputime ( term )? )+ EOF )
            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:31:3: ( MULT NATOMS ( ELECENG )+ ( ( FREQVAL )+ TRANSPART ROTPART )? cputime ( term )? )+ EOF
            {
            root_0 = (Object)adaptor.nil();


            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:31:3: ( MULT NATOMS ( ELECENG )+ ( ( FREQVAL )+ TRANSPART ROTPART )? cputime ( term )? )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==MULT) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:31:4: MULT NATOMS ( ELECENG )+ ( ( FREQVAL )+ TRANSPART ROTPART )? cputime ( term )?
            	    {
            	    MULT8=(Token)match(input,MULT,FOLLOW_MULT_in_script170); 
            	    MULT8_tree = 
            	    (Object)adaptor.create(MULT8)
            	    ;
            	    adaptor.addChild(root_0, MULT8_tree);


            	    NATOMS9=(Token)match(input,NATOMS,FOLLOW_NATOMS_in_script172); 
            	    NATOMS9_tree = 
            	    (Object)adaptor.create(NATOMS9)
            	    ;
            	    adaptor.addChild(root_0, NATOMS9_tree);


            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:31:16: ( ELECENG )+
            	    int cnt1=0;
            	    loop1:
            	    do {
            	        int alt1=2;
            	        int LA1_0 = input.LA(1);

            	        if ( (LA1_0==ELECENG) ) {
            	            alt1=1;
            	        }


            	        switch (alt1) {
            	    	case 1 :
            	    	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:31:16: ELECENG
            	    	    {
            	    	    ELECENG10=(Token)match(input,ELECENG,FOLLOW_ELECENG_in_script174); 
            	    	    ELECENG10_tree = 
            	    	    (Object)adaptor.create(ELECENG10)
            	    	    ;
            	    	    adaptor.addChild(root_0, ELECENG10_tree);


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt1 >= 1 ) break loop1;
            	                EarlyExitException eee =
            	                    new EarlyExitException(1, input);
            	                throw eee;
            	        }
            	        cnt1++;
            	    } while (true);


            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:31:25: ( ( FREQVAL )+ TRANSPART ROTPART )?
            	    int alt3=2;
            	    int LA3_0 = input.LA(1);

            	    if ( (LA3_0==FREQVAL) ) {
            	        alt3=1;
            	    }
            	    switch (alt3) {
            	        case 1 :
            	            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:31:26: ( FREQVAL )+ TRANSPART ROTPART
            	            {
            	            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:31:26: ( FREQVAL )+
            	            int cnt2=0;
            	            loop2:
            	            do {
            	                int alt2=2;
            	                int LA2_0 = input.LA(1);

            	                if ( (LA2_0==FREQVAL) ) {
            	                    alt2=1;
            	                }


            	                switch (alt2) {
            	            	case 1 :
            	            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:31:26: FREQVAL
            	            	    {
            	            	    FREQVAL11=(Token)match(input,FREQVAL,FOLLOW_FREQVAL_in_script178); 
            	            	    FREQVAL11_tree = 
            	            	    (Object)adaptor.create(FREQVAL11)
            	            	    ;
            	            	    adaptor.addChild(root_0, FREQVAL11_tree);


            	            	    }
            	            	    break;

            	            	default :
            	            	    if ( cnt2 >= 1 ) break loop2;
            	                        EarlyExitException eee =
            	                            new EarlyExitException(2, input);
            	                        throw eee;
            	                }
            	                cnt2++;
            	            } while (true);


            	            TRANSPART12=(Token)match(input,TRANSPART,FOLLOW_TRANSPART_in_script181); 
            	            TRANSPART12_tree = 
            	            (Object)adaptor.create(TRANSPART12)
            	            ;
            	            adaptor.addChild(root_0, TRANSPART12_tree);


            	            ROTPART13=(Token)match(input,ROTPART,FOLLOW_ROTPART_in_script183); 
            	            ROTPART13_tree = 
            	            (Object)adaptor.create(ROTPART13)
            	            ;
            	            adaptor.addChild(root_0, ROTPART13_tree);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_cputime_in_script187);
            	    cputime14=cputime();

            	    state._fsp--;

            	    adaptor.addChild(root_0, cputime14.getTree());

            	    // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:31:63: ( term )?
            	    int alt4=2;
            	    int LA4_0 = input.LA(1);

            	    if ( (LA4_0==TERMTAG) ) {
            	        alt4=1;
            	    }
            	    switch (alt4) {
            	        case 1 :
            	            // /home/cmayes/code/java/projects/hartree/src/main/antlr3/org/cmayes/hartree/parser/gaussian/Gaussian09Parser.g:31:63: term
            	            {
            	            pushFollow(FOLLOW_term_in_script189);
            	            term15=term();

            	            state._fsp--;

            	            adaptor.addChild(root_0, term15.getTree());

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            EOF16=(Token)match(input,EOF,FOLLOW_EOF_in_script194); 
            EOF16_tree = 
            (Object)adaptor.create(EOF16)
            ;
            adaptor.addChild(root_0, EOF16_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "script"

    // Delegated rules


 

    public static final BitSet FOLLOW_CPUTAG_in_cputime62 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_CPUINT_in_cputime66 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_CPUDAYS_in_cputime68 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_CPUINT_in_cputime72 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_CPUHOURS_in_cputime74 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_CPUINT_in_cputime78 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_CPUMINS_in_cputime80 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_CPUFLOAT_in_cputime84 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_CPUSECS_in_cputime86 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TERMTAG_in_term129 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_TERMDATE_in_term133 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_TERMEND_in_term135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MULT_in_script170 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NATOMS_in_script172 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ELECENG_in_script174 = new BitSet(new long[]{0x0000000000024800L});
    public static final BitSet FOLLOW_FREQVAL_in_script178 = new BitSet(new long[]{0x0000000800020000L});
    public static final BitSet FOLLOW_TRANSPART_in_script181 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ROTPART_in_script183 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_cputime_in_script187 = new BitSet(new long[]{0x0000000400100000L});
    public static final BitSet FOLLOW_term_in_script189 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_EOF_in_script194 = new BitSet(new long[]{0x0000000000000002L});

}