<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="com.tutorial.struts.utils.Constantes" %>

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
		
		<link rel="icon" type="image/x-icon" href="<%=request.getContextPath()%>/img/favicon.ico" />
		
		<title><bean:message key="global.title"/></title>
		<html:base/>
	</head>
	<body>
		<!-- Header page information -->
    	<tiles:insert attribute="header"/>
  		<!-- Menu bar -->
		<!-- B. MAIN -->
	    <div class="main">
	      	<!-- B.1 MAIN NAVIGATION -->
	      	<div class="main-navigation">
	        	<!-- Navigation Level 3 -->
	        	<div class="round-border-topright"></div>
	        		<h1 class="first">
	        			<%-- <html:img page="/img/user-Green-icon.png" width="15px" height="15px" align="top" border="0"/>--%>
	        			<bean:write name="<%=Constantes.LOGIN%>" property="usuario.nombre" />
	        			<bean:write name="LOGIN" property="usuario.apellidos" />
	        		</h1>
	        		
	        		<!-- Navigation with grid style -->
	        		<dl class="nav3-grid">
          				<dt><html:link page="/action/viewExpediente"><bean:message key="menu.vistageneral"/></html:link></dt>
          					<dd><html:link page="/action/viewExpediente"><bean:message key="menu.vistageneral.estado"/></html:link></dd>
          				
          				<dt><html:link page="/action/listarApuntes"><bean:message key="menu.apuntes"/></html:link></dt>
	            			<dd><html:link page="/action/listarApuntes"><bean:message key="menu.apuntes.gestion"/></html:link></dd>
	            			<dd><html:link page="/action/seleccionarArchivoApuntes"><bean:message key="menu.apuntes.importar"/></html:link></dd>
	            		
	            		<dt><a href="<%=request.getContextPath()%>/action/listarGrupos"><bean:message key="menu.presupuestos"/></a></dt>
	            		
	            		<dt><a href="<%=request.getContextPath()%>/action/seleccionAnalisis"><bean:message key="menu.analisis"/></a></dt>
	            			<dd><html:link page="/action/informe"><bean:message key="menu.analisis.informes"/></html:link></dd>
	            			<dd><html:link page="/action/seleccionAnalisis"><bean:message key="menu.analisis.graficas"/></html:link></dd>
	            			<%-- <a href="<%=request.getContextPath()%>/jsp/analisis/analisis.jsp"></a> 
	            			<dd><a href="<%=request.getContextPath()%>/jsp/prueba/prueba.jsp">Prueba JSON</a></dd>
          					 --%>
	        		</dl>                        
	      		</div>
	      		<!-- B.1 MAIN CONTENT -->
	      		<div class="main-content">
	        	<!-- Content unit - One column -->
	        		<div class="column1-unit">                         
	         			<!-- Main body information -->
	    				<!-- ==================== CONTROL DE ERRORES ==================== -->
						<logic:messagesPresent message="false">
							<div class="errores">	
								<bean:message key="errors.title"/>
								<html:messages id="error"> 
									<li><bean:write name="error"/></li>
								</html:messages>
							</div>
						</logic:messagesPresent>
	    				
	    				<tiles:insert attribute="body-content"/>

	    				<hr class="clear-contentunit" />  
	        		</div>  
	        		                   
	      		</div>
	      		
	      	</div>	
  		<!-- Copyright information -->
    	<tiles:insert attribute="copyright"/>	
	</body>

</html:html>