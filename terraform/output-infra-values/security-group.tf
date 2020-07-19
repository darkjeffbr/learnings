resource "aws_security_group" "ssh-access" {
  name        = "ssh-access"
  description = "Allow SSH into EC2 instances"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = var.cidrs_remote_access
  }

  tags = {
    Name = "ssh-access"
  }
}