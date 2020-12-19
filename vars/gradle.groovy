
def call(){
  
				       stage('Build & Test')
					   {
					      env.TAREA = env.STAGE_NAME
					      bat "gradle clean build"  

					   }
				       stage('Sonar')
					   {
							env.TAREA = env.STAGE_NAME
					   		def scannerHome = tool 'sonar-scanner';
							
							withSonarQubeEnv('SonarQube') { 
							  bat "${scannerHome}\\bin\\sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build"

							}
					   }	
				       stage('Run')
					   {
					      env.TAREA = env.STAGE_NAME
						  bat "nohup start gradle bootRun &"
						  sleep 20

					   }	
				       stage('Rest')
					   {
					       env.TAREA = env.STAGE_NAME
					       bat 'curl -X GET http://localhost:8082/rest/mscovid/test?msg=testing'
					   }					   
				   	   stage('Nexus')
					   {
					        env.TAREA = env.STAGE_NAME
					        nexusPublisher nexusInstanceId: 'Nexus',
							nexusRepositoryId: 'test-nexus',
							packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'C:\\Users\\cmartinez\\Documents\\personal\\devops\\Unidad 3\\tarea11\\ejemplo-gradle\\build\\libs\\DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]

					   }
				   

}

return this;