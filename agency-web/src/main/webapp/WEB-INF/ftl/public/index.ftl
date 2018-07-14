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

        <div>
            <form action="<@spring.url "/tour/findAll"/>" method="get">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <input class="button" type="submit" value="<@spring.message "general.find.all.tours"/>">
            </form>

            <br>

            <form action="<@spring.url "/tour/findByCriteria"/>" method="get">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <input class="button" type="submit" value="<@spring.message "general.find"/>">

                <br><br>

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
            </form>
        </div>

        <div align="center" style="float: top; padding-bottom: 80px; margin-top: -320px;">
            <#if tourList??>
                <#list tourList as tour>
                    <div>
                        <p class="text">
                            <a class="text" href="<@spring.url "/tour/get/${tour.getId()}"/>"><@spring.message "tour"/> ${tour.getId()}</a>
                            <br>
                            <@spring.message "tour.country"/>: ${tour.getCountry()}
                            <br>
                            <@spring.message "tour.cost"/>: ${tour.getCost()}$
                            <br>
                            <img src="<@spring.url "${tour.getPhoto()}"/>">
                        </p>
                    </div>
                </#list>
            </#if>
        </div>

        <#include "copyright.ftl">
    </body>
</html>