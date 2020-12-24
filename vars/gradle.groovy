
def call( String stages){
                       
                       println 'Recibidos parametros ' + stages
					   println 'Directorio ' + env.WORKSPACE
					   
					   def Jobs = stages.split(';')
					   
					   def bEjecutarBT = false
					   def bEjecutarSonar = false;
					   def bEjecutarRun = false;
					   def bEjecutarRest = false;
					   def bEjecutarNexus = false;
					   
					   
					   for( String job : Jobs )
						{
						   if (job == 'Build & Test') bEjecutarBT = true;
						   if (job == 'Sonar') bEjecutarSonar = true;
						   if (job == 'Run') bEjecutarRun = true;
						   if (job == 'Rest') bEjecutarRest = true;
						   if (job == 'Nexus') bEjecutarNexus = true;						   
						}
 					   if (bEjecutarBT)
						{ 
						   stage('Build & Test')
						   {

							  env.TAREA = env.STAGE_NAME
							  bat "gradle clean build"  
						   }
					    }
					   
					   if (bEjecutarSonar)
						{					   
						   stage('Sonar')
						   {
								env.TAREA = env.STAGE_NAME
								def scannerHome = tool 'sonar-scanner';
								
								withSonarQubeEnv('SonarQube') { 
								  bat "${scannerHome}\\bin\\sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build"
								}
							  
						   }
					    }

					   if (bEjecutarRun)
						{						
						   stage('Run')
						   {
							  env.TAREA = env.STAGE_NAME
							  bat "nohup start gradle bootRun &"
							  sleep 20
							 
						   }
						}


					   if (bEjecutarRest)
						{						
						  stage('Rest')
						  {
						    env.TAREA = env.STAGE_NAME
					        bat 'curl -X GET http://localhost:8082/rest/mscovid/test?msg=testing'
						  }
					   }		

					   if (bEjecutarNexus)
						{					   
						   stage('Nexus')
						   {

								env.TAREA = env.STAGE_NAME
								nexusPublisher nexusInstanceId: 'Nexus',
								nexusRepositoryId: 'test-nexus',
								packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: "${env.WORKSPACE}\\ejemplo-gradle\\build\\libs\\DevOpsUsach2020-0.0.1.jar"]], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]

							  
						   }
					   }
				   

}

return this;