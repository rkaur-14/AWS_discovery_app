package com.example.aws_discovery_app.service.impl;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.example.aws_discovery_app.model.businessModels.DiscoveryServiceResponseModel;
import com.example.aws_discovery_app.model.dbModels.EC2Instance;
import com.example.aws_discovery_app.model.dbModels.S3Bucket;
import com.example.aws_discovery_app.repository.EC2InstanceRepository;
import com.example.aws_discovery_app.repository.S3BucketRepository;
import com.example.aws_discovery_app.service.AwsDiscoveryService;
import com.example.aws_discovery_app.service.AwsEC2Service;
import com.example.aws_discovery_app.service.AwsS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class AwsDiscoveryServiceImpl implements AwsDiscoveryService {

    @Autowired
    private AmazonEC2 amazonEC2;

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private EC2InstanceRepository ec2InstanceRepository;

    @Autowired
    private S3BucketRepository s3BucketRepository;

    private final AwsS3Service awsS3Service;
    private final AwsEC2Service awsEC2Service;

    @Autowired
    public AwsDiscoveryServiceImpl(AwsS3Service awsS3Service, AwsEC2Service awsEC2Service) {
        this.awsS3Service = awsS3Service;
        this.awsEC2Service = awsEC2Service;
    }

    @Override
    @Async
    @Transactional
    public DiscoveryServiceResponseModel discoverServices(List<String> services) throws Exception {
        List<String> instanceIds = new ArrayList<>();
        List<Instance> ec2Instances = new ArrayList<>();
        List<Bucket> buckets = new ArrayList<>();
        HashSet<String> hs = new HashSet<>();

        DiscoveryServiceResponseModel resp = new DiscoveryServiceResponseModel() {
        };

        for (String service : services) {
            if (hs.contains(service.toUpperCase())) {
                continue;
            } else {
                hs.add(service.toUpperCase());

                if (service.equalsIgnoreCase("EC2")) {

                    ec2Instances = awsEC2Service.getEc2Instances().get();

                    for (Instance instance : ec2Instances) {
                        EC2Instance newEc2Instance = new EC2Instance(
                                instance.getInstanceId(),
                                instance.getKeyName(),
                                instance.getLaunchTime(),
                                instance.getState().getName()
                        );

                        resp.Ec2Ids.add(ec2InstanceRepository.save(newEc2Instance).getId());

                    }

                } else if (service.equalsIgnoreCase("S3")) {
                    // Fetch S3 buckets asynchronously
                    buckets = awsS3Service.getS3Buckets().get();
                    for (Bucket bk : buckets) {
                        S3Bucket newS3bucket = new S3Bucket(
                                bk.getName(),
                                bk.getCreationDate(),
                                bk.getOwner().getId(),
                                bk.getOwner().getDisplayName(),
                                bk.getClass().getName()
                        );
                        resp.S3Ids.add(s3BucketRepository.save(newS3bucket).getId());
                    }


                } else {
                    throw new Exception("Service not recognised");
                }
            }
        }


        return resp;
    }

    @Override
    public String getEC2InstanceStatus(String jobId) {
        EC2Instance job = EC2InstanceRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return job.getStatus();
    }

//    @Override
//    public String getJobStatus(String service, String id) {
//        // Assuming 'service' can be either "EC2" or "S3"
//        DiscoveryServiceResponseModel responseModel = fetchDiscoveryResponse(); // Implement method to fetch response
//        UUID jobId = null;
//
////        if ("EC2".equalsIgnoreCase(service)) {
////            if (responseModel.getEc2Ids().contains(id)) {
////                // Fetch job id from responseModel based on EC2 id
////                jobId = fetchJobIdForEc2Id(id);
////            }
////        } else if ("S3".equalsIgnoreCase(service)) {
////            if (responseModel.getS3Ids().contains(id)) {
////                // Fetch job id from responseModel based on S3 bucket name
////                jobId = fetchJobIdForS3Bucket(id);
////            }
////        } else {
////            throw new IllegalArgumentException("Unsupported service type");
////        }
//
//        if (jobId != null) {
//            return getJobResult(jobId);
//        } else {
//            throw new RuntimeException("Job ID not found for the provided service and ID");
//        }
//    }
//    private UUID fetchJobIdForEc2Id(String ec2InstanceId) {
//        // Implement logic to retrieve job ID associated with the EC2 instance ID
//        // Example: querying from a database where EC2 instance IDs are stored with their job IDs
//        EC2Instance ec2Instance = ec2InstanceRepository.findByInstanceId(ec2InstanceId);
//        if (ec2Instance != null) {
//            return ec2Instance.getJobId();
//        }
//        return null; // Return null if no job ID found for the given EC2 instance ID
//    }
//
//    private UUID fetchJobIdForS3Bucket(String s3BucketName) {
//        // Implement logic to retrieve job ID associated with the S3 bucket name
//        // Example: querying from a database where S3 bucket names are stored with their job IDs
//        S3Bucket s3Bucket = (S3Bucket) s3BucketRepository.findByBucketName(s3BucketName);
//        if (s3Bucket != null) {
//            return s3Bucket.getJobId();
//        }
//        return null; // Return null if no job ID found for the given S3 bucket name
//    }
//    private DiscoveryServiceResponseModel fetchDiscoveryResponse() {
//        // Implement logic to fetch or construct the DiscoveryServiceResponseModel
//        DiscoveryServiceResponseModel responseModel = new DiscoveryServiceResponseModel();
//
//        // Fetching EC2 instance IDs (example)
//        List<EC2Instance> ec2Instances = ec2InstanceRepository.findAll();
//        List<String> ec2Ids = ec2Instances.stream()
//                .map(EC2Instance::getInstanceId)
//                .collect(Collectors.toList());
////        responseModel.setEc2Ids(ec2Ids);
//
//        // Fetching S3 bucket names (example)
//        List<S3Bucket> s3Buckets = s3BucketRepository.findAll();
//        List<String> s3Ids = s3Buckets.stream()
//                .map(S3Bucket::getBucketName)
//                .collect(Collectors.toList());
////        responseModel.setS3Ids(s3Ids);
//
//        return responseModel;
//    }
//
//
//
//    @Override
//    public Object getDiscoveryResult(String service) {
//        if ("S3".equalsIgnoreCase(service)) {
//            return getS3DiscoveryResult();
//        } else if ("EC2".equalsIgnoreCase(service)) {
//            return getEC2DiscoveryResult();
//        } else {
//            throw new IllegalArgumentException("Unsupported service type");
//        }
//    }
//
//    @Override
//    @Async
//    @Transactional
//    public CompletableFuture<UUID> getS3BucketObjects(String bucketName) {
//        UUID jobId = UUID.randomUUID();
//        Job job = new Job();
//        job.setId(jobId);
//        job.setStatus("In Progress");
//        jobRepository.save(job);
//
//        return CompletableFuture.runAsync(() -> {
//            try {
//                amazonS3.listObjects(bucketName).getObjectSummaries().forEach(s3Object -> {
//                    // Persist S3 objects
//                    S3Object s3ObjectEntity = new S3Object();
//                    s3ObjectEntity.setKey(s3Object.getKey());
//                    s3ObjectEntity.setBucketName(bucketName);
//                    s3ObjectEntity.setJobId(jobId);
//                    s3ObjectRepository.save(s3ObjectEntity);
//                });
//                updateJobStatus(jobId, "Success");
//            } catch (Exception e) {
//                updateJobStatus(jobId, "Failed");
//                e.printStackTrace();
//            }
//        }).thenApply(voidResult -> jobId);
//    }
//
//    @Override
//    public int getS3BucketObjectCount(String bucketName) {
//        return s3ObjectRepository.countByBucketName(bucketName);
//    }
//
//    @Override
//    public List<String> getS3BucketObjectLike(String bucketName, String pattern) {
//        List<S3Object> s3Objects = s3ObjectRepository.findByBucketNameAndKeyLike(bucketName, pattern);
//        return s3Objects.stream()
//                .map(S3Object::getKey) // Assuming 'getKey' is the method to retrieve the key as String
//                .collect(Collectors.toList());
//    }
//
//
//    @Async
//    private CompletableFuture<Void> discoverEC2InstancesAsync(UUID jobId) {
//        return CompletableFuture.runAsync(() -> {
//            try {
//                DescribeInstancesRequest request = new DescribeInstancesRequest();
//                DescribeInstancesResult response = amazonEC2.describeInstances(request);
//
//                for (Reservation reservation : response.getReservations()) {
//                    reservation.getInstances().forEach(instance -> {
//                        EC2Instance ec2Instance = new EC2Instance();
//                        ec2Instance.setInstanceId(instance.getInstanceId());
//                        ec2Instance.setJobId(jobId);
//                        ec2InstanceRepository.save(ec2Instance);
//                    });
//                }
//            } catch (Exception e) {
//                updateJobStatus(jobId, "Failed");
//                e.printStackTrace();
//
//            }
//        });
//    }
//
//    @Async
//    private CompletableFuture<Void> discoverS3BucketsAsync(UUID jobId) {
//        return CompletableFuture.runAsync(() -> {
//            try {
//                List<Bucket> buckets = amazonS3.listBuckets();
//                buckets.forEach(bucket -> {
//                    S3Bucket s3Bucket = new S3Bucket();
//                    s3Bucket.setBucketName(bucket.getName());
//                    s3Bucket.setJobId(jobId);
//                    s3BucketRepository.save(s3Bucket);
//                });
//            } catch (Exception e) {
//                updateJobStatus(jobId, "Failed");
//                e.printStackTrace();
//            }
//        });
//    }
//
//    private Object getEC2DiscoveryResult() {
//        return ec2InstanceRepository.findAll();
//    }
//
//    private Object getS3DiscoveryResult() {
//        return s3BucketRepository.findAll();
//    }
//
//    private void updateJobStatus(UUID jobId, String status) {
//        Job job = jobRepository.findById(jobId).orElse(null);
//        if (job != null) {
//            job.setStatus(status);
//            jobRepository.save(job);
//        }
//    }
//
//
}
