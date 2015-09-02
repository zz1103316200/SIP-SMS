package function;

public class SocketTools {
	public static final String targetIp="localhost";
	//此端口主要是web端与Client进行交互是用的：创建/删除工程、xml、java发送、部署、启动、得到日志
	public static final int serverPort = 7077;
	//此端口主要是Client与Server交互时候用到的：流调用次数、流负载信息
	public static final int infoPort = 7078;
	//此端口是启动/停止工程线程用的
	public static final int startPort = 7079;
	
	//此端口用来接收启动指令的返回信息，仅仅是Client发start/end给Server来用的
	public static final int retPort = 7080;
	
	public static final int webPort = 7081;
	
}
