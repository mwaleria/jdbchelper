package pl.mwaleria.jdbchelper.query;

/**
 * Created by Marcin on 2014-11-02.
 */
public enum CriteriaType {
    EQUALS("="),
    LESS("<"),
    LEQ("<="),
    GREATER(">"),
    GEQ(">="),
    LIKE(" LIKE "),
    IN(" IN ");

    private final String sign;


    CriteriaType(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return this.sign;
    }
}
