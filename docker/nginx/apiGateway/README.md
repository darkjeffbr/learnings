# API gateway - NGINX server

An example of how to configure a NGINX server as an API Gateway.

To run it execute:
```
docker run -d --rm --name nginx -v ${PWD}/conf:/etc/nginx/conf.d/:ro -v ${PWD}:/usr/share/nginx/html:ro -p 8090:8080 nginx
```

Access [http://localhost:8090/service1](http://localhost:8090/service1) for **service 1**.
Access [http://localhost:8090/service2](http://localhost:8090/service2) for **service 2**.
Access [http://localhost:8090/](http://localhost:8090/) with anything ending in .php to access a "local" server.


