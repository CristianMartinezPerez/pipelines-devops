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
								gradle.call()
							}
							else 
							{
								def ejecucion = load 'maven.groovy'
								maven.call()
							}
							 
						   }
					}
								
				}
				}
				

			}
		}
  
}

return this;