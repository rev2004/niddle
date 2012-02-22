package convertit;



import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

/**
 * A class designed to use the native os's file chooser
 * and handle all the Input Output use
 * @author Rahul Behera
 */
public class IO {

    /**
     * Opens a File using FileDialog
     * @param Title The title to post in the top of the window
     * @return File chosen by user
     */
    public static File OpenFile(String Title) {
        FileDialog fd = new FileDialog(new Frame(), Title, FileDialog.LOAD);
        fd.setLocation(50, 50);
        fd.setVisible(true);
        return new File(fd.getDirectory(), fd.getFile());
    }

    /**
     * Select a Directory using JFileChooser (Cannot figure out how to do this in AWT, I hate JFileChooser)
     *
     * @param Title The title to post in the the top of window
     * @return Directory chosen by user, or NULL in case user cancels
     */
    @SuppressWarnings("static-access")
    public static File ChooseFolder(String Title) {
        JFileChooser fc = new JFileChooser();
        String nativeLNF = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(nativeLNF);
        } catch (Exception e) {
        }

        fc.setDialogTitle(Title);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnval = fc.showOpenDialog(null);
        if (returnval == fc.APPROVE_OPTION) {
            return fc.getSelectedFile();
        }
        return null;


    }

    /**
     * Saves a File using FileDialog
     * @param Title The title to post in the top of the window
     * @return a copy of the file saved
     */
    public static File SaveFile(String Title) {
        FileDialog fd = new FileDialog(new Frame(), Title, FileDialog.SAVE);
        fd.setLocation(50, 50);
        fd.setVisible(true);
        return new File(fd.getDirectory(), fd.getFile());
    }

    /**
     * This is used to save FFmpeg_old's location to a file so that the user wont be asked everytime to locate FFmpeg_old
     */
    public static void saveFFmpegLocation() {
        String file = System.getProperty("user.home") + System.getProperty("file.separator") + ".Convertit" + System.getProperty("file.separator") + "ffmpeg.txt";
        File f = new File(file);
        File dir = new File( System.getProperty("user.home") + System.getProperty("file.separator") + ".Convertit");
        try {
            dir.mkdir();
            f.createNewFile();
            write2file(FFmpeg_old.FFMPEG.getAbsolutePath(), f);
        } catch (IOException e) {
            //new PopUpTextBox("Error", "Cannot write to " + file + "\nI/O Exception with file: " + f.getAbsolutePath() + "\n" + e.getLocalizedMessage());
        }
    }

    /**
     * Simple method to write to a file
     * @param write The string to put in the file
     * @param f Where to write
     * @throws IOException Incase there is trouble writing.
     */
    public static void write2file(String write, File f) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write(write);
        bw.close();
    }


    /**
     * Simple method to write a file
     * @param f File to be read
     * @param lineSeperator how to seperate each line
     * @return A string containing the values of File f
     */
    public static String readFile(File f, String lineSeperator){
        StringBuilder sb;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            sb = new StringBuilder();
            for(String line = br.readLine(); line != null; line =  br.readLine()){
                sb.append(line + lineSeperator);
            }
        } catch (Exception e) {
            return null;
        }
        return sb.toString();


    }

    /**
     * Returns FFmpeg_old's Location if it is valid.
     * Will return null if it cannot find it
     * @return File pointing to ffmpeg
     */
    public static File getFFmpegLocation() {
        String file = System.getProperty("user.home") + System.getProperty("file.separator") + ".Convertit" + System.getProperty("file.separator") + "ffmpeg.txt";
        try {
            String output = readFile(new File(file), "");
            return new File(output);
        } catch (Exception e) {
            return null;
        }

    }
}
