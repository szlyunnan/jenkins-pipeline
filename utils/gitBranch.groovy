#!/usr/bin/env groovy

def PullSingle() {
    if ("${gitbranch}" == "" ) {
        branchc = baseInfo.get("branch")
    } else {
        (tag1, tag2) = GitBranch("${gitbranch}")
        if (tag1 == "false"){
            println(GitErrorMsg())
            error ""
        } else {
            gitUrl = tag1[0]
            branchc = tag1[1]
        }
    }

    checkout([$class: "GitSCM", branches: [[name: "*/${branchc}"]], 
            doGenerateSubmoduleConfigurations: false,
            clearWorkspace: true,
            userRemoteConfigs: [[credentialsId: "${GITLAB_ID}", url: "${gitUrl}"]]
    ]) 
}

def PullMultiple() {
    if ("${gitbranch}" == "" ) {
        branch1 = baseInfo.get("branch1")
        gitUrl1 = baseInfo.get("gitUrl1")

        branch2 = baseInfo.get("branch2")
        gitUrl2 = baseInfo.get("gitUrl2")

    } else {
        (tag1, tag2) = GitBranch("${gitbranch}")
        if (tag1 == "false") {
            println(GitErrorMsg())
            error ""
        } else {
            if (tag2 == "false") {
                gitUrl1 = tag1[0]
                branch1 = tag1[1]
                gitUrl2 = baseInfo.get("gitUrl2")
                branch2 = baseInfo.get("branch2")
            } else {
                gitUrl1 = tag1[0]
                branch1 = tag1[1]
                gitUrl2 = tag2[0]
                branch2 = tag2[1]
            }
        }   
    }

    def dir1    = baseInfo.get("dir1")

    def dir2    = baseInfo.get("dir2")

    dir(path: "${WORKSPACE}"){
        checkout([$class: "GitSCM", branches: [[name: "*/${branch1}"]], 
            doGenerateSubmoduleConfigurations: false,
            extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: "${dir1}"]], 
            clearWorkspace: true,
            userRemoteConfigs: [[credentialsId: "${GITLAB_ID}", url: "${gitUrl1}"]]
        ])  
        checkout([$class: 'GitSCM', branches: [[name: "*/${branch2}"]], 
                doGenerateSubmoduleConfigurations: false, 
                extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: "${dir2}"]], 
                clearWorkspace: true, 
                userRemoteConfigs: [[credentialsId: "${GITLAB_ID}", url: "${gitUrl2}"]]
        ])
    }
}

def GitBranch(gitAdd) {
    gitUrl2 = baseInfo.get("gitUrl2")
    tagF = "false"
    if ("${gitAdd}".contains(';')) {
        branchList = "${gitAdd}".split(';')
        if (branchList.size() == 2) {
            branchOne = branchList[0]
            branchTwo = branchList[1]
            branchOneList = branchOne.split("\\*")
            branchTwoList = branchTwo.split("\\*")
            if (branchOneList.size() == 2 && branchTwoList.size() == 2) {
                return [branchOneList, branchTwoList]
            } else {
                return ["${tagF}", "${tagF}"]
            }
        } else {
            branchOne = branchList[0]
            branchList = "${branchOne}".split("\\*")
            if (branchList.size() == 2) {
                if ("${gitUrl2}" != null) {
                    branchListOne = [branchList[0], branchList[1]]
                    branchListTwo = [baseInfo.get("gitUrl2"), baseInfo.get("branch2")]
                    return [branchListOne, branchListTwo]
                } else {
                    branchListOne = [branchList[0], branchList[1]]
                    branchListTwo = "false"
                    return [branchListOne, branchListTwo]
                }
            } else {
                return ["${tagF}", "${tagF}"]
            }
        }
    } else {
        branchList = "${gitAdd}".split("\\*")
        if (branchList.size() == 2) {
            if ("${gitUrl2}" != null) {
                branchListOne = [branchList[0], branchList[1]]
                branchListTwo = [baseInfo.get("gitUrl2"), baseInfo.get("branch2")]
                return [branchListOne, branchListTwo]
            } else {
                branchListOne = [branchList[0], branchList[1]]
                branchListTwo = "false"
                return [branchListOne, branchListTwo]
            }
        } else {
            return ["${tagF}", "${tagF}"]
        }
    }
}

return this
