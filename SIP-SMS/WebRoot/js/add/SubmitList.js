$(document).ready(function(){

		$( "#submitButton").click(function(){
                        
                        //alert($("#strategyName").attr("value"));
                        //alert($("#cpu").val());
                        //alert($("#memory").val());
						//alert($(this).parent().next().text());
						//alert($(this).parent().next().next().children().val());
						//alert($(this).parent().next().next().children().next().next().val());
						$.post("servlet/strategyparaservlet",{
							strategyname : $("#strategyName").attr("value"),
							cpuvalue : $("#cpu").val(),
							memoryvalue : $("#memory").val(),
							},function(response){});
		});
});

		