<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "tour.title"/></title>

        <link href="<@spring.url "/resources/css/common.css"/>" rel="stylesheet"/>
    </head>

    <body>
         <#include "../public/header.ftl">

        <h1 class="title"><@spring.message "tour.title"/></h1>

        <form action="<@spring.url "/tour/load"/>" method="post" enctype="multipart/form-data">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <input type="file" name="file" accept="application/json" required>
            <input class="button" type="submit" value="<@spring.message "tour.upload"/>">
        </form>

        <br><br>

        <form action="<@spring.url "/tour/remove"/>" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <input class="text" type="text" placeholder="<@spring.message "tour.id"/>" name="id" required>
            <input class="button" type="submit" value="<@spring.message "general.remove"/>">
        </form>

        <br><br>

        <form action="<@spring.url "/tour/update"/>" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <label class="text"> <@spring.message "tour.country"/> :
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

            <br><br>

            <label class="text"> <@spring.message "tour.type"/> :
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

            <br><br>

            <label class="text"> <@spring.message "tour.stars"/> :
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

            <br><br>

            <input class="text" type="date" placeholder="<@spring.message "tour.date"/>" name="date">

            <br><br>

            <input class="text" type="number" placeholder="<@spring.message "tour.duration"/>" name="durationString">

            <br><br>

            <input class="text" type="number" placeholder="<@spring.message "tour.cost"/>" name="cost">

            <br><br>

            <input class="button" type="submit" value="<@spring.message "general.update"/>">
        </form>

        <p class="text">
            <#if result?? && result?is_string && result=="notFound">
                <@spring.message "tour.not.found"/>
            <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result == "notFound">
                <@spring.message "tour.not.found"/>
            <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result == "removed">
                <@spring.message "tour.removed.successfully"/>
            <#elseif RequestParameters.result?? && RequestParameters.result?is_string && RequestParameters.result == "notRemoved">
                <@spring.message "tour.not.removed"/>
            <#elseif RequestParameters.result??>
                ${RequestParameters.result}
            <#elseif result??>
                ${result}
            </#if>
        </p>

        <#include "../public/copyright.ftl">
    </body>
</html>