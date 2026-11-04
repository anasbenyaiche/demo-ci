
pipeline {
    agent {
        label 'built-in'
    }

    tools {
        maven 'Maven-3'
    }

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
      stage('Checkout GitHub') {
      steps {
        git branch: 'main',
                    url: 'https://github.com/anasbenyaiche/demo-ci.git'
      }
      }

        stage('Build & Unit Tests') {
      steps {
        sh 'mvn -B clean verify'
      }
      post {
        always {
          junit allowEmptyResults: false,
                          testResults: 'target/surefire-reports/*.xml'
        }
      }
        }

        stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('SonarQube-Local') {
          sh '''
                        mvn -B \
                          org.sonarsource.scanner.maven:sonar-maven-plugin:5.8.0.7211:sonar \
                          -Dsonar.projectKey=demo-ci \
                          -Dsonar.projectName=demo-ci
                    '''
        }
      }
        }

        stage('Quality Gate') {
      steps {
        timeout(time: 5, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
        }

        stage('Publish to Nexus') {
      steps {
        withCredentials([
                    usernamePassword(
                        credentialsId: 'nexus-credentials',
                        usernameVariable: 'NEXUS_USER',
                        passwordVariable: 'NEXUS_PASSWORD'
                    )
                ]) {
          sh '''
                        set +x
                        umask 077

                        SETTINGS_FILE=$(mktemp)
                        trap 'rm -f "$SETTINGS_FILE"' EXIT

                        cat > "$SETTINGS_FILE" <<EOF
<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0">
    <servers>
        <server>
            <id>nexus-snapshots</id>
            <username>${NEXUS_USER}</username>
            <password>${NEXUS_PASSWORD}</password>
        </server>
        <server>
            <id>nexus-releases</id>
            <username>${NEXUS_USER}</username>
            <password>${NEXUS_PASSWORD}</password>
        </server>
    </servers>
</settings>
EOF

                        mvn -B -ntp \
                          -s "$SETTINGS_FILE" \
                          -DskipTests deploy
                    '''
        }
      }
        }

        stage('Docker Build & Test') {
      agent {
        label 'docker-agent'
      }

      steps {
        git branch: 'main',
                    url: 'https://github.com/anasbenyaiche/demo-ci.git'

        sh '''
                    docker version

                    docker build \
                      -t demo-ci:${BUILD_NUMBER} .

                    OUTPUT=$(docker run --rm demo-ci:${BUILD_NUMBER})
                    printf '%s\\n' "$OUTPUT"

                    printf '%s\\n' "$OUTPUT" |
                      grep -F 'Application DEMO-CI démarrée !'
                '''
      }
        }

    stage('Docker Push to Nexus') {
      agent {
        label 'docker-agent'
      }

      steps {
        withCredentials([
            usernamePassword(
                credentialsId: 'nexus-credentials',
                usernameVariable: 'NEXUS_USER',
                passwordVariable: 'NEXUS_PASSWORD'
            )
        ]) {
            sh '''
                set -eu
                set +x

                umask 077
                export DOCKER_CONFIG="$(mktemp -d)"
                trap 'rm -rf "$DOCKER_CONFIG"' EXIT

                printf '%s' "$NEXUS_PASSWORD" |
                    docker login nexus:8082 \
                        -u "$NEXUS_USER" \
                        --password-stdin

                docker tag \
                    "demo-ci:${BUILD_NUMBER}" \
                    "nexus:8082/demo-ci:${BUILD_NUMBER}"

                docker push \
                    "nexus:8082/demo-ci:${BUILD_NUMBER}"
            '''
        }
      }
    }
    }

    post {
        success {
      echo 'CI/CD PIPELINE SUCCESS'
        }

        failure {
      echo 'CI/CD PIPELINE FAILED'
        }

        always {
      echo 'Pipeline terminé.'
        }
    }
}
