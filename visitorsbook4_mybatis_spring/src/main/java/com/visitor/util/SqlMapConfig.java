//package com.visitor.util;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//
//
//
//public class SqlMapConfig {
//	
//	private static SqlSessionFactory factory;
//	
//	static {
//		try {
//			String resource = "mybatis-config.xml";
//			InputStream inputStream = Resources.getResourceAsStream(resource);
//			factory = new SqlSessionFactoryBuilder().build(inputStream);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public static SqlSession getSqlSession() {
//		return factory.openSession();
//	}
//	
//}
