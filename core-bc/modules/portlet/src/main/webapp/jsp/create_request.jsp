<%@page import="org.springframework.validation.ObjectError"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%
%><portlet:renderURL var="cancelUrl">
	<portlet:param name="externalPage" value="none"/>
</portlet:renderURL><%
%><portlet:actionURL name="saveRequest" var="saveRequestUrl"/><%
%><portlet:resourceURL id="subCategories" cacheability="cacheLevelFull" var="subCatUrl"/><%
%><portlet:defineObjects/>

<c:if test="${config.useInternalResources}">
	<script src="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/jquery/jquery-1.8.2.js") %>" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/css/retursidan.css")%>"/>
</c:if>

<%
	org.springframework.validation.BindingResult bindingResult =
		(org.springframework.validation.BindingResult)renderRequest.getAttribute("org.springframework.validation.BindingResult.request");
%>

<div id="content-primary" class="article cf" role="main">
	<h1>Skapa ny efterlysning</h1>
	<p>Välj en tydlig beskrivande rubrik och en bra beskrivning så är chansen större att någon svarar på din efterlysning.</p>


<%
	if (bindingResult.hasErrors()) {
%>
	<div class="system-info portlet-msg-error">
		<h2>Felaktigt inmatade efterlysningsuppgifter</h2>
		<p>Följande fel upptäcktes i efterlysningen, rätta felen och försök igen.</p>
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

	<form:form id="new-want-ad-form" cssClass="form-general" modelAttribute="request" action="${saveRequestUrl}" >
		<div class="row cols-2 cf">
			<div class="select medium col col-1 mandatory <%= bindingResult.hasFieldErrors("topCategory") ? "error" : "" %>">
				<label for="5086c4a3b2949">Kategori <em>(obligatoriskt)</em> <div><form:errors cssClass="portlet-msg-error" path="topCategory"/></div></label>
				<form:select id="5086c4a3b2949" path="topCategory.id">
					<form:option value="-1">Välj kategori...</form:option>
					<form:options items="${topCategories}" itemLabel="title" itemValue="id"/>
				</form:select>
			</div>
			<div id="subCategories" class="select medium col col-2 mandatory <%= bindingResult.hasFieldErrors("category") ? "error" : "" %>">
				<label for="5086c4a3b2aae">Underkategori <em>(obligatoriskt)</em> <div><form:errors cssClass="portlet-msg-error" path="category"/></div></label>
				<form:select id="5086c4a3b2aae" path="category.id">
					<option value="-1" selected="selected">Välj underkategori...</option>
					<c:if test="${!empty subCategories}">
						<form:options items="${subCategories}" itemLabel="title" itemValue="id"/>
					</c:if>
				</form:select>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="title"/></c:set>
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("title") ? "error" : "" %>">
				<label for="5086c4a3b2bb2">Rubrik <em>(obligatoriskt)</em> <div><form:errors cssClass="portlet-msg-error" path="title"/></div></label>
				<form:input path="title" id="5086c4a3b2bb2"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="text col full col-1 mandatory <%= bindingResult.hasFieldErrors("title") ? "error" : "" %>">
				<label for="5086c4a3b2c09">Beskrivning <em>(obligatoriskt)</em> <div><form:errors cssClass="portlet-msg-error" path="description"/></div></label>
				<form:textarea path="description" cols="30" rows="10" id="5086c4a3b2c09"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="col hr col-1">
				<hr>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="select large col col-1 mandatory <%= bindingResult.hasFieldErrors("unit") ? "error" : "" %>"">
				<label for="5086c4a3b2c60">Förvaltning som efterlyser <em>(obligatoriskt)</em><div><form:errors cssClass="portlet-msg-error" path="unit"/></div></label>
				<form:select id="5086c4a3b2c60" path="unit.id">
					<form:option value="-1" label="Välj förvaltning..." />
					<form:options items="${units}" itemValue="id" itemLabel="name"/>
				</form:select>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="select large col col-1 mandatory <%= bindingResult.hasFieldErrors("area") ? "error" : "" %>"">
				<label for="5086c4a3b2c60">Geografiskt område <em>(obligatoriskt)</em><div><form:errors cssClass="portlet-msg-error" path="area"/></div></label>
				<form:select id="5086c4a3b2c60" path="area.id">
					<form:option value="-1" label="Välj geografiskt område..." />
					<form:options items="${areas}" itemValue="id" itemLabel="name"/>
				</form:select>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("contact.name") ? "error" : "" %>">
				<label for="5086c4a3b306c">Kontaktperson <em>(obligatoriskt)</em> <div><form:errors cssClass="portlet-msg-error" path="contact.name"/></div></label>
				<form:input path="contact.name" id="5086c4a3b306c"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("contact.phone") ? "error" : "" %>">
				<label for="5086c4a3b30c2">Telefonnummer <em>(obligatoriskt)</em> <div><form:errors cssClass="portlet-msg-error" path="contact.phone"/></div></label>
				<form:input path="contact.phone" id="5086c4a3b30c2"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("contact.email") ? "error" : "" %>">
				<label for="5086c4a3b3119">E-mail <em>(obligatoriskt)</em> <div><form:errors cssClass="portlet-msg-error" path="contact.email"/></div></label>
				<form:input path="contact.email" id="5086c4a3b3119"/>
			</div>
		</div>
		<p><span class="author">Efterlysningen skapad av ${userId}.</span></p>
		<div class="row cols-1 cf clearfix">
<%--
			<div class="col medium col-1 submit-area">
				<input type="submit" value="Lägg upp efterlysning" name="submit-509ae872593b6">
				<a href="${cancelUrl}">Avbryt</a>
			</div>
--%>
            <aui:button-row>
                <aui:button cssClass="btn btn-primary" type="submit" value="Lägg upp efterlysning" name="submit-509ae872593b6"/>
                <a class="btn btn-default" href="${cancelUrl}">Avbryt</a>
            </aui:button-row>
		</div>
	</form:form>
</div>
<script type="text/javascript">
	$("#5086c4a3b2949").change(function() {
		$.ajax({
			type: "POST",
			url: "${subCatUrl}",
			dataType: "html",
			data: { parent: $(this).find(":selected").val() },
			success: function(result) {
				$("#5086c4a3b2aae").html("<option value=\"-1\" selected=\"selected\">Välj underkategori...</option>" + result);
				$("#5086c4a3b2aae").trigger("change");
			},
			error: function(result) {
				alert("Ett fel uppstod på servern underkategorier hämtades: " + result.responseText);
			}
		});
	});
</script>
