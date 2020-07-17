provider "aws" {
    version = "~> 2.0"
    region = "eu-west-3"
}

resource "aws_instance" "dev" {
    count = 3
    ami = "ami-0bfddfb1ccc3a6993"
    instance_type = "t2.micro"
    key_name = "terraform-tutorial"
    tags = {
        Name = "dev-${count.index}"
    }
}
