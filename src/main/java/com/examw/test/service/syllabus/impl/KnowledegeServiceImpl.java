package com.examw.test.service.syllabus.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.test.dao.syllabus.IKnowledgeDao;
import com.examw.test.domain.syllabus.Knowledge;
import com.examw.test.model.syllabus.KnowledgeInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.syllabus.IKnowledgeService;
/**
 * 知识点服务接口实现类。
 * @author lq.
 * @since 2014-08-07.
 */
public class KnowledegeServiceImpl extends BaseDataServiceImpl<Knowledge, KnowledgeInfo> implements IKnowledgeService {
	private static final Logger logger = Logger.getLogger(KnowledegeServiceImpl.class);
	private IKnowledgeDao knowDao;
	/**
	 * 设置知识点数据接口。
	 * @param knowDao
	 * 知识点数据接口。
	 */
	public void setKnowDao(IKnowledgeDao knowDao) {
		if(logger.isDebugEnabled())logger.debug("注入知识点数据接口...");
		this.knowDao = knowDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Knowledge> find(KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		return null;
	}
	/*
	 * 统计数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("统计数据...");
		return null;
	}
	/*
	 * 类型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected KnowledgeInfo changeModel(Knowledge data) {
		if(logger.isDebugEnabled())logger.debug("类型转换...");
		if(logger.isDebugEnabled())logger.debug(" 知识点类型转换...");
		if(data == null) return null;
		KnowledgeInfo info = new KnowledgeInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getSyllabus() != null){
			info.setSyllId(data.getSyllabus().getId());
			info.setSyllName(data.getSyllabus().getTitle());
		}
		if(data.getBook() != null){
			info.setBookId(data.getBook().getId());
		}
		return info;
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public KnowledgeInfo update(KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("更新数据...");
		return null;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled())logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length;i++){
			Knowledge data = this.knowDao.load(Knowledge.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug("删除数据［"+ ids[i]+"］");
				this.knowDao.delete(data);
			}
		}
	}
	/*
	 * 根据大纲加载知识点
	 * @see com.examw.test.service.syllabus.IKnowledgeService#loadKnowledge(java.lang.String)
	 */
	@Override
	public KnowledgeInfo loadKnowledge(final String syllabusId) {
		List<Knowledge> knowList = this.knowDao.findKnowledges(null, new KnowledgeInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSyllId() { return syllabusId; }
		});
		if(knowList==null || knowList.size()==0)
		return null;
		return this.changeModel(knowList.get(0));
	}
}