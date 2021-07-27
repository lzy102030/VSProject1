package client.service.database;

import java.sql.*;
import java.util.HashMap;

public class MySQL {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = //"jdbc:mysql://localhost:3306/VSProject?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            "jdbc:mysql://noneid.myds.me:3306/VSProject?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "QylLzy2021VsP";

    HashMap<String, Integer> heroInfoMap = new HashMap<>();

    public MySQL() {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            // 执行查询
            ResultSet rs = null;

            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM hero_info";
            rs = stmt.executeQuery(sql);


            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                int id = rs.getInt("id");
                String name = rs.getString("hero_name");
                int impact = rs.getInt("hero_impact");

                heroInfoMap.put(name, impact);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception se) {
            // 处理 JDBC 错误   处理 Class.forName 错误
            se.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new MySQL();
    }

    public HashMap<String, Integer> getHeroInfoMap() {
        return heroInfoMap;
    }
}

