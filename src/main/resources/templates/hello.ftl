<!DOCTYPE html>  
<html>  
    <head>  
        <title>Hello World!</title>  
    </head>  
    <body>  
        <h1>Hello ${remoteUser!''}</h1>  
        <form action="/logout" method="post">  
            <input type="submit" value="Sign Out"/>  
        </form>  
    </body>  
</html>  