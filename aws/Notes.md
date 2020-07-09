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