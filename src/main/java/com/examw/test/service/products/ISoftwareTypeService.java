package com.examw.test.service.products;

import java.util.List;

import com.examw.test.model.products.SoftwareTypeInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 软件类型服务接口。
 * @author fengwei.
 * @since 2014年8月12日 上午8:28:05.
 */
public interface ISoftwareTypeService extends IBaseDataService<SoftwareTypeInfo>{
	/**
	 * 加载最大代码值。
	 * @return
	 */
	Integer loadMaxCode();
	/**
	 * 加载全部软件类型集合。
	 * @return
	 */
	List<SoftwareTypeInfo> loadAllSoftwareTypes();
}