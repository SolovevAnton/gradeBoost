To create data model from JSON, there is a special Library, that can be used:
Json2PojoGenerator. it is not part of the standard [[Jackson main|Jackson Library]].  However, it is really useful, when it is needed to create POJOs from JSON sources:
Problem is:
==It works ONLY FROM A FILE==

Dependency to add:
```XML
<!-- https://mvnrepository.com/artifact/org.jsonschema2pojo/jsonschema2pojo-core -->  
<dependency>  
	<groupId>org.jsonschema2pojo</groupId>  
	<artifactId>jsonschema2pojo-core</artifactId>  
	<version>1.1.1</version>  
</dependency>
```

Code itself:
```java
import com.sun.codemodel.JCodeModel;  
import org.jsonschema2pojo.*;  
import org.jsonschema2pojo.rules.RuleFactory;  
  
import java.io.File;  
import java.io.IOException;  
import java.net.URL;  
/**  
* Class to generate models from the incoming JSON file  
* Just copied this class from JSONlect  
*/  
public class Json2PojoGenerator {  
private final URL source;  
private final File destDir;  
private GenerationConfig config;  
  
/**  
* Constructor for initialize configuration of generation  
* @param srcFileName - Source file with JSON Object  
* @param resultDirectoryName - Path to your java code folder  
* @throws IOException - Configuration Exception  
*/  
public Json2PojoGenerator(String srcFileName, String resultDirectoryName) throws IOException {  
File file = new File(srcFileName);  
destDir = new File(resultDirectoryName);  
source = file.toURI().toURL();  
config = new DefaultGenerationConfig() {  
@Override  
public boolean isGenerateBuilders() {  
return false;  
}  
  
@Override  
public SourceType getSourceType() {  
return SourceType.JSON;  
}  
  
@Override  
public boolean isIncludeAdditionalProperties() {  
return false;  
}  
  
@Override  
public boolean isIncludeGeneratedAnnotation() {  
return false;  
}  
  
@Override  
public boolean isIncludeToString() {  
return false;  
}  
  
@Override  
public boolean isIncludeHashcodeAndEquals() {  
return false;  
}  
  
@Override  
public boolean isUsePrimitives() {  
return true;  
}  
  
@Override  
public boolean isIncludeConstructors() {  
return true;  
}  
  
@Override  
public boolean isInitializeCollections() {  
return true;  
}  
};  
}  
  
/**  
* Generate All Schema Classes  
* @param rootClassName - Name Class of Object Root  
* @param packageName - Name Package of Your Schema  
* @throws IOException - Generation Exception  
*/  
public void generate(String rootClassName, String packageName) throws IOException {  
JCodeModel codeModel = new JCodeModel();  
SchemaMapper mapper = new SchemaMapper(new RuleFactory(config,  
new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());  
mapper.generate(codeModel, rootClassName, packageName, source);  
codeModel.build(destDir);  
}  
}
```

Usage example:
```java
//generate classes of user:  
String fileNameUsers = "Users.json";  
String dirName = "src/main/java/";
Json2PojoGenerator generatorUsers = new Json2PojoGenerator(fileNameUsers, dirName);  
String classNameUsers = "ExampleUsers";  
String packageNameUsers = "com.solovev.example.user"; //changed to example to not break toString equals and hashcode in generated classes  
generatorUsers.generate(classNameUsers, packageNameUsers);
```
