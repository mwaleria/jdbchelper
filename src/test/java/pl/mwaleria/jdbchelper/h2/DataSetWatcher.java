package pl.mwaleria.jdbchelper.h2;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import javax.xml.crypto.Data;

/**
 * Created by Marcin on 2014-11-02.
 */
public class DataSetWatcher extends TestWatcher {


    private DataSet dataSet;

    @Override
    protected void starting(Description description) {
        dataSet = description.getAnnotation(DataSet.class);
    }

    public DataSet getDataSet() {
        return this.dataSet;
    }

    public String getDataSetValue()  {
        return dataSet!=null ? dataSet.value() : "";
    }
}
