package start.util;

import org.springframework.context.ApplicationContext;

public class ApplicationContextUtil {
    /**
     * 上下文对象实例
     */
    private static volatile ApplicationContext applicationContext;

    /**
     * 只允许被初始化一次
     */
    public static void setApplicationContext(ApplicationContext context){
        if(null == applicationContext) {
            synchronized (ApplicationContextUtil.class) {
                if (applicationContext == null){
                    applicationContext = context;
                }

            }
        }
    }

    /**
     * 获取applicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     * @param name
     * @return
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}