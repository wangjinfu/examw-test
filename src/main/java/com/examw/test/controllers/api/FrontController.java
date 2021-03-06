package com.examw.test.controllers.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.test.model.library.FrontPaperInfo;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.model.library.PaperPreview;
import com.examw.test.model.products.FrontProductInfo;
import com.examw.test.model.records.FeedBackInfo;
import com.examw.test.model.settings.AreaInfo;
import com.examw.test.model.settings.ExamInfo;
import com.examw.test.model.settings.FrontCategoryInfo;
import com.examw.test.model.settings.FrontExamInfo;
import com.examw.test.model.settings.SubjectInfo;
import com.examw.test.model.syllabus.ChapterKnowledgeInfo;
import com.examw.test.model.syllabus.SyllabusInfo;
import com.examw.test.service.library.IFrontPaperService;
import com.examw.test.service.products.IFrontProductService;
import com.examw.test.service.records.IFeedBackService;
import com.examw.test.service.settings.ExamStatus;
import com.examw.test.service.settings.IExamService;
import com.examw.test.service.settings.IFrontCategoryService;
import com.examw.test.service.syllabus.IChapterKnowledgeService;
import com.examw.test.service.syllabus.ISyllabusService;

/**
 * 前端业务数据接口。
 * 
 * @author yangyong
 * @since 2014年9月25日
 */
@Controller
@RequestMapping(value = { "/api/data" })
public class FrontController {
	private static final Logger logger = Logger.getLogger(FrontController.class);
	//注入前端考试类别服务接口。
	@Resource
	private IFrontCategoryService frontCategoryService;
	//注入考试服务接口。
	@Resource
	private IExamService examService;
	//注入产品服务接口。
	@Resource
	private IFrontProductService frontProductService;
	//注入考试大纲服务接口。
	@Resource
	private ISyllabusService syllabusService;
	//注入大纲知识点服务接口。
	@Resource
	private IChapterKnowledgeService knowledgeService;
	//注入前端试卷服务接口。
	@Resource
	private IFrontPaperService frontPaperService;
	@Resource
	private IFeedBackService feedBackService;
	/**
	 * 加载考试类别集合。
	 * @return
	 */
	@RequestMapping(value = {"/categories"}, method = {RequestMethod.GET})
	@ResponseBody
	public List<FrontCategoryInfo> loadCategories(){
		if(logger.isDebugEnabled()) logger.debug("加载考试类别...");
		return this.frontCategoryService.loadCategories();
	}
	/**
	 * 加载考试类别信息。
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = {"/categories/{categoryId}"}, method = {RequestMethod.GET})
	@ResponseBody
	public FrontCategoryInfo loadCategory(@PathVariable String categoryId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试类别［categoryId = %s］信息...", categoryId));
		return this.frontCategoryService.loadCategory(categoryId);
	}
	/**
	 * 加载考试信息集合。
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = {"/exams"} , method = {RequestMethod.GET})
	@ResponseBody
	public List<FrontExamInfo> loadExams(String categoryId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试［categoryId = %s］信息集合...", categoryId));
		 List<FrontExamInfo> list = new ArrayList<>();
		List<ExamInfo> exams =  this.examService.loadExams(categoryId,ExamStatus.ENABLE);
		if(exams != null && exams.size() > 0){
			for(ExamInfo exam : exams){
				if(exam == null) continue;
				list.add(new FrontExamInfo(exam));
			}
		}
		return list;
	}
	/**
	 * 加载考试信息。
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = {"/exams/{examId}"} , method = {RequestMethod.GET})
	@ResponseBody
	public FrontExamInfo loadExam(@PathVariable String examId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试［examId = %s］信息...", examId));
		return new FrontExamInfo(this.examService.loadExam(examId));
	}
	/**
	 * 加载考试下所有的产品
	 * @param examId
	 * 考试ID。
	 * @return
	 */
	@RequestMapping(value = {"/products/exam/{examId}"}, method = {RequestMethod.GET})
	@ResponseBody
	public List<FrontProductInfo> loadProducts(@PathVariable String examId){
		if(logger.isDebugEnabled()) logger.debug("考试下产品数据...");
		return this.frontProductService.loadProductsByExam(examId);
	}
	@RequestMapping(value = {"/products/category/{categoryId}"}, method = {RequestMethod.GET})
	@ResponseBody
	public List<FrontProductInfo> loadProductsByCategory(@PathVariable String categoryId){
		if(logger.isDebugEnabled()) logger.debug("考试分类下产品数据...");
		return this.frontProductService.loadProductsByCategory(categoryId);
	}
	/**
	 * 加载产品信息。
	 * @param productId
	 * 产品ID。
	 * @return
	 */
	@RequestMapping(value = {"/product/{productId}"}, method = {RequestMethod.GET})
	@ResponseBody
	public FrontProductInfo loadProduct(@PathVariable String productId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品[productId = %s]数据...", productId));
		return  this.frontProductService.loadProduct(productId);
	}
	/**
	 * 加载产品科目集合。
	 * @param productId
	 * 产品ID。
	 * @return
	 */
	@RequestMapping(value = {"/products/{productId}/subjects"}, method = {RequestMethod.GET})
	@ResponseBody
	public List<SubjectInfo> loadProductSubjects(@PathVariable String productId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品[productId = %s]科目集合...", productId));
		return this.frontProductService.loadProductSubjects(productId);
	}
	/**
	 * 加载产品地区集合。[Add by FW 2014.09.28]
	 * @param productId
	 * 产品ID。
	 * @return
	 */
	@RequestMapping(value = {"/products/{productId}/areas"}, method = {RequestMethod.GET})
	@ResponseBody
	public List<AreaInfo> loadProductAreas(@PathVariable String productId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品[productId = %s]科目集合...", productId));
		return this.frontProductService.loadProductAreas(productId);
	}
	/**
	 * 加载产品下试卷摘要信息集合。
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = {"/products/{productId}/papers"}, method = {RequestMethod.GET})
	@ResponseBody
	public List<FrontPaperInfo> loadProductPapers(@PathVariable String productId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品下的试卷数据集合...", productId));
		return this.frontPaperService.loadProductPapers(productId);
	}
	/**
	 * 加载试卷内容。
	 * @param paperId
	 * 所属试卷ID。
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/papers/{paperId}"}, method = {RequestMethod.GET})
	@ResponseBody
	public PaperPreview loadPaperContent(@PathVariable String paperId) throws Exception{
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［paperId = %s］内容...", paperId));
		return this.frontPaperService.loadPaperContent(paperId);
	}
	@RequestMapping(value="/papers/type",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> loadPaperType(){
		return this.frontPaperService.loadPaperType();
	}
	/**
	 * 加载科目下的考试大纲。
	 * @param subjectId
	 * @return
	 */
	@RequestMapping(value = {"/subjects/{subjectId}/syllabuses"}, method = {RequestMethod.GET})
	@ResponseBody
	public List<SyllabusInfo> loadSubjectSyllabuses(@PathVariable String subjectId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载科目[subjectId = %s]下的考试大纲...", subjectId));
		return this.syllabusService.loadLastSyllabuses(subjectId);
	}
	/**
	 * 加载考试大纲数据。
	 * @param syllabusId
	 * @return
	 */
	@RequestMapping(value = {"/syllabus/{syllabusId}"}, method = {RequestMethod.GET})
	@ResponseBody
	public SyllabusInfo loadSubjectSyllabus(@PathVariable String syllabusId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试大纲[subjectId = %s]信息...", syllabusId));
		return this.syllabusService.conversion(this.syllabusService.loadSyllabus(syllabusId));
	}
	/**
	 * 加载大纲知识点。
	 * @param syllabusId
	 * @param textBookId
	 * @return
	 */
//	@RequestMapping(value = {"/syllabus/{syllabusId}/knowledges"}, method = {RequestMethod.GET})
//	@ResponseBody
//	public List<ChapterKnowledgeInfo> loadKnowledges(@PathVariable String syllabusId,String textBookId){
//		if(logger.isDebugEnabled()) logger.debug(String.format("加载知识点[subjectId = %1$s，textBookId = %2$s]信息...", syllabusId,textBookId));
//		return this.knowledgeService.loadKnowledges(syllabusId, textBookId);
//	}
	@RequestMapping(value = {"/syllabus/{syllabusId}/knowledges"}, method = {RequestMethod.GET})
	@ResponseBody
	public List<ChapterKnowledgeInfo> loadKnowledges(@PathVariable String syllabusId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载知识点[subjectId = %1$s]信息...", syllabusId));
		return this.knowledgeService.loadKnowledges(syllabusId);
	}
	/**
	 * 加载知识点信息。
	 * @param knowledgeId
	 * @return
	 */
	@RequestMapping(value = {"/knowledges/{knowledgeId}"}, method = {RequestMethod.GET})
	@ResponseBody
	public ChapterKnowledgeInfo loadKnowledge(@PathVariable String knowledgeId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载知识点［knowledgeId = %s］信息...", knowledgeId));
		return this.knowledgeService.conversion(this.knowledgeService.loadKnowledge(knowledgeId));
	}
	/**
	 * 加载每日一练试卷ID。
	 * @param subjectId
	 * 所属科目。
	 * @param areaId
	 * 所属地区。
	 * @param page
	 * 当前页码。
	 * @param rows
	 * 每页数据。
	 * @return
	 */
	@RequestMapping(value = {"/daily/papers/{productId}"}, method = { RequestMethod.GET })
	@ResponseBody
	public List<FrontPaperInfo> loadDailyloadDailyPapers(@PathVariable String productId,Integer page,Integer rows){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品下的每日一练试卷数据集合...", productId));
		return this.frontPaperService.loadDailyPapers(productId, page, rows);
	}
	
