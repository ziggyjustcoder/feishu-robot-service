FROM registry.logan.xiaopeng.local/logancloud/logan-base:1.1.5-RC5-J21_0.8
MAINTAINER Linzhaoming <linzm2@xiaopeng.com>

ENV JAVA_OPTS="-XX:MinRAMPercentage=40.0 -XX:MaxRAMPercentage=65.0 -XX:InitialRAMPercentage=40.0 -XX:MaxMetaspaceSize=256m -XX:ReservedCodeCacheSize=128m -XX:MaxDirectMemorySize=128m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+DisableExplicitGC -XX:+HeapDumpOnOutOfMemoryError --add-opens java.base/java.lang=ALL-UNNAMED"

ENTRYPOINT [ "sh", "-c", "java $APP_OPTS $JMX_OPTS $JAVA_OPTS $GCLOG_OPTS -Djava.security.egd=file:/dev/./urandom -jar xp-vmp-trip-report-mcp-boot.jar" ]

WORKDIR /home/xpmotors/local

COPY target/feishu-robot-service-*.jar /home/xpmotors/local/feishu-robot-service.jar
USER xpmotors