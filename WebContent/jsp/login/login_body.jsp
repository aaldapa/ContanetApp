<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


 
	<div class="main-content-login">
		<div class="logon-unit">
			<html:form action="login" method="post">
		    	
		    	<logic:messagesPresent message="false">
							<div class="erroresInicio">	
								<bean:message key="errors.title"/>
								<html:messages id="error"> 
									<li><bean:write name="error"/></li>
								</html:messages>
							</div>
				</logic:messagesPresent>
		   	
		   	   <h1> <bean:message key="login.header"/> </h1>
		   	   <bean:message key="login.instructions"/>
		       <div class="contactformLogin">
		            <fieldset>
		            	<legend><bean:message key="login.legend"/></legend>
		                <table style="margin-left: 40px;margin-bottom: 20px;">
		                	
		                	<tr>
		                		<td>
		                			<html:img page="/img/app-spreadsheet-icon.png" width="48px" height="48px" align="top"/>
		                		</td>
		                		<td >
		                			<label class="top"><bean:message key="label.email"/></label><br />
									<html:text size="22" property="email" styleClass="field"/>
								</td>
		                		
		                	</tr>
		                	<tr>
		                		<td></td>
		                		<td>
		                			<label class="top"><bean:message key="label.password"/></label><br />
			                  		<html:password styleClass="field" size="22" property="password" value=""/>
		                		</td>
		                		
		                	</tr>
		               	</table>
					</fieldset>
				</div>
				<div class="botonera" >
		 			<html:submit styleClass="boton" value="LOGIN"/>
		 		</div>
			</html:form>
		</div>      
	
	</div>


