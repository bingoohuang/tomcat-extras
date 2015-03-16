# tomcat-extras
A set of useful extra components for the Apache Tomcat v8.
from [Michael Osipov's Apache Tomcat Extras](http://mo-tomcat-ext.sourceforge.net/user-guide.html)

## LogbackContextNameListener
  1. Copy tomcat-extras-{version}.jar and its dependencies to $CATALINA_BASE/lib or $CATALINA_HOME/lib.
  2. Open your app's context.xml and add:

  ```xml
<Context>
[…]
  <!-- Add this -->
  <Listener className="com.github.bingoohuang.tomcat.extras.listeners.LogbackContextNameListener" />
[…]
</Context>
```

  3. Open logback.xml and add:
  ```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <property name="logs-folder" value="${catalina.base}/logs" />

    <insertFromJNDI env-entry-name="java:comp/env/logback/contextName" as="contextName" />
    <contextName>${contextName}</contextName>

    <appender name="Console" class="ch.qos.logback.core.ConsoleAppender">
        <!-- encoders are assigned the type ch.qos.logback.classic.encoder.PatternLayoutEncoder by default -->
        <encoder>
            <pattern>${HOSTNAME} ${logs-folder} ${contextName} %d{yyyy-MM-dd HH:mm:ss,SSS} %-5level %logger{32} [%thread] ${CONTEXT_NAME} %X %replace(%msg){'\n', '\\n'}%n%throwable{short}</pattern>
        </encoder>
    </appender>

    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${logs-folder}/${contextName}-${HOSTNAME}.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${logs-folder}/${contextName}-${HOSTNAME}.%d{yyyyMMdd}.log</fileNamePattern>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss,SSS} %-5level %logger{32} [%thread] %X %replace(%msg){'\n', '\\n'}%n%throwable{short}</pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="Console" />
    </root>

</configuration>
```
