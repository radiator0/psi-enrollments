pipeline {
  agent any
    
  tools {nodejs "node"}
    
  stages {
        
    stage('Cloning Git') {
      steps {
        git branch: ' origin/f/jenkins', url: 'https://github.com/radiator0/psi-enrollments.git'
      }
    }
        
    stage('Install dependencies') {
      steps {
        sh 'npm install'
      }
    }
     
    stage('Test') {
      steps {
         sh 'npm test'
      }
    }  
    
    stage('Packaging') {
      steps {
         sh './mvnw package -Pprod -DskipTests'
      }
    }

    stage('Publishing') {
	  steps {
         echo 'publishing'
      }
        // sh "aws s3 cp target/jhipster-0.0.1-SNAPSHOT.war  s3://jhipipeline/jhipster-0.0.1-SNAPSHOT.war"
    }

    stage('Deploying') {
	  steps {
         echo 'deploying'
      }
        // sh "ssh -oStrictHostKeyChecking=no -i ~/.ssh/awsperso.pem ec2-user@$AWS_PROD_INSTANCE_IP 'aws s3 cp s3://jhipipeline/jhipster-0.0.1-SNAPSHOT.war .; chmod +x jhipster-0.0.1-SNAPSHOT.war'"
        // sh """ssh -oStrictHostKeyChecking=no -i ~/.ssh/awsperso.pem ec2-user@$AWS_PROD_INSTANCE_IP 'ps -aux | grep -i java | grep -v grep | cut -d' ' -f2 | xargs kill -9'"""
        // sh "ssh -oStrictHostKeyChecking=no -i ~/.ssh/awsperso.pem ec2-user@$AWS_PROD_INSTANCE_IP './jhipster-0.0.1-SNAPSHOT.war --spring.profiles.active=prod --spring.datasource.password=$AWS_PROD_DATABASE_PASSWORD --spring.datasource.username=jhipipe --spring.datasource.url=jdbc:mysql://$AWS_PROD_DATABASE_URL:3306/jhipipedbprod?useUnicode=true&characterEncoding=utf8&useSSL=false &'"
    }
  }
}