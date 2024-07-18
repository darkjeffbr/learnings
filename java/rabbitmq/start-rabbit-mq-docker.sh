#!/bin/bash

# Default user/password guest/guest
# This will start a RabbitMQ container listening on the default port of 5672.
# You can access the admin UI on http://localhost:15672
docker run -d --name rabbitmq-broker -p 15672:15672 -p 5672:5672 rabbitmq:3.13.4-management


