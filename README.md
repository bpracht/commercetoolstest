Etsy Shop Comparison Tool
This program runs as a Java application from the command line. To build it, the following is required:

* A Java JDK 1.8 or higher.
* Maven 3.3.9 or higher 
* An Etsy API key file.

1.  Make sure both Java and Maven accessible in your path.
2.  Create a text file in a secure place to store your API key.  This is needed for running some of the
test cases.
3.  Invoke the Maven build by running  

mvn clean compile assembly:single

This downloads any dependencies you might need, compiles the Java code, and assembles all the dependencies
into one jar.  This jar is at ./target/commercetools-1.0-SNAPSHOT-jar-with-dependencies.jar

4.  Invoke the program using 
./launch.sh --help to get the program options
or
./launch.sh --directories --shopids <shopid1> <shopid2> <...> --apikeyfile <yourapikeyfile>


