package ins.platform.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ins.platform.kit.StrKit;


public class DistrictProvince {
	// 总部
	public static final String HeadQuarter = "000000";
	// 北京（直辖市）
	public static final String BeiJin = "110000";
	// 天津(直辖市)
	public static final String TianJin = "120000";
	// 河北省
	public static final String HeBei = "130000";
	// 山西省
	public static final String ShanXiJi = "140000";
	// 内蒙古
	public static final String NeiMengGu = "150000";
	// 辽宁省
	public static final String LiaoNing = "210000";
	// 吉林省
	public static final String JiLin = "220000";
	// 黑龙江
	public static final String HeiLongJiang = "230000";
	// 上海(直辖市)
	public static final String ShangHai = "310000";
	// 江苏省
	public static final String JiangSu = "320000";
	// 浙江省
	public static final String ZheJiang = "330000";
	// 安徽省
	public static final String AnHui = "340000";
	// 福建省
	public static final String FuJian = "350000";
	// 江西省
	public static final String JiangXi = "360000";
	// 山东省
	public static final String ShanDong = "370000";
	// 河南省
	public static final String HeNan = "410000";
	// 湖北省
	public static final String HuBei = "420000";
	// 湖南省
	public static final String HuNan = "430000";
	// 广东省
	public static final String GuangDong = "440000";
	// 广西壮族自治区
	public static final String GuangXi = "450000";
	// 海南省
	public static final String HaiNan = "460000";
	// 重庆市(直辖市)
	public static final String ChongQing = "500000";
	// 四川省
	public static final String SiChuan = "510000";
	// 贵州省
	public static final String GuiZhou = "520000";
	// 云南省
	public static final String YunNan = "530000";
	// 西藏
	public static final String XiZang = "540000";
	// 陕西省
	public static final String ShanXiShan = "610000";
	// 甘肃省
	public static final String GanSu = "620000";
	// 青海
	public static final String QingHai = "630000";
	// 宁夏
	public static final String Ningxia = "640000";
	// 新疆
	public static final String XinJiang = "650000";
	// 深圳(计划单列市)
	public static final String ShenZhen = "440300";
	// 大连(计划单列市)
	public static final String DaLian = "210200";
	// 厦门(计划单列市)
	public static final String XiaMen = "350200";
	// 宁波(计划单列市)
	public static final String NingBo = "330200";
	// 青岛(计划单列市)
	public static final String QingDao = "370200";
	
	
	static Set<String> DistrictProvinceSet =new HashSet<String>();
	/***
	 *   当成分公司的市
	 * */
	static Set<String> ProvinceLevelCitySet =new HashSet<String>();
	
	static Map<String,String> Province_Code_Name_Map =new HashMap<>();
	/**省级行政区
	 *   23个省：河北省、山西省、吉林省、辽宁省、黑龙江省、陕西省、甘肃省、青海省、山东省、福建省、
	 *         浙江省、台湾省、河南省、湖北省、湖南省、     江西省、江苏省、安徽省、广东省、海南省、
	 *         四川省、贵州省、云南省。
	 *         
	 *   4个直辖市：北京市、上海市、天津市、重庆市。
	 *   5个自治区：内蒙古自治区、新疆维吾尔自治区、宁夏回族自治区、广西壮族自治区、西藏自治区。
	 *   2个特别行政区：香港特别行政区、澳门特别行政区。
	 * */
	
	
	/**
	 *   注：目前只有22个，没加台湾
	 * */
	static Set<String>  ProvinceRegion_RealProvinceSet =new HashSet<>(23);//省  
	static Set<String>  ProvinceRegion_MunicipalitySet =new HashSet<>(4);//直辖市
	static Set<String>  ProvinceRegion_AutonomousRegionSSet =new HashSet<>(5);//5个自治区
	
