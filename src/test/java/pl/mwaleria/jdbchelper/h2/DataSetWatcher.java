package pl.mwaleria.jdbchelper.h2;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Created by Marcin on 2014-11-02.
 */
public class DataSetWatcher extends TestWatcher {
    @Override
    protected void starting(Description description) {
        super.starting(description);
    }
}
