package com.visitor.visitorsbook.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visitor.visitorsbook.model.VisitorDto;
import com.visitor.visitorsbook.model.dao.VisitorDao;

@Service
public class VisitorServiceImpl implements VisitorService {

	@Autowired
	private VisitorDao visitorDao;
	
	@Override
	public int idCheck(String id) throws Exception {
		return visitorDao.idCheck(id); // 0 or 1
	}

	@Override
	public void registerVisitor(VisitorDto visitorDto) throws Exception {
//		validation check
		visitorDao.registerVisitor(visitorDto);
	}

	@Override
	public VisitorDto login(Map<String, String> map) throws Exception {
		return visitorDao.login(map);
	}

}
