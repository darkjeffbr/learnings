/*
output "dev-ip-adress" { # The name here will be in the output along with its value
  value       = "Ip adress : ${element(aws_instance.dev.*.public_ip, 0)}"
}
*/
output "dev-ip-adress-0" { # The name here will be in the output along with its value
  value       = "${aws_instance.dev[0].public_ip}"
}

output "dev-ip-adress-1" { 
  value       = "${aws_instance.dev[1].public_ip}"
}