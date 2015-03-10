package ch.heigvd.res.io.metrics;

import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 */
public class Metric {
  private String operation;
  private String strategy;
  private long blockSize;
  private long fileSizeInBytes;
  private long durationInMs;

  public Metric(String operation, String strategy, long blockSize, long fileSizeInBytes, long durationInMs) {
    this.operation = operation;
    this.strategy = strategy;
    this.blockSize = blockSize;
    this.fileSizeInBytes = fileSizeInBytes;
    this.durationInMs = durationInMs;
  }
  
  public static void writeCSVHeader(Writer writer) throws IOException {
    writer.write("operation");
    writer.write(',');
    writer.write("strategy");
    writer.write(',');
    writer.write("blockSize");
    writer.write(',');
    writer.write("fileSizeInBytes");
    writer.write(',');
    writer.write("durationInMs");
    writer.write('\n');    
  }
  
  public void toCSV(Writer writer) throws IOException {
    writer.write(operation);
    writer.write(',');
    writer.write(strategy);
    writer.write(',');
    writer.write(Long.toString(blockSize));
    writer.write(',');
    writer.write(Long.toString(fileSizeInBytes));
    writer.write(',');
    writer.write(Long.toString(durationInMs));
    writer.write('\n');
  }

}
