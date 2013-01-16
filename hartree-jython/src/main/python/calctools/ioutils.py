import os

"""
This module is a set of utility functions for file interactions,
including template fetching and filling.

"""

class NotFoundError(EnvironmentError):
    pass

class ExistingResourceError(EnvironmentError):
    pass

def cmakedir(tgt_dir):
    """
    Creates the given directory and its parent directories if it
    does not already exist.

    Keyword arguments:
    tgt_dir -- The directory to create
    
    """
    if not os.path.exists(tgt_dir):
        os.makedirs(tgt_dir)
    elif not os.path.isdir(tgt_dir):
        raise NotFoundError("Resource %s exists and is not a dir" % 
                        tgt_dir)

# From https://ssscripting.wordpress.com/2009/03/03/python-recursive-directory-walker/
def walk(tgt_dir,meth):
    """ walks a directory, and executes a callback on each fname """
    tgt_dir = os.path.abspath(tgt_dir)
    for fname in [fname for fname in os.listdir(tgt_dir) if not fname in [".",".."]]:
        nfile = os.path.join(tgt_dir,fname)
        meth(nfile)
        if os.path.isdir(nfile):
            walk(nfile,meth)

