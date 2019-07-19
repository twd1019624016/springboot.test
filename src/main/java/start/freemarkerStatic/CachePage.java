package start.freemarkerStatic;


import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

public class CachePage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private byte[] content;
	
	public void process(HttpServletResponse response) throws Exception {
		response.getOutputStream().write(content);
		return;

	}

	public CachePage(byte[] byteArray) {
		super();
		this.content = byteArray;
	}
	
	
}
