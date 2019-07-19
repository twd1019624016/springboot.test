//package start.security;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class CustomInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
//
//	@Autowired
//	private SysResourceRepostory sResourceVODao;
//
//	@Autowired
//	private SysRoleRespository sRoleVODao;
//
//	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
//
//	@PostConstruct
//	private void loadResourceDefine() {
//		List<SysRole> list = sRoleVODao.findAll();
//		List<String> query = new ArrayList<String>();
//		if (list != null && list.size() > 0) {
//			for (SysRole sr : list) {
//				// String name = sr.get("name")
//				Object value = sr.getName();
//				String name = String.valueOf(value);
//				query.add(name);
//			}
//		}
//
//		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
//
//		for (String auth : query) {
//			ConfigAttribute ca = new SecurityConfig(auth);
//			// List<Map<String,Object>> query1 =
//			// sResourceVODao.findByRoleName(auth);
//			List<String> query1 = new ArrayList<String>();
//			List<SysResource> list1 = sResourceVODao.findByResourceString(auth);
//			if (list1 != null && list1.size() > 0) {
//				for (SysResource sr : list1) {
//					Object value = sr.getResourceString();
//					String url = String.valueOf(value);
//					query1.add(url);
//				}
//			}
//			for (String res : query1) {
//				String url = res;
//
//				if (resourceMap.containsKey(url)) {
//
//					Collection<ConfigAttribute> value = resourceMap.get(url);
//					value.add(ca);
//					resourceMap.put(url, value);
//				} else {
//					Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
//					atts.add(ca);
//					resourceMap.put(url, atts);
//				}
//
//			}
//		}
//	}
//
//	@Override
//	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//		 FilterInvocation filterInvocation = (FilterInvocation) object;  
//	        if (resourceMap == null) {  
//	            loadResourceDefine();  
//	        }  
//	        Iterator<String> ite = resourceMap.keySet().iterator();  
//	        while (ite.hasNext()) {  
//	            String resURL = ite.next();  
//	             RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);  
//	                if(requestMatcher.matches(filterInvocation.getHttpRequest())) {  
//	                return resourceMap.get(resURL);  
//	            }  
//	        }  
//	  
//	        return null;  
//		
//	}
//
//	@Override
//	public Collection<ConfigAttribute> getAllConfigAttributes() {
//		return new ArrayList<ConfigAttribute>();  
//	}
//
//	@Override
//	public boolean supports(Class<?> clazz) {
//		
//		return true;
//	}
//
//}
