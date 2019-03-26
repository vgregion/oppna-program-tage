<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:defineObjects/>

<link rel="stylesheet" type="text/css" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/css/retursidan.css")%>"/>

<div id="content-primary" class="cf clearfix" role="main">
	<p class="back-link"><a class="btn btn-default" href="<portlet:renderURL/>">Tillbaka</a></p>
	<div class="clearfix">
        <div class="content-header cf clearfix">
		    <h1>Statistik</h1>
	    </div>
    </div>
    <div style="font-size: 12pt;">Antal unika besökare på funktionen: <strong>${uniqueVisitors}</strong></div>
    <h2>Upplagda annonser</h2>

	<div class="cols-2 clearfix">
		<div class="col-table col col-1">
			<table class="statistics" style="border: 1px solid #AAA">
				<tr>
					<th>Förvaltning</th>
					<c:forEach items="${unitRequestCount.get(0).keySet()}" var="year">
						<th>${year}</th>
					</c:forEach>
				</tr>
				<c:forEach items="${units}" var="unit" varStatus="status">
					<tr>
						<td>${unit.name}</td>
						<c:forEach items="${unitAdCount[status.index].keySet()}" var="year">

							<td>${unitAdCount[status.index][year]}</td>
						</c:forEach>
					</tr>
				</c:forEach>
				<tr>
					<td><strong>TOTALT</strong></td>
					<c:forEach items="${totalNumberOfAds.keySet()}" var="year">
						<td><strong>${totalNumberOfAds[year]}</strong></td>
					</c:forEach>
				</tr>
			</table>
		</div>

		<div class="col-table col col-2">
			<table class="statistics" style="border: 1px solid #AAA">
				<tr>
					<th>Geografiskt område</th>
					<c:forEach items="${unitRequestCount.get(0).keySet()}" var="year">
						<th>${year}</th>
					</c:forEach>
				</tr>
				<c:forEach items="${areas}" var="area" varStatus="status">
					<tr>
						<td>${area.name}</td>
						<c:forEach items="${areaAdCount[status.index].keySet()}" var="year">

							<td>${areaAdCount[status.index][year]}</td>
						</c:forEach>
					</tr>
				</c:forEach>
				<tr>
					<td><strong>TOTALT</strong></td>
                    <c:forEach items="${totalNumberOfAreaAds.keySet()}" var="year">
					    <td><strong>${totalNumberOfAreaAds[year]}</strong></td>
                    </c:forEach>
				</tr>
			</table>
		</div>
	</div>

	<h2>Upplagda efterlysningar</h2>
	<div class="cols-2 clearfix">
		<div class="col-table col col-1">
			<table class="statistics" style="border: 1px solid #AAA">
				<tr>
					<th>Förvaltning</th>
                    <c:forEach items="${unitRequestCount.get(0).keySet()}" var="year">
                        <th>${year}</th>
                    </c:forEach>
				</tr>
				<c:forEach items="${units}" var="unit" varStatus="status">
					<tr>
						<td>${unit.name}</td>
                        <c:forEach items="${unitRequestCount[status.index].keySet()}" var="year">

                            <td>${unitRequestCount[status.index][year]}</td>
                        </c:forEach>
					</tr>
				</c:forEach>
				<tr>
					<td><strong>TOTALT</strong></td>
                    <c:forEach items="${totalNumberOfAreaAds.keySet()}" var="year">
					    <td><strong>${totalNumberOfAreaAds[year]}</strong></td>
                    </c:forEach>
				</tr>
			</table>
		</div>

		<div class="col-table col col-2">
			<table class="statistics" style="border: 1px solid #AAA">
				<tr>
					<th>Geografiskt område</th>
                    <c:forEach items="${unitRequestCount.get(0).keySet()}" var="year">
                        <th>${year}</th>
                    </c:forEach>
				</tr>
				<c:forEach items="${areas}" var="area" varStatus="status">
					<tr>
						<td>${area.name}</td>
                        <c:forEach items="${areaRequestCount[status.index].keySet()}" var="year">

                            <td>${areaRequestCount[status.index][year]}</td>
                        </c:forEach>
					</tr>
				</c:forEach>
				<tr>
					<td><strong>TOTALT</strong></td>
                    <c:forEach items="${totalNumberOfAreaRequests.keySet()}" var="year">
					    <td><strong>${totalNumberOfAreaRequests[year]}</strong></td>
                    </c:forEach>
				</tr>
			</table>
		</div>
	</div>

	<h2>Bokade annonser</h2>
	<div class="cols-2 clearfix">

		<div class="col-table col col-1">

			<table class="statistics" style="border: 1px solid #AAA">
				<tr>
					<th>Förvaltning</th>
                    <c:forEach items="${unitRequestCount.get(0).keySet()}" var="year">
                        <th>${year}</th>
                    </c:forEach>
				</tr>
				<c:forEach items="${units}" var="unit" varStatus="status">
					<tr>
						<td>${unit.name}</td>
                        <c:forEach items="${unitBookedCount[status.index].keySet()}" var="year">

                            <td>${unitBookedCount[status.index][year]}</td>
                        </c:forEach>
					</tr>
				</c:forEach>
				<tr>
					<td><strong>TOTALT</strong></td>
                    <c:forEach items="${totalNumberOfBookedAds.keySet()}" var="year">
                        <td><strong>${totalNumberOfBookedAds[year]}</strong></td>
                    </c:forEach>
				</tr>
			</table>
		</div>

		<div class="col-table col col-2">
			<table class="statistics" style="border: 1px solid #AAA">
				<tr>
					<th>Geografiskt område</th>
                    <c:forEach items="${unitRequestCount.get(0).keySet()}" var="year">
                        <th>${year}</th>
                    </c:forEach>
				</tr>
				<c:forEach items="${areas}" var="area" varStatus="status">
					<tr>
						<td>${area.name}</td>
                        <c:forEach items="${areaBookedCount[status.index].keySet()}" var="year">

                            <td>${areaBookedCount[status.index][year]}</td>
                        </c:forEach>
					</tr>
				</c:forEach>
				<tr>
					<td><strong>TOTALT</strong></td>
                    <c:forEach items="${totalNumberOfBookedAreaAds.keySet()}" var="year">
                        <td><strong>${totalNumberOfBookedAreaAds[year]}</strong></td>
                    </c:forEach>
				</tr>
			</table>
		</div>

	</div>

	<h2>Övrigt</h2>
	Antal icke bokade men utgångna annonser:
    <table class="statistics" style="border: 1px solid #AAA">
        <tr>
            <c:forEach items="${unitRequestCount.get(0).keySet()}" var="year">
                <th>${year}</th>
            </c:forEach>
        </tr>
        <tr>
            <c:forEach items="${totalNumberOfExpiredAds.keySet()}" var="year">
                <td>${totalNumberOfExpiredAds[year]}</td>
            </c:forEach>
        </tr>
    </table>
</div>
