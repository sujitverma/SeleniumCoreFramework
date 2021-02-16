package igt.test.automation.util;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * This utility class is meant for data access operations, using Spring
 * Framework. Internally it uses {@code JdbcTemplate} class to perform all the
 * JDBC operations. This simplifies the use of JDBC and helps to avoid common
 * errors.
 * 
 *
 * @author
 *
 */
public final class DataBaseUtil {

    private DataBaseUtil() {

    }

    /**
     * This method is to execute the passed SQL query in the specified.
     * 
     * @param sqlQueryToExecute
     *            the SQL command to execute.
     * @param jdbcTemplate
     *            is {@link JdbcTemplate}
     * @return an integer.
     */
    public static int executeSQLQueryCount(final String sqlQueryToExecute,
            final JdbcTemplate jdbcTemplate) {
        Integer count = jdbcTemplate.queryForObject(sqlQueryToExecute,
                Integer.class);
        return count != null ? count.intValue() : 0;

    }

    /**
     * This method is to execute the passed SQL query in the specified.
     * 
     * @param sqlQueryToExecute
     *            the SQL command to execute.
     * @param jdbcTemplate
     *            is {@link JdbcTemplate}
     * @return List<Map<String, Object>> The results list (one entry for each
     *         row) ofMaps (one entry for each column using the column name as
     *         the key).
     */
    public static List<Map<String, Object>> executeSQLQueryAndReturnResults(
            final String sqlQueryToExecute, final JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForList(sqlQueryToExecute);
    }

    /**
     * A method for an INSERT/UPDATE/DELETE SQL query.
     * 
     * @param sqlQuery
     *            the SQL command. e.g. "insert into t_actor (first_name,
     *            last_name) values (?, ?)"
     * @param params
     *            - parameters list in the same order to replace ? with the
     *            parameter you pass in
     * @param jdbcTemplate
     *            is {@link JdbcTemplate}
     * @return int the number of rows affected.
     * 
     */
    public static int executeQuery(final String sqlQuery, final Object[] params,
            final JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.update(sqlQuery, params);
    }

}
