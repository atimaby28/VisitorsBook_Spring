package com.visitor.visitorsbook.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.visitor.util.DBUtil;
import com.visitor.visitorsbook.model.VisitorsBookDto;

@Repository
public class VisitorsBookDaoImpl implements VisitorsBookDao {
	
	@Autowired
	private DBUtil dbUtil;
	
	@Autowired
	private DataSource dataSource;

	@Override
	public void registerArticle(VisitorsBookDto visitorsBookDto) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder registerArticle = new StringBuilder();
			registerArticle.append("INSERT INTO visitorsbook (visitorid, subject, content, regtime) \n");
			registerArticle.append("VALUES (?, ?, ?, now())");
			pstmt = conn.prepareStatement(registerArticle.toString());
			pstmt.setString(1, visitorsBookDto.getVisitorId());
			pstmt.setString(2, visitorsBookDto.getSubject());
			pstmt.setString(3, visitorsBookDto.getContent());
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public List<VisitorsBookDto> listArticle(Map<String, Object> map) throws Exception {
		List<VisitorsBookDto> list = new ArrayList<VisitorsBookDto>();
		
		String key = (String) map.get("key");
		String word = (String) map.get("word");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder listArticle = new StringBuilder();
			listArticle.append("SELECT g.articleno, g.visitorid, g.subject, g.content, g.regtime, m.visitorname \n");
			listArticle.append("FROM visitorsbook g, visitors m \n");
			listArticle.append("WHERE g.visitorid = m.visitorid \n");
			if(!word.isEmpty()) {
				if(key.equals("subject"))
					listArticle.append("and g.subject LIKE ? \n");
				else 
					listArticle.append("and g." + key + " = ? \n");
			}
			listArticle.append("ORDER BY g.articleno DESC \n");
			listArticle.append("LIMIT ?, ?");
			pstmt = conn.prepareStatement(listArticle.toString());
			int idx = 0;
			if(!word.isEmpty()) {
				if(key.equals("subject"))
					pstmt.setString(++idx, "%" + word + "%");
				else 
					pstmt.setString(++idx, word);
			}
			pstmt.setInt(++idx, (int) map.get("start"));
			pstmt.setInt(++idx, (int) map.get("spp"));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				VisitorsBookDto visitorsBookDto = new VisitorsBookDto();
				visitorsBookDto.setArticleNo(rs.getInt("articleno"));
				visitorsBookDto.setVisitorId(rs.getString("visitorid"));
				visitorsBookDto.setVisitorName(rs.getString("visitorname"));
				visitorsBookDto.setSubject(rs.getString("subject"));
				visitorsBookDto.setContent(rs.getString("content"));
				visitorsBookDto.setRegTime(rs.getString("regtime"));
				
				list.add(visitorsBookDto);
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public int getTotalCount(Map<String, String> map) throws Exception {
		int cnt = 0;
		
		String key = map.get("key");
		String word = map.get("word");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(articleno) \n");
			sql.append("FROM visitorsbook \n");
			if(!word.isEmpty()) {
				if(key.equals("subject"))
					sql.append("WHERE subject LIKE ? \n");
				else 
					sql.append("WHERE " + key + " = ? \n");
			}
			pstmt = conn.prepareStatement(sql.toString());
			if(!word.isEmpty()) {
				if(key.equals("subject"))
					pstmt.setString(1, "%" + word + "%");
				else 
					pstmt.setString(1, word);
			}
			rs = pstmt.executeQuery();
			rs.next();
			cnt = rs.getInt(1);
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return cnt;
	}

	@Override
	public VisitorsBookDto getArticle(int articleNo) throws Exception {
		VisitorsBookDto visitorsBookDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder getArticle = new StringBuilder();
			getArticle.append("SELECT subject, content \n");
			getArticle.append("FROM visitorsbook \n");
			getArticle.append("WHERE articleno = ? \n");
			pstmt = conn.prepareStatement(getArticle.toString());
			pstmt.setInt(1, articleNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				visitorsBookDto = new VisitorsBookDto();
				visitorsBookDto.setArticleNo(articleNo);
				visitorsBookDto.setSubject(rs.getString("subject"));
				visitorsBookDto.setContent(rs.getString("content"));
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return visitorsBookDto;
	}

	@Override
	public void updateArticle(VisitorsBookDto visitorsBookDto) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder registerArticle = new StringBuilder();
			registerArticle.append("UPDATE visitorsbook \n");
			registerArticle.append("SET subject = ?, content = ? \n");
			registerArticle.append("WHERE articleno = ?");
			pstmt = conn.prepareStatement(registerArticle.toString());
			pstmt.setString(1, visitorsBookDto.getSubject());
			pstmt.setString(2, visitorsBookDto.getContent());
			pstmt.setInt(3, visitorsBookDto.getArticleNo());
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder registerArticle = new StringBuilder();
			registerArticle.append("DELETE FROM visitorsbook \n");
			registerArticle.append("WHERE articleno = ?");
			pstmt = conn.prepareStatement(registerArticle.toString());
			pstmt.setInt(1, articleNo);
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

}
