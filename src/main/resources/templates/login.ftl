<!DOCTYPE html>  
<html>  
   <head>  
        <title>Spring Security Example </title>  
    </head>  
    <body>  
    <#if error?? >
        <div>  
            Invalid username and password.  
        </div>  
    </#if>
    <#if logout?? >
        <div>  
            You have been logged out.  
        </div> 
    </#if> 
        <form action="/login" method="post">  
            <div><label> 用户名: <input type="text" name="username"/> </label></div>  
            <div><label> 密码: <input type="password" name="password"/> </label></div>  
            <div><input type="submit" value="登陆"/></div>  
            <input type="checkbox" name="remember-me" value="true" th:checked="checked"/><p>Remember me</p>  
        </form>  
    </body>  
</html>  