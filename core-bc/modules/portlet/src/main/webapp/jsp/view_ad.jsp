<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%
%><portlet:resourceURL id="thumbnail" cacheability="cacheLevelFull" var="thumbnailUrl"/><%
%><portlet:resourceURL id="photo" cacheability="cacheLevelFull" var="photoUrl"/><%
%><portlet:defineObjects/>

<c:if test="${config.useInternalResources}">
	<script src="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/jquery/jquery-1.8.2.js") %>" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/css/retursidan.css")%>"/>
</c:if>

<portlet:renderURL var="backUrl">
	<c:if test="${not empty previousPage}">
		<portlet:param name="page" value="${previousPage}"/>
	</c:if>
</portlet:renderURL>


<c:if test="${not empty advertisement}">
	<div id="content-primary" class="article cf" role="main">
		<aui:button-row>
			<c:if test="${!advertisement.booked}">
				<portlet:renderURL var="bookUrl">
					<portlet:param name="page" value="bookAd"/>
					<portlet:param name="advertisementId" value="${advertisement.id}"/>
				</portlet:renderURL>
				<a href="${bookUrl}" class="btn btn-primary">Boka</a>
			</c:if>
			<c:if test="${userId eq advertisement.creatorUid}">
				<c:if test="${!advertisement.published}">
					<portlet:renderURL var="republishAdUrl">
						<portlet:param name="page" value="republishAd"/>
						<portlet:param name="advertisementId" value="${advertisement.id}"/>
					</portlet:renderURL>
					<a href="${republishAdUrl}" class="btn btn-primary">Återpublicera</a>
				</c:if>
				<portlet:actionURL name="loadAd" var="changeAdUrl">
					<portlet:param name="advertisementId" value="${advertisement.id}"/>
				</portlet:actionURL>
				<a href="${changeAdUrl}" class="btn btn-primary">Redigera</a>
			</c:if>
			<a href="<portlet:renderURL/>" class="btn btn-default">Tillbaka till startsidan</a>
		</aui:button-row>

		<h1>${advertisement.title}</h1>

		<div class="inventory-info">
			<c:if test="${fn:length(advertisement.photos) ne 0}">
				<div class="flexslider">
					<ul class="slides cf" style="list-style: none">
						<c:forEach items="${advertisement.photos}" var="photo">
							<li data-thumb="${thumbnailUrl}&id=${photo.id}">
								<img src="${photoUrl}&id=${photo.id}" alt="" />
							</li>
						</c:forEach>				
					</ul>
				</div>
			</c:if>			
			<p class="meta"><span class="date">${advertisement.formattedCreatedDate}</span> | <span class="category">${advertisement.category.parent.title} <span class="sep">&gt;</span> ${advertisement.category.title}</span></p>
			<p class="meta">Bortskänkes av <strong>${advertisement.unit.name}</strong></p>
			<p>${advertisement.description}</p>
			<div class="info-box">
				<h3>Hämtningsadress</h3>
				<p>${advertisement.pickupAddress}</p>
				<h3>Hämtningsvillkor</h3>
				<p>${advertisement.pickupConditions}</p>
			</div>
			<h2>Kontaktperson</h2>
			<div class="contact-person">
				<span>${advertisement.contact.name}</span>
				<span>${advertisement.contact.phone}</span>
				<div class="email"><a href="mailto:${advertisement.contact.email}">${advertisement.contact.email}</a></div>
			</div>
			<p><span class="author">Annonsen skapad av ${advertisement.creatorUid}.</span></p>
            <aui:button-row>
                <c:if test="${!advertisement.booked}">
                    <portlet:renderURL var="bookUrl">
                        <portlet:param name="page" value="bookAd"/>
                        <portlet:param name="advertisementId" value="${advertisement.id}"/>
                    </portlet:renderURL>
                    <a href="${bookUrl}" class="btn btn-primary">Boka</a>
                </c:if>
                <c:if test="${userId eq advertisement.creatorUid}">
                    <c:if test="${!advertisement.published}">
                        <portlet:renderURL var="republishAdUrl">
                            <portlet:param name="page" value="republishAd"/>
                            <portlet:param name="advertisementId" value="${advertisement.id}"/>
                        </portlet:renderURL>
                        <a href="${republishAdUrl}" class="btn btn-primary">Återpublicera</a>
                    </c:if>
                    <portlet:actionURL name="loadAd" var="changeAdUrl">
                        <portlet:param name="advertisementId" value="${advertisement.id}"/>
                    </portlet:actionURL>
                    <a href="${changeAdUrl}" class="btn btn-primary">Redigera</a>
                </c:if>
                <a href="<portlet:renderURL/>" class="btn btn-default">Tillbaka till startsidan</a>
            </aui:button-row>
			<c:if test="${userId eq advertisement.creatorUid}">
				Behöver du ta bort annonsen, maila funktionsbrevlåda: <a href="mailto:tage@vgregion.se">tage@vgregion.se</a>
			</c:if>
	</div>
	</div>
</c:if>
<c:if test="${empty advertisement}">
	<div id="content-primary" class="article cf" role="main">
		<p class="back-link"><a href="<portlet:renderURL/>">Tillbaka</a></p>
		<h1>Annonsen kunde inte hittas</h1>
		<div class="inventory-info">
			Den begärda annonsen kunde inte hittas i systemet. Detta innebär förmodligen att den har tagits bort, eller att länken du klickat på är felaktig.
		</div>
		<a href="<portlet:renderURL/>" class="button">Tillbaka till startsidan</a>
	</div>
</c:if>