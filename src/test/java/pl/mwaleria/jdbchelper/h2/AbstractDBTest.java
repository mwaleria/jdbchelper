package pl.mwaleria.jdbchelper.h2;

import org.dbunit.DatabaseUnitException;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Marcin on 2014-11-02.
 */
public abstract class AbstractDBTest {

    @Rule
    public DataSetWatcher dataSetWatcher = new DataSetWatcher();

    protected Connection connection;

    private final static String JDBC_TEST_URL ="jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private final static String H2_DRIVER_CLASS = "org.h2.Driver";
    private final static String H2_USER = "sa";
    private final static String H2_PASSWORD = "";

    private final static String SCHEMA_SQL = "src/test/resources/schema.sql";


    @Before
    public void setUp() throws Exception {

        if(dataSetWatcher.getDataSet() != null ) {

            IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("src\\test\\resources\\dataset\\"+this.getClass().getSimpleName()+"\\"+ dataSetWatcher.getDataSetValue()));
            IDatabaseTester databaseTester = new JdbcDatabaseTester(H2_DRIVER_CLASS, JDBC_TEST_URL,H2_USER,H2_PASSWORD);
            connection = databaseTester.getConnection().getConnection();
            databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
            databaseTester.setDataSet(dataSet);
            databaseTester.onSetup();
        }



    }

    @BeforeClass
    public static void createSchema() throws Exception {
        RunScript.execute(JDBC_TEST_URL,
                H2_USER, H2_PASSWORD, SCHEMA_SQL, StandardCharsets.UTF_8, false);
    }


}
