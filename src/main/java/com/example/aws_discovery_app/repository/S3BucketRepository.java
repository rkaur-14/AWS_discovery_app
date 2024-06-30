package com.example.aws_discovery_app.repository;

import com.example.aws_discovery_app.model.dbModels.S3Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface S3BucketRepository extends JpaRepository<S3Bucket, String> {

//    int countByBucketName(String bucketName);
//
//    List<S3Bucket> findByBucketName(String bucketName);
//
//    // Example of modifying the method based on available properties
//    List<S3Bucket> findByBucketNameAndJobId(String bucketName, UUID jobId);
}
