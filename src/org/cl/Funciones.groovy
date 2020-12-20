// Funciones Groovy

package org.cl

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
