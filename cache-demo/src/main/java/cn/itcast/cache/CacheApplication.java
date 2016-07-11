package cn.itcast.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.jarvis.cache.admin.servlet.AdminServlet;
import com.jarvis.cache.aop.aspectj.AspectjAopInterceptor;
import com.jarvis.cache.map.CachePointCut;
import com.jarvis.cache.script.SpringELParser;
import com.jarvis.cache.serializer.CompressorSerializer;
import com.jarvis.cache.serializer.FastjsonSerializer;
import com.jarvis.cache.serializer.HessianSerializer;
import com.jarvis.cache.serializer.JdkSerializer;
import com.jarvis.cache.to.AutoLoadConfig;

@SpringBootApplication
public class CacheApplication {
	
	@Bean
	public AutoLoadConfig getAutoLoadConfig() {
		AutoLoadConfig autoLoadConfig = new AutoLoadConfig() ;
		autoLoadConfig.setThreadCnt(10);
		autoLoadConfig.setMaxElement(20000);
		autoLoadConfig.setPrintSlowLog(true);
		autoLoadConfig.setSlowLoadTime(500);
		autoLoadConfig.setSortType(1);
		autoLoadConfig.setCheckFromCacheBeforeLoad(true);
		autoLoadConfig.setAutoLoadPeriod(50);
		return autoLoadConfig ;
	}
	
	@Bean(initMethod="start", destroyMethod="destroy")
	public CachePointCut getCachePointCut(AutoLoadConfig autoLoadConfig, 
						HessianSerializer hessianSerializer,
						SpringELParser springELParser) {
		CachePointCut cachePointCut = new CachePointCut(autoLoadConfig, hessianSerializer, springELParser) ;
		cachePointCut.setNamespace("test_zhangtian");
		return cachePointCut ;
	}
	
	@Bean
	public HessianSerializer getHessianSerializer() {
		return new HessianSerializer() ;
	}
	
	@Bean
	public FastjsonSerializer getFastjsonSerializer() {
		return new FastjsonSerializer() ;
	}
	
	@Bean
	public JdkSerializer getJdkSerializer() {
		return new JdkSerializer() ;
	}
	
	@Bean
	public CompressorSerializer getCompressorSerializer(HessianSerializer hessianSerializer) {
		return new CompressorSerializer(hessianSerializer) ;
	}
	
	@Bean
	public SpringELParser getSpringELParser() {
		return new SpringELParser() ;
	}
	
	// ========================= AOP配置 ============================
	@Bean
	public AspectjAopInterceptor getAspectjAopInterceptor(CachePointCut cacheManager) {
		AspectjAopInterceptor aspectjAopInterceptor = new AspectjAopInterceptor() ;
		aspectjAopInterceptor.setCacheManager(cacheManager);
		return aspectjAopInterceptor ;
	}
	
	// ============================ 注册SServlet ============================
	@Bean
	public ServletRegistrationBean getManagementServletRegistrationBean() {
		ServletRegistrationBean managementServlet = new ServletRegistrationBean(new AdminServlet(), "/cacheadmin") ;
		managementServlet.addInitParameter("user", "admin");
		managementServlet.addInitParameter("password", "admin");
		managementServlet.addInitParameter("cacheManagerConfig", "com.jarvis.cache.admin.servlet.SpringCacheManagerConfig");
	
		managementServlet.setLoadOnStartup(1);
		return managementServlet ;
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(CacheApplication.class, args) ;
	}
}
