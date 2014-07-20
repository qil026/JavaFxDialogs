/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RadioManagers;

import QueueManager.Action;
import java.util.ArrayList;

/**
 *
 * @author Frankie(Qingyang) Liu (qingyang.liu@oracle.com) Oracle Inc.
 */
public interface RadioRule {
    
    /**
     * Downloads Sound Tracks from the link stored within the RadioManager class.
     * 1. Read the play list.
     * 2. Extract sound track addresses that needs to be downloaded
     * 3. Add to IOHandler a list of files to download.
     * 4. Update the queue of events that needs to be executed in the future.
     * @return 
     */
    public Action downloadSoundTracks();
    
    /**
     * Deletes useless sound tracks from disk folder.
     * After the time zone (default 15 hrs) expires, delete useless sound tracks
     * 1. Connect to database to retrieve a list of files to delete.
     * 2. Send these delete commands to IOHandler to delete them later.
     * 3. Reset Timer to 60s later.
     * @return 
     */
    public Action deleteSoundTracks();
    
    /**
     * Update live play lists.
     * 1. Read in the current live play list entry, delete the 1st entry and 
     * 2. Add a new entry at the end.
     * 3. Send file content to IOHandler to over write the existing play list.
     * @return 
     */
    public Action updateLivePlayList();
    
    /**
     * Update sync play lists.
     * 1. Read in the current sync play list entry, delete the 1st entry and 
     * 2. Add a new entry at the end.
     * 3. Send file content to IOHandler to over write the existing play list.
     * @return 
     */
    public Action updateSyncPlayList();
    
    /**
     * Update CID key.
     * 1. Execute the c++ program to generate a new cid key for this radio station.
     * 2. Read the file to extract the new cid key.
     * @return 
     */
    public Action updateCidKey();
    
    
    /**
     * Return a list of initial Jobs.
     * Return precisely 5 Actions, one for each of the above.
     * Note: must give updateCidKey the current timestamp
     *       give all other jobs a 5 second delay!
     * @return
     */
    public ArrayList<Action> getInitialActions();

}
