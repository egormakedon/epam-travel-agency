<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "login.title"/></title>

        <link href="<@spring.url "/resources/css/common.css"/>" rel="stylesheet"/>
    </head>

    <body>
        <#include "header.ftl">

        <br><br>

        <div align="center">
            <h1 class="title"><@spring.message "login.log"/></h1>

            <br><br>

            <form action="<@spring.url "/login"/>" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <input class="text" type="text" placeholder="<@spring.message "user.login"/>" name="login" required> <br><br>
                <input class="text" type="password" placeholder="<@spring.message "user.password"/>" name="password" required> <br><br>
                <label class="text" for="remember-me"><@spring.message "login.remember.me"/></label>
                <input type="checkbox" name="remember-me" value="remember-me"> <br><br>

                <#if result?? && result?is_string && result=="usernameOrPassword">
                    <p class="error">
                        <@spring.message "login.username.or.password"/>
                    </p>
                </#if>

                <input class="button" type="submit" value="<@spring.message "login.log"/>">
            </form>
        </div>

        <#include "copyright.ftl">
    </body>
</html>