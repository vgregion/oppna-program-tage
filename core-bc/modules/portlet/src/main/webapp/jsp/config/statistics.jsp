<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:defineObjects/>

<link rel="stylesheet" type="text/css" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/css/retursidan.css")%>"/>

<div id="content-primary" class="cf" role="main">
	<p class="back-link"><a class="btn btn-default" href="<portlet:renderURL/>">Tillbaka</a></p>
	<div class="clearfix">
        <div class="content-header cf">
		    <h1>Statistik</h1>
	    </div>
    </div>
    <div style="font-size: 12pt;">Antal unika bes�kare p� funktionen: <strong>${uniqueVisitors}</strong></div>
    <h2>Upplagda annonser</h2>

	<div class="cols-2 clearfix">
		<div class="col-table col col-1">
			<table class="statistics" style="border: 1px solid #AAA">
				<tr>
					<th>F�rvaltning</th>
					<th>Antal</th>
				</tr>
				<c:forEach items="${units}" var="unit" varStatus="status">
					<tr>
						<td>${unit.name}</td>
						<td><strong>${unitAdCount[status.index]}</strong></td>
					</tr>
				</c:forEach>
				<tr>
					<td>TOTALT</td>
					<td><strong>${totalNumberOfAds}</strong></td>
				</tr>
			</table>
		</div>

		<div class="col-table col col-2">
			<table class="statistics" style="border: 1px solid #AAA">
				<tr>
					<th>Geografiskt omr�de</th>
					<th>Antal</th>
				</tr>
				<c:forEach items="${areas}" var="area" varStatus="status">
					<tr>
						<td>${area.name}</td>
						<td><strong>${areaAdCount[status.index]}</strong></td>
					</tr>
				</c:forEach>
				<tr>
					<td>TOTALT</td>
					<td><strong>${totalNumberOfAreaAds}</strong></td>
				</tr>
			</table>
		</div>
	</div>

	<h2>Upplagda efterlysningar</h2>
	<div class="cols-2 clearfix">
		<div class="col-table col col-1">
			<table class="statistics" style="border: 1px solid #AAA">
				<tr>
					<th>F�rvaltning</th>
					<th>Antal</th>
				</tr>
				<c:forEach items="${units}" var="unit" varStatus="status">
					<tr>
						<td>${unit.name}</td>
						<td><strong>${unitRequestCount[status.index]}</strong></td>
					</tr>
				</c:forEach>
				<tr>
					<td>TOTALT</td>
					<td><strong>${totalNumberOfRequests}</strong></td>
				</tr>
			</table>
		</div>

		<div class="col-table col col-2">
			<table class="statistics" style="border: 1px solid #AAA">
				<tr>
					<th>Geografiskt omr�de</th>
					<th>Antal</th>
				</tr>
				<c:forEach items="${areas}" var="area" varStatus="status">
					<tr>
						<td>${area.name}</td>
						<td><strong>${areaRequestCount[status.index]}</strong></td>
					</tr>
				</c:forEach>
				<tr>
					<td>TOTALT</td>
					<td><strong>${totalNumberOfAreaRequests}</strong></td>
				</tr>
			</table>
		</div>
	</div>

	<h2>Bokade annonser</h2>
	<div class="cols-2 clearfix">

		<div class="col-table col col-1">

			<table class="statistics" style="border: 1px solid #AAA">
				<tr>
					<th>F�rvaltning</th>
					<th>Antal</th>
				</tr>
				<c:forEach items="${units}" var="unit" varStatus="status">
					<tr>
						<td>${unit.name}</td>
						<td><strong>${unitBookedCount[status.index]}</strong></td>
					</tr>
				</c:forEach>
				<tr>
					<td>TOTALT</td>
					<td><strong>${totalNumberOfBookedAds}</strong></td>
				</tr>
			</table>
		</div>

		<div class="col-table col col-2">
			<table class="statistics" style="border: 1px solid #AAA">
				<tr>
					<th>Geografiskt omr�de</th>
					<th>Antal</th>
				</tr>
				<c:forEach items="${areas}" var="area" varStatus="status">
					<tr>
						<td>${area.name}</td>
						<td><strong>${areaBookedCount[status.index]}</strong></td>
					</tr>
				</c:forEach>
				<tr>
					<td>TOTALT</td>
					<td><strong>${totalNumberOfBookedAreaAds}</strong></td>
				</tr>
			</table>
		</div>

	</div>

	<h2>�vrigt</h2>
	Antal icke bokade men utg�ngna annonser: <strong>${totalNumberOfExpiredAds}</strong>
</div>
