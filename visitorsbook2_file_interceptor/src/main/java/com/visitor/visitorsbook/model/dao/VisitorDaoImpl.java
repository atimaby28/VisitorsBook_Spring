package com.visitor.visitorsbook.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.visitor.util.DBUtil;
import com.visitor.visitorsbook.model.VisitorDto;

@Repository
public class VisitorDaoImpl implements VisitorDao {
	
	@Autowired
	private DBUtil dbUtil;
	
	@Autowired
	private DataSource dataSource;

	@Override
	public int idCheck(String id) throws Exception {
		int cnt = 1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder loginVisitor = new StringBuilder();
			loginVisitor.append("select count(visitorid) \n");
			loginVisitor.append("from visitors \n");
			loginVisitor.append("where visitorid = ?");
			pstmt = conn.prepareStatement(loginVisitor.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			cnt = rs.getInt(1);
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return cnt;
	}

	@Override
	public void registerVisitor(VisitorDto visitorDto) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder registerVisitor = new StringBuilder();
			registerVisitor.append("insert into visitors (visitorid, visitorname, visitorpwd, email, joindate) \n");
			registerVisitor.append("values (?, ?, ?, ?, now())");
			pstmt = conn.prepareStatement(registerVisitor.toString());
			pstmt.setString(1, visitorDto.getVisitorId());
			pstmt.setString(2, visitorDto.getVisitorName());
			pstmt.setString(3, visitorDto.getVisitorPwd());
			pstmt.setString(4, visitorDto.getEmail());
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public VisitorDto login(Map<String, String> map) throws Exception {
		VisitorDto visitorDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder loginVisitor = new StringBuilder();
			loginVisitor.append("select visitorid, visitorname \n");
			loginVisitor.append("from visitors \n");
			loginVisitor.append("where visitorid = ? and visitorpwd = ? \n");
			pstmt = conn.prepareStatement(loginVisitor.toString());
			pstmt.setString(1, map.get("visitorId"));
			pstmt.setString(2, map.get("visitorPwd"));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				visitorDto = new VisitorDto();
				visitorDto.setVisitorId(rs.getString("visitorid"));
				visitorDto.setVisitorName(rs.getString("visitorname"));
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return visitorDto;
	}

}
