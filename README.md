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
	$ wget http://www-eu.apache.org/dist/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.tar.gz
	$ tar zxvf apache-maven-3.6.0-bin.tar.gz
	$ ln -s apache-maven-3.6.0 maven
	$ echo 'export PATH=$PATH:$HOME/apps/maven/bin' >> ~/.bashrc
	$ . ~/.bashrc
	$ mvn --version

The last command should produce an output starting with something similar to `Apache Maven 3.6.0`.

### Installing cmayes-common

This library also uses Maven, so be sure that it's installed as per the previous section.

	$ cd ~/code
	$ git clone https://github.com/cmayes/cmayes-common
	$ cd cmayes-common
	$ git checkout cmayes-common-1.1.7
	$ mvn clean install

This will pull down the library's dependencies, run the build for the 1.1.7 release, and install
the release's JAR in your local Maven repository (usually `~/.m2/repository`).

### Building Hartree

Once you have Maven and cmayes-common installed, you can build Hartree.

        $ cd ~/code
        $ git clone https://github.com/team-mayes/hartree
        $ cd hartree
        $ git checkout hartree-1.2.5
        $ mvn clean package

Note that you should check out the tag that corresponds to the release that you wish to build.

The artifact at `hartree-cli/target/hartree-cli-1.2.5.jar` (or whichever version you've built)
is what you'll use.

## Usage

The basic usage is:

	$ java -jar hartree-cli-1.2.5.jar
	Argument not one of (norm,snap,cpsnap,test,therm,lowen)
        java org.cmayes.hartree.Main [options...] (norm,snap,cpsnap,test,therm,lowen)
        Available arguments:
        norm   : Handles normal mode evaluation                                            
        snap   : Provides a snapshot of calculation data                                   
        cpsnap : Provides a snapshot of calculation data including Cremer-Pople coordinates
        test   : Test mode (no handling performed)                                         
        therm  : Handles thermo calculations                                               
        lowen  : creates Gaussian input files for the lowest energy in the input files     
        Available options:
         --cfgfile (-c) CFG     : Configuration settings file (required for DB inserts)
         --directory (-d) INDIR : The base directory of the files to process
         --extensions (-e) EXTS : Extensions to include in input directory searches
                                  (.log and .out by default)
         --file (-f) INFILE     : The file to process
         --ion (-i) ION         : The ion element type to use.
         --mediatype (-m) MEDIA : The media type to use instead of the default.
         --outdir (-o) OUTDIR   : The output directory for result files
         --proctype (-p) PROC   : The processor type to use instead of the default.
         --projname (-n) PROJ   : The name of this data's project (required for DB
                                  inserts)
         --tags (-t) TAGS       : Categories that describe the input data

This tells Java to run the main class in the JAR.  The output is a
usage message because we have not specified an operation. 

Let's try the normal mode option: 

	$ java -jar hartree-cli-1.2.5.jar norm
        No input file or directory specified.
        java org.cmayes.hartree.Main [options...] (norm,snap,cpsnap,test,therm,lowen)
        Available arguments:
        norm   : Handles normal mode evaluation                                            
        snap   : Provides a snapshot of calculation data                                   
        cpsnap : Provides a snapshot of calculation data including Cremer-Pople coordinates
        test   : Test mode (no handling performed)                                         
        therm  : Handles thermo calculations                                               
        lowen  : creates Gaussian input files for the lowest energy in the input files     
        Available options:
         --cfgfile (-c) CFG     : Configuration settings file (required for DB inserts)
         --directory (-d) INDIR : The base directory of the files to process
         --extensions (-e) EXTS : Extensions to include in input directory searches
                                  (.log and .out by default)
         --file (-f) INFILE     : The file to process
         --ion (-i) ION         : The ion element type to use.
         --mediatype (-m) MEDIA : The media type to use instead of the default.
         --outdir (-o) OUTDIR   : The output directory for result files
         --proctype (-p) PROC   : The processor type to use instead of the default.
         --projname (-n) PROJ   : The name of this data's project (required for DB
                                  inserts)
         --tags (-t) TAGS       : Categories that describe the input data

As the error message indicate, We need to specify a source to process.  Let's do a single file first.

	$ java -jar hartree-cli-1.2.5.jar norm -f glucose5m062xEtOHnorm.log
	Normal mode summary for file glucose5m062xEtOHnorm.log

	Highest DoF percentages by dihedral:
	...

