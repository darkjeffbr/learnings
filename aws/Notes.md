# Regions and availability zones
- **Region** is a set of data centers.
- Each region has a name corresponding to its geographical location.
- Services are region-scoped.
- **Availability zone (AZ)** is a data data center.
- A region can have from 2 up to 6 AZ.
- AZs are separated from each other to avoid disaster but they are connected through high-speed and low latency network.

# Identity and Access Management- IAM
- Where security is managed: users, groups and roles.
- Policies are JSON documents.
- IAM has a global view

- One IAM User per PHYSICAL PERSON
- One IAM Role per Application
- IAM credentials should NEVER BE SHARED
- Never, ever, ever, ever write IAM credentials in code. EVER!
- And even less, NEVER EVER EVER COMMIT YOUR IAM credentials
- **Never use the ROOT account except for initial setup**
- **Never ser ROOT IAM Credentials**

# EC2
- Provide virtual machines with different and custom configurations
- An instance have two different IPs:
-- Public: Changes every time the instance is restarted. Used to access the instance from the outside-world
-- Private: Identifies the instance inside AWS network. Do not change with restarts
- Amazon also provide a so called Elastic IP, which is an static ip address bound to you account that can be associated with an instance. An user can have up to 4 Elastic IP, more thant that is necessary to talk to AWS.

# Security groups
- Work like firewalls
