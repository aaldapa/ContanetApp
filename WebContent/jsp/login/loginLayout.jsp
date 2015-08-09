<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<html:html locale="true">
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1"/>
  		<meta http-equiv="cache-control" content="no-cache" />
  		<meta http-equiv="expires" content="3600" />
		<meta name="revisit-after" content="2 days" />
		<meta name="robots" content="index,follow" />
		<meta name="publisher" content="Your publisher infos here ..." />
		<meta name="copyright" content="Your copyright infos here ..." />
		<meta name="author" content="Alberto Cuesta" />
		<meta name="distribution" content="global" />
		<meta name="description" content="Your page description here ..." />
		<meta name="keywords" content="Your keywords, keywords, keywords, here ..." />
		
		<link rel="stylesheet" type="text/css" media="screen,projection,print" href="<%=request.getContextPath()%>/css/layout2_setup.css" />
		<link rel="stylesheet" type="text/css" media="screen,projection,print" href="<%=request.getContextPath()%>/css/layout2_text.css" />
		
		<link rel="icon" type="image/x-icon" href="<%=request.getContextPath()%>/img/favicon.ico"/>
		<title><bean:message key="global.title"/></title>
		<html:base/>
	</head>
	<body>
		<!-- Main body information -->
    	<tiles:insert attribute="body-content"/>
	</body>

</html:html>