/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RadioManagers;

import SingletonHelpers.DatabaseHelper;
import SingletonHelpers.IOHandler;
import QueueManager.Action;
import Utility.SoundTrack;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frankie(Qingyang) Liu (qingyang.liu@oracle.com) Oracle Inc.
 */
public class RadioManager implements RadioRule{
    
    //Radio Station Properties:
    private String stationName;
    private String stationDir;
    
    //m3u8_address depends on CID, so updating CID should also modify m3u8_address
    private int CID;
    private String m3u8_address;
    
    private BufferedReader reader;
    //Stores the timestamp of last downloaded sound track
    private long lastSoundTrackTimeStamp;
    
    
    //Utility Variables
    private final IOHandler ioHandler;
    private final DatabaseHelper databaseHelper;
    
    public RadioManager(IOHandler ioHandler, DatabaseHelper databaseHelper){
        //Set initial value, indicating not set
        this.CID = -1;
        this.m3u8_address = "";
        this.ioHandler = ioHandler;
        this.databaseHelper = databaseHelper;
    }
    

    @Override
    public Action downloadSoundTracks() {
        //Check CID
        if(this.CID < 0) {
            System.out.println("CID Key not updated!!");
            return null;
        }
        
        try {
            //Create URL
            URL m3u8_url = new URL(m3u8_address);
            reader = new BufferedReader(new InputStreamReader(m3u8_url.openStream()));
            
            String line;
            ArrayList<SoundTrack> tracks = new ArrayList<>();
            while((line=reader.readLine()) != null){
                //Check if this line contains a segment
                if(line.substring(1, 7).equals("EXTINF")){
                    //Dig out the duration
                    String str = "";
                    for(int i = 8; i < line.length(); i++){
                        if(line.charAt(i) == ',') break;
                        str += line.charAt(i);
                    }
                    int duration = Integer.parseInt(str);
                    //Read next line and get the url
                    line = reader.readLine();
                    String url = line;
                    tracks.add(new SoundTrack(url,duration));
                }
            }
            
            //Remove already downloaded sound tracks from the list.
            //Also set the lastSoundTrackTimeStamp
            long newEndTime = this.lastSoundTrackTimeStamp;
            while(!tracks.isEmpty()){
                SoundTrack track = tracks.get(0);
                long timestamp = track.getTimestampLong();
                if(timestamp <= this.lastSoundTrackTimeStamp){
                    tracks.remove(0);
                    if(timestamp > newEndTime){
                        newEndTime = timestamp;
                    }
                }
                else{
                    break;
                }
            }
            this.lastSoundTrackTimeStamp = newEndTime;
            
            
            
            //Send the list of sound tracks to IOHandler to download them
            IOHandler.getInstance().downloadSoundTracks(tracks);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RadioManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RadioManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                if(reader != null)
                    reader.close();
            } catch (IOException ex) {
                Logger.getLogger(RadioManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;      
    }

    @Override
    public Action deleteSoundTracks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Action updateLivePlayList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Action updateSyncPlayList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Action> getInitialActions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Action updateCidKey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
