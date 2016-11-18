package cn.com.titans.tcsmp.nettyService;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import cn.com.titans.tcsmp.nettyService.NettyServer;

public class NettyServerStart implements ApplicationListener<ContextRefreshedEvent> {


	private NettyServer server;

	public NettyServer getServer() {
		return server;
	}

	public void setServer(NettyServer server) {
		this.server = server;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("server is running……");

	}
}