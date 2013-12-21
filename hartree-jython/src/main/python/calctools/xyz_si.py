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
    logfile = os.path.join(logdir, "geocomp.log")
else:
    logfile = expanduser('~/.hartree/geocomp.log')

if not os.path.exists(logfile):
    cmakedir(os.path.dirname(logfile))    

logging.basicConfig(format='%(asctime)s - %(name)s - %(levelname)s - %(message)s', 
                    level=logging.DEBUG,
                    filename=logfile)

# Logic #

loader = SnapshotLoader()

def process_line(line, target, base_dir):
    if len(line) < 3:
        raise FileParserError("Line %s has fewer than three fields", line)
    target.write("%s %s%s" % (line[0], line[1], os.linesep))
    data_file = os.path.join(base_dir, line[2])
    if not os.path.isfile(data_file):
        raise NotFoundError("'%s' is missing or not a file" % data_file)
    for atom in loader.load(data_file, FileReader(data_file)).getAtoms():
        target.write("%s   % f   % f   % f%s" % (atom.type.symbol, atom.x,
              atom.y, atom.z, os.linesep))

def process_file(infile, target, err):
    base_dir = os.path.dirname(os.path.abspath(infile))
    with open(infile) as csvfile:
        is_first = True
        for row in csv.reader(csvfile):
            try:
                process_line(row, target, base_dir)
                target.write(os.linesep)
            except Exception, e:
                err.write("Skipping line %s: %s%s" % (",".join(row), e, os.linesep))

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
    
    process_file(args[0], out, err)
    
    return 0  # success

if __name__ == '__main__':
    status = main()
    java.lang.System.exit(status)

