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

        <form action="<@spring.url "/review/get"/>" method="get">
            <input type="text" placeholder="<@spring.message "review.id"/>" name="id" required>
            <input type="submit" value="<@spring.message "general.find"/>">
        </form>

        <br>

        <form action="<@spring.url "/review/remove"/>" method="post">
            <input type="text" placeholder="<@spring.message "review.id"/>" name="id" required>
            <input type="submit" value="<@spring.message "general.remove"/>">
        </form>

        <br>

        <form action="<@spring.url "/review/update"/>" method="post">
            <input type="text" placeholder="<@spring.message "review.id"/>" name="id" required>
            <input type="text" placeholder="<@spring.message "tour.id"/>" name="tourId">
            <input type="text" placeholder="<@spring.message "user.id"/>" name="userId">
            <input type="text" placeholder="<@spring.message "review.content"/>" name="content">
            <input type="submit" value="<@spring.message "general.update"/>">
        </form>

        <br>

        <a href="<@spring.url "/"/>"><@spring.message "general.return.to.welcome.page"/></a>

        <br><br>

        <#if RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result=="added">
            <@spring.message "review.added.successfully"/>
        <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result=="notAdded">
            <@spring.message "review.not.added"/>
        <#elseif result?? && result?is_string && result=="notFoundTour">
            <@spring.message "tour.not.found"/>
        <#elseif result?? && result?is_string && result=="notFoundUser">
            <@spring.message "user.not.found"/>
        <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result == "notFound">
            <@spring.message "review.not.found"/>
        <#elseif result?? && result?is_string && result == "notFound">
            <@spring.message "review.not.found"/>
        <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result == "removed">
            <@spring.message "review.removed.successfully"/>
        <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result == "notRemoved">
            <@spring.message "review.not.removed"/>
        <#elseif RequestParameters.result??>
            ${RequestParameters.result}
        <#elseif result??>
            ${result}
        </#if>
    </body>
</html>