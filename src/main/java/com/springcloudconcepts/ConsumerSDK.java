package com.springcloudconcepts;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.GetRecordsRequest;
import com.amazonaws.services.kinesis.model.GetRecordsResult;
import com.amazonaws.services.kinesis.model.GetShardIteratorRequest;
import com.amazonaws.services.kinesis.model.GetShardIteratorResult;
import com.amazonaws.services.kinesis.model.ShardIteratorType;

import jakarta.annotation.PostConstruct;

@Component
public class ConsumerSDK {

//    @Value("${spring.cloud.stream.bindings.input.destination}")
//    private String IPS_STREAM;
//    
//    @Value("${spring.cloud.stream.bindings.input.shard-id}")
//    private String IPS_SHARD_ID;
    
    @Autowired
    private AmazonKinesis kinesis;
    
    private GetShardIteratorResult shardIterator;

    public void consumeWithKinesis() {
        GetRecordsRequest recordsRequest = new GetRecordsRequest();
        recordsRequest.setShardIterator(shardIterator.getShardIterator());
        recordsRequest.setLimit(25);

        GetRecordsResult recordsResult = kinesis.getRecords(recordsRequest);
        while (!recordsResult.getRecords().isEmpty()) {
            recordsResult.getRecords()
                .stream()
                .map(record -> new String(record.getData()
                    .array()))
                .forEach(System.out::println);

            recordsRequest.setShardIterator(recordsResult.getNextShardIterator());
            recordsResult = kinesis.getRecords(recordsRequest);
        }
    }

    @PostConstruct
    private void buildShardIterator() {
    	System.out.println("------------buildShardIterator--------------");
        GetShardIteratorRequest readShardsRequest = new GetShardIteratorRequest();
        readShardsRequest.setStreamName("stream/kinesis-example");
        readShardsRequest.setShardIteratorType(ShardIteratorType.AT_TIMESTAMP);
        readShardsRequest.setShardId("3");
        this.shardIterator = kinesis.getShardIterator(readShardsRequest);
        System.out.println("------------buildShardIterator--------------");
    }
}