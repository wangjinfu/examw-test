package com.examw.test.service.library;

/**
 * 试卷状态枚举类。
 * @author yangyong.
 * @since 2014-08-07.
 */
public enum PaperStatus {
	/**
	 * 未审核。
	 */
	NONE(0x00),
	/**
	 * 已审核。
	 */
	AUDIT(0x01),
	/**
	 * 已发布。
	 */
	PUBLISH(0x02);
	
	private int value;
	//构造函数。
	private PaperStatus(int value){
		this.value = value;
	}
	/**
	 * 获取枚举值。
	 * @return 枚举值。
	 */
	public Integer getValue(){
		return this.value;
	}
	/**
	 * 枚举转换。
	 * @param value
	 * 枚举值。
	 * @return
	 * 枚举对象。
	 */
	public static PaperStatus convert(Integer value){
		PaperStatus result = PaperStatus.NONE;
		switch(value){
			case 0x01: 
				result = PaperStatus.AUDIT;
			break;
			case 0x02:
				result = PaperStatus.PUBLISH;
			break;
			default:break;
		}
		return result;
	}
}