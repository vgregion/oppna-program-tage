<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
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
	<script src="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/js/tage.js") %>" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/css/retursidan.css")%>"/>
</c:if>

<portlet:renderURL var="backUrl">
	<c:if test="${not empty previousPage}">
		<portlet:param name="page" value="${previousPage}"/>
	</c:if>
</portlet:renderURL>


<c:if test="${not empty advertisement}">
	<div id="content-primary" class="article cf clearfix" role="main">
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
					<c:if test="${advertisement.status eq 'DRAFT'}">
						<portlet:actionURL name="performRepublishing" var="publishDraftUrl">
							<portlet:param name="advertisementId" value="${advertisement.id}"/>
						</portlet:actionURL>
						<a href="${publishDraftUrl}" class="btn btn-primary">Publicera utkast</a>
					</c:if>
					<c:if test="${not (advertisement.status eq 'DRAFT')}">
						<portlet:renderURL var="republishAdUrl">
							<portlet:param name="page" value="republishAd"/>
							<portlet:param name="advertisementId" value="${advertisement.id}"/>
						</portlet:renderURL>
						<a href="${republishAdUrl}" class="btn btn-primary">Återpublicera</a>
					</c:if>
				</c:if>
				<portlet:actionURL name="loadAd" var="changeAdUrl">
					<portlet:param name="advertisementId" value="${advertisement.id}"/>
				</portlet:actionURL>
				<a href="${changeAdUrl}" class="btn btn-primary">Redigera</a>
			</c:if>

			<c:if test="${userId eq advertisement.creatorUid}">
				<portlet:renderURL var="copyAd" portletMode="view">
					<portlet:param name="page" value="createAd"/>
					<portlet:param name="copyAdvertisementId" value="${advertisement.id}"/>
				</portlet:renderURL>
				<a class="btn btn-primary" href="${copyAd}">Kopiera till ny annons</a>
			</c:if>

			<c:if test="${userId eq advertisement.creatorUid}">
                <portlet:actionURL var="hideAd" portletMode="view">
                    <portlet:param name="previousPage" value="${previousPage}"/>
                    <portlet:param name="hideAdvertisementId" value="${advertisement.id}"/>
                </portlet:actionURL>
                <portlet:actionURL var="hideAdAndMarkAsBooked" portletMode="view">
                    <portlet:param name="previousPage" value="${previousPage}"/>
                    <portlet:param name="hideAdvertisementId" value="${advertisement.id}"/>
                    <portlet:param name="markAsBooked" value="true"/>
                </portlet:actionURL>
                <a class="hide-ad-link btn btn-danger ${not (advertisement.status eq 'BOOKED') ? 'hide' : ''}" href="" data-href="${hideAd}">Ta bort</a>
                <a class="hide-ad-link-with-decision btn btn-danger ${advertisement.status eq 'BOOKED' ? 'hide' : ''}" href="" data-href="${hideAd}" data-href-booked="${hideAdAndMarkAsBooked}">Ta bort</a>
			</c:if>

			<a href="${backUrl}" class="btn btn-default">Tillbaka</a>
		</aui:button-row>

		<h1>${advertisement.title}</h1>

		<div class="inventory-info">
			<c:if test="${fn:length(advertisement.photos) ne 0}">
				<div class="flexslider">
					<ul class="slides cf clearfix" style="list-style: none">
						<c:forEach items="${advertisement.photos}" var="photo">
							<li data-thumb="${thumbnailUrl}&id=${photo.id}">
								<img class="thumbnail" src="${photoUrl}&id=${photo.id}" alt="" />
							</li>
						</c:forEach>
					</ul>
				</div>
			</c:if>
			<p class="meta"><span class="date">${advertisement.formattedCreatedDate}</span> | <span class="category">${advertisement.category.parent.title} <span class="sep">&gt;</span> ${advertisement.category.title}</span></p>
			<p class="meta">Bortskänkes av <strong>${advertisement.unit.name}</strong></p>
			<c:if test="${not empty advertisement.area.name}">
				<p class="meta">Geografiskt område <strong>${advertisement.area.name}</strong></p>
			</c:if>
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

			<c:if test="${not empty booker}">
				<h2>Bokad av</h2>
				<div class="contact-person">
					<span>${booker.userId}</span>
					<span>${booker.phone}</span>
					<div class="email"><a href="mailto:${booker.email}">${booker.email}</a></div>
				</div>
			</c:if>

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
						<c:if test="${advertisement.status eq 'DRAFT'}">
							<portlet:actionURL name="performRepublishing" var="publishDraftUrl">
								<portlet:param name="advertisementId" value="${advertisement.id}"/>
							</portlet:actionURL>
							<a href="${publishDraftUrl}" class="btn btn-primary">Publicera utkast</a>
						</c:if>
						<c:if test="${not (advertisement.status eq 'DRAFT')}">
							<portlet:renderURL var="republishAdUrl">
								<portlet:param name="page" value="republishAd"/>
								<portlet:param name="advertisementId" value="${advertisement.id}"/>
							</portlet:renderURL>
							<a href="${republishAdUrl}" class="btn btn-primary">Återpublicera</a>
						</c:if>
                    </c:if>
                    <portlet:actionURL name="loadAd" var="changeAdUrl">
                        <portlet:param name="advertisementId" value="${advertisement.id}"/>
                    </portlet:actionURL>
                    <a href="${changeAdUrl}" class="btn btn-primary">Redigera</a>
                </c:if>

				<c:if test="${userId eq advertisement.creatorUid}">
					<portlet:renderURL var="copyAd">
						<portlet:param name="page" value="createAd"/>
						<portlet:param name="copyAdvertisementId" value="${advertisement.id}"/>
					</portlet:renderURL>
					<a class="btn btn-primary" href="${copyAd}">Kopiera till ny annons</a>
				</c:if>

				<c:if test="${userId eq advertisement.creatorUid}">
					<portlet:actionURL var="hideAd" portletMode="view">
						<portlet:param name="previousPage" value="${previousPage}"/>
						<portlet:param name="hideAdvertisementId" value="${advertisement.id}"/>
					</portlet:actionURL>
					<portlet:actionURL var="hideAdAndMarkAsBooked" portletMode="view">
						<portlet:param name="previousPage" value="${previousPage}"/>
						<portlet:param name="hideAdvertisementId" value="${advertisement.id}"/>
						<portlet:param name="markAsBooked" value="true"/>
					</portlet:actionURL>
					<a class="hide-ad-link btn btn-danger ${not (advertisement.status eq 'BOOKED') ? 'hide' : ''}" href="" data-href="${hideAd}">Ta bort</a>
					<a class="hide-ad-link-with-decision btn btn-danger ${advertisement.status eq 'BOOKED' ? 'hide' : ''}" href="" data-href="${hideAd}" data-href-booked="${hideAdAndMarkAsBooked}">Ta bort</a>
				</c:if>

				<a href="<portlet:renderURL/>" class="btn btn-default">Tillbaka</a>
            </aui:button-row>
		</div>
	</div>
