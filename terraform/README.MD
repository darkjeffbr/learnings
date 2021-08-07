# Terraform
My terraform playground :)

## Commands
terraform init

terraform plan

terraform apply

terraform destroy

terraform destroy -target resource.name
Important to remove the resource from the .tf file, otherwise in the next apply they will be created again

terraform show [-json]

terraform refresh

terraform fmt

terraform state list # List resources in the Terraform state

terraform console

### AWS commands
aws ec2 describe-security-groups