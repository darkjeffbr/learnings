provider "aws" {
    version = "~> 2.0"
    region = "eu-west-3"
}

provider "aws" {
    alias = "eu-west-2"
    version = "~> 2.0"
    region = "eu-west-2"
}

resource "aws_instance" "dev-eu-west-3" {
    ami = var.amis["eu-west-3"]
    instance_type = var.instance_type
    key_name = var.key_name
    tags = {
        Name = "dev in region eu-west-3"
    }
    vpc_security_group_ids = ["${aws_security_group.ssh-access.id}"]    
}


resource "aws_instance" "dev-eu-west-2" {
    provider = aws.eu-west-2
    ami = var.amis["eu-west-2"]
    instance_type = var.instance_type
    key_name = var.key_name
    tags = {
        Name = "dev in region eu-west-2"
    }
    vpc_security_group_ids = ["${aws_security_group.ssh-access-eu-west-2.id}"]
    depends_on = [aws_s3_bucket.bucket-dev-eu-west-2]
}

resource "aws_s3_bucket" "bucket-dev-eu-west-2" {
  provider = aws.eu-west-2
  bucket = "bucket-dev-eu-west-2"
  acl    = "private"

  tags = {
    Name = "bucket-dev-eu-west-2"
  }
}
