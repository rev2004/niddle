package Objects;

/**
 *
 * @author Rahul Behera
 */
import java.sql.Date;

import Objects.Constants.ConversionType;
public class Profile {

    /**
     * This class is used to help keep all the assigned profile information retrieved from
     * the video profiles database so information can quickly be retrieved
     */

    private String Name;
    private Date date= null;
    private String Comments;
    private String Search_Keywords;
    private int Max_threads;
    private int Max_Horizontal;
    private int Max_Vertical;
    private int HQ_video_bitrate;
    private int HQ_audio_bitrate;
    private String[] Code;
    private String Output;
    private int count;
    private ConversionType conversionType;

    /**
     * Construct a Profile Object definining everything
     * @param Name Name of Profile
     * @param date Date added
     * @param Comments Comments on the Profile
     * @param Max_threads Maximum supported threads
     * @param Title Title of Profile
     * @param Max_Horizontal Max y pixels
     * @param Max_Vertical Max x pixels
     * @param Max_Pixels Max total pixels
     * @param HQ_video_bitrate video bitrate
     * @param HQ_audio_bitrate audio bitrate
     * @param Precode code ran before the "-i"
     * @param Code code ran after the input is given
     * @param Postcode code ran after output file is posted
     * @param Output output extension
     * @param count how many times profile has been used.
     */
    public Profile(String Name, Date date, String Comments, String Search_Keywords,
            int Max_threads, int Max_Horizontal,
            int Max_Vertical, int HQ_video_bitrate,
            int HQ_audio_bitrate, String[] Code,
            String Output, int count, ConversionType conversionType){
        this.Name = Name;
        this.date = date;
        this.Comments = Comments;
        this.Max_threads = Max_threads;
        this.Search_Keywords = Search_Keywords;
        this.Max_Horizontal = Max_Horizontal;
        this.Max_Vertical = Max_Vertical;
        this.HQ_video_bitrate = HQ_video_bitrate;
        this.HQ_audio_bitrate = HQ_audio_bitrate;
        this.Code = Code;
        this.Output = Output;
        this.count = count;
        this.conversionType = conversionType;
    }
    /**
     * Construct profile with few details
     * @param Name Name of Profile
     * @param Comments Comments on the Profile
     * @param Output output extension
     */
    public Profile(String Name, String Comments, String Output){
        this.Name = Name;
        this.Comments = Comments;
        this.Output = Output;
    }

    public Profile(String Name, Date date, String Comments, String Search_Keywords,
            int Max_threads,String[] Code,
            String Output, int count, ConversionType conversionType){
        this.Name = Name;
        this.date = date;
        this.Comments = Comments;
        this.Max_threads = Max_threads;
        this.Search_Keywords = Search_Keywords;
        this.Code = Code;
        this.Output = Output;
        this.count = count;
        this.conversionType = conversionType;
    }

    /**
     * Gives you Name of Profile
     * @return name of profile
     */
    @Override
    public String toString(){
        return Name;
    }
    /**
     * gives you the name of profile
     * @return name of profile
     */
    public String getName(){
        return Name;
    }

    /**
     * Returns the Class the profile is designed to convert FROM
     * 
     * @return
     */
    public ConversionType getConverstionType(){
        return conversionType;
    }

    /**
     * Get date profile was created
     * @return date profile was created
     */
    public Date getDate(){
        return date;
    }
    /**
     * Get the Comments posted in profile
     * @return Comments
     */
    public String getComments(){
        return Comments;
    }
    /**
     * Returns number of max runnable threads
     * @return Max Threads supported by profile
     */
    public int getMaxThreads(){
        return Max_threads;
    }
    /**
     * Return the search keywords of the profile
     * @return Search_Keywords of profile
     */
    public String getSearch_Keywords(){
        return Search_Keywords;
    }
    /**
     * Get the Max horizontal pixels of profile
     * @return Max y pixels
     */
    public int getMaxHorizontal(){
        return Max_Horizontal;
    }
    /**
     * Get teh Max Vertical pixels of profile
     * @return Max X pixels
     */
    public int getMaxVertical(){
        return Max_Vertical;
    }

    /**
     * Get the recommended HQ video bitrate
     * @return Bitrate
     */
    public int getHQVideoBitrate(){
        return HQ_video_bitrate;
    }
    /**
     * Get the recommended HQ Audio bitrate
     * @return Audio Bitrate
     */
    public int getHQAudioBitrate(){
        return HQ_audio_bitrate;
    }

    /**
     * Get all the parameters that alter the video/audio clip
     * @return Code
     */
    public String[] getCode(){
        return Code;
    }

    /**
     * Get the output extension.
     * @return Output extension, such as ".mpg"
     */
    public String getOutput(){
        return Output;
    }
    /**
     * Get the use count of this profile
     * @return Count
     */
    public int getCount(){
        return count;
    }
}
