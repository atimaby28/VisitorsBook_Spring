package com.visitor.visitorsbook.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visitor.visitorsbook.model.VisitorDto;
import com.visitor.visitorsbook.model.mapper.VisitorMapper;

@Service
public class VisitorServiceImpl implements VisitorService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int idCheck(String checkId) throws Exception {
		return sqlSession.getMapper(VisitorMapper.class).idCheck(checkId); // 0 or 1
	}

	@Override
	public void registerVisitor(VisitorDto visitorDto) throws Exception {
//		validation check
		sqlSession.getMapper(VisitorMapper.class).registerVisitor(visitorDto);
	}

	@Override
	public VisitorDto login(Map<String, String> map) throws Exception {
		return sqlSession.getMapper(VisitorMapper.class).login(map);
	}
	
	@Override
	public List<VisitorDto> listVisitor() throws Exception {
		return sqlSession.getMapper(VisitorMapper.class).listVisitor();
	}

	@Override
	public VisitorDto getVisitor(String visitorId) throws Exception {
		return sqlSession.getMapper(VisitorMapper.class).getVisitor(visitorId);
	}

	@Override
	public void updateVisitor(VisitorDto visitorDto) throws Exception {
		sqlSession.getMapper(VisitorMapper.class).updateVisitor(visitorDto);
	}

	@Override
	public void deleteVisitor(String visitorId) throws Exception {
		sqlSession.getMapper(VisitorMapper.class).deleteVisitor(visitorId);
	}

}
