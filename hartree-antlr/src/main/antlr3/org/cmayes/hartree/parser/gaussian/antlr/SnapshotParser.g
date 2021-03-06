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

xyzAtoms
  :
  XYZINT XYZINT XYZINT XYZFLOAT XYZFLOAT XYZFLOAT
  ;

script
  :
  (FUNCSET? (CHARGE|MULT)+ (NATOMS|xyzAtoms|ELECENG|STOI|DIPTOT|BSSE|SOLVENT)+ (FREQVAL+ | (cputime term) | ZPECORR))+ ZPECORR? H298? G298? cputime? term? EOF
  ;
