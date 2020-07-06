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
- Layer 4 - TCP / UDP
- Handle millions of request per seconds
- Less latency ~ 100ms (vs 400ms for ALB)
- Has one static IP per AZ, support assigning Elastic IP
- Not include in the free tier



### Load Balancer Stickiness
- Same client is always redirected to the same instance behind load balancer
- Works for CLB & ALB
- The "cookie" used for stickness has an expiration date you control
- **Use case**: make sure user doesn't lose his session data
- **ATENTION**: Enabling stickness may bring imbalance to the load over the backend EC2 instances

### Cross-Zone Load Balancing
- With Cross Zone Load Balancing: each load balancer instance distributes evenly across all registered instances in all AZ.
- Classic Load Balancer:
    - Disabled by default
    - No charges for inter AZ data if enabled
- Application Load Balancer
    - Always on (can't be disabled)
    - No charges for inter AZ data
- Network Load Balancer
    - Disabled by default
    - You pay charges ($) for inter AZ data if enabled

### Load Balancer - SSL Certificates
 - The load balancer uses an X.509 certificate (SSL/TLS server certificate)
 - You can manage certificates using AWS Certificate Manager (ACM)
 - You can create/upload your own certificates alternatively
 - HTTPS listener:
     - Must specify default certificate
     - Is possible to add optional certs to support multiple domains
     - Clients can use Server Name Indication (SNI) to specify the hostname they reach
     - Ability to specify a security policy to support older versions of SSL/TLS (legacy clients)
#### Server name Indication
- SNI solves the problem of loading multiple SSL certificates onto one web server ( to serve multiple websites )
- Requires the client to indicate the hostname of the target server in the initial SSL handshake
- The server will find the correct certificate, or return a defult one
- Note:
    - Only works for ALB & NLB, CloudFront
    - Does not work for CLB

### Connection Draining
- Naming:
    - CLB: Connection Draining
    - Target Group: Deregistration delay
- Time to complete "in-flight requests" while the instance is de-registering or unhealthy
- Between 1 to 3600 seconds, default 300 seconds
- Disable if set value to 0
- Set to a low value if your requests are short

### Auto Scaling Group - ASG
- Increase/Decrease the number of instances as the load on the website/application changes
    - In the cloud it is possilbe to create and get rid of servers very quickly
- Goal:
    - Scale out (add EC2 instances) to match an increased load
    - Scale in (remove EC2 instances) to match a decreased load
    - Ensure we have a minimum and a maximum number of running machines
    - Automatically register new instances to a load balancer
- ASG have the following attributes:
    - Launch configuration:
        - AMI + Instance type
        - EC2 User Data
        - EBS Volumes
        - Security Groups
        - SSH Key Pair
    - Min/Max/Initial capacity
    - Network + Subnets Information
    - Load Balancer Information
- Auto Scaling Alarms
    - Scale and ASG based on CloudWatch alarms
    - An alarm monitors a metric
    - **Metrics are computed for the overall ASG instances**
- It is now possible to define "better" auto scaling rules managed by EC2
    - Average CPU Usage
    - Number of requests on the ELB per instance
    - Average Network In/Out
- Is is possible to create custom metrics, sending metrics from application on EC2 to CloudWatch
- IAM roles attached to an ASG will get assigned to EC2 instances
- ASG are free. You pay for the underlying resources being launched
- **Launch Template vs Launch Configuration**
    - Both can use AMI set instance type, key pair, security groups and other parameters to launch an EC2 instance.
	- Launch Configuration (legacy)
	    - Must be re-created every time
	- Luanch Template (newer)
	    - Can have multiple versions
		- Create parameters subsets (partial configuration for re-use and inheritance)
		- Provision using both On-Demand and Spot instances (or a mix)
		- Can use T2 unlimited burst feature
		- **Recommended by AWS**
#### Scaling Policies
- Target Tracking Scaling
    - Most simple. Example: average ASG CPU to stay aroung 40%
- Simple / Step Scaling
    - Based on CloudWatch alarm then scale in/out
- Scheduled Actions
    - Anticipate a scaling based on known usage patterns. Example: increase capacity to 10 at 5pm on Fridays
- **Scaling Colldowns**
    - The cooldown period helps to ensure that your Auto Scaling group doesn't launch or terminate additional instances before the previous scaling activity takes effect.

# Elastic Block Store - EBS
- Is a network drive that can be attached to an EC2 instance
    - Can be detached and attached from an instance to another quickly
- It allows instances to persist data
- It is locked to an Availability Zone
- It has a provisioned capacity
    - Bill is for the provisioned capacity ( not for used capacity )

## EBS Volume Types
- There are four types of EBS volumes
    - GP2 (SSD) : General purpose SSD, it balances price and performance
	    - General purpose volumes (cheap)
	    - 3 IOPS / GiB, minimum 100 IOPS, burst to 3000 IOPS, max 16000 IOPS
		- 1GiB - 16TiB, +1 TB = +3000 IOPS
	- IO1 (SSD) : Highest-performance SSD for mission-critical low-latency or high-throughput workloads
	    - Provisioned IOPS (expensive)
	    - Min 100 IOPS, Max 64000 IOPS (Nitro) or 32000 (other)
		- 4GiB - 16TiB. Size of volume and IOPS are independent
	- ST1 (HDD) : Low cost HDD volume for frequently accessed, throughput-intensive workloads
	    - Throughput Optimized HDD
	    - 500GiB - 16TiB, 500MiB/s throughput
	- SC1 (HDD) : Lowest cost HDD volumen for less frequently accessed workloads
	    - Cold HDD, Infrequently accessed data
		- 250GiB - 16TiB, 250MiB/s throughput
- **Only GP2 and IO1 can be used as boot volumes**

## EBS Snapshots
- Incremental
- Use IO and shouldn't run while the application is handling a lot of traffic
- It is stored in S3 ( but you won't directly see them )
- Not necessary to detach volume, although it is recommended
- Max 100.000 snapshots
- Can copy snapshots across AZ or Region
- Can make an AMI from snapshot
- EBS volumes restored by snapshots need to be pre-warmed (using fio or dd command to read the entire volume)
- Snapshots can be automated using Amazon Data Lifecycle Manager

## EBS Encryption
- When an EBS encrypted volume is created
    - Data inside volume is encrypted
	- Data in flight moving between instance and volume is encrypted
	- Snapshots are encrypted
	- Volumes created from snapshots are encrypted
- Encryption/Decryption are handled transparently (you have nothing to do)
- It has minimal impact on latency
- EBS encryption leverages keys from KMS (AES-256)
- Copying an unencrypted snaptshot allows encryption

## EBS vs Instance Store
- Some instances do not come with root EBS volumes
- They come with "Instance Store" (= ephemeral storage)
- Instance storage is physically attached to the machine (EBS is a network drive)
    - Very high IOPS (because physical)
	- Disks up to 7.5TiB (can change over time)
	- Block storeage (just like EBS)
	- Cannot be increased in size
	- Risk of data loss in case of hardware failure
- Pros:
    - Better I/O performance
	- Good for buffer / cache / scratch data / temporary content
	- Data survives reboots
- Cons:
    - On stop or termination, the instace storage is lost
	- Can't resize instance store
	- Backups must be operated by user

## EBS RAID Options
- EBS is already redundant storage (replicated within an AZ)
- RAID is possible as long as OS supports it
- RAID options are:
    - RAID 0
	- RAID 1
	- RAID 5 (not recommended for EBS)
	- RAID 6 (not recommended for EBS)
	
### RAID 0 (increase performance)
- Combine 2 or more volumes and getting the total disk space and I/O
- If one disk fails, all the data is lost
- Using this option is possible to have a very big disk with a lot of IOPS
    - Sum up the size and sum up the IOPS

### RAID 1 (increase fault tolerance)
- RAID 1 = Mirroring a volume to another
- If one disk fails, our logical volume is still working
- We have to send data to two EBS volume at the same time (2x network)

# Elastic File System - EFS
- Managed NFS (Network File System) that can be mounted on many EC2
- EFS works with EC2 instances in multi-AZ
- Highly available, scalable, expensive (3x gp2), **pay per use**
- Uses NFSv4.1 protocol
- Uses security group to control access to EFS
- **Compatible with Linux based AMI (not Windows)**
- Encryption at rest using KMS
- POSIX file system (~Linux) that has a standard file API
- Fily system scales automatically, pay-per-use, no capacity planning!

## EFS - Performance & Storage Class

### EFS Scale
- 1000s of concurrent NFS clients, 10GB+/s throughput
- Grow to Petabyte-scale network file system, automatically

### Performance mode (set at EFS creation time)
- General purpose (default): latency-sensitive (web server, CMS, ...)
- Max I/O - higher latency, throughput, highly parallel (big data, media processing)

### Storage Tiers (lifecycle management feature - move files after N days)
- Standard: for frequently accessed files
- Infrequent access (EFS-IA): cost to retrieve files, lower price to store
