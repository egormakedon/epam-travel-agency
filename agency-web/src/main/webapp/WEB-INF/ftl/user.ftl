<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "user.title"/></title>
    </head>

    <body>
        <@spring.message "general.language"/> : <a href="?locale=en"><@spring.message "general.english"/></a> | <a href="?locale=ru"><@spring.message "general.russian"/></a> <br>
    </body>
</html>