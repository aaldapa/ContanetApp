<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<tiles:definition id="template.default" page="defaultLayout.jsp" scope="request">
	<tiles:put name="header" value="header.jsp"/>
	<tiles:put name="menubar" value="menu.jsp"/>
	<tiles:put name="copyright" value="copyright.jsp"/>
</tiles:definition>
