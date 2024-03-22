MaKayla Robinson - mrobin45@u.rochester.edu
John Moses - jmoses4@u.rochester.edu

CSC 242 Spring 2023
Project 3 - Uncertain Inference
03/29/2023

Open a terminal at our P3 folder.
- Type "bash run.sh" to build/compile.
- EXACT INFERENCE command line format:
    java -cp "./bin" bn/Test [File Name] [Query Variable] [Names & values of evidence variables]
    (example) java -cp "./bin" bn/Test bn/examples/aima-alarm.xml B J true M true
- REJECTION SAMPLING command line format:
    java -cp "./bin" bn/Test [Number of samples] [File Name] [Query Variable] [Names and values of evidence variables] rejection
    (example) java -cp "./bin" bn/Test 1000 bn/examples/aima-alarm.xml B J true M true rejection
LIKELIHOOD WEIGHTING command line format:
    java -cp "./bin" bn/Test [Number of samples] [File Name] [Query Variable] [Names and values of evidence variables] likelihood
    (example) java -cp "./bin" bn/Test 1000 bn/examples/aima-alarm.xml B J true M true likelihood

We implemented Bayesian Networks using ALL the files Professor provided us.
Our inference algorithms can be found in the P3/bn/inference folder, with the appropriate names.
Test.java is located in the P3/bn folder.
The program can read both XMLBIF and BIF files.