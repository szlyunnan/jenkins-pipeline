#!/usr/bin/env groovy

def GetPackage(cmd) {
    return sh (script: "${cmd}", returnStdout: true).trim()
}

// build common vue
def BuildVue(target) {
    sh "export PATH=\$PATH:${NODE_PATH} && npm install && npm run build"
}

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
