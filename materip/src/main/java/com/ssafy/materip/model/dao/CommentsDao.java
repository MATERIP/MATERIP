package com.ssafy.materip.model.dao;

import com.ssafy.materip.model.dto.Comments;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface CommentsDao {
    int createComments(Comments comments) throws SQLException;

    List<Comments> readAllComments(Comments comments) throws SQLException;

    int updateComments(Comments comments) throws SQLException;

    int deleteComments(Comments comments) throws SQLException;
}
