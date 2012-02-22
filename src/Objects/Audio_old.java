package Objects;

import java.io.File;
import javax.swing.JProgressBar;

import Objects.Constants.ConversionType;

/**
 *
 * @author Rahul Behera
 */
public class Audio_old extends Multimedia {

    private String codec;
    private String bitrate = "320 kb/s";
    private String frequency;
    private String channels;
    private String title;
    private int track;
    private String Genre;
    private int year;
    private String comments;
    private String album;
    private String author;
    private File f;
    private String time;
    private long conversion_status;
    private Profile profile;
    private ProcessController pc;
    private boolean partOfVideo;
    private JProgressBar jp;
    private boolean possible2convert = true;
    final private ConversionType conversiontype = ConversionType.AUDIO;



    /**
     * Constructs an Audio_old class and extracts the information from the audio
     * track and assigns all the information
     * Currently only works from the output of FFmpeg<br>
     * Example:
     * <pre>
     * Input #0, <strong>mp3</strong>, from '<strong>D:\copy\311\311\01 Down.mp3</strong>':
     *   Duration: <strong>00:02:53.36</strong>, start: 0.000000, bitrate: <strong>160 kb/s</strong>
     *     Stream #0.0: Audio_old: <strong>mp3</strong>, <strong>44100 Hz</strong>, <strong>2 channels</strong>, s16, <strong>160 kb/s</strong>
     *   Metadata
     *     title           : <strong>Down</strong>
     *     track           : <strong>1</strong>
     *     year            : <strong>1995</strong>
     *     comment         : <strong>0</strong>
     *     album           : <strong>311</strong>
     *     author          : <strong>311</strong></pre>
     *
     * Also accepts and will extract audio feed from a Video<br>
     * Example:
     * <pre>
     * Input #0, mpeg, from '<strong>E:\Recorded Videos\Rahul's Birthday ' 1.mpg</strong>':
     *   Duration: <strong>00:41:31.94</strong>, start: 0.186522, bitrate: 6250 kb/s
     *     Stream #0.0[0x1e0]: Video: mpeg2video, yuv420p, 720x480 [PAR 8:9 DAR 4:3], 8500 kb/s, 29.97 tbr, 90k tbn, 59.94 tbc
     *     Stream #0.1[0x1c0]: Audio_old: <strong>mp2</strong>, <strong>48000 Hz</strong>, <strong>2 channels</strong>, s16, <strong>224 kb/s</strong></pre>
     * @param input FFmpeg's output after analyzing the clip
     */
    public Audio_old(String[] input) {
        for (String line : input) {
            if (line.startsWith("Input")) {
                f = new File(line.substring(line.indexOf("from '") + 6, line.lastIndexOf("'")));
            }else if(line.startsWith("  Duration: ")){
                time = line.substring(line.indexOf("Duration: ") + 10, line.indexOf(","));
            }else if(line.contains("Audio_old:")){
                int pos = line.indexOf("Audio_old:") + 7;
                codec = line.substring(pos, line.indexOf(",", pos));
                pos = line.indexOf(", ", pos);
                frequency = line.substring(pos, line.indexOf(", ", pos));
                pos = line.indexOf(", ", pos);
                channels = line.substring(pos, line.indexOf(", ", pos));
                if(line.contains("kb/s")){
                    pos = line.lastIndexOf(", ", line.lastIndexOf("kb/s")) + 3;
                    bitrate = line.substring(pos - 1, line.indexOf("kb/s")) + "kb/s";
                }
            }else if(line.contains("title")){
                title = line.substring(line.indexOf(": ")+2);
            }else if(line.contains("track")){
                track = Integer.parseInt(line.substring(line.indexOf(": ")+2));
            }else if(line.contains("genre")){
                Genre = line.substring(line.indexOf(": ")+2);
            }else if(line.contains("year")){
                year = Integer.parseInt(line.substring(line.indexOf(": ")+2));
            }else if(line.contains("comment")){
                comments = line.substring(line.indexOf(": ")+2);
            }else if(line.contains("album")){
                album = line.substring(line.indexOf(": ")+2);
            }else if(line.contains("author")){
                 author = line.substring(line.indexOf(": ")+2);
            }
        }
    }


    public double getProgress() {
        return conversion_status / getTime(time);
    }

    @Override
	public File getFile() {
        return f;
    }

    @Override
	public boolean isDone() {
        return conversion_status == total();
    }

    public String getExtension() {
        String file = f.getName();
        return file.substring(file.lastIndexOf("."));
    }

    @Override
	public long current() {
        return conversion_status;
    }

    public String getGenre(){
        return Genre;
    }


    public String getInfo(){
        return f.getName() + "\n" + codec + "\n" + time + "\n" + bitrate +
                "\n" + author + "-" + album + "-" + title + "\n" +
                "Track:" + track + "\n" + year + "\n" + channels + "\n" +
                frequency + "\n" + comments;
    }

    @Override
	public long total() {
        return getTime(time);
    }

    @Override
	public Profile getProfile() {
        return profile;
    }

    @Override
	public ConversionType getConversionType(){
        return conversiontype;
    }

    @Override
	public void setProfile(Profile profile) {
        this.profile = profile;
    }
    public String getBitrate(){
        return bitrate;
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

    public void setPartOfVideo(boolean partOfVideo){
        this.partOfVideo = partOfVideo;
    }

    public boolean getPartOfVideo(){
        return partOfVideo;
    }

    @Override
    public String toString(){
        return f.getName();
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



}
