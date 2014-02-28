/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.heigvd.res.samples.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Olivier Liechti
 */
public class CensorshipWriterTest {
	
	public CensorshipWriterTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void wrappingAWriterWithACensorshipWriterShouldFilterCharacters() {
		String source = "This string contains a number of u characters. Two of them.";
		String destinationShouldBe = source.replaceAll("u", "");
		
		ByteArrayInputStream bis = new ByteArrayInputStream(source.getBytes());
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		int c;
		while ( (c = bis.read()) != -1 ) {
			bos.write(c);
		}
		
		String destination = bos.toString();
		assertEquals(source, destination);
		assertEquals(destination, destinationShouldBe);
		
	}
	
}
