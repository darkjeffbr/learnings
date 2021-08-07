terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~>2.14.0"
    }
  }
}

provider "docker" {}

resource "random_string" "random" {
  count = 1
  length = 4
  special = false
  upper = false
}

resource "docker_image" "nodered_image" {
  name = "nodered/node-red:latest"
}

resource "docker_container" "nodered_container" {
  count = 1
  name  = join("-", ["nodered", random_string.random[count.index].result])
  image = docker_image.nodered_image.latest
  ports {
    internal = 1880
    # external = 1880
  }
}

output "Ip-Address" {
  value = [for container in docker_container.nodered_container[*]: join(":", [container.ip_address], container.ports[*].external)]

  #join(":", [docker_container.nodered_container.ip_address, docker_container.nodered_container.ports[0].external])
  description = "The IP address and external port of the container"
}

output "Container-Name" {
  value = docker_container.nodered_container[*].name
  description = "The name of the container"
}