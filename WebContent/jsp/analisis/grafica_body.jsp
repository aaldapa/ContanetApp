<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-1.8.2.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jqwidgets/jqxcore.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jqwidgets/jqxchart.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jqwidgets/jqxdata.js"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/jqwidgets/styles/jqx.base.css" />
<script type="text/javascript">
//jquery
var $j = jQuery.noConflict();
	
$j(document).ready(function (){

	var _url = "<%=request.getContextPath()%>/action/recargarAnalisis";
		
		var source = { datafields: [{ name: 'id' },{ name: 'mes' },{ name: 'anio' },{ name: 'ingresos' },{ name: 'gastos' },{ name: 'diferencias' }],
			datatype: "json",
			url: _url
		};
	    
	    var dataAdapter = new $j.jqx.dataAdapter(source, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error);} });
	    // prepare jqxChart settings
	    var settings = {
			title: "Gráfica de gastos e ingresos",
	        description: "Agrupación por meses",
	        showLegend: false,
	        enableAnimations: true,
	        padding: { left: 5, top: 5, right: 5, bottom: 5 },
	        titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	        source: dataAdapter,
	        categoryAxis:
	            {
	        	 	dataField: 'mes',
	                textRotationAngle: 50,
	                showGridLines: true, 
	                unitInterval: 1,
	                gridLinesInterval: 3,
	                gridLinesColor: '#888888',
	                axisSize: 'auto'
	            },
	        colorScheme: 'scheme01',
	       
	        seriesGroups:
	            [
	                {
	                    type: 'column',
	                    valueAxis:
	                    {
	                    	unitInterval: 500,
	                        displayValueAxis: true,
	                        description: 'Cantidades en euros'
	                    },
	                    series: [
	                             { dataField: 'ingresos', displayText: 'Ingresos'} ,
	                             { dataField: 'gastos', displayText: 'Gastos'} ,
	                             { dataField: 'diferencias', displayText: 'Diferencias'} 
	                        ]
	                }
	            ]
	    };
	    // setup the chart
	    $j('#jqxChart').jqxChart(settings);
});
</script>
</head>
<body class='default'>

<div style='height: 600px; width: 682px;'>
    <div id='host' style="margin: 0 auto; width:680px; height:400px;">
		<div id='jqxChart' style="width:680px; height:400px; position: relative; left: 0px; top: 0px;">
		</div>
	</div>
    </div>
</body>
</html>
