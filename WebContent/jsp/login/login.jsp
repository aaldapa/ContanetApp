<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@include file="/jsp/login/templates/templateLogin.jsp"%>

<tiles:insert beanName="templateLogin.default" beanScope="request">
	<tiles:put name="body-content" value="/jsp/login/login_body.jsp"/>
</tiles:insert>