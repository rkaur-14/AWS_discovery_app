package com.example.aws_discovery_app.model.dbModels;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class EC2Instance {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    private String instanceId;

    private String keyName;

    private Date createdAt;

    private String status;


    public EC2Instance(String instanceId, String keyName, Date createdAt, String status) {
        this.instanceId = instanceId;
        this.keyName = keyName;
        this.createdAt = createdAt;
        this.status = status;
    }

    public String getId() { return id; }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
