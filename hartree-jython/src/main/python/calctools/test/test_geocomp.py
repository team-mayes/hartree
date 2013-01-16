import unittest
import calctools.geocomp as geo
import os
try:
    from unittest.mock import MagicMock
except ImportError:
    from mock import MagicMock

# Constants #

FILE_DIR = 'geofiles'
PWD = os.path.dirname(os.path.realpath(__file__))
BASE_FILE_LOC = os.path.join(PWD, FILE_DIR, 'testircropt.log')
CCSDT_FILE_LOC = os.path.join(PWD, FILE_DIR, 'testircroptccsdt.log')
MISMATCH_FILE_LOC = os.path.join(PWD, FILE_DIR, 'mismatch.log')
DIR = 'dir'
EXT_FILE = "something%s" % geo.DEF_EXT
SUFFIX_FILE = "something%s%s" % (geo.DEF_SUFFIX, geo.DEF_EXT,)
CUSTOM_EXT = 'custom_ext'
CUSTOM_SUFFIX = 'custom_suffix'
CUSTOM_EXT_FILE = "something%s" % CUSTOM_EXT
CUSTOM_SUFFIX_FILE = "something%s%s" % (CUSTOM_SUFFIX, CUSTOM_EXT,)


# Tests #

# SuffixFileComparator

class DefaultSuffixTests(unittest.TestCase):
    "Tests for suffix and extension evaluations"

    def setUp(self):
        opts = geo.parse_cmdline([DIR])[0]
        self.calc = geo.SuffixFileComparator(opts)
    
    def test_ext(self):
        self.calc.eval_file(EXT_FILE)
        self.assertIn(EXT_FILE, self.calc.ext_files)

    def test_suffix(self):
        self.calc.eval_file(SUFFIX_FILE)
        self.assertIn(SUFFIX_FILE, self.calc.suffix_files)
        
class CustomSuffixTests(unittest.TestCase):
    "Tests for custom suffix and extension evaluations"

    def setUp(self):
        opts = geo.parse_cmdline([DIR, '-s', CUSTOM_SUFFIX, '-e', CUSTOM_EXT])[0]
        self.calc = geo.SuffixFileComparator(opts)
    
    def test_ext(self):
        self.calc.eval_file(EXT_FILE)
        self.assertNotIn(EXT_FILE, self.calc.ext_files)

    def test_suffix(self):
        self.calc.eval_file(SUFFIX_FILE)
        self.assertNotIn(SUFFIX_FILE, self.calc.suffix_files)

    def test_custom_ext(self):
        self.calc.eval_file(CUSTOM_EXT_FILE)
        self.assertIn(CUSTOM_EXT_FILE, self.calc.ext_files)

    def test_custom_suffix(self):
        self.calc.eval_file(CUSTOM_SUFFIX_FILE)
        self.assertIn(CUSTOM_SUFFIX_FILE, self.calc.suffix_files)
        
class MatchingFilesTests(unittest.TestCase):
    "Tests for matching base to suffix"

    def setUp(self):
        opts = geo.parse_cmdline([DIR])[0]
        self.calc = geo.SuffixFileComparator(opts)
    
    def test_match(self):
        "Tests matching qualifying files"
        self.calc.eval_file(SUFFIX_FILE)
        self.calc.eval_file(EXT_FILE)
        mismatch_suffix = self.calc.map_file_pairs()
        self.assertFalse(mismatch_suffix)
        self.assertDictEqual(self.calc.pairs, {SUFFIX_FILE: EXT_FILE})
        
    def test_not_ext_match(self):
        "Tests not matching with an invalid ext"
        self.calc.eval_file(SUFFIX_FILE)
        self.calc.eval_file(CUSTOM_EXT_FILE)
        mismatch_suffix = self.calc.map_file_pairs()
        self.assertIn(SUFFIX_FILE, mismatch_suffix)
        self.assertFalse(self.calc.pairs)
        
    def test_not_suffix_match(self):
        "Tests not matching with an invalid suffix"
        self.calc.eval_file(CUSTOM_SUFFIX_FILE)
        self.calc.eval_file(EXT_FILE)
        mismatch_suffix = self.calc.map_file_pairs()
        self.assertFalse(mismatch_suffix)
        self.assertFalse(self.calc.pairs)

def prep_calc(comp):
    opts = geo.parse_cmdline([DIR])[0]
    calc = geo.SuffixFileComparator(opts, comp_func=comp)
    calc.eval_file(SUFFIX_FILE)
    calc.eval_file(EXT_FILE)
    calc.map_file_pairs()
    return calc

class EvalFilesTests(unittest.TestCase):
    "Tests for evaluating whether the matched files compare"
    
    def test_comp_match(self):
        comp = MagicMock(return_value=True)
        calc = prep_calc(comp)
        mismatches = calc.compare()
        self.assertFalse(mismatches)
        comp.assert_called_with(SUFFIX_FILE, EXT_FILE)
        
    def test_comp_mismatch(self):
        comp = MagicMock(return_value=False)
        calc = prep_calc(comp)
        mismatches = calc.compare()
        self.assertDictEqual(mismatches, {SUFFIX_FILE: EXT_FILE})
        comp.assert_called_with(SUFFIX_FILE, EXT_FILE)

# HartreeMoleculeComparator

class SystemHartreeComparatorTests(unittest.TestCase):
    "Tests for the Hartree comparator (includes file access)"
    
    def setUp(self):
        self.hartree = geo.HartreeMoleculeComparator()
    
    def test_match(self):
        self.assertTrue(self.hartree.compare(CCSDT_FILE_LOC, BASE_FILE_LOC))
        
    def test_mismatch(self):
        self.assertFalse(self.hartree.compare(CCSDT_FILE_LOC, MISMATCH_FILE_LOC))
    