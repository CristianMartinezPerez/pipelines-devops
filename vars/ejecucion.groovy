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

							bOK = funciones.CheckStage(params.herramienta,params.stage,validStages)
								  if (bOK)
								  {
								  	   println "Parametros stage enviados :" + params.stage
									   if (params.herramienta == 'gradle') 
									       gradle.call(params.stage)
										else
										   maven.call(params.stage)
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