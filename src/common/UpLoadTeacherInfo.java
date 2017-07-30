package common;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

public class UpLoadTeacherInfo extends ActionSupport{
	private Map<String, Object> dataMap = new HashMap<String, Object>();;
	private Connection conn = new SqlConnection().connectDataBase();
	private TeacherInfo teacherInfo;
	public String upLoadTeacherInfo() {
		try {
			upLoad();
			dataMap.put("success", teacherInfo);
			closeDataBase(conn);
			return SUCCESS;
		} catch (SQLException e) {
			dataMap.put("error", "service Exception");
			e.printStackTrace();
			closeDataBase(conn);
			return SUCCESS;
		}
		
	}
	
	// 关闭数据库
	private void closeDataBase(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String dealInfo(String info) {
		return "'" + info + "'";
	}
	
	private void upLoad() throws SQLException {
		Statement stmt = conn.createStatement();
		String sql="insert into teacher_info (phone_num,user_name,true_name,sex,age,collage,grade,major,teach_grade,subjects,salary,specialty,teach_exp,note) values (";
		sql=sql+dealInfo(teacherInfo.getPhoneNum())+","+dealInfo(teacherInfo.getUserName())+","+dealInfo(teacherInfo.getTrueName())+",";
		sql=sql+teacherInfo.getSex()+","+teacherInfo.getAge()+","+dealInfo(teacherInfo.getCollage())+","+dealInfo(teacherInfo.getGrade())+",";
		sql=sql+dealInfo(teacherInfo.getMajor())+","+dealInfo(teacherInfo.getTeachGrade())+","+dealInfo(teacherInfo.getSubjects())+",";
		sql=sql+dealInfo(teacherInfo.getSalary())+","+dealInfo(teacherInfo.getSpecialty())+","+dealInfo(teacherInfo.getTeachExp())+","+dealInfo(teacherInfo.getNote())+")";
		stmt.executeLargeUpdate(sql);
	}
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public TeacherInfo getTeacherInfo() {
		return teacherInfo;
	}

	public void setTeacherInfo(TeacherInfo teacherInfo) {
		this.teacherInfo = teacherInfo;
	}

}
