package ins.platform.common;

/**
 * 险种代码
 * @author CSL
 *
 */
public final class Risk {

   /**
    * 旧商业险
    */
   public static final String DAA = "0101";
   /**
    * 新商业险
    */
   public static final String DAB = "0103";
   /**
    * 交强险
    */
   public static final String DQZ = "0107";
   /**
    * 旧单程提车险
    */
   public static final String DAT = "0105";
   /**
    * 新单程提车险
    */
   public static final String DAE = "0109";
   /**
    * 合约分入险
    */
   public static final String DAD = "0198";
   
   /***
    * 个人意外伤害保险定额产品 
    */
   public static final String PSD ="6130";
   
   /**
    * 
    */
   public static final String CYI = "9999";
   
   /**
    * 
    */
   public static final String ZLI = "3125";
   
   public static final String ZFG = "3116";
   
   public static final String JPB = "1207";
   
   /**
    * 是否旧商业险
    * @param riskcode
    * @return
    */
   public  static boolean isDAA(String riskcode){
	   
	   if(Risk.DAA.equals(riskcode)){
		   return true;
	   }
	   return false;
   }
   /**
    * 是否新商业险
    * @param riskcode
    * @return
    */
   public  static boolean isDAB(String riskcode){
	   
	   if(Risk.DAB.equals(riskcode)){
		   return true;
	   }
	   return false;
   }
   
   /**
    * 是否是商业险
    * @param riskcode
    * @return
    */
   public  static boolean isDAAorDAB(String riskcode){
	   
	   if(Risk.DAA.equals(riskcode)||Risk.DAB.equals(riskcode)){
		   return true;
	   }
	   return false;
   }
   /**
    * 是否交强险
    * @param riskcode
    * @return
    */
   public  static boolean isDQZ(String riskcode){
	   
	   if(Risk.DQZ.equals(riskcode)){
		   return true;
	   }
	   return false;
   }
   /**
    * 是否旧单程提车险
    * @param riskcode
    * @return
    */
   public  static boolean isDAT(String riskcode){
	   
	   if(Risk.DAT.equals(riskcode)){
		   return true;
	   }
	   return false;
   }
   /**
    * 是否新单程提车险
    * @param riskcode
    * @return
    */
   public  static boolean isDAE(String riskcode){
	   
	   if(Risk.DAE.equals(riskcode)){
		   return true;
	   }
	   return false;
   }
   /**
    * 是否合约分入险
    * @param riskcode
    * @return
    */
   public  static boolean isDAD(String riskcode){
	   
	   if(Risk.DAD.equals(riskcode)){
		   return true;
	   }
	   return false;
   }
   
   /**
    * 是否旧商业险
    * @param riskcode
    * @return
    */
   public  static boolean isPSD(String riskcode){
	   
	   if(Risk.PSD.equals(riskcode)){
		   return true;
	   }
	   return false;
   }
   
   /**
    * 是否是商业险的投保单
    * @param riskCode
    * @return
    */
   public static boolean isDAAorDABProposalNo(String proposalNo){
     if (proposalNo == null)
       return false;
     if ((proposalNo.startsWith("9"+Risk.DAA)) || (proposalNo.startsWith("9"+Risk.DAB))) {
       return true;
     }
     return false;
   }
   
   /**
    * 是否是交强险的暂存单
    * @param riskCode
    * @return
    */
   public static boolean isDQZProvisionNo(String provisionNo){
     if (provisionNo == null)
       return false;
     if (provisionNo.startsWith("0"+Risk.DQZ)) {
       return true;
     }
     return false;
   }
   /**
    * 是否是商业险的暂存单
    * @param riskCode
    * @return
    */
   public static boolean isDAAorDABProvisionNo(String provisionNo){
     if (provisionNo == null)
       return false;
     if ((provisionNo.startsWith("0"+Risk.DAA)) || (provisionNo.startsWith("0"+Risk.DAB))) {
       return true;
     }
     return false;
   }
   
