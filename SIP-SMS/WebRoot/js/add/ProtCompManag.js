	var attributenum=0;
function readjsondata(){
		var jsondata = "[";
		var valuestring = "";
		var attributename = "";
		var attributetype = "";
		var optionvalue = "";
		for(var i=0; i<attributenum;i++){
			valuestring = ".value"+i;

			attributename = $(valuestring).find(".attributename").val();
			attributetype = $(valuestring).find(".attributeType").val();
			
			if(i==attributenum-1){
				if(attributetype=="select"){
					optionvalue = $(valuestring).find(".optionvalue").val();
					jsondata += "{'name':'" + attributename + "','type':'" + attributetype + "','option':'" + optionvalue + "'}";
				}else{
					jsondata += "{'name':'" + attributename + "','type':'" + attributetype +"'}";
				}
			}else{
				if(attributetype=="select"){
					optionvalue = $(valuestring).find(".optionvalue").val();
					jsondata += "{'name':'" + attributename + "','type':'" + attributetype + "','option':'" + optionvalue + "'},";
				}else{
					jsondata += "{'name':'" + attributename + "','type':'" + attributetype +"'},";
				}
			}
						
			
		}
		jsondata += "]";
		return jsondata;
	}
function fileupload(){
	var jsondata = readjsondata();
	//1.上传文件
	var filepath = $("#filepath").val();
	if($("#filepath").val()==""){
		//alert($("#filepath").val());
		//alert("上传文件不能为空!");
		$('#warnTag').html("上传文件不能为空!");
		return false;
	}
$.ajaxFileUpload({		
			url:"servlet/FilesUpload",
			secureuri:false,
			fileElementId:'filepath',	
			success: function (data) {
				//2.传递其他参数
				var newCompName = $("#newCompName").val();
			
				var newCompDes = $("#newCompDes").val();
				
				if(newCompName==""||newCompDes==""){
					
				}else{
					$.ajax({
						type:"post",
						url:"servlet/ProtocolAdd",
						data:{FileName:filepath,CompName:newCompName,Description:newCompDes,property:jsondata},
						success:function(data){
							alert(data);
							if(data=="success"){
								alert("添加组件成功！");
								location.reload();
							}else{
								alert("添加组件失败！");
							}
							
						}
					});
				}
			},
			error: function (data, status, e){
				alert("error:"+data);
			}
	});	
				
}

