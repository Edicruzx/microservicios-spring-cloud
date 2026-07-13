pipeline {
  agent any
  environment {
    REGISTRY = credentials('docker-registry')
  }
  stages {
    stage('Test and package') {
      steps { powershell '.\\build-all.ps1' }
    }
    stage('Build images') {
      steps {
        script {
          def services = ['discovery-server','config-server','user-service','product-service','client-service','cloud-gateway','authentication-server-oauth2','authentication-client-oauth2','audit-service','product-query-service','order-saga-service']
          services.each { service ->
            bat "docker build -t %REGISTRY_USR%/${service}:${BUILD_NUMBER} ${service}"
          }
        }
      }
    }
    stage('Push images') {
      steps {
        bat 'echo %REGISTRY_PSW% | docker login -u %REGISTRY_USR% --password-stdin'
        script {
          def services = ['discovery-server','config-server','user-service','product-service','client-service','cloud-gateway','authentication-server-oauth2','authentication-client-oauth2','audit-service','product-query-service','order-saga-service']
          services.each { service -> bat "docker push %REGISTRY_USR%/${service}:${BUILD_NUMBER}" }
        }
      }
    }
    stage('Deploy AKS') {
      when { expression { return fileExists('k8s') } }
      steps { bat 'kubectl apply -f k8s/' }
    }
  }
}
