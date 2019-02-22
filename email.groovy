try {
    node('master') {
        stage 'build Job1'
        build 'Job1'

        stage 'scp apk to stf node'
        def apkDir="/home/test/.jenkins/jobs/Job1/workspace/app/build/outputs/apk"
        def destDir="stf@linux-for-stf:/home/stf/jenkins/apks"
        sh "scp $apkDir/test.apk $destDir"
    }
    node('Linux-for-stf') {
        stage 'install apk'
        def device="ABCD1234"
        def apkFile="/home/stf/jenkins/apks/test.apk"
        sh "adb -s $device install -r $apkFile"

        stage 'run monkey'
        sh "adb -s $device shell monkey -p com.example.ExampleApp -s 100 --ignore-crashes --ignore-timeouts --throttle 700 -v 10000"
    }
    node('master') {
        stage 'send email'
        mail to: 'test@gmail.com',
        subject: "Job '${env.JOB_NAME}' (${env.BUILD_NUMBER}) succeeded",
        body: "Please go to ${env.BUILD_URL} and verify the build"
    }
} catch (Exception e) {
    node('master') {
        stage 'send email'
        echo '$e'
        mail to: 'test@gmail.com',
        subject: "Job '${env.JOB_NAME}' (${env.BUILD_NUMBER}) failed",
        body: "Please go to ${env.BUILD_URL} and verify the build"
    }
}