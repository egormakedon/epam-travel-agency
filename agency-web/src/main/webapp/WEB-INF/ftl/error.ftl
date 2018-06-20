<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "error.title"/></title>
    </head>

    <body>
        <@spring.message "general.language"/> : <a href="<@spring.url "/error?locale=en"/>"><@spring.message "general.english"/></a> | <a href="<@spring.url "/error?locale=ru"/>"><@spring.message "general.russian"/></a> <br>
        <@spring.message "error.exception.message"/> : <#if RequestParameters.exceptionMessage??>${RequestParameters.exceptionMessage}</#if> <br>
        <a href="<@spring.url "/"/>"><@spring.message "general.return.to.welcome.page"/></a>
    </body>
</html>