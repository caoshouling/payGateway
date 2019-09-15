<!DOCTYPE html>
<html  lang="zh-CN">
<head>
 <#include "include-head.ftl"/>
<title>模板页面</title>
</head>
<body>
    <h1>Spring MVC + ${message!}  freemarker模板页面<  </h1>
    
    <button name="logout" id = "logoutButton"> logout</button>
    
    
     <#include "include-body.ftl"/>
</body>
</html>