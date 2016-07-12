package cn.itcast.cache.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jarvis.cache.aop.aspectj.AspectjAopInterceptor;

/**
 * ClassName: CacheAddManagerAspect  
 * (切面配置，增加与查询缓存)
 * @author zhangtian  
 * @version
 */
@Aspect
@Component
public class CacheAddManagerAspect {
	
	@Autowired
	private AspectjAopInterceptor aspectjAopInterceptor ;
	
	// 拦截Dao中的带有Cache注解的方法
	@Pointcut(value="execution(public !void cn.itcast.cache.dao..*.*(..)) && @annotation(com.jarvis.cache.annotation.Cache)")
	public void cacheAddPointCut() {
		
	}
	
	// 查询缓存未命中则查询数据库并写入缓存
	@Around(value="cacheAddPointCut()")
	public Object cacheAddAround(ProceedingJoinPoint p) throws Throwable {
		System.out.println("===================== 增加缓存入口 =====================");
		return aspectjAopInterceptor.checkAndProceed(p) ;
	}
	
}
