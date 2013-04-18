			README:
+++++++++++++++++++++++++++++++
DATABASE SYSTEM IMPLEMENTATION
	COMSW4112 2013 SPRING
	  PROJECT 2 STAGE 2
+++++++++++++++++++++++++++++++
===============================
TEAM MEMBERS:
Sichang Li	(UNI: sl3484)
Tian Xia	(UNI: tx2126)
===============================
FILE LIST:
op.java		//The algorithm implementation program
Subset.java	//A class defined to use in op.java
Makefile	//The makefile used to compile this project
stage2.sh	//shell script to run the implementation program
query.txt	//test file containing sample selectivity lists
conf.txt	//txt file containing configuration parameters
output.txt	//Execution Output
README.txt
**********
8 totally
**********
===============================
1. PROJECT INTRODUCTION:
This project is an implementation based on the algorithm proposed by Professor Kenneth A. Ross[1] to optimize database query. According to this algorithm, the optimial plan is generated with NO-Branching, Logic-AND and AND-Branching cases.
The program is implemented with JAVA (in op.java) and could be called with the shell script stage2.sh after compiling is conducted by using Makefile. 
In this implementation, dynamic programming is applied by using two loops. 

2. INSTALL AND RUNNING
2.1 INSTALL
A Makefile has been written to help install. 
To install this program, unzip and put all the code in the a directory and open a command-line-window/terminal in that directory then type the following command:
make
This program will be automatically setup and be ready for running.
We also provide two more choices of commands in case of different demands, as following:
make clean	//clean all .class files in this directory.
make all 	//clean all .class files in this directory and conduct the installation again

2.2 RUNNING
This program can be started by calling the shell script file stage2.sh in the following way:
./stage2.sh query.txt config.txt
The query.txt file is the file storing selectivity lists for basic terms. A sample file is included in this package. 
The config.txt file contains values of estimated costs. A sample file is also included in this package.

2.3 OUTPUT:
This program will print results on the screen if no redirection is assigned when the shell script is called.
For a list of selectivities, the output will be returned in the following layout:

===========================================
the list of selectivities
-------------------------------------------
optimal plan generated with that algorithm
-------------------------------------------
cost

===============================
REFERENCE:
[1]. KENNETH A. ROSS, "SELECTION CONDITIONS IN MAIN MEMORY", ACM Transactions on Database Systems, Vol. 29, No. 1, March 2004, Page 132-161.
