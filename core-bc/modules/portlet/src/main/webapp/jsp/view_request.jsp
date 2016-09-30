<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="liferay" uri="http://liferay.com/tld/portlet" %>

<%
%><portlet:defineObjects/>

<c:if test="${config.useInternalResources}">
	<script src="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/jquery/jquery-1.8.2.js") %>" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/css/retursidan.css")%>"/>
</c:if>

<portlet:renderURL var="cancelUrl">
    <portlet:param name="externalPage" value="none"/>
</portlet:renderURL>

<liferay:renderURL var="backUrlLiferay" portletName="Retursidan_WAR_tageportlet">
	<c:if test="${not empty previousPage}">
		<portlet:param name="page" value="${previousPage}"/>
	</c:if>
	<portlet:param name="externalPage" value="none"/>
</liferay:renderURL>

<portlet:renderURL var="backUrl" >
	<c:if test="${not empty previousPage}">
		<portlet:param name="page" value="${previousPage}"/>
	</c:if>
	<portlet:param name="externalPage" value="none"/>
</portlet:renderURL>

<c:if test="${not empty request}">
	<div id="content-primary" class="article cf clearfix" role="main">
		<p class="back-link"><a class="btn btn-default" href="${backUrl}">Tillbaka till startsidan</a></p>
		<h1>${request.title}</h1>
		<div class="inventory-info">
			<p class="meta"><span class="date">${request.formattedCreatedDate}</span> | <span class="category">${request.category.parent.title} <span class="sep">&gt;</span> ${request.category.title}</span></p>
			<p class="meta">Efterlyses av <strong>${request.unit.name}</strong></p>
			<c:if test="${not empty request.area.name}">
				<p class="meta">Geografiskt område <strong>${request.area.name}</strong></p>
			</c:if>
			<p>${request.description}</p>
			<h2>Kontaktperson</h2>
			<div class="contact-person">
				<span>${request.contact.name}</span>
				<span>${request.contact.phone}</span>
				<div class="email"><a href="mailto:${request.contact.email}">${request.contact.email}</a></div>
			</div>
			<p><span class="author">Efterlysningen skapad av ${request.creatorUid}.</span></p>
			<c:if test="${userId eq request.creatorUid}">
				<c:if test="${request.published}">
					<portlet:renderURL var="expireRequestUrl">
						<portlet:param name="page" value="expireRequest"/>
						<portlet:param name="requestId" value="${request.id}"/>
					</portlet:renderURL>
					<a href="${expireRequestUrl}" class="btn btn-danger">Ta bort efterlysning</a>
				</c:if>
				<portlet:actionURL name="loadRequest" var="changeRequestUrl">
					<portlet:param name="requestId" value="${request.id}"/>
				</portlet:actionURL>
				<a href="${changeRequestUrl}" class="btn btn-primary">Redigera</a>
			</c:if>
			<a href="${cancelUrl}" class="btn btn-default">Tillbaka till startsidan</a>
		</div>
	</div>
</c:if>
<c:if test="${empty request}">
	<div id="content-primary" class="article cf" role="main">
		<p class="back-link"><a href="<portlet:renderURL/>">Tillbaka</a></p>
		<h1>Efterlysningen kunde inte hittas</h1>
		<div class="inventory-info">
			Den begärda efterlysningen kunde inte hittas i systemet. Detta innebär förmodligen att den har tagits bort, eller att länken du klickat på är felaktig.
		</div>
		<a href="${cancelUrl}" class="button">Tillbaka till startsidan</a>
	</div>
</c:if>