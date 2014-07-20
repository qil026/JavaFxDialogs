/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moonradio;

import QueueManager.Action;
import QueueManager.ActionQueue;
import RadioManagers.RadioManager;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frankie(Qingyang) Liu (qingyang.liu@oracle.com) Oracle Inc.
 */
public class MoonRadio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        //Create radio station and action queue
        ArrayList<RadioManager> radioStations = new ArrayList();
        ActionQueue actionQueue = new ActionQueue();
        
        //Create instances of radio manager then set its parameters
        /**
         * 
         * 
         * ...
         * 
         * 
         */
        
        
        //Initialize Action queue stage
        for (RadioManager manager : radioStations) {
            ArrayList<Action> actions = manager.getInitialActions();
            actionQueue.addActions(actions);
        }
        
        
        //Execute actions
        while(true){
            if(actionQueue.hasAction()){
                actionQueue.executeAction();
            }
            else{
                try {
                    sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MoonRadio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    
    
}
