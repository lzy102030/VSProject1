package client.service.database;

import server.service.CreateDataPDF;

import java.sql.*;
import java.util.HashMap;

public class MySQL {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    //数据库网址
    private static final String DB_URL = //"jdbc:mysql://localhost:3306/VSProject?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            "jdbc:mysql://noneid.myds.me:3306/vsproject?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    Connection connection = null;

    //数据库的用户名与密码
    static final String USER = "root";
    static final String PASS = "QylLzy2021VsP";

    //英雄信息存储
    HashMap<String, Integer> heroInfoMap = new HashMap<>();

    public MySQL() {
        //注册JDBC驱动 初始化数据库连接
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //打开链接
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //读取英雄信息
    private void readHeroInfo() {
        Statement statement = null;
        ResultSet resultSet;

        try {
            //执行查询
            statement = connection.createStatement();
            String sql;
            sql = "SELECT * FROM hero_info";
            resultSet = statement.executeQuery(sql);

            //展开结果集数据库
            while (resultSet.next()) {
                //通过字段检索
                String name = resultSet.getString("hero_name");
                int impact = resultSet.getInt("hero_impact");

                //存入map
                heroInfoMap.put(name, impact);
            }

            // 完成后关闭
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (statement != null) statement.close();
            } catch (SQLException ignored) {
            }

            try {
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    //返回给主程序heroMap
    public HashMap<String, Integer> getHeroInfoMap() {
        readHeroInfo();

        return heroInfoMap;
    }

    //建表
    public void createDataTable(String tableName) {
        Statement statement = null;

        String sql = "CREATE TABLE `" + tableName + "` (" +
                "  `userid` int COMMENT '用户id'," +
                "  `impact` int COMMENT '造成伤害'," +
                "  `defence` int COMMENT '格挡伤害'," +
                "  `hp` int COMMENT '剩余血量'," +
                "  `mp` int COMMENT '获得怒气'," +
                "  PRIMARY KEY (`userid`)" +
                ")";

        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (statement != null) statement.close();
            } catch (SQLException ignored) {
            }

            try {
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    //存入数据
    public void insertData(double[][] data, String tableName) {
        PreparedStatement preparedStatement = null;

        try {
            //使用prepareStatement方法组合构造数据库指令
            for (int i = 0; i < 2; ++i) {
                String sql = "insert into " + tableName + " (userid,impact,defence,hp,mp) values(?,?,?,?,?)";

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, String.valueOf(i + 1));

                for (int j = 0; j < 4; ++j) {
                    preparedStatement.setString(j + 2, String.valueOf((int) data[i][j]));
                }

                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException ignored) {
            }

            try {
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    //绘制pdf并输出
    public void createPDF(String tableName) {
        new CreateDataPDF(connection, tableName);
    }
}

