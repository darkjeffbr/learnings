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
    count = 1
    ami = "ami-0bfddfb1ccc3a6993"
    instance_type = "t2.micro"
    key_name = "terraform-tutorial"
    tags = {
        Name = "dev in region eu-west-3"
    }
    vpc_security_group_ids = ["${aws_security_group.ssh-access.id}"]    
}


resource "aws_instance" "dev-eu-west-2" {
    provider = aws.eu-west-2
    ami = "ami-04122be15033aa7ec"
    instance_type = "t2.micro"
    key_name = "terraform-tutorial"
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
