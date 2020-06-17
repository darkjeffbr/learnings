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
    - Public: Changes every time the instance is restarted. Used to access the instance from the outside-world
    - Private: Identifies the instance inside AWS network. Do not change with restarts
- Amazon also provide a so called Elastic IP, which is an static ip address bound to you account that can be associated with an instance. An user can have up to 4 Elastic IP, more thant that is necessary to talk to AWS.

## Instance Types - Main Ones
- R: applications that needs a lot of RAM - in-memory caches
- C: applications that needs good CPU - compute / databases
- M: applications that are balanced (think "medium") - general / web app
- I: applications that need good local I/O (instance storage) - databases
- G: applications that need a GPU - video rendering / machine learning

- T2/T3: burstable instances (up to a capacity)
- T2/T3 - unlimited: unlimited burst

## Amazon Machine Image - AMI
- AMI - an image that we use to create our instances
- Custom AMI are built for a specific AWS region(!)
- Custom AMI take space in Amazon S3 ( not possible to see in S3 console)
- By default AMI are private to an account/region, but can be made public

## Elastic Network Interface - ENI
- Represent a virtual network card
- Can be attached / detached from an instance
- Can have one Elastic IP and one public IP
- One or more security groups


# Security groups
- Work like firewalls

# EC2 Load Balancing - ELB
- Why ?
    - Spread load across multiple downstream instances
    - Expose a single point of access (DNS) to your application
    - Seamlessly handle failures of downstream instances
    - Do regular health checks to your instances
    - Provide SSL termination (HTTPS) for your websites
    - Enforce stickiness with cookies
    - High availability across zones
    - Separate public traffic from private traffic
## Health Checks
- Super important! The way to know if instances are available to reply to requests
- Is done on a port and a route
- Response is not 200(OK), instance is unhealthy

## Types of load balances on AWS
- It is possible to set a private or public load balancer
### Classic Load Balancer (v1) - CLB
- Old generation - 2009
- HTTP, HTTPS, TCP
### Application Load Balancer - ALB
- Layer 7 only (HTTP/HTTPS)
- New generation - 2016
- HTTP, HTTPS, WebSocket
- Load balancing to multiple HTTP applications across machines (target groups)
- Load balancing to multiple applications on the same machine (ex: containers)
- Support redicts (from HTTP to HTTPS for example)
- Routing tables to different target groups:
    - Based on path in URL
    - Based on hostname in URL
    - Based on query string, headers
- Great fit for micro services and container-based application
- ALB can route to multiple target groups
- Health checks are at the target group level


- IMPORTANT: Application server do not see the IP of the client directly
    - True IP -> Header X-Forwarded-For
    - Port -> X-Forwarded-Port
    - Protocol -> X-Forwarded-Proto

#### Target Groups
- EC2 instances
- ECS tasks
- Lambda functions
- IP Address

### Network Load Balancer
- New generation - 2017
- TCP, TLS (secure TCP) and UDP