	static {
			//5个自治区：内蒙古自治区
		ProvinceRegion_AutonomousRegionSSet.add(NeiMengGu);
			//5个自治区：新疆维吾尔自治区
		ProvinceRegion_AutonomousRegionSSet.add(XinJiang);
			//5个自治区：宁夏回族自治区
		ProvinceRegion_AutonomousRegionSSet.add(Ningxia);
			//5个自治区：广西壮族自治区
		ProvinceRegion_AutonomousRegionSSet.add(GuangXi);
			//5个自治区：西藏自治区。
		ProvinceRegion_AutonomousRegionSSet.add(XiZang);
		
			//4个直辖市 -北京
		ProvinceRegion_MunicipalitySet.add(BeiJin);
			//4个直辖市 -上海
		ProvinceRegion_MunicipalitySet.add(ShangHai);
			//4个直辖市 -天津
		ProvinceRegion_MunicipalitySet.add(TianJin);
		    //4个直辖市 -重庆
		ProvinceRegion_MunicipalitySet.add(ChongQing);
		
			// 河北省
		ProvinceRegion_RealProvinceSet.add( HeBei);
			// 山西省
		ProvinceRegion_RealProvinceSet.add( ShanXiJi);
			// 辽宁省
		ProvinceRegion_RealProvinceSet.add( LiaoNing);
			// 吉林省
		ProvinceRegion_RealProvinceSet.add( JiLin);
			// 黑龙江
		ProvinceRegion_RealProvinceSet.add( HeiLongJiang);
			// 江苏省
		ProvinceRegion_RealProvinceSet.add( JiangSu);
			// 浙江省
		ProvinceRegion_RealProvinceSet.add( ZheJiang);
			// 安徽省
		ProvinceRegion_RealProvinceSet.add( AnHui);
			// 福建省
		ProvinceRegion_RealProvinceSet.add( FuJian);
			// 江西省
		ProvinceRegion_RealProvinceSet.add( JiangXi);
			// 山东省
		ProvinceRegion_RealProvinceSet.add( ShanDong);
			// 河南省
		ProvinceRegion_RealProvinceSet.add( HeNan);
			// 湖北省
		ProvinceRegion_RealProvinceSet.add( HuBei);
			// 湖南省
		ProvinceRegion_RealProvinceSet.add( HuNan);
			// 广东省
		ProvinceRegion_RealProvinceSet.add( GuangDong);
			// 海南省
		ProvinceRegion_RealProvinceSet.add( HaiNan);
			// 四川省
		ProvinceRegion_RealProvinceSet.add( SiChuan);
			// 贵州省
		ProvinceRegion_RealProvinceSet.add( GuiZhou);
			// 云南省
		ProvinceRegion_RealProvinceSet.add( YunNan);
			// 陕西省
		ProvinceRegion_RealProvinceSet.add( ShanXiShan);
			// 甘肃省
		ProvinceRegion_RealProvinceSet.add( GanSu);
			// 青海省
		ProvinceRegion_RealProvinceSet.add( QingHai);
	
	
	
	
	
		   // 总部
		DistrictProvinceSet.add( HeadQuarter);
			// 北京（直辖市）
		DistrictProvinceSet.add( BeiJin);
			// 天津(直辖市)
		DistrictProvinceSet.add( TianJin);
			// 河北省
		DistrictProvinceSet.add( HeBei);
			// 山西省
		DistrictProvinceSet.add( ShanXiJi);
			// 内蒙古
		DistrictProvinceSet.add( NeiMengGu);
			// 辽宁省
		DistrictProvinceSet.add( LiaoNing);
			// 吉林省
		DistrictProvinceSet.add( JiLin);
			// 黑龙江
		DistrictProvinceSet.add( HeiLongJiang);
			// 上海(直辖市)
		DistrictProvinceSet.add( ShangHai);
			// 江苏省
		DistrictProvinceSet.add( JiangSu);
			// 浙江省
		DistrictProvinceSet.add( ZheJiang);
			// 安徽省
		DistrictProvinceSet.add( AnHui);
			// 福建省
		DistrictProvinceSet.add( FuJian);
			// 江西省
		DistrictProvinceSet.add( JiangXi);
			// 山东省
		DistrictProvinceSet.add( ShanDong);
			// 河南省
		DistrictProvinceSet.add( HeNan);
			// 湖北省
		DistrictProvinceSet.add( HuBei);
			// 湖南省
		DistrictProvinceSet.add( HuNan);
			// 广东省
		DistrictProvinceSet.add( GuangDong);
			// 广西壮族自治区
		DistrictProvinceSet.add( GuangXi);
			// 海南省
		DistrictProvinceSet.add( HaiNan);
			// 重庆市(直辖市)
		DistrictProvinceSet.add( ChongQing);
			// 四川省
		DistrictProvinceSet.add( SiChuan);
			// 贵州省
		DistrictProvinceSet.add( GuiZhou);
			// 云南省
		DistrictProvinceSet.add( YunNan);
			// 西藏
		DistrictProvinceSet.add( XiZang);
			// 陕西省
		DistrictProvinceSet.add( ShanXiShan);
			// 甘肃省
		DistrictProvinceSet.add( GanSu);
			// 青海
		DistrictProvinceSet.add( QingHai);
			// 宁夏
		DistrictProvinceSet.add( Ningxia);
			// 新疆
		DistrictProvinceSet.add( XinJiang);
			// 深圳(计划单列市)
		DistrictProvinceSet.add( ShenZhen);
			// 大连(计划单列市)
		DistrictProvinceSet.add( DaLian);
			// 厦门(计划单列市)
		DistrictProvinceSet.add( XiaMen);
			// 宁波(计划单列市)
		DistrictProvinceSet.add( NingBo);
			// 青岛(计划单列市)
		DistrictProvinceSet.add( QingDao);
		
		/***当成分公司的市*/
		//广东的深圳
		ProvinceLevelCitySet.add(ShenZhen);
		//浙江的宁波
		ProvinceLevelCitySet.add(NingBo);
		//山东的青岛
		ProvinceLevelCitySet.add(QingDao);
		//福建的厦门
		ProvinceLevelCitySet.add(XiaMen);
		//辽宁的大连
		ProvinceLevelCitySet.add(DaLian);
		
		
		Province_Code_Name_Map.put(HeadQuarter, "总部");
		Province_Code_Name_Map.put(BeiJin, "北京");
		Province_Code_Name_Map.put(TianJin, "天津");
		Province_Code_Name_Map.put(HeBei, "河北");
		Province_Code_Name_Map.put(ShanXiJi, "山西");
		Province_Code_Name_Map.put(NeiMengGu, "内蒙古");
		Province_Code_Name_Map.put(LiaoNing, "辽宁");
		Province_Code_Name_Map.put(JiLin, "吉林");
		Province_Code_Name_Map.put(HeiLongJiang, "黑龙江");
		Province_Code_Name_Map.put(ShangHai, "上海");
		Province_Code_Name_Map.put(JiangSu, "江苏");
		Province_Code_Name_Map.put(ZheJiang, "浙江");
		Province_Code_Name_Map.put(AnHui, "安徽");
		Province_Code_Name_Map.put(FuJian, "福建");
		Province_Code_Name_Map.put(JiangXi, "江西");
		Province_Code_Name_Map.put(ShanDong, "山东");
		Province_Code_Name_Map.put(HeNan, "河南");
		Province_Code_Name_Map.put(HuBei, "湖北");
		Province_Code_Name_Map.put(HuNan, "湖南");
		Province_Code_Name_Map.put(GuangDong, "广东");
		Province_Code_Name_Map.put(GuangXi, "广西");
		Province_Code_Name_Map.put(HaiNan, "海南");
		Province_Code_Name_Map.put(ChongQing, "重庆");
		Province_Code_Name_Map.put(SiChuan, "四川");
		Province_Code_Name_Map.put(GuiZhou, "贵州");
		Province_Code_Name_Map.put(YunNan, "云南");
		Province_Code_Name_Map.put(XiZang, "西藏");
		Province_Code_Name_Map.put(ShanXiShan, "陕西");
		Province_Code_Name_Map.put(GanSu, "甘肃");
		Province_Code_Name_Map.put(QingHai, "青海");
		Province_Code_Name_Map.put(Ningxia, "宁夏");
		Province_Code_Name_Map.put(XinJiang, "新疆");
		Province_Code_Name_Map.put(ShenZhen, "深圳");
		Province_Code_Name_Map.put(DaLian, "大连");
		Province_Code_Name_Map.put(XiaMen, "厦门");
		Province_Code_Name_Map.put(NingBo, "宁波");
		Province_Code_Name_Map.put(QingDao, "青岛");
		
	}
	

