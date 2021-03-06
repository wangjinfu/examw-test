package com.examw.test.domain.syllabus;

import java.io.Serializable;
import java.util.Set;

import com.examw.test.domain.library.Item;
import com.examw.test.domain.settings.Area;
import com.examw.test.domain.settings.Subject;
/**
 * 考试大纲。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class Syllabus implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,title;
	private Subject subject;
	private Syllabus parent;
	private Set<Syllabus> children;
	private Integer status,orderNo;
	private Set<ChapterKnowledge> knowledges;
	//Add by FW 增加两个字段
	private Integer year;
	private Set<Area> areas;
	//2014.12.28
	private Set<Item> items;
	/**
	 * 获取要点ID。
	 * @return 要点ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置要点ID。
	 * @param id 
	 *	要点ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取要点。
	 * @return 要点。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置要点。
	 * @param title 
	 *	要点。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取状态（1-启用，0-停用）。
	 * @return 状态（1-启用，0-停用）。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置状态（1-启用，0-停用）。
	 * @param status 
	 *	  状态（1-启用，0-停用）。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取所属科目。
	 * @return 所属科目。
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * 设置所属科目。
	 * @param subject 
	 *	所属科目。
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	/**
	 * 获取上级要点。
	 * @return 上级要点。
	 */
	public Syllabus getParent() {
		return parent;
	}
	/**
	 * 设置上级要点。
	 * @param parent 
	 *	上级要点。
	 */
	public void setParent(Syllabus parent) {
		this.parent = parent;
	}
	/**
	 * 获取子要点集合。
	 * @return 子要点集合。
	 */
	public Set<Syllabus> getChildren() {
		return children;
	}
	/**
	 * 设置子要点集合。
	 * @param children 
	 *	子要点集合。
	 */
	public void setChildren(Set<Syllabus> children) {
		this.children = children;
	}
	/**
	 * 获取排序号。
	 * @return 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo 
	 *	 排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取知识点集合。
	 * @return 知识点集合。
	 */
	public Set<ChapterKnowledge> getKnowledges() {
		return knowledges;
	}
	/**
	 * 设置知识点集合。
	 * @param knowledges 
	 *	  知识点集合。
	 */
	public void setKnowledges(Set<ChapterKnowledge> knowledges) {
		this.knowledges = knowledges;
	}
	/**
	 * 获取 年份
	 * @return year
	 * 年份
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * 设置 年份
	 * @param year
	 * 年份
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	/**
	 * 获取 地区
	 * @return areas
	 * 地区
	 */
	public Set<Area> getAreas() {
		return areas;
	}
	/**
	 * 设置 地区
	 * @param areas
	 * 地区
	 */
	public void setAreas(Set<Area> areas) {
		this.areas = areas;
	}
	
	/**
	 * 获取 试题的集合
	 * @return items
	 * 
	 */
	public Set<Item> getItems() {
		return items;
	}
	/**
	 * 设置 试题的集合
	 * @param items
	 * 
	 */
	public void setItems(Set<Item> items) {
		this.items = items;
	}
	
	//2015.03.06 添加一个内容字段
	private String description;		//描述
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