package cn.itcast.ppx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
    String user = "root";//用户名
    String password = "16251425Zyq";//密码
    String url = "jdbc:mysql://cdb-e0agigrr.gz.tencentcdb.com:10087/test1.0";//数据库名
    String driver = "com.mysql.jdbc.Driver";//数据库驱动
    PreparedStatement preStmt=null;
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    //打开数据库连接
    public void openConnect(){
        try{
            //加载驱动程序
            Class.forName(driver);
            con=(Connection) DriverManager.getConnection(url,user,password);
            if(!con.isClosed()){
                System.out.println("成功连接数据库！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // 获得查询user表后的数据集
    public ResultSet getUser() {
        // 创建 statement对象
        try {
            stmt = con.createStatement(); // 执行SQL查询语句
            rs = stmt.executeQuery("select * from login");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }


    // 判断数据库中是否存在某个用户名及其密码,注册和登录的时候判断
    public boolean isExistInDB(String username, String password) {
        boolean isFlag = false; // 创建 statement对象
        try {
            System.out.println("判断用户名密码");
            stmt = con.createStatement(); // 执行SQL查询语句
            rs = stmt.executeQuery("select * from login");// 获得结果集
            if (rs != null) {
                while (rs.next()) { // 遍历结果集
                    if (rs.getString("user_name").equals(username)) {
                        if (rs.getString("user_pwd").equals(password)) {
                            isFlag = true;
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            isFlag = false;
        }
        return isFlag;
    }

    // 注册 将用户名和密码插入到数据库(id设置的是自增长的，因此不需要插入)
    public boolean insertDataToDB(String username, String password) {
        String sql = " insert into login (user_name , user_pwd) values ( " + "'" + username + "', " + "'" + password
                + "' )";
        try {
            stmt = con.createStatement();
            // 执行SQL查询语句
            return stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 关闭数据库连接
    public void closeConnect() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
            System.out.println("关闭数据库连接成功");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
