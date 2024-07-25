The goal of this programming assignment is to implement an inverted index and query mechanismfor an input set of documents using Hadoop.  An inverted index is a mapping of words to the IDsof the documents in which they appear (and possibly also their locations in the documents).  Mostmodern information retrieval systems and search engines utilize some form of an inverted index toprocess user-submitted queries.

This code can be run on VM or DSBA Hadoop cluster.

Create directory and input directory on HDFS
Store all the input files on this directory in Hadoop


SIMPLE and FULL INVERTED INDEX

- make directory for build : mkdir -p build
- Compile and create JAR files for the respective .java files. 
: javac WordCount.java -cp $(hadoop classpath)
: javac -cp `hadoop classpath` Index.java -d build
- Create JAR files.
: jar -cvf index.jar -C build/ .
- Run the JAR files on the HDFS input folder where data files reside.
hadoop jar index.jar Index /user/asarmalk/query/input /user/asarmalk/query/output

- The outputs have been combined using cat command on the local system.
hadoop fs -cat wordcount/output/*  > output.txt

QUERIES on SIMPLE and FULL INVERTED INDEX:

- The java codes for both these queries run on local with the assumption that all the input text files (cagedbird.txt, daffodils.txt and roadnot.txt) and the simple and full index files (index_all.txt and fullIndex_all.txt) are residing on the local system in the same directory along with the java files (QueryIndex.java and QueryFullIndex.java)
- Compile the respective java codes and pass 3 parameters while running the code as arguments (QueryFileName, IndexFileName, OutputFileName)
