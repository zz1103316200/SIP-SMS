package Message;
import java.io.Serializable;


public class Message implements Serializable{
	// ��Ϣ����
	private MsgType type;
	// ��Ϣ��
	private Object body;
	public Message(MsgType type, Object body)
	{
		this.type = type;
		this.body = body;
	}
	
	public MsgType getType()
	{
		return type;
	}
	public void setType(MsgType t)
	{
		this.type = type;
	}
	public Object getBody()
	{
		return body;
	}
	public void setBody(Object obj)
	{
		body = obj;
	}
}