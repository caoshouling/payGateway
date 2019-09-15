<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <#include "../include-head.ftl"/>
    <title>登录页面</title>
   
  </head>
  <body>
    <h1>登录页面</h1>
    <div class = "loginForm">
        
	     <#if Session["SPRING_SECURITY_LAST_EXCEPTION"]?? && Session["SPRING_SECURITY_LAST_EXCEPTION"].message??>

	           <font color="red">${Session["SPRING_SECURITY_LAST_EXCEPTION"].message}</font>
	     </#if>
	  
		<form action="${ctx}/login"  id="userForm" method="POST" class="form-horizontal">
		  <div class="form-group">
		    <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
		    <div class="col-sm-10">
		      <input type="text" name = "userName" class="form-control" id="inputEmail3" placeholder="username">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
		    <div class="col-sm-10">
		      <input type="password" name = "password"  class="form-control" id="inputPassword3" placeholder="Password">
		    </div>
		  </div>
		  <div class="form-group">
            
            <label for="vercode" class="col-sm-2 control-label">验证码</label>
		    <div class="col-sm-8">
		      <input type="text" name="vercode"  class="form-control vcodeImgText"  id="vercode" placeholder="图形验证码">
		      <img src="${ctx}/auth/vcode"  id="user-vercode" class="vcodeImg">
		    </div>
          </div>
		  <div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		      <div class="checkbox">
		        <label>
		          <input type="checkbox" name = "remenberMe"> 记住我
		        </label>
		      </div>
		    </div>
		  </div>
	      <div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		      <button   type="submit" class="btn btn-default">提交</button>
		    </div>
		  </div>
	</form>

  </div>

  <#include "../include-body.ftl"/>
 
 </body>
</html>