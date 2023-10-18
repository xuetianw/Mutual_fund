pipeline {
    agent any
    stages {
        stage("Build") {
            steps {
                sh "sudo npm install"
                sh "sudo npm run build"
            }
        }
        stage("Deploy") {
            steps {
                sh "sudo rm -rf /var/www/mutual-funds"
                sh "sudo cp -r ${WORKSPACE}/build/ /var/www/mutual-funds/"
            }
        }
    }
}


// pipeline {
//     agent {
//         any {
//             image 'node:16-alpine' 
//             args '-p 3000:3000' 
//         }
//     }
//     stages {
//         stage('Build') { 
//             steps {
//                 sh 'npm install' 
//             }
//         }
//     }
// }