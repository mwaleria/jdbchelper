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
                    columns.put(field.getName(),column);
                }
            }
        }

    }


    public boolean create(T entity) throws NoSuchFieldException, IllegalAccessException, SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(tableName);
        sb.append(" ( ");
        addColumnsToQuery(sb);
        sb.append(" )");
        sb.append(" VALUES (");
        addQuestionMarksToQuery(sb);
        sb.append(")");
        PreparedStatement ps = preparedStatement(sb.toString(),entity);
        boolean status = ps.execute();
        connection.commit();
        return status;
    }

    public List<T> findByCriteria(Criteria[] criterias) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        ArrayList<T> results = new ArrayList<T>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        addColumnsToQuery(sb);
        sb.append("FROM ");
        sb.append(tableName);
        sb.append(" ");
        sb.append(QueryBuilder.buildWhereClause(criterias));
        PreparedStatement preparedStatement = prepareStatement(sb.toString(),criterias);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            results.add(this.parseRecord(resultSet));
        }
        return results;
    }

    private PreparedStatement prepareStatement(  String sql,Criteria[] criterias) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        if(criterias != null) {
            int index = 1;
            for (Criteria criteria : criterias) {
                Object condition = criteria.getCondition();
                if(condition instanceof  String) {
                    ps.setString(index, (String) condition);
                } else if(condition instanceof Integer) {
                    ps.setInt(index,(Integer) condition);
                } else {
                    ps.setString(index, (String) condition.toString());
                }
                index++;
            }
        }

        return ps;
    }

    private PreparedStatement preparedStatement(String sql, T entity) throws SQLException, NoSuchFieldException, IllegalAccessException {
        PreparedStatement ps = connection.prepareStatement(sql);
        Iterator<String> fields = columns.keySet().iterator();
        int index = 1;

        while(fields.hasNext())  {
            String fieldName = fields.next();
            Column column = columns.get(fieldName);
            Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);
            ps.setObject(index,f.get(entity));
            index++;
        }
        return ps;

    }

    protected T parseRecord(ResultSet resultSet) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException, NoSuchFieldException {
        T entity = (T) clazz.getConstructor().newInstance();
        for(String s : columns.keySet()) {
            Column column = columns.get(s);
            Field field = clazz.getDeclaredField(s);
            field.setAccessible(true);
            switch(column.dataType()) {
                case CHAR:
                    break;
                case VARCHAR:
                    field.set(entity,resultSet.getString(column.name()));
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
                    field.set(entity,resultSet.getInt(column.name()));
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



    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private void addColumnsToQuery(StringBuffer sb) {
        Iterator<Column> columnNames = columns.values().iterator();
        while(columnNames.hasNext()) {
            sb.append(columnNames.next().name());
            if(columnNames.hasNext()) {
                sb.append(", ");
            } else {
                sb.append(" ");
            }
        }
    }

    private void addQuestionMarksToQuery(StringBuffer sb) {
        int size = columns.size();
        for(int i =0 ; i < size -1 ; i++ ) {
            sb.append("?, ");
        }
        sb.append("? ");
    }
}
