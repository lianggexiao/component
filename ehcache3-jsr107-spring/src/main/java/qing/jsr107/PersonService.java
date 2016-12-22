package qing.jsr107;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;

import javax.cache.CacheManager;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.stereotype.Component;

@Component
@CacheDefaults(cacheName = "people")
public class PersonService {

	 private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
	 
	 private static Map<String,Person> map = new HashMap<String,Person>();

	    //create cache
	    @Component
	    public static class CachingSetup implements JCacheManagerCustomizer{
//	      @Override
	      public void customize(CacheManager cacheManager)
	      {
	    	  
	    	  map.put("11", new Person("11", "张三", "张"));
	    	  map.put("22", new Person("22", "李四", "李"));
	    	  map.put("33", new Person("33", "王五", "王"));
	        cacheManager.createCache("people", new MutableConfiguration<Object, Object>()
	          .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(SECONDS, 30)))
	          .setStoreByValue(false)
	          .setStatisticsEnabled(true));
	      }
	    }

	    @CacheResult
	    public Person getPerson(@CacheKey String ssn){   //只有一个参数@CacheKey可以不要
	        LOGGER.info("ssn " + ssn + " not found in cache. TimeStamp: {}", new Date());
	        return map.get(ssn);
	    }
	    
	    @CachePut(cacheName = "people")
	    public void update(String ssn, @CacheValue Person updatedPerson) {
            map.put(ssn, updatedPerson);
	    }
	    
	    @CacheRemove
	    public void clearEntryFromCache(String ssn) {
	    	
	    }
	    
	    @CacheRemoveAll
	    public void clearCache() {
	    	
	    }
	    
}