The report is dumped to standard out.  We could redirect this to a
file, but it's just as easy to point Hartree to an output directory:

	$ java -jar hartree-cli-1.2.5.jar norm -f glucose5m062xEtOHnorm.log -o out
	05:19:29.377 [main] DEBUG org.cmayes.hartree.Main - Created out dir
	/home/cmayes/Downloads/out

This does not produce any output aside from
a debug message about creating the out dir (it won't write that
message if the dir exists).  In the output dir, we have one file:

	$ ls out
	glucose5m062xEtOHnorm.log-norm.txt

This is the normal mode report.  It's named for the log file with "-norm.txt" at the end.

Now, let's try processing a whole directory at once:

	$ java -jar hartree-cli-1.2.5.jar norm -d ~/g09/ -o out
	line 741:11 mismatched input '45' expecting CPUTAG
	...

This generates a lot of noise because most of my test files do not
have normal mode data.  The result in the out dir:

	$ ls out
        GL_THF_rev4.log-norm.txt            
        glucose5m062xEtOHnorm.log-norm.txt  
        init_gas_rad2_3.out-norm.txt
        init_THF+negFAIL.log-norm.txt
        init_water+pos4.log-norm.txt
        init2thfTS+camAgain3.log-norm.txt   
        levoglucosan8spTooLittleData.log-norm.txt
        m-glucose_gasm062x.out-norm.txt
        m-glucose_waterm062xspTooLittleData.out-norm.txt
        methanolmp4mp2TooLittleData.log-norm.txt

It's processed all of the input files in the same way, but since most
of the input files didn't have any normal mode data, they look like
this:

	$ cat out/GL_THF_rev4.log-norm.txt
	Normal mode summary for file GL_THF_rev4.log

	No normal mode data found.

It should handle nested directories such that input files in
sub-directories are written to the same sub-directory in the output
directory.

## Options

### norm

The goal of this program is to summarize output from a Gaussian run with the freq=internalmodes.

For each normal mode found, it will sum the Relative Weights (%) from Angle Bending (A), Bond Stretching (R), and 
Dihedral Angles (D). For dihedral angles, it further added together all the separate dihedral angles that share the same
two central atoms (here called a "dihedral pair"). For example, the 64 lines of Gaussian output for Normal Mode 1 
in hartree-antlr/target/test-classes/files/g09/glucose5m062xEtOHnorm.log, which includes:

                                   ----------------------------
                                   ! Normal Mode     1        !
         --------------------------                            --------------------------
         ! Name  Definition              Value          Relative Weight (%)             !
         --------------------------------------------------------------------------------
         ! A19   A(3,4,5)                0.0243                  0.6                    !
         ! D1    D(7,1,2,3)              0.0681                  1.8                    !
         ! D2    D(7,1,2,8)              0.0686                  1.8                    !
         ! D3    D(7,1,2,14)             0.0735                  2.0                    !
         ! D4    D(11,1,2,3)             0.0726                  1.9                    !
         ...
         ! D66   D(24,6,12,22)          -0.0279                  0.7                    !
         --------------------------------------------------------------------------------

The dihedral angles D1 through D9 all describe rotation around the 1-2 bond (pair) and thus are 
summed in the hartree output file. The hartree output for Normal Mode 1 summarizes the data in 14 lines as:

         === Normal mode   1 ===
         Frequency          : 75.23
         Angle Bending   (A): 0.60
         Bond Stretching (R): 0.00
         Dihedral pairs  (D):
                  (  1,   2): 17.60
                  (  2,   3): 11.00
                  (  2,   8): 5.00
                  (  3,   4): 9.30
                  (  4,   5): 25.20
                  (  4,  10): 4.40
                  (  5,   6): 9.60
                  (  5,  11): 6.40
                  (  6,  12): 2.20
         
Note that total sum of the original relative weights in the Guassian output file was 91.3%. Thus, the total
relative weights in the new summary also do not equal 100%.

### snap

This "snapshot" option summarizes salient output from the many-line Guassian output file(s) into one line per file. 
There may be multiple listings of some properties (i.e. electronic energy). The listed value will be the last 
one in the file. The column headings in the resulting csv file are:

