/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.io.File;
import javax.swing.JProgressBar;

import Objects.Constants.ConversionType;

/**
 *
 * @author Rahul Behera
 */
public class Graphic {

    private String bitrate;
    private String codec;
    private String resolution;
    private float FPS;
    private File f;
    private String time;
    private long conversion_status;
    private Profile profile;
    private ProcessController pc;
    private boolean partOfVideo;
    private JProgressBar jp;
    private boolean possible2convert;
    final private ConversionType conversionType = ConversionType.GRAPHIC;


    /**
     * Constructs a Graphic class which can hold all the information provided
     * by FFmpeg. It accepts information directly from FFmpeg<br>
     * Example of a PNG file
     * <pre>
     * Input #0, image2, from '<strong>C:\Users\Rahul Behera\Desktop\applogo.png</strong>':
     *   Duration: <strong>00:00:00.04</strong>, start: 0.000000, bitrate: <strong>N/A</strong>
     *     Stream #0.0: Video: <strong>png</strong>, rgb24, <strong>245x245</strong>, <strong>25</strong> tbr, 25 tbn, 25 tbc
     * </pre>
     * Example of Video file as input
     * <pre>
     * Input #0, mpeg, from '<strong>E:\Recorded Videos\Rahul's Birthday ' 1.mpg</strong>':
     *   Duration: <strong>00:41:31.94</strong>, start: 0.186522, bitrate: 6250 kb/s
     *     Stream #0.0[0x1e0]: Video: <strong>mpeg2video</strong>, yuv420p, <strong>720x480</strong> [PAR 8:9 DAR 4:3], <strong>8500 kb/s</strong>, <strong>29.97 tbr</strong>, 90k tbn, 59.94 tbc
     *     Stream #0.1[0x1c0]: Audio: mp2, 48000 Hz, 2 channels, s16, 224 kb/s
     * </pre>
     * @param input
     */
    public Graphic(String[] input) {
        try {
            for (String line : input) {
                if (line.startsWith("Input")) {
                    f = new File(line.substring(line.indexOf("from '") + 6, line.lastIndexOf("'")));
                } else if (line.startsWith("  Duration: ")) {
                    time = line.substring(line.indexOf("Duration: ") + 10, line.indexOf(","));
                } else if (line.contains("Video:")) {
                    int pos = line.indexOf("Video:") + 7;
                    codec = line.substring(pos, line.indexOf(",", pos));
                    pos = line.indexOf(", ", pos);
                    pos = line.indexOf(", ", pos);
                    resolution = line.substring(pos, line.indexOf(", ", pos));
                    if (line.contains("kb/s")) {
                        pos = line.lastIndexOf(", ", line.indexOf("kb/s")) + 2;
                        bitrate = line.substring(pos, line.indexOf("kb/s") + 4);
                    }
                    if (line.contains("tbr")) {
                        pos = line.lastIndexOf(", ", line.indexOf("tbr")) + 2;
                        bitrate = line.substring(pos, line.indexOf("tbr") + 3);
                    }
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            //Valid = false;
        }
    }

    public double getProgress() {
        return conversion_status / total();
    }

    @Override
	public File getFile() {
        return f;
    }

    public String getExtension() {
        String file = f.getName();
        return file.substring(file.lastIndexOf("."));
    }

    @Override
	public long current() {
        return conversion_status;
    }

    @Override
	public long total() {
        return getTime(time);
    }

    @Override
	public boolean isDone() {
        return total() == getProgress();
    }
    
    public String getInfo(){
        return f.getName() + "\n" + codec + "\n" +
                time + "\n" + resolution + "\n" + FPS + "\n" + bitrate;
    }

    @Override
	public void setProfile(Profile profile){
        this.profile = profile;
    }

    @Override
	public Profile getProfile() {
        return profile;
    }

    @Override
	public void setCurrent(long rofl) {
        if(jp != null)
            jp.setValue((int)rofl);
        conversion_status = rofl;
    }

    @Override
	public ProcessController getProcessController() {
        return pc;
    }

    @Override
	public void setProcessController(ProcessController pc) {
        this.pc = pc;
    }

    @Override
    public String toString(){
        
        return f.getName();
        
    }

    public void setPartOfVideo(boolean partOfVideo){
        this.partOfVideo = partOfVideo;
    }

    public boolean getPartOfVideo(){
        return partOfVideo;
    }

    @Override
	public JProgressBar getProgressBar() {
        jp = new JProgressBar(0, (int)total());

        return jp;
    }

    @Override
	public boolean possible2convert() {
        return possible2convert;
    }

    @Override
	public void setPossible2Convert(boolean possible2convert) {
        this.possible2convert = possible2convert;
    }

    @Override
	public ConversionType getConversionType() {
        return conversionType;
    }

	@Override
	public double getSize() {
		// TODO Auto-generated method stub
		return 0;
	}
    
}
