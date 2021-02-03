pipeline {
  agent {
    node {
      label 'develop'
    }

  }
  stages {
    stage('Source') {
      steps {
        git(url: 'https://lab.ssafy.com/s04-webmobile2-sub3/s04p13b208.git', branch: 'develop')
      }
    }

    stage('Build') {
      steps {
        tool 'gradle'
        tool 'node'
      }
    }

    stage('Deploy') {
      steps {
        sh 'echo "Deploy Success"'
      }
    }

  }
}