package ins.platform.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload; 

/**
 * 校验声明
 * @author Administrator
 *
 */
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE,ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BusinessValidator.class)
@Documented
public @interface BusinessValid {

    //默认错误消息
    String message() default "";

    //分组
    Class<?>[] groups() default { };

    //负载
    Class<? extends Payload>[] payload() default { };
    

	Class<?>[] value();
	
	Class<? extends BusinessInitBeforeValidator>[] initClass() default {};
    
    
 
}
