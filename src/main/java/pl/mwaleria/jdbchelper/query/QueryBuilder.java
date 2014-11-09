package pl.mwaleria.jdbchelper.query;

import pl.mwaleria.jdbchelper.types.Column;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Marcin on 2014-11-02.
 */
public class QueryBuilder {

    private final static String DELETE_FROM = "DELETE FROM ";
    private final static String INSERT_INTO = "INSERT INTO ";
    private final static String VALUES = "VALUES ";
    private final static String SELECT = "SELECT ";
    private final static String FROM = "FROM ";
    private final static String EMPTY_STRING = "";
    private final static String WHERE = "WHERE ";
    private final static String AND = "AND ";
    private final static String SPACE = " ";
    private final static String COMA = ", ";
    private final static String QUESTION_MARK = "? ";
    private final static String QUESTION_MARK_WITH_COMA = "?, ";
    private final static String ORDER_BY = "ORDER BY ";
    private final static String DESC = "DESC";
    private final static String LEFT_PARENTHESIS = "( ";
    private final static String RIGHT_PARENTHESIS = ") ";

    private String tableName;

    private Map<String,Column> columns;

    public QueryBuilder(String tableName, Map<String,Column> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }


    public String buildDeleteQuery(Criteria[] criterias) {
        if(criterias == null || criterias.length == 0 ) {
            throw new IllegalArgumentException("Criterias have to be non null and  no  empty array for delete method");
        }
        StringBuilder query = new StringBuilder();
        query.append(DELETE_FROM);
        query.append(tableName);
        query.append(SPACE);
        query.append(buildWhereClause(criterias));
        return query.toString();
    }

    public String buildSelectQuery(Criteria[] criterias) {
        StringBuffer query = new StringBuffer();
        query.append(SELECT);
        addColumnsToQuery(query);
        query.append(FROM);
        query.append(tableName);
        query.append(SPACE);
        query.append(buildWhereClause(criterias));
        return query.toString();
    }

    public String buildSelectQuery(Criteria[] criterias, List<String> orderBy , boolean desc) {
        StringBuffer query = new StringBuffer();
        query.append(SELECT);
        addColumnsToQuery(query);
        query.append(FROM);
        query.append(tableName);
        query.append(SPACE);
        query.append(buildWhereClause(criterias));
        query.append(ORDER_BY);
        orderBy.get(0);
        for(int i=1; i< orderBy.size() ; i++) {
            query.append(COMA);
            query.append(orderBy.get(i));
        }
        if(desc == true) {
            query.append(DESC);
        }
        return query.toString();
    }

    public String buildInsertQuery() {
        StringBuffer query = new StringBuffer();
        query.append(INSERT_INTO);
        query.append(tableName);
        query.append(LEFT_PARENTHESIS);
        addColumnsToQuery(query);
        query.append(RIGHT_PARENTHESIS);
        query.append(VALUES);
        query.append(LEFT_PARENTHESIS);
        addQuestionMarksToQuery(query, columns.size());
        query.append(RIGHT_PARENTHESIS);
        return query.toString();
    }

    public String buildWhereClause (Criteria[] criterias) {
        StringBuffer sb = new StringBuffer();
        if(criterias == null || criterias.length==0) {
            return EMPTY_STRING;
        }
        sb.append(WHERE);
        int index = 0;
        for(Criteria criteria : criterias) {
            index++;
            sb.append(criteria.getColumn());
            sb.append(criteria.getCriteriaType().getSign());
            sb.append(QUESTION_MARK);
            if(index < criterias.length) {
                sb.append(AND);
            }
        }
        return sb.toString();
    }



    private void addQuestionMarksToQuery(StringBuffer sb, int size) {
        for(int i =0 ; i < size -1 ; i++ ) {
            sb.append(QUESTION_MARK_WITH_COMA);
        }
        sb.append(QUESTION_MARK);
    }

    private void addColumnsToQuery(StringBuffer sb) {
        Iterator<Column> columnNames = columns.values().iterator();
        while(columnNames.hasNext()) {
            sb.append(columnNames.next().name());
            if(columnNames.hasNext()) {
                sb.append(COMA);
            } else {
                sb.append(SPACE);
            }
        }
    }
}