	//2014.12.28
	/**
	 * 获取产品下日期之后需要更新的试卷数据
	 * @param productId
	 * @param lastTime
	 * @return
	 */
	@RequestMapping(value = {"/products/{productId}/papersupdate"}, method = { RequestMethod.GET })
	@ResponseBody
	public List<FrontPaperInfo> loadNeedUpdatePapers(@PathVariable String productId,long lastTime)
	{
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品下[%1$s]日期[%2$s]后的试卷数据集合...", productId,lastTime));
		return this.frontPaperService.loadNeedUpdatePapers(productId,new Date(lastTime));
	}
	@RequestMapping(value = {"/syllabus/{syllabusId}/items"}, method = { RequestMethod.GET })
	@ResponseBody
	public List<ItemInfo> loadSyllabusItems(@PathVariable String syllabusId)
	{
		if(logger.isDebugEnabled()) logger.debug(String.format("加载大纲下[%1$s]试题的集合...", syllabusId));
		return this.syllabusService.loadSyllabusItems(syllabusId);
	}
	//2015.01.12 增加反馈
	@RequestMapping(value = {"/feedback/add"}, method = { RequestMethod.POST })
	@ResponseBody
	public Json addFeedBack(@RequestBody FeedBackInfo feedBack)
	{
		Json json = new Json();
		try
		{
			json.setSuccess(true);
			this.feedBackService.update(feedBack);
		}catch(Exception e)
		{
			json.setSuccess(false);
		}
		return json;
	}
}