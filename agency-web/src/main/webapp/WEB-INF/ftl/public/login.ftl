<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "login.title"/></title>
    </head>

    <body>
        <@spring.message "general.language"/> :
        <a href="<@spring.url "/login?locale=en"/>"><@spring.message "general.english"/></a> |
        <a href="<@spring.url "/login?locale=ru"/>"><@spring.message "general.russian"/></a>

        <br><br>

        <div align="center">
            <@spring.message "login.log"/>

            <br><br>

            <form action="<@spring.url "/login"/>" method="post">
                <input type="text" placeholder="<@spring.message "user.login"/>" name="login" required> <br><br>
                <input type="password" placeholder="<@spring.message "user.password"/>" name="password" required> <br><br>
                <input type="submit" value="<@spring.message "login.log"/>">
            </form>

            <br>

            <a href="<@spring.url "/registration"/>"><@spring.message "general.registration"/></a>
        </div>
    </body>
</html>
