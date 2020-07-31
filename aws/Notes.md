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
	- Actions that can be performed on AWS resources
	- There is an AWS Policy Simulator to test out what is possible to do with a given policy
- IAM has a global view

- One IAM User per PHYSICAL PERSON
- One IAM Role per Application
	- IAM roles are attached to applications or EC2 instances
	- IAM roles can come with a policy authorizing exactly what the EC2 instace should be able to do
		- EC2 Instances can then use these profiles automatically without any additional configurations
		- **THIS IS THE BEST PRACTICE ON AWS AND YOU SHOULD 100% DO THIS**
		- An EC2 instance can have only one IAM role attached to it
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

## Instance Metadata
- AWS EC2 Instance Metadata is powerful but one of the least known features to developers
- It allows AWS EC2 instances to "learn about themselves" **without using an IAM Role for that purpose**
- The URL is http://169.254.169.254/latest/metadata
- You can retrieve the IAM Role name from the metadata, but you CANNOT retrieve the IAM policy
- Metadata = Info about the EC2 instance
- Userdata = Launch script of the EC2 instance

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

# Relational Database Service (RDS)
- It is a managed database service for relational databases
- Allows creation of database in the cloud
- Supported databases:
    - Postgres
	- MySQL
	- MariaDB
	- Oracle
	- Microsoft SQL Server
	- Aurora (AWS Proprietary database)
## RDS vs DB on EC2
- RDS is a managed service:
    - Automated provisioning, OS patching
	- Continuous backups and restore to specific timestamp
	- Monitoring dashboards
	- Read replicas for improved read performance
	- Multi AZ setup for disaster recovery (DR)
	- Maintenance windows for upgrades
	- Scaling capability (vertical and horizontal)
	- Storage backed by EBS (gp2 or io1)
- It is not possible to SSH into RDS instances

## RDS Backups
- Automatically enabled
- Automated backups:
    - Daily full backup of the database (during the maintenance window)
	- Transaction logs are backed-up every 5 minutes
	- Ability to restore to any point in time (from oldest backup up to 5 minutes ago)
	- 7 days retention (can be increased to 35 days)
- DB snapshots
    - Manually triggered by the user
	- Retention of backup for as long as you want

## RDS Read Replicas for read scalability
- There is a main RDS DB instance with read/write
    - Can have read replicas where data is asynchronously replicated
- Up to 5 Read Replicas
- Replication is **ASYNC**, so reads are eventually consistent
- Replicas can be promoted to their own DB
    - The replicas is out of replication mechanism and have read/write
- Applications must update the connection string to leverage read replicas

### Use Cases
- There is a production database that is taking on normal load
- We want to run a reporting application to run some analytics
- Create a Read Replica to run the new workload there
- The production application is unaffected
- Read replicas are used for SELECT (=read) only kind of statements (**not INSERT, UPDATE, DELETE**)

### RDS Read Replicas - Network cost
- In AWS there's a network cost when data goes from one AZ to another
    - Main RDS instance in AZ a, replication cross AZ to AZ b. It will cost money.
- To reduce the cost, you can have  your Read Replicas in the same AZ
    - Main RDS instance and replica in the same AZ. No cost in data replication.
	
### RDS Multi AZ (Disaster Recovery)
- **SYNC** replication
    - There is a RDS master DB instance (AZ A) and a RDS DB instance standby (AZ B)
	- Any changes to the master to be accepted should also be applied successfully to the standby instance
- One DNS name - automatic app failover to standby
- Increase **availability**
- Failover in case of loss of AZ, loss of network, instance or storage failure
- No manual intervention in apps
- Not used for scaling

- **NOTE**: The Read Replicas can be setup as a Multi AZ for Disaster Recovery (DR)

## RDS Security - Encryption
- At rest encryption:
    - Possibility to encrypt the master & read replicas with AWS KMS - AES-256 encryption
	- Encryption has to be defined at launch time
	- **If master is not encrypted, the read replicas *cannot* be encrypted**
	- Transparent Data Encryption (TDE) available for Oracle and SQL Server
- In-flight encryption:
    - SSL certificates to encrypt data to RDS in flight
	- Provide SSL options with trust certificate when connecting to database
	- To enforce SSL:
	    - **PostgreSQL:** rds.force_ssl=1 in the AWS RDS Console (Parameter Groups)
		- **MySQL:** Within the DB:
		`GRANT USAGE ON *.* TO 'mysqluser'@'%' REQUIRE SSL;`

### RDS Encryption - Operations
- Encrypting RDS backups
    - Snapshots of un-encrypted RDS databases are un-encrypted
	- Snapshots of encrypted RDS databases are encrypted
	- Can copy a snapshot into an encrypted one
- To encrypt an un-encrypted RDS database:
    - Create a snapshot of the un-encrypted database
	- Copy the snapshot and enable encryption for the snapshot
	- Restore the database from the encryption snapshot
	- Migrate applications to the new database, and delete the old database
## RDS Security - Network & IAM
- Network security
    - RDS databases are usually deployed within a private subnet, not in a public one
	- RDS security works by leveraging security groups (same concept as EC2)
	    - Controls which IP / security group can **communicate** with RDS
- Access Management
    - IAM policies help control who can **manage** AWS RDS (through the RDS API)
	- Traditional Username & Password can be used to login into the database
	- IAM-based authentication can be used to login into RDS MySQL and PostgreSQL
	    - You don't need a password, just an authentication token obtained through IAM & RDS API calls
		- Auth token has a lifetime of 15 minutes
	    - Benefits:
		    - Network in/out must be encrypted using SSL
			- IAM to centrally manage users instead of DB
			- Can leverage IAM roles and EC2 instances profiles for easy integration
	
