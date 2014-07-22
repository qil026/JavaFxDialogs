/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SingletonHelpers;

/**
 *
 * @author Frankie(Qingyang) Liu (qingyang.liu@oracle.com) Oracle Inc.
 * 
 * Singleton Class
 */

public class Configuration {
    /**
     * Global Constants
     */
    //Root directory for the radio app (with the final forward slash)
    public final static String root_dir = "/Users/frankie/Workspace/WebDev/RadioHack/";
    
    /**
     * Singleton Instance
     */
    private static Configuration instance = null;
    
    
    //Private Configurations:
    
    /**
     * Sina file downloading server root address
     * Without the final forward slash!!
     */
    private String sina_server_root;
    
    
    
    //Preventing class instantiation
    private Configuration(){
        
    }
    
    public static Configuration getInstance(){
        if(instance == null){
            instance = new Configuration();
        }
        return instance;
    }

    public String getSina_server_root() {
        return sina_server_root;
    }

    /**
     * Guarantees the sina_server_root string will not include the last
     * forward slash.
     * @param sina_server_root 
     */
    public void setSina_server_root(String sina_server_root) {
        int size = sina_server_root.length();
        if(sina_server_root.charAt(size-1) == '/'){
            this.sina_server_root = sina_server_root.substring(0,size-1);
        }else{
            this.sina_server_root = sina_server_root;
        }
    }
    
    
    
}
