package com.example.litepaltest.Dao;

import android.util.Log;

import com.example.litepaltest.entity.Approve;
import com.example.litepaltest.entity.News;
import com.example.litepaltest.entity.Schedule;
import com.example.litepaltest.entity.Sign;
import com.example.litepaltest.entity.User;
import com.example.litepaltest.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.litepal.LitePalBase.TAG;

public class DataDao extends BaseDao{

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;


    //人员的添加
    public void addUser(User user){
        String sql = "insert into user(name,password,role,phone) VALUES(?,?,?,?) ";
        Object [] objects = {user.getName(),user.getPassword(),user.getRole(),user.getPhone()};
        toUpdate(sql,objects);
    }

    //人员的查询,名字
    public List<User> selectUser(String name){
        List<User> userList = new ArrayList<>();
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from user where name = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,name);
            rs= ps.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setRole(0);
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                userList.add(user);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtils.release(conn,ps,rs);
        }
        return userList;
    }


    //人员的查询
    public List<User> selectAllUser(){
        List<User> userList = new ArrayList<User>();
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from user ";
            ps = conn.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()){
                User user = new User();

                    user.setRole(0);
                    user.setName(rs.getString("name"));
                    user.setPhone(rs.getString("phone"));

                userList.add(user);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtils.release(conn,ps,rs);
        }
        return userList;
    }

    //根据名字删除人员
    public void deleteUser(String name){
        String sql = "delete from user where name = ?";
        Object [] objects = {name};
        toUpdate(sql,objects);

    }





    //进行签到
    public void addLocation(Sign sign){
        String sql = "insert into sign (name,location,date) VALUES(?,?,?) ";
        Object [] objects = {sign.getName(),sign.getLocation(),sign.getDate()};
        toUpdate(sql,objects);
    }

    //显示所有打卡
    public List<Sign> selectAllSign(){
        List<Sign> signList = new ArrayList<Sign>();
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from sign ";
            ps = conn.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()){
                Sign sign = new Sign();
                sign.setName(rs.getString("name"));
                sign.setLocation(rs.getString("location"));
                sign.setDate(rs.getString("date"));
                signList.add(sign);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtils.release(conn,ps,rs);
        }
        return signList;

    }

    //分派任务
    public void addSchedule(Schedule schedule){
        String sql = "insert into Schedule(name,content,date,finished) VALUES(?,?,?,?) ";
        Object [] objects = {schedule.getName(),schedule.getContent(),schedule.getDate(),schedule.getFinished()};
        toUpdate(sql,objects);
    }


    //显示所有任务
    public List<Schedule> selectSchedule(String name){
        List<Schedule> scheduleList = new ArrayList<Schedule>();
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from Schedule where name = ? and finished = 0 ";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,name);
            rs= ps.executeQuery();
            while (rs.next()){
                Schedule schedule = new Schedule();
                schedule.setName(rs.getString("name"));
                schedule.setContent(rs.getString("content"));
                schedule.setDate(rs.getString("date"));
                scheduleList.add(schedule);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtils.release(conn,ps,rs);
        }
        return scheduleList;

    }

    //完成任务
    public void updataSchedule(String name){
        String sql = "update Schedule set finished = 1 where name = ?";
        Object [] objects = {name};
        toUpdate(sql,objects);
    }


    //发布公告
    public void addNews(News news){
        String sql = "insert into news (title,content,pricture) VALUES (?,?,?)  ";
        Object [] objects = {news.getTitle(),news.getContent(),news.getPrictureUrl()};
        toUpdate(sql,objects);
    }


    //显示所有公告
    public List<News> selectNews(){
        List<News>  newsList = new ArrayList<>();
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from news  order by id desc";
            ps = conn.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()){
                News news = new News();
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setPrictureUrl(rs.getString("pricture"));
                newsList.add(news);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtils.release(conn,ps,rs);
        }
        return newsList;

    }

    //显示所有用户的审批
    public List<Approve> selectApprove(){
        List<Approve> approveList = new ArrayList<>();
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from approve where approve =  0 ";
            ps = conn.prepareStatement(sql);

            rs= ps.executeQuery();
            while (rs.next()){
                Approve approve = new Approve();
                approve.setContent(rs.getString("content"));
                approve.setDate(rs.getString("date"));
                approve.setName(rs.getString("name"));
                approveList.add(approve);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtils.release(conn,ps,rs);
        }
        return approveList;
    }

    //显示该用户的审批
    public List<Approve> selectUserApprove(String name){
        List<Approve> approveList = new ArrayList<>();
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from approve where name = ? and approve = 0 ";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,name);
            rs= ps.executeQuery();
            while (rs.next()){
                Approve approve = new Approve();
                approve.setContent(rs.getString("content"));
                approve.setDate(rs.getString("date"));
                approve.setName(rs.getString("name"));
                approveList.add(approve);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtils.release(conn,ps,rs);
        }
        return approveList;
    }

    public void addApprove(Approve approve){
        String sql = "insert into approve (name,content,date,approve) VALUES (?,?,?,?)  ";
        Object [] objects = {approve.getName(),approve.getContent(),approve.getDate(),approve.getApprove()};
        toUpdate(sql,objects);
    }


    //完成任务
    public void updataApprove(String content){
        String sql = "update approve set approve = 1 where content = ?";
        Object [] objects = {content};
        toUpdate(sql,objects);
    }

    //显示所有的员工姓名
    public List<String> selectUserName(){
        List<String> userName = new ArrayList<>();
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select name from user ";
            ps = conn.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()){
                userName.add(rs.getString("name"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtils.release(conn,ps,rs);
        }
        return userName;
    }


    //模糊查询公告
    public List<News> selectNewsByTitle(String title){
        List<News>  newsList = new ArrayList<>();
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from news where title like '%"+title+"%' ";
            ps = conn.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()){
                News news = new News();
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setPrictureUrl(rs.getString("pricture"));
                newsList.add(news);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtils.release(conn,ps,rs);
        }
        return newsList;

    }




    }
