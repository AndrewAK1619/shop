pipeline {
    agent any

    stages {
        stage('Build and test') {
            steps {
                sh '''docker build -t shop\
                -f Dockerfile \
                --security-opt label=disable \
                -v /var/lib/jenkins/.m2:root/.m2 \
                --tls-verify=false \
                --ulimit nofile=32768 .
                '''
            }
            post {
                success {
                    echo 'Success'
                }
                failure {
                    echo 'Failure'
                }
            }
        }
    }
}
