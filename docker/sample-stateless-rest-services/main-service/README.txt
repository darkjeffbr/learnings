mvn clean package
docker build -t main-service:latest .
docker run -d --rm --name main-service -p 8080:8080 main-service
docker run -d --rm --name main-service -e SECONDARY_SERVICE_CALL=true -e SECONDARY_SERVICE_URL= -p 8080:8080 main-service
