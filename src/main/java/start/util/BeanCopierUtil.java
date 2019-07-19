package start.util;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cglib.beans.BeanCopier;


public class BeanCopierUtil {
	
    private static final Map<String, BeanCopier> BEAN_COPIERS_CACHE = new ConcurrentHashMap<String, BeanCopier>();

    public static void copy(Object srcObj, Object destObj) {
        String key = getCacheKey(srcObj.getClass(), destObj.getClass());
        BeanCopier copier = BEAN_COPIERS_CACHE.get(key);
        if (Objects.isNull(copier)) {
            copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);
            BeanCopier old = BEAN_COPIERS_CACHE.putIfAbsent(key, copier);
            if(Objects.nonNull(old)) {
            	copier = old;
            }
        } 
        copier.copy(srcObj, destObj, null);
    }

    private static String getCacheKey(Class<?> srcClazz, Class<?> destClazz) {
        return srcClazz.getName() + "_" + destClazz.getName();

    }
}