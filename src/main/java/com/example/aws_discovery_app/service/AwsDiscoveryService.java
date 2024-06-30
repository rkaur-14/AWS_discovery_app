package com.example.aws_discovery_app.service;

import com.example.aws_discovery_app.model.businessModels.DiscoveryServiceResponseModel;

import java.util.List;

public interface AwsDiscoveryService {

    DiscoveryServiceResponseModel discoverServices(List<String> services) throws Exception;

    String getEC2InstanceStatus(String jobId);

//    String getJobStatus(String service, String id);
//
//    Object getDiscoveryResult(String service);
//
//    CompletableFuture<UUID> getS3BucketObjects(String bucketName);
//
//    int getS3BucketObjectCount(String bucketName);
//
//    List<String> getS3BucketObjectLike(String bucketName, String pattern);

}
