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
	    int numberOfObjects = Integer.parseInt(args.length == 0 ? "20" : args[0]);    //����20������
	    int numberOfIteration = Integer.parseInt(args.length == 0 ? "2" : args[1]);   //����2��
	    int sleepTimeMillisBetweenIterations = Integer.parseInt(args.length == 0 ? "1000" : args[2]);  //���ʱ��1��

	    new CreateBasicJCacheWithXML().run(numberOfIteration, numberOfObjects, sleepTimeMillisBetweenIterations);

	    LOGGER.info("Exiting");
	  }

	  public void run(int numberOfIteration, int numberOfObjectPerIteration, int sleepTimeMillisBetweenIterations) throws Exception {
	    LOGGER.info("JCache testing BEGIN - Creating cache manager via XML resource");
	    String xmlClassPath = System.getProperty("jsr107.config.classpath", "demo/jsr107/ehcache-jsr107-simple.xml");

	    CachingProvider cachingProvider = Caching.getCachingProvider();

	    try (CacheManager cacheManager = cachingProvider.getCacheManager(   //�������ļ�
	      Thread.currentThread().getContextClassLoader().getResource(xmlClassPath).toURI(),
	      Thread.currentThread().getContextClassLoader())) {

	      //go over all the caches
	      for (String cacheName : cacheManager.getCacheNames()) {  //�õ��˻�������
	        LOGGER.info("----------------------------------------------------------------");
	        LOGGER.info("Cache testing with cache name {}", cacheName);
	        Cache<Long, String> myJCache = cacheManager.getCache(cacheName, Long.class, String.class);   //�õ�����

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
