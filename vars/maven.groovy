def call(){
  
   stage('Compile') {
        env.TAREA = env.STAGE_NAME
        sh 'mvn clean compile -e'
    }
    stage('Test') {
	    env.TAREA = env.STAGE_NAME
        sh 'mvn clean test -e'
    }
    stage('Jar') {
	    env.TAREA = env.STAGE_NAME
        sh 'mvn clean package -e'
    }
    stage('SonarQube analysis') {
	    env.TAREA = env.STAGE_NAME
        withSonarQubeEnv(installationName: 'SonarQube') {
          sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
        }
    }
	
	stage('Run') {
	            env.TAREA = env.STAGE_NAME
				sh 'mvn spring-boot:run &'
				sleep 20
	}
	stage('Rest'){
	   env.TAREA = env.STAGE_NAME
	   bat 'curl -X GET http://localhost:8082/rest/mscovid/test?msg=testing'
   }
	
    stage('Nexus') {
        env.TAREA = env.STAGE_NAME
        nexusPublisher nexusInstanceId: 'Nexus',
        nexusRepositoryId: 'test-nexus',
        packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'C:\\Users\\cmartinez\\Documents\\personal\\devops\\Unidad 3\\tarea10\\ejemplo-maven\\build\\DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]

    }

}

return this;