# Amazon Aurora
- Is a proprietary technology from AWS (not open sourced)
- Postgres and MySQL are both supported as Aurora DB (your drivers will work as if Aurora was a Postgres or MySQL database)
- It is "AWS coud optimized" and claims 5x performance improvement over MySQL on RDS, over 3x the performance of Postgres on RDS
- Storage automatically grows in increments of 10GB, up to 64TB
- Can have 15 replicas while MySQL can have only 5, and the replication process is faster (sub 10ms replica lag)
- Failover in Aurora is instantaneous. It's high-availability navite.
- It cost more than RDS (20% more) - but is more efficient

## Features
- Automatic fail-over
- Backup and recovery
- Isolation and security
- Industry compliance
- Push-button scaling
- Automated patching with zero downtime
- Advanced monitoring
- Routine maintenance
- Backtrack: restore data at any point of time without using backups

## Aurora High Availability and Read Scaling
- 6 copies of your data across 3 AZ:
    - 4 copies out of 6 needed for writes
	- 3 copies out of 4 needed for reads
	- Self healing with peer-to-peer replication
	- Storage is striped across 100s of volumes
- One Aurora Instance takes writes (master)
- Automated failover for master in less than 30 seconds
- Master + up to 15 Aurora Read Replicas serve reads
- Support for Cross Region Replication

- Shared storage volume
    - Replication + Self Healing + Auto Expanding

## Aurora DB Cluster
- Writer Endpoint - Pointing to the master
- Reader Endpoint - Connection Load Balancing
    - Auto Scaling
	
## Aurora Security
- Similar to RDS because uses the same engines
- Encryption at rest using KMS
- Automated backups, snapshots and replicas are also encrypted
- Encryption in flight using SSL (same process as MySQL or Postgres)
- **Possibility to authenticate using IAM token (same method as RDS)**
- You are responsible for protecting the instances with security groups
- You can't SSH

## Aurora Serverless
- Automated database instantiation and auto-scaling based on actual usage
- Good for infrequent, intermittent or unpredictable workloads
- No capacity planning needed
- Pay per second, can be more cost-effective

- There is a shared storage volumen and a proxy fleet (managed by Aurora)
    - When there is more workload then more replicas will be instantiated
	- When there is no workload replicas are automatically released up to 0 instances

## Global Aurora
- Aurora Cross Region Read Replicas:
    - Useful for disaster recovery
	- Simple to put in place
- Aurora Global Database (recommended):
    - 1 Primary Region (read/write)
	- Up to 5 secondary (read-only) regions, relication lag is less than 1 second
	- Up to 16 Read Replicas per secondary region
	- Help for decreasing latency
	- Promoting another region (for disaster recovery) has an RTO of < 1 minute

# ElastiCache
- Is to get managed Redis or Memcached
- Caches are in-memory databases with really high performance, low latency
- Helps reduce load off of databases for read intensive workloads
- Helps make your application stateless
- Write scaling using sharding
- Reads scaling using read replicas
- Multi AZ with Failover Capability
- AWS takes care of OS maintenance/patching, optimizations, setup, configuration, monitoring, failure recovery and backups

## Redis vs Memcached

### Redis
- Multi AZ with Auto-Failover
- Read Replicas to scale reads and have high availability
- Data Durability using AOF persitence
- Backup and restore features

### Memcached
- Multi-node for partitioning of data (sharding)
- Non persistent
- No backup and restore
- Multi-threaded architecture

## Solution Architecture

### DB Cache
- Applications queries ElastiCache, if not available, get from RDS and store in ElastiCache
- Helps relieve load in RDS
- Cache must have an invalidation strategy to make sure only the most current data is used in there

### User Session Store
- User logs int oany of the applications
    - Maybe we have an autoscaling group
- The application writes the session data into ElastiCache
- The user hits another instance of our application
- The instance retrieves the data and the user is already logged in

### Patterns for ElastiCache
- **Lazy Loading:** all the read data is cached, data can become stale in cache
- **Write Through:** Adds or update data in the cache when written to a DB (no stale data)
- **Session Store:** store temporary session data in a cache (using TTL features)

## Cache Security
- All caches in ElastiCache:
    - Support SSL in flight encryption
	- **Do not support IAM authentication**
	- IAM policies on ElastiCache are only used for AWS API-level security
- Redis Auth
    - You can set a "password/token" when you create a Redis cluster
	- This is an extra level of security for your cache (on top of security groups)
- Memcached
    - Supports SASL-based authentication (advanced)
	
# Route53
- It is a managed DNS
- Most common records are:
    - A: hostname to IPv4
	- AAAA: hostname to IPv6
	- CNAME: hostname to hostname
	- Alias: hostname to AWS resource
- Can use public domain names (you own or buy) and private domain names ( can be resolved by the application in a VPC)
- Has advanced features:
    - Load balancing (through DNS - also called client load balancing)
	- Health checks (although limited)
	- Routing policy: simple, failover, geolocation, latency, weighted, multi value
- You pay $0.50 per month per hosted zone
- **DNS Records TTL (Time To Live)**
   - WebBrowser/Client cache the DNS response for a given time so as to not overload the DNS with a lot of queries
   - TTL is mandatory for each DNS record
   - High TTL: (e.g. 24hr)
       - Less traffic on DNS
	   - Possibly outdated records
   - Low TTL: (e.g. 60s)
       - More traffic on DNS
	   - Records are outdated for less time
	   - Easy to change records
