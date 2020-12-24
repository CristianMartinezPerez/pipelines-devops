def call(String stages){

   println 'Recibidos parametros ' + stages
   
   def Jobs = stages.split(';')
   
   def bEjecutarCompile = false;
   def bEjecutarTest = false;
   def bEjecutarJar = false;
   def bEjecutarSonar = false;
   def bEjecutarRun = false;
   def bEjecutarRest = false;
   def bEjecutarNexus = false;
   
   for( String job : Jobs )
	{
	   if (job == 'Compile') bEjecutarCompile = true;
	   if (job == 'Test') bEjecutarTest = true;
	   if (job == 'Jar') bEjecutarJar = true;	   
	   if (job == 'Sonar') bEjecutarSonar = true;
	   if (job == 'Run') bEjecutarRun = true;
	   if (job == 'Rest') bEjecutarRest = true;
	   if (job == 'Nexus') bEjecutarNexus = true;						   
	}

   if (bEjecutarCompile)
	{ 
		stage('Compile') {
        env.TAREA = env.STAGE_NAME
        sh 'mvn clean compile -e'
		}
	}
   if (bEjecutarTest)
	{ 
		stage('Test') {
			env.TAREA = env.STAGE_NAME
			sh 'mvn clean test -e'
		}
	}	

   if (bEjecutarJar)
	{ 	
		stage('Jar') {
			env.TAREA = env.STAGE_NAME
			sh 'mvn clean package -e'
		}
	}
	
   if (bEjecutarSonar)
	{	
		stage('SonarQube analysis') {
			env.TAREA = env.STAGE_NAME
			withSonarQubeEnv(installationName: 'SonarQube') {
			  sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
			}
		}
	}
	
   if (bEjecutarRun)
	{
		stage('Run') {
					env.TAREA = env.STAGE_NAME
					sh 'nohup start mvn spring-boot:run &'
					sleep 20
		}
	}
	
   if (bEjecutarRest)
	{	
		stage('Rest'){
		   env.TAREA = env.STAGE_NAME
		   bat 'curl -X GET http://localhost:8082/rest/mscovid/test?msg=testing'
	   }
	}

   if (bEjecutarNexus)
	{	
		stage('Nexus') {
			env.TAREA = env.STAGE_NAME
			nexusPublisher nexusInstanceId: 'Nexus',
			nexusRepositoryId: 'test-nexus',
			packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'C:\\Users\\cmartinez\\Documents\\personal\\devops\\Unidad 3\\tarea10\\ejemplo-maven\\build\\DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]

		}
	}
}

return this;