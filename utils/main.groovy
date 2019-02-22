#!/usr/bin/env groovy

def RunMain() {
    stage('Check Env') {
        println(messageLoad.PrintEvn())
    }
    stage("Preparation") {
        if (SKIP_CLEAN == false) {
            cleanWs()
        } else {
            println('Preparation path')
        }
    }
    stage('Pull Code') {
        gitLoad.CodePull()
        /*
        if ("${codescan}" != "") {
            node("${SONARQUBE_SERVER}") {
                PullCode()
            }
        }
        */
    }
    stage('Build Code') {
        buildLoad.BuildVue()
        /*
        if ("${codescan}" != "") {
            node("${SONARQUBE_SERVER}") {
                BuildCode()
            }
        }
        */
    }
    stage('Code Scan') {
        println('code scan')
        /*
        node("${SONARQUBE_SERVER}") {
            if ("${codescan}".toBoolean() == true) {
                println('code scan yes')
            } else {
                println('code scan no')
            }
        }
        */
    }
    stage('Build Image') {
        dir(path: "${WORKSPACE}") {
            sh "docker build . --force-rm --no-cache -t ${DOCKER_IMAGES}" 
        }
    }
    stage('Push Image') {
        sh "docker push ${DOCKER_IMAGES}"
        sh "docker image rm ${DOCKER_IMAGES}"
    }
    stage('Deploy') {
        if (SKIP_DEPLOY == false) {
            def kubeconfig = kubectlLoad.KubeControl()
            sh "kubectl config use-context ${kubeconfig}"
            sh "kubectl apply k8s.yaml"
        } else {
            println("Deploy to k8s")
        }
    }
}

return this
