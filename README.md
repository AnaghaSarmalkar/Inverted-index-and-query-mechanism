<a id="readme-top"></a>
# Inverted Index and Query Mechanism for Documents Using Hadoop
<!-- ABOUT -->
## About
An inverted index is a mapping of words to the IDs of the documents in which they appear (and possibly also their locations in the documents).  Most modern information retrieval systems and search engines utilize some form of an inverted index to process user-submitted queries.

## Usage
This code can be run on VM or DSBA Hadoop cluster.

### Implementation of simple and full inverted index.
1. Create directory and input directory on HDFS and store all the input files on this directory in Hadoop.
2. make directory for build
   ```
   mkdir -p build
   ```
3. Compile and create JAR files for the respective .java files.
   ```
   javac WordCount.java -cp $(hadoop classpath)
   javac -cp `hadoop classpath` Index.java -d build
   jar -cvf index.jar -C build/ .
   ```
4. Run the JAR files on the HDFS input folder where data files reside.
   ```
   hadoop jar index.jar Index /user/asarmalk/query/input /user/asarmalk/query/output
   ```
5. Combine the outputs in one single file on the local system.
   ```
   hadoop fs -cat wordcount/output/*  > output.txt
   ```
### Querying simple and full inverted index
- The java codes for both these queries run on local with the assumption that all the input text files (`cagedbird.txt`, `daffodils.txt` and `roadnot.txt`) and the simple and full index files (`index_all.txt` and `fullIndex_all.txt`) are residing on the local system in the same directory along with the java files (`QueryIndex.java` and `QueryFullIndex.java`)
- Compile the respective java codes and pass 3 parameters while running the code as arguments (`QueryFileName`, `IndexFileName`, `OutputFileName`)
