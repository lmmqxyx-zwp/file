package top.by.file.vo;

import java.io.Serializable;

/**
 * 中国省市区县
 * 
 * <p>Title: China.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: www.iceos.top</p>
 *
 * ----------------------------------------
 * ----------------------------------------
 * @author zwp
 * @date 2018年12月24日
 *
 * @version 1.0
 */
public class China implements Serializable {

	private Integer id;
	private String name;
	private Integer parentId;
	private String shortName;
	private Integer levelType;
	private String cityCode;
	private String zipCode;
	private String managerName;
	private Float lng;
	private Float lat;
	private String pinyin;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Integer getLevelType() {
		return levelType;
	}

	public void setLevelType(Integer levelType) {
		this.levelType = levelType;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	@Override
	public String toString() {
		return  "	id => " + id + "\n" + 
				"	name => " + name + "\n" + 
				"	parentId => " + parentId + "\n" + 
				"	shortName => " + shortName + "\n" + 
				"	levelType => " + levelType + "\n" + 
				"	cityCode => " + cityCode + "\n" + 
				"	zipCode => " + zipCode + "\n" + 
				"	managerName => " + managerName + "\n" + 
				"	lng => " + lng + "\n" + 
				"	lat => " + lat + "\n" + 
				"	pinyin => " + pinyin + "\n";
	}

}
