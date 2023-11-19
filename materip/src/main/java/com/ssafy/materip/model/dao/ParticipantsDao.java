package com.ssafy.materip.model.dao;

import com.ssafy.materip.model.dto.Participants;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ParticipantsDao {

	int addParticipants(Participants participants) throws SQLException;

	List<Participants> getParticipants(int boardId) throws SQLException;

	int removeParticipants(Participants participants) throws SQLException;

	int getParticipantsCnt(int boardId) throws SQLException;
}
