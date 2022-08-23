//package com.visitor.visitorsbook.model.dao;
//
//import java.util.Map;
//
//import org.apache.ibatis.session.SqlSession;
//import org.springframework.stereotype.Repository;
//
//import com.visitor.util.SqlMapConfig;
//import com.visitor.visitorsbook.model.VisitorDto;
//
//@Repository
//public class VisitorDaoImpl implements VisitorDao {
//	
//	private final String NAMESPACE = "com.visitor.visitorsbook.model.dao.VisitorDao.";
//
//	@Override
//	public int idCheck(String id) throws Exception {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void registerVisitor(VisitorDto visitorDto) throws Exception {
//		try (SqlSession sqlSession = SqlMapConfig.getSqlSession()) {
//			sqlSession.insert(NAMESPACE + "registerVisitor", visitorDto);
//			sqlSession.commit();
//		}
//	}
//
//	@Override
//	public VisitorDto login(Map<String, String> map) throws Exception {
//		try (SqlSession sqlSession = SqlMapConfig.getSqlSession()) {
//			return sqlSession.selectOne(NAMESPACE + "login", map);
//		}
//	}
//
//}