### CNAME vs Alias
- AWS resources (LoadBalancer, CloudFront ...) expose an AWS hostname: lbl-1234.us-east-2.elb.amazonaws.com and we want myapp.mydomain.com
    - **CNAME**
	    - Points a hostname to any other hostname (app.mydomain.com => blabla.anything.com)
		- **__ONLY FOR NON ROOT DOMAIN (ex.: something.mydomain.com)__**
	- **Alias**
	    - Points a hostname to an **AWS resource** (app.mydomain.com => blabla.amazonaws.com)
		- **__Works for ROOT DOMAIN and NON ROOT DOMAIN (aka mydomain.com)__**
		- Free of charges
		- Native health check
## Routing Policy

### Simple Routing Policy
- Use when you need to redirect to a single resource
- You can't attach health checks to simple routing policy
- If multiple values are returned, a random one is chosen by the **client**

### Weighted Routing Policy
- Control the % of the requests that go to specific endpoint
- Helpful to test 1% of traffic on new app version for example
- Helpful to split traffic between two regions
- Can be associated with Health Checks

### Latency Routing Policy
- Redirect to the server that has the least latency close to us
- Super helpful when latency of users is a priority
- Latency is evaluated in terms of user to designated AWS Region
- Germany may be redirected to the US (fi that's the lowest latency)

### Failover Routing Policy
- There is a primary instance
    - Mandatory health check
	- In case health check fails, Route53 will failover to the secondary instance

### Geo Location Routing Policy
- Different from Latency based!
- This is routing based on user location
- We specify: traffic from this location should go to this specific IP
- Should create a "default" policy (in case there's no match on location)

### Multi Value Routing Policy
- Use when routing traffic to multiple resources
- Want to associate a Route 53 health checks with records
- Up to 8 healthy records are returned for each Multi Value query
- Multi Value is not a substitute for having an ELB

## Health Checks
- Have X health checks failted -> unhealthy (default 3)
- After X health checks passed -> health (default 3)
- Default health check interval: 30s (can set to 10s - higher cost)
- About 15 health checkers will check the endpoint health
    - One request every 2 seconds on average
- Can have HTTP, TCP and HTTPS health checks (no SSL verification)
- Possibility of integrating the health check with CloudWatch
- Health checks can be linked to Route53 DNS queries!

## 3rd Party Domains and Route53
- Route53 as a Registrar
    - A domain name registrar is an organization that manages the reservation of Internet domain names
	- Famous names:
	    - GoDaddy
		- Google Domains
		- Etc ...
	- And also ... Route53 (e.g. AWS)!
	- Domain Registrar != DNS
- **If you buy your domain on 3rd party website, you can still use Route53**
    - 1) Create a Hosted Zone in Route 53
	- 2) Update NS Records on 3rd party website to use Route53 **name servers**
	
# ElasticBeanStalk
- Managed service
    - Is a developer centric view of deploying an application on AWS
	    - It uses all the components: EC2, ASG, ELB, RDS, etc...
		- All in one view that is easy to make sense of!
		- We still have full control over the configuration
    - Instance configuration / OS is handled by beanstalk
	- Deployment strategy is configurable but performed by ElasticBeanStalk
- It is free but you pay for the underlying instances / resources
- Just the application code is the responsability of the developer
- Three architecture models:
    - Single Instance deployment: good for dev
	- LB + ASG : great for production or pre-production web applications
	- ASG only : great for non-web apps in production (workers, etc...)

- ElasticBeanStalk has three components:
    - Application
	- Application version : each deployment gets assigned a version
	- Environment name (dev, test, prod ...): free naming
- You deploy application versions to environments and can promote application versions to the next environment
- Rollback feature to previous application version
- Full control over lifecycle of environments
- Support for many platforms:
    - Go, Java SE, Java with Tomcat, .NET on Windows Server with IIS, Node.js ...
	- Single Container Docker, Multicontainer Docker, Preconfigured Docker
- If not supported, you can write your custom platform (advanced)

# S3
- Allows people to store objects (files) in "buckets" (directories)

## Buckets
- A bucket must have a globally unique name
- Buckets are defined at the region level
- Naming convention:
    - No uppercase
	- No underscore
	- 3-63 characters long
	- Not an IP
	- Must start with lowercase letter or number

## Objects
- Objects (files) have a key
- The **key** is the FULL path:
    - s3://my-bucket/**my_file.txt**
	- s3://my-bucket/**my_folder/another_folder/my_file.txt**
- The key is composed of _prefix_ + **object name**
    - s3://my-bucket/ _my_folder/another_folder_/_**my_file.txt**
- There is no concept of "directories" within buckets (although the UI will trick you to think otherwise)
	- Just keys with very long names that contain slashes ("/")
- Object values are the content of the body:
    - Max Object size is 5TB (5000GB)
	- If uploading more than 5GB, muse use "multi-part upload"
- Metadata (list of text key / value pairs - system or user metadata)
- Tags (Unicode key / value pair - up to 10) - useful for security / lifecycle
- Version ID (if versioning is enabled)

## Versioning
- It is possible to version files in Amazon S3
- It is enabled at the **bucket level**
- Same key overwrite will increment the "version": 1, 2, 3, ...
	- It is possible to delete a specific version of an object
