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

def get_branch_type(String branch_name) {

    def dev_pattern = ".*develop"
    def release_pattern = ".*release*"
    def feature_pattern = ".*feature*"
    def hotfix_pattern = ".*hotfix**"
    def master_pattern = ".*master"
    if (branch_name =~ dev_pattern) {
        return "dev"
    } else if (branch_name =~ release_pattern) {
        return "release"
    } else if (branch_name =~ master_pattern) {
        return "master"
    } else if (branch_name =~ feature_pattern) {
        return "feature"
    } else if (branch_name =~ hotfix_pattern) {
        return "hotfix"
    } else {
        return null;
    }
} 

def get_integration_type(String branch_type) {
    if (branch_type == "dev") {
        return "CI"
    } else if (branch_type == "release") {
        return "CD"
    } else if (branch_type == "master") {
        return "CD"
    } else {
        return null;
    }
}
