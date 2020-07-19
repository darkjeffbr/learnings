provider "aws" {
    version = "~> 2.0"
    region = "eu-west-3"
}

resource "aws_instance" "dev" {
    count = 2
    ami = var.amis["eu-west-3"]
    instance_type = var.instance_type
    key_name = var.key_name
    tags = {
        Name = "dev machine ${count.index}"
    }
    vpc_security_group_ids = ["${aws_security_group.ssh-access.id}"]    
}