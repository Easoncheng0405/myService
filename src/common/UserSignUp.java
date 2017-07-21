package common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

public class UserSignUp extends ActionSupport {
	private Map<String, Object> dataMap = new HashMap<String, Object>();;
	private Connection conn = new SqlConnection().connectDataBase();
	private OpreationInfo info;

	public String signUp() {
		try {
			if (userExist()) {
				dataMap.put("error", "username or phoneNum already exist");
				return SUCCESS;
			}
			String sql = "create table " + info.getName() + "_tab" + "(\r\n"
					+ "	 id                int(10) primary key not null auto_increment,\r\n"
					+ "	 phone_num         varchar(15)  NOT NULL,\r\n"
					+ "	 name              varchar(20)  NOT NULL,\r\n"
					+ "	 type              varchar(10)  NOT NULL,\r\n"
					+ "	 op_time           varchar(20)	NOT NULL,\r\n" + "	 device            varchar(20),\r\n"
					+ "	 location          varchar(30),\r\n" + "	 message_type      varchar(5),\r\n"
					+ "	 message_content   varchar(100),\r\n" + "	 message_to        varchar(20)\r\n" + ");";
			System.out.println(sql);
			update(sql);
			sql = "insert into " + info.getName() + "_tab"
					+ " (phone_num,name,type,op_time,device,location,message_content) values(";
			sql = sql + dealInfo(info.getPhoneNum()) + "," + dealInfo(info.getName()) + "," + dealInfo(info.getType())
					+ "," + dealInfo(info.getOp_time()) + "," + dealInfo(info.getDevice()) + ","
					+ dealInfo(info.getLocation()) + "," + dealInfo(info.getMessageContent()) + ")";
			System.out.println(sql);
			update(sql);
			sql = "insert into user (phone_num,name,password,signup_time,type) values (";
			sql = sql + dealInfo(info.getPhoneNum()) + "," + dealInfo(info.getName()) + ","
					+ dealInfo(info.getMessageContent()) + "," + dealInfo(info.getOp_time()) + ","
					+ dealInfo(info.getType()) + ")";
			System.out.println(sql);
			update(sql);
			closeDataBase(conn);
			dataMap.put("success", "user signup success");
			return SUCCESS;
		} catch (SQLException e) {
			closeDataBase(conn);
			e.printStackTrace();
			dataMap.put("error", "create user table failed");
			return SUCCESS;
		}
	}

	private boolean userExist() throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "select * from user where phone_num=" + dealInfo(info.getPhoneNum()) + " or name="
				+ dealInfo(info.getName());
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next())
			return true;
		else
			return false;
	}

	public void update(String sql) throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
	}

	public OpreationInfo getInfo() {
		return info;
	}

	public void setInfo(OpreationInfo info) {
		this.info = info;
	}

	private String dealInfo(String info) {
		return "'" + info + "'";
	}

	private void closeDataBase(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}
}