* File Name: repeats the file name where the data was obtained
* Solvent type: if an implicit solvent was specified (i.e. water) it will be noted here. Otherwise, the program will list "N/A"
* Stoichiometry: the molecular formula for the system, i.e. C5H10O
* Charge: total system charge
* Mult: system multiplicity
* Functional: functional used. Note that this will be repeated as listed in the output, not in the input, so you may see "RM062X" instead of "m062x"
* Basis Set: basis set, if applicable ("N/A" if semiempirical)
* Energy (A.U.): Last-listed electronic energy
* dipole: total dipole moment for the system
* ZPE (Hartrees): the zero point energy; "N/A" if no calculated (no frequency calculated)
* G298 (Hartrees): Gibbs free energy at 298 K (if calculated)
* H298 (Hartrees): Enthalpy at 298 K (if calculated)
* Freq 1: Lowest frequency (wavenumber) in cm^-1; since Gaussian sorts frequencies, listing the lowest first, and since it 
  stores imaginary numbers as negative numbers, you can use this to determine if the structure is a 
  local minimum (if so, this will be a positive number)
* Freq 2: Second-lowest frequency (wavenumber) in cm^-1; if Freq 1 is listed as a negative number and Freq 2 
  is positive, then the structure is a saddle point (transition state) 

### cpsnap

This option produces one-line summaries of each output file (as in the "snap" option). It also provides the 
following columns. Some were created for very specific tasks and thus may not be generally useful.

The "CP" of cpsnap stands for Cremer-Pople. While CP parameters are generally defined for many ring sizes, this program assumes only 6-membered rings.

* phi: Cremer-Pople (CP) puckering parameter phi, calculated  
* theta: CP theta
* Q: CP Q
* Pucker: the closest IUPAC recognized puckering designation, as calculated based on arc length 
* HM1 (deg): HM stands for "hydroxymethyl" and this is the O5-C5-C6-O6 dihedral angle; these calcs 
  were used to determine the orientation of the hydroxymethyl arm on sugars such as glucose, GlcNAC, and mannose. "N/A" is returned for xylose.
* HM2 (deg): as above, with this showing the C4-C5-C6-O6 dihedral angle
* AC1 (deg): valid for GlcNAc (N/A otherwise); angle specifying orientation of acetyl group: C1-C2-N-C
* AC2 (deg): as above, but C3-C2-N-C
* Ano1 (deg): the C5-O5-C1-O1 dihedral angle, used to determine the stereochemistry at the anomeric carbon  
* Ano2 (deg): the C3-C2-C1-O1 dihedral angle (the other anomeric carbon angle)
* R1 (A): these are the bond length around the 6-membered ring: O5-C1
* R2 (A): C1-C2
* R3 (A): C2-C3
* R4 (A): C3-C4
* R5 (A): C4-C5
* R6 (A): C5-O5
* O1 (A): For a six-membered ring sugar, the C1-O1 distance. Note that the there is no "O5" output. That is because 
  for the sugars, the "O5" is part of the ring and thus the distances between O5 and C5 (and O5 and C1) are already output above.
* O2 (A): C2-O2 distance (C2-N if GlcNAc)
* O3 (A): C3-O3 distance
* O4 (A): C4-O4 distance
* O6 (A): C6-O6 distance (N/A if the sugar is xylose)
* Ion1 (A): distance between a specified atom type and each of up to 6 oxygen atoms (O1 to O6) (assumes one such atom type, 
  as created for when I looked at ions like Ca or Na interacting with a glucose molecule). By default, it will look for a 
  sodium ion (Na or 11). This can be changes with the -i or --ion option. Note that the whole element name must be used (i.e. "-i calcium"). 
* Ion2 (A): ion-O2
* Ion3 (A): ion-O3
* Ion4 (A): ion-O4
* Ion5 (A): ion-O5
* Ion6 (A): ion-O6


### test

Test function; no output created

### therm

Not sure that this function is complete.

### lowen

Created to help restart a calculation from the lowest energy point (determined from looking at "SCF: Done"), 
which may not be the last point. Sometimes, an optimization goes off course and restarting from an intermediate 
position is helpful. The output (xyz coordinates) is added to a template. An example template can be found at /hartree-common/src/test/resources/files/tpl/lowtpl 


