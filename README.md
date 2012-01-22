# Hartree: Tools for Processing Computational Chemistry Data

I wrote Hartree to help my wife with her processing of Gaussian 09 log files.

## Usage

These usage instructions are from notes that I gave to my wife.

The basic usage is:

	$ java -jar hartree-1.0.2.one-jar.jar
	Argument not one of (norm,therm,test)
	java org.cmayes.hartree.Main [options...] (norm,therm,test)
	Available arguments:
	norm  : Handles normal mode evaluation   
	therm : Handles thermo calculations      
	test  : Test mode (no handling performed)
	Available options:
	 --directory (-d) INDIR : The base directory of the files to process
	 --file (-f) INFILE     : The file to process
	 --outdir (-o) OUTDIR   : The output directory for result files

This tells Java to run the main class in the JAR.  The output is a
usage message because we have not specified an operation.  Normal mode
is the only one that works right now, so let's try that:

	$ java -jar hartree-1.0.2.one-jar.jar norm
	Argument not one of (norm,therm,test)
	java org.cmayes.hartree.Main [options...] (norm,therm,test)
	Available arguments:
	norm  : Handles normal mode evaluation   
	therm : Handles thermo calculations      
	test  : Test mode (no handling performed)
	Available options:
	 --directory (-d) INDIR : The base directory of the files to process
	 --file (-f) INFILE     : The file to process
	 --outdir (-o) OUTDIR   : The output directory for result files

We need to specify a source to process.  Let's do a single file first.

	$ java -jar hartree-1.0.2.one-jar.jar norm -f glucose5m062xEtOHnorm.log 
	Normal mode summary for file glucose5m062xEtOHnorm.log
	
	Highest DoF percentages by dihedral:
	...

The report is dumped to standard out.  We could redirect this to a
file, but it's just as easy to point Hartree to an output directory:

	$ java -jar hartree-1.0.2.one-jar.jar norm -f glucose5m062xEtOHnorm.log -o out
	05:19:29.377 [main] DEBUG org.cmayes.hartree.Main - Created out dir
	/home/cmayes/Downloads/out

This does not produce any output aside from
a debug message about creating the out dir (it won't write that
message if the dir exists).  In the output dir, we have one file:

	$ ls out
	glucose5m062xEtOHnorm.log-norm.txt

This is the normal mode report.  It's named for the log file with "-norm.txt" at the end.

Now, let's try processing a whole directory at once:

	$ java -jar hartree-1.0.2.one-jar.jar norm -d
	~/g09/ -o out
	line 741:11 mismatched input '45' expecting CPUTAG
	...

This generates a lot of noise because most of my test files do not
have normal mode data.  The result in the out dir:

	$ ls out
	GL_THF_rev4.log-norm.txt            init_gas_rad2_3.out-norm.txt
	levoglucosan8spTooLittleData.log-norm.txt
	m-glucose_waterm062xspTooLittleData.out-norm.txt
	glucose5m062xEtOHnorm.log-norm.txt  init_THF+negFAIL.log-norm.txt
	methanolmp4mp2TooLittleData.log-norm.txt
	init2thfTS+camAgain3.log-norm.txt   init_water+pos4.log-norm.txt
	m-glucose_gasm062x.out-norm.txt

It's processed all of the input files in the same way, but since most
of the input files didn't have any normal mode data, they look like
this:

	$ cat out/GL_THF_rev4.log-norm.txt
	Normal mode summary for file GL_THF_rev4.log

	No normal mode data found.

It should handle nested directories such that input files in
sub-directories are written to the same sub-directory in the output
directory.