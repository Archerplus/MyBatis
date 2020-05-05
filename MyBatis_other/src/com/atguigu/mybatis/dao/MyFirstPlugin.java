package com.atguigu.mybatis.dao;

import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

/*
 * 完成插件的签名：
 * 		告诉MyBatis当前插件用来拦截那个对象的那个方法
 */

@Intercepts({ @Signature(type = StatementHandler.class, method = "parameterize", args = java.sql.Statement.class) })
public class MyFirstPlugin implements Interceptor {

	/*
	 * intercept：拦截 
	 * 		拦截目标对象的目标方法的执行；
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("MyFirstPlugin......intercept" + invocation.getMethod());
		//动态改变一下这个SQL运行的参数：以前查询1号员工，实际从数据库中查询3号员工
		Object target = invocation.getTarget();
//		System.out.println("当前拦截到的对象: " + target);
		//拿到：StatementHandle里面的==》ParameterHandle==》parameterObject
		//拿到target的元数据，操作这个对象，就能拿到这个对象的所有属性值
//		MetaObject metaObject = SystemMetaObject.forObject(target);
//		Object value = metaObject.getValue("parameterHandler.parameterObject");
//		System.out.println("sql语句用的参数是:  " + value);
		//修改sql语句用的参数
//		metaObject.setValue("parameterHandler.parameterObject", 11); 
		// 执行目标方法
		Object proceed = invocation.proceed();
		// 返回执行后的返回值
		return proceed;
	}

	/*
	 * plugin: 包装目标对象：包装：为目标对象创建一个代理对象
	 */
	@Override
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		System.out.println("MyFirstPlugin......plugin:mybatis将要包装的对象" + target);
		// 我们可以借助Plugin的wrap方法来使用当前Interceptor包装我们目标对象
		Object wrap = Plugin.wrap(target, this);
		// 返回为当前target创建的动态代理
		return wrap;
	}

	/*
	 * setProperties： 将插件注册时的property设置进来
	 * 
	 */
	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		System.out.println("插件配置的信息" + properties);
	}

}
