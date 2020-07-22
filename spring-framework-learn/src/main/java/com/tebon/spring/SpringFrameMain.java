package com.tebon.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringFrameMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
		TestDao dao =  ctx.getBean(TestDao.class);
		System.out.println(dao.getClass());
	}
}
