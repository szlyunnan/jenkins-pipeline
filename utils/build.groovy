#!/usr/bin/env groovy

/*
    每个build方式基本需要一个参数, 没有参数可以"" 取代
        cmd     获取构建产物的命令
        target  其他代码获取build产物的路径
*/

// load("${jenkinsFileDir}/cfg/cfg.groovy")

def GetPackage(cmd) {
    return sh (script: "${cmd}", returnStdout: true).trim()
}

// build common java
def BuildCommonJava(cmd) {
    withMaven(
        jdk: "${JAVA_VERSION}",
        maven: "${MVN_VERSION}") {sh "export PATH=\$PATH:${MVN_PATH} && mvn install -Dmaven.test.skip=true package -U"}
    javaApk = GetPackage("${cmd}")
}

// build common vue
def BuildCommonVue(target) {
    if ("${target}" != "") {
        if ("${target}" == "client") {
            sh """export PATH=\$PATH:${NODE_PATH} && 
            cd ${target}/ && cnpm install && 
            cnpm run build"""
        } else {
            sh """export PATH=\$PATH:${NODE_PATH} && 
            cd jw-scaffold/ && npm --ignore-scripts install && 
            npm run build target=${target}"""
        }
    } else {
        sh "export PATH=\$PATH:${NODE_PATH} && cnpm install && cnpm run build"
    }
}

def BuildCommonVueModeler(target) {
    sh """export PATH=\$PATH:${NODE_PATH} && 
    cd ${target} && cnpm install && cnpm run build
    """
}

// build common nodejs
def BuildCommonNodejs(target) {
    if ("${target}" != "") {
        sh "export PATH=\$PATH:${NODE_PATH} && cd ${target}/ && npm --unsafe-perm=true --allow-root install"
    } else {
        sh "export PATH=\$PATH:${NODE_PATH} && npm --unsafe-perm=true --allow-root install"
    }
}


/*
    每个方式基本需要两个参数, 没有参数可以"" 取代
        src   Docker COPY 的源目录
*/

def ArchiveArtifacts(apk) {
    archiveArtifacts artifacts: "${apk}", fingerprint: true
}

def TarApk(src) {
    if ("${src}" != "") {
        sh """tar -czf ${JOB_NAME}.gz ./${src} --exclude=${JOB_NAME}.gz \
            --exclude=Dockerfile --exclude=*.yaml"""
    } else {
        sh """tar -czf ${JOB_NAME}.gz ./* --exclude=${JOB_NAME}.gz \
            --exclude=Dockerfile --exclude=*.yaml"""
    }
    def tarApk = GetPackage("ls ${JOB_NAME}.gz")
    ArchiveArtifacts("${tarApk}")
}

return this