- It is best practice to version your buckets
    - Protect against unintended deletes (ability to restore a version)
	    - When file is deleted, adds a delete marker. Which is a new version of the file with size 0
		- When the delete marker is deleted, the file is restored to its previous version
	- Easy roll back to previous version
- Notes:
	- Any file that is not versioned prior to enabling versioning will have version "null"
	- Suspending versioning does not delete the previous versions

## S3 Encryption for Objects
- There are 4 methods of encrypting objects in S3
    - SSE-S3: encrypts S3 objects using keys handled & managed by AWS
	- SSE-KMS: leverage AWS Key Management Service to manage encryption keys
	- SSE-C: when you want to manage your own encryption keys
	- Client Side Encryption
- It is important to understand which ones are adapted to which situation

### Server Side Encryption - S3 (SSE-S3)
- Encryption using keys handled & managed by Amazon S3
- Object is encrypted server side
- AES-256 encryption type
- Must set header: **"x-amz-server-side-encryption":"AES256"**
    - Send object through HTTP/S + Header
	- Amazon will apply an encryption using the object + S3 Managed Data Key
	- **IMPORTANT: Data key is entirely owned and managed by Amazon**
	
### Server Side Encryption - Key Management System (SSE-KMS)
- Encryption using keys handled & managed by KMS
- Object is encrypted server side
- AES-256 encryption type
- Must set header: **"x-amz-server-side-encryption":"aws:kms"**
    - Send object through HTTP/S + Header
	- Amazon will apply an encryption using the object + KMS Customer Master Key (CMK)
	
### Server Side Encryption - Customer (SSE-C)
- Server-Side encryption using data keys fully managed by the customer outside of AWS
- Amazon S3 does not store the encryption key you provide
- HTTS must be used
    - Sending a secret so the connection must be encrypted in both ends
- Encryption key must be provided in HTTP headers, for every HTTP request made
    - Needs to be sent every request, because it is discarted every single time
- Send object through HTTPS + Client side data key
- Amazon will apply an encryption using the object + Client-provided data key
- To retrieve the file then is necessary to provide the same client side data key used in the upload
- This requires more management because you manage the keys

### Client Side Encryption
- Client library suchas as the Amazon S3 Encryption Client
- Clients must encrypt data themselves before sending to S3
- Clients must decrypt data themselves when retrieving from S3
- Customer fully manages the keys and encryption cycle

### Encryption in transit (SSL/TLS)
- Amazon S3 exposes:
    - HTTP endpoint : non encrypted
	- HTTPS endpoint : encryption in flight
- You're free to use the endpoint you want, but HTTPS is recommended
- Most clients would use the HTTPS endpoint by default

- HTTPS is mandatory for SSE-C
- Encryption in flight is also called SSL/TLS

## S3 Security
- User based
	- IAM policies - which API calls should be allowed for a specific from IAM console
- Resource based
	- Bucket Policies - bucket wide rules from the S3 console - allows cross account
	- Object Access Control List (ACL) - finer grain
	- Bucket Access Control List (ACL) - less common
- Note: an IAM principial can access an S3 object if
	- the user IAM permissions allow it **OR** the resource policy ALLOWS it
	- **AND** there is no explicit DENY
		- Example: If IAM permissions allow a user but the bucket policy explicit deny an user, the the access will be denied
### S3 Bucket Policies
- JSON based policies
    - Resources: bucket and objects
	- Actions: Set of API to Allow or Deny
	- Effect: Allow / Deny
	- Principal: The account or user to apply the policy to
	- Example:
		```javascript
		{
			"Version": "2012-10-17",
			"Statement": [
				{
					"Sid": "PublicRead",
					"Effect": "Allow",
					"Principal": "*",
					"Action": [
						"s3:GetObject"
					],
					"Resource": [
						"arn:aws:s3:::examplebucket/*"
					]
				}
			]
		}
		```
- Use S3 bucket for policy to:
	- Grant public access to the bucket
	- Force objects to be encrypted at upload
	- Grant access to another account (Cross Account)

#### Bucket setttings for Block Public Access
- Block public access to buckets and objects granted through
	- new access control list (ACLs)
	- any access control list (ACLs)
	- new public bucket or access point policies
- Block public and cross-account access to buckets and objects through any public bucket or access point policies
- **These settings were created to prevent company data leaks**
- If you know your bucket should never be public, leave these on
- Can be set at the account level

### S3 Security - Other
- Networking:
	- Supports VPC Endpoints (for instances in VPC without www internet)
- Logging and Audit:
	- S3 Access Logs can be stored in other S3 bucket
	- API calls can be logged in AWS CloudTrail
- User Security:
	- Multi Factor Authentication (MFA) Delete: MFA can be required in versioned buckets to delete objects
	- Pre-Signed URLs: URLs that are valid only for a limited time (ex.: premium video service for logged in users)
	
## S3 Websites
- S3 can host static websites and have them accesible on the www
- The website URL will be:
	- <bucket-name>.s3-website-<AWS-region>.amazonaws.com
	- <bucket-name>.s3-website.<AWS-region>.amazonaws.com
- If you get a 403 (Forbidden) error, make sure the bucket policy allows public reads!

## Cross-Origin Resource Sharing (CORS)
- An origin is a scheme (protocol), host (domain) and port
	- Ex.: https://www.example.com (implied port is 443 for HTTPS, 80 for HTTP)
		- protocol: HTTPS, domain: www.example.com, port: 443
- CORS is a web browser based mechanism to allow requests to other origins while visiting the main origin
	- Same origin: http://www.example.com/api1 & http://www.example.com/api2
	- Different origins: http://www.example.com & http://other.example.com
