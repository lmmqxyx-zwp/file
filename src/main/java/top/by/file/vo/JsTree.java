package top.by.file.vo;

import java.util.List;
import java.util.Map;
// Alternative format of the node (id & parent are required)
// {
// id          : "string" // required
// parent      : "string" // required
// text        : "string" // node text
// icon        : "string" // string for custom
// state       : {
//  opened    : boolean  // is the node open
//  disabled  : boolean  // is the node disabled
//  selected  : boolean  // is the node selected
// },
// li_attr     : {}  // attributes for the generated LI node
// a_attr      : {}  // attributes for the generated A node
// }
/**
 * 
 * <p>Title: JsTree.java</p>
 * <p>Description: JsTree封装</p>
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
public class JsTree<T> {
	private String id;
	private String parent;
	private String text;
	private String icon;
	private Map<String, Boolean> state; 
	private List<JsTree> children;
	// 源数据
	private T sourceData;
	private Map<String, Object> li_attr;
	private Map<String, Object> a_attr;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Map<String, Boolean> getState() {
		return state;
	}
	public void setState(Map<String, Boolean> state) {
		this.state = state;
	}
	public List<JsTree> getChildren() {
		return children;
	}
	public void setChildren(List<JsTree> children) {
		this.children = children;
	}
	public T getSourceData() {
		return sourceData;
	}
	public void setSourceData(T sourceData) {
		this.sourceData = sourceData;
	}
	public Map<String, Object> getLi_attr() {
		return li_attr;
	}
	public void setLi_attr(Map<String, Object> li_attr) {
		this.li_attr = li_attr;
	}
	public Map<String, Object> getA_attr() {
		return a_attr;
	}
	public void setA_attr(Map<String, Object> a_attr) {
		this.a_attr = a_attr;
	}
}
