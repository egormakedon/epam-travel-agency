<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "review.title"/></title>
    </head>

    <body>
        <@spring.message "general.language"/> : <a href="?locale=en"><@spring.message "general.english"/></a> | <a href="?locale=ru"><@spring.message "general.russian"/></a> <br>

        <form action="<@spring.url "/review/add"/>" method="post">
            <input type="text" placeholder="<@spring.message "tour.id"/>" name="tourId" required>
            <input type="text" placeholder="<@spring.message "user.id"/>" name="userId" required>
            <input type="text" placeholder="<@spring.message "review.content"/>" name="content" required>
            <input type="submit" value="<@spring.message "general.add"/>">
        </form>

        <br>

        <#if RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result=="added">
            <@spring.message "review.added.successfully"/>
        <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result=="notAdded">
            <@spring.message "review.not.added"/>
        <#elseif result?? && result?is_string && result == "notFound">
            <@spring.message "hotel.not.found"/>
        <#elseif result?? && result?is_string && result == "removed">
            <@spring.message "hotel.removed.successfully"/>
        <#elseif result?? && result?is_string && result == "notRemoved">
            <@spring.message "hotel.not.removed"/>
        <#elseif result??>
            ${result}
        </#if>
    </body>
</html>