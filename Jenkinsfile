pipeline {
  agent any
  tools { maven 'Maven' }
  triggers { pollSCM('H/2 * * * *') }
  environment { NEXUS_URL = 'http://nexus:8081' }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }
    stage('Build & Tests') {
      steps {
        // TODO A : construire et tester en mode batch
      }
    }
    stage('Publish') {
      steps {
        // TODO B : injecter le credential 'nexus-deploy'
        //          dans NEXUS_USER / NEXUS_PASSWORD, puis publier
        //          avec ci-settings.xml vers $NEXUS_URL
      }
    }
  }

  post {
    // TODO C : toujours publier les rapports de tests
    // TODO D : en cas de succès, archiver le JAR
  }
}
