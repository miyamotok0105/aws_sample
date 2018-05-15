

IDEの始め方    
http://techmonies.com/index.php/2017/01/05/aws-java-sdk-and-maven-setup/    

- mvnでプロジェクトを始める    
- pom.xmlに追加    
- プロジェクトをビルド    
- mavenビルド    
- javaファイルを作る    
- ide上でrunボタン押下    


<hr>


```xml:pom.xml
<dependencyManagement>
<dependencies>
<dependency>
<groupId>com.amazonaws</groupId>
<artifactId>aws-java-sdk-bom</artifactId>
<version>1.11.66</version>
<type>pom</type>
<scope>import</scope>
</dependency>
</dependencies>
</dependencyManagement>
```

ビルド    

```
mvn package
mvn clean install
```


