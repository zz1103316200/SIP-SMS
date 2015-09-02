var CompID;   //一级服务的ID
var SubName;   //二级服务的名字
$(document).ready(function() {	
	
	$.ajax({
		type:"post",
		url:"servlet/ServiceList",
		data:{type:'serviceId'},
		success:function(data){
			$("#newServicename").empty();
			for(var i in data)
			{	
				for(var key in data[i])
				{	
					$("#newServicename").append("<option value=" + key +">"+ data[i][key] + "</option>");
				}				
			}
			
			//根据一级服务查找二级服务的情报
			$("#SubServicename").click(function(){
				CompID = $("#newServicename").val();
				alert(CompID);
				$.post("servlet/ServiceList",{
					type:"SubService",
					ID:CompID
				},function(data){
					$("#SubServicename").empty();
					for(var i in data)
					{	
						for(var key in data[i])
						{	
							$("#SubServicename").append("<option value=" + data[i][key] +">"+ data[i][key] + "</option>");
						}				
					}
				});
			});
			
			//根据一级、二级服务查找接口情报
			$("#Interface").click(function(){
				SubName = $("#SubServicename").val();
				alert(SubName);
				$.post("servlet/ServiceList",{
					type:"Interface",
					ID:CompID,
					Name:SubName
				},function(data){
					$("#Interface").empty();
					for(var i in data)
					{	
						for(var key in data[i])
						{	
							$("#Interface").append("<option value=" + data[i][key] +">"+ data[i][key] + "</option>");
						}				
					}
				});
			});
			
		}
	})
	//修改弹出框
	$(".page1").dialog({
		autoOpen : false,
		modal : true,
		title : "修改"
	});
	
	$(".page2").dialog({
		autoOpen : false,
		modal : true,
		title : "添加业务组件"
	});	
	$("#AddComp").click(function(){
		$(".page2").dialog("open");
	});
	
	
	//选择搜索类型	
	$("#search-type").change(function(){
		//alert("sdf");
		var searchtype = $("#search-type").val();
		if(searchtype == "CompName" || searchtype == "Description" || searchtype == "Status"){
			$(".filtertype").empty();
			$(".filtertype").append("<input type='text' class='input-sm' id='searchvalue'/>");
			
		}else{
			//alert(searchtype);
			$(".filtertype").empty();
			$(".filtertype").append("<select class='input-sm' id='searchvalue' style='font-size:14px;display: inline-block;width: 9.3%;'><select>");
			$.ajax({
				type:"post",
				url: "servlet/ServiceList",
				data:{
					type : searchtype 
					},
				success:function(data){
					//alert(data);
					$("#searchvalue").empty();
					for(var i=0;i<data.length;i++){
						$("#searchvalue").append("<option value=" + data[i] +">"+ data[i] + "</option>");
					}
					
				}
			});
		}

	});

	
	
	/*
	 * 瀹炵幇鏁版嵁鍒嗛〉閮ㄥ垎-wenyanqi
	 */
	//涓変釜鍏ㄥ眬鍙橀噺
	//鑾峰彇褰撳墠涓�〉鏄剧ず澶氬皯鏉¤褰�
	var itemnumber = $("#itemnumber").val();
	//涓�叡鏈夊灏戦〉
	var pagenum;
	//涓�叡鏈夊灏戞潯璁板綍
	var allitemnumber;
	
	//鍏ㄩ儴鑺傜偣锛氭樉绀哄叏閮ㄨ妭鐐圭殑椤电爜
	
	$.ajax({
		type:"post",
		url:"servlet/BusinessOfNum",
		
		success:function(data){
			//alert("pagenum=");
			allitemnumber = data.businessCompNum;
			pagenum = Math.ceil(allitemnumber /itemnumber);
			$(".pagination").empty();
			$(".pagination").addClass("allnoderesult");
			$(".allnoderesult").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-left'></i></a></li>");
			for(var i = 1; i<=pagenum;i++){
				if(i == 1){
					$(".allnoderesult").append("<li class='changenum active'><a class=" + i + ">" + i + "</a></li>");
				}else{
					$(".allnoderesult").append("<li class='changenum'><a class=" + i + ">" + i + "</a></li>");
				}
				
			}
			$(".allnoderesult").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-right'></i></a></li>");
			//鍏ㄩ儴鑺傜偣锛氱偣鍑荤炕椤典箣鍚�
			$(".allnoderesult .changenum").click(function(){
				//alert("sdf");
				$(".allnoderesult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"servlet/BusinessList",
					data:{startNum:startnum,pageNum:itemnumber},
					success:function(data){
						NodeList(data);
					}
				});
			});

			//鍏ㄩ儴鑺傜偣锛氱偣鍑讳笂涓�〉
			$(".allnoderesult .fa-chevron-left").parent().click(function(){

				var currentpage = $(".allnoderesult .active").find("a").html();
				if(currentpage > 1){
					currentpage = Number(currentpage)-1;
					$(".allnoderesult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");
					
					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"servlet/BusinessList",
						data:{startNum:startnum,pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
				
			});
			//鍏ㄩ儴鑺傜偣锛氱偣鍑讳笅涓�〉
			$(".allnoderesult .fa-chevron-right").parent().click(function(){
				var currentpage = $(".allnoderesult .active a").html();
				//alert(pagenum);
				if(currentpage < pagenum){
					currentpage = Number(currentpage)+1;
					$(".allnoderesult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");

					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"servlet/BusinessList",
						data:{startNum:startnum,pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
			});
		}
	});
	
	//鍔犺浇绗竴椤垫暟鎹�
	$.ajax({
		type:"post",
		url:"servlet/BusinessList",
		data:{startNum:0,pageNum:itemnumber},
		success:function(data){
			NodeList(data);
		}
	});

	/*******************鏉′欢鎼滅储涔嬪悗鐨勮妭鐐规樉绀�**************************/
	//鎼滅储鑺傜偣锛�鐐瑰嚮鎼滅储鍥炬爣涔嬪悗
	$("#searchservice").click(function(){
		var searchtype = $("#search-type").val();
		var searchkey = $("#searchvalue").val();
		
		$(".pagination").empty();
		$(".pagination").removeClass("allnoderesult");
		$(".pagination").addClass("searchresult");
		//1 鍏堣姹傝妭鐐逛釜鏁扮敓鎴愰〉鐮�
		$.ajax({
			type:"post",
			url:"servlet/BusinessSearchNum",
			data:{style:searchtype,value:searchkey},
			success:function(data){
				//alert(data);
				allitemnumber = data.businessSearchNum;
				pagenum = Math.ceil(allitemnumber / itemnumber);
				//alert(pagenum);
				$(".searchresult").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-left'></i></a></li>");				
				for(var i = 1; i<=pagenum;i++){
					if(i == 1){
						$(".searchresult").append("<li class='changenum active'><a class=" + i + ">" + i + "</a></li>");
					}else{
						$(".searchresult").append("<li class='changenum'><a class=" + i + ">" + i + "</a></li>");
					}
					
				}
				$(".searchresult").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-right'></i></a></li>");
				//鎼滅储鑺傜偣锛氱偣鍑荤炕椤典箣鍚�
				$(".searchresult .changenum").click(function(){
					$(".searchresult li").removeClass("active");
					var currentpage = $(this).find("a").html();
					$(this).addClass("active");

					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					var searchtype = $("#search-type").val();
					var searchkey = $("#searchvalue").val();
					$.ajax({
						type:"post",
						url:"servlet/BusinessSearch",
						data:{style:searchtype,
							  value:searchkey,
							  startNum:startnum,
							  pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				});

				//鎼滅储鑺傜偣锛氱偣鍑讳笂涓�〉
				$(".searchresult .fa-chevron-left").parent().click(function(){
					var currentpage = $(".searchresult .active").find("a").html();
					var searchtype = $("#search-type").val();
					var searchkey = $("#searchvalue").val();
					if(currentpage > 1){
						currentpage = Number(currentpage)-1;
						$(".searchresult .active").removeClass('active');
						var currentpage_s = "." + currentpage;
						$(currentpage_s).parent().addClass("active");
						
						var itemnumber = $("#itemnumber").val();	
						var startnum = itemnumber*(currentpage-1);
						$.ajax({
							type:"post",
							url:"servlet/BusinessSearch",
							data:{style:searchtype,
							      value:searchkey,
							      startNum:startnum,
							      pageNum:itemnumber},
							success:function(data){
								NodeList(data);
							}
						});
					}
					
				});
				//鎼滅储鑺傜偣锛氱偣鍑讳笅涓�〉
				$(".searchresult .fa-chevron-right").parent().click(function(){
					var currentpage = $(".searchresult .active").find("a").html();
					var searchtype = $("#search-type").val();
					var searchkey = $("#searchvalue").val();
					if(currentpage < pagenum){
						currentpage = Number(currentpage)+1;
						$(".searchresult .active").removeClass('active');
						var currentpage_s = "." + currentpage;
						$(currentpage_s).parent().addClass("active");

						var itemnumber = $("#itemnumber").val();	
						var startnum = itemnumber*(currentpage-1);
						$.ajax({
							type:"post",
							url:"servlet/BusinessSearch",
							data:{style:searchtype,
							      value:searchkey,
							      startNum:startnum,
							      pageNum:itemnumber},
							success:function(data){
								NodeList(data);
							}
						});
					}
				});
			}
		});
		//鐢熸垚绗﹀悎鏉′欢鐨勭涓�〉鏁版嵁
		$.ajax({
			type:"post",
			url: "servlet/BusinessSearch",
			data:{style:searchtype,
				  value:searchkey,
				  startNum:0,
				  pageNum:itemnumber},
			success: function(data){
				
				NodeList(data);
			}
		});
		
	});


	 /***************鍏叡閮ㄥ垎*********************/


	//褰撴瘡椤垫樉绀轰釜鏁板彉鍖栦箣鍚�
	$("#itemnumber").change(function(){
		itemnumber = $("#itemnumber").val();
		//閲嶆柊鐢熸垚椤电爜

		var pagenum = Math.ceil(allitemnumber/itemnumber);
		$(".pagination").empty();
		$(".pagination").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-left'></i></a></li>");
		for(var i = 1; i<=pagenum;i++){
			if(i == 1){
				$(".pagination").append("<li class='changenum active'><a class=" + i + ">" + i + "</a></li>");
			}else{
				$(".pagination").append("<li class='changenum'><a class=" + i + ">" + i + "</a></li>");
			}
		}
		$(".pagination").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-right'></i></a></li>");
		
		//鍒ゆ柇鎼�pagination鍖呭惈鐨勬槸allserviceresult杩樻槸searchresult锛屾槸searchresult鍒欏悜filterserviceservlet璇锋眰鏁版嵁锛屽惁鍒欏悜servicelistpageup璇锋眰鏁版嵁
		var searchtype = $("#search-type").val();
		var searchkey = $("#searchvalue").val();
		//璇锋眰鍏ㄩ儴鏁版嵁
		if($(".pagination").hasClass("allnoderesult")){
			//鍏ㄩ儴鑺傜偣锛氱偣鍑荤炕椤典箣鍚�
			$(".allnoderesult .changenum").click(function(){
				//alert("sdf");
				$(".allnoderesult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"servlet/BusinessList",
					data:{startNum:startnum,pageNum:itemnumber},
					success:function(data){
						
						NodeList(data);
					}
				});
			});

			//鍏ㄩ儴鏈嶅姟锛氱偣鍑讳笂涓�〉
			$(".allnoderesult .fa-chevron-left").parent().click(function(){

				var currentpage = $(".allnoderesult .active").find("a").html();
				if(currentpage > 1){
					currentpage = Number(currentpage)-1;
					$(".allnoderesult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");
					
					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"servlet/BusinessList",
						data:{startNum:startnum,pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
				
			});
			//鍏ㄩ儴鏈嶅姟锛氱偣鍑讳笅涓�〉
			$(".allnoderesult .fa-chevron-right").parent().click(function(){
				var currentpage = $(".allnoderesult .active a").html();
				//alert(pagenum);
				if(currentpage < pagenum){
					currentpage = Number(currentpage)+1;
					$(".allnoderesult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");

					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"servlet/BusinessList",
						data:{startNum:startnum,pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
			});
			$.ajax({
				type:"post",
				url:"servlet/BusinessList",
				data:{startNum:0,pageNum:itemnumber},
				success:function(data){
					//alert(data);
					NodeList(data);
				}
			});
		}else{
			//璇锋眰鎼滅储鐨勬暟鎹�
			//鎼滅储鑺傜偣锛氱偣鍑荤炕椤典箣鍚�
			$(".searchresult .changenum").click(function(){
				$(".searchresult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				var searchtype = $("#search-type").val();
				var searchkey = $("#searchvalue").val();
				$.ajax({
					type:"post",
					url:"servlet/BusinessSearch",
					data:{style:searchtype,
			 		      value:searchkey,
			 		      startNum:startnum,
			 		      pageNum:itemnumber},
					success:function(data){
						NodeList(data);
					}
				});
			});

			//鎼滅储鑺傜偣锛氱偣鍑讳笂涓�〉
			$(".searchresult .fa-chevron-left").parent().click(function(){
				var currentpage = $(".searchresult .active").find("a").html();
				var searchtype = $("#search-type").val();
				var searchkey = $("#searchvalue").val();
				if(currentpage > 1){
					currentpage = Number(currentpage)-1;
					$(".searchresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");
					
					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"servlet/BusinessSearch",
						data:{style:searchtype,
			 		      	  value:searchkey,
			 		      	  startNum:startnum,
			 		      	  pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
				
			});
			//鎼滅储鏈嶅姟锛氱偣鍑讳笅涓�〉
			$(".searchresult .fa-chevron-right").parent().click(function(){
				var currentpage = $(".searchresult .active").find("a").html();
				var searchtype = $("#search-type").val();
				var searchkey = $("#searchvalue").val();
				if(currentpage < pagenum){
					currentpage = Number(currentpage)+1;
					$(".searchresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");

					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"servlet/BusinessSearch",
						data:{style:searchtype,
			 		      	  value:searchkey,
			 		      	  startNum:startnum,
			 		      	  pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
			});
			$.ajax({
				type:"post",
				url: "servlet/BusinessSearch",
				data:{style:searchtype,
	 		      	  value:searchkey,
	 		      	  startNum:startnum,
	 		      	  pageNum:itemnumber},
				success: function(data){
					NodeList(data);
				}
			});
		}
		
	});
	function NodeList(data){
		//data=JSON.parse(data);
		$("#Comptable").empty();
		for(var i=0;i<data.length;i++)
		{
			var txtHtml="";

			txtHtml += "<tr id='"+data[i].ServiceID+"'><td class='compname'>"+data[i].CompName+"</td>";
			txtHtml += "<td >"+data[i].ServiceName+"</td>";
			txtHtml += "<td >"+data[i].SubServiceName+"</td>";
			txtHtml += "<td >"+data[i].InterfaceName+"</td>";
			txtHtml += "<td>"+data[i].System+"</td>";
			txtHtml += "<td class='compdes'>"+data[i].Description+"</td>";
			if(data[i].Status == "true")
			{
				txtHtml += "<td><form class='smart-form' style='width:30px;height:30px;'><label class='toggle'><input type='checkbox' name='checkbox-toggle' checked='checked'><i data-swchon-text='开' data-swchoff-text='关' class='compstatus'></i></label></form></td>";
			}else
			{
				txtHtml += "<td><form class='smart-form' style='width:30px;height:30px;'><label class='toggle'><input type='checkbox' name='checkbox-toggle'><i data-swchon-text='开' data-swchoff-text='关' class='compstatus'></i></label></form></td>";
			}
			txtHtml += "<td><a class='btn btn-primary editcomp btn-sm' ><i class='fa fa-gear'></i>&nbsp;&nbsp;修改</a></td>";
			txtHtml += "<td><a class='btn btn-danger deletecomp btn-sm' ><i class='fa fa-times-circle'></i>&nbsp;&nbsp;卸载</a></td></tr>";
			
			$("#Comptable").append(txtHtml);
			
		}
		//点击组件的修改按钮
		$(".editcomp").click(function(){
			$(".page1").dialog("open");
			var compid = $(this).parent().parent().attr("id");
			var compname = $(this).parent().parent().find(".compname").html();
			var compdes = $(this).parent().parent().find(".compdes").html();
			
			$("#compname").val(compname);
			$("#compdes").val(compdes);
			$("#editSubmit").parent().attr("title",compid);
		});
		
		//提交修改
		$("#editSubmit").click(function(){
			
			var compid = $("#editSubmit").parent().attr("title");
			var compname = $("#compname").val();
			var compdes = $("#compdes").val();
			if(compname == "" || compdes==""){
				alert("请填入完整的信息！");
			}else{

				$.ajax({
					type:"post",
					url:"servlet/BusinessEdit",
					data:{ServiceID:compid,CompName:compname,Description:compdes},
					success:function(data){	
						if(data=="success"){
							alert("组件修改成功！");
							location.reload();
						}else{
							alert("组件修改失败！");
						}
					}
				});
			}		
		});
		
		//卸载一个组件
		$(".deletecomp").click(function(){
			var compid = $(this).parent().parent().attr("id");
			alert(compid);
			$.ajax({
				type:"post",
				url:"servlet/BusinessDestroy",
				data:{ServcieID:compid},
				success:function(data){
					
					if(data=="success"){
						alert("卸载组件成功！");
					}else{
						alert("卸载失败！");
					}
					location.reload();
				}
			});
		});
		
		//组件的开关
		$(".compstatus").click(function(){
			var aa = $(this).prev().attr("checked");
			if(aa=="checked"){
				status = "false";
			}else{
				status = "true";
			}
			var compid = $(this).parent().parent().parent().parent().attr("id");
			$.ajax({
				type:"post",
				url:"servlet/BusinessStatusChange",
				data:{ServiceID:compid,Status:status},
				success:function(data){
					if(data=="success"){
						location.reload();
					}
				}
			});
		});
	}
	
});