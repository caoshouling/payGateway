-----------------------------------------swagger注解-----------------------------------------
   swagger通过注解表明该接口会生成文档，包括接口名、请求方法、参数、返回信息的等等。

	@Api：修饰整个类，描述Controller的作用 
    	value="该参数没什么意义，在UI界面上也看到，所以不需要配置"
    	tags="说明该类的作用，可以在UI界面上看到的注解"
	@ApiOperation：描述一个类的一个方法，或者说一个接口 
	 	value="说明方法的用途、作用"
    	notes="方法的备注说明
	@ApiParam：单个参数描述 
	@ApiModel：用对象来接收参数 ，用于响应类上，表示一个返回响应数据的信息
	@ApiProperty：用对象接收参数时，描述对象的一个字段 

	@ApiIgnore：默认会添加所有的controller请求的方法，使用该注解忽略这个API 
	@ApiError ：发生错误返回的信息 
	@ApiImplicitParam：一个请求参数 ，可以用在@ApiImplicitParams中
	    name：参数名
        value：参数的汉字说明、解释
        required：参数是否必须传
        paramType：参数放在哪个地方
            · header --> 请求参数的获取：@RequestHeader
            · query --> 请求参数的获取：@RequestParam
            · path（用于restful接口）--> 请求参数的获取：@PathVariable
            · body（不常用）
            · form（不常用）    
        dataType：参数类型，默认String，其它值dataType="Integer"       
        defaultValue：参数的默认值
        
	@ApiImplicitParams：多个请求参数，
	例如：
	   @ApiImplicitParams({
   			 @ApiImplicitParam(name="mobile",value="手机号",required=true,paramType="form"),
    		 @ApiImplicitParam(name="password",value="密码",required=true,paramType="form"),
    		 @ApiImplicitParam(name="age",value="年龄",required=true,paramType="form",dataType="Integer")
       })
	@ApiResponse：HTTP响应其中1个描述 
	   code：数字，例如400
       message：信息，例如"请求参数没填好"
       response：抛出异常的类
	
	@ApiResponses：HTTP响应整体描述 	   
	       模式和@ApiImplicitParams类似，即多个@ApiResponse的数组。
	
    
 
 

 

 


	
