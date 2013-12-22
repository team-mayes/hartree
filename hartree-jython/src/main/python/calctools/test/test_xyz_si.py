from __future__ import absolute_import, division, with_statement
import os
import unittest
import calctools.xyz_si as xyz
import StringIO
import tempfile

FILE_DIR = 'xyz_for_si'
PWD = os.path.dirname(os.path.realpath(__file__))
INFILE = os.path.join(PWD, FILE_DIR, 'list.txt')
OUTFILE = os.path.join(PWD, FILE_DIR, 'out.txt')

class TopLevelTests(unittest.TestCase):
    "Tests for suffix and extension evaluations"
    def setUp(self):
        self.out = StringIO.StringIO()
        self.err = StringIO.StringIO()
        
    def test_no_file(self):
        """
        Exit for no file.
        """
        self.assertRaises(SystemExit, xyz.main, [])

    def test_three_lines(self):
        """
        Basic test with a three-line input file.
        """
        xyz.main([INFILE], self.out, self.err)
        with open(OUTFILE) as match_me:
            self.assertEqual(match_me.read(), self.out.getvalue())