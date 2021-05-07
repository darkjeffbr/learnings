# Load Balancer - NGINX server

An example of how to configure a NGINX server as load balancer.

To run it execute:
```
docker run -d --rm --name nginx -v ${PWD}/conf:/etc/nginx/conf.d/:ro -v ${PWD}:/usr/share/nginx/html:ro -p 8090:8080 nginx
```

Access [http://localhost:8090/](http://localhost:8090/) and you'll see diferent messages from different servers.


