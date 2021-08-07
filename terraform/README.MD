# Terraform
My terraform playground :)

## Commands
terraform init

terraform plan

terraform plan --var-file custom.tfvars # When no var-file is specified terraform fall back to the file terraform.tfvars

terraform apply

terraform destroy

terraform destroy -target resource.name
Important to remove the resource from the .tf file, otherwise in the next apply they will be created again

terraform show [-json]

terraform refresh

terraform fmt

terraform state list # List resources in the Terraform state

terraform state rm resource_type.resource_name # Remove a resource only from the state file

terraform console

terraform import

terraform apply -refresh-only # Has the same effect as the refresh command, but gives the opportunity to review the possible changes

terraform plan -var-file custom-values.tfvars -var custom_var=1 -var custom_var=2 # Terraform will use the last provided value for custom_var, which is in this case 2. So the order is important!!

### AWS commands
aws ec2 describe-security-groups