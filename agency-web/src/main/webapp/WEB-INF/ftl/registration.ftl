<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "registration.title"/></title>
    </head>

    <body>
        <@spring.message "general.language"/> :
        <a href="<@spring.url "/registration?locale=en"/>"><@spring.message "general.english"/></a> |
        <a href="<@spring.url "/registration?locale=ru"/>"><@spring.message "general.russian"/></a>

        <br><br>

        <div align="center">
            <@spring.message "general.registration"/>

            <br><br>

            <form action="" method="post">
                <input type="text" placeholder="<@spring.message "user.login"/>" name="login" required> <br><br>
                <input type="password" placeholder="<@spring.message "user.password"/>" name="password" required> <br><br>
                <input type="password" placeholder="<@spring.message "registration.confirm.password"/>" name="confirmPassword" required> <br><br>
                <input type="submit" value="<@spring.message "registration.registr"/>">
            </form>

            <br>

            <a href="<@spring.url "/login"/>"><@spring.message "login.log"/></a>
        </div>
    </body>
</html>