- The request won't be fulfilled unless the other origin allows for the requests, using CORS Headers (ex.: Access-Control-Allow-Origin)

## Amazon S3 - Consistency Model
- Amazon S3 is made of different servers, and when a file is uploaded to a server it will be replicated to other servers. This leads to different consistency issues
- Read after write consistency for PUTS of new objects
	- As soon as a new object is written, we can retrieve it.
		- ex: PUT 200 => GET 200
	- This is true, **except** if we did a GET before to see if the object existed
		- ex: GET 404 => PUT 200 => GET 404 - eventually consistent
- Eventual Consistency for DELETES and PUTS of existing objects
	- If we read an object after updating, we might get the older version
		- ex: PUT 200 => PUT 200 => GET 200 (might be the older version)
	- If we delete an object, we might still be able to retrieve it for a short time
		- ex: DELETE 200 => GET 200
- Note: There is no way to request _"strong_ _consistency"_

# AWS Command Line Interface - CLI
CLI to access AWS resources

```
aws configure
```

# AWS Software Development Kit (SDK)
- SDK to perform action on AWS from an application code
	- Official AWS CLI is built upon Python SDK
- If it is not specified or configured a default region, then us-east-1 will be chosen by default
- It's recommended to use the **default credential provider chain**
- The **default credential provider chain** works seamlessly with:
	- AWS credentials at ~/.aws/credentials (only on your computer or on premise)
	- Instance Profile Credentials using IAM Roles (for EC2 machines, etc...)
	- Environment variables (AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY)
- **Overall, NEVER EVER STORE AWS CREDENTIALS IN YOUR CODE**
- **Best practice is for credentials to be inherited from mechanisms above, and 100% IAM ROles if working from withing AWS Services**

## Exponential Backoff
- Any API that fails because of too many calls needs to be retrived with Exponential Backoff
- These apply to rate limited API
- Retry mechanism included in SDK API calls
	- In case of failure the next retry will wait for the double of time of previous wait
	- Ex.: Fist call fail, then to the second call it will wait 10ms, in case it fails to the third call it will wait 20ms, and so on


# Advanced Amazon S3

## S3 Multi Factor Authentication - Delete
- MFA forces user to generate a code on a device before doing important operations on S3
    - Version on S3 bucket must be enabled to use MFA delete
- You will need MFA to:
    - Permanently delete an object version
	- Suspend versioning on the bucket
- You won't need MFA for:
    - enable versioning
	- listing deleted version
- **Only bucket owner (root account) can enable/disable MFA delete**
- MFA-delete currently can only be enabled using the CLI

## S3 Default Encryption vs Bucket Policies
- Old way to enable default encryption was to use a bucket policy and refuse any HTTP command without the proper headers
- The new way is to use the "default encryption" option in S3
- Note: Bucket policies are evaluated before "default encryption"

## S3 Access Logs
- For audit purpose, you may want to log all access to S3 buckets
- Any request made to S3, from any account, authorized or denied, will be logged into another S3 bucket
    - ***VERY IMPORTANT!!! Do not set your logging bucket to be the monitored bucket. It will create a logging loop, and your bucket will grow in size exponentially**
- That data can be analyzed using data analysis tools
- The log format is at : https://docs.aws.amazon.com/AmazonS3/latest/dev/LogFormat.html

## S3 Replication
- Must enable versioning in source and destination buckets
- Cross Region Replication (CRR)
    - Use cases: compliance, lower latency access, replication across accounts
- Same Region Replication (SRR)
    - Use cases: log aggregation, live replication between production and test accounts
- Buckets can be in different accounts
- Copying is asynchronous
- Must give proper IAM permissions to S3

- After activating, **only new objects are replicated (not retroactive)**
- For DELETE operations:
    - if you delete without a version ID, it adds a delete marker, not replicated
	- If you delete with version ID, it deletes in the source, not replicated
	- In short delete operations are not replicated
- There is no "chaining" of replication
    - If bucket 1 has replication into bucket 2, which has replication into bucket 3
	- Then objects created in bucket 1 are not replicated to bucket 3
	
## S3 Pre-signed URLs
- Can generate pre-signed URLs using SDK or CLI
    - For downloads (easy, can use the CLI)
	- For uploads (harder, must use the SDK)
	- ***aws s3 presign s3://my-bucket/file.jpg --expires-in 1 --region eu-west-1***
	- **IMPORTANT!!! Configure the signature algorithm: aws configure set default.s3.signature_version s3v4**
- Valid for a default of 3600 seconds, can change timeout with --expires-in \[TIME_BY_SECONDS\] argument
- Users given a pre-signed URL inherit the permissions of the person who generated the URL for GET / PUT
- Examples:
    - Allow only logged-in users to download a premium video on your S3 bucket
	- Allow an ever changing list of users to download files by generating URLS dynamically
	- Allow temporarily a user to upload a file to a precise location in our bucket
	
## S3 Storage Tiers
- Comparison between S3 storage classes
    - https://aws.amazon.com/s3/storage-classes/

### Amazon S3 Standard - General purpose
- High durability (99,999999999%) of objects across multiple AZ
- If you store 10,000,00 objects with Amazon S3, you can on average expect to incur a loss of a single object once every 10,000 years
- 99,99% availability over a given year
- Sustain 2 concurrent facility failures

- Use cases: Big Data analytics, mobile & gaming applications, content distribution ...


