package ch.heigvd.res.impl;

import ch.heigvd.res.interfaces.IFileProcessor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
/**
 * This class is responsible for processing a file found during the file system
 * exploration. The class should open the file, read its content, transform it
 * on the fly and write it to a file with the same name and an additional .out
 * extension (in other words, when it processes a file named a.txt, it should
 * write the result in a file named a.txt.out).
 * 
 * This class should use the ScramblerFilterWriter and UppercaseFilterWriter
 * classes to do the actual transformation. It should concentrate on opening the
 * files, getting the related Reader and Writer instances and on reading the
 * file content in a loop.
 *
 * @author Olivier Liechti
 */
public class WeirdFileProcessor implements IFileProcessor {
	
	@Override
	public void processFile(File file) {
	}
	
}
