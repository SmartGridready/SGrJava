# SGr message based communications API

## Message system configuration

### Hive MQTT
- Client identifier
- Message broker host/IP
- Message broker port
- Topics to subscribe
- Topics to publish to
- Quality of service
- Communication type: blocking, async

### Kafka
- Bootstrap server(s)
- Key serializer config
- Value serializer config
- GroupID (clients only, groups clients to manage consumed messages)
- acks: The number of acknowledgments the producer requires the leader to have received before considering a request complete. This can be set to "all" for the strongest guarantee.
- retries: The number of retries the producer will attempt in case of transient errors.
- batch.size: The producer will attempt to batch records together into fewer requests whenever multiple records are being sent to the same partition.
- linger.ms: The producer will wait for up to the given delay to allow other records to be sent so that the sends can be batched together.
- buffer.memory: The total bytes of memory the producer can use to buffer records waiting to be sent to the server.
- auto.offset.reset: What to do when there is no initial offset in Kafka or if the current offset no longer exists on the server.
- group.id: A unique string that identifies the consumer group this consumer belongs to.
- auto.commit.interval.ms: The frequency in milliseconds that the consumer offsets are auto-committed to Kafka if enable.auto.commit is set to true.
- max.poll.records: The maximum number of records returned in a single call to poll().
- props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);


##  Comparison Hive MQTT vs Kafka

### Broker
- Hive MQTT uses a single Broker, 
- Kafka clients can connect to multiple bootstrap servers, each managing multiple brokers

### Publishing
- Hive MQTT can publish using a blocking or an async API. Alternatively it can be used with RxJava.
- Kafka publish/send is always asynchronous send() and returns a CompletableFuture, to optionally wait for ACK. Use CompletableFuture.get() to wait for a result.


### Subscribing/consuming messages
- Hive MQTT can subscribe to a topic in synchronous or async manner. Messages are always received asynchronous within callbacks.
- Kafka uses a polling loop with a given poll inteval. Successfully consumed and processed messages can be acknowledged.

## Processing in Java

### MQTT Publisher

1. Create a client using host/port
2. Connect client to the host (sync or async)
3. Call publish using topic, message payload, quality of service (sync or async)
4. Disconnect (sync or async)

### Kafka Publisher

1. Create a Kafka Producer using bootstrap server, message serializer config
2. Create a MessageRecord using topic, optional key and message,
3. Send message using Producer.send() -> returns a CompletableFuture to wait for broker 'ack' with message metadata if needed.
4. Disconnect Producer


### MQTT Subscriber/Consumer

1. Create a client using host/port
2. Connect client to the host
3. If we need to await for one or n response message(s) we can use a CountDownLatch(1 or n)
4. Subscribe to a topic defining a call-back function when a message is received
   - If using a CountDownLatch, decrement the latch for each message.
5. Wait for the 'ack' of the subscription (when working synchronous)
6. Wait for the CountDownLatch until the expected number of messages is recieved
7. Disconnect the client

### Kafka Subscriber/Consumer

1. Create a KafkaConsumer using a bootstrap server list, group-id and serializer configuration
2. Subscribe consumer to a topic
3. Implement a polling loop that uses Consumer.poll(timeout, list<message> -> message-callback-fn)
   - Define some termination criteria for the polling loop
   - message-callback-fn: commit (ack) each successfully received message

## Communication flow within SGr

Send message:<br>[Communicator] => [Commhandler] => [MessageBroker] => [SGrDevice]

Receive message:<br> [SGrDevice] => [MessageBroker] => [Commhandler] => [Communicator]

## SGr Datapoints

Each SGr device/datapoint is related to a topic from its character. It appears that in the MQTT world there might 
be thousands of topics within an environment. In MQTT a topic is usually a path like 
sensors/living_room/temperature, sensors/kitchen/humidity 
or more generic {{sensors}}/{{sensor_id}}/{{value}}, {{actuator}}/{{actuator_id}}/{{value}}

The datapoint can support publishing, subscribe or both. This is called "bi-directional-communication" or "round-trip messaging". 

Kafka uses a key to determine the partition where the message is stored. Messages are published and consumed by the same client only if the group-id and the key do match.

The message can be a single value (string, number) or a data transfer object (DTO).

There could be interest in receiving a single message (as in "round-trip messaging") or in publishing or receiving a
data stream:

- Write a single data value (e.g set a room temperature)
- Read a single data value (round-trip) (e.g. read a room temperature, write and read back a configuration value) 
- Receive a data-stream (e.g. receive room temperature sent every minute, receive status change events)
- Publish a data-stream (e.g. publish control values within a controller-loop)

Further we could read values synchronously using getVal() setVal() (waiting for ack) or asynchronously. 
Asynchronous read needs a new API with a callback or polling mechanism similar to Kafka. For SmartgridReady I propose
the callback method since MQTT uses the callback approach. 

## EI-XML configuration requirements 

### Interface description element

The element `<messagingInterfaceDescription>` designates a message based communication interface.


#### messagingInterfaceDescription:
- platform MQTT | Kafka .....
- brokerList
  - List of: hostname|ipAddress, optional port
- clientPropertyList
  - key / value pairs depending on messaging platform. Examples
    - MQTT: clientIdentifier, optional ev. configurable {{client_identifier}}
    - Kafka: serlializers / deserializers
- authenticationMethods
  - MQTT: Basic|ClientCertificate|OAuth2|Custom
  - Kafka: SSL/TLS|Bearer|Kerberos|SASL

#### messagingDatapointConfiguration:
- topic
  - MQTT: path, example `openWB/lp/2/kWhChargedSincePlugged`
  - Kafka: topic name: example `heatpump-sensors`
- if data direction 'W': out-message building rules
  - message body template (with parameters)
- if data direction 'R': in-message parsing rules
  - message parsing template analogue to REST-API (plain value, JMESPath, Regex, XML)



















