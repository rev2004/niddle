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
public interface ConversionStatus{

    public ConversionType getConversionType();

    /**
     * Get the input file which will be converted
     * @return The file being read
     */
    public  File getFile();
    /**
     *
     * @return Is the conversion process done?
     */
    public  boolean isDone();
    /**
     * Return the current status of the conversion
     * @return
     */
    public  long current();
    /**
     * Returns a number where the conversion process is trying to acheive
     * @return a long number where you know when to stop
     */
    public  long total();
    /**
     * Returns the profile that is going to be used to convert
     * @return Profile
     */
    public  Profile getProfile();

    @Override
    public String toString();
    /**
     * Sets the Conversion to convert to this profile
     * @param profile
     */
    public  void setProfile(Profile profile);
    /**
     * Get the filesize of the new converted file in a megabyte form
     * @return filesize
     */
    public  double getSize();
    /**
     * Sets the current progress conversion
     * @param rofl
     */
    public  void setCurrent(long rofl);

    public  ProcessController getProcessController();
    public  void setProcessController(ProcessController pc);

    public  JProgressBar getProgressBar();

    public  boolean possible2convert();
    public  void setPossible2Convert(boolean possible2convert);
    public boolean isConverting();
    public void setConverting(boolean converting);


}
