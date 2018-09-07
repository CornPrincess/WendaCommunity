package com.minmin.wenda.dao;

import com.minmin.wenda.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author corn
 * @version 1.0.0
 */

@Mapper
public interface QuestionDAO {
    String TABLE_NAME = " question ";
    String INSERT_FILEDS = " title, content, created_date, user_id, comment_count ";
    String SELECT_FILED = " id, " + INSERT_FILEDS;

    /*
        add question function
     */
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FILEDS,
            ") values (#{title}, #{content}, #{createdDate}, #{userId}, #{commentCount})"})
    int addQuestion(Question question);

    //@Select({"select ", SELECT_FILED, " from ", TABLE_NAME, " where id=#{id}"})


    /*
        selectionLatestQuestions function
     */
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);


}
