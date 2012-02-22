package convertit;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Objects.*;

/**
 * This class is designed to be a centralized place to call FFmpeg_old.
 * This is used to be able to simply locate the FFmpeg_old executable and pass parameters
 *
 * @author Rahul Behera
 */
public class FFmpeg_old implements Runnable {

    /**
     * Easy way to pass the location of FFmpeg_old to other classes
     */
    public static File FFMPEG;
    private Process p;
    private ProcessBuilder pb;

    /**
     * Returns if the FFmpeg_old is valid by calling the executable and checking if it
     * prints FFmpeg_old in the first line
     */
    public static boolean valid;

    /**
     * Constructs an FFmpeg_old object which will try and automatically locate
     * FFmpeg_old, and if that fails it will prompt the user to locate FFmpeg_old
     */
    public FFmpeg_old() {
        File f = IO.getFFmpegLocation();
        if (f != null) {
            setFFmpeg(f);
        } else {
            setFFmpeg();
        }
        //FFMPEG_VERSION = FFmpeg_String("-version");
    }

    /**
     * Constructs an FFmpeg_old object
     * @param F File which points to FFmpeg_old
     */
    public FFmpeg_old(File F) {
        FFMPEG = F;
        //FFMPEG_VERSION = FFmpeg_String("-version");
        IO.saveFFmpegLocation();
    }

    /**
     * Start an instance of FFmpeg_old and pass the parameter
     * @param args the arguments to be passed to ffmpeg. FFmpeg_old is executed with the arguments passed
     * @return An instance of the BufferedReader so you can directly get the output
     */
    public BufferedReader FFmpeg(String[] args) {
        String[] final_args = new String[args.length + 1];
        final_args[0] = FFMPEG.getAbsolutePath();
        System.arraycopy(args, 0, args, 1, args.length + 1);
        pb = new ProcessBuilder(final_args);
        Thread t = new Thread(this);
        t.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        return br;
    }

    /**
     * Start an instance of FFmpeg_old and pass the parameter args.
     * Used mainly when line by line control is not needed.
     * @param args arguments to pass to FFmpeg_old
     * @return String, which has output of FFmpeg_old
     * @throws IOException Not to be handeled by this method.
     */
    public synchronized String[] FFmpeg_String(String[] args) throws IOException {
        String[] final_args = new String[args.length + 1];
        final_args[0] = FFMPEG.getAbsolutePath();
        System.arraycopy(args, 0, final_args, 1, args.length);
        pb = new ProcessBuilder(final_args);
        p = pb.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        ArrayList<String> list = new ArrayList<String>();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            list.add(line);

        }
        return list.toArray(new String[list.size()]);

    }

    public ConversionStatus FFmpeg_parse(File f) throws IOException{
        
        String[] final_args = {
            FFMPEG.getAbsolutePath(),
            "-i",
            f.getAbsolutePath()
        };
        pb = new ProcessBuilder(final_args);
        p = pb.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        ArrayList<String> list = new ArrayList<String>();
        boolean audio = false, graphic = false;
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            list.add(line);
            if (line.contains(" Audio:")) {
                audio = true;
            }
            if (line.contains(" Video:")) {
                graphic = true;
            }
        }
        String[] input = list.toArray(new String[list.size()]);
        /*if (audio && graphic) {
            return new Video(input);
        } else if (audio) {
            return new Audio(input);
        } else if (graphic) {
            return new Graphic(input);
        }*/


        return null;

    }

    /**
     * Start an instance of FFmpeg_old and pass a parameter.
     * Used mainly when line by line control is not needed.
     * @param args arguments to pass to FFmpeg_old
     * @return String, which has output of FFmpeg_old
     */
    public String FFmpeg_String(String arg) {
        String[] final_args = {
            FFMPEG.getAbsolutePath(),
            arg
        };
        pb = new ProcessBuilder(final_args);
        StringBuilder sb = new StringBuilder();
        try {
            p = pb.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            //new PopUpTextBox("Error", final_args.toString() + "\n" + e.getLocalizedMessage());

        }
        p.destroy();
        return sb.toString();
    }

    /**
     * Start an instance of FFmpeg_old and pass a parameter.
     * Used mainly when line by line control is not needed.
     * @param arg arguments to pass to FFmpeg_old
     * @param f File pointing to FFmpeg_old
     * @return String, which has output of FFmpeg_old
     */
    public String FFmpeg_String(String arg, File f) {
        String[] final_args = {
            f.getAbsolutePath(),
            arg
        };
        pb = new ProcessBuilder(final_args);
        try {
            p = pb.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        StringBuilder sb = new StringBuilder();
        try {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.destroy();
        return sb.toString();
    }

    /**
     * Set the FFmpeg_old executable
     * @param FF_file File object containing FFmpeg_old
     */
    public void setFFmpeg(File FF_file) {
        String output = FFmpeg_String("-version", FF_file);
        valid = output.startsWith("FFmpeg_old version");
        FFMPEG = FF_file;
        IO.saveFFmpegLocation();
    }

    /**
     * Starts up a FileDialog and allows you to choose an FFmpeg_old
     */
    public void setFFmpeg() {

        try {
            File temp = IO.OpenFile("Open FFmpeg_old");
            String output = FFmpeg_String("-version", temp);
            valid = output.startsWith("FFmpeg_old version");
            FFMPEG = temp;
            IO.saveFFmpegLocation();
        } catch (NullPointerException e) {
        }
    }

/*    *//**
     * Get the FFmpeg_old's Version
     * @return FFmpeg_old Version
     *//*
    public static String getVersion() {
        return FFMPEG_VERSION;
    }
*/
    /**
     * Used to execute a FFmpeg_old call
     */
    @Override
	public void run() {
        try {
            p = pb.start();
        } catch (IOException a) {
            a.printStackTrace();
        }
    }
}
