package com.visitor.visitorsbook.model.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visitor.visitorsbook.model.VisitorDto;
import com.visitor.visitorsbook.model.dao.VisitorDao;

@Service
public class VisitorServiceImpl implements VisitorService {

//	@Autowired
//	private VisitorDao visitorDao;
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int idCheck(String id) throws Exception {
		return sqlSession.getMapper(VisitorDao.class).idCheck(id); // 0 or 1
	}

	@Override
	public void registerVisitor(VisitorDto visitorDto) throws Exception {
//		validation check
		sqlSession.getMapper(VisitorDao.class).registerVisitor(visitorDto);
	}

	@Override
	public VisitorDto login(Map<String, String> map) throws Exception {
		return sqlSession.getMapper(VisitorDao.class).login(map);
	}

}
