
def call( String stages){
                       
                       println 'Recibidos parametros ' + stages
					   
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
				       stage('Sonar')
					   {
					      if (bEjecutarSonar)
						  {
						  	env.TAREA = env.STAGE_NAME
					   		def scannerHome = tool 'sonar-scanner';
							
							withSonarQubeEnv('SonarQube') { 
							  bat "${scannerHome}\\bin\\sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build"

							}
						  }

					   }	
				       stage('Run')
					   {
					     if (bEjecutarRun)
						 {
						  env.TAREA = env.STAGE_NAME
						  bat "nohup start gradle bootRun &"
						  sleep 20
						 }
					   }	
				       stage('Rest')
					   {
					      if (bEjecutarRest)
						  {
						    env.TAREA = env.STAGE_NAME
					       bat 'curl -X GET http://localhost:8082/rest/mscovid/test?msg=testing'
						  }
					   }					   
				   	   stage('Nexus')
					   {
					      if (bEjecutarNexus)
						  {
						  	env.TAREA = env.STAGE_NAME
					        nexusPublisher nexusInstanceId: 'Nexus',
							nexusRepositoryId: 'test-nexus',
							packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'C:\\Users\\cmartinez\\Documents\\personal\\devops\\Unidad 3\\tarea11\\ejemplo-gradle\\build\\libs\\DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]

						  }

					   }
				   

}

return this;