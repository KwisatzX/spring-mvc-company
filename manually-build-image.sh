mvn clean
mvn package
mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
docker build -t kwisatzx/paper-company .