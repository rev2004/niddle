package convertit;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import Objects.*;
public class FFmpeg_Process extends Thread implements ProcessController {

    private String[] arguments;
    private ConversionStatus V;
    private boolean a_r;
    private boolean suspended = false;
    private Process process;
    private BufferedReader br;

    /*
    public FFmpeg_Process(String[] arguments){

    this.arguments = arguments;
    }
     */
    public FFmpeg_Process(String saveAt, ConversionStatus V, String volume) {
        V.setProcessController(this);
        Runtime runtime = Runtime.getRuntime();
        int proc = runtime.availableProcessors();
        Profile profile = V.getProfile();
        boolean maxthreads = proc < profile.getMaxThreads();
        ArrayList<String> args = new ArrayList<String>();
        args.add(FFmpeg_old.FFMPEG.getAbsolutePath());
        if (profile.getMaxThreads() > 1) {
            args.add("-threads");
            if (maxthreads) {
                args.add(proc + "");
            } else {
                args.add(profile.getMaxThreads() + "");
            }
        }
        args.add("-i");
        args.add(V.getFile().getAbsolutePath());
        for (String lol : profile.getCode()) {
            if (lol.equalsIgnoreCase("<audio>")) {
/*                String Audio_Bitrate = ((Video)V).getAudio().getBitrate();
                args.add(Audio_Bitrate.substring(0, Audio_Bitrate.indexOf("kb/s") - 1) + "k");
*/
            } else {
                args.add(lol);
            }
        }
        String name = System.getProperty("file.separator") + V.getFile().getName();
        name = name.substring(0, name.indexOf("."));
        name += "_Converted" + V.getProfile().getOutput();
        args.add("-y");
        args.add(saveAt + name);
        arguments = args.toArray(new String[args.size()]);
        this.V = V;
        //this.qg = qg;


    }

    @Override
	public void pauseConversion() {
        suspended = true;

    }

    public void updateCO(String line, ConversionStatus v) {
        if (line.contains("Lsize=")) {
            //"frame= 5772 fps=223 q=0.0 Lsize=  102271kB time=192.45 bitrate=4353.4kbits/s    "
            v.setCurrent(v.total());
        } else {
            int dot = line.indexOf(".", line.indexOf(".")+ 1);
            line = line.substring(0, dot) + line.substring(dot + 1);
            line = line.substring(line.indexOf("time=") + 5, line.indexOf(" bitrate"));
             v.setCurrent(Long.parseLong(line));

        }
        
    }

    public void extractConversion(String line) {
        int dot = line.indexOf(".", line.indexOf(".")+ 1);
        line = line.substring(0, dot) + line.substring(dot + 1);
        V.setCurrent(Long.parseLong(line.substring(line.indexOf("time=") + 5, line.indexOf(" bitrate=")).trim()));
        a_r = true;
    }

    @Override
	public void resumeConversion() {
        suspended = false;
        notify();
    }

    @Override
    public void run() {
        this.setPriority(MIN_PRIORITY);
        //qg.add(V);
        V.setConverting(true);
        ProcessBuilder processb = new ProcessBuilder(arguments);
        try {
            process = processb.start();
            br = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                synchronized (this) {
                    while (suspended) {
                        wait();
                    }
                }
                if (line.startsWith("frame=")) {
                    if (!a_r) {
                        extractConversion(line);
                        //qg.setVisible(true);
                    } else {
                        updateCO(line, V);
                    }
                    //qg.FireDataChange();
                } else {
                    //qg.addtoLog(line + "\n");
                }
            }
            V.setConverting(false);

        } catch (IOException e) {
            //new PopUpTextBox("Error", arguments.toString() + "\n" + e.getLocalizedMessage());
        } catch (InterruptedException e) {
            //new PopUpTextBox("Error", arguments.toString() + "\n" + "Thread interrupted?");
        }
    }

    @Override
	public void cancelConversion() {

        try {

            br.close();
            process.destroy();
            V.setConverting(false);
        } catch (IOException e) {
            //process.destroy();
        }
    }

    @Override
	public Process getProcess() {
        return process;
    }

    @Override
	public void setProcess(Process process) {
        this.process = process;
    }
}
