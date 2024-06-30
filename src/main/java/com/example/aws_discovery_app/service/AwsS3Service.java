package com.example.aws_discovery_app.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AwsS3Service {

    private AmazonS3 s3client;

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @PostConstruct
    private void init() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretKey);
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    @Async
    public CompletableFuture<List<Bucket>> getS3Buckets() {
        List<Bucket> buckets = s3client.listBuckets();
        return CompletableFuture.completedFuture(buckets);
    }
}
