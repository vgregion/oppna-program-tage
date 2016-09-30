<%@page import="org.springframework.validation.ObjectError"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%
%><portlet:actionURL name="saveArea" var="saveAreaUrl"/><%
%><portlet:actionURL name="removeArea" var="removeAreaUrl"/><%
%><portlet:renderURL var="cancelUrl"/><%
%><portlet:defineObjects/>

<link rel="stylesheet" type="text/css" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/css/retursidan.css")%>"/>

<% 
	org.springframework.validation.BindingResult bindingResult = 
		(org.springframework.validation.BindingResult)renderRequest.getAttribute("org.springframework.validation.BindingResult.config"); 
%>

<div id="content-primary" class="article cf" role="main">
	<p class="back-link"><a class="btn btn-default" href="<portlet:renderURL/>">Tillbaka</a></p>
	<h1>Administration - administrera geografiska områden</h1>
	
<%
	if (bindingResult.hasErrors()) {
%>
	<div class="system-info portlet-msg-error">
		<h2>Felaktigt inmatade texter</h2>
		<p>Följande fel upptäcktes i texterna, rätta felen och försök igen.</p>
		<ul>
<%
		for(ObjectError error: bindingResult.getAllErrors()) {
%>			
			<li><spring:message code="<%= error.getCode() %>"/></li>
<%
		}
%>
		</ul>
	</div>
<%
	}
%>	
	
	<form:form id="change-area-form" cssClass="form-general" modelAttribute="newArea" action="${saveAreaUrl}" >
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="name"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("name") ? "error" : "" %>">
				<label for="nameInput">Geografiskt område <strong><form:errors path="name"/></strong></label>
				<form:input path="name" id="nameInput"/>
			</div>
		</div>
		<div class="row cols-1 cf">
            <aui:button-row>
                <aui:button cssClass="btn btn-primary" type="submit" value="Lägg till nytt geografiskt område"/>
            </aui:button-row>
		</div>	
	</form:form>
	<form:form id="change-area-form" cssClass="form-general" modelAttribute="removeArea" action="${removeAreaUrl}" >
		<div class="row cols-1 cf">
			<div class="col full col-1">
				<fieldset>
					<legend class="wrap"><span>Välj geografiskt område att ta bort</span></legend>
					<div class="fieldset-content">
						<c:forEach items="${areas}" var="area">
							<div class="radio">
								<form:radiobutton path="id" title="${area.name}" value="${area.id}" id="area${area.id}"/>
								<label for="area${area.id}">${area.name}</label>
							</div>
						</c:forEach>	
					</div>
				</fieldset>
			</div>
		</div>
		<div class="row cols-1 cf">
<%--
			<div class="col medium col-1 submit-area">
				<input type="submit" value="Ta bort vald enhet">
			</div>
--%>
            <aui:button-row>
                <aui:button cssClass="btn btn-danger" type="submit" value="Ta bort valt geografiskt område"/>
            </aui:button-row>
		</div>		
	</form:form>
</div>
