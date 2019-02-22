#!/usr/bin/env groovy

settingsFile   = "settings.groovy"
permissionFile = "permission.groovy"
messageFile    = "message.groovy"
kubectlFile    = "kubectl.groovy"
gitFile        = "gitBranch.groovy"
buildFile      = "build.groovy"
mainFile       = "main.groovy"

node('master'){
  settingsLoad   = load("${settingsFile}")
  permissionLoad = load("${permissionFile}")
  messageLoad    = load("${messageFile}")
  kubectlLoad    = load("${kubectlFile}")
  gitLoad        = load("${gitFile}")
  buildLoad      = load("${buildFile}")
  mainLoad       = load("${mainFile}") 

  mainLoad.RunMain()
}

