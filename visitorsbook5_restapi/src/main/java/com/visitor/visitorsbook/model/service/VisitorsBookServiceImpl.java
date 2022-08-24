package com.visitor.visitorsbook.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.visitor.util.PageNavigation;
import com.visitor.visitorsbook.model.FileInfoDto;
import com.visitor.visitorsbook.model.VisitorsBookDto;
import com.visitor.visitorsbook.model.mapper.VisitorsBookMapper;

@Service
public class VisitorsBookServiceImpl implements VisitorsBookService {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	@Transactional
	public void registerArticle(VisitorsBookDto visitorsBookDto) throws Exception {
		VisitorsBookMapper visitorsBookMapper = sqlSession.getMapper(VisitorsBookMapper.class);
		visitorsBookMapper.registerArticle(visitorsBookDto);
		List<FileInfoDto> fileInfos = visitorsBookDto.getFileInfos();
		if (fileInfos != null && !fileInfos.isEmpty()) {
			visitorsBookMapper.registerFile(visitorsBookDto);
		}
	}

	@Override
	public List<VisitorsBookDto> listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if("visitorid".equals(key))
			key = "g.visitorid";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pg") == null ? "1" : map.get("pg"));
		int sizePerPage = Integer.parseInt(map.get("spp"));
		int start = (currentPage - 1) * sizePerPage;
		param.put("start", start);
		param.put("spp", sizePerPage);
		return sqlSession.getMapper(VisitorsBookMapper.class).listArticle(param);
	}
	
	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();
		
		int naviSize = 10;
		int currentPage = Integer.parseInt(map.get("pg"));
		int sizePerPage = Integer.parseInt(map.get("spp"));
		
		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(VisitorsBookMapper.class).getTotalCount(map);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		
		return pageNavigation;
	}

	@Override
	public VisitorsBookDto getArticle(int articleNo) throws Exception {
		return sqlSession.getMapper(VisitorsBookMapper.class).getArticle(articleNo);
	}

	@Override
	public void updateArticle(VisitorsBookDto visitorsBookDto) throws Exception {
		sqlSession.getMapper(VisitorsBookMapper.class).updateArticle(visitorsBookDto);
	}

	@Override
	@Transactional
	public void deleteArticle(int articleNo, String path) throws Exception {
		VisitorsBookMapper visitorsBookMapper = sqlSession.getMapper(VisitorsBookMapper.class);
		List<FileInfoDto> fileList = visitorsBookMapper.fileInfoList(articleNo);
		visitorsBookMapper.deleteFile(articleNo);
		visitorsBookMapper.deleteArticle(articleNo);
		for(FileInfoDto fileInfoDto : fileList) {
			File file = new File(path + File.separator + fileInfoDto.getSaveFolder() + File.separator + fileInfoDto.getSaveFile());
			file.delete();
		}
	}

}
