package top.by.file.vo;

import java.io.Serializable;

public class FileList implements Serializable {

	private int id;
	
	private String fileName;
	
	private String fileType;
	
	private String fileRemark;
	
	private String fileContent;
	
	private String fileVersion;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileRemark() {
		return fileRemark;
	}

	public void setFileRemark(String fileRemark) {
		this.fileRemark = fileRemark;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[id = " + id + ", fileName = " + fileName + ", fileType = " + fileType + ", fileRemark = " + fileRemark + ", fileContent = " + fileContent + ", fileVersion = " + fileVersion + "]";
	}
}
