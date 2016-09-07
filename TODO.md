#  Wishlist additions for Hartree: Tools for Processing Computational Chemistry Data

## Documentation requests

* Update docs on how to build the jar

## Requests related to current functionality

* If a log file that does not have a 6-membered ring is provided with the cpnsap option, output the info it can find (the snap info) and "N/A" for the rest.
  Example: This file does not have a ring, and works great with snap:

        $ hartree snap -f ./hartree-antlr/src/test/resources/files/g09/methanolmp4mp2TooLittleData.log
        "File Name","Solvent type","Stoichiometry","Charge","Mult","Functional","Basis Set","Energy (A.U.)","dipole","ZPE (kcal/mol)","G298 (Hartrees)","H298 (Hartrees)","Freq 1","Freq 2"
        "methanolmp4mp2TooLittleData.log","thf","CH4O","0","1","mp4","6-31G(d)","-115.039357504","2.1353","N/A","N/A","N/A","N/A","N/A"

  It has no ring and no output is produced using cpsnap:

        $ hartree cpsnap -f ./hartree-antlr/src/test/resources/files/g09/methanolmp4mp2TooLittleData.log
        Error: NullPointerException(null). Check error logs for more detail.

  Desired output: same as snap output + "N/A" for additional cpsnap fields

* If a file is encountered that cannot be processed, print the file name that gave it trouble. This will help us debug which file in a directory of files is causing the problem.

## Feature requests

None right now!

Would like to add XYZ 



