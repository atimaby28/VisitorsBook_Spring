package com.visitor.visitorsbook.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visitor.util.PageNavigation;
import com.visitor.visitorsbook.model.VisitorsBookDto;
import com.visitor.visitorsbook.model.dao.VisitorsBookDao;

@Service
public class VisitorsBookServiceImpl implements VisitorsBookService {
	
	@Autowired
	private VisitorsBookDao visitorsBookDao;

	@Override
	public void registerArticle(VisitorsBookDto visitorsBookDto) throws Exception {
		visitorsBookDao.registerArticle(visitorsBookDto);
	}

	@Override
	public List<VisitorsBookDto> listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("key", map.get("key") == null ? "" : map.get("key"));
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pg") == null ? "1" : map.get("pg"));
		int sizePerPage = Integer.parseInt(map.get("spp"));
		int start = (currentPage - 1) * sizePerPage;
		param.put("start", start);
		param.put("spp", sizePerPage);
		return visitorsBookDao.listArticle(param);
	}
	
	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();
		
		int naviSize = 10;
		int currentPage = Integer.parseInt(map.get("pg"));
		int sizePerPage = Integer.parseInt(map.get("spp"));
		
		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		int totalCount = visitorsBookDao.getTotalCount(map);
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
		return visitorsBookDao.getArticle(articleNo);
	}

	@Override
	public void updateArticle(VisitorsBookDto visitorsBookDto) throws Exception {
		visitorsBookDao.updateArticle(visitorsBookDto);
	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
		visitorsBookDao.deleteArticle(articleNo);
	}

}
