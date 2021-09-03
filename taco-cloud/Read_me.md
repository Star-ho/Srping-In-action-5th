
##Cassandra Docker Run

docker pull cassandra:latest

docker network create cassandraNetwork

docker run --name taco-cassandra -p 9000:9000 --network cassandraNetwork -d cassandra

docker run -it --network cassandraNetwork --rm cassandra cqlsh taco-cassandra

create keyspace tacocloud with replication={'class':'SimpleStrategy','replication_factor':1} and durable_writes=true;