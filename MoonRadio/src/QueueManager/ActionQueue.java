/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QueueManager;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author Frankie(Qingyang) Liu (qingyang.liu@oracle.com) Oracle Inc.
 */
public class ActionQueue {
    
    private final TreeMap<Long,Action> actionQueue;
    
    public ActionQueue(){
        actionQueue = new TreeMap<>();
    }
    
    /**
     * Add a single Action
     * @param action
     */
    public void addAction(Action action){
        actionQueue.put(action.getTime(), action);
    }
    
    /**
     * Add a list of Actions
     * @param actions
     */
    public void addActions(List<Action> actions){
        for (Action action : actions) {
            this.addAction(action);
        }
    }
    
    /**
     * Returns true if any job is scheduled to run.
     * Get current time in seconds(not miliseconds)
     * Compare current time against first job in action Queue,
     * if current time >= first job's key, then return true
     * else return false;
     * @return 
     */
    public boolean hasAction(){
        //Get current time
        Date now = new Date();
        long currentTime = now.getTime() / 1000;
        //Get first job's time stamp
        long firstJob = actionQueue.firstKey();
        
        //Compare the values
        return currentTime >= firstJob;
    }
    
    /**
     * Run the commands in Action
     * Receive a new Action and add it to the queue
     */
    public void executeAction(){
        //Get current time
        Date now = new Date();
        long currentTime = now.getTime() / 1000;
        
        while(true){
            long firstJob = actionQueue.firstKey();
            if(firstJob > currentTime) break;
            
            //Execute this job
            Action currentJob = actionQueue.get(firstJob);
            Action newAction = currentJob.execute();
            
            //Delete the executed job
            actionQueue.remove(firstJob);
            actionQueue.put(newAction.getTime(), newAction);
        }
        //End.
    }
}
