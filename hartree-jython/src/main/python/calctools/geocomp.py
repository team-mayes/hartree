#!/usr/bin/env jython

"""
Compares geometry of two files based on a suffix pattern match.
"""

import sys
import optparse
import re
import logging
from ioutils import walk, cmakedir
import os.path
from os.path import expanduser
import java

# Constants #
DEF_EXT = '.log'
DEF_SUFFIX = 'ccsdt'

from org.cmayes.hartree.loader.gaussian import SnapshotLoader
from java.io import FileReader

# Exceptions #

class FileParserError(Exception): pass
class HartreeError(FileParserError): pass

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

class HartreeMoleculeComparator(object):
    def __init__(self):
        for key, value in locals().items():
            if key != self:
                setattr(self, key, value)
        self.logger = logging.getLogger('hartree_comparator')
        self.loader = SnapshotLoader()
    
    def get_atoms(self, fname):
        "Pulls the atoms data structure from the given file"
        result = self.loader.load(fname, FileReader(fname))
        return result.getAtoms()
        
    def compare(self, first, second):
        try:
            first_atoms = self.get_atoms(first)
        except ValueError:
            self.logger.exception("Problems parsing first file %s" % first)
            raise
        try:
            second_atoms = self.get_atoms(second)
        except ValueError:
            self.logger.exception("Problems parsing second file %s" % second)
            raise
        
        if first_atoms == second_atoms:
            return True
        self.logger.error("Atoms for files %s and %s do not match" % (first, 
           second,))
        self.logger.error("%s: %s" % (first, first_atoms))
        self.logger.error("%s: %s" % (second, second_atoms))
        return False
        

class SuffixFileComparator(object):
    def __init__(self, opts, comp_func=None):
        """
        Create a geometry comparison evaluator for the files in the 
        target dir.
        `opts` contains the configuration values for file extension and 
            suffix.
        `comp_func` is the default function to use when comparing two
            files.
        """
        if comp_func is None:
            comp_func = HartreeMoleculeComparator().compare
        
        self.inst_comp_func = comp_func
        self.suffix_re = re.compile("%s%s$" % (opts.suffix, opts.file_ext,))
        self.ext_re = re.compile("%s$" % (opts.file_ext,))
        self.ext = opts.file_ext
        
        # Files with suffix + ext
        self.suffix_files = []
        # Files with just ext
        self.ext_files = []
        # Pairs of suffix to ext file names
        self.pairs = {}
        self.logger = logging.getLogger('suffix_comparator')

    
    def eval_file(self, fname):
        """
        Evaluates whether the given file name is a suffix match, an
        extension match, or neither.
        `fname` is a file name to evaluate.
        """
        if self.suffix_re.search(fname):
            self.suffix_files.append(fname)
        elif self.ext_re.search(fname):
            self.ext_files.append(fname)
        else:
            self.logger.debug('File %s does not have the ext %s' % 
                (fname, self.ext))
    
    def map_file_pairs(self):
        """
        For each suffix file, this function looks for and saves a file name
        with the suffix removed.
        Returns a list of suffix files that do not have a basename match.
        """
        mismatches = []
        for sfx_file in self.suffix_files:
            basename = re.sub(self.suffix_re, self.ext, sfx_file)
            if basename in self.ext_files:
                self.pairs[sfx_file] = basename
            else:
                mismatches.append(sfx_file)
                
        return mismatches
    
    def compare(self, comp_func=None):
        """
        For each matched pair, runs the comparison function.
        Comparison function defaults to the one specified for
        this instance.
        Returns a map of mismatched suffixes to their basenames.
        """
        if comp_func is None:
            comp_func = self.inst_comp_func
        
        mismatches = {}
        for (suffix, base) in self.pairs.items():
            try:
                if not comp_func(suffix, base):
                    mismatches[suffix] = base
            except ValueError:
                self.logger.exception("Problems parsing pair %s:%s" % (suffix, base))
                
        return mismatches

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

    # define options here:
    parser.add_option('-e', '--file_ext',
                      help='File extension (default is %s)' % DEF_EXT,
                      default=DEF_EXT)
    parser.add_option('-s', '--suffix',
                      help='Suffix to compare vs. base (default is %s)' % 
                      DEF_SUFFIX, default=DEF_SUFFIX)
    parser.add_option('-q', '--quiet', action="store_true",
        help='Suppress non-error output')
    parser.add_option(# customized description; put --help last
        '-h', '--help', action='help',
        help='Show this help message and exit.')

    opts, args = parser.parse_args(argv)

    # check number of arguments, verify values, etc.:
    if not args:
        parser.error('Target directory required')

    # further process opts & args if necessary

    return opts, args

def main(argv=None, out=sys.stdout, err=sys.stderr, walker=walk,
         comper=SuffixFileComparator):
    """
    Runs the evaluations, printing out any errors to err.
    If there are mismatched pairs, exits with status 2.
    """
    opts, args = parse_cmdline(argv)
    comp = comper(opts)
    walker(args[0], comp.eval_file)
    mismatch_suffixes = comp.map_file_pairs()
    if mismatch_suffixes:
        err.write('%d files with no basename match: %s%s' % 
                  (len(mismatch_suffixes), ", ".join(mismatch_suffixes), os.linesep,))
    mismatch_pairs = comp.compare()
    if mismatch_pairs:
        err.write('%d pairs do not match: %s%s' % 
                  (len(mismatch_pairs), ", ".join([":".join([key, value]) for 
                   (key, value) in mismatch_pairs.items()]), os.linesep,))
        return 2
    if not opts.quiet:
        out.write("All pairs match." + os.linesep)
    return 0  # success

if __name__ == '__main__':
    status = main()
    java.lang.System.exit(status)
