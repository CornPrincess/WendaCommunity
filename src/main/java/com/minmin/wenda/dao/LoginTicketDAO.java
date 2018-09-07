package com.minmin.wenda.dao;

import com.minmin.wenda.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @author corn
 * @version 1.0.0
 */

@Mapper
public interface LoginTicketDAO {
    String TABLE_NAME = " login_ticket ";
    String INSERT_FILEDS = " user_id, expired, status, ticket ";
    String SELECT_FILED = " id, " + INSERT_FILEDS;


    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FILEDS,
            ") values (#{user_id}, #{expired}, #{status}, #{ticket})"})
    int addTicket(LoginTicket ticket);

    @Select({"select ", SELECT_FILED, " from ", TABLE_NAME, " where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update, ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);

}
