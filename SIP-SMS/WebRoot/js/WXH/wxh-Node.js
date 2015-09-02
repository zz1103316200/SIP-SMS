$(document).ready(function() {

	pageSetUp();
	//右上角小柱状图
	$.ajax({
		type:"post",
		url:"nodedetail",
		success:function(data){
			//alert(data);
			var datainfo = data.split("@");
			
			$("#servicenum").append("<h5 style='font-family:Microsoft YaHei'> 服务总数 <span class='txt-color-blue'>" + datainfo[0] + "</span></h5>")
							.append("<div class='sparkline txt-color-blue hidden-mobile hidden-md hidden-sm'>" + datainfo[1] + "</div>");
			$("#startingnum").append("<h5 style='font-family:Microsoft YaHei'> 正在运行 <span class='txt-color-blue'>" + datainfo[2] + "</span></h5>")
							.append("<div class='sparkline txt-color-blue hidden-mobile hidden-md hidden-sm'>" + datainfo[3] + "</div>");
			$("#disconnectnum").append("<h5 style='font-family:Microsoft YaHei'> 已断开 <span class='txt-color-blue'>" + datainfo[4] + "</span></h5>")
							.append("<div class='sparkline txt-color-blue hidden-mobile hidden-md hidden-sm'>" + datainfo[5] + "</div>");
			pageSetUp();
		}
	});
		
	//选择一个搜索类型			
	$("#search-type").change(function(){
		var searchtype = $("#search-type").val();
		//alert(searchtype);
		if(searchtype == "HostIp" || searchtype == "MAC" || searchtype == "Type"){
			$(".filtertype").empty();
			$(".filtertype").append("<select class='input-sm' id='searchvalue' style='font-size:14px;display: inline-block;width: 9.5%;'></select>");
			
			
		}else{
			$(".filtertype").empty();
			$(".filtertype").append("<input type='text' class='input-sm' id='searchvalue'/>");

		}
		if(searchtype != ""){
			$.ajax({
				type: "post",
				url: "nodefilterfirst",
				data:{value:searchtype},
				success:function(data){
					var infoarray = new Array();
					infoarray = data.split("@");
					for(var i=0;i<infoarray.length;i++){
					
						$("#searchvalue").append("<option value=" + infoarray[i] +">"+ infoarray[i] + "</option>");
					}
				}
			});
		}
	});		
					
	//增加节点
	//添加节点弹出框
	//修改弹出框
	$(".page1").dialog({
		autoOpen : false,
		modal : true,
		/*title : */
	});
	$(".page2").dialog({
		autoOpen : false,
		modal : true,
		/*title : */
	});
	
	$("#AddNode").click(function(){
		$(".page2").dialog("open");

	});
					
					
	//增加节点确定按钮
	$( "#AddButton" ).click(function(){
		var str = $("#AddInnerIP").val()+"@"+$("#AddInnerNodeName").val()+"@"+$("#AddInnerDescribeInfo").val()+"@"+$("#AddInnerContainerName").val()+"@"+$("#AddInnerContainerNum").val()+"@"+$("#AddInnerMAC").val()+"@"+$("#AddInnerNodeLocal").val()+"@"+$("#AddInnerOS").val()+"@"+$("#AddInnerOsBit").val()+"@"+$("#AddInnerNodeType").val();
		//alert(str);

		var txtHtml="";															
		$.post("nodeaddservlet",{
			nodestring : str
		},function(data){
			//alert("a");
			if(data=="true"){
				/*//第1行 状态
				txtHtml += "<tr><td style='text-align:center;'><i class='fa fa-circle txt-color-red'></i><label>&nbsp;&nbsp;disconnect</label></td>";
																
				txtHtml += "<td class='name'>"+ $("#AddInnerNodeName").val() +"</td>";   //第2行 节点名称
				txtHtml += "<td class='describe'>"+ $("#AddInnerDescribeInfo").val() +"</td>";   //第3行 描述信息
				txtHtml += "<td class='ip'>"+ $("#AddInnerIP").val() +"</td>";   //第4行 IP地址
				txtHtml += "<td class='mac'>"+ $("#AddInnerMAC").val() +"</td>";   //第5行 MAC地址
				txtHtml += "<td class='os'>"+ $("#AddInnerOS").val()+"-"+$("#AddInnerOsBit").val() +"</td>";   //第6行 操作系统
				txtHtml += "<td class='local'>"+ $("#AddInnerNodeLocal").val() +"</td>";   //第7行 节点位置
				txtHtml += "<td class='type'>"+ $("#AddInnerNodeType").val() +"</td>";   //第8行 节点类型
				txtHtml += "<td><a class='btn btn-primary btn-sm' href='javascript:void(0);' name='modify'>修改</a><a class='btn btn-danger btn-sm' href='javascript:void(0);' name='delete'>删除</a></td>";   //第9行 操作按钮
							
							
				$("#NodeTable").append(txtHtml);
				$(".page2").dialog("close");*/
				alert("节点添加成功！");
				location.reload();
			}else{
				/*$(".page2").dialog("close");*/
				alert("节点添加失败！");
			}
		});
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
		url:"getnumber",
		data:{type:"node"},
		success:function(data){
			//alert("pagenum="+data);
			allitemnumber = data;
			pagenum = Math.ceil(data /itemnumber);
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
					url:"nodelistservlet",
					data:{startnumber:startnum,itemnumber:itemnumber},
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
						url:"nodelistservlet",
						data:{startnumber:startnum,itemnumber:itemnumber},
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
						url:"nodelistservlet",
						data:{startnumber:startnum,itemnumber:itemnumber},
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
		url:"nodelistservlet",
		data:{startnumber:0,itemnumber:itemnumber},
		success:function(data){
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
			url:"getfilternumber",
			data:{filtertype:"node",type:searchtype,value:searchkey},
			success:function(data){
				//alert(data);
				allitemnumber = data;
				pagenum = Math.ceil(data / itemnumber);
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
						url:"nodefilter",
						data:{type:searchtype,value:searchkey,startnumber:startnum,itemnumber:itemnumber},
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
							url:"nodefilter",
							data:{type:searchtype,value:searchkey,startnumber:startnum,itemnumber:itemnumber},
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
							url:"nodefilter",
							data:{type:searchtype,value:searchkey,startnumber:startnum,itemnumber:itemnumber},
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
			url: "nodefilter",
			data:{type:searchtype,value:searchkey,startnumber:0,itemnumber:itemnumber},
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
					url:"nodelistservlet",
					data:{startnumber:startnum,itemnumber:itemnumber},
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
						url:"nodelistservlet",
						data:{startnumber:startnum,itemnumber:itemnumber},
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
						url:"nodelistservlet",
						data:{startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
			});
			$.ajax({
				type:"post",
				url:"nodelistservlet",
				data:{startnumber:0,itemnumber:itemnumber},
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
					url:"nodefilter",
					data:{type:searchtype,value:searchkey,startnumber:startnum,itemnumber:itemnumber},
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
						url:"nodefilter",
						data:{type:searchtype,value:searchkey,startnumber:startnum,itemnumber:itemnumber},
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
						url:"nodefilter",
						data:{type:searchtype,value:searchkey,startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
			});
			$.ajax({
				type:"post",
				url: "nodefilter",
				data:{type:searchtype,value:searchkey,startnumber:0,itemnumber:itemnumber},
				success: function(data){
					NodeList(data);
				}
			});
		}
		
	});
	function NodeList(data){
		data=JSON.parse(data);
		$("#NodeTable").empty();
		for(var i=0;i<data.nodelist.length;i++)
		{
			var txtHtml="";
			
			//第1行 状态
			if(data.nodelist[i].status == "connect")
			{
				txtHtml += "<tr><td style='text-align:center;' class='nodestatus'><i class='fa fa-circle txt-color-green'></i><label>&nbsp;&nbsp;运行</label></td>";
			}
			else
			{
				txtHtml += "<tr><td style='text-align:center;' class='nodestatus'><i class='fa fa-circle txt-color-red'></i><label>&nbsp;&nbsp;停止</label></td>";
			}
			txtHtml += "<td class='name'>"+ data.nodelist[i].hostname +"</td>";   //第2行 节点名称
			txtHtml += "<td class='describe'>"+ data.nodelist[i].description +"</td>";   //第3行 描述信息
			txtHtml += "<td class='ip'>"+ data.nodelist[i].hostip +"</td>";   //第4行 IP地址
			txtHtml += "<td class='mac'>"+ data.nodelist[i].mac +"</td>";   //第5行 MAC地址
			txtHtml += "<td class='os'>"+ data.nodelist[i].os +"</td>";   //第6行 操作系统
			txtHtml += "<td class='local'>"+ data.nodelist[i].location +"</td>";   //第7行 节点位置
			txtHtml += "<td class='type'>"+ data.nodelist[i].type +"</td>";   //第8行 节点类型
			if(data.nodelist[i].status == "connect")
			{
				txtHtml += "<td><form class='smart-form' style='width:78px;height:30px;'><label class='toggle '><input type='checkbox' name='checkbox-toggle' checked='checked'><i data-swchon-text='开' data-swchoff-text='关' class='startstop'></i>	</label></form></td>";
			}
			else
			{
				txtHtml += "<td><form class='smart-form' style='width:78px;height:30px;'><label class='toggle'><input type='checkbox' name='checkbox-toggle' ><i data-swchon-text='开' data-swchoff-text='关' class='startstop'></i>	</label></form></td>";
			}
			txtHtml += "<td><a class='btn btn-primary btn-sm' href='javascript:void(0);' name='modify'>修改</a><a class='btn btn-danger btn-sm' href='javascript:void(0);'name='delete'>删除</a></td></tr>";   //第9行 操作按钮
			
			/*<tr><td style='text-align:center;'><i class='fa fa-circle txt-color-green'></i><label>&nbsp;&nbsp;
			连接</label></td>
			<td>123</td>
			<td>Jennifer</td>
			<td>1-342-463-8341</td>
			<td>Et Rutrum Non Associates</td>
			<td>35728</td>
			<td>Fogo</td>
			<td>Huntly</td>
			<td><a class='btn btn-primary btn-sm' href='javascript:void(0);' name='modify'>修改</a><a class='btn btn-danger btn-sm' href='javascript:void(0);'>删除</a></td>																							</tr>
		    */
			
			$("#NodeTable").append(txtHtml);
			
		}
		
		
		//修改按钮
		$("a[name='modify']").click(function(){
			$(".page1").dialog("open");
			$("#innerNodeType").attr("value",$(this).parent().prev().prev().text());
			$("#innerNodeLocal").attr("value",$(this).parent().prev().prev().prev().text());
			
			//拆分操作系统和位数
			var str=$(this).parent().prev().prev().prev().prev().text();
			os = new Array;
			os = str.split("-");
			
			$("#innerOsBit").attr("value",os[1]);
			$("#innerOS").attr("value",os[0]);
			$("#innerMAC").attr("value",$(this).parent().prev().prev().prev().prev().prev().text());
			$("#innerIPAddress").attr("value",$(this).parent().prev().prev().prev().prev().prev().prev().text());
			$("#innerDescribeInfo").attr("value",$(this).parent().prev().prev().prev().prev().prev().prev().prev().text());
			$("#innerNodeName").attr("value",$(this).parent().prev().prev().prev().prev().prev().prev().prev().prev().text());
			
			$(this).parent().parent().addClass($("#innerMAC").attr("value"));
		});



        //修改确定按钮
		$( "#ModifyButton" ).click(function(){
			//alert($(this).attr("name"));
			var mac = $("#innerMAC").val();
			var mac1 = "."+mac;
			var str1 = $("#innerNodeName").val()+"@"+$("#innerDescribeInfo").val()+"@"+$("#innerIPAddress").val()+"@"+$("#innerOS").val()+"@"+$("#innerOsBit").val()+"@"+$("#innerNodeLocal").val()+"@"+$("#innerNodeType").val()+"@"+mac;
			//alert(str1);
			$.post("nodechange",{
				nodestring : str1
			},function(data){
				if(data=="true"){
				var $Chtr = $(mac1);
				$Chtr.find('td.name').text($("#innerNodeName").val());	
				$Chtr.find('td.describe').text($("#innerDescribeInfo").val());
				$Chtr.find('td.ip').text($("#innerIPAddress").val());
				$Chtr.find('td.os').text($("#innerOS").val()+"-"+$("#innerOsBit").val());
				$Chtr.find('td.local').text($("#innerNodeLocal").val());
				$Chtr.find('td.type').text($("#innerNodeType").val());
				$(".page1").dialog("close");
				}else{
					$(".page1").dialog("close");
				}									
			});
         });
		
		
		
		//删除按钮
 		$("a[name='delete']").click(function(){
			//alert($(this).parent().prev().prev().prev().prev().prev().text());
 			var str = $(this).parent().prev().prev().prev().prev().prev().prev().text();
 			var str1 = "TheDelete"; //mac
 			alert(str);
 			$(this).parent().parent().addClass(str1);
 			var str2 = "."+str1;
 						             						             						             			
 			$.post("nodedelete",{
				nodeip : str
			},function(data){
				alert(data);
				if(data=="true"){
					
					
					alert("删除成功！");
					location.reload();
				}
			});

 		});
 		var nodestatus;
 		//开关
 		$(".startstop").click(function(){
 			var nodeip = $(this).parent().parent().parent().parent().find('td.ip').text();
 			
 			var str = "theone";
 			$(this).parent().parent().parent().parent().addClass(str);
 			var str1 = "."+str;
 			
 			if($(str1).find(".nodestatus i").hasClass("txt-color-green")){
 				nodestatus = "disconnect";
 				alert(nodestatus);
			}else{
				nodestatus = "connect";
				alert(nodestatus);
			}
 			 			
 			$.post("nodestatus",{
 				nodeip : nodeip,
 				status : nodestatus
 			},function(data){
 				if(data=="true"){
 					if(nodestatus == "connect"){
						
						$(str1).find(".nodestatus i").removeClass('txt-color-red');
						//$(serviceid).find(".servicestatus i lable").empty();
						
						$(str1).find(".nodestatus i").addClass('txt-color-green');
						$(str1).find(".nodestatus label").replaceWith("<lable>&nbsp&nbsp运行</lable>");
						
					}else{
						
						$(str1).find(".nodestatus i").removeClass('txt-color-green');

						$(str1).find(".nodestatus i").addClass('txt-color-red');
						$(str1).find(".nodestatus label").replaceWith("<lable>&nbsp&nbsp停止</lable>");
					}
 					$(str1).removeClass('theone');
 				}else{
 					$(str1).removeClass('theone');
 				}            				
 			});
 			
 			
 			
 		});          	
 		
	}	
	
	/*//修改表单IP验证
	var $validator = $("#Modifypage").validate({
    	debug:true,
    
    rules: {
        IPAddress: {
        required: true,
        remote: {
			type: "post",
			url: "nodeexist",
			data: {
				nodeip: function() {
				return $("#innerIPAddress").val();
				}
			},
			dataFilter: function(data) {
				if (data == "true")
				return true;
				else
				return false;
			}
		}				        				        
      },
      
    },
    
    messages: {
    	IPAddress: {
    		    required: "此项不能为空！",
    		    remote: "此IP不可用！"
    		   },
    },
    
    highlight: function (element) {
      $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
    },
    unhighlight: function (element) {
      $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
    },
    errorElement: 'div',
    errorClass: 'ErrorInfo',
  });*/

	
//增加表单IP验证
	$("#Addpage").validate({
    	debug:true,
    
    rules: {
    	IP: {
        required: true,
        remote: {
			type: "post",
			url: "nodeexist",
			data: {
				nodeip: function() {
				return $("#AddInnerIP").val();
				}
			},
			dataFilter: function(data) {
				if (data == "true")
				return false;
				else
				return true;
			}
		}				        				        
      },
      
    },
    
    messages: {
    	IP: {
    		    required: "此项不能为空！",
    		    remote: "此IP不可用！"
    		   },
    },
    
    highlight: function (element) {
      $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
    },
    unhighlight: function (element) {
      $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
    },
    errorElement: 'div',
    errorClass: 'ErrorInfo',
  });

	
});