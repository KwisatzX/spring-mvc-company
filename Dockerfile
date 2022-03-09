FROM openjdk:17.0.2-jdk-oraclelinux8
RUN adduser -rU spring
USER spring:spring
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","io.github.kwisatzx.springmvccompany.SpringMvcCompanyApplication"]