</c:if>
<c:if test="${empty advertisement}">
	<div id="content-primary" class="article cf clearfix" role="main">
		<p class="back-link"><a href="<portlet:renderURL/>">Tillbaka</a></p>
		<h1>Annonsen kunde inte hittas</h1>
		<div class="inventory-info">
			Den begärda annonsen kunde inte hittas i systemet. Detta innebär förmodligen att den har tagits bort, eller att länken du klickat på är felaktig.
		</div>
		<a href="<portlet:renderURL/>" class="button">Tillbaka till startsidan</a>
	</div>
</c:if>

<div class="dialog" id="justConfirmDialog">
	<p>Ta bort bokad annons från mina annonser. Klicka på &#8221;Bekräfta ta bort&#8221;.</p>

    <div>
        <button class="btn btn-default" onclick="javascript: $('#justConfirmDialog').hide(); $('#maskElement').hide();">Avbryt</button>
        <button id="justConfirmButton" class="btn btn-primary">Bekräfta ta bort</button>
    </div>
</div>

<div class="dialog" id="decideWhetherBooked">
	<p>Om möbeln har hämtats för fortsatt användning, klicka på &#8221;Bekräfta ta bort och markera som bokad&#8221;. Om möbeln däremot har kasserats klicka på &#8221;Bekräfta ta bort&#8221;.</p>

    <div>
        <button class="btn btn-default" onclick="javascript: $('#decideWhetherBooked').hide(); $('#maskElement').hide();">Avbryt</button>
        <button id="decideWhetherBookedButtonNotBooked" class="btn btn-primary">Bekräfta ta bort</button>
        <button id="decideWhetherBookedButtonBooked" class="btn btn-primary">Bekräfta ta bort och markera som bokad</button>
    </div>
</div>

<div id="maskElement"></div>
