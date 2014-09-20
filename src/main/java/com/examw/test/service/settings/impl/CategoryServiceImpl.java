package com.examw.test.service.settings.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.model.TreeNode;
import com.examw.test.dao.settings.ICategoryDao;
import com.examw.test.domain.settings.Category;
import com.examw.test.domain.settings.Exam;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.front.CategoryFrontInfo;
import com.examw.test.model.settings.CategoryInfo;
import com.examw.test.model.settings.ExamInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.settings.ICategoryService;
import com.examw.test.service.settings.IExamService;

/**
 * 考试分类服务接口实现类
 * @author fengwei.
 * @since 2014年8月6日 下午3:36:37.
 */
public class CategoryServiceImpl extends BaseDataServiceImpl<Category, CategoryInfo> implements ICategoryService {
	private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);
	private ICategoryDao categoryDao;
	private IExamService examService;
	/**
	 * 设置 考试分类数据接口
	 * @param categoryDao
	 * 
	 */
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	/**
	 * 设置考试服务接口。
	 * @param examService 
	 *	  考试服务接口。
	 */
	public void setExamService(IExamService examService) {
		this.examService = examService;
	}
	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Category> find(CategoryInfo info) {
		if (logger.isDebugEnabled())logger.debug("查询[考试分类]数据...");
		return categoryDao.findCategorys(info);
	}
	/*
	 * 数据模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected CategoryInfo changeModel(Category data) {
		if (logger.isDebugEnabled())logger.debug("[考试分类]数据模型转换...");
		if(data == null) return null;
		CategoryInfo info = new CategoryInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getParent() != null){
			info.setPid(data.getParent().getId());
		}
		info.setFullName(this.loadFullName(data));
		return info;
	}
	//加载类别全称。
	private String loadFullName(Category data){
		if(data == null) return null;
		if(data.getParent() == null) return data.getName();
		StringBuilder builder = new StringBuilder(data.getName());
		builder.insert(0, this.loadFullName(data.getParent()) + " >> ");
		return builder.toString();
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(CategoryInfo info) {
		if (logger.isDebugEnabled())	logger.debug("查询[考试分类]数据总数...");
		return this.categoryDao.total(info);
	}
	/*
	 * 更新或插入数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public CategoryInfo update(CategoryInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Category data = StringUtils.isEmpty(info.getId()) ? null :this.categoryDao.load(Category.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Category();
		}
		BeanUtils.copyProperties(info, data);
		if(!StringUtils.isEmpty(info.getPid()) && (data.getParent() == null || !data.getParent().getId().equalsIgnoreCase(info.getPid()))){
			Category parent = this.categoryDao.load(Category.class, info.getPid());
			//自己不能是自己的父类
			if(parent != null && !parent.getId().equalsIgnoreCase(data.getId()))	data.setParent(parent);
		}
		if(StringUtils.isEmpty(info.getPid())){
			data.setParent(null);
		}
		if(isAdded) this.categoryDao.save(data);
		return info;
	}
	/*
	 * 删除数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Category data = this.categoryDao.load(Category.class, ids[i]);
			if(data != null && (data.getChildren() == null || data.getChildren().size() == 0)){
				this.categoryDao.delete(data); 
				if(logger.isDebugEnabled()) logger.debug("删除数据:" + data.getName());
			}else{
				throw new RuntimeException("数据不存在或者请先删除子类");
			}
		}
	}
	/*
	 * 加载最大的代码值
	 * @see com.examw.test.service.settings.ICategoryService#loadMaxCode(java.lang.String)
	 */
	@Override
	public Integer loadMaxCode(String parentCatalogId) {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		return this.categoryDao.loadMaxCode(parentCatalogId);
	}
	/*
	 * 查询所有的考试分类
	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategorys()
	 */
	@Override
	public List<TreeNode> loadAllCategorys(String ignoreCategoryId) {
		List<TreeNode> result = new ArrayList<>();
		List<Category> topCategories = this.categoryDao.loadTopCategories();
		if(topCategories != null){
			for(Category data : topCategories){
				if(data == null) continue;
				TreeNode node = this.createTreeNode(data,ignoreCategoryId,null,false,false);
				if(node != null){
					result.add(node);
				}
			}
		}
		return result;
	}
	/**
	 * 根据条件创建树节点
	 * @param data		考试分类
	 * @param attributes	分类节点带的属性
	 * @param withExam	是否加载所包含考试
	 * @param withSubject	是否加载所包含科目
	 * @return
	 */
	private TreeNode createTreeNode(Category data,String ignoreCategoryId,Map<String, Object> attributes,boolean withExam,boolean withSubject){
		if(data == null || data.getId().equalsIgnoreCase(ignoreCategoryId)) return null;
		TreeNode node = new TreeNode();
		node.setId(data.getId());
		node.setText(data.getName());
		//只在加载类别树的时候添加一些属性
		boolean withAttr = (attributes == null);
		if(withAttr){
			attributes = new HashMap<>();
			attributes.put("abbr", data.getAbbr());
			attributes.put("code", data.getCode());
		}
		node.setAttributes(attributes);
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<TreeNode> list = new ArrayList<>();
			for(Category c : data.getChildren()){
				if(withAttr) attributes = null;
				TreeNode t = this.createTreeNode(c,ignoreCategoryId,attributes,withExam,withSubject);
				 if(t != null){
					 list.add(t);
				 }
			}
			node.setChildren(list);
		}
		if(withExam){
			List<TreeNode> list_exams = new ArrayList<>();
			for(final Exam e : data.getExams()){
				if(e == null) continue;
				TreeNode tv_exam = new TreeNode();
				tv_exam.setId(e.getId());
				tv_exam.setText(e.getName());
				attributes = new HashMap<>();
				attributes.put("type", "exam");
				tv_exam.setAttributes(attributes);
				if(withSubject){
					if(e.getSubjects() != null && e.getSubjects().size() > 0){
						List<TreeNode> list_subjects = new ArrayList<>();
						for(Subject s : e.getSubjects()){
							TreeNode tv_subject = new TreeNode();
							tv_subject.setId(s.getId());
							if(s.getArea()==null)
								tv_subject.setText(s.getName());
							else
								tv_subject.setText(s.getName()+"["+s.getArea().getName()+"]");
							attributes = new HashMap<>();
							attributes.put("type", "subject");
							tv_subject.setAttributes(attributes);
							list_subjects.add(tv_subject);
						}
						tv_exam.setChildren(list_subjects);
					}
					list_exams.add(tv_exam);
				}else{
					list_exams.add(tv_exam);
				}
			}
			if(node.getChildren()==null)
				node.setChildren(list_exams);
			else{
				list_exams.addAll(node.getChildren());
				node.setChildren(list_exams);
			}
		}
		return node;
	}
	/*
	 * 加载所有的考试分类-考试树
	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategoryExams()
	 */
	@Override
	public List<TreeNode> loadAllCategoryExams() {
		List<TreeNode> treeNodes = new ArrayList<>();
		List<Category> list =  this.categoryDao.loadTopCategories();
		if(list != null && list.size() > 0){
			for(final Category data : list){
				if(data == null) continue;
				Map<String,Object> attributes = new HashMap<>();
				attributes.put("type", "category");
				treeNodes.add(createTreeNode(data,null,attributes,true,false));
			}
		}
		return treeNodes;
	}
	/*
	 * 加载考试分类-考试-科目树
	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategoryExamSubjects()
	 */
	@Override
	public List<TreeNode> loadAllCategoryExamSubjects() {
		List<TreeNode> treeNodes = new ArrayList<>();
		List<Category> list = this.categoryDao.loadTopCategories();
		if(list != null && list.size() > 0){
			for(final Category data : list){
				if(data == null) continue;
				Map<String,Object> attributes = new HashMap<>();
				attributes.put("type", "category");
				treeNodes.add(createTreeNode(data,null,attributes,true,true));
			}
		}
		return treeNodes;
	}
	/*
	 * 前台加载考试分类
	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategoryAndExams()
	 */
	@Override
	public List<CategoryFrontInfo> loadAllCategoryAndExams() {
		List<CategoryFrontInfo> result = new ArrayList<CategoryFrontInfo>();
		List<Category> list =  this.categoryDao.loadTopCategories();
		if(list != null && list.size() > 0){
			for(final Category data : list){
				if(data == null) continue;
				result.add(createCategoryFrontInfo(data));
			}
		}
		return result;
	}
	/**
	 * 构造前台分类对象
	 * @param data	考试分类
	 * @return
	 */
	private CategoryFrontInfo createCategoryFrontInfo(Category data)
	{
		CategoryFrontInfo info = new CategoryFrontInfo();
		BeanUtils.copyProperties(data, info, new String[]{"exams"});
		setExams(info,data);
		if(data.getChildren()!=null){
			for(Category category:data.getChildren()){
				setExams(info,category);
			}
		}
		return info;
	}
	/**
	 * 设置分类下的所有的考试
	 * @param info	
	 * @param data
	 */
	private void setExams(CategoryFrontInfo info,Category data){
		if(data.getExams()!=null){
			List<ExamInfo> exams = new ArrayList<ExamInfo>();
			for(Exam exam:data.getExams())
			{
				if(data == null)continue;
				exams.add(this.examService.conversion(exam));
			}
			if(info.getExams()==null)
				info.setExams(exams);
			else
				info.getExams().addAll(exams);
		}
	}
}