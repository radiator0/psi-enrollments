properties([pipelineTriggers([githubPush()])])
node {
    env.NODEJS_HOME = "${tool 'nodejs'}"
    env.PATH="${env.NODEJS_HOME}/bin:${env.PATH}"
	
    stage('Cloning Git') {
        checkout scm
		stash includes: 'k8s/**/*', name: 'sources'
    }
        
    stage('Install dependencies') {
        sh 'npm install'
    }
     
    stage('Frontend tests') {
        sh 'npm run test-ci'
    }  
	
    stage('Backend tests') {
        sh './mvnw clean test'
    }  
	
    stage('Integration tests') {
        sh './mvnw clean verify'
    }
    
    stage('Packaging and publishing') {
		withCredentials([usernamePassword( credentialsId: 'docker-hub-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
			sh './mvnw -Pprod package -DskipTests jib:build'
		}
    }
}
podTemplate {
    node(POD_LABEL) {
	
		stage('Deploying DB') {
			echo 'deploying'
			unstash 'sources'
			kubernetesDeploy(kubeconfigId: 'kubeconfig-credentials-id', configs: 'k8s/postgres.yaml')
		}
		
		stage('Deploying app') {
			echo 'deploying'
			unstash 'sources'
			kubernetesDeploy(kubeconfigId: 'kubeconfig-credentials-id', configs: 'k8s/deploy-service.yaml')
			kubernetesDeploy(kubeconfigId: 'kubeconfig-credentials-id', configs: 'k8s/deploy.yaml')
		}
	}
}
