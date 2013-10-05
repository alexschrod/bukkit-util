bukkit-util
===========
This is a collection of utility classes for use with [Bukkit](https://github.com/Bukkit/Bukkit) plugin development. These are intended to either be included in your project directly, either as source files (though please change the Java package name to avoid conflicts with other plugins using the same classes) or, if you use [Maven](http://maven.apache.org/) (and if you don't, why aren't you?),  to be included with the Maven Shade plugin, like this:

    <project>
      ...
      <repositories>
      ...
        <repository>
          <id>maven-alexanderschroeder-net</id>
          <url>http://maven.alexanderschroeder.net/releases/</url>
        </repository>
      </repositories>
      ...
      <dependencies>
        ...
        <dependency>
          <groupId>net.alexanderschroeder.bukkitutil</groupId>
          <artifactId>BukkitUtil</artifactId>
          <version>1.0.0</version>
        </dependency>
      ...
      <build>
        ...
        <plugins>
          ...
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>2.1</version>
            <configuration>
              ...
              <relocations>
                ...
                <relocation>
                  <pattern>net.alexanderschroeder.bukkitutil</pattern>
                  <shadedPattern>your.project.package.bukkitutil</shadedPattern>
                </relocation>
              </relocations>
            </configuration>
            <executions>
              <execution>
                <phase>package</phase>
                <goals>
                  <goal>shade</goal>
                </goals>
              </execution>
            </executions>
          </plugin>
        </plugins>
      </build>
    </project>

What's important is that you set the value of `<shadedPattern>` to be unique to your plugin's package name, so Maven changes it when it creates your jar for you.

Also, while it should be obvious, I feel I should mention that you must not place `net.alexanderschroeder.bukkitutil:BukkitUtil` in the shade plugin's `artifactSet`'s excludes, as that negates the whole point. In fact, make sure you understand exactly how the Maven Shade plugin does before you attempt this, because if you configure it incorrectly, it's going to include the whole Bukkit jar in your jar, and you don't want that.
