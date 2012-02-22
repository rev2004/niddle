package Objects;

import java.io.File;
import javax.swing.JProgressBar;

import Objects.Constants.ConversionType;


/**
 *
 * @author Rahul Behera
 */
public class Video{
    private Audio audio_channel;
    private Graphic graphic_channel;
    private Profile profile;
    private long conversion_status;
    private ProcessController pc;
    private JProgressBar jp = null;
    private boolean possible2convert;
    final private ConversionType conversionType = ConversionType.VIDEO;

    public Video(String[] input){
        audio_channel = new Audio(input);
        audio_channel.setPartOfVideo(true);
        graphic_channel = new Graphic(input);
        graphic_channel.setPartOfVideo(true);
    }

    public Audio getAudio(){
        return audio_channel;
    }
    public Graphic getGraphic(){
        return graphic_channel;
    }

    @Override
	public File getFile() {
        return getGraphic().getFile();
    }

    public String getExtension() {
        return getGraphic().getExtension();
    }
    @Override
	public void setProfile(Profile profile){
        this.profile = profile;
    }

    @Override
	public boolean isDone() {
        return current() == total();
    }

    @Override
	public long current() {
        return conversion_status;
    }

    @Override
	public long total() {
        return conversion_status;
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
        return graphic_channel.toString();
    }

    @Override
	public JProgressBar getProgressBar() {
        if(jp == null)
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


}