package com.minmin.wenda.dao;

import com.minmin.wenda.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @author corn
 * @version 1.0.0
 */

@Mapper
public interface UserDAO {
    String TABLE_NAME = " user ";
    String INSERT_FILEDS = " name, password, salt, head_url ";
    String SELECT_FILED = " id, " + INSERT_FILEDS;


    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FILEDS,
            ") values (#{name}, #{password}, #{salt}, #{headUrl})"})
    int addUser(User user);

    @Select({"select ", SELECT_FILED, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FILED, " from ", TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
