def call(){
  
		pipeline {
			agent any
			
			parameters { choice(name: 'herramienta', choices: ['gradle', 'maven'], description: '') }

			stages {
				stage('Pipeline') {
					steps {
						   /*echo "Choice: ${params.herramienta}"*/
						   script{				 
							 /*def ejecucion = (params.herramienta == 'gradle') ? load 'gradle.groovy' : load 'maven.groovy'
							 ejecucion.call()*/		
							 
							 env.HERRAMIENTA = params.herramienta 
							 
							if (params.herramienta == 'gradle') 
							{
								def ejecucion = load 'gradle.groovy'
								ejecucion.call()
							}
							else 
							{
								def ejecucion = load 'maven.groovy'
								ejecucion.call()
							}
							 
						   }
					}
								
				}
				}
				

			}
		}
  
}

return this;