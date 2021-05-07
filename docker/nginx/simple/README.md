# Simple NGINX server

An example of how to configure a NGINX server to server static content.

To run it execute:
```
docker run -d --rm --name nginx -v ${PWD}/conf:/etc/nginx/conf.d/:ro -v ${PWD}:/usr/share/nginx/html:ro -p 8090:8080 nginx
```

Access: [http://localhost:8090/](http://localhost:8090)

## Error page

In case you access a url/file unkown for nginx a custom error page will be presented.
