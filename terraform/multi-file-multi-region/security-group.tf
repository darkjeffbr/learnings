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

resource "aws_security_group" "ssh-access-eu-west-2" {
  provider = aws.eu-west-2
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