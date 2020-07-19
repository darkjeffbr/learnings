# Terraform
My terraform playground :)

## Commands
terraform init

terraform plan

terraform apply

terraform destroy

terraform destroy -target resource.name
Important to remove the resource from the .tf file, otherwise in the next apply they will be created again

terraform show

terraform refresh

### AWS commands
aws ec2 describe-security-groups