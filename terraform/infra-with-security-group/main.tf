provider "aws" {
    version = "~> 2.0"
    region = "eu-west-3"
}

resource "aws_instance" "dev" {
    count = 1
    ami = "ami-0bfddfb1ccc3a6993"
    instance_type = "t2.micro"
    key_name = "terraform-tutorial"
    tags = {
        Name = "dev-${count.index}"
    }
    # vpc_security_group_ids = ["sg-0ab3e320980b0c2f9"] # Security group IDs (List)
    vpc_security_group_ids = ["${aws_security_group.ssh-access.id}"] # Reference to security group
}

resource "aws_security_group" "ssh-access" {
  name        = "ssh-access"
  description = "Allow SSH into EC2 instances"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] # List of allowed IP. It is a godd practice to restrict ingress to only necessary IPs and ports.
  }

  tags = {
    Name = "ssh-access"
  }
}

