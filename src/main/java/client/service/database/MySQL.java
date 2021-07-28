package client.service.database;

import server.service.CreateDataPDF;

import java.sql.*;
import java.util.HashMap;

public class MySQL {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = //"jdbc:mysql://localhost:3306/VSProject?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            "jdbc:mysql://noneid.myds.me:3306/test0728?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    Connection connection = null;

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "QylLzy2021VsP";

    HashMap<String, Integer> heroInfoMap = new HashMap<>();

    public MySQL() {
        // 注册 JDBC 驱动
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 打开链接
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //[todo] 建表与添加 添加可能使用线程
    private void readHeroInfo() {
        Statement stmt = null;
        ResultSet rs;

        try {
            // 执行查询
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT * FROM hero_info";
            rs = stmt.executeQuery(sql);


            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                String name = rs.getString("hero_name");
                int impact = rs.getInt("hero_impact");

                heroInfoMap.put(name, impact);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            connection.close();
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
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public HashMap<String, Integer> getHeroInfoMap() {
        readHeroInfo();

        return heroInfoMap;
    }

    public void createDataTable(String tableName) {
        Statement stmt = null;

        String sql = "CREATE TABLE `" + tableName + "` (" +
                "  `userid` int COMMENT '用户id'," +
                "  `impact` int COMMENT '造成伤害'," +
                "  `defence` int COMMENT '格挡伤害'," +
                "  `hp` int COMMENT '剩余血量'," +
                "  `mp` int COMMENT '获得怒气'," +
                "  PRIMARY KEY (`userid`)" +
                ")";

        try {
            stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            }// 什么都不做
            try {
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new MySQL();
    }

    public void insertData(double[][] data, String tableName) {
        PreparedStatement preparedStatement = null;

        try {
            for (int i = 0; i < 2; ++i) {
                String sql = "insert into " + tableName + " (userid,impact,defence,hp,mp) values(?,?,?,?,?)";

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, String.valueOf(i + 1));

                for (int j = 0; j < 4; ++j) {
                    preparedStatement.setString(j + 2, String.valueOf((int) data[i][j]));
                }

                preparedStatement.executeUpdate();// 返回值代表收到影响的行数
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException ignored) {
            }// 什么都不做
            try {
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void createPDF(String tableName) {
        new CreateDataPDF(connection, tableName);
    }
}