	/**
	 * 是否走广东平台（目前除了深圳和上海，其他的都是广东平台）,对应旧系统的Pubtools.getBICheckQurey(comCode)方法
	 * @param comCode
	 * @return
	 */
	public static boolean isBICheckQurey(String comCode){
		 
		if(!DistrictProvince.isShenZhen(comCode) && !DistrictProvince.isShangHai(comCode) ){
			
			return true;
		}
		return false;
	}
	/**
     *  获取分公司代码(区分类似广东和深圳等机构)
	 * @param comCode
	 * @return
	 */
	public static String getProvinceComCode(String comCode) {
		
		    String provinceComCode = comCode;

			if(comCode != null && comCode.length() >= 4){
				String comCode_2 = comCode.substring(0, 2);
				String comCode_4  = comCode.substring(0, 4);
                if(DistrictProvinceSet.contains(comCode_4 + "00")){
                	provinceComCode = comCode_4 + "0000";
                } else if(DistrictProvinceSet.contains(comCode_2 + "0000")){
                	provinceComCode = comCode_2 + "000000";
                }
			}
		    return provinceComCode;
	}
	/**
     *  获取名称
	 * @param comCode
	 * @return
	 */
	public static String getProvinceComName(String comCode) {
		
		    String provinceComCode = getProvinceComCode(comCode);
		    if(StrKit.isNoEmpty(provinceComCode) && provinceComCode.length() == 8){
		    	return Province_Code_Name_Map.get(provinceComCode.substring(0,6));
		    }
		    return "";
	}
	/**
	 *  是否是23个省中的一个
	 * @param comCode
	 * @return
	 */
	public static boolean isRealProvinceRegion(String comCode) {
		
        boolean flag = false;		
		if(comCode != null && comCode.length() >= 4){
			String provinceComCode = getProvinceComCode(comCode);
			comCode  = provinceComCode.substring(0, 6);
			if(ProvinceRegion_RealProvinceSet.contains(comCode)){
				flag = true;
			} 
		}
		return flag;
	}
	/**
	 *  是否是市级别的分公司
	 * @param comCode
	 * @return
	 */
	public static boolean isProvinceLevelCity(String comCode) {
		
		boolean flag = false;		
		if(comCode != null && comCode.length() >= 4){
			String comCode_4  = comCode.substring(0, 4);
			if(ProvinceLevelCitySet.contains(comCode_4 + "00")){
				flag = true;
			} 
		}
		return flag;
	}
	
