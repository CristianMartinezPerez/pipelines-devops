def call(){
  
		pipeline {
			agent any
			
			parameters { choice(name: 'herramienta', choices: ['gradle', 'maven'], description: '') ,
						 string{name: 'stage', defaultValue:'',description:''}
			}

			stages {
				stage('Pipeline') {
					steps {

						   script{
						   
						     def validGradle = "Build & Test;Sonar;Run;Rest;Nexus"
							 def validMaven = "Compile;Test;Jar;SonarQube analysis;Run;Rest;Nexus"
							 
							 
						   
							 println "Ejecucion :" + params.herramienta
							 env.HERRAMIENTA = params.herramienta 
							 
							if (params.herramienta == 'gradle') 
							{
								gradle.call()
							}
							else 
							{
								maven.call()
							}
							 
							 //
							  
							  
							 
							 
							 
							 //
						   }
					}
								
				}
				}
				

			}
		}

return this;