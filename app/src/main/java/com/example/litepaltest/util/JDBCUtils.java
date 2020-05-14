package com.example.litepaltest.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtils {

    /**
     * 获取连接方法
     * @return
     */

    public static Connection getConnection(){

        Connection conn = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://101.133.214.9:3306/dingding","root","123456");



        } catch (Exception e) {



            e.printStackTrace();

        }





        return conn;

    }

    /**
     * 释放资源方法
     * @param conn
     * @param pstmt
     * @param rs
     */

    public static void release(Connection conn, PreparedStatement pstmt, ResultSet rs){

        if(rs != null){

            try {

                rs.close();

            } catch (SQLException e) {



                e.printStackTrace();

            }

        }

        if(pstmt != null){

            try {

                pstmt.close();

            } catch (SQLException e) {



                e.printStackTrace();

            }

        }

        if(conn != null){

            try {

                conn.close();

            } catch (SQLException e) {



                e.printStackTrace();

            }

        }

    }


}
