package pl.mwaleria.jdbchelper.types;

import java.sql.*;

/**
 * Mapping table from <a href="http://db.apache.org/ojb/docu/guides/jdbc-types.html#mapping-table">Mapping table</a>
 *
 * Created by Marcin on 2014-11-01.
 */
public enum JdbcDataType {
    CHAR (String.class),
    VARCHAR (String.class),
    LONGVARCHAR ( String.class),
    NUMERIC ( 	java.math.BigDecimal.class ),
    DECIMAL( 	java.math.BigDecimal.class ),
    BIT ( Boolean.class),
    BOOLEAN(Boolean.class),
    TINYINT(Byte.class),
    SMALLINT(Short.class),
    INTEGER(Integer.class),
    BIGINT(Long.class),
    REAL(Double.class),
    DOUBLE(Double.class),
    //BINARY
    //VARBINARY
    //LONGVARBINARY
    DATE(Date.class),
    TIME(Time.class),
    TIMESTAMP(Timestamp.class),
    CLOB(Clob.class),
    BLOB(Blob.class),
    FOREIGN_KEY(Object.class);

    private final Class<?> clazz;

    JdbcDataType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getJavaClass() {
        return clazz;
    }
}
