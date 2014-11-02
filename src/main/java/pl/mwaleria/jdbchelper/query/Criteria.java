package pl.mwaleria.jdbchelper.query;

/**
 * Created by Marcin on 2014-11-02.
 */
public class Criteria {

    private String column;
    private CriteriaType criteriaType;
    private Object condition;

    public Criteria(String column, CriteriaType criteriaType, Object condition) {
        this.column = column;
        this.criteriaType = criteriaType;
        this.condition = condition;
    }
    public Criteria() {}

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    public void setCriteriaType(CriteriaType criteriaType) {
        this.criteriaType = criteriaType;
    }

    public Object getCondition() {
        return condition;
    }

    public void setCondition(Object condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Criteria{" + column + " " + criteriaType.getSign() + " " + condition.toString() + " }";
    }
}
