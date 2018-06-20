<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "tour.title"/></title>
    </head>

    <body>
        <@spring.message "general.language"/> : <a href="<@spring.url "/tour?locale=en"/>"><@spring.message "general.english"/></a> | <a href="<@spring.url "/tour?locale=ru"/>"><@spring.message "general.russian"/></a> <br>

        <form action="<@spring.url "/tour/load"/>" method="post" enctype="multipart/form-data">
            <input type="file" name="file" accept="application/json" required>
            <input type="submit" value="<@spring.message "tour.upload"/>">
        </form>

        <br>

        <form action="<@spring.url "/tour/get"/>" method="get">
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

        <br>

        <a href="<@spring.url "/"/>"><@spring.message "general.return.to.welcome.page"/></a>

        <br><br>

        <#if result?? && result?is_string && result=="notFoundTour">
            <@spring.message "tour.not.found"/>
        <#elseif RequestParameters.result??>
            <#list RequestParameters.result as tour>
                <p>${tour}
            </#list>
        <#elseif result??>
            <#list result as tour>
                <p>${tour}
            </#list>
        </#if>
    </body>
</html>