package common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

public class UserSignIn extends ActionSupport{
	private Map<String, Object> dataMap = new HashMap<String, Object>();;
	private Connection conn = new SqlConnection().connectDataBase();
	private OpreationInfo info;
	private UserInfo user=new UserInfo();
	public String signIn() {
		try {	
			String name=getUserName();
			if(name==null) {
				dataMap.put("error", "password incorrect or user doesn't exist");
				return SUCCESS;
			}else {
				String sql="insert into "+name+"_tab"+"(phone_num,name,type,op_time,device,location,message_content) values(" ;
				sql=sql+dealInfo(info.getPhoneNum())+","+dealInfo(name)+","+dealInfo(info.getType())+","
				+dealInfo(info.getOp_time())+","+dealInfo(info.getDevice())+","+dealInfo(info.getLocation())
				+","+dealInfo(info.getMessageContent())+")";
				Statement stmt=conn.createStatement();
				System.out.println(sql);
				stmt.executeUpdate(sql);
				user.setPassword(info.getMessageContent());
				user.setName(name);
				user.setPhoneNum(info.getPhoneNum());
				dataMap.put("success", user);
				return SUCCESS;
			}
		} catch (SQLException e) {
			dataMap.put("error", "service exception");
			e.printStackTrace();
			return SUCCESS;
		}
		
		
	}
	private String getUserName() throws SQLException{
		String sql="select password,name from user where phone_num="+dealInfo(info.getPhoneNum());
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery(sql);
		while(rs.next()) {
			if(rs.getString(1).equals(info.getMessageContent()))
				return rs.getString(2);
			else
				return null;
		}
		return null;
	}
	private String dealInfo(String info) {
		return "'" + info + "'";
	}
	public OpreationInfo getInfo() {
		return info;
	}

	public void setInfo(OpreationInfo info) {
		this.info = info;
	}
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
}
