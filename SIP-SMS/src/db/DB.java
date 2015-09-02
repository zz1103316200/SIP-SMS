package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB {
	static private DB instance; // The single instance
	private static Connection con;
	private String user = DBProperty.userName;
	private String password = DBProperty.userPass;
	private static final String drivername = DBProperty.driver;
	private static final String url = DBProperty.url;
	
	//���ݿ����Ӷ���
	private Connection conn=null;
		//���ݿ�Ԥ�������
	private PreparedStatement ps=null;
		//�����
	private ResultSet rs=null;

	static public DB getInstance() {
		if (instance == null) {
			instance = new DB();
		}
		return instance;
	}

	public DB() {
		try {
			Class.forName(drivername);
		} catch (ClassNotFoundException e1) {
			System.out.println("driver����");
		}
		try {
			con = DriverManager.getConnection(url, user, password);
			System.out.println("���ӳɹ�");
		} catch (SQLException e) {
			System.out.println("���ӳ���");
		}
	}

	public Connection getConnection() {
		return con;
	}
	
	public void close()
	{
		if(rs!=null)
		{
			try{
				rs.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(ps!=null)
		{
			try{
				ps.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void closeAll()
	{
		if(rs!=null)
		{
			try{
				rs.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(ps!=null)
		{
			try{
				ps.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(conn!=null)
		{
			try{
				conn.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**  
	 * @param sql���ݿ����(����ɾ����) ���      
     * @param pras�����б��ɴ����ɲ���������ΪNULL����������ʽ���ڣ�  
     * @return ������Ӱ�춼����  
     */
	public int update(String sql,String... pras){  
        int resu=0;  
        //conn=getConn();  
        try {  
            ps=con.prepareStatement(sql);  
            for(int i=0;i<pras.length;i++){  
                ps.setString(i+1,pras[i]);  
            }  
            resu=ps.executeUpdate();  
        } catch (SQLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } 
        //�ͷ���Դ
        if(ps!=null)
		{
			try{
				ps.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
        return resu;  
    }  
	/**  
     * @param sql���ݿ��ѯ���  
     * @param pras�����б��ɴ����ɲ���������ΪNULL����������ʽ���ڣ�  
     * @return ���ؽ����  
     */ 
	//�������� �����ŵ���close�رճ�conn����Դ
    public ResultSet query(String sql,String... pras){  
        //conn=getConn();  
        try {  
            ps=con.prepareStatement(sql);  
  
            if(pras!=null)  
                for(int i=0;i<pras.length;i++){  
                    ps.setString(i+1, pras[i]);  
                }  
            rs=ps.executeQuery();  
        } catch (SQLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return rs;  
    }  
    public void createTable(String sql)
    {
    	Statement stat =null;
    	try
    	{
    		stat = con.createStatement();
    		stat.execute(sql);
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	//�ر���Դ
    	if(stat !=null)
    	{
    		try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    public void printResultSetElement() throws SQLException
    {
    	if(rs!=null)
    	{
    		while(rs.next())
    		{
    			System.out.println(rs.getString(1));
    		}
    	}
    }

    public static void main(String[] args){
    	DB db = DB.getInstance();
    	db.getConnection();
    }
    
}
