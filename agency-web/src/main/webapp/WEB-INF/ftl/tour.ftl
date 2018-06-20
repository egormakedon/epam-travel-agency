<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "tour.title"/></title>
    </head>

    <body>
        <@spring.message "general.language"/> : <a href="?locale=en"><@spring.message "general.english"/></a> | <a href="?locale=ru"><@spring.message "general.russian"/></a> <br>

        <form action="<@spring.url "/tour/load"/>" method="post" enctype="multipart/form-data">
            <input type="file" name="file" accept="application/json" required>
            <input type="submit" value="<@spring.message "tour.upload"/>">
        </form>

        <br>

        <form action="<@spring.url "/tour/get"/>" method="get">
            <input type="text" placeholder="<@spring.message "review.id"/>" name="id">
            <input type="submit" value="<@spring.message "general.find"/>">
        </form>

        <br>

        <a href="<@spring.url "/"/>"><@spring.message "general.return.to.welcome.page"/></a>

        <br><br>

        <#if RequestParameters.result??>
            <#list RequestParameters.result as tour>
                <p>${tour}
            </#list>
        <#elseif result??>
            <#list result as tour>
                <p>${tour}
            </#list>
        </#if>
    </body>
</html>