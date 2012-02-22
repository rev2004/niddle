package convertit;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JProgressBar;

import Objects.ConversionStatus;

//import cPopUpTextBox;

/**
 * A class designed to scan folders and add to the GUI
 * @author Rahul Behera
 */
public class ScanFolderThread extends Thread {

    private JList FileList;
    private JProgressBar progress;
    private JLabel status;
    private File folder;
    private boolean isSelected;
    private Vector<ConversionStatus> convertables;
    private FFmpeg_old ff;


    /**
     * Constructs a ScanFolderThread
     * @param FileList The JList to directly connect to
     * @param progress The JProgressBar to update
     * @param status The JLabel status control
     * @param folder The folder being scanned
     * @param isSelected Should it scan through subfolders
     * @param load The ArrayList containing all the Video objects
     */
    public ScanFolderThread(JList FileList, JProgressBar progress, JLabel status, File folder, boolean isSelected, Vector<ConversionStatus> convertables, FFmpeg_old ff) {
        this.FileList = FileList;
        this.progress = progress;
        this.status = status;
        this.folder = folder;
        this.isSelected = isSelected;
        this.ff = ff;
        this.convertables = convertables;

    }

    /**
     * Get the Video Files in a nice arraylist
     * @return All the Video Objects picked up by FFmpeg_old
     */
    public Vector<ConversionStatus> getLoad() {
        return convertables;
    }

    /**
     * Starts a thread to scan through folders.
     */
    @Override
    public void run() {
        try {
            progress.setValue(0);
            progress.setVisible(true);
            progress.setStringPainted(true);
            if (folder.isDirectory()) {
                progress.setMaximum(getNumofFiles(folder));
                scanfolder(folder);
            } else {
                progress.setMaximum(1);
                scanfolder(folder);
            }
        } catch (NullPointerException e) {
        }

    }

    /**
     * Returns the number of files in a folder
     * @param f The folder being searched
     * @return An integer with the count of files
     */
    public int getNumofFiles(File f) {
        int temp = 0;
        File[] files = filterOutInvalid(f);
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                temp++;
            } else if (files[i].isDirectory() && isSelected) {
                temp += getNumofFiles(files[i]);
            }
        }
        return temp;
    }

    public ConversionStatus addFile(File f) {
        status.setText("Scanning file: " + f.getName());
        ConversionStatus cs = null;
        try {
            cs = ff.FFmpeg_parse(f);
        } catch (IOException e) {
           /** new PopUpTextBox("Problem!!", "There is a problem with reading from"
                    + " your computer. Please make sure there is no other" +
                    " program using the file!");**/
        }
        progress.setValue(progress.getValue() + 1);
        return cs;
    }

    /**
     * Scans the folders and automatically adds to JList
     * @param f Folder being searched
     */
    public synchronized void scanfolder(File f) {
        ConversionStatus cs = null;
        if(f.isFile()){
            cs = addFile(f);
            if(cs != null){
                convertables.add(cs);

            }
        }else{
            File[] files = filterOutInvalid(f);
            cs = null;
            for (File file : files) {
                if (file.isFile()) {
                    cs = addFile(file);
                    if (cs != null) {
                        convertables.add(cs);
                        FileList.setListData(convertables);
                         
                    }
                } else if (file.isDirectory() && isSelected) {
                    status.setText("Entering Directory: " + file.getName());
                    scanfolder(file);
                }
            }
        }
    }

    /**
     * Filters out invalid filetypes
     * @param original the array of files to be checked
     * @return an array of files which are valid
     */
    public File[] filterOutInvalid(File original) {
        File[] files = original.listFiles();
        ArrayList<File> filtered = new ArrayList<File>();
        for (File test : files) {
            if (test.isFile()) {
                if (check_validity(test)) {
                    filtered.add(test);
                }
            } else if (test.isDirectory()) {
                filtered.add(test);
            }

        }
        return filtered.toArray(new File[filtered.size()]);
    }

    /**
     *  Checks to make sure that the file is not an extension of a compressed format
     * @param input_video_file file to be tested
     * @return if file is valid
     */
    public static boolean check_validity(File input_video_file) {
        boolean test = true;
        if (input_video_file.getName().endsWith("0")) {
            test = (false);
        } else if (input_video_file.getName().endsWith("1")) {
            test = (false);
        } else if (input_video_file.getName().endsWith("2")) {
            test = (false);
        } else if (input_video_file.getName().endsWith("3")) {
            if (!input_video_file.getName().endsWith("mp3")) {
                test = (false);
            }
        } else if (input_video_file.getName().endsWith("4")) {
            if (!input_video_file.getName().endsWith("mp4")) {
                test = (false);
            }
        } else if (input_video_file.getName().endsWith("5")) {
            test = (false);
        } else if (input_video_file.getName().endsWith("6")) {
            test = (false);
        } else if (input_video_file.getName().endsWith("7")) {
            test = (false);
        } else if (input_video_file.getName().endsWith("8")) {
            test = (false);
        } else if (input_video_file.getName().endsWith("9")) {
            test = (false);
        } else if (input_video_file.getName().endsWith("rar")) {
            test = (false);
        } else if (input_video_file.getName().endsWith("nfo")) {
            test = (false);
        } else if (input_video_file.getName().endsWith("log")) {
            test = (false);
        }
        return test;
    }
}
