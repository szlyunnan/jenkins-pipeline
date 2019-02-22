#!/usr/bin/env groovy

def CodePull() {
    checkout([$class: "GitSCM", branches: [[name: "*/master"]], 
            doGenerateSubmoduleConfigurations: false,
            clearWorkspace: true,
            userRemoteConfigs: [[credentialsId: "${GITLAB_ID}", 
            url: "http://xxx.xxx.xxx/platform/test.git"]]
    ]) 
}

return this
