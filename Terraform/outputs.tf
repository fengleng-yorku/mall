output "instance_id" {
  description = "EC2 instance ID"
  value       = aws_instance.main.id
}

output "public_ip" {
  description = "Public IP address of the EC2 instance"
  value       = aws_instance.main.public_ip
}

output "vpc_id" {
  description = "VPC ID"
  value       = aws_vpc.main.id
}

output "subnet_id" {
  description = "Public subnet ID"
  value       = aws_subnet.public.id
}

output "security_group_id" {
  description = "Security group ID"
  value       = aws_security_group.ec2_sg.id
}

output "ssh_command" {
  description = "SSH command to connect to the instance"
  value       = "ssh -i ${local_sensitive_file.private_key.filename} ec2-user@${aws_instance.main.public_ip}"
}

output "mysql_connection" {
  description = "MySQL connection command"
  value       = "mysql -h ${aws_instance.main.public_ip} -P 3306 -u root -p"
}

output "redis_connection" {
  description = "Redis connection command"
  value       = "redis-cli -h ${aws_instance.main.public_ip} -p 6379"
}