$(document).ready(function() {		
	pageSetUp();

	//获取用户输入的属性类型
	
	
	$("#newAttribute").click(function(){
		var valuestring = ".value"+attributenum;
		
		var appendinfo = "<div class='form-group value"+attributenum+"'><label class='control-label col-md-2'>新属性1：</label>";
		appendinfo += "<div class='col-md-3'><input type='text' placeholder='属性名' class='attributename'/></div>";
		appendinfo += "<div class='col-md-2'><select class='input-sm attributeType'><option value=''>属性类型</option><option value='input'>input</option><option value='select'>select</option></select>";
		$(this).parent().parent().parent().find(".attributecontent").append(appendinfo);
		
		$(valuestring).find(".attributeType").change(function(){
			
			var type = $(valuestring).find(".attributeType").val();
			if(type=="select"){
				alert("select");
				$(valuestring).find(".col-md-5").remove();
				$(valuestring).append("<div class='col-md-5'><input type='text' placeholder='选项值请用，分隔' class='optionvalue'/></div>");
			}else{
				$(valuestring).find(".col-md-5").remove();
			}
		});
		attributenum++;
	});


	//选择一个搜索类型			
	$("#search-type").change(function(){
		var searchtype = $("#search-type").val();
		//alert(searchtype);
		if(searchtype == "protocolStatus"){
			$(".filtertype").empty();
			$(".filtertype").append("<select class='input-sm' id='searchvalue' style='font-size:14px;display: inline-block;width: 9.5%;'><option value='True'>开</option><option value='False'>关</option></select>");
			
		}else{
			$(".filtertype").empty();
			$(".filtertype").append("<input type='text' class='input-sm' id='searchvalue'/>");
		}
	});		
	
//修改弹出框
	$(".page1").dialog({
		autoOpen : false,
		modal : true,
		title : "修改"
	});	

	$(".page2").dialog({
		autoOpen : false,
		modal : true,
		title : "添加协议组件"
	});	
	//点击添加组件
	$("#AddComp").click(function(){
		$(".page2").dialog("open");
	});
	
	

	
	
	/*
	 * 实现数据分页部分-wenyanqi
	 */
	//三个全局变量
	//获取当前一页显示多少条记录
	var itemnumber = $("#itemnumber").val();
	//一共有多少页
	var pagenum;
	//一共有多少条记录
	var allitemnumber;
	
	//全部节点：显示全部节点的页码
	$.ajax({
		type:"post",
		url:"servlet/ProtocolOfNum",
		data:{name:"hello"},
		success:function(data){
			allitemnumber = data.num;
			//alert("pagenum="+data.protocolNum);
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
			//全部节点：点击翻页之后
			$(".allnoderesult .changenum").click(function(){
				//alert("sdf");
				$(".allnoderesult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"servlet/ProtocolList",
					data:{startNum:startnum,pageNum:itemnumber},
					success:function(data){
						NodeList(data);
					}
				});
			});

			//全部节点：点击上一页
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
						url:"servlet/ProtocolList",
						data:{startNum:startnum,pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
				
			});
			//全部节点：点击下一页
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
						url:"servlet/ProtocolList",
						data:{startNum:startnum,pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
			});
			
		}
	});
	
	
	//加载第一页数据
	$.ajax({
		type:"post",
		url:"servlet/ProtocolList",
		data:{startNum:0,pageNum:itemnumber},
		success:function(data){
			//alert("data:"+data);
			NodeList(data);
		}
	});


	/*******************条件搜索之后的节点显示***************************/
	//搜索节点： 点击搜索图标之后
	$("#searchservice").click(function(){
		var searchtype = $("#search-type").val();
		var searchkey = $("#searchvalue").val();
		
		$(".pagination").empty();
		$(".pagination").removeClass("allnoderesult");
		$(".pagination").addClass("searchresult");
		//1 先请求节点个数生成页码
		$.ajax({
			type:"post",
			url:"servlet/ProtocolSearchNum",
			data:{style:searchtype,value:searchkey},
			success:function(data){
				alert(data.protocolSearchNum);
				allitemnumber = data.protocolSearchNum;
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
				//搜索节点：点击翻页之后
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
						url:"servlet/ProtocolSearch",
						data:{style:searchtype,
							  value:searchkey,
							  startNum:startnum,
							  pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				});

				//搜索节点：点击上一页
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
							url:"servlet/ProtocolSearch",
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
				//搜索节点：点击下一页
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
							url:"servlet/ProtocolSearch",
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
		//生成符合条件的第一页数据
		$.ajax({
			type:"post",
			url: "servlet/ProtocolSearch",
			data:{style:searchtype,
				  value:searchkey,
				  startNum:0,
				  pageNum:itemnumber},
			success: function(data){
				
				NodeList(data);
			}
		});
		
	});


	 /***************公共部分*********************/


	//当每页显示个数变化之后
	$("#itemnumber").change(function(){
		itemnumber = $("#itemnumber").val();
		//重新生成页码

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
		
		//判断搜.pagination包含的是allserviceresult还是searchresult，是searchresult则向filterserviceservlet请求数据，否则向servicelistpageup请求数据
		var searchtype = $("#search-type").val();
		var searchkey = $("#searchvalue").val();
		//请求全部数据
		if($(".pagination").hasClass("allnoderesult")){
			//全部节点：点击翻页之后
			$(".allnoderesult .changenum").click(function(){
				//alert("sdf");
				$(".allnoderesult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"servlet/ProtocolList",
					data:{startNum:startnum,pageNum:itemnumber},
					success:function(data){
						
						NodeList(data);
					}
				});
			});

			//全部服务：点击上一页
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
						url:"servlet/ProtocolList",
						data:{startNum:startnum,pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
				
			});
			//全部服务：点击下一页
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
						url:"servlet/ProtocolList",
						data:{startNum:startnum,pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
			});
			$.ajax({
				type:"post",
				url:"servlet/ProtocolList",
				data:{startNum:0,pageNum:itemnumber},
				success:function(data){
					//alert(data);
					NodeList(data);
				}
			});
		}else{
			//请求搜索的数据
			//搜索节点：点击翻页之后
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
					url:"servlet/ProtocolSearch",
					data:{style:searchtype,
			 		      value:searchkey,
			 		      startNum:startnum,
			 		      pageNum:itemnumber},
					success:function(data){
						NodeList(data);
					}
				});
			});

			//搜索节点：点击上一页
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
						url:"servlet/ProtocolSearch",
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
			//搜索服务：点击下一页
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
						url:"servlet/ProtocolSearch",
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
				url: "servlet/ProtocolSearch",
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
		
		$("#Comptable").empty();
	
		for(var i=0;i<data.length;i++)
		{
			var txtHtml="";
			txtHtml += "<tr id='"+data[i].ProtocolID+"'><td class='compname'>"+data[i].ProtocolName+"</td>";
			txtHtml += "<td class='compdes'>"+data[i].ProtocolDescription+"</td>";
			if(data[i].ProtocolStatus == "true")
			{
				txtHtml += "<td><form class='smart-form' style='width:30px;height:30px;padding-left:20px'><label class='toggle'><input type='checkbox' name='checkbox-toggle' checked='checked'><i data-swchon-text='开' data-swchoff-text='关' class='compstatus'></i></label></form></td>";
			}else
			{
				txtHtml += "<td><form class='smart-form' style='width:30px;height:30px;padding-left:20px'><label class='toggle'><input type='checkbox' name='checkbox-toggle'><i data-swchon-text='开' data-swchoff-text='关' class='compstatus'></i></label></form></td>";
			}
			txtHtml += "<td><a class='btn btn-primary editcomp btn-sm' ><i class='fa fa-gear'></i>&nbsp;&nbsp;修改</a></td>";
			txtHtml += "<td><a class='btn btn-danger deletecomp btn-sm' ><i class='fa fa-times-circle'></i>&nbsp;&nbsp;卸载</a></td></tr>";
			
			// <tr><td>组件的名字</td>
			// 	<td>这个组件是用来实现。。。。</td>
			// 	<td><form class='smart-form' style='height:30px;width:30px;padding-left:20px'><label class='toggle'><input type='checkbox' name='checkbox-toggle'><i data-swchon-text='开' data-swchoff-text='关'></i></label></form></td>
			// 	<td><a class='btn btn-primary deleteservice btn-sm' ><i class='fa fa-gear'></i>&nbsp;&nbsp;修改</a></td>
			// 	<td><a class='btn btn-danger deleteservice btn-sm' ><i class='fa fa-times-circle'></i>&nbsp;&nbsp;卸载</a></td></tr>
			
			$("#Comptable").append(txtHtml);
		}
		
		//点击组件的修改按钮
		$(".editcomp").click(function(){
			$(".page1").dialog("open");
			var compid = $(this).parent().parent().attr("id");
			var compname = $(this).parent().parent().find(".compname").html();
			var compdes = $(this).parent().parent().find(".compdes").html();
			
			$("#compName").val(compname);
			$("#compDes").val(compdes);
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
					url:"servlet/ProtocolEdit",
					data:{ProtocolID:compid,ProtocolName:compname,ProtocolDescription:compdes},
					success:function(data){
				
						if(data=="success"){
							alert("组件修改成功！");
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
				url:"servlet/ProtocolDestroy",
				data:{ProtocolID:compid},
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
				url:"servlet/ProtocolStatusChange",
				data:{ProtocolID:compid,ProtocolStatus:status},
				success:function(data){
					alert(data);
				}
			});
		});
	}
	

	
	
});