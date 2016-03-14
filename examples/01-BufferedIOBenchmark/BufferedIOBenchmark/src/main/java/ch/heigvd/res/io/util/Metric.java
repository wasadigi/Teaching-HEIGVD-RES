package ch.heigvd.res.io.util;

/**
 *
 * @author Olivier Liechti
 */
public class Metric {
  
  
  
  private long duration;
  private String operationType;

  public Metric(long duration, String operationType) {
    this.duration = duration;
    this.operationType = operationType;
  }

  
  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public String getOperationType() {
    return operationType;
  }

  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }
  
  

}
