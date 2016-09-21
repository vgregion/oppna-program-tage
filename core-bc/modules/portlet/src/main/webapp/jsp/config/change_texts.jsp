<%@page import="org.springframework.validation.ObjectError"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%
%><portlet:actionURL name="saveTexts" var="saveTextsUrl"/><%
%><portlet:renderURL var="cancelUrl"/><%
%><portlet:defineObjects/>

<link rel="stylesheet" type="text/css" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/css/retursidan.css")%>"/>

<% 
	org.springframework.validation.BindingResult bindingResult = 
		(org.springframework.validation.BindingResult)renderRequest.getAttribute("org.springframework.validation.BindingResult.config"); 
%>

<div id="content-primary" class="article cf" role="main">
	<p class="back-link"><a href="<portlet:renderURL/>">Tillbaka</a></p>
	<h1>Administration - ändra texter</h1>
	<p>Ändra de applikationstexter som används i Tage</p>
	
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
	
	<form:form id="change-texts-form" cssClass="form-general" modelAttribute="texts" action="${saveTextsUrl}" >
		<p>Godkännandetexter</p>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="confirmCreateAdText"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("confirmCreateAdText") ? "error" : "" %>">
				<label for="confirmCreateAdTextInput">Skapa annons <strong><form:errors path="confirmCreateAdText"/></strong></label>
				<form:textarea path="confirmCreateAdText" id="confirmCreateAdTextInput" cols="50" rows="10"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="confirmBookingText"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("confirmBookingText") ? "error" : "" %>">
				<label for="confirmBookingTextInput">Boka annons <strong><form:errors path="confirmBookingText"/></strong></label>
				<form:textarea path="confirmBookingText" id="confirmBookingTextInput" cols="50" rows="10"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="confirmRepublishText"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("confirmRepublishText") ? "error" : "" %>">
				<label for="confirmRepublishTextInput">Återpublicering <strong><form:errors path="confirmRepublishText"/></strong></label>
				<form:textarea path="confirmRepublishText" id="confirmRepublishTextInput" cols="50" rows="10"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="confirmRemoveRequestText"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("confirmRemoveRequestText") ? "error" : "" %>">
				<label for="confirmRemoveRequestTextInput">Ta bort efterlysning <strong><form:errors path="confirmRemoveRequestText"/></strong></label>
				<form:textarea path="confirmRemoveRequestText" id="confirmRemoveRequestTextInput" cols="50" rows="10"/>
			</div>
		</div>			
		<div class="row cols-1 cf">
			<div class="col hr col-1">
				<hr>
			</div>
		</div>
		<p>Bekräftelsetexter</p>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="bookingConfirmationText"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("bookingConfirmationText") ? "error" : "" %>">
				<label for="bookingConfirmationTextInput">Bokningsbekräftelse <strong><form:errors path="bookingConfirmationText"/></strong></label>
				<form:textarea path="bookingConfirmationText" id="bookingConfirmationTextInput" cols="50" rows="10"/>
			</div>
		</div>			
		<div class="row cols-1 cf">
			<div class="col hr col-1">
				<hr>
			</div>
		</div>
		<p>Epost-utskick</p>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailSenderAddress"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailSenderAddress") ? "error" : "" %>">
				<label for="mailSenderAddressInput">Avsändare <strong><form:errors path="mailSenderAddress"/></strong></label>
				<form:input path="mailSenderAddress" id="mailSenderAddressInput"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailSubject"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailSubject") ? "error" : "" %>">
				<label for="mailSubjectInput">Rubrik<strong><form:errors path="mailSubject"/></strong></label>
				<form:input path="mailSubject" id="mailSubjectInput"/>
			</div>
		</div>
		<p>Följande ersättningsfält kan du använda i innehållstexten, de byts ut mot motsvarande text när epost-meddelandet skapas:<br/>
		Annonsens rubrik: <strong>{title}</strong>, bokarens namn: <strong>{bookerName}</strong>, bokarens telefon: <strong>{bookerPhone}</strong>, 
		bokarens epost-adress:<strong>{bookerMail}</strong>, annonsörens namn: <strong>{advertiserName}</strong>, 
		annonsörens telefon: <strong>{advertiserPhone}</strong>, annonsörens epost-adress: <strong>{advertiserMail}</strong> och länk till annonsen: <strong>{link}</strong>.</p>		
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailBody"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailBody") ? "error" : "" %>">
				<label for="mailBodyInput">Innehåll <strong><form:errors path="mailBody"/></strong></label>
				<form:textarea path="mailBody" id="mailBodyInput" cols="50" rows="15"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<%--<div class="col medium col-1 submit-area">
				<input type="submit" value="uppdatera konfigurationen" name="submit-5086c4a3b380d">
				<a href="${cancelUrl}">Avbryt</a>
			</div>--%>
            <aui:button-row>
                <aui:button cssClass="btn btn-primary" type="submit" value="Uppdatera konfigurationen" name="submit-5086c4a3b380d"/>
                <a class="btn btn-default" href="${cancelUrl}">Avbryt</a>
            </aui:button-row>
		</div>
	</form:form>	
</div>
