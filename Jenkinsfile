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
                git branch: 'master',
                    credentialsId:'stratusjenkins-github-enterprise-token',
                    url: 'https://github.albertsons.com/albertsons/scgo-api-automation.git'
            }
        }
        stage('API Testing') {
            steps {
                sh "mvn clean site -Dsuite=${params.scenario}.xml -Denv=${params.environment}"
                sh "echo ${env.BRANCH_SHORT_NAME}"
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
