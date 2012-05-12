parser grammar SnapshotParser;

options {
  language   = Java;
  output     = AST;
  tokenVocab = SnapshotLexer;
}

@header {
package org.cmayes.hartree.parser.gaussian.antlr;
}

// These rules pull data out of the token stream and create an AST using the format at the end of the line.

//def
//  :
//  DEFOPEN DEFCLOSE
//    ->
//      ^(DEFDATA )
//  ;

cputime
  :
  CPUTAG d=CPUINT CPUDAYS h=CPUINT CPUHOURS m=CPUINT CPUMINS s=CPUFLOAT CPUSECS
    ->
      ^(CPUTIME $d $h $m $s)
  ;

term
  :
  TERMTAG d=TERMDATE TERMEND
    ->
      ^(TERM $d)
  ;

script
  :
  (FUNCSET? SOLVENT? CHARGE MULT (ELECENG|STOI|DIPTOT)+  (FREQVAL+ | (cputime term)))+ ZPECORR? cputime term EOF
  ;
