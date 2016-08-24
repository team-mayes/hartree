# Hartree: Tools for Processing Computational Chemistry Data

I wrote Hartree to help my wife with her processing of Gaussian 09 log files.

## Build

The project uses [Maven](https://maven.apache.org/ "Maven") as its build tool and
has [cmayes-common](https://github.com/cmayes/cmayes-common) as a dependency.  
Since cmayes-common is not yet in Maven Central, you will need to build and install
this library locally.

### Installing Maven

Maven is a Java library and can be unpacked in a local directory and added to your classpath.  For example:

	$ cd ~/apps
	$ wget http://www-eu.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
	$ tar zxvf apache-maven-3.3.9-bin.tar.gz
	$ ln -s apache-maven-3.3.9 maven
	$ echo 'export PATH=$PATH:$HOME/apps/maven/bin' >> ~/.bashrc
	$ . ~/.bashrc
	$ mvn --version

The last command should produce an output starting with something similar to `Apache Maven 3.3.9`.

### Installing cmayes-common

This library also uses Maven, so be sure that it's installed as per the previous section.

	$ cd ~/code
	$ git clone https://github.com/cmayes/cmayes-common
	$ cd cmayes-common
	$ git checkout cmayes-common-1.1.5
	$ mvn clean install

This will pull down the library's dependencies, run the build for the 1.1.5 release, and install
the release's JAR in your local Maven repository (usually `~/.m2/repository`).

### Building Hartree

Once you have Maven and cmayes-common installed, you can build Hartree.

        $ cd ~/code
        $ git clone https://github.com/team-mayes/hartree
        $ cd hartree
        $ git checkout hartree-1.1.8
        $ mvn clean package

Note that you should check out the tag that corresponds to the release that you wish to build.

The artifact at `hartree-cli/target/hartree-cli-1.1.8.jar` (or whichever version you've built)
is what you'll use.

## Usage

These usage instructions are from notes that I gave to my wife.

The basic usage is:

	$ java -jar hartree-cli-1.1.8.jar
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

	$ java -jar hartree-cli-1.1.8.jar norm
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

	$ java -jar hartree-cli-1.1.8.jar norm -f glucose5m062xEtOHnorm.log
	Normal mode summary for file glucose5m062xEtOHnorm.log

	Highest DoF percentages by dihedral:
	...

The report is dumped to standard out.  We could redirect this to a
file, but it's just as easy to point Hartree to an output directory:

	$ java -jar hartree-cli-1.1.8.jar norm -f glucose5m062xEtOHnorm.log -o out
	05:19:29.377 [main] DEBUG org.cmayes.hartree.Main - Created out dir
	/home/cmayes/Downloads/out

This does not produce any output aside from
a debug message about creating the out dir (it won't write that
message if the dir exists).  In the output dir, we have one file:

	$ ls out
	glucose5m062xEtOHnorm.log-norm.txt

This is the normal mode report.  It's named for the log file with "-norm.txt" at the end.

Now, let's try processing a whole directory at once:

	$ java -jar hartree-cli-1.1.8.jar norm -d
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
