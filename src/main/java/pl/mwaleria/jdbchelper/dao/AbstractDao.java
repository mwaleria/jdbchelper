package pl.mwaleria.jdbchelper.dao;

import pl.mwaleria.jdbchelper.query.Criteria;
import pl.mwaleria.jdbchelper.query.QueryBuilder;
import pl.mwaleria.jdbchelper.types.Column;
import pl.mwaleria.jdbchelper.types.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Marcin on 2014-11-02.
 */
public abstract class AbstractDao<T> {

    protected Class<?> clazz;

    protected Connection connection;

    protected String tableName;

    LinkedHashMap<String,Column> columns;

    public AbstractDao(Class<?> entityClass, Connection connection) {
        this.connection = connection;
        this.clazz = entityClass;
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        init();
    }

    private void init() {
        Entity e = clazz.getDeclaredAnnotation(Entity.class);
        if(e == null) {
            throw new IllegalArgumentException("Class have to have pl.mwaleria.jdbchelper.types.Entity annotations");
        }
        tableName = e.tableName();
        columns = new LinkedHashMap<String, Column>();
        Field[] fields = clazz.getDeclaredFields();
        if(fields!= null) {
            for(Field field : fields) {
                Column column = field.getDeclaredAnnotation(Column.class);
                if(column != null) {
                    columns.put(column.name(),column);
                }
            }
        }

    }


    public boolean create(T entity) {
        return false;
    }

    public List<T> findByCriteria(Criteria[] criterias) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        ArrayList<T> results = new ArrayList<T>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        Iterator<String> columnNames = columns.keySet().iterator();
        while(columnNames.hasNext()) {
            sb.append(columnNames.next());
            if(columnNames.hasNext()) {
                sb.append(", ");
            } else {
                sb.append(" ");
            }
        }
        sb.append("FROM ");
        sb.append(tableName);
        sb.append(" ");
        sb.append(QueryBuilder.buildWhereClause(criterias));
        PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
        this.prepareStatement();
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            results.add(this.parseRecord(resultSet));
        }
        return results;
    }

    protected T parseRecord(ResultSet resultSet) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException, NoSuchFieldException {
        T entity = (T) clazz.getConstructor().newInstance();
        for(String s : columns.keySet()) {
            Column column = columns.get(s);
            Field field = clazz.getDeclaredField(s);
            switch(column.dataType()) {
                case CHAR:
                    break;
                case VARCHAR:
                    field.set(s,resultSet.getString(column.name()));
                    break;
                case LONGVARCHAR:
                    break;
                case NUMERIC:
                    break;
                case DECIMAL:
                    break;
                case BIT:
                    break;
                case BOOLEAN:
                    break;
                case TINYINT:
                    break;
                case SMALLINT:
                    break;
                case INTEGER:
                    field.set(s,resultSet.getInt(column.name()));
                    break;
                case BIGINT:
                    break;
                case REAL:
                    break;
                case DOUBLE:
                    break;
                case DATE:
                    break;
                case TIME:
                    break;
                case TIMESTAMP:
                    break;
                case CLOB:
                    break;
                case BLOB:
                    break;
                case FOREIGN_KEY:
                    break;
            }
        }
        return entity;
    }

    protected void prepareStatement() {

    }
}
