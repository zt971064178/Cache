package cn.itcast.cache.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jarvis.cache.aop.aspectj.AspectjAopInterceptor;


@Aspect
@Component
public class CacheAddManagerAspect {
	
	@Autowired
	private AspectjAopInterceptor aspectjAopInterceptor ;
	
	@Pointcut(value="execution(public !void cn.itcast.cache.dao..*.*(..)) && @annotation(com.jarvis.cache.annotation.Cache)")
	public void cacheAddPointCut() {
		
	}
	
	@Around(value="cacheAddPointCut()")
	public Object cacheAddAround(ProceedingJoinPoint p) throws Throwable {
		return aspectjAopInterceptor.checkAndProceed(p) ;
	}
	
}
