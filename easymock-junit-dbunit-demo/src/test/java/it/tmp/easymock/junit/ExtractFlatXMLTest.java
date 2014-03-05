package it.tmp.easymock.junit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.util.search.SearchException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ExtractFlatXMLTest {

	private Connection jdbcConnection;
	private IDatabaseConnection connection;

	@Before
	public void setUp() {
		// database connection
		try {
			jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", "password");
			connection = new DatabaseConnection(jdbcConnection);
		} catch (SQLException | DatabaseUnitException e) {
			e.printStackTrace();
		}
	}
	
	@Test @Ignore
	public void fullExportTest() {
		// full database export
		IDataSet fullDataSet;
		try {
			fullDataSet = connection.createDataSet();
			try {
				FlatXmlDataSet.write(fullDataSet, new FileOutputStream("employees-full.xml"));
			} catch (DataSetException | IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void partialExportTest() {
		// partial database export
		QueryDataSet partialDataSet = new QueryDataSet(connection);
		try {
			partialDataSet.addTable("employees","SELECT * FROM employees LIMIT 0,500");
		} catch (AmbiguousTableNameException e) {
			e.printStackTrace();
		}
		try {
			FlatXmlDataSet.write(partialDataSet, new FileOutputStream("employees.xml"));
		} catch (DataSetException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void dependetTablesExportTest() {
		// dependent tables database export: export table X and all tables that
		// have a PK which is a FK on X, in the right order for insertion
		IDataSet depDataset;
		try {
			String[] depTableNames = TablesDependencyHelper.getAllDependentTables(connection, "salaries");
			depDataset = connection.createDataSet(depTableNames);
			FlatXmlDataSet.write(depDataset, new FileOutputStream("salaries.xml"));
		} catch (SearchException | DataSetException | SQLException | IOException e) {
			e.printStackTrace();
		}
		
		
//		//Get target database connection
//		IDatabaseConnection dbConnection = DBUnitHelper.getTargetDBConnection();
// 
//		//Create an array with all the tables that have a dependency with the target table
//		String[] depTableNames = TablesDependencyHelper.getAllDependentTables(dbConnection, "Delivery" );
// 
//		//create dataset
//		IDataSet depDataset = dbConnection.createDataSet(depTableNames);
// 
//		//Write the file with the result
//		FlatXmlDataSet.write(depDataset, new FileOutputStream(DBUnitHelper.getOutputFolder() + "3_sampleDependency.xml"));
	}
}