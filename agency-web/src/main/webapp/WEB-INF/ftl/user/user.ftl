<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "user.title"/></title>
    </head>

    <body>
        <@spring.message "general.language"/> : <a href="<@spring.url "/user?locale=en"/>"><@spring.message "general.english"/></a> | <a href="<@spring.url "/user?locale=ru"/>"><@spring.message "general.russian"/></a> <br>

        <form action="<@spring.url "/user/add"/>" method="post">
            <input type="text" placeholder="<@spring.message "user.login"/>" name="login" required>
            <input type="password" placeholder="<@spring.message "user.password"/>" name="password" required>
            <input type="submit" value="<@spring.message "general.add"/>">
        </form>

        <br>

        <form action="<@spring.url "/user/get"/>" method="get">
            <input type="text" placeholder="<@spring.message "user.id"/>" name="id" required>
            <input type="submit" value="<@spring.message "general.find"/>">
        </form>

        <br>

        <form action="<@spring.url "/user/remove"/>" method="post">
            <input type="text" placeholder="<@spring.message "user.id"/>" name="id" required>
            <input type="submit" value="<@spring.message "general.remove"/>">
        </form>

        <br>

        <a href="<@spring.url "/"/>"><@spring.message "general.return.to.welcome.page"/></a>

        <br><br>

        <#if RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result=="added">
            <@spring.message "user.added.successfully"/>
        <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result=="notAdded">
            <@spring.message "user.not.added"/>
        <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result == "notFound">
            <@spring.message "user.not.found"/>
        <#elseif result?? && result?is_string && result == "notFound">
            <@spring.message "user.not.found"/>
        <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result == "removed">
            <@spring.message "user.removed.successfully"/>
        <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result == "notRemoved">
            <@spring.message "user.not.removed"/>
        <#elseif RequestParameters.result??>
            ${RequestParameters.result}
        <#elseif result??>
            ${result}
        </#if>

        <#if login??>
            <p><@spring.message "user.login"/> : ${login}
        </#if>
        <#if role??>
            <p><@spring.message "user.role"/> : ${role}
        </#if>
        <#if reviewList??>
            <p><@spring.message "user.review.list"/> :
            <#list reviewList as review>
                <p>${review}
            </#list>
        </#if>
        <#if tourList??>
            <p><@spring.message "user.tour.list"/> :
            <#list tourList as tour>
                <p>${tour}
            </#list>
        </#if>
    </body>
</html>