package com.example.aws_discovery_app.repository;

import com.example.aws_discovery_app.model.dbModels.EC2Instance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EC2InstanceRepository extends JpaRepository<EC2Instance, String> {
//    EC2Instance findByInstanceId(String ec2InstanceId);

}
