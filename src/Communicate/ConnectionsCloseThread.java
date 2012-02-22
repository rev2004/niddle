package Communicate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This is a relatevly simple class, with the idea to just
 * close connections properly without slowing down the program
 * @author Rahul Behera
 */
public class ConnectionsCloseThread implements Runnable{
    private Connection c;

    /**
     * Constructs a ConnectionsCloseThread
     * @param c Any connection that needs to be closed
     */
    public ConnectionsCloseThread(Connection c){
        this.c = c;
    }

    /**
     * A thread which will close the thread by basically calling close()
     */
    @Override
    public void run() {
        try {
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}
