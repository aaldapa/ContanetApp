<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<!-- B. MAIN -->
    <div class="main">
       <!-- B.1 MAIN NAVIGATION -->
      <div class="main-navigation">
        <!-- Navigation Level 3 -->
        <div class="round-border-topright"></div>
        <h1 class="first">Title</h1>
        <!-- Navigation with grid style -->
        <dl class="nav3-grid">
          <dt><html:link page="/action/viewperfil"><bean:message key="menu.perfil"/>sss</html:link></dt>
          <dt><html:link page="/action/viewperfil"><bean:message key="menu.usuarios"/></html:link></dt>
          <dt><html:link page="/action/listarContabilidades"><bean:message key="menu.contabilidad"/></html:link></dt>
            <dd><a href="#">Navlink 121</a></dd>
            <dd><a href="#">Navlink 122</a></dd>
            <dd><a href="#">Navlink 123</a></dd>		
          <dt><a href="#">Navlink 13</a></dt>
          <dt><a href="#">Navlink 14</a></dt>
          <dt><a href="#">Navlink 15</a></dt>
        </dl>                        
      </div>
      <!-- B.1 MAIN CONTENT -->
      <div class="main-content">
        <!-- Pagetitle -->
        <h1 class="pagetitle">Multiflex-3 / Layout-2</h1>
        <!-- Content unit - One column -->
        <div class="column1-unit">
          <h1>Here comes the title</h1>                            
          <h3>Monday, 20 November 2006 at 20:30 GMT, by <a href="#">SiteAdmin </a></h3>
        </div>          
        <hr class="clear-contentunit" />
       
        <div class="column1-unit">                         
         <%-- <tiles:insert attribute="body-content"/>--%>   
        </div>          
        <hr class="clear-contentunit" />
        
      </div>
           
      
    </div>