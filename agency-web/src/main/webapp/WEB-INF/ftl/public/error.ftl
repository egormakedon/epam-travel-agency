<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "error.title"/></title>
    </head>

    <body>
        <#include "header.ftl">

        <br><br>

        <div style="text-align: center; color: red; font-size: 35px; font-weight: bold;">
            <@spring.message "error.exception.message"/> :
            <#if RequestParameters.exceptionMessage??>
                <span>${RequestParameters.exceptionMessage}</span>
            </#if>
        </div>

        <#include "copyright.ftl">
    </body>
</html>