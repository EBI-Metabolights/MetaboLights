To run the HSQLB Server:

1.- Go to the root of the project where the pom file is.
2.- run: mvn exec:java -Dexec.mainClass="org.hsqldb.Server" -Dexec.args="-database.0 file:target/data/tutorial" -e