   /**
    * 是否是交强险的投保单
    * @param riskCode
    * @return
    */
   public static boolean isDQZProposalNo(String proposalNo){
     if (proposalNo == null)
       return false;
     if (proposalNo.startsWith("9"+Risk.DQZ)) {
       return true;
     }
     return false;
   }
   /**
    * 是否是商业险的保单号
    * @param riskCode
    * @return
    */
   public static boolean isDAAorDABPolicyNo(String policyNo){
     if (policyNo == null)
       return false;
     if ((policyNo.startsWith("3"+Risk.DAA)) || (policyNo.startsWith("3"+Risk.DAB))) {
       return true;
     }
     return false;
   }
   
   /**
    * 通过保单号、批单号、或者投保单号获取险种代码
    * @param certiNo
    * @return
    */
   public static String getRiskCodeByCertiNo(String certiNo){
     if (certiNo == null){
    	 return "";
     }else if (certiNo.length() > 5) {
         return certiNo.substring(1, 5);
     }
     return "";
   }
   
   /**
    * 是否是交强险的保单号
    * @param riskCode
    * @return
    */
   public static boolean isDQZPolicyNo(String policyNo){
     if (policyNo == null)
       return false;
     if (policyNo.startsWith("3"+Risk.DQZ)) {
       return true;
     }
     return false;
   }
   
   /**
    * 是否车险：支持险种代码、险别代码及各种单（投保单、保单、批单，暂存单）
    * @param value 
    * @return
    */
   public static boolean isDA(String value) {
     if (value == null) 
    	 return false;
     if (value.equals("01")) {
       return true;
     }
     if (value.trim().length() > 5 && Risk.isDA(value.substring(1, 5))) {
         return true;
     }
     if ((value.length() >= 2) && (value.substring(0, 2).equals("01"))) {
       return true;
     }
     return false;
   }
   /**
    * 是否车险业务号：（投保单、保单、批单，暂存单）
    * @param value 
    * @return
    */
   public static boolean isCarBusinessNo(String value) {
    
     if (value.trim().length() > 5 && Risk.isDA(value.substring(1, 5))) {
         return true;
     }
     return false;
   }
   /**
    * 是否非车险业务号：（投保单、保单、批单，暂存单）
    * @param value 
    * @return
    */
   public static boolean isNoCarBusinessNo(String value) {
    
     return !isCarBusinessNo(value);
   }
   /**
    * 是否EA险
    * @param riskCode
    * @return
    */
   public static boolean isEA(String riskCode){
     if (riskCode == null) return false;
     if (riskCode.equals("61")) {
       return true;
     }
     if ((riskCode.length() >= 2) && (riskCode.substring(0, 2).equals("61"))) {
       return true;
     }
     return false;
   }
   /**
    * 是否UA险
    * @param code
    * @return
    */
   public static boolean isUA(String riskCode){
     if (riskCode == null) return false;
     if (riskCode.equals("71")) {
       return true;
     }
     if ((riskCode.length() >= 2) && (riskCode.substring(0, 2).equals("71"))) {
       return true;
     }
     return false;
   }

   /**
    * 是否YA险
    * @param code
    * @return
    */
   public static boolean isYA(String code){
     if (code == null) return false;
     if (code.equals("51")) {
       return true;
     }
     if ((code.length() >= 2) && (code.substring(0, 2).equals("51"))) {
       return true;
     }
     return false;
   }
   
   /**
    * 是否3116
    * @param riskCode
    * @return
    */
   public static boolean isZFG(String riskCode){
     if (riskCode == null)
       return false;
     if (riskCode.equals(Risk.ZFG)) {
       return true;
     }
     return false;
   }
   
   /**
    * 是否1207
    * @param riskCode
    * @return
    */
   public static boolean isJPB(String riskCode){
     if (riskCode == null)
       return false;
     if (riskCode.equals(Risk.JPB)) {
       return true;
     }
     return false;
   }

}
