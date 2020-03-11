<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:renderURL var="createRequestUrl"><portlet:param name="externalPage" value="createRequest"/></portlet:renderURL><%
%><portlet:defineObjects/>

<c:if test="${config.useInternalResources}">
    <script src="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/jquery/jquery-1.8.2.js") %>" type="text/javascript"></script>
</c:if>
<link rel="stylesheet" type="text/css" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/css/retursidan.css")%>"/>

<div id="m-requested-items" class="m m-link-list">
	<div class="m-h clearfix content-header">
		<h2>Efterlysningar</h2>
        <a href="${createRequestUrl}" class="btn btn-primary">Skapa</a>
	</div>
	<div class="m-c">
		<c:if test="${fn:length(requests) gt 0}">
			<ul class="request-listing">
				<c:forEach items="${requests}" var="request">
					<portlet:renderURL var="viewRequestUrl">
						<portlet:param name="externalPage" value="viewRequest"/>
						<portlet:param name="externalRequestId" value="${request.id}"/>
					</portlet:renderURL>
					<li class="cf clearfix">
						<a href="${viewRequestUrl}">${request.title}</a>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${fn:length(requests) eq 0}">
			<div>Inga efterlysningar hittades.</div>
		</c:if>		
	</div>
</div>