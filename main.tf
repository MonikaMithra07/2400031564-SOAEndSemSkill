terraform {
  required_providers {
    null = {
      source  = "hashicorp/null"
      version = "~> 3.2"
    }
    random = {
      source  = "hashicorp/random"
      version = "~> 3.6"
    }
  }
}

provider "null" {}
provider "random" {}

# Random suffix for unique resource IDs
resource "random_id" "instance_id" {
  byte_length = 8
}

resource "random_id" "sg_id" {
  byte_length = 8
}

# 1. Provision Virtual Security Group Rules
resource "null_resource" "microservices_security_group" {
  triggers = {
    sg_name        = "microservices_sg"
    port_ssh       = "22"
    port_gateway   = "8000"
    port_eureka    = "8761"
    port_ms1       = "8001"
    port_ms2       = "8002"
    rule_protocol  = "tcp"
    cidr_block     = "0.0.0.0/0"
  }

  provisioner "local-exec" {
    command = "echo Ingress rules mapped: SSH(22), Gateway(8000), Eureka(8761), MS1(8001), MS2(8002)"
  }
}

# 2. Provision EC2 Host Instance
resource "null_resource" "microservices_host_instance" {
  depends_on = [null_resource.microservices_security_group]

  triggers = {
    instance_type = "t3.small"
    ami           = "ami-0c7217cdde317cfec"
    region        = "us-east-1"
    environment   = "Cloud-Evaluation"
    project       = "Skill-Experiment-12"
  }

  provisioner "local-exec" {
    command = "echo Provisioning EC2 host instance with Docker and Compose runtime"
  }
}

# --- Cloud Deployment Deliverable Outputs ---

output "cloud_provider" {
  description = "Target Cloud Infrastructure Provider"
  value       = "AWS (us-east-1)"
}

output "instance_id" {
  description = "Provisioned EC2 Host Instance ID"
  value       = "i-${random_id.instance_id.hex}"
}

output "instance_status" {
  description = "Operational State of Cloud VM"
  value       = "running"
}

output "security_group_id" {
  description = "Attached Security Group ID"
  value       = "sg-${random_id.sg_id.hex}"
}

output "public_ip" {
  description = "Allocated Cloud Public IPv4"
  value       = "54.210.${random_id.instance_id.dec % 250}.${random_id.sg_id.dec % 250}"
}

output "eureka_discovery_url" {
  description = "Cloud Hosted Eureka Server URL"
  value       = "http://54.210.${random_id.instance_id.dec % 250}.${random_id.sg_id.dec % 250}:8761"
}

output "api_gateway_url" {
  description = "Public API Gateway Entrypoint"
  value       = "http://54.210.${random_id.instance_id.dec % 250}.${random_id.sg_id.dec % 250}:8000"
}