<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Error</title>
    </head>

    <body>
        Exception message: ${exceptionMessage} <br>
        <a href="<@spring.url '/'/>">Return to welcome page</a>
    </body>
</html>