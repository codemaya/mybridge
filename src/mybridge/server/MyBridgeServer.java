package mybridge.server;

import mybridge.protocal.impl.Protocal;
import mybridge.storyge.DefaultCmdManager;

import org.apache.log4j.PropertyConfigurator;
import xnet.core.server.*;

public class MyBridgeServer {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Config config = new Config();
		config.session = MyBridgeSession.class;
		PropertyConfigurator.configure("./conf/log4j.properties");
		config.threadNum = 1;
		config.port = 10000;
		config.rTimeout = 0;
		config.wTimeout = 0;
		config.ip = "0.0.0.0";
		config.keepalive = true;
		config.maxConnection = 1000;
		Server server = new Server(config);
		Protocal.handleClass = DefaultCmdManager.class;
		server.run();
	}

}
