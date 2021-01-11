// ejecucion.groovy

import org.cl.*

def call(){
  
		pipeline {
			agent any
			
			parameters { choice(name: 'herramienta', choices: ['gradle', 'maven'], description: '') 
						 string(name: 'stage', defaultValue:'',description:'')
			}

			stages {
				stage('Pipeline') {
					steps {

						   script{
						     bat 'set'
					             println "rama:"+ env.GIT_BRANCH
							   
						     def validGradle = "Build & Test;Sonar;Run;Rest;Nexus"
							 def validMaven = "Compile;Test;Jar;Sonar;Run;Rest;Nexus"
							 def validStages = "";
							 
							 def funciones   = new Funciones()
							 def bOK = false
							 
							 env.HERRAMIENTA = params.herramienta
							 
							 if (params.herramienta == 'gradle') 
								validStages  = validGradle
                             else
								validStages  = validMaven	
							 
							 def stagePar=""
							   
							 if (params.stage == "")
							     stagePar = validStages
							 else
							     stagePar = params.stage

							bOK = funciones.CheckStage(params.herramienta,stagePar,validStages)
								  if (bOK)
								  {
								  	   println "Parametros stage enviados :" + stagePar
									   if (params.herramienta == 'gradle') 
									       gradle.call(stagePar)
										else
										   maven.call(stagePar)
								  }
								  else
								      {
									   error("Parametros no Valido")
									  }
								      

						   }
					}
								
				}
				}
				

			}
		}

return this;
