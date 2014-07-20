/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QueueManager;

import RadioManagers.RadioManager;

/**
 *
 * @author Frankie(Qingyang) Liu (qingyang.liu@oracle.com) Oracle Inc.
 */
public class Action {
    //Action Parameter, JobType
    public final static int JOB_DOWNLOAD_TRACKS = 100;
    public final static int JOB_DELETE_TRACKS = 200;
    public final static int JOB_UPDATE_LIVE = 300;
    public final static int JOB_UPDATE_SYNC = 400;  
    public final static int JOB_UPDATE_CID = 500;
    
    
    private final RadioManager manager;
    private final int jobType;
    private final long time;
    
    public Action(RadioManager manager, int jobType, long time){
        this.manager = manager;
        this.jobType = jobType;
        this.time = time;
    }
    
    public Action execute(){
        Action newAction = null;
        switch(jobType){
            case Action.JOB_DOWNLOAD_TRACKS:
                newAction = this.manager.downloadSoundTracks();
                break;
            case Action.JOB_DELETE_TRACKS:
                newAction = this.manager.deleteSoundTracks();
                break;
            case Action.JOB_UPDATE_LIVE:
                newAction = this.manager.updateLivePlayList();
                break;
            case Action.JOB_UPDATE_SYNC:
                newAction = this.manager.updateSyncPlayList();
                break;
            case Action.JOB_UPDATE_CID:
                newAction = this.manager.updateCidKey();
                break;
        }
        return newAction;
    }
    
    public long getTime(){
        return time;
    }
}