### Amazon S3 Standard-Infrequent Access (IA)
- Suitable for data that is less frequently accessed, but requires rapid access when needed
- High durability (99,999999999%) of objects across multiple AZ
- 99,9% availability
- Low cost compared to Amazon S3 Standard
    - The idea is if you access your files less, you don't need to pay much
- Sustain 2 concurrent facility failures

- Use cases: As a data store for disaster recovery, backups ...


### Amazon S3 One Zone-Infrequent Access
- Same as IA but data is stored in a single AZ
- High durability (99,999999999%) of objects in a single AZ; data lost when AZ is destroyed
- 99,5% availability
- Low latency and high throughput performance
- Supports SSL for data at transit and encryption at rest
- Low cost compared to IA ( by 20% )

- Use cases: Storing secondary backup copies of on-premise data, or storing data you can recreate


### Amazon S3 Intelligent Tiering
- Same low latency and hight throughput performance of S3 Standard
- Small monthly monitoring and auto-tiering fee
- Automatically moves object between two access tiers based on changing access patterns
- Designed for durability of 99,999999999% of objects across multiple Availability Zones
- Resilient against events that impact an entire Availability Zone
- Designed for 99,9% availability over a given year


### Amazon Glacier & Amazon Glacier Deep Archive
- Low cost object storage meant for archiving / backup
- Data is retained for the longer term (10s of years)
- Alternative to on-premise magnetic tape storage
- Average annual durability is 99,999999999%
- Cost per storage per month ($0,004 / GB) + retrieval cost
- Each item in Glacier is called "Archive" (up to 40tb)
- Archives are stored in "Vaults" ( very similar to bucket )

#### Retrieval options - Amazon Glacier
- Expedited (1 to 5 minutes)
- Standard (3 to 5 hours)
- Bulk (5 to 12 hours)
- Minimum storage duration of 90 days


#### Retrieval options - Amazon Glacier Deep Archive
- For super long term storage - cheaper
- Standard (12 hours)
- Bulk (48 hours)
- Minimum storage duration of 180 days

## S3 Lifecycle Rules
- **Transition actions:** It defines when objects are transitioned to another storage class
    - Move objects to Standard IA class 60 after creation
	- Move to Glacier for archiving after 6 months
- **Expiration actions:** configure objects to expire (delete) after some time
    - Access log files can be se to delete after a 365 days
	- **Can be used to delete old version of files (if versioning is enabled)**
	- Can be used to delete incomplete multi-part uploads
