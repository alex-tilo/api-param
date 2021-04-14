pipeline {
    agent any
    parameters {
        choice(name: 'environment', choices: ['qa', 'prod'], description: 'Environment')
        choice(name: 'scenario', choices: ['happy_path', 'error_handling'], description: 'Scenario')
        string(name: 'repo', defaultValue: 'github.albertsons.com/albertsons/scgo-api-automation.git', description: 'GitHub Repository')
        string(name: 'branch', defaultValue: 'master', description: 'GitHub Branch')
        string(name: 'token', defaultValue: '73137bdaf4714198bd43ceaa414faee8f12e4195', description: 'GitHub Token')
    }
    
    stages {
        stage('API Testing') {
            tools {maven 'Maven_3.6.0'; jdk 'JDK 1.8.0_281'}
            steps {
                git branch: "${params.branch}", poll: false, url: "https://${params.token}@${params.repo}"
                sh "mvn clean site -Dsuite=${params.scenario}.xml -Denv=${params.environment}"
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'target/allure-results']]
                          ])
                        }
                    }
                }
        }
}