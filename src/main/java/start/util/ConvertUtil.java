package start.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

public class ConvertUtil {


    /**
     * list 中获取不重复的 属性
     *
     * @param list
     * @param mapper 
     * @param <T>
     * @param <R>
     * @return
     */

    public static  <T, R> List<R> getDistinctProperty(List<T> list, Function<? super T, ? extends R> mapper) {
        if (CollectionUtils.isNotEmpty(list)) {
            return list.stream().map(mapper).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        } else {
            return Collections.EMPTY_LIST;
        }

    }

    /**
     * list 转换为map
     *
     * @param list
     * @param keyMapper 
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Map<R, T> convertListToMap(List<T> list, Function<? super T, ? extends R> keyMapper) {
        if (CollectionUtils.isNotEmpty(list)) {
            return list.stream().collect(Collectors.toMap(keyMapper, t -> t));
        } else {
            return Collections.EMPTY_MAP;
        }

    }

    public static <T, R1,R2> Map<R1, R2> convertListToMap(List<T> list, Function<? super T, ? extends R1> keyMapper
            ,Function<? super T, ? extends R2> valueMapper) {
        if (CollectionUtils.isNotEmpty(list)) {
            return list.stream().collect(Collectors.toMap(keyMapper, valueMapper));
        } else {
            return Collections.EMPTY_MAP;
        }

    }
}
