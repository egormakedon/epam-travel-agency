<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "error.title"/></title>

        <link href="<@spring.url "/resources/css/common.css"/>" rel="stylesheet"/>
    </head>

    <body>
        <#include "header.ftl">

        <h1 class="title"><@spring.message "error.title"/></h1>

        <div align="center">
            <p class="error">
                <@spring.message "error.exception.message"/> :
                <#if RequestParameters.exceptionMessage??>
                    ${RequestParameters.exceptionMessage}
                </#if>
            </p>
        </div>

        <#include "copyright.ftl">
    </body>
</html>