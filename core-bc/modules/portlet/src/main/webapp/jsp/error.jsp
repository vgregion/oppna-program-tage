<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:renderURL var="cancelUrl"/><%
%><portlet:defineObjects/>

<%--<c:if test="${config.useInternalResources}">--%>
	<script src="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/external/jquery/jquery-1.8.2.js") %>" type="text/javascript"></script>
<%--</c:if>--%>

<div id="content-primary" class="cf" role="main">
	<div class="content-header cf">
		<h1>Ett fel uppstod!</h1>
	</div>
	<p>Systemet kunde inte slutföra dig begäran då ett fel uppstod. Kontakta support om felet kvarstår!</p>
	<p>Klicka <a href="${cancelUrl}">här</a> för att börja om.
	<p>
		<strong>${error}</strong>
		<div style="border:1px #BBB solid; padding: 5px; width:600px">
			<a href="#" id="showHide" class="button">Visa detaljer</a>
			<div style="font-family:Courier New; font-size:8pt; display:none;" id="stack">
				<textarea style="white-space: nowrap;border:none;width:600px" rows="20">${stackTrace}</textarea>
			</div>
		</div>
	</p>
</div>

<script type="text/javascript">
	$("#showHide").click(function() {
		if ($("#stack").css("display") == "none") {
			$("#stack").css("display", "");
			$("#showHide").text("Dölj detaljer");
		} else {
			$("#stack").css("display", "none");
			$("#showHide").text("Visa detaljer");
		}
	});
</script>