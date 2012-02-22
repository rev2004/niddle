package Objects;

/**
 *
 * @author Rahul Behera
 */
//public abstract class Multimedia_old implements ConversionStatus  {
public abstract class Multimedia_old {

    /**
     * Get the size of the file at the moment.
     * Returns in a MB form
     * @return size of file in megabytes
     */
	public abstract double getSize();
    
	
    /**
     * Converts a long value such as  into a HH:MM:SS.MS format
     * @param time The length of the clip
     * @return Time in a HH:MM:SS.MS format
     */
    public String getTime(long time) {
        //01:45:24.20
        String milliseconds = "" + (int) time % 1000;
        if (milliseconds.length() < 3) {
            milliseconds = "0" + milliseconds;
        }
        String seconds = "" + (int) ((time / 1000) % 60);
        if (seconds.length() < 2) {
            seconds = "0" + seconds;
        }
        String minutes = "" + (int) ((time / (1000 * 60)) % 60);
        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }
        String hours = "" + (int) ((time / (1000 * 60 * 60)) % 24);
        return hours + ":" + minutes + ":" + seconds + "." + milliseconds;
    }

    /**
     * Converts the HH:MM:SS.MS value into a millsecond value
     * @param time HH:MM:SS.MS
     * @return MS value of the time
     */
    public long getTime(String time) {
        long milliseconds = Long.parseLong(time.substring(time.indexOf(".") + 1));
        long seconds = Long.parseLong(time.substring(time.lastIndexOf(":") + 1, time.indexOf(".")));
        long minutes = Long.parseLong(time.substring(time.indexOf(":") + 1, time.lastIndexOf(":")));
        long hours = Long.parseLong(time.substring(0, time.indexOf(":")));

        return milliseconds + seconds * 1000 + minutes * 60000 + hours * 3600000;

    }

/*    private boolean converting = false;
    @Override
	public void setConverting(boolean converting){
        this.converting = converting;
    }
    @Override
	public boolean isConverting(){
        return converting;
    }*/
}
