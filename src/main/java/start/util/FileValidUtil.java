package start.util;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Sets;

import cn.hutool.core.io.FileTypeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 1
 * @Date 2019/8/6 16:38
 **/
@Slf4j
public class FileValidUtil {

    private static final Set<String> imageType = Sets.newHashSet("jpg","png","gif","tif","bmp");

    /**
     * 根据文件头判断 获取文件类型
     *
     **/
    public static String getFileTypeByHeader(MultipartFile multipartFile) {
        String type = null;
        try {
            type = FileTypeUtil.getType(multipartFile.getInputStream());
        } catch (Exception e) {
            log.error("FileValidUtil--解析文件类型异常",e);
        }

        return type;
    }

    /**
     * 根据文件头判断 是否是限定的图片类型
     *
    **/       
    public static Boolean isImageHeader(MultipartFile multipartFile) {

        String fileType = getFileTypeByHeader(multipartFile);
        if(StringUtils.isNotBlank(fileType) && imageType.contains(fileType)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
