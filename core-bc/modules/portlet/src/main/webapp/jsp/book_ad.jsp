<%@page import="org.springframework.validation.ObjectError"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%
%><portlet:actionURL name="performBooking" var="bookAdUrl" /><%
%><portlet:renderURL var="cancelUrl"/><%
%><portlet:defineObjects/>

<link rel="stylesheet" type="text/css" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/css/retursidan.css")%>"/>
<script src="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/jquery/jquery-1.8.2.js") %>" type="text/javascript"></script>

<%
	org.springframework.validation.BindingResult bindingResult =
		(org.springframework.validation.BindingResult)renderRequest.getAttribute("org.springframework.validation.BindingResult.booking");
%>

<div id="content-primary" class="article cf clearfix" role="main">
	<h1>Boka ${booking.advertisementTitle}</h1>

<%
	if (bindingResult.hasErrors()) {
%>
	<div class="system-info portlet-msg-error">
		<h2>Felaktigt inmatade bokningsuppgifter</h2>
		<p>Följande fel upptäcktes i bokningen, rätta felen och försök igen.</p>
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

	<form:form id="book-inventory-form" cssClass="form-general" modelAttribute="booking" action="${bookAdUrl}" >
		<form:hidden path="advertisementId"/>
		<form:hidden path="advertisementTitle"/>
		<p>${texts.confirmBookingText}</p>
		<div class="row cols-1 cf clearfix">
			<div class="col col-1">
				<div class="checkbox alt">
					<form:checkbox path="transportationConfirmed" id="checkbox1-50911f89bfed7"/>
					<label for="checkbox1-50911f89bfed7">Jag förstår att transport måste ordnas</label>
				</div>
			</div>
		</div>
		<div class="row cols-1 cf clearfix">
			<div class="col hr col-1">
				<hr>
			</div>
		</div>
		<h2>Kontaktuppgifter till mig</h2>
		<div class="row cols-1 cf clearfix">
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("contact.name") ? "error" : "" %>">
				<label for="50911f89c015f">Mitt namn <em>(obligatoriskt)</em> <strong><form:errors path="contact.name"/></strong></label>
				<form:input path="contact.name" id="50911f89c015f"/>
			</div>
		</div>
		<div class="row cols-1 cf clearfix">
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("contact.phone") ? "error" : "" %>">
				<label for="50911f89c01d9">Telefonnummer <em>(obligatoriskt)</em> <strong><form:errors path="contact.phone"/></strong></label>
				<form:input path="contact.phone" id="50911f89c01d9"/>
			</div>
		</div>
		<div class="row cols-1 cf clearfix">
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("contact.email") ? "error" : "" %>">
				<label for="50911f89c029f">E-mail <em>(obligatoriskt)</em> <strong><form:errors path="contact.email"/></strong></label>
				<form:input path="contact.email" id="50911f89c029f"/>
			</div>
		</div>
		<div class="row cols-1 cf clearfix">
			<%--<div class="col medium col-1 submit-area">--%>
            <aui:button-row>
				<button class="btn btn-primary" type="submit" name="submit-50911f89c035a" onClick="this.disabled=true; this.form.submit()">Bekräfta bokningen</button>
				<a class="btn btn-default" href="${cancelUrl}">Avbryt</a>
            </aui:button-row>
			<%--</div>--%>
		</div>
	</form:form>
</div>
