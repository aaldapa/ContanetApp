<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

  <!-- Main Page Container -->
  <div class="page-container">
   <!-- For alternative headers START PASTE here -->
	<!-- A. HEADER -->      
    <div class="header">
      <!-- A.1 HEADER MIDDLE -->
      <div class="header-middle">    
        <!-- Sitelogo and sitename -->
        <a class="sitelogo" href="<%=request.getContextPath()%>/action/viewExpediente" title="Ir a la página de inicio"></a>
        <div class="sitename">
          <h1><a href="<%=request.getContextPath()%>/action/viewExpediente" title="Ir a la página de inicio"><bean:message key="global.header"/></a></h1>
          <h2><bean:message key="global.subheader"/></h2>
        </div>
        <!-- Navigation Level 1 -->
        <div class="nav1">
          <ul>
            <li><html:link page="/action/logout" titleKey='<bean:message key="menu.texto.ayuda"/>'><bean:message key="menu.ayuda"/></html:link></li>
            <li><html:link page="/action/Email" titleKey='<bean:message key="menu.texto.contacto"/>'><bean:message key="menu.contacto"/></html:link></li>															
            <li><html:link page="/action/logout" titleKey='<bean:message key="menu.texto.salir"/>'><bean:message key="menu.salir"/></html:link></li>
          </ul>
        </div>              
      </div>
     
      <!-- A.2 HEADER BOTTOM -->
      <div class="header-bottom">
        <!-- Navigation Level 2 (Drop-down menus) -->
        <div class="nav2">
           <logic:equal name="LOGIN" property="usuario.perfil.idPerfil" value="1">
	         <!-- Navigation item -->   
	          <ul>
	            <li><a href="<%=request.getContextPath()%>/action/listarUsuarios"><bean:message key="menu.administracion"/></a>
	                <ul>
	                  <li><html:link page="/action/listarRoles"><bean:message key="menu.roles"/></html:link></li>
	                  <li><html:link page="/action/listarFamilias"><bean:message key="menu.familias"/></html:link></li>
	                  <li><html:link page="/action/listarUsuarios"> <bean:message key="menu.usuarios"/></html:link></li>
	                </ul>
	            </li>
	          </ul>
	            </logic:equal>
	          <!-- Navigation item -->
	          <ul>
	            <li><a href="<%=request.getContextPath()%>/action/listarContabilidades"><bean:message key="menu.configuracion"/></a>
	              <!--[if lte IE 6]><table><tr><td><![endif]-->
	                <ul>
	                  <li><html:link page="/action/listarContabilidades" paramName="LOGIN" paramProperty="usuario.idUsuario" paramId="idUsuario"><bean:message key="menu.configuracion.contabilidades"/></html:link></li>
	                  <li><html:link page="/action/listarCuentas" paramName="LOGIN" paramProperty="usuario.idUsuario" paramId="idUsuario"><bean:message key="menu.configuracion.cuentas"/></html:link></li>
	                  <li><html:link page="/action/listarDepositos" paramName="LOGIN" paramProperty="usuario.idUsuario" paramId="idUsuario"><bean:message key="menu.configuracion.depositos"/></html:link></li>
	                  <li><html:link page="/action/viewApuntePorDefecto"><bean:message key="menu.apuntepd"/></html:link></li>
	                </ul>
	              <!--[if lte IE 6]></td></tr></table></a><![endif]-->
	            </li>
	          </ul>        
	          <!-- Navigation item -->   
	          <ul>
	            <li><a href="<%=request.getContextPath()%>/action/listarGrupos"><bean:message key="menu.etiquetas"/></a><!--<![endif]-->
	              <!--[if lte IE 6]><table><tr><td><![endif]-->
	                <ul>
	                  <li><html:link page="/action/listarGrupos"><bean:message key="menu.etiquetas.grupos"/></html:link></li>
	                  <li><html:link page="/action/listarClases?idGrupo=0"><bean:message key="menu.etiquetas.clases"/></html:link></li>
	                </ul>
	              <!--[if lte IE 6]></td></tr></table></a><![endif]-->
	            </li>
	          </ul>
	          
	       
        </div>
	  </div>
	
      <!-- A.3 HEADER BREADCRUMBS -->
      <!-- Breadcrumbs -->
      
      <div class="header-breadcrumbs">
        <!-- 
        <ul>
          <li><a href="#">Home</a></li>
          <li><a href="#">Webdesign</a></li>
          <li><a href="#">Templates</a></li>
          <li>Multiflex-3</li>
        </ul>
        -->
        <%--
        <!-- Search form -->                  
        <div class="searchform">
          <form action="index.html" method="get">
            <fieldset>
              <input name="field" class="field"  value=" Search..." />
              <input type="submit" name="button" class="button" value="GO!" />
            </fieldset>
          </form>
        </div>
        --%>
      </div>
    </div>
    
  	<!-- For alternative headers END PASTE here -->