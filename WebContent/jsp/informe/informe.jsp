<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@include file="../template.jsp"%>

<tiles:insert beanName="template.default" beanScope="request" page="../defaultLayout.jsp">
	<tiles:put name="body-content" value="/jsp/informe/informe_body.jsp" />
</tiles:insert>