<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

        <link rel="shortcut icon" href="<@spring.url "/resources/img/logo.png"/>" type="image/png">
        <link href="<@spring.url "/resources/css/header.css"/>" rel="stylesheet"/>
    </head>

    <body>
        <div class="headerContainer">
            <div class="title">
                <a href="<@spring.url "/"/>"><@spring.message "general.travel.agency"/></a>
            </div>

            <div class="auth">
                <#if currentUser??>
                    <div class="element">
                        <a href="<@spring.url "/logout"/>"><@spring.message "general.logout"/></a>
                    </div>
                <#else>
                     <div class="element">
                         <a href="<@spring.url "/registration"/>"><@spring.message "general.registration"/></a>
                     </div>

                    <div class="element">
                        <a href="<@spring.url "/login"/>"><@spring.message "login.log"/></a>
                    </div>
                </#if>

                <br>

                <div class="element">
                    <#if RequestParameters.url??>
                        <@spring.message "general.language"/> :
                        <a href="<@spring.url "${url}?locale=en"/>"><@spring.message "general.english"/></a> |
                        <a href="<@spring.url "${url}?locale=ru"/>"><@spring.message "general.russian"/></a>
                    <#elseif url??>
                        <@spring.message "general.language"/> :
                        <a href="<@spring.url "${url}?locale=en"/>"><@spring.message "general.english"/></a> |
                        <a href="<@spring.url "${url}?locale=ru"/>"><@spring.message "general.russian"/></a>
                    </#if>
                </div>
            </div>
        </div>
    </body>
</html>