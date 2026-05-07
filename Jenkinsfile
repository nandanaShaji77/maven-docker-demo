pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Clone') {
            steps {
                git 'https://github.com/nandanaShaji77/maven-docker-demo.git'
            }
        }

        stage('Build Maven Project') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t docker-demo:1.0 .'
            }
        }

        stage('Run Docker Container') {
            steps {
                bat 'docker run --name docker-container docker-demo:1.0'
            }
        }
    }
}