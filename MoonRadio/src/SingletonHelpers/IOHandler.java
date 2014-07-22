/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SingletonHelpers;

import Utility.SoundTrack;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;

/**
 *
 * @author Frankie(Qingyang) Liu (qingyang.liu@oracle.com) Oracle Inc.
 */
public class IOHandler {
    //Singleton Pattern Parameter
    private static IOHandler instance = null;
    
    
    //Static parameter:
    public static final int QUEUE_SIZE = 10;
    
    private final ArrayList<SoundTrack> download_queue;
    private Task downloadTask;
    
    //No Instantiation by users allowed
    private IOHandler(){
       download_queue = new ArrayList<>(); 
       initialize();
    }
    
    public static IOHandler getInstance(){
        if(instance == null){
            instance = new IOHandler();
        }
        return instance;
    }
    
    public void downloadSoundTracks(List<SoundTrack> tracks){
        download_queue.addAll(tracks);
        if(download_queue.size() > IOHandler.QUEUE_SIZE){
            processDownloadQueue();
        }
    }
    
    private void processDownloadQueue(){
        Thread th = new Thread(downloadTask);
        th.start();
    }
    
    
    private void initialize(){
        downloadTask = new Task<Void>() {
            @Override
            protected Void call() {
                //Check to ensure download_queue is not empty
                if(download_queue.isEmpty()) return null;
                
                //Proceed to create a file with a list of addresses
                Writer writer = null;
                String file_dir = Configuration.root_dir + "Download_list.txt";
                try{
                    writer = new BufferedWriter(
                                new OutputStreamWriter(
                                    new FileOutputStream(
                                            file_dir), "utf-8"));
                    String server_root = Configuration.getInstance().getSina_server_root();
                    //Traverse through the download list and write each download address to file
                    String content = "";
                    for(int i = 0; i < download_queue.size(); i++){
                        SoundTrack track = download_queue.get(i);
                        String address = server_root + track.getLocalFileName();
                        content += address;
                        if(i < (download_queue.size() - 1)){
                            content += "\n";
                        }
                    }
                    //Write to file
                    writer.write(content);
                    
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(IOHandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(IOHandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(IOHandler.class.getName()).log(Level.SEVERE, null, ex);
                } finally{
                    try {
                        if(writer != null)
                            writer.close();
                    } catch (IOException ex) {
                        Logger.getLogger(IOHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                //Run wget command to download the listed files
                String command = "wget";
                command += " -P " + Configuration.root_dir + "SoundTracks/";
                command += " -i list";
                try {
                    Process p = Runtime.getRuntime().exec(command);
                    p.waitFor();
                } catch (IOException ex) {
                    Logger.getLogger(IOHandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(IOHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return null;
            }
        };
    }
}
