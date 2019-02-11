#!/usr/bin/env groovy

def KubeControl() {
    if ("${enviroment}" == "dev") {
        return "${KUBE_DEV}"
    } else if ("${enviroment}" == "test") {
        return "${KUBE_TEST}"
    } else if ("${enviroment}" == "pre") {
        return "${KUBE_PRE}"
    }
}

return this
