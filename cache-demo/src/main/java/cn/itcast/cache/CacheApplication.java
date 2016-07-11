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
	
	// =================================== 基本配置区域  ===============================
	/*
	    threadCnt :处理自动加载队列的线程数量,默认值为10；
	    maxElement ： 自动加载队列中允许存放的最大容量，默认值为20000；
	    printSlowLog ： 是否打印比较耗时的请求，默认值：true；
	    slowLoadTime ：当请求耗时超过此值时，记录目录（printSlowLog=true 时才有效），单位：毫秒；默认值500；
	    sortType： 自动加载队列排序算法, 0：按在Map中存储的顺序（即无序）；1 ：越接近过期时间，越耗时的排在最前；2：根据请求次数，倒序排序，请求次数越多，说明使用频率越高，造成并发的可能越大。更详细的说明，请查看代码com.jarvis.cache.type.AutoLoadQueueSortType
	    checkFromCacheBeforeLoad： 加载数据之前去缓存服务器中检查，数据是否快过期，如果应用程序部署的服务器数量比较少，设置为false, 如果部署的服务器比较多，可以考虑设置为true；
	    autoLoadPeriod： 单个线程中执行自动加载的时间间隔, 此值越小，遍历自动加载队列频率起高，对CPU会越消耗CPU；
	    functions： 注册表达式中用到的自定义函数；
	    refreshThreadPoolSize ： 异步刷新缓存线程池的 corePoolSize，默认值：2
	    refreshThreadPoolMaxSize ： 异步刷新缓存线程池的 maximumPoolSize ，默认值：20；
	    refreshThreadPoolkeepAliveTime ： 异步刷新缓存线程池的 keepAliveTime。默认值20，单位分钟；
	    refreshQueueCapacity ： 异步刷新缓存队列容量，默认值：2000；	
	 */
	/**
	 *  getAutoLoadConfig:(AutoLoadConfig). 
	 *  AutoLoadConfig配置项  可以从配置文件中读取
	 *  @return_type:AutoLoadConfig
	 *  @author zhangtian  
	 *  @return
	 */
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
	
	// ============================= 数据序列化区域  可自定义 ======================
	// 通过实现com.jarvis.cache.serializer.ISerializer来扩展（比如：Kryo和FST等）
	/**
	 *  getHessianSerializer:(HessianSerializer序列化).
	 *  本例使用 
	 *  基于Hessian2序列化工具
	 *  @return_type:HessianSerializer
	 *  @author zhangtian  
	 *  @return
	 */
	@Bean
	public HessianSerializer getHessianSerializer() {
		return new HessianSerializer() ;
	}
	
	/**
	 *  getFastjsonSerializer:(FastJson序列化). 
	 *  本例未使用
	 *  基于Fastjson序列化工具
	 *  使用Fastjson时需要注意：返回值中如果是泛型的话，需要指明具体的类型，比如：List，如果是直接返回List则会出错。
	 *  @return_type:FastjsonSerializer
	 *  @author zhangtian  
	 *  @return
	 */
	@Bean
	public FastjsonSerializer getFastjsonSerializer() {
		return new FastjsonSerializer() ;
	}
	
	/**
	 *  getJdkSerializer:(Jdk序列化与反序列化).
	 *  本例未使用 
	 *  JDK自带序列化工具
	 *  @return_type:JdkSerializer
	 *  @author zhangtian  
	 *  @return
	 */
	@Bean
	public JdkSerializer getJdkSerializer() {
		return new JdkSerializer() ;
	}
	
	/**
	 *  getCompressorSerializer:(压缩序列化). 
	 *  本例未使用
	 *  希望对比较长的数据进行压缩处理后再传到分布式缓存服务器
	 *  支持GZIP，BZIP2，XZ，PACK200，DEFLATE，等几种压缩算法（默认使用GZIP）
	 *  @return_type:CompressorSerializer
	 *  @author zhangtian  
	 *  @param hessianSerializer
	 *  @return
	 */
	@Bean
	public CompressorSerializer getCompressorSerializer(HessianSerializer hessianSerializer) {
		return new CompressorSerializer(hessianSerializer) ;
	}
	
	@Bean
	public SpringELParser getSpringELParser() {
		return new SpringELParser() ;
	}
	
	// =============================== 缓存配置区域  配置CurrentHashMap缓存 ======================================
	@Bean(initMethod="start", destroyMethod="destroy")
	public CachePointCut getCachePointCut(AutoLoadConfig autoLoadConfig, 
						HessianSerializer hessianSerializer,
						SpringELParser springELParser) {
		CachePointCut cachePointCut = new CachePointCut(autoLoadConfig, hessianSerializer, springELParser) ;
		cachePointCut.setNamespace("test_zhangtian");
		return cachePointCut ;
	}
	
	// ========================= AOP配置  拦截注解以及自定义拦截缓存配置项   ============================
	@Bean
	public AspectjAopInterceptor getAspectjAopInterceptor(CachePointCut cacheManager) {
		AspectjAopInterceptor aspectjAopInterceptor = new AspectjAopInterceptor() ;
		aspectjAopInterceptor.setCacheManager(cacheManager);
		return aspectjAopInterceptor ;
	}
	
	// ============================ 控制台监控缓存  注册Servlet ============================
	@Bean
	public ServletRegistrationBean getManagementServletRegistrationBean() {
		// 注册控制套访问的Servlet
		ServletRegistrationBean managementServlet = new ServletRegistrationBean(new AdminServlet(), "/cacheadmin") ;
		// 登录用户名，密码，默认admin
		managementServlet.addInitParameter("user", "admin");
		managementServlet.addInitParameter("password", "admin");
		// cacheManagerConfig ：用于获取系统中AbstractCacheManager的子类。从而读取自动加载队列中的数据
		managementServlet.addInitParameter("cacheManagerConfig", "com.jarvis.cache.admin.servlet.SpringCacheManagerConfig");
	
		managementServlet.setLoadOnStartup(1);
		return managementServlet ;
	}
	
	// =========================== 程序启动入口 =============================
	public static void main(String[] args) {
		SpringApplication.run(CacheApplication.class, args) ;
	}
}
