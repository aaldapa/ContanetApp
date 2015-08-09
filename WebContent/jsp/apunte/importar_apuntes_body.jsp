<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!-- Defino variables para referenciar los textos de los botones-->

<html:form styleId="fomulario" action="/importarApuntes" method="post" enctype="multipart/form-data">

<html:hidden property="id"></html:hidden>
	<logic:messagesNotPresent message="false">
		<logic:present name="updloadApunteForm" property="apuntesInsertados">
			<div class="mensajes">	
				<bean:message key="messages.title"/>
					<ul style="list-style: none outside none;  margin: 0 0";>
						<li style="margin: -0.5;font-size:100%;">
						<bean:message key="mensaje.apunte.napuntesinsertados"/>&nbsp;
						<bean:write name="updloadApunteForm" property="apuntesInsertados"/>
						</li>
					</ul>
			</div>
		</logic:present>
	</logic:messagesNotPresent>
	
	<h1 class="pagetitle">
		<bean:message key="apunte.importar.titulo"/>
	</h1>
	
	<logic:messagesPresent message="true">
			<div class="mensajes">	
				<bean:message key="messages.title"/>
				<ul>
					<li><bean:write name="updloadApunteForm" property="apuntesInsertados"/></li>
				</ul>
			</div>
	</logic:messagesPresent>
	
	
	
	<div class="contactForm">
		<fieldset>
			<legend><bean:message key="apunte.importar.leyenda"/></legend>
			<!--
			Guardo el listado de contabilidades en una variable para referenciarla mas tarde en el 
			collections del option porque no puedo o no se acceder al listado de forma directa 
			-->
			<p>
				<label class="left"><bean:message key="apunte.importar.contabilidades" /></label>
				<bean:define id="contabilidadesCollection" name="updloadApunteForm" property="lstContabilidades" />
					
				<html:select styleId="id_combo" name="updloadApunteForm" property="idContabilidad" onchange="reload()">
					<html:option  value="0" key="apunte.importar.seleccion.contabilidad" />
						<logic:present name="updloadApunteForm" property="lstContabilidades">
							<html:options collection="contabilidadesCollection"  property="idContabilidad" labelProperty="nombreContabilidad"/>
						</logic:present>
				</html:select>
			</p>
			<p>
				<label class="left"><bean:message key="apunte.importar.cuentas" /></label>
				<bean:define id="cuentasCollection" name="updloadApunteForm" property="lstCuentas" />
					
				<html:select styleId="id_combo" name="updloadApunteForm" property="idCuenta">
					<html:option  value="0" key="apunte.importar.seleccion.cuenta" />
						<logic:present name="updloadApunteForm" property="lstCuentas">
							<html:options collection="cuentasCollection"  property="idCuenta" labelProperty="notas"/>
						</logic:present>
				</html:select>
			</p>
			
			<p>
				<label class="left">
					<html:img page="/img/icono_info.png" width="15px" height="15px" align="top"  titleKey="info.apunte.archivo"/>
					<bean:message key="apunte.importar.selecion.archivo" />
				</label>
				<html:file property="file" />
			</p>
		
		</fieldset>
	</div>
	
	
	
	<div class="botonera">
		<html:submit  property="accion" styleClass="boton">
			<bean:message key="apunte.importar.boton.carga" />
		</html:submit>
	</div>
	
	
</html:form>

<script type="text/javascript">
	function reload(){
		var accion="<%=request.getContextPath()%>/action/seleccionarArchivoApuntes?idContabilidad="+document.getElementById("id_combo").value;
		document.getElementById('fomulario').action=accion;
		document.getElementById('fomulario').submit();
	}
</script>