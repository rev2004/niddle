package Objects;

/**
 *
 * @author Rahul Behera
 */
public interface ProcessController {
    public abstract Process getProcess();
    public abstract void setProcess(Process process);
    public abstract void cancelConversion();
    public abstract void resumeConversion();
    public abstract void pauseConversion();
}
