package test;

import java.io.File;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:example-config.xml")
public class GenericTest {


	private IDatabaseTester databaseTester;
	

	@Before
	
	public void setUp() throws Exception {
		// add set up environment needed.
		databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver","jdbc:hsqldb:file:target/db/entity_hsqldb","sa","");

		// initialize your dataset here
		IDataSet dataSet = getDataSet();

		databaseTester.setDataSet(dataSet);
		// will call default setUpOperation
		databaseTester.onSetup();
	}

	@Test
	public void exampleTest() throws Exception {
        assertTrue(true);
	}

	@After
	public void tearDown() throws Exception {
		// set free the resources after execution.
		
		// will call default tearDownOperation
        databaseTester.onTearDown();
	}


	/**
	 * loading data in db
	 * 
	 * @return
	 * @throws Exception
	 */
	private IDataSet getDataSet() throws Exception {
		// get insert data
		return new FlatXmlDataSet(new File("target/partial.xml"));
	}

}
