package com.visitor.visitorsbook.model.mapper;

import java.util.List;
import java.util.Map;

import com.visitor.visitorsbook.model.FileInfoDto;
import com.visitor.visitorsbook.model.VisitorsBookDto;

public interface VisitorsBookMapper {
	
	void registerArticle(VisitorsBookDto visitorsBookDto) throws Exception;
	void registerFile(VisitorsBookDto visitorsBookDto) throws Exception;
	List<VisitorsBookDto> listArticle(Map<String, Object> map) throws Exception;
	int getTotalCount(Map<String, String> map) throws Exception;
	VisitorsBookDto getArticle(int articleNo) throws Exception;
	void updateArticle(VisitorsBookDto visitorsBookDto) throws Exception;
	void deleteFile(int articleNo) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
	
}
