/*CARGA DE BASE DE DATOS PARA EL INICIO DEL USO DE LA APLICACION*/
/*Perfiles*/
go
INSERT INTO `contanetapp`.`perfil` (`id_perfil`, `nombre`, `descripcion`,
  `baja`)
  VALUES (1, 'ADMIN', 'Administrador del sistema', 'N');

/*Usuarios*/
go
INSERT INTO `contanetapp`.`usuario` (`id_usuario`, `id_perfil`,
  `LastName`, `FirstName`, `email`, `password`, `baja`)
  VALUES (1, 1, 'ADMINISTRADOR', 'DEL SISTEMA', 'admin', 'admin',
    'N');
    
/*Grupos*/
go
INSERT INTO `contanetapp`.`grupo` (`id_grupo`,
  `nombre`, `notas`, `baja`)
  VALUES (1, 'SIN GRUPO', 'Para clases sin agrupar', 'N');
  
/*Clases*/
go
INSERT INTO `contanetapp`.`clase` (`id_clase`,`id_grupo`,
  `nombre`, `notas`, `baja`)
  VALUES (1, 1, 'SIN CLASE', 'Para apuntes sin agrupar', 'N');
  
/*Vista vista_apuntes_groupby_mes*/
go
DROP VIEW IF EXISTS `vista_apuntes_groupby_mes`;
go
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_apuntes_groupby_mes` AS (SELECT CONCAT(SUBSTR(`apt`.`fecha`,1,4),SUBSTR(`apt`.`fecha`,6,2)) AS `id_vista`,SUM(IF((`apt`.`tipo_operacion` = 'INGRESO'),`apt`.`importe`,0)) AS `INGRESOS`,SUM(IF((`apt`.`tipo_operacion` = 'GASTO'),`apt`.`importe`,0)) AS `GASTOS`,(SUM(IF((`apt`.`tipo_operacion` = 'INGRESO'),`apt`.`importe`,0)) + SUM(IF((`apt`.`tipo_operacion` = 'GASTO'),`apt`.`importe`,0))) AS `DIFERENCIAS`,SUBSTR(`apt`.`fecha`,6,2) AS `MESNUMERO`,(CASE SUBSTR(`apt`.`fecha`,6,2) WHEN '01' THEN 'Enero' WHEN '02' THEN 'Febrero' WHEN '03' THEN 'Marzo' WHEN '04' THEN 'Abril' WHEN '05' THEN 'Mayo' WHEN '06' THEN 'Junio' WHEN '07' THEN 'Julio' WHEN '08' THEN 'Agosto' WHEN '09' THEN 'Septiembre' WHEN '10' THEN 'Octubre' WHEN '11' THEN 'Noviembre' WHEN '12' THEN 'Diciembre' END) AS `MES`,SUBSTR(`apt`.`fecha`,1,4) AS `ANIO`,`apt`.`id_cuenta` AS `CUENTA` FROM ((`apunte` `apt` JOIN `clase` `cl`) JOIN `grupo` `gr`) WHERE ((`cl`.`id_clase` = `apt`.`id_clase`) AND (`cl`.`id_grupo` = `gr`.`id_grupo`) AND (`apt`.`baja` = 'N') AND (`apt`.`traspaso` = 'N')) GROUP BY `apt`.`id_cuenta`,SUBSTR(`apt`.`fecha`,1,4),SUBSTR(`apt`.`fecha`,6,2) ORDER BY `apt`.`id_cuenta`,SUBSTR(`apt`.`fecha`,1,4),SUBSTR(`apt`.`fecha`,6,2));

/*Vista vista_apuntes_groupby_grupos*/
go
DROP VIEW IF EXISTS `vista_apuntes_groupby_grupos`;
go
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_apuntes_groupby_grupos` AS (SELECT CONCAT(SUBSTR(`apt`.`fecha`,1,4),`apt`.`id_cuenta`,`gr`.`id_grupo`) AS `id_vista`,`gr`.`nombre` AS `GRUPO`,SUM(IF((`apt`.`tipo_operacion` = 'INGRESO'),`apt`.`importe`,0)) AS `INGRESOS`,SUM(IF((`apt`.`tipo_operacion` = 'GASTO'),`apt`.`importe`,0)) AS `GASTOS`,(SUM(IF((`apt`.`tipo_operacion` = 'INGRESO'),`apt`.`importe`,0)) + SUM(IF((`apt`.`tipo_operacion` = 'GASTO'),`apt`.`importe`,0))) AS `DIFERENCIAS`,SUBSTR(`apt`.`fecha`,1,4) AS `ANIO`,`apt`.`id_cuenta` AS `CUENTA` FROM ((`apunte` `apt` JOIN `clase` `cl`) JOIN `grupo` `gr`) WHERE ((`cl`.`id_clase` = `apt`.`id_clase`) AND (`cl`.`id_grupo` = `gr`.`id_grupo`) AND (`apt`.`baja` = 'N') AND (`apt`.`traspaso`= 'N')) GROUP BY `gr`.`id_grupo`,`apt`.`id_cuenta`,SUBSTR(`apt`.`fecha`,1,4) ORDER BY `apt`.`id_cuenta`,SUBSTR(`apt`.`fecha`,1,4),`gr`.`nombre`);

/*Vista vista_apuntes_groupby_clases*/
go
DROP VIEW IF EXISTS `vista_apuntes_groupby_clases`;
go
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_apuntes_groupby_clases` AS (SELECT CONCAT(SUBSTR(`apt`.`fecha`,1,4),`cl`.`id_clase`) AS `id_vista`,`gr`.`id_grupo` AS `IDGRUPO`,`gr`.`nombre` AS `GRUPO`,`cl`.`nombre` AS `CLASE`,SUM(IF((`apt`.`tipo_operacion` = 'INGRESO'),`apt`.`importe`,0)) AS `INGRESOS`,SUM(IF((`apt`.`tipo_operacion` = 'GASTO'),`apt`.`importe`,0)) AS `GASTOS`,(SUM(IF((`apt`.`tipo_operacion` = 'INGRESO'),`apt`.`importe`,0)) + SUM(IF((`apt`.`tipo_operacion` = 'GASTO'),`apt`.`importe`,0))) AS `DIFERENCIAS`,SUBSTR(`apt`.`fecha`,1,4) AS `ANIO`,`apt`.`id_cuenta` AS `CUENTA`FROM ((`apunte` `apt` JOIN `clase` `cl`) JOIN `grupo` `gr`)WHERE ((`cl`.`id_clase` = `apt`.`id_clase`)AND (`cl`.`id_grupo` = `gr`.`id_grupo`)AND (`apt`.`baja` = 'N')AND (`apt`.`traspaso`= 'N'))GROUP BY `cl`.`id_clase`,`apt`.`id_cuenta`,SUBSTR(`apt`.`fecha`,1,4)ORDER BY `gr`.`nombre`,`cl`.`nombre`,`apt`.`id_cuenta`,SUBSTR(`apt`.`fecha`,1,4),`gr`.`nombre`);

/*TmaeBancos*/
go
INSERT INTO`tmaebanco`(id_banco,nombre_banco,nombre_logotipo,logotipo) 
values (1,'Kutxabank',NULL,NULL),(2,'ING Direct',NULL,NULL),(3,'Open Bank',NULL,NULL);