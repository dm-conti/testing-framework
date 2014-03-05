package it.tmp.easymock.junit;

import static org.easymock.EasyMock.*;
import it.tmp.easymock.junit.ExampleService;
import junit.framework.TestCase;

public class ExampleServiceTests extends TestCase {

	private ExampleService service = new ExampleService();
	
	public void testReadOnce() throws Exception {
		assertEquals("Hello world!", service.getMessage());
	}
	
	/**
	 * Quando usiamo JUnit Framework questo tipo di test funziona
	 * perche' il metodo asserEquals(primitivo, primitivo) al suo interno
	 * crea dal primitivo il warapper corrispondente new Wrapper(primitivo)
	 * e successivamente invoca il metodo equal.
	 * 
	 * es. metodo assertEquals(char, char)
	 * 
	 * static public void assertEquals(String message, char expected, char actual) {
	 * 		assertEquals(message, new Character(expected), new Character(actual));
	 * }
	 * 
	 * 
	 * 
	 * Notiamo che, nonostante vengano creati due oggetti Warapper uno per
	 * espected ed uno per actual, il fatto che vengano usate le classi Wrapper
	 * garatisce che l'invoazione del metodo equals su due oggetti differenti
	 * restituisca true.
	 * 
	 * es. metodo equals del Wrapper Char
	 * 
	 * public boolean equals(Object obj) {
	 * 		if (obj instanceof Short) {
	 * 			return value == ((Short)obj).shortValue();
	 * 		}
	 * 		return false;
	 * }
	 * **/
	
	public void testChar() throws Exception {
		char c = "c".charAt(0);
		assertEquals(c, c);
	}
	
	public void testByteArray() throws Exception {
		byte[] bytes = "ciao".getBytes();
		assertEquals(bytes, bytes);
	}
	
	public void testCharWithEasyMock_verifyBevaviour(){
		String expectedString = "ccccc";
		char c = "c".charAt(0);
		
		IEntity entity = createMock(IEntity.class);
		expect(entity.charMultiply(c)).andReturn(expectedString);
//		entity.setCharValue(c);
//		expectLastCall();
		replay(entity);
		
		String actualString = entity.charMultiply(c);
		
		assertEquals(expectedString, actualString);
				
		verify(entity);
	}
	
	/**
	 * 
	 * **/
	interface IEntity{
		public char getCharValue();
		public byte[] getBytes();
		public void setCharValue(char c);
		public void setBytes(byte[] bytes);
		public String charMultiply(char c);
	}
	
//	interface IParentEntity{
//		public IEntity getEntity();
//	}
} 