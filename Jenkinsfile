pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        sh """
          if docker ps -a | grep -q pizzeria-back; then
            docker stop pizzeria-back || true
            docker rm pizzeria-back || true
            if docker images | grep -q pizzeria-back; then
              docker rmi -f pizzeria-back || true
            fi
          fi
        """

        sh """
          docker build -t pizzeria-back .
        """
      }
    }

    stage('Deploy') {
      steps {
        sh """
          docker run -d --name pizzeria-back -p 8084:8080 --restart=always pizzeria-back
        """
      }
    }
  }
}