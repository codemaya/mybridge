package mybridge.storyge;
 
 
import java.util.List;

import mybridge.protocal.packet.*;

public interface Commond {
	/**
	 * 初始化
	 */
	public void init();
	/**
	 * 执行命令，并返回要发送给client的packet list
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	public List<Packet> doCommand(PacketCommand cmd) throws Exception;
	/**
	 * 会话结束
	 */
	public void destrory();
	/**
	 * 设置当前连接编码
	 * @param charsetIndex
	 */
	public void setCharset(String charset);
	/**
	 * 获取连接编码
	 */
	public String getCharSet();
}
