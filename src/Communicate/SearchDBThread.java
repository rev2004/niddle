package Communicate;

import java.util.ArrayList;
import javax.swing.JList;

import Objects.Profile;



/**
 * This class is designed to make searching through the profile repositiory simple
 * and easy to control the threads.
 * @author Rahul Behera
 */
public class SearchDBThread implements Runnable{

    private String search;
    private MySQL ms;
    private JList list;
    private  ArrayList<Profile> lol;

    public SearchDBThread(MySQL ms){
        this.ms = ms;
    }
    public void setSearch(String search, JList list){
        this.search = search;
        this.list = list;
    }
    public ArrayList<Profile> getProfiles(){
        
        return lol;
    }
    @Override
	public void run() {
        
        lol = ms.searchDB(search);
        list.setListData(lol.toArray());
        

    }
    
}
