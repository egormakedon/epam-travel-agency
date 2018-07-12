<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "login.title"/></title>
    </head>

    <body>
        <#include "header.ftl">

        <br><br>

        <div align="center">
            <@spring.message "login.log"/>

            <br><br>

            <form action="<@spring.url "/login"/>" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <input type="text" placeholder="<@spring.message "user.login"/>" name="login" required> <br><br>
                <input type="password" placeholder="<@spring.message "user.password"/>" name="password" required> <br><br>
                <@spring.message "login.remember.me"/> <input type="checkbox" name="remember-me" value="remember-me"> <br><br>

                <#if result?? && result?is_string && result=="usernameOrPassword">
                    <p style="color: red; font-size: 25px; font-weight: bold">
                        <@spring.message "login.username.or.password"/>
                    </p>
                </#if>

                <input type="submit" value="<@spring.message "login.log"/>">
            </form>
        </div>

        <#include "copyright.ftl">
    </body>
</html>