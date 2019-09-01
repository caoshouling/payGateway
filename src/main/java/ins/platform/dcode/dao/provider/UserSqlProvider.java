package ins.platform.dcode.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider {
    /**
     * SqlProvider说明：
     * 1.如果dao方法中只有一个参数且没有加@Param修饰，那么SqlProvider方法中可以直接定义
     * 2.如果dao方法中有多个参数 或者 加了@Param修饰，那么SqlProvider方法中只能用Map接收
     * 3.参数的获取可以通过序号获取，如Map.get("0")代表第一个参数
     * 4.多个参数加了@Param，那么除了序号获取，还可以通过Map.get(key)，key为@Param定义的值。
     *   或者用表达式取，如#{userName}
     *   推荐：@Param 加 表达式#{}的方式
     * @param userName
     * @param password
     * @return
     */
	public String selectUserByUserNameAndPassword(final Map<String, Object> params) {

		return new SQL() {
			{

				SELECT(" * ");
				FROM("sys_user");
				WHERE("user_Name =  #{userName}");
				AND();
				WHERE("password =   #{password} ");

			}
		}.toString();
	}

	public static void main(String[] args) {
		String SQL = new SQL() {
			{

				SELECT(" * ");
				FROM("sys_user");
				WHERE("userName =  #{userName}");
				AND();
				WHERE("password =  #{password}");

			}
		}.toString();
		System.out.println(SQL);
	}
}
