package Objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class FFmpeg implements Runnable{

	private Process p;
	private ProcessBuilder pb;
	private String[] args;
	private String input_file;
	private String[] output;
	
	public FFmpeg(File F, String input, String[] arguments) {
		input_file = input;
		args = new String[arguments.length + 3];
		args[0] = F.getPath();
		args[1] = "-i";
		args[2] = input;
		System.arraycopy(arguments, 0, args, 3, arguments.length);
        
    }
    /**
     * Start an instance of FFmpeg and pass the parameter args.
     * Used mainly when line by line control is not needed.
     * @param args arguments to pass to FFmpeg
     * @return String, which has output of FFmpeg
     * @throws IOException Not to be handled by this method.
     */
    public String[] FFmpegArray(){
    	if(output == null || output.length < 1){
    		pb = new ProcessBuilder(args);
    		pb.redirectErrorStream(true);

    		try {
				p = pb.start();
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    		ArrayList<String> list = new ArrayList<String>();
	    		for (String line = br.readLine(); line != null; line = br.readLine()) {
	    			list.add(line);
	    		}
	            output = list.toArray(new String[list.size()]);
	    		
			} catch (Exception e) {
				System.out.println("FFMPEG FAILED!");
				e.printStackTrace();
			}     		
    	}
        return output;
    	
    }
    /*
    * Start an instance of FFmpeg and pass the parameter args.
    * Used mainly when line by line control is not needed.
    * @param args arguments to pass to FFmpeg
    * @return String, which has output of FFmpeg
    * @throws IOException Not to be handled by this method.
    */
   public synchronized String FFmpegString(){
       String[] list = FFmpegArray();
       StringBuilder sb = new StringBuilder();
       for(String i : list){
    	   sb.append(i + "\n");
       }
       return sb.toString();

   }    

    /**
     * Start an instance of FFmpeg_old and pass the parameter
     * @param args the arguments to be passed to ffmpeg. FFmpeg_old is executed with the arguments passed
     * @return An instance of the BufferedReader so you can directly get the output
     */
    public BufferedReader FFmpeg() {
        pb = new ProcessBuilder(args);
        Thread t = new Thread(this);
        t.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        return br;
    }
    
    /**
     * Used to execute a FFmpeg call
     */
    @Override
	public void run() {
        try {
            p = pb.start();
        } catch (IOException a) {
            a.printStackTrace();
        }
    }
 
	
	public String getInputFile(){
		return input_file;
	}
	
	
	
	
}
