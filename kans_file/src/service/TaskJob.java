package service;

import org.springframework.stereotype.Service;

@Service("taskJob")
public class TaskJob {
	
	public void job() {
		System.out.println("1任务!!!");
		System.out.println("2任务");
		
    }

}