	/**
	 * 判断是否为总部
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isHeadQuarter(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.HeadQuarter.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否为北京
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isBeiJin(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.BeiJin.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否为天津
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isTianJin(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.TianJin.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否为河北
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isHeBei(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.HeBei.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为陕西
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isShanXiJi(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.ShanXiJi.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为辽宁
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isLiaoNing(String comCode) {
		if (comCode != null && comCode.length() >= 4&&!isDaLian(comCode)) {
			if (DistrictProvince.LiaoNing.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为吉林
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isJiLin(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.JiLin.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为黑龙江
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isHeiLongJiang(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.HeiLongJiang.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为上海
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isShangHai(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.ShangHai.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为江苏
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isJiangSu(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.JiangSu.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为浙江
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isZheJiang(String comCode) {
		if (comCode != null && comCode.length() >= 4&&!isNingBo(comCode)) {
			if (DistrictProvince.ZheJiang.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为安徽
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isAnHui(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.AnHui.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为福建
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isFuJian(String comCode) {
		if (comCode != null && comCode.length() >= 4&&!isXiaMen(comCode)) {
			if (DistrictProvince.FuJian.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为江西
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isJiangXi(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.JiangXi.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为山东
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isShanDong(String comCode) {
		if (comCode != null && comCode.length() >= 4&&!isQingDao(comCode)) {
			if (DistrictProvince.ShanDong.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为河南
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isHeNan(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.HeNan.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为湖北
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isHuBei(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.HuBei.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为湖南
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isHuNan(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.HuNan.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为广东
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isGuangDong(String comCode) {
		if (comCode != null && comCode.length() >= 4&&!isShenZhen(comCode)) {
			if (DistrictProvince.GuangDong.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为广西
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isGuangXi(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.GuangXi.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为海南
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isHaiNan(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.HaiNan.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为重庆
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isChongQing(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.ChongQing.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为四川
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isSiChuan(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.SiChuan.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为贵州
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isGuiZhou(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.GuiZhou.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为贵州
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isQingHai(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.QingHai.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为云南
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isYunNan(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.YunNan.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为西藏
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isXiZang(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.XiZang.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为陕西
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isShanXiShan(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.ShanXiShan.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为甘肃
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isGanSu(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.GanSu.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为内蒙古
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isNeiMengGu(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.NeiMengGu.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为宁夏
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isNingxia(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.Ningxia.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为新疆
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isXinJiang(String comCode) {
		if (comCode != null && comCode.length() >= 2) {
			if (DistrictProvince.XinJiang.startsWith(
					comCode.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为深圳
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isShenZhen(String comCode) {
		if (comCode != null && comCode.length() >= 4) {
			if (DistrictProvince.ShenZhen.startsWith(
					comCode.substring(0, 4))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为大连
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isDaLian(String comCode) {
		if (comCode != null && comCode.length() >= 4) {
			if (DistrictProvince.DaLian.startsWith(
					comCode.substring(0, 4))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为厦门
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isXiaMen(String comCode) {
		if (comCode != null && comCode.length() >= 4) {
			if (DistrictProvince.XiaMen.startsWith(
					comCode.substring(0, 4))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为宁波
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isNingBo(String comCode) {
		if (comCode != null && comCode.length() >= 4) {
			if (DistrictProvince.NingBo.startsWith(
					comCode.substring(0, 4))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否为青岛
	 * 
	 * @param comCode
	 * @return
	 */
	public static boolean isQingDao(String comCode) {
		if (comCode != null && comCode.length() >= 4) {
			if (DistrictProvince.QingDao.startsWith(
					comCode.substring(0, 4))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否为直辖市
	 * @param comCode
	 * @return
	 */
	public static boolean isMunicipality(String comCode){
		if(DistrictProvince.isShangHai(comCode)||DistrictProvince.isTianJin(comCode) 
				|| DistrictProvince.isChongQing(comCode) || DistrictProvince.isBeiJin(comCode)) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		// 总部
		System.out.println(isRealProvinceRegion("44030000"));
		
	}
}
