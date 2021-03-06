#!/usr/bin/env groovy

// email list
EMAIL_LIST =  [
    "test1@cc.co",
    "text2@cc.co",
    "test3@cc.co",
    "test4@cc.co",
]

// binary path
NODE_PATH    = "/usr/local/node-v8.9.4-linux-x64/bin" 

// app info
APP_NAME     = "${JOB_NAME}"[4..-1]

// skip action
SKIP_TEST    = false
SKIP_SONAR   = false
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
JAVA_IMG     = "test.cc.cq/base/openjdk:v8"
NODEJS_IMG   = "test.cc.cq/base/nodejs:8.9.4-pm2-docker"
VUE_IMG      = "test.cc.cq/base/nginx:v1.14"
PYTHON_IMG   = "test.cc.cq/base/python:v3.6"

// docker lang
DOCKER_LANG  = "C.UTF-8"

// docker
TIME_ZONE           = "Asia/Shanghai"

DOCKER_IMAGES       = "test.cc.cq/platform/platform-client:v1.0"

// slave node info 
//      sonarqube
SONARQUBE_SERVER    = ""

return this
