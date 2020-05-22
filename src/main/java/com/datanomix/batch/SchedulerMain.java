package com.datanomix.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SchedulerMain {

    private static void Script(ProcessBuilder pb){ 
    	System.out.println("start Scheduler......");
    	 Process p;
		try {
			p = pb.start();
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    	String line = null;
	    	while ((line = reader.readLine()) != null)
	    	 {
	    	    System.out.println(line);
	    	 }
		} catch (IOException e) {
			e.printStackTrace();
		} 
    	System.out.println("end Scheduler......");

    }
    	
    
    public static void main(String[] args) throws InterruptedException {
        
    	System.out.println("passing arguments....");
    	String name_DB = args[0];
    	String period = args[1];
    	String time = args[2];
    	System.out.println("Base de données...."+name_DB);
    	System.out.println("La periode...."+period);
    	System.out.println("Minuterie...."+time);

    	TimeUnit timeUnit;
    	switch(time) {
    	case "S" : timeUnit = TimeUnit.SECONDS;
        break;
    	case "M" : timeUnit = TimeUnit.MINUTES;
		        break;
    	case "H" : timeUnit = TimeUnit.HOURS;
    			break;
    	case "J" : timeUnit = TimeUnit.DAYS;
    	        break;
    	default :    timeUnit = TimeUnit.MINUTES;  
    	        break;
    	    
    	}
    	String path = null;
    	switch(name_DB) {
    	case "Neo4j" :  path = "/home/hicham/Bureau/projects/DataNoMIX/datanomix-db/datanomix-db-rdbms2document/DocScript.sh";
        break;
    	case "MongoDB" :  path = "/home/hicham/Bureau/projects/DataNoMIX/datanomix-db/datanomix-db-rdbms2document/DocScript.sh";
		        break;
    
        default :  ;    	
                break;
    	    
    	}
      	 ProcessBuilder  pb = new ProcessBuilder(path);

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        Runnable task1 = () -> { Script(pb);};

        // init Delay = 5, repeat the task every 5 seconds
        ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task1,0, Integer.valueOf(period), timeUnit);

        while (true) {
               
           Thread.sleep(3000);
       
        }   
    }
}