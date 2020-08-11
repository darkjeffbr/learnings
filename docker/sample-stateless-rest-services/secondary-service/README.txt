mvn clean package
docker build -t secondary-service:latest .
docker run -d --rm --name secondary-service -p 8080:8080 secondary-service
