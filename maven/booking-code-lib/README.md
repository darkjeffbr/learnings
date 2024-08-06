# Simple Booking Code Generation Lib

**THIS IS NOT A SERIOUS PROJECT, IT IS NOT INTENDED TO BE USED IN PRODUCTION.**  

The idea of this small project is to play with nexus and understand/learn how it handles the releases.  
For that purpose we use a Nexus instance running in a docker container.

**Start local nexus**
``docker run -d -p 8081:8081 --name nexus sonatype/nexus3``

**Deploy to local nexus**
``mvn deploy``

## Learnings

### pom.xml configuration

1. Under the distributionManagement section we can define the repository ( for final release version) and snapshotRepository ( for non-final release version ).
2. The credentials to the repository are defined in the settings.xml file placed in the .m2 folder

### Nexus behavior

1. When in the `pom.xml` file the version ends with **-SNAPSHOT** nexus store the generated artifact under a folder `*version*`-SNAPSHOT. For example: `com/darkjeff/booking/code/booking-code/1.0-SNAPSHOT/booking-code-1.0-20240806.103050-1/booking-code-1.0-20240806.103050-1.jar`

2. When the version in the pom.xml file doesn't end with -SNAPSHOT nexus store the artifcat under the root component folder. For example: `com/darkjeff/booking/code/booking-code/1.0/booking-code-1.0.jar`


