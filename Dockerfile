FROM openjdk:17.0.2-jdk-oraclelinux8 as builder
WORKDIR app
COPY target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:17.0.2-jdk-oraclelinux8
WORKDIR app
RUN adduser -rU spring
USER spring:spring
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./
EXPOSE 8080:8080
ENTRYPOINT ["java","-cp","BOOT-INF/classes:BOOT-INF/lib/*","io.github.kwisatzx.springmvccompany.SpringMvcCompanyApplication"]