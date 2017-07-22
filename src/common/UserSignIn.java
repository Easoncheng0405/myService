package common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opensymphony.xwork2.ActionSupport;

public class UserSignIn extends ActionSupport {
	private Map<String, Object> dataMap = new HashMap<String, Object>();;
	private Connection conn = new SqlConnection().connectDataBase();
	private OpreationInfo info;
	private UserInfo user = new UserInfo();

	public String signIn() {
		try {
			String name = null,phoneNum=null;
			if (isPhoneNum(info.getMessageTo())) {
				phoneNum=info.getMessageTo();
				info.setPhoneNum(phoneNum);
				name=getUserName();
				
				info.setName(name);
			} else {
				name=info.getMessageTo();
				info.setName(name);
				phoneNum=getPhoneNum();
				info.setPhoneNum(phoneNum);
			}
			if (name == null || phoneNum == null) {
				dataMap.put("error", "password incorrect or user doesn't exist");
				closeDataBase(conn);
				return SUCCESS;
			} else {
				String sql = "insert into " + name + "_tab"
						+ "(phone_num,name,type,op_time,device,location,message_content) values(";
				sql = sql + dealInfo(info.getPhoneNum()) + "," + dealInfo(name) + "," + dealInfo(info.getType()) + ","
						+ dealInfo(info.getOp_time()) + "," + dealInfo(info.getDevice()) + ","
						+ dealInfo(info.getLocation()) + "," + dealInfo(info.getMessageContent()) + ")";
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				user.setPassWord(info.getMessageContent());
				user.setUserName(name);
				user.setPhoneNum(info.getPhoneNum());
				dataMap.put("success", user);
				closeDataBase(conn);
				return SUCCESS;
			}
		} catch (SQLException e) {
			dataMap.put("error", "service exception");
			e.printStackTrace();
			closeDataBase(conn);
			return SUCCESS;
		}

	}

	private String getUserName() throws SQLException {
		String sql = "select password,name from user where phone_num=" + dealInfo(info.getPhoneNum());
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			if (rs.getString(1).equals(info.getMessageContent()))
				return rs.getString(2);
			else
				return null;
		}
		return null;
	}

	private String getPhoneNum() throws SQLException {
		String sql = "select password,phone_num from user where name=" + dealInfo(info.getName());
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			if (rs.getString(1).equals(info.getMessageContent())) {
				return rs.getString(2);
			}
			else
				return null;
		}
		return null;
	}

	private String dealInfo(String info) {
		return "'" + info + "'";
	}

	private boolean isPhoneNum(String phoneNum) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(phoneNum);
		return m.matches();
	}

	private void closeDataBase(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
