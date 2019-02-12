#!/usr/bin/env groovy

// binary path
// JAVA_PATH    = ""
JAVA_VERSION = "jdk1.8.0_181"
MVN_PATH     = "/usr/local/maven/bin"
MVN_VERSION  = "MAVEN3.5.4"
NODE_PATH    = "/usr/local/node-v8.9.4-linux-x64/bin" 

// app info
APP_NAME     = "${JOB_NAME}"[4..-1]

// skip action
// SKIP_TEST    = false
// SKIP_SONAR   = false
SKIP_CLEAN   = false
SKIP_DEPLOY  = false

// tar action
TAR_TAG      = true

// print env tip
ENV_DEV      = "开发环境"
ENV_TEST     = "测试环境"
ENV_PRE      = "预发布环境"
TIP_TEST     = "缺少操作测试环境的权限"
TIP_PRE      = "缺少操作预发布环境的权限"

// kube control
KUBE_DEV     = "dev"
KUBE_TEST    = "test"
KUBE_PRE     = "pro"

// gitlab ID
GITLAB_ID    = ""

// docker image
JAVA_IMG     = "test.cc.co/base/openjdk:v8"
NODEJS_IMG   = "test.cc.co/base/nodejs:8.9.4-pm2-docker"
VUE_IMG      = "test.cc.co/base/nginx:v1.14"
PYTHON_IMG   = "test.cc.co/base/python:v3.6"

// docker lang
DOCKER_LANG  = "C.UTF-8"

// docker
TIME_ZONE           = "Asia/Shanghai"
if ("${versionno}" == "" ) {
    BUILD_VERSION   = "v1.0"
} else {
    BUILD_VERSION   = "${versionno}"
}
HARBOR_URL          = "test.cc.co"
HARBOR_REPO_PREFIX  = baseInfo.get("harborprefix")
FULL_IMAGE_NAME     = "${HARBOR_REPO_PREFIX}/${JOB_NAME}:${BUILD_VERSION}.${BUILD_NUMBER}"
DOCKER_IMAGES       = "${HARBOR_URL}/${FULL_IMAGE_NAME}"

// slave node info 
//      sonarqube
SONARQUBE_SERVER    = ""

return this
