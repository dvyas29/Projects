## Instructions to deploy data from Kinesis to S3

Open the AWS Kinesis service:
* Create a new Data stream
* Set "Number of open shards" to 1 Mib/s to reduce cost.
* After creating the stream, under "Consumers" section select Kinesis Data Firehose.
* Create a new delivery stream.
* Under "Choose a destination", select S3.
* I selected a Buffer size of 1 Mib/s and buffer interval of 60 sec.