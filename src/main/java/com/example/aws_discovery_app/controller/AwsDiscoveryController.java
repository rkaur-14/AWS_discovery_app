package com.example.aws_discovery_app.controller;

import com.example.aws_discovery_app.model.businessModels.DiscoveryServiceResponseModel;
import com.example.aws_discovery_app.service.AwsDiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aws")
public class AwsDiscoveryController {


    @Autowired
    private AwsDiscoveryService awsDiscoveryService;


    @PostMapping("/discover")
    public DiscoveryServiceResponseModel discoverServices(@RequestBody List<String> services) throws Exception {
        return awsDiscoveryService.discoverServices(services);
    }
//
//    @GetMapping("/job/{jobId}")
//    public ResponseEntity<String> getJobStatus(@PathVariable UUID jobId) {
//        try {
//            String status = awsDiscoveryService.getJobResult(jobId);
//            return ResponseEntity.ok(status);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @GetMapping("/discovery/{service}")
//    public Object getDiscoveryResult(@PathVariable String service) {
//        return awsDiscoveryService.getDiscoveryResult(service);
//    }
//
//    @PostMapping("/s3/{bucketName}/objects")
//    public CompletableFuture<UUID> getS3BucketObjects(@PathVariable String bucketName) {
//        return awsDiscoveryService.getS3BucketObjects(bucketName);
//    }
//
//    @GetMapping("/s3/{bucketName}/objectCount")
//    public int getS3BucketObjectCount(@PathVariable String bucketName) {
//        return awsDiscoveryService.getS3BucketObjectCount(bucketName);
//    }
//
//    @GetMapping("/s3/{bucketName}/objectLike")
//    public List<String> getS3BucketObjectLike(@PathVariable String bucketName,
//                                              @RequestParam String pattern) {
//        return awsDiscoveryService.getS3BucketObjectLike(bucketName, pattern);
//    }
}
