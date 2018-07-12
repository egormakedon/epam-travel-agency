<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "index.title"/></title>
    </head>

    <body>
    <#if user??>
        ${user}
    </#if>
        <@spring.message "general.language"/> : <a href="<@spring.url "/?locale=en"/>"><@spring.message "general.english"/></a> | <a href="<@spring.url "/?locale=ru"/>"><@spring.message "general.russian"/></a> <br>
        <a href="<@spring.url "/hotel"/>"><@spring.message "index.hotels"/></a> <br>
        <a href="<@spring.url "/tour"/>"><@spring.message "index.tours"/></a> <br>
        <a href="<@spring.url "/user"/>"><@spring.message "index.users"/></a> <br>
        <a href="<@spring.url "/review"/>"><@spring.message "index.reviews"/></a> <br>
    </body>
</html>