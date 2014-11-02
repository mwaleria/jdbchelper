package pl.mwaleria.jdbchelper.testdata;

import pl.mwaleria.jdbchelper.dao.AbstractDao;
import pl.mwaleria.jdbchelper.testdata.Person;

import java.sql.Connection;

/**
 * Created by Marcin on 2014-11-02.
 */
public class PersonDao extends AbstractDao<Person> {
    public PersonDao( Connection connection) {
        super(Person.class, connection);
    }
}