- Rules can be created for a certain prefix (ex - s3://mybucket/mp3/*)
- Rules can be created for a certain objects tags (ex - Department: Finance)

## S3 Performance

### Baseline Performance
- Amazon S3 automatically scales to high requests rates, latency 100-200ms
- Your application can achieve at least **3.500 PUT/COPY/POST/DELETE and 5.500 GET/HEAD requests per second per prefix in a bucket**
- There are no limits to the number of prefixes in a bucket
- Example ( object path => prefix ):
    - bucket/folder1/sub1/file => /folder1/sub1/
	- bucket/folder1/sub2/file => /folder1/sub2/
	- bucket/1/file => /1/
	- bucket/2/file => /2/
- If you spread reads across all your prefixes evenly, you can achived 22.000 requests per second for GET and HEAD

### KMS Limitation
- If you use SSE-KMS, you may be impacted by the KMS limits
- When you upload, it calls the **GenerateDataKey** KMS API
- When you download, it calls the **Decrypt** KMS API
- Count towards the KMS quota per second (5500, 10000, 30000 req/s based on region)
- As of today, you cannot request a quota increase for KMS

### General performance
- **Multi-Part upload**
    - Recommended for files > 100MB, must use for files > 5GB
	- Can help parallelize uploads ( speed up transfers)
- **S3 Transfer Acceleration (upload only)**
    - Increase transfer speed by transferring file to an AWS edge location (public www) which will forward the data to the S3 bucket in the target region (private AWS)
	- Compatible with multi-part upload

### S3 Byte-Range Fetches
- Parallelize GETs by requesting specific byte ranges
- Better resilience in case of failures
- Can be used to speed up downloads
- Can be used to retrieve only partial data ( for example the head of a file )

## S3 Select & Glacier Select
- Retrieve less data using SQL by performing **server side filtering**
- Can filter by rows & columns (simple SQL statements)
- Less network transfer, less CPU cost client-side
    - Before: S3 send the whole data
	- After: only selected data is sent
	    - Up to 400% faster
		- Up to 80% cheaper
		- https://aws.amazon.com/blogs/aws/s3-glacier-select/

## S3 Event Notifications
- Can react to S3 events: S3:ObjectCreated, S3:ObjectRemoved, S3:ObjectRestore, S3:Replication ...
- Object name filtering possible (*.jpg)
- Use case: generate thumbnails of images uploaded to S3
- **Can create as many "S3 events" as desired**
- Notifications delivered to SNS, SQS or Lambda Function

- S3 event notifications typically deliver events in seconds but can sometimes take a minute or longer
- If two writes are made to a single non-versioned object at the same time, it is possible that only a single event notication will be sent
- If you want to ensure that an event notification is sent for every successful write, you can enable versioning on your bucket.

## S3 Object Lock and Glacier Vault Lock
- S3 Object Lock
    - Adopt a WORM (Write Once Read Many) model
	- Block an object version deletion for a specified amount of time
- Glacier Vault Lock
    - Adopt a WORM (Write Once Read Many) model
	- Lock the policy for future edits (can no longer be changed)
	- Helpful for compliance and data retention

# AWS Athena

- **Serveless** service to perform analytics **directly against s3 files**
- Uses SQL language to query the files
- Has a JDBC / ODBC driver
- Charged per query and amount of data scanned
- Supports CSV, JSON, ORC, Avro, and Parquet (built on Presto)
- Use cases: Business Intelligence / analytics / reporting, analyze & query VPC Flow Logs, ELB Logs, CloudTrail trails, etc ...
- Exam Tip: Analyze data directly on S3 => use Athena

# AWS CloudFront
- Content Delivery Network (CDN)
- Improves read performance, content is cached at the edge
- 216 point of presence globally (edge locations)
- DDoS protection, integration with Shield, AWS Web Application Firewall
- Can expose external HTTPS and can talk to internal HTTPS backends
- The Amazon CloudFront Global Edge Network: https://aws.amazon.com/cloudfront/features/?nc=sn&loc=2

## CloudFront - Origins
- S3 bucket
    - For distributing files and caching them at the edges
		- Transfer over a private AWS network
	- Enhanced security with CloudFront **Origin Access Identity (OAI)**
	    - Bucket policy + OAI (role)
	- CloudFront can be used as an ingress (to upload files to S3)
- Custom Origin (HTTP)
    - Application Load Balancer
		- ALB must be public
		- EC2 instances can be private
		- Security group must allow access from edge location
	- EC2 instance
		- Instances must be public
		- Security groups must allow access from edge location
	- S3 website (must first enable the bucket as a static s3 website)
	- Any HTTP backend you want

## CloudFront at a high level
- There are a bunch of edge location spread all over the world
- These edge locations are connected to an origin (s3 or any http endpoint)
- Client make request, it is forwarded to origin (including query strings and request headers)
- Edge location will cache response

## CloudFront Geo Restriction
- You can restrict who can access your distribution
    - Whitelist : Allow your users to access your content only if they're in one of the countries on a list of approved countries
	- Blacklist : Prevent your users from accessing your content if they're in one of the countries on blacklist of banned countries
- The "country" is determined using a 3rd part Geo-IP database
- Use case: Copyright Laws to control access to content

## CloudFront vs S3 Cross Region Replication
- CloudFront:
    - Global Edge network
	- Files are cached for a TTL (maybe a day)
	- **Great for static content that must be available everywhere**

- S3 Cross Region Replication:
    - Must be setup for each region you want replication to happen
	- Files are updated in near real-time
	- Read only
	- **Great for dynamic content that needs to be available at low-latency in few regions**


## CloudFront Signed URL / Signed Cookies
- You want to distribute paid shared content to premium users over the world
- We can use CloudFront Signed URL / Cookie. We attach a policy with:
    - includes URL expiration
	- Includes IP ranges to access the data from
	- Trusted signers (which AWS accounts can create signed URLs)
- How long should the URL be valid for ?
    - Shared content (movie, music): make it short (a few minutes)
	- Private content (private to the user): you can make it last for years
- Signed URL = access to individual filese (one signed URL per file)
- Signed Cookies = access to multiple files (one signed cookie for many files)

### CloudFront Signed URL vs S3 Pre-Signed URL
	- **CloudFront Signed URL:**
		- Allow access to a path, no matter the origin
		- Account wide key-pair, only the root can manage it
		- Can filter by IP, path, date, expiration
		- Can leverage caching features
	- **S3 Pre-Signed URL:**
		- Issue a request as the person who pre-signed the URL
		- Uses the IAM key of the signing IAM principal
		- Limited lifetime

# Global Accelerator
- Background:
	- You have deployed an application and have global users who want to access it directly.
	- They go over the public internet, which can add a lot of latency due to many hops
	- We wish to go as fast as possible through AWS network to minimize latency
	- Anycast IP: all servers hold the same IP address and the client is routed to the nearest one
	
- Global Accelerator uses the concept of Anycast
- Leverage the AWS internal network to route to your application
- 2 Anycast IP are created for your application
- The Anycast IP send traffic directly to Edge Locations
- The Edge location send the traffic to your application

- Works with Elastic IP, EC2 instances, ALB, NLB, public or private
- Consistent Performance
	- Intelligent routing to lowest latency and fast regional failover
	- No issue with client cache (because the IP doesn't change)
	- Internal AWS network
- Health Checks
	- Global Accelerator performs a health check of your applications
	- Helps make your application global (failover less than 1 minute for unhealthy)
	- Great for disaster recovery (thanks to the health checks)
- Security
	- only 2 external IP need to be whitelisted
	- DDoS protection thanks to AWS Shield

## Global Accelerator vs CloudFront
- They both use the AWS global network and its edge locations around the world
- Both services integrate with AWS Shield for DDoS protection

- **CloudFront**
	- Improves performance for both cacheable content (such as images and videos)
	- Dynamic content (such as API acceleration and dynamic site delivery)
	- Content is served at the edge
- **Global Accelerator**
	- Improves performance for a wide range of applications over TCP or UDP
	- Proxying packets at the edge to applications running in one or more AWS Regions
	- Good fit for non-HTTP use cases, such as gaming (UDP), IoT (MQTT), or Voice Over IP
	- Good for HTTP use cases that requires static IP adresses
	- Good for HTTP use cases that required deterministic, fast regional failover
	
# Snowball
- Huge box that physically allows data transfer
- Physical data transport solution that helps moving TBs or PBs of data in or out of AWS
- Alternative to moving data over the network (and paying network fees)
- Secure, tamper resistent, uses KMS 256 bit encryption
- Tracking using SNS and text messages. E-ink shipping label
- Pay per data transfer job
- Use cases: large data cloud migrations, DC decommission, disaster recovery
- If it takes more than a week to transfer over the network, use Snowball devices!

## Snowball process
1. Request snowball devices from the AWS console for delivery
2. Install the snowball client on your servers
3. Connect the snowball to your servers and copy files using the client
4. Ship back the device when you're done (goes to the right AWS facility)
5. **Data will be loaded into an S3 bucket**
6. Snowball is completely wiped
7. Tracking is done using SNS, text messages and the AWS console

## Snowball Edge
- Snowball Edge add computational capability to the device
- 100TB capacity with either:
	- Storage optimized - 24 vCPU
	- Compute optimized - 52 vCPU & optional GPU
- Supports a custom EC2 AMI so you can perform processing on the go
- Supports custom Lambda functions

- Very useful to pre-process the data while moving
- Use case: data migration, image collation, IoT capture, machine learning

## Snowmobile
- A truck!!!
- Transfer exabytes of data ( 1EB = 1000PB = 1000000TBs)
- Each Snowmobile has 100PB of capacity (use multipel in parallel)
- BEtter than Snowball if you transfer more than 10PB


## Solution Architecture: Snowball into Glacier
- **Snowball cannot import to Glacier directly**
- You have to use Amazon S3 first, and an S3 lifecycle policy

# AWS Storage Gateway
- AWS Storage Gateway is a service connecting an on-premises software appliance with cloud-based storage to provide seamless and secure integration between an organization's on-premises IT environment and AWS's storage infrastructure.

## Hybrid Cloud for Storage
- AWS is pushing for "hybrid cloud"
	- Part of your infrastructure is on the cloud
	- Part of your infrastructure is on-premise
- This can be due to:
	- Long cloud migrations
	- Security requirements
	- Compliance requirements
	- IT strategy
- S3 is a proprietary storage technology (unlike EFS / NFS), so how do you expose the S3 data on-premise ?
	- **AWS Storage Gateway**

## AWS Storage Cloud Native Options
- Block
	- Amazon EBS
	- EC2 Instance Store
- File
	- Amazon EFS
- Object
	- S3
	- Glacier

## Storage Gateway
- Bridge between on-premise data and cloud data in S3
- Use cases: disaster recovery, backup & restore, tiered storag
- 3 types of Storage Gateway:
	- File Gateway
	- Volume Gateway
	- Tape Gateway
- **Exam Tip: You need to know the difference between all 3!**

### File Gateway
- Configured S3 buckets are accessible using the NFS and SMB protocol
- Supports S3 standard, S3 IA, S3 One Zone IA
- Bucket access using IAM roles for each File Gateway
- Most recently used data is cached in the file gateway
- Can be mounted on many servers

### Volume Gateway
- Block storage using iSCSI protocol backed by S3
- Backed by EBS snapshots which can help restore on-premise volumes!
- **Cached volumes:** low latency access to most recent data
- **Stored volumes:** entire dataset is on-premise, scheduled backups to S3

### Tape Gateway
- Some companies have backup process using physical tapes (!)
- With Tape Gateway, companies use the same processes but in the cloud
- Virtual Tape Library (VTL) backed by Amazon S3 and Glacier
- Back up data using existing tape-based processes (and iSCSI interface)
- Works with leading backup software vendors

## AWS Storage Gateway Summary
- Exam tip: Read the question well, it will hint at which gateway to use
- On premise data to the cloud => Storage Gateway

- File Access / NFS => File Gateway (backed by S3)
- Volumes / Block Storage / iSCSI => Volume Gateway (backed by S3 with EBS snapshots)
- VTL Tape Solution / Backup with iSCSI => Tape Gateway (backed by S3 and Glacier)


# Amazon FSx
## Amazon FSx for Windows (File Server)
- EFS is a shared POSIX system for Linux systems

- **FSx for Windows** is a fully managed **Windows** file system share drive
- Supports SMB protocol & Windows NTFS
- Microsoft Active Directory integration, ACLs, user quotas
- Built on SSD, scale up to 10s of GB/s, millions of IOPS, 100s PB of data
- Can be accessed from your on-premise infrastructure
- Can be configured to be Multi-AZ (High availability)
- Data is backed-up daily to S3

## Amazon FSx for Lustre
- Lustre is a type of parellel distributed file system, for large-scale computing
- The name Lustre is derived from "Linux" and "cluster"

- Machine Learning, **High Performance Computing (HPC)**
- Video Processing, Financial Modeling, Electronic Design Automation
- Scales up to 100s GB/s, millions of IOPS, sub-ms latencies
- **Seamles integragion with S3**
	- Can "read S3" as a file system (through FSx)
	- Can write the output of the computations back to S3 (through FSx)
- Can be used from on-premise servers

# AWS Storage Comparison
- **S3:** Object Storage
- **Glacier:** Object Archival
- **EFS:** Network File System for Linux instances, POSIX filesystem
- **FSx for Windows:** Network File System fro Windows servers
- **FSx for Lustre:** High Performance Computing for Linux filesystem
- **EBS volumes:** Network storage for one EC2 instance at time
- **Instance Storage:** Physical storage for your EC2 instance (high IOPS)
- **Storage Gateway:** File Gateway, Volume Gateway (cache & stored), Tape Gateway
- **Snowball/Snowmobile:** To move large amount of data to the cloud, physically
- **Database:** For specific workloads, usually with indexing and querying
