package ins.platform.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * 业务层级校验器
 * @author Administrator
 *
 */
public class BusinessValidator implements ConstraintValidator<BusinessValid,Object> {
	
	@Autowired
	private ApplicationContext ctx;

	private BusinessCheck[] businessChecks;
	
	private BusinessInitBeforeValidator[] businessInitBeforeValidators;
	
	protected static final Logger logger = LoggerFactory.getLogger(BusinessValidator.class);

	@Override
	public void initialize(BusinessValid anno) {
			
		businessChecks=new BusinessCheck[anno.value().length];
		for(int i=0;i<anno.value().length;i++){
			businessChecks[i]=(BusinessCheck) ctx.getBean(anno.value()[i]);
		}
		businessInitBeforeValidators = new BusinessInitBeforeValidator[anno.initClass().length];
		if(anno.initClass() != null){
			for(int i=0;i<anno.initClass().length;i++){
			  businessInitBeforeValidators[i]=(BusinessInitBeforeValidator) ctx.getBean(anno.initClass()[i]);
			}
		}
		
	} 

	@Override
	public boolean isValid(Object target, ConstraintValidatorContext context) {
		String msg =  "";
		for(BusinessInitBeforeValidator businessInitBeforeValidator : businessInitBeforeValidators){
			try{
				if(businessInitBeforeValidator != null){
					businessInitBeforeValidator.init(target);
				}
			}catch(Exception e){
				msg = "数据校验出现系统异常："+ e.getMessage();
				logger.error(msg,e);
			}
			if(!StringUtils.isEmpty( msg)){
				context.disableDefaultConstraintViolation();  
				context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
				return false;
			}
		}
		for(BusinessCheck businessCheck:businessChecks){
			
			try{
				msg = businessCheck.check(target);
				
			}catch(Exception e){
				msg = "数据校验出现系统异常："+ e.getMessage();
				logger.error(msg,e);
			}
			if(!StringUtils.isEmpty( msg)){
				context.disableDefaultConstraintViolation();  
				context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
				return false;
			}
		}
		return true;
	}
 

}
