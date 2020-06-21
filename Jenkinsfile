pipeline {
  agent any
  stages {
    stage('检出') {
      steps {
        checkout([
          $class: 'GitSCM',
          branches: [[name: env.GIT_BUILD_REF]],
          userRemoteConfigs: [[
            url: env.GIT_REPO_URL,
            credentialsId: env.CREDENTIALS_ID
          ]]])
        }
      }
      stage('编译') {
        steps {
          sh 'mvn package -Dmaven.test.skip=ture'
        }
      }
      stage('构建镜像') {
        steps {
          sh "docker build -t ${env.DOCKER_REPOSITORY_NAME}:${env.DOCKER_IMAGE_NAME} -t ${env.DOCKER_REPOSITORY_NAME}:latest ."
        }
      }
      stage('推送镜像') {
        steps {
          script {
            docker.withRegistry("https://${DOCKER_REGISTRY_HOSTNAME}", "${env.DOCKER_REGISTRY_CREDENTIAL}") {
              docker.image("${env.DOCKER_REPOSITORY_NAME}:${env.DOCKER_IMAGE_NAME}").push()
            }
            docker.withRegistry("https://${DOCKER_REGISTRY_HOSTNAME}", "${env.DOCKER_REGISTRY_CREDENTIAL}") {
                          docker.image("${env.DOCKER_REPOSITORY_NAME}:latest").push()
            }
          }

        }
      }
    }
    environment {
      DOCKER_REGISTRY_HOSTNAME = "${env.TCR_REGISTRY_HOSTNAME}"
      DOCKER_REGISTRY_CREDENTIAL = "${env.TCR_REGISTRY_CREDENTIAL}"
      DOCKER_REPOSITORY_NAME = "${env.TCR_NAMESPACE_NAME}/${env.TCR_REPOSITORY_NAME}"
      DOCKER_IMAGE_NAME = "${env.TCR_IMAGE_NAME}"
    }
  }