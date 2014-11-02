package pl.mwaleria.jdbchelper;

import java.sql.Connection;

/**
 * Created by Marcin on 2014-11-02.
 */
public class SequenceGetter {

    protected Connection connection;

    public SequenceGetter(Connection connection) {
        this.connection = connection;
    }

}
