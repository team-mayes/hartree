lexer grammar Gaussian09Lexer;

// Setting filter to true drops input that doesn't match our rules.
options {
  language = Java;
  filter=true;
}

tokens { TERM; CPUTIME; XYZATOM;
}

@header {
  package org.cmayes.hartree.parser.gaussian.antlr;
}

// This is Java-specific; these are context flags to avoid matching on unwanted data. 
@members {
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
    boolean xyzCtx = false; 
}

// Multiplicity
MULTTAG: 'Multiplicity' { multCtx = true; $channel = HIDDEN; };
MULT: {multCtx}? => INT { multCtx = false; };

// XYZ coordinates
XYZTAG: 'Input orientation:' { xyzCtx = true; $channel = HIDDEN; };
XYZFLOAT: {xyzCtx}? FLOAT ;
XYZINT: {xyzCtx}? INT ;
XYZEND: 'Distance matrix (angstroms):' { xyzCtx = false; $channel = HIDDEN; };

// Number of atoms
NATOMSTAG: {!natomsFound}? => 'NAtoms' { natomsCtx = true; $channel = HIDDEN; };
NATOMS: {(natomsCtx && (!natomsFound))}? => INT { natomsFound = true; };
NACTIVE: {natomsCtx}? => 'NActive'{ natomsCtx = false; $channel = HIDDEN; };

SCFTAG: 'SCF Done' { elecEngCtx = true; $channel = HIDDEN; };
ELECENG: {elecEngCtx}? => FLOAT { elecEngCtx = false; };

FREQTAG: 'Frequencies' { freqCtx = true; $channel = HIDDEN; } ;
FREQVAL: {freqCtx}? => FLOAT ;
REDMASS: {freqCtx}? => 'Red. masses' { freqCtx = false; $channel = HIDDEN;} ; 

// Find asymmetric top, if it exists
ASYM: 'asymmetric' ;

// Partition
PARTITIONTAG: 'Q' WS+ 'Log10(Q)' WS+ 'Ln(Q)' { partCtx = true; $channel = HIDDEN; } ;
TRANSTAG: {partCtx}? => 'Translational' { transCtx = true; $channel = HIDDEN; } ;
TRANSPART: {transCtx}? => FLOAT { transCtx = false; } ;
ROTTAG: {partCtx}? => 'Rotational' { rotCtx = true; partCtx = false; $channel = HIDDEN; } ;
ROTPART: {rotCtx}? => FLOAT { rotCtx = false; } ;

// CPU time
CPUTAG: 'Job cpu time:' { cpuCtx = true; } ;
CPUDAYS: 'days' ;
CPUHOURS: 'hours' ;
CPUMINS: 'minutes' ;
CPUSECS: 'seconds.' { cpuCtx = false; } ;
CPUFLOAT: {cpuCtx}? => FLOAT ;
CPUINT: {cpuCtx}? => INT ;

// Termination date
TERMTAG: 'Normal termination of Gaussian 09 at' { termCtx = true; } ;
TERMINT: {termCtx}? => INT ;
TERMDATE: {termCtx}? => DATE ;
TERMEND: {termCtx}? => '.' { termCtx = false; natomsFound = false; } ;



fragment FLOAT: ('-')? ('0'..'9')+ '.' ('0'..'9')+ (('e'|'E'|'D'|'d') ('+'|'-')? ('0'..'9')+)? ;
fragment INT: ('-')? '0'..'9'+ ;
fragment ANUM: ('0'..'9' | 'A'..'Z' | 'a'..'z')+ ;
fragment DATE: LETTER+ WS+ LETTER+ (WS | ':' | INT)+ ;
fragment LETTER: ('a'..'z' | 'A'..'Z' | '_') ;
fragment WORD: LETTER+ ;
fragment WS: (' ' | '\t' | '\n' | '\r' | '\f')+ ;
