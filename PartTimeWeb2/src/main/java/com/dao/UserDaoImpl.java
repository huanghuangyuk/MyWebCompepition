package com.dao;

import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    public JdbcTemplate jdbcTemplate;
    @Override
    public boolean getUser(User user){
        boolean flag=false;
        String sql="select 1 from student where stu_phone=? and stu_passw=?";//用1效率高
        String str=jdbcTemplate.queryForObject(sql,new Object[]{user.getPhonenumber(),user.getPassword()},java.lang.String.class);
        if(str.equals("1")){
            flag=true;
        }else{
            sql="select 1 from business where b_phone=? and b_passw=?";
            str=jdbcTemplate.queryForObject(sql,new Object[]{user.getPhonenumber(),user.getPassword()},java.lang.String.class);
            if(str.equals("1")) flag=true;
        }
        return flag;
    }
}
