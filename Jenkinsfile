pipeline {
  agent any
  stages {
    stage('clean env') {
      steps {
        sh 'echo clean env'
      }
    }
    stage('build') {
      steps {
        sh 'echo buile'
      }
    }
    stage('docker buill') {
      parallel {
        stage('docker buill') {
          steps {
            sh 'echo docker buile'
          }
        }
        stage('tar build') {
          steps {
            sh 'echo tar build'
          }
        }
      }
    }
    stage('docker push') {
      steps {
        sh 'echo docker push'
      }
    }
    stage('deploy') {
      steps {
        sh 'echo deploy'
      }
    }
  }
  environment {
    nodeUrl = 'http://127.0.0.1:8080'
  }
}