from __future__ import absolute_import, division, with_statement
import os.path
from os.path import expanduser
from ioutils import cmakedir
import java
import sys
import optparse
import logging
import csv
from org.cmayes.hartree.loader.gaussian import SnapshotLoader
from java.io import FileReader

class FileParserError(Exception): pass
class NotFoundError(Exception): pass

# Log Setup #
logdir = os.environ.get("LOGDIR")
if logdir:
    logfile = os.path.join(logdir, "xyz_si.log")
else:
    logfile = expanduser('~/.hartree/xyz_si.log')

if not os.path.exists(logfile):
    cmakedir(os.path.dirname(logfile))    

logging.basicConfig(format='%(asctime)s - %(name)s - %(levelname)s - %(message)s', 
                    level=logging.DEBUG,
                    filename=logfile)

logger = logging.getLogger('xyz_si')

# Logic #

gauss_load_func = SnapshotLoader().load

def process_line(line, target, base_dir, loader=gauss_load_func):
    """
    Writes the line's first two fields on one line then fetches
    the atoms from the target file, writing those on the subsequent lines.
    
    `line`: a three element list where the last element is a file name.
    `target`: the destination for written data.
    `base_dir`: the base filesystem location for finding the data files.
    `loader`: a function capable of extracting atom data from the file.
    """
    if len(line) < 3:
        raise FileParserError("Line %s has fewer than three fields", line)
    target.write("%s %s%s" % (line[0], line[1], os.linesep))
    data_file = os.path.join(base_dir, line[2])
    if not os.path.isfile(data_file):
        raise NotFoundError("'%s' is missing or not a file" % data_file)
    for atom in loader(data_file, FileReader(data_file)).getAtoms():
        target.write("%s   % f   % f   % f%s" % (atom.type.symbol, atom.x,
              atom.y, atom.z, os.linesep))

def process_file(infile, target):
    """
    Reads the contents of the given file, which should contain lines of the
    format:
    
    index, description, atom_file_loc
    
    ...where the first two elements are descriptive and may be any value.  
    The third element must be a file name relative to the location of the
    given file.  This file will contain atom data to be written to the given
    target.
    
    Each line of the file will result in something similar to the following
    being written to the target:
    
    11 cyclic enol
    C   -0.249986    0.890519   -0.021576
    C   -0.869790    1.437656   -1.287677
    ...
    
    Each output result will be separated by a blank line.
    
    `infile`: The location of the file to process.
    `target`: The target for the processed data.
    """
    base_dir = os.path.dirname(os.path.abspath(infile))
    with open(infile) as csvfile:
        for row in csv.reader(csvfile):
            try:
                process_line(row, target, base_dir)
                target.write(os.linesep)
            except Exception, e:
                logger.error("Skipping line %s: %s%s" % (",".join(row), e, os.linesep))

# CLI #

def parse_cmdline(argv):
    """
    Return a 2-tuple: (opts object, args list).
    `argv` is a list of arguments, or `None` for ``sys.argv[1:]``.
    """
    if argv is None:
        argv = sys.argv[1:]

    # initialize the parser object:
    parser = optparse.OptionParser(
        formatter=optparse.TitledHelpFormatter(width=78),
        add_help_option=None)
    
    parser.set_usage("%s [options] infile" % os.path.basename(sys.argv[0]))
    
    # define options here:
    parser.add_option(# customized description; put --help last
        '-h', '--help', action='help',
        help='Show this help message and exit.')
    parser.add_option('-f', '--file',
        help='The target file (writes to stdout by default)')

    opts, args = parser.parse_args(argv)

    # check number of arguments, verify values, etc.:
    if not args:
        parser.error('Input file required')

    # further process opts & args if necessary

    return opts, args

def main(argv=None, out=sys.stdout, err=sys.stderr):
    """
    Get query file as arg, write to stdout
    """
    opts, args = parse_cmdline(argv)
    
    if opts.file:
        target = open(opts.file, 'w+')
    else:
        target = out
    
    process_file(args[0], target)
    
    return 0  # success

if __name__ == '__main__':
    status = main()
    java.lang.System.exit(status)

