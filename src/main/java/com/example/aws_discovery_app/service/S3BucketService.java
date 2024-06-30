package com.example.aws_discovery_app.service;

import com.example.aws_discovery_app.model.dbModels.EC2Instance;
import com.example.aws_discovery_app.model.dbModels.S3Bucket;
import com.example.aws_discovery_app.repository.S3BucketRepository;

import java.util.List;
import java.util.UUID;

public class S3BucketService {
    private S3BucketRepository s3BucketRepository;

    public List<S3Bucket> getAllS3Buckets() {
        return s3BucketRepository.findAll();
    }

    public S3Bucket saveS3Bucket(S3Bucket s3Bucket) {
        return s3BucketRepository.save(s3Bucket);
    }

    public S3Bucket getS3BucketById(String id) {
        return s3BucketRepository.findById(id).orElse(null);
    }
}
