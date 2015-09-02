$(document).ready(function(){

		// $( "#ServiceRouting" ).click(function(){
			//alert($(this).attr("name"));
			$.post("strategylistservlet",{
				strategytype :"COPYSTRGLIST"
			},function(data){
				$("#tableTbody").empty();
				data=JSON.parse(data);
				//console.log(data.length);
				//var color=["#27A9E3","#35B347","#E02222","#FEB848","#27A9E3","#35B347","#E02222","#FEB848","#27A9E3","#35B347","#E02222","#FEB848","#27A9E3","#35B347"];
				for(var i=0;i<data.COPYSTRGLIST.length;i++)
				//for(var i=0;i<4;i++)
					{	
						// var txtHtml = "<tr><td><input type='radio' name='Choose' onclick="check_value(this)"/></td><td>"
						// +data.SERVROUTESTRGLIST[i].strategyname
						// +"</td><td>"
						// +data.SERVROUTESTRGLIST[i].description
						// +"<td><input type='button' name='change' value="修改" class="btn btn-default btn-xs change"/></td></tr>";
						if(data.COPYSTRGLIST[i].inuse==0){
							var txtHtml = "<tr><td><input type='radio' name='radio' class='radio'/></td><td><label class='radio'>"
						+data.COPYSTRGLIST[i].strategyname
						+"</label></td><td>"
						+data.COPYSTRGLIST[i].description
						+"</td><td><input type='button' name='change' value='修改' class='btn btn-primary btn-sm' /></td></tr>";
						}
						else{
							var txtHtml = "<tr><td><input type='radio' name='radio' class='radio' checked='checked'/></td><td><label class='radio'>"
						+data.COPYSTRGLIST[i].strategyname
						+"</label></td><td>"
						+data.COPYSTRGLIST[i].description
						+"</td><td><input type='button' name='change' value='修改' class='btn btn-primary btn-sm' /></td></tr>";
						}

					$("#tableTbody").append(txtHtml);	
					}
				$("input[name='radio']").click(function(){
				    $(".change").attr("disabled",true);
				    $(this).parent().next().next().next().children().attr("disabled",false);
			    });
				$("input[name='change']").click(function(){
					var policyname = $(this).parent().prev().prev().text();
				    var discription = $(this).parent().prev().text();
				    $("#discript").attr("value",discription);
			        $("#strategyName").attr("value",policyname);
			        for(var i=0;i<data.COPYSTRGLIST.length;i++){
			        	if(data.COPYSTRGLIST[i].strategyname==policyname){
			        		$("#cpu").attr("value",data.COPYSTRGLIST[i].cpuvalue);
			    		    $("#memory").attr("value",data.COPYSTRGLIST[i].memoryvalue);
			    	    }

			        }
			        $(".page1").dialog("open");
			    });
			    $(".select").click(function(){
				$(".page1").dialog("close");
				//alert("参数设置成功！")；
			    });
		});
	});