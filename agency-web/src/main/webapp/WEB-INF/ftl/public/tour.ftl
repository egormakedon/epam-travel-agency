<#ftl encoding="UTF-8">
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><@spring.message "tour"/> ${tour.getId()}</title>

        <link href="<@spring.url "/resources/css/common.css"/>" rel="stylesheet"/>
    </head>

    <body>
        <#include "header.ftl">

        <h1 class="title"><@spring.message "tour"/> ${tour.getId()}</h1>

        <div>
            <p class="text">
                <@spring.message "tour.date"/>: ${tour.getDate()}
            </p>
            <p class="text">
                <@spring.message "tour.duration"/>: ${tour.getDuration().getNano()} <@spring.message "tour.days"/>
            </p>
            <p class="text">
                <@spring.message "tour.country"/>: ${tour.getCountry()}
            </p>
            <p class="text">
                <@spring.message "tour.type"/>: ${tour.getType()}
            </p>
            <p class="text">
                <@spring.message "tour.cost"/>: ${tour.getCost()}$
            </p>
            <p class="text">
                <@spring.message "tour.description"/>: ${tour.getDescription()}
            </p>
        </div>

        <div align="center" style="margin-top: -265px">
            <p class="text">
                <@spring.message "hotel.name"/>: ${tour.getHotel().getName()}
            </p>
            <p class="text">
                <@spring.message "hotel.phone"/>: ${tour.getHotel().getPhone()}
            </p>
            <p class="text">
                <@spring.message "hotel.stars"/>: ${tour.getHotel().getStars()}
            </p>
        </div>

        <div style="float: right; margin-top: -100px; margin-right: 200px">
            <img src="<@spring.url "${tour.getPhoto()}"/>">
        </div>

        <#include "copyright.ftl">
    </body>
</html>