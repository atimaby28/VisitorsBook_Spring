package com.visitor.visitorsbook.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.visitor.util.SqlMapConfig;
import com.visitor.visitorsbook.model.FileInfoDto;
import com.visitor.visitorsbook.model.VisitorsBookDto;

@Repository
public class VisitorsbookDaoImpl implements VisitorsBookDao {
	
	private final String NAMESPACE = "com.visitor.visitorsbook.model.dao.VisitorsBookDao.";
	
	@Override
	public void registerArticle(VisitorsBookDto visitorsBookDto) throws Exception {
		try (SqlSession sqlSession = SqlMapConfig.getSqlSession()) {
			sqlSession.insert(NAMESPACE + "registerArticle", visitorsBookDto);
			List<FileInfoDto> fileInfos = visitorsBookDto.getFileInfos();
			if (fileInfos != null && !fileInfos.isEmpty()) {
				sqlSession.insert(NAMESPACE + "registerFile", visitorsBookDto);
			}
			sqlSession.commit();
		}
	}

	@Override
	public List<VisitorsBookDto> listArticle(Map<String, Object> map) throws Exception {
		try (SqlSession sqlSession = SqlMapConfig.getSqlSession()) {
			return sqlSession.selectList(NAMESPACE + "listArticle", map);
		}
	}

	@Override
	public int getTotalCount(Map<String, String> map) throws Exception {
		try (SqlSession sqlSession = SqlMapConfig.getSqlSession()) {
			return sqlSession.selectOne(NAMESPACE + "getTotalCount", map);
		}
	}

	@Override
	public VisitorsBookDto getArticle(int articleNo) throws Exception {
		try (SqlSession sqlSession = SqlMapConfig.getSqlSession()) {
			return sqlSession.selectOne(NAMESPACE + "getArticle", articleNo);
		}
	}

	@Override
	public void updateArticle(VisitorsBookDto visitorsBookDto) throws Exception {
		try (SqlSession sqlSession = SqlMapConfig.getSqlSession()) {
			sqlSession.selectOne(NAMESPACE + "updateArticle", visitorsBookDto);
			sqlSession.commit();
		}
	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
		try (SqlSession sqlSession = SqlMapConfig.getSqlSession()) {
			sqlSession.delete(NAMESPACE + "deleteFile", articleNo);
			sqlSession.delete(NAMESPACE + "deleteArticle", articleNo);
			sqlSession.commit();
		}
	}

	@Override
	public List<FileInfoDto> fileInfoList(int articleNo) throws Exception {
		try (SqlSession sqlSession = SqlMapConfig.getSqlSession()) {
			return sqlSession.selectList(NAMESPACE + "fileInfoList", articleNo);
		}
	}
	

}
