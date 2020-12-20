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
							  def CheckStage(herramienta,stage, Stages) 
								{
										String[] validJobs
										validJobs = Stages.split(';')
										
										String[] jobs
										jobs = stage.split(';')
										
										def valido = false
										def stageInvalido ="";
										def countValidos = 0;
										def nuneroParametro=0;
										
										
										for( String job : jobs )
										{
											nuneroParametro++;
											for( String validJob : validJobs )
											{
												println job.toUpperCase() +"<---> "+ validJob.toUpperCase()
												countValidos=0;
												if (job.toUpperCase() == validJob.toUpperCase())
													countValidos ++;
											   
												if (countValidos>0)
												{
													valido = true
													break;
												}
											}
											
											if (countValidos==0)
											   {
												   stageInvalido = job;
													valido = false
													break; 
											   }
										}
										
										//devuelve el primer parametro stage no valido
										if (!valido)
											println "Stage no valido ---> " + stageInvalido + " ( ver parametro stage numero " + nuneroParametro +")"
										
										
										
										if (valido)
										{
											println "Ejecucion :" + herramienta
											println "Stage :" + stage
										}
										
										return valido;
								}

							  
							 
							 
							 
							 //
						   }
					}
								
				}
				}
				

			}
		}

return this;