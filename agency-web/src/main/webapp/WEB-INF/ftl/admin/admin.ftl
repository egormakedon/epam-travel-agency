<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "general.admin.room"/></title>

        <link href="<@spring.url "/resources/css/common.css"/>" rel="stylesheet"/>
    </head>

    <body>
        <#include "../public/header.ftl">

        <h1 class="title">
            <@spring.message "general.admin.room"/>

            <br>

            <@spring.message "general.welcome"/> ${currentUser.getUsername()}!
        </h1>

        <div style="float: left">
            <a class="text" href="<@spring.url "/hotel"/>"><@spring.message "hotels"/></a>
        </div>

        <#include "../public/copyright.ftl">
    </body>
</html>