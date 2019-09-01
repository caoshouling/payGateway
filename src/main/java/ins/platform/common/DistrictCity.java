package ins.platform.common;

public class DistrictCity {
	
	/**
	 * 广州
	 */
	public static final String GuangZhou = "440100";
	/**
	 * 珠海
	 */
	public static final String ZhuHai = "440400";
	/**
	 * 江门
	 */
	public static final String JiangMen = "440700";
	
	/**
	 * 佛山
	 */ 
	public static final String FoShan = "440600";
	
	/**
	 * 湛江  
	 */ 
	public static final String ZhanJiang = "440800";
	/**
	 * 清远 
	 */ 
	public static final String  QingYuan= "441800";
	/**
	 * 惠州
	 */ 
	public static final String  HuiZhou= "441300";
	/**
	 * 茂名
	 */ 
	public static final String MaoMing = "440900";
	
	/**
	 * 南沙
	 */ 
	public static final String NanSha = "440115";
	
	/**
	 * 判断是否为广州
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isGuangZhou(String comCode) {
		if (comCode != null && comCode.length() >= 4) {
			if (DistrictCity.GuangZhou.startsWith(comCode.substring(0, 4))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为茂名
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isMaoMing(String comCode) {
		if (comCode != null && comCode.length() >= 4) {
			if (DistrictCity.MaoMing.startsWith(comCode.substring(0, 4))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为惠州
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isHuiZhou(String comCode) {
		if (comCode != null && comCode.length() >= 4) {
			if (DistrictCity.HuiZhou.startsWith(comCode.substring(0, 4))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否为清远 
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isQingYuan(String comCode) {
		if (comCode != null && comCode.length() >= 4) {
			if (DistrictCity.QingYuan.startsWith( comCode.substring(0, 4))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为湛江 
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isZhanJiang(String comCode) {
		if (comCode != null && comCode.length() >= 4) {
			if (DistrictCity.ZhanJiang.startsWith(comCode.substring(0, 4))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为珠海
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isZhuHai(String comCode) {
		if (comCode != null && comCode.length() >= 4) {
			if (DistrictCity.ZhuHai.startsWith(comCode.substring(0, 4))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为江门
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isJiangMen(String comCode) {
		if (comCode != null && comCode.length() >= 4) {
			if (DistrictCity.JiangMen.startsWith ( comCode.substring(0, 4))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为佛山
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isFoShan(String comCode) {
		if (comCode != null && comCode.length() >= 4) {
			if (DistrictCity.FoShan.startsWith(
					comCode.substring(0, 4))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否为南沙
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isNanSha(String comCode) {
		if (comCode != null && comCode.length() >= 6) {
			if (DistrictCity.NanSha.startsWith(comCode.substring(0, 6))) {
				return true;  
			}
		}
		return false;
	}
}
