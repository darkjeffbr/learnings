terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~>2.14.0"
    }
  }
}

provider "docker" {}

resource "docker_image" "nodered_image" {
  name = "nodered/node-red:latest"
}

resource "docker_container" "nodered_container" {
  name  = "nodered-abc"
  image = docker_image.nodered_image.latest
  ports {
    internal = 1880
    external = 1880
  }
}