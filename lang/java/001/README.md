
【初めてのJava】Intellijでとりあえず動くものを作る    
https://qiita.com/Esfahan/items/5dfd3a07cd30093092c5    

## mvnで新規プロジェクト作成    

GroupId jp.sample    
ArtifactId  sample-artifact    

## pom.xmlを記載    


下記を追記    


```pom.xml
    <properties>
        <maven.compiler.source>1.6</maven.compiler.source>
        <maven.compiler.target>1.6</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>1.10.2</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>RELEASE</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <finalName>sample-${project.version}</finalName>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                    <archive>
                        <manifest>
                            <mainClass>jp.sample.Main</mainClass>
                        </manifest>
                    </archive>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

jp.sampleパッケージを作成    
その下に２ファイルを作成    


```java:Main.java
package jp.sample;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        final ScrapeService scrapeService = new ScrapeService();
        System.out.println(scrapeService.getTitle());
    }
}
```


```java:ScrapeService.java
package jp.sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;

public class ScrapeService {
    private String url = "https://qiita.com/";

    public String getTitle() throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements title = document.select("title");
        return title.text();
    }
}

```


Mainファンクションで右クリックしてデバッグ実行


