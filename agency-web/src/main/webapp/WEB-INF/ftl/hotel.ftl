<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "hotel.title"/></title>
    </head>

    <body>
        <@spring.message "general.language"/> : <a href="?locale=en"><@spring.message "general.english"/></a> | <a href="?locale=ru"><@spring.message "general.russian"/></a> <br>

        <form action="<@spring.url "/hotel/add"/>" method="post">
            <input type="text" placeholder="<@spring.message "hotel.name"/>" name="name" required>
            <input type="text" placeholder="<@spring.message "hotel.phone"/>" name="phone" required>
            <input type="text" placeholder="<@spring.message "hotel.stars"/>" name="stars" required>
            <input type="submit" value="<@spring.message "general.add"/>">
            <#if RequestParameters.result??>${RequestParameters.result}</#if>
        </form>

        <br>

        <form action="<@spring.url "/hotel/get"/>" method="get">
            <input type="text" placeholder="<@spring.message "hotel.id"/>" name="id" required>
            <input type="submit" value="<@spring.message "general.find"/>">
            <#if result?? && result?is_string && result == "notFound">
                <@spring.message "hotel.not.found"/>
            <#elseif result??>
                ${result}
            </#if>
        </form>
    </body>
</html>