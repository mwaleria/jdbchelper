package pl.mwaleria.jdbchelper.dao;

import org.junit.Before;
import org.junit.Test;
import pl.mwaleria.jdbchelper.h2.AbstractDBTest;
import pl.mwaleria.jdbchelper.h2.DataSet;
import pl.mwaleria.jdbchelper.query.Criteria;
import pl.mwaleria.jdbchelper.query.CriteriaType;
import pl.mwaleria.jdbchelper.testdata.Person;
import pl.mwaleria.jdbchelper.testdata.PersonDao;

import java.util.List;

import static org.junit.Assert.*;

public class AbstractDaoTest extends AbstractDBTest {
    
    private PersonDao personDao;

    @Before
    public void init() {
        personDao = new PersonDao(this.connection);
    }

    @Test
    @DataSet("emptydataset.xml")
    public void testCreate() throws Exception {
        Person p = new Person();
        p.setId(1);
        p.setFirstName("John");
        p.setLastName("Doe");
        personDao.create(p);
    }

    @Test
    @DataSet("person1.xml")
    public void testFindByCriteriaNoCriteria() throws Exception {
        List<Person> persons = personDao.findByCriteria(null);
        assertEquals(1,persons.size());
        Person p = persons.get(0);
        assertEquals(1,p.getId());
        assertEquals("John",p.getFirstName());
        assertEquals("Doe",p.getLastName());
    }

    @Test
    @DataSet("person2.xml")
    public void testFindByCriteriaOneCriteria() throws Exception {
        Criteria c = new Criteria();
        c.setColumn("LAST_NAME");
        c.setCriteriaType(CriteriaType.EQUALS);
        c.setCondition("Doe");
        Criteria[] criterias = {c};
        List<Person> persons = personDao.findByCriteria(criterias);
        assertEquals(3,persons.size());
    }

    @Test
    @DataSet("person2.xml")
    public void testFindByCriteriaManyCriterias() throws Exception  {
        Criteria[] criterias = {new Criteria("FIRST_NAME", CriteriaType.EQUALS, "John"), new Criteria("LAST_NAME", CriteriaType.EQUALS, "Doe")};
        List<Person> persons = personDao.findByCriteria(criterias);
        assertEquals(1,persons.size());
    }

    @Test
    public void testParseRecord() throws Exception {

    }

    @Test
    public void testPrepareStatement() throws Exception {

    }
}