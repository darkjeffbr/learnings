output "Ip-Address" {
  value = [for container in docker_container.nodered_container[*]: join(":", [container.ip_address], container.ports[*].external)]
  description = "The IP address and external port of the container"
}

output "Container-Name" {
  value = docker_container.nodered_container[*].name
  description = "The name of the container"
}