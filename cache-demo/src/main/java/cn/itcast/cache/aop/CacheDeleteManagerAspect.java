package cn.itcast.cache.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.jarvis.cache.aop.aspectj.AspectjAopInterceptor;

/**
 * ClassName: CacheDeleteManagerAspect  
 * (切面配置，删除缓存)
 * @author zhangtian  
 * @version
 */
@Aspect
@Component
// order 参数控制 aop通知的优先级，值越小，优先级越高 ，在事务提交后删除缓存
@Order(value=1000)
public class CacheDeleteManagerAspect {
	
	@Autowired
	private AspectjAopInterceptor aspectjAopInterceptor ;

	// 拦截Dao中的带有CacheDelete注解的方法
	@Pointcut(value="execution(* cn.itcast.cache.dao..*.*(..)) && @annotation(com.jarvis.cache.annotation.CacheDelete)")
	public void cacheDeletePointCut() {
		
	}
	
	// 删除缓存信息
	@AfterReturning(value="cacheDeletePointCut()", returning="retVal")
	public void cacheDeleteAfter(JoinPoint aopProxyChain, Object retVal) {
		System.out.println("===================== 删除缓存入口 =====================");
		aspectjAopInterceptor.checkAndDeleteCache(aopProxyChain, retVal);
	}
}
