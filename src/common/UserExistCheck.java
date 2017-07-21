package common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

public class UserExistCheck extends ActionSupport {
	private Map<String, Object> dataMap = new HashMap<String, Object>();;
	private Connection conn = new SqlConnection().connectDataBase();
	private OpreationInfo info;
	public String check() {

		try {
			if(userNameAlreadyExist(info.getName()) ){
				dataMap.put("error", "用户名已存在");
				closeDataBase(conn);
				return SUCCESS;
			}
			if(userPhoneNumAlreadyExist(info.getPhoneNum())) {
				dataMap.put("error", "此号码已注册");
				closeDataBase(conn);
				return SUCCESS;
			}
			dataMap.put("success", "user doesn't exist");
			closeDataBase(conn);
			return SUCCESS;
		} catch (SQLException e) {
			dataMap.put("error", "服务器异常");
			closeDataBase(conn);
			e.printStackTrace();
			return SUCCESS;
		}
	}
	private boolean userNameAlreadyExist(String userName) throws SQLException {
		String sql="select * from user where name="+"'"+userName+"'";
		Statement stmt = conn.createStatement();
		ResultSet rs=stmt.executeQuery(sql);
		if(rs.next())
			return true;
		return false;																																												
		
	}	
	private boolean userPhoneNumAlreadyExist(String phoneNum) throws SQLException {
		String sql="select * from user where phone_num="+"'"+phoneNum+"'";
		Statement stmt = conn.createStatement();
		ResultSet rs=stmt.executeQuery(sql);
		if(rs.next())
			return true;
		return false;
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
	private void closeDataBase(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
