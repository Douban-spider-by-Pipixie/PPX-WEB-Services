package cn.itcast.ppx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
    String user = "root";//�û���
    String password = "16251425Zyq";//����
    String url = "jdbc:mysql://cdb-e0agigrr.gz.tencentcdb.com:10087/test1.0";//���ݿ���
    String driver = "com.mysql.jdbc.Driver";//���ݿ�����
    PreparedStatement preStmt=null;
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    //�����ݿ�����
    public void openConnect(){
        try{
            //������������
            Class.forName(driver);
            con=(Connection) DriverManager.getConnection(url,user,password);
            if(!con.isClosed()){
                System.out.println("�ɹ��������ݿ⣡");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // ��ò�ѯuser�������ݼ�
    public ResultSet getUser() {
        // ���� statement����
        try {
            stmt = con.createStatement(); // ִ��SQL��ѯ���
            rs = stmt.executeQuery("select * from login");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }


    // �ж����ݿ����Ƿ����ĳ���û�����������,ע��͵�¼��ʱ���ж�
    public boolean isExistInDB(String username, String password) {
        boolean isFlag = false; // ���� statement����
        try {
            System.out.println("�ж��û�������");
            stmt = con.createStatement(); // ִ��SQL��ѯ���
            rs = stmt.executeQuery("select * from login");// ��ý����
            if (rs != null) {
                while (rs.next()) { // ���������
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

    // ע�� ���û�����������뵽���ݿ�(id���õ����������ģ���˲���Ҫ����)
    public boolean insertDataToDB(String username, String password) {
        String sql = " insert into login (user_name , user_pwd) values ( " + "'" + username + "', " + "'" + password
                + "' )";
        try {
            stmt = con.createStatement();
            // ִ��SQL��ѯ���
            return stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // �ر����ݿ�����
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
            System.out.println("�ر����ݿ����ӳɹ�");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
