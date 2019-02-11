#!/usr/bin/env groovy

def RunStageNotPermission(tipStr) {
    stage('Check Env') {
        println("${tipStr}")
    }
}

def RunStagePermission(BuildCode, BuildImage, PullCode) {
    stage('Check Env') {
        println(PrintEvn())
    }
    stage("Preparation") {
        if (SKIP_CLEAN == false) {
            cleanWs()
        } else {
            println('Preparation path')
        }
    }
    stage('Pull Code') {
        PullCode()
        /*
        if ("${codescan}" != "") {
            node("${SONARQUBE_SERVER}") {
                PullCode()
            }
        }
        */
    }
    stage('Build Code') {
        if ("${APP_NAME}" == "micro-workflow-modeler") {
            dir(path: "${WORKSPACE}") {
                sh "/bin/cp /root/.cicd-shell/files/app-cfg.js src/main/resources/static/editor-app/app-cfg.js" 
            } 
        }
        BuildCode()
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
            BuildImage()
            sh "docker build . --force-rm --no-cache -t ${DOCKER_IMAGES}" 
        }
    }
    stage('Push Image') {
        sh "docker push ${DOCKER_IMAGES}"
        sh "docker image rm ${DOCKER_IMAGES}"
    }
    stage('Deploy') {
        if (SKIP_DEPLOY == false) {
            def kubeconfig = KubeControl()
            sh "kubectl config use-context ${kubeconfig}"
            
            def domain = baseInfo.get("domain")
            if ("${domain}") {
                sh "bash ${shellPath} ${APP_NAME} ${containerPort} ${servicePort} ${nameSpaces} ${DOCKER_IMAGES} ${domain}"
            } else {
                sh "bash ${shellPath} ${APP_NAME} ${containerPort} ${servicePort} ${nameSpaces} ${DOCKER_IMAGES}"
            }
        } else {
            println("Deploy to k8s")
        }
    }
}

def RunMainStage(BuildCode, BuildImage) {
    wrap([$class: 'BuildUser']) { 
        if ("${enviroment}" == "pre") {
            if (BUILD_USER_ID in PERMISSION_LIST["pre"]) {
                RunStagePermission(BuildCode, BuildImage, this.&PullSingle)
            } else {
                RunStageNotPermission("${TIP_PRE}")
            } 
        } else if ("${enviroment}" == "test") {
            if (BUILD_USER_ID in PERMISSION_LIST["test"]) {
                RunStagePermission(BuildCode, BuildImage, this.&PullSingle)
            } else {
                RunStageNotPermission("${TIP_TEST}")
            }
        } else {
            RunStagePermission(BuildCode, BuildImage, this.&PullSingle)
        } 
    }
}

def RunMainScaffold(BuildCode, BuildImage) {
    wrap([$class: 'BuildUser']) { 
        if ("${enviroment}" == "pre") {
            if (BUILD_USER_ID in PERMISSION_LIST["pre"]) {
                RunStagePermission(BuildCode, BuildImage, this.&PullMultiple)
            } else {
                RunStageNotPermission("${TIP_PRE}")
            } 
        } else if ("${enviroment}" == "test") {
            if (BUILD_USER_ID in PERMISSION_LIST["test"]) {
                RunStagePermission(BuildCode, BuildImage, this.&PullMultiple)
            } else {
                RunStageNotPermission("${TIP_TEST}")
            }
        } else {
            RunStagePermission(BuildCode, BuildImage, this.&PullMultiple)
        } 
    }
}

return this
