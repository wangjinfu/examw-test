package com.examw.test.service.products.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.products.IChannelDao;
import com.examw.test.dao.products.IProductDao;
import com.examw.test.dao.products.IRegistrationDao;
import com.examw.test.dao.products.ISoftwareTypeDao;
import com.examw.test.dao.products.ISoftwareTypeLimitDao;
import com.examw.test.domain.products.Channel;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.products.Registration;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.domain.products.SoftwareTypeLimit;
import com.examw.test.domain.settings.Category;
import com.examw.test.domain.settings.Exam;
import com.examw.test.model.products.BatchRegistrationInfo;
import com.examw.test.model.products.RegistrationInfo;
import com.examw.test.model.products.RegistrationLimitInfo;
import com.examw.test.model.products.SoftwareTypeInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.products.IRegistrationService;
import com.examw.test.service.products.RegistrationStatus;
import com.examw.utils.StringUtil;
import com.examw.utils.VerifyCodeUtil;

/**
 * 注册码服务接口
 * @author fengwei.
 * @since 2014年8月14日 下午3:06:56.
 */
public class RegistrationServiceImpl extends BaseDataServiceImpl<Registration,RegistrationInfo> implements IRegistrationService{
	private static final Logger logger = Logger.getLogger(RegistrationServiceImpl.class);
	private IRegistrationDao registrationDao;
	private IProductDao productDao;
	private IChannelDao channelDao;
	private ISoftwareTypeDao softwareTypeDao;
	private ISoftwareTypeLimitDao softwareTypeLimitDao;
	private Map<Integer,String> statusMap;
	//默认的期限和绑定次数
	private Integer limitTime,bingTimes;
	/**
	 * 设置注册码数据接口。
	 * @param registrationDao 
	 *	  注册码数据接口。
	 */
	public void setRegistrationDao(IRegistrationDao registrationDao) {
		if(logger.isDebugEnabled()) logger.debug("注入注册码数据接口...");
		this.registrationDao = registrationDao;
	}
	/**
	 * 设置产品数据接口。
	 * @param productDao 
	 *	  产品数据接口。
	 */
	public void setProductDao(IProductDao productDao) {
		if(logger.isDebugEnabled()) logger.debug("注入产品数据接口...");
		this.productDao = productDao;
	}
	/**
	 * 设置渠道数据接口。
	 * @param channelDao 
	 *	  渠道数据接口。
	 */
	public void setChannelDao(IChannelDao channelDao) {
		if(logger.isDebugEnabled()) logger.debug("注入渠道数据接口...");
		this.channelDao = channelDao;
	}
	/**
	 * 设置软件类型数据接口。
	 * @param softwareTypeDao 
	 *	  软件类型数据接口。
	 */
	public void setSoftwareTypeDao(ISoftwareTypeDao softwareTypeDao) {
		if(logger.isDebugEnabled()) logger.debug("注入软件类型数据接口...");
		this.softwareTypeDao = softwareTypeDao;
	}
	/**
	 * 设置注册码软件类型限制数据接口。
	 * @param softwareTypeLimitDao 
	 *	  注册码软件类型限制数据接口。
	 */
	public void setSoftwareTypeLimitDao(ISoftwareTypeLimitDao softwareTypeLimitDao) {
		if(logger.isDebugEnabled()) logger.debug("注入注册码软件类型限制数据接口...");
		this.softwareTypeLimitDao = softwareTypeLimitDao;
	}
	/**
	 * 设置状态值名称集合。
	 * @param statusMap 
	 *	  状态值名称集合。
	 */
	public void setStatusMap(Map<Integer,String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入状态值名称集合...");
		this.statusMap = statusMap;
	}
	
