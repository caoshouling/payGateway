package ins.platform.validator;

public abstract class BusinessCheck<T> {

	public abstract String check(T t) throws Exception;


}
