package com.example.aws_discovery_app.service;

import com.example.aws_discovery_app.model.dbModels.EC2Instance;
import com.example.aws_discovery_app.repository.EC2InstanceRepository;

import java.util.List;
import java.util.UUID;

public class EC2InstanceService {
    private EC2InstanceRepository ec2InstanceRepository;

    public List<EC2Instance> getAllEC2Instances() {
        return ec2InstanceRepository.findAll();
    }

    public EC2Instance saveEC2Instance(EC2Instance ec2Instance) {
        return ec2InstanceRepository.save(ec2Instance);
    }

    public EC2Instance getEC2InstanceByInstanceId(String instanceId) {
        return ec2InstanceRepository.findById(instanceId).orElse(null);
    }
}