	/**
	 * 设置 默认使用期限
	 * @param limitTime
	 * 
	 */
	public void setLimitTime(Integer limitTime) {
		this.limitTime = limitTime;
	}
	/**
	 * 设置 默认绑定次数
	 * @param bingTimes
	 * 
	 */
	public void setBingTimes(Integer bingTimes) {
		this.bingTimes = bingTimes;
	}
	/*
	 * 加载状态值名称。
	 * @see com.examw.test.service.products.IRegistrationService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Registration> find(RegistrationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.registrationDao.findRegistrations(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected RegistrationInfo changeModel(Registration data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Registration => RegistrationInfo...");
		if(data == null) return null;
		RegistrationInfo info = new RegistrationInfo();
		BeanUtils.copyProperties(data, info,new String[]{"code"});
		info.setCode(this.formatCode(data.getCode()));//格式化注册码
		Product product = null;
		if((product = data.getProduct()) != null){
			info.setProductId(product.getId());
			info.setProductName(product.getName());
			Exam exam = null;
			if((exam = product.getExam()) != null){
				info.setExamId(exam.getId());
				info.setExamName(exam.getName());
				Category category = null;
				if((category = exam.getCategory()) != null){
					info.setCategoryId(category.getId());
				}
			}
		}
		Channel channel = null;
		if((channel = data.getChannel()) != null){
			info.setChannelId(channel.getId());
			info.setChannelName(channel.getName());
		}
		info.setStatusName(this.loadStatusName(info.getStatus()));
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(RegistrationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.registrationDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public RegistrationInfo update(RegistrationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据..."); 
		return this.changeModel(this.updateRegistration(info));
	}
	//更新注册码
	private Registration updateRegistration(RegistrationInfo info){
		if(info == null) return null;
		boolean isAdded = false;
		Registration data = StringUtils.isEmpty(info.getId()) ?  null : this.registrationDao.load(Registration.class, info.getId());
		if(isAdded = (data ==  null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			data = new Registration();
		}else{
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		info.setLastTime(new Date());
		if(info.getStatus() == null){
			info.setStatus(RegistrationStatus.NONE.getValue());
		}
		try {
			if(!this.verificationFormat(info.getCode())){
				throw new Exception(String.format("非法注册码:%s", info.getCode()));
			}
			info.setCode(this.cleanCodeFormat(info.getCode()));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		//创建注册码激活
		if(info.getLimits() > 0 && info.getStatus() == RegistrationStatus.ACTIVE.getValue()){
			data.setStartTime(new Date());//激活时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(data.getStartTime());
			calendar.add(Calendar.MONTH, info.getLimits());
			calendar.set(Calendar.HOUR_OF_DAY, 23);//时
			calendar.set(Calendar.MINUTE, 59);//分
			calendar.set(Calendar.SECOND, 59);//秒
			calendar.set(Calendar.MILLISECOND, 0);//微妙
			data.setEndTime(calendar.getTime());//过期时间
		}
		//属性赋值
		BeanUtils.copyProperties(info, data, new String[]{"startTime","endTime"});
		//产品
		data.setProduct(StringUtils.isEmpty(info.getProductId()) ? null : this.productDao.load(Product.class, info.getProductId()));
		//渠道
		data.setChannel(StringUtils.isEmpty(info.getChannelId()) ?  null : this.channelDao.load(Channel.class, info.getChannelId()));
		//add
		if(isAdded)this.registrationDao.save(data);
		//注册码软件类型限制
		List<SoftwareTypeLimit> updateLimits = new ArrayList<>(),limits = new ArrayList<>();
		if(!isAdded && data.getSoftwareTypeLimits() != null && data.getSoftwareTypeLimits().size() > 0){
			for(SoftwareTypeLimit limit : data.getSoftwareTypeLimits()){
				if(limit == null) continue;
				limits.add(limit);
			}
		}
		if(info.getTypeLimits() != null && info.getTypeLimits().size() > 0){
			for(RegistrationLimitInfo limit : info.getTypeLimits()){
				if(limit == null) continue;
				SoftwareType type = this.softwareTypeDao.load(SoftwareType.class, limit.getSoftwareTypeId());
				if(type == null) throw new RuntimeException(String.format("软件类型［%s］不存在！", limit.getSoftwareTypeId()));
				SoftwareTypeLimit stl = this.softwareTypeLimitDao.update(data, type, limit.getTimes());
				if(stl != null) updateLimits.add(stl);
			}
		}
		if(!isAdded && limits != null && limits.size() > 0){
			for(SoftwareTypeLimit limit : limits){
				if(limit == null) continue;
				if(updateLimits.size() == 0){
					this.softwareTypeLimitDao.deleteByRegistrationId(data.getId());
					break;
				}
				int index = Collections.binarySearch(updateLimits, limit, new Comparator<SoftwareTypeLimit>(){
					@Override
					public int compare(SoftwareTypeLimit o1,SoftwareTypeLimit o2) {
						String o1_value = String.format("%1$s-%2$s", o1.getRegister().getId(), o1.getSoftwareType().getId()),
									o2_value = String.format("%1$s-%2$s", o2.getRegister().getId(), o2.getSoftwareType().getId());
						return o1_value.compareTo(o2_value);
					}
				});
				if(index < 0){
					data.getSoftwareTypeLimits().remove(limit);
					this.softwareTypeLimitDao.delete(limit);
				}
			}
		}
		return data;
	}
	/*
	 * 批量创建注册码。
	 * @see com.examw.test.service.products.IRegistrationService#updateBatch(com.examw.test.model.products.BatchRegistrationInfo)
	 */
	@Override
	public List<String> updateBatch(BatchRegistrationInfo info) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("批量创建注册码...");
		List<String> list = new ArrayList<>();
		if(info == null || info.getCount() == null || info.getCount() <= 0) return list;
		for(int i = 0; i < info.getCount(); i++){
			RegistrationInfo registerInfo = new RegistrationInfo();
			BeanUtils.copyProperties(info, registerInfo);
			registerInfo.setCode(this.generatedCode(registerInfo.getPrice().intValue(), registerInfo.getLimits().intValue()));
			Registration data = this.updateRegistration(registerInfo);
			if(data != null){
				list.add(data.getCode());
			}
		}
		return list;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Registration data = this.registrationDao.load(Registration.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s", ids[i]));
				this.registrationDao.delete(data);
			}
		}
	}
	/*
	 * 校验注册码格式。
	 * @see com.examw.test.service.products.IRegistrationCodeService#verificationFormat(java.lang.String)
	 */
	@Override
	public boolean verificationFormat(String code) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("校验注册码格式:%s ...", code));
		if(StringUtils.isEmpty(code)) throw new Exception("注册码为空！");
		//清理注册码格式
		code = this.cleanCodeFormat(code);
		if(!code.matches("^\\d{16}$")) return false;
		return this.calChecksumValue(code.substring(0, 8) + code.substring(10)) == Integer.parseInt(code.substring(8,10));
	}
	/*
	 * 验证注册码合法性。
	 * @see com.examw.test.service.products.IRegistrationCodeService#validation(java.lang.String)
	 */
	@Override
	public boolean validation(String code) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("验证注册码合法性：%s", code));
		boolean result = this.verificationFormat(code);
		if(!result) throw new Exception("注册码错误！");
		Registration data = this.registrationDao.loadRegistration(code);
		if(data == null) throw new Exception(String.format("注册码未登记！"));
		if(data.getStatus() != RegistrationStatus.ACTIVE.getValue()){
			throw new Exception(String.format("注册码：%s!", this.loadStatusName(data.getStatus())));
		}
		if(data.getStartTime() != null && data.getEndTime() != null){//验证有效期
			long current_time = System.currentTimeMillis(),start_time = data.getStartTime().getTime(),end_time = data.getEndTime().getTime();
			if(current_time < start_time || current_time > end_time){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				throw new Exception(String.format("注册码不在有效时间范围［%1$s - %2$s］内！", sdf.format(data.getStartTime()), sdf.format(data.getEndTime())));
			}
		}
		return true;
	}
	/*
	 * 去除注册码格式。
	 * @see com.examw.test.service.products.IRegistrationCodeService#cleanCodeFormat(java.lang.String)
	 */
	@Override
	public String cleanCodeFormat(String code){
		if(StringUtils.isEmpty(code)) return null;
		//转半角
		code = StringUtil.toSemiangle(code);
		//剔除空格和横杠
		return code.replaceAll("\\s+", "").replaceAll("-", "").trim();
	}
	/*
	 * 格式化注册码。
	 * @see com.examw.test.service.products.IRegistrationCodeService#formatCode(java.lang.String)
	 */
	@Override
	public String formatCode(String code){
		if(StringUtils.isEmpty(code)) return null;
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < code.length(); i++){
			if(i > 0 && i%4 == 0){
				builder.append(" ");
			}
			builder.append(code.substring(i, i+1));
		}
		return builder.toString();
	}
	//计算校验数字。
	private int calChecksumValue(String source){
		int sum = 0,len = WEIGHT_VALUE.length;
		for(int i = 0; i < source.length(); i++){
			sum += Integer.parseInt(source.substring(i, i + 1)) * WEIGHT_VALUE[i % len];
		}
		return sum % CHECK_RESIDUE_VALUE;
	}
	/*
	 * 生成注册码。
	 * @see com.examw.test.service.products.IRegistrationCodeService#generatedCode(int, int)
	 */
	@Override
	public String generatedCode(int price, int limit) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("生成注册码:[price=%1$d][limit=%2$d]", price, limit));
		String p = String.format("%03d", price),l = String.format("%02d", limit);
		if(p.length() > 3) p = p.substring(0, 3);//3位的价格
		if(l.length() > 2) l = l.substring(0,2);//2位的期限
		StringBuilder codeBuilder = new StringBuilder();
		codeBuilder.append(String.format("%ty", new Date()))//2位的年份
						   .append(p)//3位的价格
						   .append(VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 3, null))//3位随机数字
						   .append(VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 4, null))//4位随机数字
						   .append(l);//2位的期限
		int checksum = this.calChecksumValue(codeBuilder.toString());
		codeBuilder.insert(8, String.format("%02d", checksum));
		String code = codeBuilder.toString();
		if(!this.verificationFormat(code)){//校验格式
			throw new Exception(String.format("生成注册码失败：%s",code));
		}
		if(this.registrationDao.existCode(code)){//判断是否存在
			return this.generatedCode(price, limit);//重新产生
		}
		return this.formatCode(code);
	}
	/*
	 * 自动生成代码
	 * @see com.examw.test.service.products.IRegistrationCodeService#autoGeneratedCode(java.lang.String, java.lang.String, int)
	 */
	@Override
	public String autoGeneratedCode(String productId, String channelId,Integer limit) throws Exception {
		if(StringUtils.isEmpty(productId))
			throw new Exception("产品ID为空!");
		Product product = this.productDao.load(Product.class, productId);
		if(product == null)
			throw new Exception("没有此产品的信息");
		Registration data = new Registration();
		//设置ID
		data.setId(UUID.randomUUID().toString()); 
		//设置状态[未激活]
		data.setStatus(RegistrationStatus.NONE.getValue());
		//设置产品
		data.setProduct(product);
		//TODO 设置价格 [产品价格]
		data.setPrice(product.getDiscount());
		//设置渠道
		data.setChannel(StringUtils.isEmpty(channelId) ?  null : this.channelDao.load(Channel.class, channelId));
		//设置期限
		data.setLimits((limit == null || limit == 0)?limitTime:limit);
		//设置生成时间
		data.setCreateTime(new Date());
		//生成注册码
		data.setCode(this.cleanCodeFormat(this.generatedCode(data.getPrice().intValue(), data.getLimits())));
		//保存
		this.registrationDao.save(data);
		//设置软件限制[默认全线三次]
		List<SoftwareType> types = this.softwareTypeDao.findSoftwareTypes(new SoftwareTypeInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort(){ return "code"; }
			@Override
			public String getOrder() { return "asc"; }
		});
		if(types!=null)
		{
			for(SoftwareType type : types)
			{
				this.softwareTypeLimitDao.update(data, type, bingTimes);
			}
		}
		return this.formatCode(data.getCode());
	}
	/*
	 * 加载注册码对象
	 * @see com.examw.test.service.products.IRegistrationCodeService#loadRegistration(java.lang.String)
	 */
	@Override
	public Registration loadRegistration(String code) {
		return this.registrationDao.loadRegistration(this.cleanCodeFormat(code));
	}
	
	@Override
	public void activeRegistration(String code) throws Exception {
		boolean result = this.verificationFormat(code);
		if(!result) throw new Exception("注册码错误！");
		Registration data = this.registrationDao.loadRegistration(code);
		if(data == null) throw new Exception(String.format("注册码未登记！"));
		//必须未激活
		if(data.getStatus() != RegistrationStatus.NONE.getValue()){
			throw new Exception(String.format("注册码：%s!", this.loadStatusName(data.getStatus())));
		}
		//激活
		int limit = 12;
		if(data.getLimits() != null && data.getLimits().intValue() != 0)
		{
			limit = data.getLimits();
		}
		data.setStartTime(new Date());//激活时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data.getStartTime());
		calendar.add(Calendar.MONTH, limit);
		calendar.set(Calendar.HOUR_OF_DAY, 23);//时
		calendar.set(Calendar.MINUTE, 59);//分
		calendar.set(Calendar.SECOND, 59);//秒
		calendar.set(Calendar.MILLISECOND, 0);//微妙
		data.setEndTime(calendar.getTime());//过期时间
		data.setStatus(RegistrationStatus.ACTIVE.getValue());
	}
}