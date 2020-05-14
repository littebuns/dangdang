package com.example.litepaltest.Dao;


import com.example.litepaltest.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * 做所有dao的父类，封装他们基本的增删改操作，
 * 因为所有的Dao的增删改操作都一样。
 * 只是换了 不同的 sql语句和传入的参数。
 */
public class BaseDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    /**
     * 封装 任何增删改
     * sql 传入方法的Sql语句
     * Object[] Sql语句里面的参数，因为参数的类型不同 所以Object
     */
    public int toUpdate(String sql,Object[] obs){
        int a=0;
        try {
            conn= JDBCUtils.getConnection();
            ps=conn.prepareStatement(sql);
            // 循环 赋值参数
            if(obs!=null&&obs.length>0){
                for(int i=0;i<obs.length;i++){
                    ps.setObject(i+1, obs[i]);
                }
            }
            a=ps.executeUpdate();
            return a;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.release(conn, ps, rs);
        }
        return a;
    }


}