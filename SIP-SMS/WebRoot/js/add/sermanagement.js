$(document).ready(function(){
	/*
	* wenyanqi 
	* 右上角小柱状图
	*/
	$.ajax({
		type:"post",
		url:"servicedetail",
		success:function(data){
			var datainfo = data.split("@");

			$("#servicenum").append("<h5 style='font-family:Microsoft YaHei'> 服务总数 <span class='txt-color-blue'>" + datainfo[0] + "</span></h5>")
							.append("<div class='sparkline txt-color-blue hidden-mobile hidden-md hidden-sm'>" + datainfo[1] + "</div>");
			$("#startingnum").append("<h5 style='font-family:Microsoft YaHei'> 正在运行 <span class='txt-color-blue'>" + datainfo[2] + "</span></h5>")
							.append("<div class='sparkline txt-color-blue hidden-mobile hidden-md hidden-sm'>" + datainfo[3] + "</div>");
			$("#stoppednum").append("<h5 style='font-family:Microsoft YaHei'> 已停止 <span class='txt-color-blue'>" + datainfo[4] + "</span></h5>")
							.append("<div class='sparkline txt-color-blue hidden-mobile hidden-md hidden-sm'>" + datainfo[5] + "</div>");
			$("#disconnectnum").append("<h5 style='font-family:Microsoft YaHei'> 已断开 <span class='txt-color-blue'>" + datainfo[6] + "</span></h5>")
							.append("<div class='sparkline txt-color-blue hidden-mobile hidden-md hidden-sm'>" + datainfo[7] + "</div>");
		}
	});


	//弹出框的效果
	$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
		_title : function(title) {
			if (!this.options.title) {
				title.html("&#160;");
			} else {
				title.html(this.options.title);
			}
		}
	}));

	$(".page1").dialog({
		autoOpen : false,
		modal : true,
		title : "<div class='widget-header' style='font-family:Microsoft YaHei;width:800px;'><h3><i class='icon-ok'></i>服务拓扑结构</h3></div>"	
	});

	$(".page2").dialog({
		autoOpen : false,
		modal : true,
		title : "<div class='widget-header'><h4><i class='icon-ok'></i>节点详细信息</h4></div>"	
	});
	$(".page3").dialog({
		autoOpen : false,
		modal : true,
		title : "<div class='widget-header'><h3><i class='icon-ok'></i>服务详细信息</h3></div>"	
	});
	$(".page4").dialog({
		autoOpen : false,
		modal : true,
		title : "<div class='widget-header'><h3><i class='icon-ok'></i>服务适配</h3></div>"	
	});


	/*
	 * ajax翻页部分
	 */
	//三个全局变量
	//获取当前一页显示多少条记录
	var itemnumber = $("#itemnumber").val();
	//一共有多少页
	var pagenum;
	//一共有多少条记录
	var allitemnumber;
	
	//全部服务：显示全部服务的页码
	$.ajax({
		type:"post",
		url:"getnumber",
		data:{type:"service"},
		success:function(data){
			//alert("pagenum="+data);
			allitemnumber = data;
			pagenum = Math.ceil(data /itemnumber);
			$(".pagination").empty();
			$(".pagination").addClass("allserviceresult");
			$(".allserviceresult").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-left'></i></a></li>");
			for(var i = 1; i<=pagenum;i++){
				if(i == 1){
					$(".allserviceresult").append("<li class='changenum active'><a class=" + i + ">" + i + "</a></li>");
				}else{
					$(".allserviceresult").append("<li class='changenum'><a class=" + i + ">" + i + "</a></li>");
				}
				
			}
			$(".allserviceresult").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-right'></i></a></li>");
			//全部服务：点击翻页之后
			$(".allserviceresult .changenum").click(function(){
				//alert("sdf");
				$(".allserviceresult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"servicelistpageup",
					data:{startnumber:startnum,itemnumber:itemnumber},
					success:function(data){
						showservicelist(data);
					}
				});
			});

			//全部服务：点击上一页
			$(".allserviceresult .fa-chevron-left").parent().click(function(){

				var currentpage = $(".allserviceresult .active").find("a").html();
				if(currentpage > 1){
					currentpage = Number(currentpage)-1;
					$(".allserviceresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");
					
					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"servicelistpageup",
						data:{startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
				
			});
			//全部服务：点击下一页
			$(".allserviceresult .fa-chevron-right").parent().click(function(){
				var currentpage = $(".allserviceresult .active a").html();
				//alert(pagenum);
				if(currentpage < pagenum){
					currentpage = Number(currentpage)+1;
					$(".allserviceresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");

					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"servicelistpageup",
						data:{startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
			});
		}
	});

	//全部服务：页面刚加载显示的数据
	$.ajax({
		type:"post",
		url:"servicelistpageup",
		data:{startnumber:0,itemnumber:itemnumber},
		success:function(data){
			
			showservicelist(data);
		}
	});


	/*******************右上角状态搜索***********************************/
	/**运行状态**/
	$("#startingnum").click(function(){
		//alert($(this).find("h5").text());
		alert("running");
		$("#headLabel").text("正在运行");
		$.post("wxhAllLineNum",{
			type : "service",
			value : "running"
		},function(data){
			allitemnumber = data;
			pagenum = Math.ceil(data /itemnumber);
			$(".pagination").empty();
			
			$(".pagination").removeClass("allserviceresult");
			$(".pagination").removeClass("searchresult");
			$(".pagination").removeClass("wxhRunningresult");
			$(".pagination").removeClass("wxhStopresult");
			$(".pagination").removeClass("wxhDisconnectresult");
			$(".pagination").addClass("wxhRunningresult");
			
			$(".wxhRunningresult").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-left'></i></a></li>");
			for(var i = 1; i<=pagenum;i++){
				if(i == 1){
					$(".wxhRunningresult").append("<li class='changenum active'><a class=" + i + ">" + i + "</a></li>");
				}else{
					$(".wxhRunningresult").append("<li class='changenum'><a class=" + i + ">" + i + "</a></li>");
				}
				
			}
			$(".wxhRunningresult").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-right'></i></a></li>");
			
			//全部服务：点击翻页之后
			$(".wxhRunningresult .changenum").click(function(){
				//alert("sdf");
				$(".wxhRunningresult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"wxhChooseList",
					data:{type:"service",value:"running",startnumber:startnum,itemnumber:itemnumber},
					success:function(data){
						showservicelist(data);
					}
				});
			});

			//全部服务：点击上一页
			$(".wxhRunningresult .fa-chevron-left").parent().click(function(){

				var currentpage = $(".wxhRunningresult .active").find("a").html();
				if(currentpage > 1){
					currentpage = Number(currentpage)-1;
					$(".wxhRunningresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");
					
					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"wxhChooseList",
						data:{type:"service",value:"running",startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
				
			});
			//全部服务：点击下一页
			$(".wxhRunningresult .fa-chevron-right").parent().click(function(){
				var currentpage = $(".wxhRunningresult .active a").html();
				//alert(pagenum);
				if(currentpage < pagenum){
					currentpage = Number(currentpage)+1;
					$(".wxhRunningresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");

					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"wxhChooseList",
						data:{type:"service",value:"running",startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
			});
		
			
		});	
		
		$.ajax({
			type:"post",
			url:"wxhChooseList",
			data:{type:"service",value:"running",startnumber:0,itemnumber:itemnumber},
			success:function(data){
				
				showservicelist(data);
			}
		});
	});
	
	/**停止状态**/
	$("#stoppednum").click(function(){
		//alert($(this).find("h5").text());
		alert("stop");
		$("#headLabel").text("已停止");
		$.post("wxhAllLineNum",{
			type : "service",
			value : "stop"
		},function(data){
			allitemnumber = data;
			pagenum = Math.ceil(data /itemnumber);
			$(".pagination").empty();
			
			$(".pagination").removeClass("allserviceresult");
			$(".pagination").removeClass("searchresult");
			$(".pagination").removeClass("wxhRunningresult");
			$(".pagination").removeClass("wxhStopresult");
			$(".pagination").removeClass("wxhDisconnectresult");
			
			$(".pagination").addClass("wxhStopresult");
			
			$(".wxhStopresult").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-left'></i></a></li>");
			for(var i = 1; i<=pagenum;i++){
				if(i == 1){
					$(".wxhStopresult").append("<li class='changenum active'><a class=" + i + ">" + i + "</a></li>");
				}else{
					$(".wxhStopresult").append("<li class='changenum'><a class=" + i + ">" + i + "</a></li>");
				}
				
			}
			$(".wxhStopresult").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-right'></i></a></li>");
			
			//全部服务：点击翻页之后
			$(".wxhStopresult .changenum").click(function(){
				//alert("sdf");
				$(".wxhStopresult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"wxhChooseList",
					data:{type:"service",value:"stop",startnumber:startnum,itemnumber:itemnumber},
					success:function(data){
						showservicelist(data);
					}
				});
			});

			//全部服务：点击上一页
			$(".wxhStopresult .fa-chevron-left").parent().click(function(){

				var currentpage = $(".wxhStopresult .active").find("a").html();
				if(currentpage > 1){
					currentpage = Number(currentpage)-1;
					$(".wxhStopresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");
					
					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"wxhChooseList",
						data:{type:"service",value:"stop",startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
				
			});
			//全部服务：点击下一页
			$(".wxhStopresult .fa-chevron-right").parent().click(function(){
				var currentpage = $(".wxhStopresult .active a").html();
				//alert(pagenum);
				if(currentpage < pagenum){
					currentpage = Number(currentpage)+1;
					$(".wxhStopresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");

					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"wxhChooseList",
						data:{type:"service",value:"stop",startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
			});
		
			
		});	
		
		$.ajax({
			type:"post",
			url:"wxhChooseList",
			data:{type:"service",value:"stop",startnumber:0,itemnumber:itemnumber},
			success:function(data){
				
				showservicelist(data);
			}
		});
	});
	
	/**断开状态**/
	$("#disconnectnum").click(function(){
		//alert($(this).find("h5").text());
		alert("discon");
		$("#headLabel").text("已断开");
		$.post("wxhAllLineNum",{
			type : "service",
			value : "disconnect"
		},function(data){
			allitemnumber = data;
			alert(allitemnumber);
			pagenum = Math.ceil(data /itemnumber);
			$(".pagination").empty();
			
			$(".pagination").removeClass("allserviceresult");
			$(".pagination").removeClass("searchresult");
			$(".pagination").removeClass("wxhRunningresult");
			$(".pagination").removeClass("wxhStopresult");
			$(".pagination").removeClass("wxhDisconnectresult");
			
			$(".pagination").addClass("wxhDisconnectresult");
			
			$(".wxhDisconnectresult").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-left'></i></a></li>");
			for(var i = 1; i<=pagenum;i++){
				if(i == 1){
					$(".wxhDisconnectresult").append("<li class='changenum active'><a class=" + i + ">" + i + "</a></li>");
				}else{
					$(".wxhDisconnectresult").append("<li class='changenum'><a class=" + i + ">" + i + "</a></li>");
				}
				
			}
			$(".wxhDisconnectresult").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-right'></i></a></li>");
			
			//全部服务：点击翻页之后
			$(".wxhDisconnectresult .changenum").click(function(){
				//alert("sdf");
				$(".wxhDisconnectresult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"wxhChooseList",
					data:{type:"service",value:"disconnect",startnumber:startnum,itemnumber:itemnumber},
					success:function(data){
						showservicelist(data);
					}
				});
			});

			//全部服务：点击上一页
			$(".wxhDisconnectresult .fa-chevron-left").parent().click(function(){

				var currentpage = $(".wxhDisconnectresult .active").find("a").html();
				if(currentpage > 1){
					currentpage = Number(currentpage)-1;
					$(".wxhDisconnectresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");
					
					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"wxhChooseList",
						data:{type:"service",value:"disconnect",startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
				
			});
			//全部服务：点击下一页
			$(".wxhDisconnectresult .fa-chevron-right").parent().click(function(){
				var currentpage = $(".wxhDisconnectresult .active a").html();
				//alert(pagenum);
				if(currentpage < pagenum){
					currentpage = Number(currentpage)+1;
					$(".wxhDisconnectresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");

					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"wxhChooseList",
						data:{type:"service",value:"disconnect",startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
			});
		
			
		});	
		
		$.ajax({
			type:"post",
			url:"wxhChooseList",
			data:{type:"service",value:"disconnect",startnumber:0,itemnumber:itemnumber},
			success:function(data){
				
				showservicelist(data);
			}
		});
	});
	
	/**总服务数**/
	$("#servicenum").click(function(){
		//alert($(this).find("h5").text());
		$("#headLabel").text("服务总数");
		alert("sum");
		$.post("wxhAllLineNum",{
			type : "service",
			value : "sum"
		},function(data){
			allitemnumber = data;
			pagenum = Math.ceil(data /itemnumber);
			$(".pagination").empty();
			
			$(".pagination").removeClass("allserviceresult");
			$(".pagination").removeClass("searchresult");
			$(".pagination").removeClass("wxhRunningresult");
			$(".pagination").removeClass("wxhStopresult");
			$(".pagination").removeClass("wxhDisconnectresult");
			
			$(".pagination").addClass("allserviceresult");

			$(".allserviceresult").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-left'></i></a></li>");
			for(var i = 1; i<=pagenum;i++){
				if(i == 1){
					$(".allserviceresult").append("<li class='changenum active'><a class=" + i + ">" + i + "</a></li>");
				}else{
					$(".allserviceresult").append("<li class='changenum'><a class=" + i + ">" + i + "</a></li>");
				}
				
			}
			$(".allserviceresult").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-right'></i></a></li>");
			//全部服务：点击翻页之后
			$(".allserviceresult .changenum").click(function(){
				//alert("sdf");
				$(".allserviceresult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"servicelistpageup",
					data:{startnumber:startnum,itemnumber:itemnumber},
					success:function(data){
						showservicelist(data);
					}
				});
			});

			//全部服务：点击上一页
			$(".allserviceresult .fa-chevron-left").parent().click(function(){

				var currentpage = $(".allserviceresult .active").find("a").html();
				if(currentpage > 1){
					currentpage = Number(currentpage)-1;
					$(".allserviceresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");
					
					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"servicelistpageup",
						data:{startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
				
			});
			//全部服务：点击下一页
			$(".allserviceresult .fa-chevron-right").parent().click(function(){
				var currentpage = $(".allserviceresult .active a").html();
				//alert(pagenum);
				if(currentpage < pagenum){
					currentpage = Number(currentpage)+1;
					$(".allserviceresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");

					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"servicelistpageup",
						data:{startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
			});
		});
		
		$.ajax({
			type:"post",
			url:"servicelistpageup",
			data:{startnumber:0,itemnumber:itemnumber},
			success:function(data){
				
				showservicelist(data);
			}
		});

	});
		
		
	/*******************条件搜索之后的服务显示***************************/
	//搜索服务： 点击搜索图标之后
	$("#searchservice").click(function(){
		var searchtype = $("#search-type").val();
		var searchkey = $("#searchvalue").val();
		
		$(".pagination").empty();
		
		$(".pagination").removeClass("allserviceresult");
		$(".pagination").removeClass("searchresult");
		$(".pagination").removeClass("wxhRunningresult");
		$(".pagination").removeClass("wxhStopresult");
		$(".pagination").removeClass("wxhDisconnectresult");
		
		$(".pagination").addClass("searchresult");
		//1 先请求服务个数生成页码
		$.ajax({
			type:"post",
			url:"getfilternumber",
			data:{filtertype:"service",type:searchtype,value:searchkey},
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
				//搜索服务：点击翻页之后
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
						url:"filterserviceservlet",
						data:{type:searchtype,value:searchkey,startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				});

				//搜索服务：点击上一页
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
							url:"filterserviceservlet",
							data:{type:searchtype,value:searchkey,startnumber:startnum,itemnumber:itemnumber},
							success:function(data){
								showservicelist(data);
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
							url:"filterserviceservlet",
							data:{type:searchtype,value:searchkey,startnumber:startnum,itemnumber:itemnumber},
							success:function(data){
								showservicelist(data);
							}
						});
					}
				});
			}
		});
		//生成符合条件的第一页数据
		$.ajax({
			type:"post",
			url: "filterserviceservlet",
			data:{type:searchtype,value:searchkey,startnumber:0,itemnumber:itemnumber},
			success: function(data){
				
				showservicelist(data);
			}
		});
		
	});


	 /***************公共部分*********************/


	//当每页显示个数变化之后
	$("#itemnumber").change(function(){
		itemnumber = $("#itemnumber").val();
		//重新生成页码
		pagenum = Math.ceil(allitemnumber/itemnumber);
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
		if($(".pagination").hasClass("allserviceresult")){
			//全部服务：点击翻页之后
			$(".allserviceresult .changenum").click(function(){-
				//alert("sdf");
				$(".allserviceresult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"servicelistpageup",
					data:{startnumber:startnum,itemnumber:itemnumber},
					success:function(data){
						showservicelist(data);
					}
				});
			});

			//全部服务：点击上一页
			$(".allserviceresult .fa-chevron-left").parent().click(function(){

				var currentpage = $(".allserviceresult .active").find("a").html();
				if(currentpage > 1){
					currentpage = Number(currentpage)-1;
					$(".allserviceresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");
					
					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"servicelistpageup",
						data:{startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
				
			});
			//全部服务：点击下一页
			$(".allserviceresult .fa-chevron-right").parent().click(function(){
				var currentpage = $(".allserviceresult .active a").html();
				//alert(pagenum);
				if(currentpage < pagenum){
					currentpage = Number(currentpage)+1;
					$(".allserviceresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");

					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"servicelistpageup",
						data:{startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
			});
			$.ajax({
				type:"post",
				url:"servicelistpageup",
				data:{startnumber:0,itemnumber:itemnumber},
				success:function(data){
					showservicelist(data);
				}
			});
		}else if($(".pagination").hasClass("searchresult")){
			//请求搜索的数据
			//搜索服务：点击翻页之后
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
					url:"filterserviceservlet",
					data:{type:searchtype,value:searchkey,startnumber:startnum,itemnumber:itemnumber},
					success:function(data){
						showservicelist(data);
					}
				});
			});

			//搜索服务：点击上一页
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
						url:"filterserviceservlet",
						data:{type:searchtype,value:searchkey,startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
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
						url:"filterserviceservlet",
						data:{type:searchtype,value:searchkey,startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
			});
			$.ajax({
				type:"post",
				url: "filterserviceservlet",
				data:{type:searchtype,value:searchkey,startnumber:0,itemnumber:itemnumber},
				success: function(data){
					showservicelist(data);
				}
			});
			
			/*$(".pagination").removeClass("allserviceresult");
		$(".pagination").removeClass("searchresult");
		$(".pagination").removeClass("wxhRunningresult");
		$(".pagination").removeClass("wxhStopresult");
		$(".pagination").removeClass("wxhDisconnectresult");*/
		}else if($(".pagination").hasClass("wxhRunningresult")){
			//全部服务：点击翻页之后
			$(".wxhRunningresult .changenum").click(function(){
				//alert("sdf");
				$(".wxhRunningresult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"wxhChooseList",
					data:{type:"service",value:"running",startnumber:startnum,itemnumber:itemnumber},
					success:function(data){
						showservicelist(data);
					}
				});
			});

			//全部服务：点击上一页
			$(".wxhRunningresult .fa-chevron-left").parent().click(function(){

				var currentpage = $(".wxhRunningresult .active").find("a").html();
				if(currentpage > 1){
					currentpage = Number(currentpage)-1;
					$(".wxhRunningresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");
					
					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"wxhChooseList",
						data:{type:"service",value:"running",startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
				
			});
			//全部服务：点击下一页
			$(".wxhRunningresult .fa-chevron-right").parent().click(function(){
				var currentpage = $(".wxhRunningresult .active a").html();
				//alert(pagenum);
				if(currentpage < pagenum){
					currentpage = Number(currentpage)+1;
					$(".wxhRunningresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");

					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"wxhChooseList",
						data:{type:"service",value:"running",startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
			});
			$.ajax({
				type:"post",
				url:"wxhChooseList",
				data:{type:"service",value:"running",startnumber:0,itemnumber:itemnumber},
				success:function(data){
					
					showservicelist(data);
				}
			});
		}else if($(".pagination").hasClass("wxhStopresult")){
			//全部服务：点击翻页之后
			$(".wxhStopresult .changenum").click(function(){
				//alert("sdf");
				$(".wxhStopresult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"wxhChooseList",
					data:{type:"service",value:"stop",startnumber:startnum,itemnumber:itemnumber},
					success:function(data){
						showservicelist(data);
					}
				});
			});

			//全部服务：点击上一页
			$(".wxhStopresult .fa-chevron-left").parent().click(function(){

				var currentpage = $(".wxhStopresult .active").find("a").html();
				if(currentpage > 1){
					currentpage = Number(currentpage)-1;
					$(".wxhStopresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");
					
					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"wxhChooseList",
						data:{type:"service",value:"stop",startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
				
			});
			//全部服务：点击下一页
			$(".wxhStopresult .fa-chevron-right").parent().click(function(){
				var currentpage = $(".wxhStopresult .active a").html();
				//alert(pagenum);
				if(currentpage < pagenum){
					currentpage = Number(currentpage)+1;
					$(".wxhStopresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");

					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"wxhChooseList",
						data:{type:"service",value:"stop",startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
			});
			$.ajax({
				type:"post",
				url:"wxhChooseList",
				data:{type:"service",value:"stop",startnumber:0,itemnumber:itemnumber},
				success:function(data){
					
					showservicelist(data);
				}
			});
		}else if($(".pagination").hasClass("wxhDisconnectresult")){
			//全部服务：点击翻页之后
			$(".wxhDisconnectresult .changenum").click(function(){
				//alert("sdf");
				$(".wxhDisconnectresult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"wxhChooseList",
					data:{type:"service",value:"disconnect",startnumber:startnum,itemnumber:itemnumber},
					success:function(data){
						showservicelist(data);
					}
				});
			});

			//全部服务：点击上一页
			$(".wxhDisconnectresult .fa-chevron-left").parent().click(function(){

				var currentpage = $(".wxhDisconnectresult .active").find("a").html();
				if(currentpage > 1){
					currentpage = Number(currentpage)-1;
					$(".wxhDisconnectresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");
					
					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"wxhChooseList",
						data:{type:"service",value:"disconnect",startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
				
			});
			//全部服务：点击下一页
			$(".wxhDisconnectresult .fa-chevron-right").parent().click(function(){
				var currentpage = $(".wxhDisconnectresult .active a").html();
				//alert(pagenum);
				if(currentpage < pagenum){
					currentpage = Number(currentpage)+1;
					$(".wxhDisconnectresult .active").removeClass('active');
					var currentpage_s = "." + currentpage;
					$(currentpage_s).parent().addClass("active");

					var itemnumber = $("#itemnumber").val();	
					var startnum = itemnumber*(currentpage-1);
					$.ajax({
						type:"post",
						url:"wxhChooseList",
						data:{type:"service",value:"disconnect",startnumber:startnum,itemnumber:itemnumber},
						success:function(data){
							showservicelist(data);
						}
					});
				}
			});
			$.ajax({
				type:"post",
				url:"wxhChooseList",
				data:{type:"service",value:"disconnect",startnumber:0,itemnumber:itemnumber},
				success:function(data){
					
					showservicelist(data);
				}
			});
		}
		
	});

	//选择搜索类型	
	$("#search-type").change(function(){
		var searchtype = $("#search-type").val();
		if(searchtype == "servicename" || searchtype == "description"){
			$(".filtertype").empty();
			$(".filtertype").append("<input type='text' class='input-sm' id='searchvalue'/>");
			
		}else{
			$(".filtertype").empty();
			$(".filtertype").append("<select class='input-sm' id='searchvalue' style='font-size:14px;display: inline-block;width: 9.3%;'></select>");
			$.ajax({
				type:"post",
				url: "filter",
				data:{type:searchtype},
				success:function(data){
					//alert(data);
					var infoarray = new Array();
					infoarray = data.split("@");
					for(var i=0;i<infoarray.length;i++){
					
						$("#searchvalue").append("<option value=" + infoarray[i] +">"+ infoarray[i] + "</option>");
					}
					
				}
			});
		}

	});
	
	
	function showservicelist(data){
		$("#table1 tbody").empty();
		res=data.split("@");
		
		var serviceinfo = JSON.parse(res[0]);
		var messageinfo = JSON.parse(res[1]);
		var servicestatusinfo = JSON.parse(res[2]);
		
		
		for(var i=0; i<serviceinfo.servicelist.length; i++){
			var appendinfo = "<tr id="+serviceinfo.servicelist[i].serviceid+">"+"<td  class='servicestatus' style='text-align:center;'>";
			var servicestatus = "";
			
			//判断服务状态
			for(var j=0; j<servicestatusinfo.statuslist.length;j++){
				
				if(servicestatusinfo.statuslist[j].serviceid == serviceinfo.servicelist[i].serviceid){
					if(servicestatusinfo.statuslist[j].status == "start"){
						servicestatus = "start";
					}else if(servicestatusinfo.statuslist[j].status == "stop"){
						servicestatus = "stop";
					}else if(servicestatusinfo.statuslist[j].status == "waiting"){
						servicestatus = "waiting";
					}
				}
			}
			if(servicestatus == ""){
				servicestatus = "disconnect";
			}
			
			if(servicestatus == "start"){
				appendinfo += "<i class='fa fa-circle txt-color-green'><label>&nbsp;&nbsp;运行</label></td>";
			}else if(servicestatus == "stop"){
				appendinfo += "<i class='fa fa-circle txt-color-red'><label>&nbsp;&nbsp;停止</label></td>";
			}else if(servicestatus == "disconnect"){
				appendinfo += "<i class='fa fa-circle txt-color-orange'><label>&nbsp;&nbsp;断开</label></td>";
			}else if(servicestatus == "waiting"){
				appendinfo += "<i class='fa fa-circle txt-color-purple'><label>&nbsp;&nbsp;等待</label></td>";
			}
			
			appendinfo += "<td class='servicename'><a href='"+serviceinfo.servicelist[i].address+"'>" + serviceinfo.servicelist[i].servicename + "</a></td>";
			appendinfo += "<td class='serviceversion' style='text-align:center;'>" + serviceinfo.servicelist[i].version + "</td>";
			
			//解析服务接口信息
			var messagelist_a = new Array();
			var messagecount = 0;
			
			
			appendinfo += "<td style='text-align:left;'><div class='tree smart-form'><ul><li><span><i class='fa fa-lg fa-plus-circle'></i>" + serviceinfo.servicelist[i].servicename + "</span><ul>";
			for(var j=0; j<messageinfo.messagelist.length; j++){


				if(messageinfo.messagelist[j].serviceid == serviceinfo.servicelist[i].serviceid){
					
					messagelist_a[messagecount] = messageinfo.messagelist[j].servicemessage;
					//alert("messagelist:"+messageinfo.messagelist[j].servicemessage);
					messagecount ++;
				}
			}

			for(var j=0; j<messagelist_a.length; j++){
				messageinfo_a = messagelist_a[j].split(";");
				//alert("sdfd");
				appendinfo += "<li style='display:none'><span><i class='fa fa-lg fa-plus-circle'></i>" + messageinfo_a[0] + "</span>"
							 + "<ul>" 
							 + "<li style='display:none'><i class='icon-leaf'></i>关键字<br/>" + messageinfo_a[1] + "</li>"
							 + "<li style='display:none'><i class='icon-leaf'></i>描述<br/>" + messageinfo_a[2] + "</li>"
							 + "<li style='display:none'><i class='icon-leaf'></i>数据报<br/>" + messageinfo_a[3] + "</li>"
							 + "<li style='display:none'><i class='icon-leaf'></i>地址<br/>" + messageinfo_a[4] + "</li>"
							 + "</ul></li>";
				
			}
			appendinfo += "</ul></li></ul></div></td>";
			var frontType="";
			if(serviceinfo.servicelist[i].servicetype == "exe"){
				frontType = "可执行程序/服务端";
		    	//alert("type:"+type);
		    }
		    else if(serviceinfo.servicelist[i].servicetype == "Restful")
		    {
		    	frontType = "Servlet";
		    }else if(serviceinfo.servicelist[i].servicetype == "Web Site")
	    	{
		    	frontType = "IIS";
	    	}else if(serviceinfo.servicelist[i].servicetype == "Web Service")
	    	{
	    		frontType = "Axis2";
	    	}
		    	
			appendinfo += "<td class='servicetype'>" + frontType + "</td>";
			appendinfo += "<td class='businesstype'>" + serviceinfo.servicelist[i].businesstype + "</td>";
			appendinfo += "<td class='owersystem'>" + serviceinfo.servicelist[i].owersystem + "</td>";
			appendinfo += "<td class='description'>" + serviceinfo.servicelist[i].description + "</td>";
			appendinfo += "<td><a herf='#' class='readgraph'>点击查看详情</a></td>";
			appendinfo += "<td><a class='btn btn-primary btn-sm adapte' href='javascript:void(0);' name='adapte'>适配</a></td>";
			appendinfo += "<td>123</td>";
			
			//开关按钮的状态
			if(servicestatus == "start"){
				appendinfo += "<td><form class='smart-form' style='width:78px;height:30px;'><label class='toggle '><input type='checkbox' name='checkbox-toggle' checked='checked'><i data-swchon-text='开' data-swchoff-text='关' class='startstop'></i>	</label></form></td>";
			}else if(servicestatus == "stop"){
				appendinfo += "<td><form class='smart-form' style='width:78px;height:30px;'><label class='toggle'><input type='checkbox' name='checkbox-toggle' ><i data-swchon-text='开' data-swchoff-text='关' class='startstop'></i>	</label></form></td>";
			}else if(servicestatus == "disconnect"){
				appendinfo += "<td><form class='smart-form' style='width:78px;height:30px;'><label class='toggle state-disabled'><input type='checkbox' name='checkbox-toggle' disabled='disabled'><i data-swchon-text='开' data-swchoff-text='关'></i></label></form></td>";
			}else if(servicestatus == "waiting"){
				appendinfo += "<td><form class='smart-form' style='width:78px;height:30px;'><label class='toggle'><input type='checkbox' name='checkbox-toggle' ><i data-swchon-text='开' data-swchoff-text='关' class='startstop'></i>	</label></form></td>";
			}
			
			//appendinfo += "<td style='text-align:center;' class='copynum'><div class='form-group wxh-spinner-size'><input class='form-control'  name='spinner-decimal' value=" + serviceinfo.servicelist[i].copynum + " max=" + serviceinfo.servicelist[i].maxcopynum + "></div></td>";
			//appendinfo += "<td style='text-align:center;' class='concurrency'><div class='form-group wxh-spinner-size'><input class='form-control' name='spinner-decimal' value=" + serviceinfo.servicelist[i].concurrency + "></div></td>";
			
			//调整最大并发数、副本因子以及卸载按钮的状态
			if(servicestatus == "disconnect"){
				appendinfo += "<td style='text-align:center;' class='copynum'><div class='form-group wxh-spinner-size'><input class='form-control disabled'  name='spinner-decimal' value=" + serviceinfo.servicelist[i].copynum + " max=" + serviceinfo.servicelist[i].maxcopynum + "></div></td>";
				appendinfo += "<td style='text-align:center;' class='concurrency'><div class='form-group wxh-spinner-size'><input class='form-control disabled' name='spinner-decimal' value=" + serviceinfo.servicelist[i].concurrency + "></div></td>";
				appendinfo += "<td><a class='btn btn-danger deleteservice btn-sm' href='javascript:void(0);'style='width:100%;font-size:14px;' disabled='disabled'><i class='fa fa-times-circle'></i>&nbsp;&nbsp;卸载</a></td>";
			}else{
				appendinfo += "<td style='text-align:center;' class='copynum'><div class='form-group wxh-spinner-size'><input class='form-control'  name='spinner-decimal' value=" + serviceinfo.servicelist[i].copynum + " max=" + serviceinfo.servicelist[i].maxcopynum + "></div></td>";
				appendinfo += "<td style='text-align:center;' class='concurrency'><div class='form-group wxh-spinner-size'><input class='form-control ' name='spinner-decimal' value=" + serviceinfo.servicelist[i].concurrency + "></div></td>";
				appendinfo += "<td><a class='btn btn-danger deleteservice btn-sm' href='javascript:void(0);'style='width:100%;font-size:14px;' ><i class='fa fa-times-circle'></i>&nbsp;&nbsp;卸载</a></td>";
			}
			
			$("#table1 tbody").append(appendinfo);

			
		}
	
		//服务接口信息树状控件
		$('.tree > ul').attr('role', 'tree').find('ul').attr('role', 'group');
		$('.tree').find('li:has(ul)').addClass('parent_li').attr('role', 'treeitem').find(' > span').attr('title', 'Collapse this branch').on('click', function(e) {
			var children = $(this).parent('li.parent_li').find(' > ul > li');
			if (children.is(':visible')) {
				children.hide('fast');
				$(this).attr('title', 'Expand this branch').find(' > i').removeClass().addClass('fa fa-lg fa-plus-circle');
			} else {
				children.show('fast');
				$(this).attr('title', 'Collapse this branch').find(' > i').removeClass().addClass('fa fa-lg fa-minus-circle');
			}
			e.stopPropagation();
		});

		$('[name=spinner-decimal]').spinner({
		    step: 1,
		    numberFormat: "n",
		    min: 1
		});
		$('[name=spinner-decimal].disabled').spinner({
		    step: 1,
		    numberFormat: "n",
		    disabled: true,
		    min:1
		});
	
		pageSetUp();
		$(".readgraph").click(function(){
			//var serviceid = $(this).parent().parent().attr("id");
			$(".page1").dialog("open");
			var serviceid = $(this).parent().parent().attr("id");
			var servicename = $(this).parent().parent().find(".servicename").text();
			var systemname = $(this).parent().parent().find(".owersystem").text();
			$.ajax({
				type:"post",
				url:"inputsession",
				data:{serviceid:serviceid,servicename:servicename,systemname:systemname},
				success:function(){
					$(".svg-here").empty();
					Topology.init();
				}
			});
			
		});

		$(".startstop").click(function(){
			var command;
			var servicetype = $(this).parent().parent().parent().parent().find(".servicetype").html();
			//alert(servicetype);
			var serviceid = $(this).parent().parent().parent().parent().attr("id");
			//alert(serviceid);
			if($(this).parent().parent().parent().parent().find(".servicestatus i").hasClass("txt-color-green")){
				command = "stop";
			}else{
				command = "start";
			}
			//alert("1");
			$.ajax({
				type: "post",
				url: "servicestatusservlet",
				data:{serviceid:serviceid,status:command,servicetype:servicetype},
				success:function(data){
					
					if(data == "true"){
						serviceid = "#" + serviceid;
						
						if(command == "start"){
						
							$(serviceid).find(".servicestatus i").removeClass('txt-color-red');
							//$(serviceid).find(".servicestatus i lable").empty();
							
							$(serviceid).find(".servicestatus i").addClass('txt-color-green');
							$(serviceid).find(".servicestatus i").html("<lable>&nbsp&nbsp运行</lable>");
							
						}else{
							
							$(serviceid).find(".servicestatus i").removeClass('txt-color-green');

							$(serviceid).find(".servicestatus i").addClass('txt-color-red');
							$(serviceid).find(".servicestatus i").html("<lable>&nbsp&nbsp停止</lable>");
						}
					}
					
				}
			});
			
			/*setTimeout(function(){
				//alert("18");
				$.post("askNowstatus",{
					ID : serviceid
				},function(data){
					serviceid = "#" + serviceid;
					if(data == "start"){
						$(serviceid).find(".servicestatus i").removeClass('txt-color-red');
						//$(serviceid).find(".servicestatus i lable").empty();
						
						$(serviceid).find(".servicestatus i").addClass('txt-color-green');
						$(serviceid).find(".servicestatus i").html("<lable>&nbsp&nbsp运行</lable>");
					}else{
						
						$(serviceid).find(".servicestatus i").removeClass('txt-color-green');

						$(serviceid).find(".servicestatus i").addClass('txt-color-red');
						$(serviceid).find(".servicestatus i").html("<lable>&nbsp&nbsp停止</lable>");
					}
				});
			},18000);*/		
		});

		$(".copynum div span input").blur(function(){
			$(this).parent().after("<img alt='' src='img/select2-spinner.gif'>");
			var copynum = $(this).val();
			var serviceid = $(this).parent().parent().parent().parent().attr("id");
			$.ajax({
				type:"post",
				url:"servicecopynumservlet",
				data:{serviceid:serviceid,copynum:copynum},
				success:function(data){
					//alert(data);
					if(data == "true"){
						//alert("修改副本因子成功");
						$(".copynum div img").remove();
					}
				}
			});
		});
		
		$(".concurrency div span input").blur(function(){
			$(this).parent().after("<img alt='' src='img/select2-spinner.gif'>");
			var concurrency = $(this).val();
			var serviceid = $(this).parent().parent().parent().parent().attr("id");
			$.ajax({
				type:"post",
				url:"serviceconcurrencynumservlet",
				data:{serviceid:serviceid,concurrency:concurrency},
				success:function(data){
					//alert(data);
					if(data == "true"){
						//alert("修最大并发数成功");
						$(".concurrency div img").remove();
					}
				}
			});
		});
		$(".deleteservice").click(function(){
			var serviceid = $(this).parent().parent().attr("id"); 
			$.ajax({
				type:"post",
				url:"servicedeleteservlet",
				data:{serviceid:serviceid},
				success:function(data){
					if(data == "true"){
						alert("卸载成功");
					}
				}
			});
		});
		
		//服务适配弹出页面
		//id编号中1代表ftp，2代表file
		$(".adapte").click(function(){
			ServiceAdapter = $(this).parent().parent().attr("id");
			//alert(ServiceAdapter);
			$(".page4").dialog("open");
			//alert("azc");
			$("#wid-id-10").removeAttr("style");
			
			//tab1动态显示
			$("#InputProtocal").change(function(){
				$(".SerAdaRowI").remove();
				var stype = $("#InputProtocal").val();
				if(stype == "ftp")
				{
					var ProtHtml="";
					
					ProtHtml += "<div class='row SerAdaRowI' style='margin-left:28%;'><div class='col-sm-4'><label style='font-size:20px;float:left;'>输入路径 ：</label>	<div class='form-group'><div class='input-group'><span class='input-group-addon'><i class='fa fa-flag fa-lg fa-fw'></i></span><input class='form-control input-lg' type='text' id='InputPath' style='width:190%;'></div></div></div></div>";
					ProtHtml += "<div class='row SerAdaRowI' style='margin-left:28%;'><div class='col-sm-4'><label style='font-size:20px;float:left;'>输入主机 ：</label>	<div class='form-group'><div class='input-group'><span class='input-group-addon'><i class='fa fa-flag fa-lg fa-fw'></i></span><input class='form-control input-lg' type='text' id='InputHost' style='width:190%;'></div></div></div></div>";
					ProtHtml += "<div class='row SerAdaRowI' style='margin-left:28%;'><div class='col-sm-4'><label style='font-size:20px;float:left;'>输入端口 ：</label>	<div class='form-group'><div class='input-group'><span class='input-group-addon'><i class='fa fa-flag fa-lg fa-fw'></i></span><input class='form-control input-lg' type='text' id='InputPort' style='width:190%;'></div></div></div></div>";
					ProtHtml += "<div class='row SerAdaRowI' style='margin-left:28%;'><div class='col-sm-4'><label style='font-size:20px;float:left;'>用户名 ：</label>	<div class='form-group'><div class='input-group'><span class='input-group-addon'><i class='fa fa-flag fa-lg fa-fw'></i></span><input class='form-control input-lg' type='text' id='InputUser' style='width:190%;'></div></div></div></div>";
					ProtHtml += "<div class='row SerAdaRowI' style='margin-left:28%;'><div class='col-sm-4'><label style='font-size:20px;float:left;'>密码 ：</label>	<div class='form-group'><div class='input-group'><span class='input-group-addon'><i class='fa fa-flag fa-lg fa-fw'></i></span><input class='form-control input-lg' type='text' id='InputPassword' style='width:190%;'></div></div></div></div>";
					ProtHtml += "<div class='row SerAdaRowI'><div id='order-form' class='smart-form wxh-browse' novalidate='novalidate'><section><div class='input input-file wxh-browse-inner'><span class='button wxh-broButton'><input  id='file1' type='file' name='file2' onchange='this.parentNode.nextSibling.value = this.value'>选择文件</span><input class='wxh-broInput' type='text' placeholder='选择服务文件' readonly=''></div></section></div></div>";
					
					$("#inputRow").after(ProtHtml);
					
				}else if(stype == "file")
				{
					var ProtHtml="";
					
					ProtHtml += "<div class='row SerAdaRowI' style='margin-left:28%;'><div class='col-sm-4'><label style='font-size:20px;float:left;'>输入路径 ：</label>	<div class='form-group'><div class='input-group'><span class='input-group-addon'><i class='fa fa-flag fa-lg fa-fw'></i></span><input class='form-control input-lg' type='text' id='InputPath2' style='width:190%;'></div></div></div></div>";
					ProtHtml += "<div class='row SerAdaRowI'><div id='order-form' class='smart-form wxh-browse' novalidate='novalidate'><section><div class='input input-file wxh-browse-inner'><span class='button wxh-broButton'><input  id='file1' type='file' name='file2' onchange='this.parentNode.nextSibling.value = this.value'>选择文件</span><input class='wxh-broInput' type='text' placeholder='选择服务文件' readonly=''></div></section></div></div>";
					
					$("#inputRow").after(ProtHtml);
				}
			});
			//tab2动态显示
			$("#OutputProtocal").change(function(){
				$(".SerAdaRowO").remove();
				var stype = $("#OutputProtocal").val();
				if(stype == "ftp")
				{
					var ProtHtml="";
					
					ProtHtml += "<div class='row SerAdaRowO' style='margin-left:28%;'><div class='col-sm-4'><label style='font-size:20px;float:left;'>输出路径 ：</label>	<div class='form-group'><div class='input-group'><span class='input-group-addon'><i class='fa fa-flag fa-lg fa-fw'></i></span><input class='form-control input-lg' type='text' id='OutputPath' style='width:190%;'></div></div></div></div>";
					ProtHtml += "<div class='row SerAdaRowO' style='margin-left:28%;'><div class='col-sm-4'><label style='font-size:20px;float:left;'>输出主机 ：</label>	<div class='form-group'><div class='input-group'><span class='input-group-addon'><i class='fa fa-flag fa-lg fa-fw'></i></span><input class='form-control input-lg' type='text' id='OutputHost' style='width:190%;'></div></div></div></div>";
					ProtHtml += "<div class='row SerAdaRowO' style='margin-left:28%;'><div class='col-sm-4'><label style='font-size:20px;float:left;'>输出端口 ：</label>	<div class='form-group'><div class='input-group'><span class='input-group-addon'><i class='fa fa-flag fa-lg fa-fw'></i></span><input class='form-control input-lg' type='text' id='OutputPort' style='width:190%;'></div></div></div></div>";
					ProtHtml += "<div class='row SerAdaRowO' style='margin-left:28%;'><div class='col-sm-4'><label style='font-size:20px;float:left;'>用户名 ：</label>	<div class='form-group'><div class='input-group'><span class='input-group-addon'><i class='fa fa-flag fa-lg fa-fw'></i></span><input class='form-control input-lg' type='text' id='OutputUser' style='width:190%;'></div></div></div></div>";
					ProtHtml += "<div class='row SerAdaRowO' style='margin-left:28%;'><div class='col-sm-4'><label style='font-size:20px;float:left;'>密码 ：</label>	<div class='form-group'><div class='input-group'><span class='input-group-addon'><i class='fa fa-flag fa-lg fa-fw'></i></span><input class='form-control input-lg' type='text' id='OutputPassword' style='width:190%;'></div></div></div></div>";
					ProtHtml += "<div class='row SerAdaRowO'><div id='order-form' class='smart-form wxh-browse' novalidate='novalidate'><section><div class='input input-file wxh-browse-inner'><span class='button wxh-broButton'><input  id='file2' type='file' name='file2' onchange='this.parentNode.nextSibling.value = this.value'>选择文件</span><input class='wxh-broInput' type='text' placeholder='选择服务文件' readonly=''></div></section></div></div>";
					
					$("#outputRow").after(ProtHtml);
					
				}else if(stype == "file")
				{
					var ProtHtml="";
					
					ProtHtml += "<div class='row SerAdaRowO' style='margin-left:28%;'><div class='col-sm-4'><label style='font-size:20px;float:left;'>输出路径 ：</label>	<div class='form-group'><div class='input-group'><span class='input-group-addon'><i class='fa fa-flag fa-lg fa-fw'></i></span><input class='form-control input-lg' type='text' id='OutputPath2' style='width:190%;'></div></div></div></div>";
					ProtHtml += "<div class='row SerAdaRowO' style='margin-left:28%;'><div class='col-sm-4'><label style='font-size:20px;float:left;'>输出组件 ：</label>	<div class='form-group'><div class='input-group'><span class='input-group-addon'><i class='fa fa-flag fa-lg fa-fw'></i></span><input class='form-control input-lg' type='text' id='OutputPattern2' style='width:190%;'></div></div></div></div>";
					ProtHtml += "<div class='row SerAdaRowO'><div id='order-form' class='smart-form wxh-browse' novalidate='novalidate'><section><div class='input input-file wxh-browse-inner'><span class='button wxh-broButton'><input  id='file2' type='file' name='file2' onchange='this.parentNode.nextSibling.value = this.value'>选择文件</span><input class='wxh-broInput' type='text' placeholder='选择服务文件' readonly=''></div></section></div></div>";
					
					$("#outputRow").after(ProtHtml);
				}
			});
			
			
			$('#bootstrap-wizard-1').bootstrapWizard({
			    'tabClass': 'form-wizard',
			    'onNext': function (tab, navigation, index) {
			      var $valid = $("#wizard-1").valid();
			      if (!$valid) {
			        $validator.focusInvalid();
			        return false;
			      } else {
			        $('#bootstrap-wizard-1').find('.form-wizard').children('li').eq(index - 1).addClass(
			          'complete');
			        $('#bootstrap-wizard-1').find('.form-wizard').children('li').eq(index - 1).find('.step')
			        .html('<i class="fa fa-check"></i>');
			      }
			      
			    //第2步进第3步,对最终表格插数据
			      if($("#tab2").hasClass("active")){
			    	  $("#tab10-table").empty();
			    	  var finalType = $("#InputProtocal").val();
					  if(finalType == "ftp"){
							htmlInfo="";
								
							htmlInfo += "<tr id='adapterWXH'><td class='InputProtocal'>"+$("#InputProtocal").val()+"</td>";   //第2行 服务类型
							
							htmlInfo += "<td class='InputPara'>"+$("#InputPath").val()+"&"+$("#InputHost").val()+"&"+$("#InputPort").val()+"</td>";   //第3行 服务名称
							
							htmlInfo += "<td class='ID' style='display:none'>"+ServiceAdapter+"</td>";   //ID
							
							htmlInfo += "<td class='IUserName' style='display:none'>"+$("#InputUser").val()+"</td>";   //
							
							htmlInfo += "<td class='Ipassword' style='display:none'>"+$("#InputPassword").val()+"</td>";   //
							
							htmlInfo += "<td class='OutputProtocal'>"+$("#OutputProtocal").val()+"</td>";   //
							
							htmlInfo += "<td class='OutputPara'>"+$("#OutputPath").val()+"&"+$("#OutputHost").val()+"&"+$("#OutputPort").val()+"</td>";   //第7行 关键字
							
							htmlInfo += "<td class='OUserName' style='display:none'>"+$("#OutputUser").val()+"</td>";
							
							htmlInfo += "<td class='Opassword' style='display:none'>"+$("#OutputPassword").val()+"</td></tr>";

							$("#tab10-table").append(htmlInfo);
							$("#wid-id-3").removeAttr("style");
					  
					  }else if(finalType == "file"){
						  htmlInfo="";
						  
						  htmlInfo += "<tr id='adapterWXH'><td class='InputProtocal'>"+$("#InputProtocal").val()+"</td>";  
						  
						  htmlInfo += "<td class='InputPara'>"+$("#InputPath2").val()+"</td>";  
						  
						  htmlInfo += "<td class='ID' style='display:none'>"+ServiceAdapter+"</td>";  //ID
						  
						  htmlInfo += "<td class='IUserName' style='display:none'></td>"; 
						  
						  htmlInfo += "<td class='Ipassword' style='display:none'></td>";
						  
						  htmlInfo += "<td class='OutputProtocal'>"+$("#OutputProtocal").val()+"</td>";
						  
						  htmlInfo += "<td class='OutputPara'>"+$("#OutputPath2").val()+"&"+$("#OutputPattern2").val()+"</td>"; 
						  
						  htmlInfo += "<td class='OUserName' style='display:none'></td>";
						  
						  htmlInfo += "<td class='Opassword' style='display:none'></td></tr>";
						  
						  $("#tab10-table").append(htmlInfo);
						  $("#wid-id-3").removeAttr("style");
					  }
			    	
						
			      }
			     
                 
				
			    },
			    onPrevious:function (tab, navigation, index) {
			    	
			    },
			    onTabClick: function (tab, navigation, index) { 
	                alert('on tab click disabled');
	                return false;
			    }                  
			  });
			
			$("#Submit").click(function(){
				//alert("123"); 
				
	                $(".submit_form").ajaxSubmit({
	                    type: "post",
	                    url: "UploadServlet",  
	                    success: function (response) {
	                    	
	                    	var $abc = $("#adapterWXH");
	                    	$.post("ServiceAdapater",{
	                    		ID : $abc.find(".ID").text(),
	                    		InputProtocal : $abc.find(".InputProtocal").text(),
	                    		InputPara : $abc.find(".InputPara").text(),
	                    		InputUser : $abc.find(".IUserName").text(),
	                    		InputPassword : $abc.find(".Ipassword").text(),
	                    		
	                    		OutputProtocal : $abc.find(".OutputProtocal").text(),
	                    		OutputPara : $abc.find(".OutputPara").text(),
	                    		OutputUser : $abc.find(".OUserName").text(),
	                    		OutputPassword : $abc.find(".Opassword").text(),		
	                    	},function(data){});
	                    	
	                    }
//	                    error: function (msg) {
//	                        alert("error");    
//	                    }
	                });
	                return false;
	           
			});
			
		});
	}
	
	
	
});