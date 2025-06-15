pipeline {
  agent any

  environment {
    IMAGE = 'pizzeria-back-img'
    CONTAINER = 'pizzeria-back'
  }

  stages {
    stage('Build') {
      steps {
        sh """
          if docker images | grep -q ${IMAGE}; then
              docker rmi -f ${IMAGE} || true
          fi
        """

        sh """
          docker build -t ${IMAGE} .
        """
      }
    }

    stage('Deploy') {
      steps {
        sh """
          if docker ps -a | grep -q ${CONTAINER}; then
            docker stop ${CONTAINER} || true
            docker rm ${CONTAINER} || true
          fi
        """

        sh """
          docker run -d --name ${CONTAINER} -p 8084:8080 --restart=always ${IMAGE}
        """
      }
    }
  }
}