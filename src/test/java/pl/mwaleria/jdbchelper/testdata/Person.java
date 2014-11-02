package pl.mwaleria.jdbchelper.testdata;

import pl.mwaleria.jdbchelper.types.Column;
import pl.mwaleria.jdbchelper.types.Entity;
import pl.mwaleria.jdbchelper.types.PrimaryKey;
import pl.mwaleria.jdbchelper.types.JdbcDataType;

/**
 * Created by Marcin on 2014-11-01.
 */
@Entity(tableName = "Persons")
public class Person {

    @PrimaryKey
    @Column(name = "ID", dataType = JdbcDataType.INTEGER)
    private int id;

    @Column(name = "FIRST_NAME", dataType = JdbcDataType.VARCHAR)
    private String firstName;

    @Column(name = "LAST_NAME", dataType = JdbcDataType.VARCHAR)
    private String lastName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
