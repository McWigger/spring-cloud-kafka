# Spring Cloud Stream for kafka Streams

### Apache Kafka Setup
#### Linux and MacOS
Download a **binary package** of Apache Kafka (e.g., `kafka_2.13-2.8.0.tgz`) from `https://kafka.apache.org/downloads` and upzip it.
In the Terminal, `cd` to the unzip folder, and start Kakfa with the following commands in two sessions:
```bash
./bin/zookeeper-server-start.sh
```
```bash
./bin/kafka-server-start.sh
```
 
#### Windows 
Download a **binary package** of Apache Kafka (e.g., `kafka_2.13-2.8.0.tgz`) from `https://kafka.apache.org/downloads` and unzip it to 
such a directory as `C:\kafka`&mdash;Windows does not like a complex path name (!). 

In the configuration file `C:\kafka\config\zookeeper.properties`, comment out the line `"dataDir=/tmp/zookeeper"`. In `C:\kafka\config\server.properties`, change the line `"log.dirs=/tmp/kafka-logs"` to `"log.dirs=.kafka-log"`.

Use the following two commands in the Windows CMD (one in each window) to start Kafka:
```bash
C:\kafka\bin\windows\zookeeper-server-start.bat C:\kafka\config\zookeeper.properties
```
```bash
C:\kafka\bin\windows\kafka-server-start.bat C:\kafka\config\server.properties
```

#### Kafka Topic Data
Sometimes you may want to clean up data in the Kafka topics to start over. For this purpose, in Linux/MacOS, delete the folders `/tmp/zookeeper`, `/tmp/kafka-log` and `/tmp/kafka-streams`. In Windows, delete the folders `C:\kafka\.dataDirzookeeper` and `C:\kafka\.kafka-log`.

## The Applications 
The producer gets `Appliance` JSON data from a public [API](https://random-data-api.com/api/appliance/random_appliance) and 
publishes it to a Kafka topic named `"appliance-topic"`. 
The processor subscribes to that Kafka topic, computes a total count for each (appliance) `Brand` 
and publishes the results to another Kafka topic named `"brand-topic"`. 
The processor also implements an interactive query service that allows users to query the results via REST.
The consumer subscribes to `"brand-topic"` and logs the records.

When the two applications are running, use the following URL to get a list of `Brand` names:
```url
http://localhost:8183/brands/all
```
Then, use the following URL (and refresh it) to get the updating result for each `Brand`, e.g. `IKEA`:
```url
http://localhost:8183/brand/IKEA/quantity
```
