provider "aws" {
    version = "~> 2.0"
    region = "eu-west-3"
}

resource "aws_instance" "dev-with-bucket" {
    count = 1
    ami = "ami-0bfddfb1ccc3a6993"
    instance_type = "t2.micro"
    key_name = "terraform-tutorial"
    tags = {
        Name = "dev with bucket"
    }
    vpc_security_group_ids = ["${aws_security_group.ssh-access.id}"]
    depends_on = [aws_s3_bucket.dev-bucket]
}

resource "aws_instance" "dev-without-bucket" {
    count = 1
    ami = "ami-0bfddfb1ccc3a6993"
    instance_type = "t2.micro"
    key_name = "terraform-tutorial"
    tags = {
        Name = "dev without bucket"
    }
    vpc_security_group_ids = ["${aws_security_group.ssh-access.id}"]
}

resource "aws_security_group" "ssh-access" {
  name        = "ssh-access"
  description = "Allow SSH into EC2 instances"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "ssh-access"
  }
}

resource "aws_s3_bucket" "dev-bucket" {
  bucket = "jefferson-terraform-dev-bucket"
  acl    = "private"

  tags = {
    Name = "jefferson-terraform-dev-bucket"
  }
}
