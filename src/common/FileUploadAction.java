package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FileUploadAction extends ActionSupport {
	private File img; // 上传的文件
	private String filename; // 文件名称

	public String execute() throws Exception {
		System.out.println("被访问了!");
		String realpath = ServletActionContext.getServletContext().getRealPath("/filename");
		// D:\apache-tomcat-6.0.18\webapps\struts2_upload\images
		System.out.println("realpath: " + realpath);
		if (img != null) {
			File savefile = new File(new File("/home/chengjie/servicePics/"), filename);
			if (!savefile.getParentFile().exists())
				savefile.getParentFile().mkdirs();
			FileUtils.copyFile(img, savefile);
			ActionContext.getContext().put("message", "文件上传成功");
		} else {
			System.out.println("空文件");
		}
		return "success";
	}

	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
