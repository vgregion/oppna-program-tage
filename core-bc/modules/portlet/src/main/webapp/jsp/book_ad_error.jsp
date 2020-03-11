<%@page import="org.springframework.validation.ObjectError"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:renderURL var="backUrl" /><%
%><portlet:defineObjects/>

<script src="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/jquery/jquery-1.8.2.js") %>" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/css/retursidan.css")%>"/>

<div id="content-primary" class="article cf clearfix" role="main">
	<p class="back-link"><a class="btn btn-default" href="${backUrl}">Tillbaka</a></p>
	<h1>Ett fel uppstod när ${advertisement.title} skulle bokas</h1>
	<div class="system-info confirmation-message">
		<h2>${errorMessage}</h2>
		<p>Din bokning har inte genomförts, klicka på tillbaka-länken ovan för att börja om och söka efter en annan annons.</p>
	</div>
</div>
