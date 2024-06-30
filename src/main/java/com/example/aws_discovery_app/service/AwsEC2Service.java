package com.example.aws_discovery_app.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AwsEC2Service {
    private AmazonEC2 ec2Client;

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @PostConstruct
    private void init() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretKey);
        this.ec2Client = AmazonEC2ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    @Async
    public CompletableFuture<List<Instance>> getEc2Instances() {
        List<Instance> ec2InstanceList = ec2Client.describeInstances()
                .getReservations()
                .stream()
                .flatMap(reservation -> reservation.getInstances().stream()).toList();

        return CompletableFuture.completedFuture(ec2InstanceList);

    }
}
