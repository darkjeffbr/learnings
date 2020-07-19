variable "amis" {
  type        = map
  default     = {
      "eu-west-3" = "ami-0bfddfb1ccc3a6993"
  }
}

variable "cidrs_remote_access" {
  type        = list
  default     = ["0.0.0.0/0"]
}

variable "instance_type" {
  type        = string
  default     = "t2.micro"
}

variable "key_name" { # by default if no type is especified, terraform assumes string
  default     = "terraform-tutorial"
}

