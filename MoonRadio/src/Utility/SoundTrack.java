/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utility;

/**
 *
 * @author Frankie(Qingyang) Liu (qingyang.liu@oracle.com) Oracle Inc.
 */
public class SoundTrack {
    
    private String url;
    private int duration;
    private String timestamp_string;
    private long timestamp_long;
    private String localFileName;
    
    //Constructors -------------------------------------------------------------
    public SoundTrack(String url, int duration){
        this.url = url;
        this.duration = duration;
        generateFileData();
    }
    
    public SoundTrack(long timestamp, int duration, String fileName){
        this.timestamp_string = String.valueOf(timestamp);
        this.timestamp_long = timestamp;
        this.duration = duration;
        this.localFileName = fileName;
    }

    //Methods ------------------------------------------------------------------
    public boolean isEqual(SoundTrack other){
        return other.getUrl().equals(this.url);
    }
    
    private void generateFileData(){
        this.timestamp_string = url.substring(46,56);
        this.timestamp_long = Long.parseLong(timestamp_string);
        this.localFileName = url;
    }
    
    //Setters ------------------------------------------------------------------
    public void setUrl(String url) {
        this.url = url;
        generateFileData();
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    //Getters ------------------------------------------------------------------
    public String getUrl() {
        return url;
    }

    public int getDuration() {
        return duration;
    }

    public String getTimestampString() {
        return timestamp_string;
    }
    
    public long getTimestampLong(){
        return timestamp_long;
    }

    public String getLocalFileName() {
        return localFileName;
    }
}
