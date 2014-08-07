package com.examw.test.controllers.syllabus;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.security.Right;
import com.examw.test.model.syllabus.KnowledgeInfo;
import com.examw.test.service.syllabus.IKnowledgeService;
/**
 * 知识点控制器。
 * @author lq.
 * @since 2014-8-06.
 */
@Controller
@RequestMapping(value = "/syllabus/knowledge")
public class KnowledegeController {
	private static final Logger logger = Logger.getLogger(KnowledegeController.class);
	//知识点服务
	@Resource
	private IKnowledgeService knowService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_KNOWLEDGE+ ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE",ModuleConstant.SYLLABUS_KNOWLEDGE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE",ModuleConstant.SYLLABUS_KNOWLEDGE + ":" + Right.DELETE);
		return "syllabus/know_list";
	}
	/**
	 * 获取编辑页面。
	 * @return
	 * 编辑页面。
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_KNOWLEDGE+ ":" + Right.VIEW})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		return "syllabus/book_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_KNOWLEDGE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<KnowledgeInfo> datagrid(KnowledgeInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.knowService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_KNOWLEDGE+ ":" + Right.VIEW})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(KnowledgeInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.knowService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新教材数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_KNOWLEDGE + ":" + Right.VIEW})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.knowService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}