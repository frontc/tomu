pipeline {
  agent any
  stages {
    stage('检出') {
      steps {
        checkout([$class: 'GitSCM',
        branches: [[name: env.GIT_BUILD_REF]],
        userRemoteConfigs: [[
          url: env.GIT_REPO_URL,
          credentialsId: env.CREDENTIALS_ID
        ]]])
      }
    }
    stage('编译') {
      steps {
        sh 'mvn package -Dmaven.test.skip=true'
      }
    }
    stage('构建 Docker 镜像') {
      steps {
        sh "docker build -f ${env.DOCKERFILE_PATH} -t ${env.CODING_DOCKER_IMAGE_NAME}:${env.DOCKER_IMAGE_VERSION} ${env.DOCKER_BUILD_CONTEXT}"
      }
    }
    stage('推送到 CODING Docker 制品库') {
      steps {
        script {
          docker.withRegistry(
            "${env.CCI_CURRENT_WEB_PROTOCOL}://${env.CODING_DOCKER_REG_HOST}",
            "${env.CODING_ARTIFACTS_CREDENTIALS_ID}"
          ) {
            docker.image("${CODING_DOCKER_IMAGE_NAME}:${env.DOCKER_IMAGE_VERSION}").push()
          }
        }

      }
    }
    stage('部署到远端服务') {
      steps {
        script {
          def remoteConfig = [:]
          remoteConfig.name = "my-remote-server"
          remoteConfig.host = "${env.REMOTE_HOST}"
          remoteConfig.port = "${env.REMOTE_SSH_PORT}".toInteger()
          remoteConfig.allowAnyHosts = true

          withCredentials([
            sshUserPrivateKey(
              credentialsId: "${env.REMOTE_CRED}",
              keyFileVariable: "privateKeyFilePath"
            ),
            usernamePassword(
              credentialsId: "${env.CODING_ARTIFACTS_CREDENTIALS_ID}",
              usernameVariable: 'CODING_DOCKER_REG_USERNAME',
              passwordVariable: 'CODING_DOCKER_REG_PASSWORD'
            )
          ]) {
            // SSH 登陆用户名
            remoteConfig.user = "${env.REMOTE_USER_NAME}"
            // SSH 私钥文件地址
            remoteConfig.identityFile = privateKeyFilePath

            // 请确保远端环境中有 Docker 环境
            sshCommand(
              remote: remoteConfig,
              command: "docker login -u ${CODING_DOCKER_REG_USERNAME} -p ${CODING_DOCKER_REG_PASSWORD} ${CODING_DOCKER_REG_HOST}",
              //sudo: true,
            )

            sshCommand(
              remote: remoteConfig,
              command: "docker rm -f tomu | true",
              //sudo: true,
            )

            // DOCKER_IMAGE_VERSION 中涉及到 GIT_LOCAL_BRANCH / GIT_TAG / GIT_COMMIT 的环境变量的使用
            // 需要在本地完成拼接后，再传入到远端服务器中使用
            DOCKER_IMAGE_URL = sh(
              script: "echo ${CODING_DOCKER_REG_HOST}/${CODING_DOCKER_IMAGE_NAME}:${env.DOCKER_IMAGE_VERSION}",
              returnStdout: true
            )

            sshCommand(
              remote: remoteConfig,
              command: "docker run -d -p 8888:8888 -v /home/web/tomu:/root/data --name tomu ${DOCKER_IMAGE_URL}",
              //sudo: true,
            )

            echo "部署成功，请到 http://${env.REMOTE_HOST}:8888 预览效果"
          }
        }

      }
    }
  }
  environment {
    CODING_DOCKER_REG_HOST = "${env.CCI_CURRENT_TEAM}-docker.pkg.${env.CCI_CURRENT_DOMAIN}"
    CODING_DOCKER_IMAGE_NAME = "${env.PROJECT_NAME.toLowerCase()}/${env.DOCKER_REPO_NAME}/${env.DOCKER_IMAGE_NAME}"
  }
}