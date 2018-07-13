<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "index.title"/></title>

        <link href="<@spring.url "/resources/css/common.css"/>" rel="stylesheet"/>
    </head>

    <body>
        <#include "header.ftl">

        <h1 class="title"><@spring.message "index.title"/></h1>

    <form action="<@spring.url "/tour/get"/>" method="get">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <input type="number" placeholder="<@spring.message "tour.cost"/>" name="cost">
        <label> <@spring.message "tour.country"/> :
            <select name = "country">
                <option disabled><@spring.message "tour.country"/></option>
                <option></option>
                <option>BELARUS</option>
                <option>RUSSIA</option>
                <option>POLAND</option>
                <option>ENGLAND</option>
                <option>USA</option>
                <option>UKRAINE</option>
                <option>SPAIN</option>
                <option>CHINA</option>
            </select>
        </label>
        <label> <@spring.message "tour.type"/> :
            <select name = "type">
                <option disabled><@spring.message "tour.type"/></option>
                <option></option>
                <option>CHILDREN</option>
                <option>WEEKEND</option>
                <option>WEDDING</option>
                <option>SHOPING</option>
                <option>EXCURSION</option>
            </select>
        </label>
        <label> <@spring.message "tour.stars"/> :
            <select name = "stars">
                <option disabled><@spring.message "tour.stars"/></option>
                <option></option>
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
            </select>
        </label>
        <input type="date" placeholder="<@spring.message "tour.date"/>" name="date">
        <input type="number" placeholder="<@spring.message "tour.duration"/>" name="durationString">
        <input type="submit" value="<@spring.message "general.find"/>">
    </form>

        <#include "copyright.ftl">
    </body>
</html>