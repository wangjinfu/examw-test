package com.examw.test.model.settings;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;

/**
 * 科目信息
 * @author fengwei.
 * @since 2014年8月6日 下午3:05:25.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SubjectInfo extends Paging implements Comparable<SubjectInfo>{
	private static final long serialVersionUID = 1L;
	private String pid,id,name,examId,examName,categoryId,categoryName,fullName;
	private Integer code;
	private String[] areaId,areaName;
	private List<SubjectInfo> children;
	//2015.03.09添加属性
	private String title,keywords,description;
	/**
	 * 获取科目ID。
	 * @return 科目ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置科目ID。
	 * @param id
	 * 科目ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取科目代码。
	 * @return 科目代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置科目代码。
	 * @param code
	 * 科目代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取科目名称。
	 * @return 科目名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置科目名称。
	 * @param name
	 * 科目名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取 所属考试ID
	 * @return examId
	 * 所属考试ID
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置 所属考试ID
	 * @param examId
	 * 所属考试ID
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取 所属考试名称
	 * @return examName
	 * 所属考试名称
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置 所属考试名称
	 * @param examName
	 * 所属考试名称
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
	/**
	 * 获取地区ID
	 * @return areaId
	 * 地区ID
	 */
	public String[] getAreaId() {
		return areaId;
	}
	/**
	 * 设置 地区ID
	 * @param areaId
	 * 地区ID
	 */
	public void setAreaId(String[] areaId) {
		this.areaId = areaId;
	}
	/**
	 * 获取 地区名称
	 * @return areaName
	 * 地区名称
	 */
	public String[] getAreaName() {
		return areaName;
	}
	/**
	 * 设置 地区名称
	 * @param areaName
	 * 地区名称
	 */
	public void setAreaName(String[] areaName) {
		this.areaName = areaName;
	}
	/**
	 * 获取 所属分类ID
	 * @return categoryId
	 * 所属分类ID
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置 所属分类ID
	 * @param categoryId
	 * 所属分类ID
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取 所属分类名称
	 * @return categoryName
	 * 所属分类名称
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 设置 所属分类名称
	 * @param categoryName
	 * 所属分类名称
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	/**
	 * 获取 父ID
	 * @return pid
	 * 父ID
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置 父ID
	 * @param pid
	 * 父ID
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取 科目全称
	 * @return fullName
	 * 科目全称
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * 设置 科目全称
	 * @param fullName
	 * 科目全称
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	/**
	 * 获取 子科目
	 * @return children
	 * 子科目
	 */
	public List<SubjectInfo> getChildren() {
		return children;
	}
	/**
	 * 设置 子科目
	 * @param children
	 * 子科目
	 */
	public void setChildren(List<SubjectInfo> children) {
		this.children = children;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SubjectInfo o) {
		if(this == o) return 0;
		int index = this.getCode() - o.getCode();
		if(index == 0){
			index = this.getExamId().compareToIgnoreCase(o.getExamId());
			if(index == 0){
				index = this.getName().compareToIgnoreCase(o.getName());
				if(index == 0){
					index = this.getId().compareToIgnoreCase(o.getId());
				}
			}
		}
		return index;
	}	
	/**
	 * 获取 标题
	 * @return title
	 * 标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置 标题
	 * @param title
	 * 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取 关键字
	 * @return keywords
	 * 关键字
	 */
	public String getKeywords() {
		return keywords;
	}
	/**
	 * 设置 关键字
	 * @param keywords
	 * 关键字
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	/**
	 * 获取 描述
	 * @return description
	 * 描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 描述
	 * @param description
	 * 描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}