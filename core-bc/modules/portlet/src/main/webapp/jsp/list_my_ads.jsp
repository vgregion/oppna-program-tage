<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<portlet:resourceURL id="thumbnail" cacheability="cacheLevelFull" var="thumbnailUrl"/><%
%><portlet:resourceURL id="subCategories" var="subCatUrl"/><%
%><portlet:renderURL var="cancelUrl" /><%
%><portlet:renderURL var="listMyAdsUrl"><portlet:param name="page" value="listMyAds"/></portlet:renderURL><%
%><portlet:actionURL name="filterMyAds" var="filterUrl"/><%
%><portlet:actionURL name="publishDrafts" var="publishDraftsUrl"/><%
%><portlet:defineObjects/>

<c:if test="${config.useInternalResources}">
	<script src="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/jquery/jquery-1.8.2.js") %>" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/css/retursidan.css")%>"/>
</c:if>

<div id="content-primary" class="cf clearfix" role="main">
	<c:if test="${not empty numberPublished}">
		<p class="portlet-msg-success">Publicerade ${numberPublished} annonser.</p>
	</c:if>

	<div class="content-header cf clearfix">
		<h1>Mina annonser</h1>
		<a href="${cancelUrl}" class="btn btn-default">Tillbaka</a>
	</div>
	<p>Här visas de annonser som du har lagt upp i Tage, både annonser som är publicerade och annonser som är bokade.</p>
	<form:form id="search-inventory-form" cssClass="form-general" modelAttribute="searchFilter" action="${filterUrl}" >
		<div class="row cols-3 cf clearfix">
			<div class="select medium col col-1">
				<label for="508e6b767babe">Kategori</label>
				<form:select id="508e6b767babe" path="topCategory.id">
					<form:option value="-1" selected="selected">Alla</form:option>
					<form:options items="${topCategories}" itemLabel="title" itemValue="id"/>
				</form:select>
			</div>
			<div class="select medium col col-2">
				<label for="508e6b767bc24">Underkategori</label>
				<form:select id="508e6b767bc24" path="subCategory.id">
					<option value="-1" selected="selected">Alla</option>
					<c:if test="${!empty subCategories}">
						<form:options items="${subCategories}" itemLabel="title" itemValue="id"/>
					</c:if>
				</form:select>
			</div>
            <div class="select medium col col-3">
				<label for="508e6b767bc24">Status</label>
				<form:select id="508e6b767bc24" path="status">
					<form:option value="${null}" label="Alla"/>
					<form:option value="PUBLISHED" label="Publicerad"/>
					<form:option value="BOOKED" label="Bokad"/>
					<form:option value="EXPIRED" label="Utgången"/>
					<form:option value="DRAFT" label="Utkast"/>
				</form:select>
			</div>
            <div class="row cols-1 cf clearfix">
                <%--<div class="col small col-1 submit-area">--%>
                <aui:button-row>
                    <button class="btn btn-primary" type="submit" name="submit-508e6b767c18f">Visa</button>
                </aui:button-row>
                <%--</div>--%>
            </div>
        </div>
	</form:form>
	<h2>
		<span id="headerTitle">Mina annonser</span>
		<form:form cssStyle="float: right" action="${publishDraftsUrl}"><button class="btn btn-primary" type="submit">Publicera alla utkast</button></form:form>
	</h2>
	<c:if test="${fn:length(ads.list) gt 0}">
		<ul class="inventory-listing">
			<c:forEach items="${ads.list}" var="ad">
				<portlet:renderURL var="viewAdUrl">
					<portlet:param name="page" value="viewAd"/>
					<portlet:param name="advertisementId" value="${ad.id}"/>
					<portlet:param name="previousPage" value="listMyAds" />
					<portlet:param name="externalPage" value="none" />
				</portlet:renderURL>

				<c:if test="${fn:length(ad.photos) gt 0}">
					<c:forEach var="photo" items="${ad.photos}" end="0">
		     			<c:set var="imageUrl" value="${thumbnailUrl}&id=${photo.id}"/>
					</c:forEach>
				</c:if>
				<c:if test="${fn:length(ad.photos) eq 0}">
	     			<c:set var="imageUrl" value="${thumbnailUrl}&id="/>
				</c:if>

				<li class="cf clearfix">
					<a class="ad-link" href="${viewAdUrl}">
						<div class="image">
							<img class="thumbnail" src="${imageUrl}" alt="">
						</div>
						<div class="content">
							<div class="meta"><span class="date">${ad.formattedCreatedDate}</span></div>
							<h3>${ad.title}</h3>
							<div class="meta"><span class="author">Upplagd av <strong>${ad.unit.name}</strong></span></div>
							<c:if test="${not empty ad.area}"><div class="meta"><span class="">Geografiskt område <strong>${ad.area.name}</strong></span></div></c:if>
							<p class="meta"><span class="category">${ad.category.parent.title} <span class="sep">&gt;</span> ${ad.category.title}</span></p>
							<p>
								<c:if test="${ad.status eq 'PUBLISHED'}">Publicerad</c:if>
								<c:if test="${ad.status eq 'BOOKED'}">Bokad</c:if>
								<c:if test="${ad.status eq 'EXPIRED'}">Utgången</c:if>
								<c:if test="${ad.status eq 'DRAFT'}">Utkast</c:if>
							</p>

						</div>
					</a>
				</li>
			</c:forEach>
		</ul>
		<c:if test="${ads.numberOfPages > 1}">
			<div class="paging cf clearfix">
				<strong>Gå till sida:</strong>
				<ul>
					<c:if test="${ads.hasPrevious}">
						<portlet:actionURL name="filterMyAds" var="prevUrl"><portlet:param name="pageIdx" value="${ads.page-1}"/></portlet:actionURL>
						<portlet:actionURL name="filterMyAds" var="firstUrl"><portlet:param name="pageIdx" value="1"/></portlet:actionURL>
						<li><a href="${firstUrl}" title="Länk till första sidan">&lt;&lt;</a></li>
						<li><a href="${prevUrl}" title="Länk till föregående sida">&lt;</a></li>
					</c:if>
					<c:forEach var="pageIdx" begin="1" end="${ads.numberOfPages}">
						<c:if test="${pageIdx eq ads.page}">
							<li>${pageIdx}</li>
						</c:if>
						<c:if test="${pageIdx ne ads.page}">
							<portlet:actionURL name="filterMyAds" var="pageUrl"><portlet:param name="pageIdx" value="${pageIdx}"/></portlet:actionURL>
							<li><a href="${pageUrl}">${pageIdx}</a></li>
						</c:if>
					</c:forEach>
					<c:if test="${ads.hasNext}">
						<portlet:actionURL name="filterMyAds" var="nextUrl"><portlet:param name="pageIdx" value="${ads.page+1}"/></portlet:actionURL>
						<portlet:actionURL name="filterMyAds" var="lastUrl"><portlet:param name="pageIdx" value="${ads.numberOfPages}"/></portlet:actionURL>
						<li><a href="${nextUrl}" title="Länk till nästa sida">&gt;</a></li>
						<li><a href="${lastUrl}" title="Länk till sista sidan">&gt;&gt;</a></li>
					</c:if>
				</ul>
			</div>
		</c:if>
	</c:if>
	<c:if test="${fn:length(ads.list) eq 0}">
		<div>Inga annonser hittades.</div>
	</c:if>
</div>

<script type="text/javascript">
	// construct the header based on filter selections
	$(document).ready(function() {
		if ($("#508e6b767babe").val() != -1) {
			$("#headerTitle").append(" i kategori " + $("#508e6b767babe option:selected").text());
		}
		if ($("#508e6b767bc24").val() != -1) {
			$("#headerTitle").append(" > " + $("#508e6b767bc24 option:selected").text());
		}
	});

	$("#508e6b767babe").change(function() {
		$.ajax({
			type: "POST",
			url: "${subCatUrl}",
			dataType: "html",
			data: { parent: $(this).find(":selected").val() },
			success: function(result) {
				$("#508e6b767bc24").html("<option value=\"-1\" selected=\"selected\">Alla</option>" + result);
				$("#508e6b767bc24").trigger("change");
			},
			error: function(result) {
				alert("Ett fel uppstod på servern när underkategorier hämtades: " + result.responseText);
			}
		});
	});
</script>
