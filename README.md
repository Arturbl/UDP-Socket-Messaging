# Create an exe java app:

* Add the following snippet to pom.xml
	```
	<plugins>
		<plugin>
			<!-- Build an executable JAR -->
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-jar-plugin</artifactId>
			<version>3.1.0</version>
			<configuration>
				<archive>
					<manifest>
						<addClasspath>true</addClasspath>
						<!-- here we specify that we want to use the main method within the App class -->
						<mainClass>com.sohamkamani.App</mainClass>
					</manifest>
				</archive>
			</configuration>
		</plugin>
	</plugins>
	```
## run the following commands
	mvn compile
	mvn package
	mvn clean package
	java -jar target/mvn-example-1.0-SNAPSHOT.jar

