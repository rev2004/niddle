package Communicate;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Objects.Constants.ConversionType;
import Objects.Profile;

public class MySQL implements Runnable {

    private String url;
    private String db;
    private String driver;
    private String user;
    private String pass;
    private Connection con;
    private ArrayList<Profile> list;

    /**
     * Constructs a MySQL object
     */
    public MySQL() {
        new Thread(this).start();
    }

    /**
     * Returns the Profiles obtained from the profile repo
     * @return ArrayList from search result
     */
    public ArrayList<Profile> getProfile() {
        return list;
    }


    /**
     * Gets all the tables from the database
     * @return Tables from database
     */
    public ArrayList<String> getTables() {

        try {
        	
            DatabaseMetaData dbm = con.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbm.getTables(null, null, "%", types);
            ArrayList<String> tables = new ArrayList<String>();
            
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
            tables.trimToSize();
            return tables;
        } catch (SQLException s) {
            System.out.println("No any table in the database");
            return null;
        }

    }

    /**
     * Searches the database for anything from the relating to the search field.
     * @param search The item to be searched in the database
     * @return ArrayList of Profiles which match the search result
     */
    public synchronized ArrayList<Profile> searchDB(String search) {
        ArrayList<Profile> profiles = new ArrayList<Profile>();
        try {
            Statement st = con.createStatement();
            search = search.trim();
            String Search = "SELECT * FROM video WHERE Name LIKE '%" + search + "%' OR Search_Keywords LIKE '%" + search + "%' OR Output LIKE '%" + search + "%'";

            ResultSet res = st.executeQuery(Search);

            while (res.next()) {

                profiles.add(new Profile(res.getString("Name"), res.getDate("Date"),
                        res.getString("Comments"), res.getString("Search_Keywords"), res.getInt("Max_threads"),
                        res.getInt("Max_Horizontal"), res.getInt("Max_Vertical"),
                        res.getInt("HQ_video_bitrate"), res.getInt("HQ_audio_bitrate"),
                        res.getString("Code").split("\\s"),
                        res.getString("Output"),
                        res.getInt("Count"), ConversionType.VIDEO));
            }
            
            Search = "SELECT * FROM audio WHERE Name LIKE '%" + search + "%' OR Search_Keywords LIKE '%" + search + "%' OR Output LIKE '%" + search + "%'";
            res = st.executeQuery(Search);

            while (res.next()) {
                profiles.add(new Profile(res.getString("Name"), res.getDate("Date"),
                        res.getString("Comments"), res.getString("Search_Keywords"), res.getInt("Max_threads"),
                        res.getString("Code").split("\\s"),
                        res.getString("Output"),
                        res.getInt("Count"), ConversionType.AUDIO));
            }
            Search = "SELECT * FROM graphic WHERE Name LIKE '%" + search + "%' OR Search_Keywords LIKE '%" + search + "%' OR Output LIKE '%" + search + "%'";
            res = st.executeQuery(Search);

            while (res.next()) {

                profiles.add(new Profile(res.getString("Name"), res.getDate("Date"),
                        res.getString("Comments"), res.getString("Search_Keywords"), res.getInt("Max_threads"),
                        res.getString("Code").split("\\s"),
                        res.getString("Output"),
                        res.getInt("Count"), ConversionType.GRAPHIC));
            }



        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL code does not execute.");
        }

        list = profiles;
        return profiles;
    }

    /**
     * Simple destructor to close out the connections
     */
    @Override
    protected void finalize() {
        new Thread(new ConnectionsCloseThread(con)).start();
    }

    /**
     * Starts the connection up in a thread
     */
    @Override
    public void run() {
        url = "jdbc:mysql://profilerepo.origamisoftware.net:3306/";
        db = "video_profiles_database";
        driver = "com.mysql.jdbc.Driver";
        user = "video_profile";
        pass = "convert";
        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + db, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
