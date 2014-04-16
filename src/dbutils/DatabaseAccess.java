package dbutils;

/**
 * Created with IntelliJ IDEA.
 * User: myan
 * Date: 14-3-30
 * Time: 下午2:26
 * To change this template use File | Settings | File Templates.
 */

import org.apache.commons.dbutils.*;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class DatabaseAccess {

    private static DatabaseAccess instance = null;

    private BasicDataSource bd;
    private QueryRunner runner;
    private Connection conn;


    private DatabaseAccess() {

    }

    public static DatabaseAccess getInstance() {
        if (instance == null) {
            instance = new DatabaseAccess();
            instance.init();
        }
        return instance;
    }

    private void init () {

        try {
            bd = new BasicDataSource();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        bd.setDriverClassName("com.mysql.jdbc.Driver");
        bd.setUsername("root");
        bd.setPassword("123456");
        bd.setUrl("jdbc:mysql://127.0.0.1:3306/dbtest?user=root" +
                "&password=123456&useUnicode=true&characterEncoding=utf-8");


        try {
            conn = bd.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        runner = new QueryRunner(bd);
    }

    public <T> T query(String sql, ResultSetHandler<T> handler, Object... params) throws SQLException {
        return runner.query(this.conn, sql ,handler, params);
    }

    public int update(String sql, Object... params) throws SQLException {
        return runner.update(this.conn, sql, params);
    }
}
