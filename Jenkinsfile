pipeline {
    agent { node { label 'pcf-build-node' } }
    parameters {
        choice(name: 'environment', choices: ['qa', 'prod'], description: 'Environment')
        choice(name: 'scenario', choices: ['happy_path', 'error_handling'], description: 'Scenario')
    }
    triggers {
        cron('0 6 * * *')
    }
    stages {
        stage ('Clean Workspace') {
            steps {
                deleteDir()
            }
        }
        stage ('Git Checkout') {
            steps {
                //bat "set"
                //bat "git clone -b devops git@github.albertsons.com:albertsons/OSCO-APS-WebUIAutomation.git ."
                git branch: 'master',
                    credentialsId:'stratusjenkins-github-enterprise-token',
                    url: 'https://github.albertsons.com/albertsons/scgo-api-automation.git'
            }
        }
        stage('API Testing') {
            //tools {maven 'Maven_3.6.0'; jdk 'JDK 1.8.0_281'}
            steps {
                //git branch: "${params.branch}", poll: false, url: "https://${params.token}@${params.repo}"
                sh "mvn clean site -Dsuite=${params.scenario}.xml -Denv=${params.environment}"
                script {
                    allure([
                        includeProperties: false,
                        jdk: 'JDK_1.8',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'target/allure-results']]
                    ])
                }
            }
        }
    }
}
