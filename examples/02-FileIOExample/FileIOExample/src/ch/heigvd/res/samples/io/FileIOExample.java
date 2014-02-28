package ch.heigvd.res.samples.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class FileIOExample {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Generator generator = new Generator();
		Duplicator duplicator = new Duplicator();

		generator.generateTestFile("/tmp/tf200", 200);
		generator.generateTestFile("/tmp/tf511", 511);
		generator.generateTestFile("/tmp/tf512", 512);
		generator.generateTestFile("/tmp/tf513", 513);
		generator.generateTestFile("/tmp/tf5110", 5110);
		generator.generateTestFile("/tmp/tf5120", 5120);
		try {
			CountingFilterInputStream is = new CountingFilterInputStream(new BufferedInputStream(new FileInputStream("/tmp/tf5120")));
			SuperstitiousFilterOutputStream os = new SuperstitiousFilterOutputStream(new BufferedOutputStream(new FileOutputStream("/tmp/tf5120-copy")));
			duplicator.duplicate(is, os);
			is.close();
			os.close();
			System.out.println(is.getNumberOfBytesRead() + " bytes read in " + is.getNumberOfReadOperations() + " operations.");
			System.out.println(os.getNumberOfBytesWritten() + " bytes written, " + os.getNumberOfBytesSkipped()+ " bytes skipped, for a total of " + os.getNumberOfBytesEvaluated() + " bytes evaluated.");
		} catch (IOException ex) {
			Logger.getLogger(FileIOExample.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
