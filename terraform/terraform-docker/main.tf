terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~>2.14.0"
    }
  }
}

provider "docker" {}

resource "null_resource" "docker_vol" {
  provisioner "local-exec" {
    command = "mkdir noderedvol/ || true && chown -R 1000:1000 noderedvol/"
  }
}

resource "random_string" "random" {
  count = local.container_count
  length = 4
  special = false
  upper = false
}

resource "docker_image" "nodered_image" {
  name = "nodered/node-red:latest"
}

resource "docker_container" "nodered_container" {
  count = local.container_count
  name  = join("-", ["nodered", random_string.random[count.index].result])
  image = docker_image.nodered_image.latest
  ports {
    internal = var.int_port
    external = var.ext_port[count.index]
  }
  #volumes {
  #  container_path = "/data"
  #  host_path = "/home/ubuntu/noderedvol"
  #}
}