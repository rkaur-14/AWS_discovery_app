USE aws_discovery;

CREATE TABLE IF NOT EXISTS S3Bucket (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    bucket_name VARCHAR(255) NOT NULL,
    owner_id VARCHAR(255) NOT NULL,
    owner_name VARCHAR(255) ,
    class_name VARCHAR(255) NOT NULL,
    created_at DATE
);

CREATE TABLE IF NOT EXISTS EC2Instance (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    instance_id VARCHAR(255) NOT NULL,
    key_name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at DATE

);
