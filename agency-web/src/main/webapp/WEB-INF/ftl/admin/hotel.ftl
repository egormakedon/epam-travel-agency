<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "hotel.title"/></title>

        <link href="<@spring.url "/resources/css/common.css"/>" rel="stylesheet"/>
    </head>

    <body>
        <#include "../public/header.ftl">

        <h1 class="title"><@spring.message "hotel.title"/></h1>

        <form action="<@spring.url "/hotel/add"/>" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <input class="text" type="text" placeholder="<@spring.message "hotel.name"/>" name="name" required>
            <input class="text" type="text" placeholder="<@spring.message "hotel.phone"/>" name="phone" required>
            <input class="text" type="text" placeholder="<@spring.message "hotel.stars"/>" name="stars" required>
            <input class="button" type="submit" value="<@spring.message "general.add"/>">
        </form>

        <br>

        <form action="<@spring.url "/hotel/get"/>" method="get">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <input class="text" type="text" placeholder="<@spring.message "hotel.id"/>" name="id" required>
            <input class="button" type="submit" value="<@spring.message "general.find"/>">
        </form>

        <br>

        <form action="<@spring.url "/hotel/remove"/>" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <input class="text" type="text" placeholder="<@spring.message "hotel.id"/>" name="id" required>
            <input class="button" type="submit" value="<@spring.message "general.remove"/>">
        </form>

        <br>

        <form action="<@spring.url "/hotel/update"/>" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <input class="text" type="text" placeholder="<@spring.message "hotel.id"/>" name="id" required>
            <input class="text" type="text" placeholder="<@spring.message "hotel.name"/>" name="name">
            <input class="text" type="text" placeholder="<@spring.message "hotel.phone"/>" name="phone">
            <input class="text" type="text" placeholder="<@spring.message "hotel.stars"/>" name="stars">
            <input class="button" type="submit" value="<@spring.message "general.update"/>">
        </form>

        <p class="text">
            <#if RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result=="added">
                <@spring.message "hotel.added.successfully"/>
            <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result=="notAdded">
                <@spring.message "hotel.not.added"/>
            <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result == "notFound">
                <@spring.message "hotel.not.found"/>
            <#elseif result?? && result?is_string && result == "notFound">
                <@spring.message "hotel.not.found"/>
            <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result == "removed">
                <@spring.message "hotel.removed.successfully"/>
            <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result == "notRemoved">
                <@spring.message "hotel.not.removed"/>
            <#elseif RequestParameters.result??>
                ${RequestParameters.result}
            <#elseif result??>
                ${result}
            </#if>
        </p>

        <#include "../public/copyright.ftl">
    </body>
</html>