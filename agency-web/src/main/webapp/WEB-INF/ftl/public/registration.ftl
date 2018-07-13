<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "registration.title"/></title>

        <link href="<@spring.url "/resources/css/common.css"/>" rel="stylesheet"/>
    </head>

    <body>
        <#include "header.ftl">

        <br><br>

        <div align="center">
            <h1><@spring.message "general.registration"/></h1>

            <br><br>

            <form role="form" name="user" action="<@spring.url "/registration"/>" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <input id="login" name="login" type="text" placeholder="<@spring.message "user.login"/>" required> <br><br>
                <input id="password" name="password" type="password" placeholder="<@spring.message "user.password"/>" required> <br><br>
                <input id="confirmPassword" name="confirmPassword" type="password" placeholder="<@spring.message "registration.confirm.password"/>" required> <br><br>

                <#if errors??>
                    <#list errors as error>
                        <p class="error">
                            <@spring.message "${error.getCode()}"/>
                        </p>
                    </#list>
                </#if>

                <input type="submit" value="<@spring.message "registration.registr"/>">
            </form>
        </div>

        <#include "copyright.ftl">
    </body>
</html>