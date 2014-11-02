package pl.mwaleria.jdbchelper.query;

/**
 * Created by Marcin on 2014-11-02.
 */
public class QueryBuilder {

    public static String buildWhereClause (Criteria[] criterias) {
        StringBuffer sb = new StringBuffer();
        if(criterias == null || criterias.length==0) {
            return "";
        }
        sb.append("WHERE ");
        for(Criteria criteria : criterias) {
            sb.append(criteria.getColumn());
            sb.append(criteria.getCriteriaType().getSign());
            sb.append(":");
            sb.append(criteria.getColumn());
        }
        return sb.toString();
    }
}
