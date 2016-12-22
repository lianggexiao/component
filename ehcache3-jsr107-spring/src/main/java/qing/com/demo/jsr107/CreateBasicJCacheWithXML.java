package qing.com.demo.jsr107;

import org.slf4j.Logger;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import static org.slf4j.LoggerFactory.getLogger;

public class CreateBasicJCacheWithXML extends BaseJCacheTester {
	
	  private static final Logger LOGGER = getLogger(CreateBasicJCacheWithXML.class);

	  public static void main(String[] args) throws Exception {
	    // pass in the number of object you want to generate, default is 100
	    int numberOfObjects = Integer.parseInt(args.length == 0 ? "20" : args[0]);    //测试20个对象
	    int numberOfIteration = Integer.parseInt(args.length == 0 ? "2" : args[1]);   //测试2次
	    int sleepTimeMillisBetweenIterations = Integer.parseInt(args.length == 0 ? "1000" : args[2]);  //间隔时间1秒

	    new CreateBasicJCacheWithXML().run(numberOfIteration, numberOfObjects, sleepTimeMillisBetweenIterations);

	    LOGGER.info("Exiting");
	  }

	  public void run(int numberOfIteration, int numberOfObjectPerIteration, int sleepTimeMillisBetweenIterations) throws Exception {
	    LOGGER.info("JCache testing BEGIN - Creating cache manager via XML resource");
	    String xmlClassPath = System.getProperty("jsr107.config.classpath", "demo/jsr107/ehcache-jsr107-simple.xml");

	    CachingProvider cachingProvider = Caching.getCachingProvider();

	    try (CacheManager cacheManager = cachingProvider.getCacheManager(   //读配置文件
	      Thread.currentThread().getContextClassLoader().getResource(xmlClassPath).toURI(),
	      Thread.currentThread().getContextClassLoader())) {

	      //go over all the caches
	      for (String cacheName : cacheManager.getCacheNames()) {  //拿到了缓存名称
	        LOGGER.info("----------------------------------------------------------------");
	        LOGGER.info("Cache testing with cache name {}", cacheName);
	        Cache<Long, String> myJCache = cacheManager.getCache(cacheName, Long.class, String.class);   //得到缓存

	        simpleGetsAndPutsCacheTest(myJCache, numberOfIteration, numberOfObjectPerIteration, 
	        		sleepTimeMillisBetweenIterations, new KeyValueGenerator<Long, String>() {
	          @Override
	          public Long getKey(Number k) {
	            return new Long(k.longValue());
	          }

	          @Override
	          public String getValue(Number v) {
	            return String.format("Da One %s!!", v.toString());
	          }
	        });
	      }
	    }
	    LOGGER.info("JCache testing DONE - Creating cache manager via XML resource");
	  }
}
