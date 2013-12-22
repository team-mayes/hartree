**Hartree Jython**

This is a packaging of Python modules with a standalone Jython JAR plus
Hartree's ANTLR parsers.  This setup allows a user to run Python scripts
that can use Hartree's Java classes directly.

Setup
=====

1. Unpack the project archive somewhere convenient
2. Add the ``scripts`` subdirectory to your PATH (unless you'd
   rather run the scripts from the project location directly).

Usage
=====

The ``scripts`` directory contains the ``hrun`` script, which will run
Python modules in the ``calctools`` namespace of the ``lib/python``
directory.  The first argument must be a valid Python module name in
the ``calctools`` namespace.  If a name is not given or the given name
is not found, the script will list known options. 

geocomp
-------

Usage::
  hrun geocomp <target dir>

This command will look through this directory and its subdirectories for files
that end in ``ccsdt.log``.  It will then try to pair these files with another 
file that has the same name as the "ccsdt" file, minus the "ccsdt" suffix.  
If no matching base file is found, the script writes a warning to standard 
error and a log at ``[project_location]/log/geocomp.log``.

The paired files are then parsed using Hartree and their atomic geometry is
extracted.  The ``geocomp`` script compares the geometries of the two files
to see whether they are identical.  If not, an error message is written to 
standard error and the log file and the script exits with the code 2 (0 
indicates success).

xyz_si
-------

Usage::
  hrun xyz_si <input_file>

This command will read each line of the input file and create an output that
contains the first two fields separated by a space on one line and the atoms
contained in the end product of the file specified in the third field of the
line.  For example, the line

11,cyclic enol,glucEO3Dbb3lm.log

...would result in the output

11 cyclic enol
C   -0.249986    0.890519   -0.021576
C   -0.869790    1.437656   -1.287677
...

The output goes to stdout by default, but users may also use the `-f` option
to specify a destination file.