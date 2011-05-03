package mybridge.filter;
 
 
import java.util.List;

import mybridge.protocal.packet.*;

public interface Filter {
	/**
	 * 执行过滤器，并返回要发送给client的packet list
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	public List<Packet> doFilter(String sql) throws Exception;
}