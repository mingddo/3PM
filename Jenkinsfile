pipeline {
	agent none
	options { skipDefaultCheckout(true) }
	stages {
		stage('Build and Test') {
			agent {
				docker {
					image 'openjdk:11'
				}
			}
			options { skipDefaultCheckout(fase) }
			steps {
				sh './gradlew clean build'
			}
		}
		stage('Docker build') {
			agent any
			steps {
				sh 'docker build -t frontend:latest frontend'
				sh 'docker build -t backend:latest backend'
			}
		}
		stage('Docker run') {
			agent any
			steps {
				sh 'docker ps -f name=frontend -q | xargs --no-run-if-empty docker container stop'
				sh 'docker ps -f name=backend -q | xargs --no-run-if-empty docker container stop'
				sh 'docker container ls -a -f name=frontend -q xargs -r docker container rm'
				sh 'docker container ls -a -f name=backend -q xargs -r docker container rm'
				sh 'docker images -f dangling=true && docker rmi $(docker images -f "dangling=true" -q)'
				sh 'docker run -d --name=frontend -p 80:80 frontend:latest'
				sh 'docker run -d --name=backend -p 8080:8080 backend:latest'
			}
		}
	}
}