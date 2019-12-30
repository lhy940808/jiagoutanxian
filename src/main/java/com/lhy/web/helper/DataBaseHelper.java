package com.lhy.web.helper;

import com.lhy.web.utils.CollectionUtil;
import com.lhy.web.utils.PropsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库操作助手
 * @author liuhaiyan
 * @date 2019-12-26 10:58
 */
@Slf4j
public final class DataBaseHelper {

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    /**
     * 确保一个线程只有一个connectiion
     * */
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

    private static final BasicDataSource DATA_SOURCE;

    static {
        Properties jdbc = PropsUtil.loadProps("jdbc.properties");
        DRIVER = jdbc.getProperty("jdbc.driver");
        URL = jdbc.getProperty("jdbc.url");
        USERNAME = jdbc.getProperty("jdbc.username");
        PASSWORD = jdbc.getProperty("jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);

//        try {
//            Class.forName(DRIVER);
//        } catch (ClassNotFoundException e) {
//            log.error("can not load jdbc driver ,", e);
//        }
    }

    /**
     * 获取连接
     * */
//    public static Connection getConnection() {
//        Connection conn = null;
//        try {
//            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        } catch (SQLException e ) {
//            log.error("get connection error ,", e);
//        }
//        return conn;
//    }

    public static Connection getConnection () {
        Connection conn = CONNECTION_HOLDER.get();

        if (conn == null) {
            try{
//                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                log.error("get connection error", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /**
     * @Description : 关闭数据库连接
     * @params [conn]
     * @return void
     * @author liuhaiyan
     * @date 2019-12-26 14:28
     */
//    public static void closeConnection(Connection conn) {
//        if (conn !=null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                log.error("close connection failure, ", e);
//            }
//        }
//    }
//    public static void closeConnection() {
//        Connection conn = CONNECTION_HOLDER.get();
//        if (conn != null) {
//            try {
//                conn.close();
//            }catch (SQLException e) {
//                log.error("close connection error, ", e);
//                throw new RuntimeException(e);
//            } finally {
//                CONNECTION_HOLDER.remove();
//            }
//        }
//    }

    /**
     * @Description : 查询实体列表
     * @params [entityClass, sql, params]
     * @return java.util.List<T>
     * @author liuhaiyan
     * @date 2019-12-26 15:47
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object ... params) {
        List<T> entityList;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            log.error("query entity list error ", e);
            throw new RuntimeException(e);
        }
//        finally {
//            closeConnection();
//        }
        return entityList;
    }

    /**
     * 查询单个实体信息
     * */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object ... params) {
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            log.error("query entity error", e);
            throw new RuntimeException(e);
        }
//        finally {
//            closeConnection();
//        }
        return entity;
    }

    /**
     * @Description : 执行查询语句
     * @params
     * @return
     * @author liuhaiyan
     * @date 2019-12-26 18:11
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object ... params) {
        List<Map<String, Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (Exception e) {
            log.error("execute query error, ", e);
            throw new RuntimeException(e);
        }
//        finally {
//            closeConnection();
//        }
        return result;
    }

    /**
     * @Description : 执行更新语句（包括insert、update、delete）
     * @params [sql, params]
     * @return int
     * @author liuhaiyan
     * @date 2019-12-26 18:15
     */
    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            log.error("execute update error ," , e);
            throw new RuntimeException(e);
        }
//        finally {
//            closeConnection();
//        }
        return rows;
    }

    /**
     * @Description : 插入实体
     * @params [entityClass, fieldMap]
     * @return boolean
     * @author liuhaiyan
     * @date 2019-12-26 19:31
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        boolean result = false;
        if (CollectionUtil.isEmpty(fieldMap)) {
            log.error("insert entity error, fieldMap is null");
            return result;
        }
        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String field : fieldMap.keySet()) {
            columns.append(field).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + "VALUES " + values;

        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) >= 1;

    }

    /**
     * @Description :更新实体
     * @params [entityClass, fieldMap, id]
     * @return boolean
     * @author liuhaiyan
     * @date 2019-12-26 19:58
     */
    public static <T> boolean updateEntity(Class<T> entityClass, Map<String, Object> fieldMap, long id) {
        boolean result = false;
        if (CollectionUtil.isEmpty(fieldMap)) {
            log.error("update entity error , fieldmap is empty");
            return result;
        }
        String sql = "update " + getTableName(entityClass) + "set ";
        StringBuilder set = new StringBuilder();
        for (String field : fieldMap.keySet()) {
            set.append(field).append("=?, ");
        }
        set.replace(set.lastIndexOf(", "), set.length(), " ");
        sql += set + "where id = ?";
        List<Object> params = new ArrayList<>();
        params.addAll(fieldMap.values());
        params.add(id);
        Object[] paramsObj =  params.toArray();
        return executeUpdate(sql, paramsObj) >= 1;
    }


    /**
     * @Description : 删除实体
     * @params [entityClass, id]
     * @return boolean
     * @author liuhaiyan
     * @date 2019-12-26 20:04
     */
    public static <T> boolean deleteEntity (Class<T> entityClass, long id) {
        String sql = "delete from " + getTableName(entityClass) + "where id = ?";
        return executeUpdate(sql, id) == 1;
    }

    public static void executeSqlFIle(String filePath) {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String sql;
            while ((sql = br.readLine()) != null) {
                executeUpdate(sql);
            }
        }catch (Exception e) {
            log.error("execute sql file error, ", e);
            throw new RuntimeException(e);
        }
    }
    private static String getTableName(Class<?> entityClass) {
        return entityClass.getSimpleName().toLowerCase();
    }

    public static void main(String[] args) {
        System.out.println(DataBaseHelper.class.getSimpleName().toLowerCase());
    }



}
