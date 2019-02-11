#!/usr/bin/env groovy

def PrintMessage() {
    if ("${enviroment}" == "dev") {
        return "${ENV_DEV}"
    } else if ("${enviroment}" == "test") {
        return "${ENV_TEST}"
    } else if ("${enviroment}" == "pre") {
        return "${ENV_PRE}"
    }
}

def GitErrorMsg(gitBranch) {
    return """====== git branch format [ ${gitBranch} ] error, please check it ! 
            Example:
                http://test-project.git*master
                http://test-project.git*master;
                http://test-project1.git*master;http://test-project2.git*master
    """
}

return this
