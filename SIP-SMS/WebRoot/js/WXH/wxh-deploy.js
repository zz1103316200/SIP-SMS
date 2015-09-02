$(document).ready(function() {

				// DO NOT REMOVE : GLOBAL FUNCTIONS!
				pageSetUp();
                var path="";
                var ftype="";
                var stype="";
                var type="";
                var nodetype="";
                var data;
                var ip="";
                var nodeId="";
                
            	//第二页翻页的三个全局变量
				//获取当前一页显示多少条记录
				var itemnumber = 5;
				//一共有多少页
				var pagenum;
				//一共有多少条记录
				var allitemnumber;
				/*
				 * PAGE RELATED SCRIPTS
				 */

				 //第一页翻页的全局变量
				   //所有总数据
				    var TheBigData;
				    //搜索完成的JSON数据
				    var TheSearData;
				    //搜索完成的字符串
				    var WxhSearch;
				    //每页显示的行数
	                var wxhitemnum = $("#itemnumber").val();
	                //页码总数
	                var wxhpagenum;
	                //总行数
	                var wxhallnum;
					
				 $.post("servlet/getKuayuAjaxServlet",null,function(data){
					 TheBigData=JSON.parse(data);
					 alert(data);
					 //TheBigData = data;
					 //alert(TheBigData);
					 if(TheBigData.length < wxhitemnum)
					    {
							//alert("4");
							append(TheBigData,0,TheBigData.length);
						}
						else{
							//alert("10");
							append(TheBigData,0,wxhitemnum);
						}
					 //append(TheBigData,0,wxhitemnum);
					 Allpage();
				 });
				 
				 //全部页面:显示页面标签
				 function Allpage(){
				 wxhallnum = TheBigData.length;
				 //alert(wxhallnum);
				 wxhpagenum = Math.ceil(wxhallnum /wxhitemnum);
				 //alert(wxhpagenum);
					$(".wxhpage").empty();
					$(".wxhpage").addClass("allserviceresult1");
					$(".allserviceresult1").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-left'></i></a></li>");
					for(var i = 1; i<=wxhpagenum;i++){
						if(i == 1){
							$(".allserviceresult1").append("<li class='changenum1 active'><a class=" + i + ">" + i + "</a></li>");
						}else{
							$(".allserviceresult1").append("<li class='changenum1'><a class=" + i + ">" + i + "</a></li>");
						}
						
					}
					$(".allserviceresult1").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-right'></i></a></li>");
					
					//全部服务：点击页码翻页
					$(".allserviceresult1 .changenum1").click(function(){
						//alert("sdf");
						$(".allserviceresult1 li").removeClass("active");
						var currentpage = $(this).find("a").html();
						$(this).addClass("active");

						var num = parseInt($("#itemnumber").val());
						//alert(num);
						var start = parseInt(num*(currentpage-1));
						//alert(start);
						var minus = parseInt(wxhallnum-start);
						//alert("minus:"+minus);
						if(minus > num){
							var end = parseInt(start+num);
							//alert(end);
							append(TheBigData,start,end);
						}else{
							append(TheBigData,start,wxhallnum);
						}
					});
					
					//全部服务：点击上一页
					$(".allserviceresult1 .fa-chevron-left").parent().click(function(){

						var currentpage = $(".allserviceresult1 .active").find("a").html();
						if(currentpage > 1){
							currentpage = Number(currentpage)-1;
							$(".allserviceresult1 .active").removeClass('active');
							var currentpage_s = "." + currentpage;
							$(currentpage_s).parent().addClass("active");
							
							var num = parseInt($("#itemnumber").val());	
							var start = parseInt(num*(currentpage-1));
							var end = parseInt(start+num);
							append(TheBigData,start,end);
						}						
					});
					
					//全部服务：点击下一页
					$(".allserviceresult1 .fa-chevron-right").parent().click(function(){
						var currentpage = $(".allserviceresult1 .active a").html();
						//alert("a");
						if(currentpage < wxhpagenum){
							//alert("pagenum");
							currentpage = Number(currentpage)+1;
							$(".allserviceresult1 .active").removeClass('active');
							var currentpage_s = "." + currentpage;
							$(currentpage_s).parent().addClass("active");

							var num = parseInt($("#itemnumber").val());	
							var start = parseInt(num*(currentpage-1));
							var minus = parseInt(wxhallnum-start);
							//alert("minus:"+minus);
							if(minus > num){
								var end = parseInt(start+num);
								//alert(end);
								append(TheBigData,start,end);
							}else{
								append(TheBigData,start,wxhallnum);
							}
						}
					});
				 } 
					
				//点击搜索按钮，生成搜索结果子串
				$("#searchservice").click(function(){
					//alert(alldata[3].serv_type);
					var searchtype = $("#search-type").val();
					var searchkey = $("#searchvalue").val();
					//alert(searchtype);
					//alert(searchkey);
					
					//拼接搜索结果串
					if(searchtype == "servicename"){
						//alert("1");
						WxhSearch="";
						WxhSearch="[";
						for(var i=0;i<TheBigData.length;i++){
							if(TheBigData[i].name.indexOf(searchkey) != -1){
								//alert("2");
											
								WxhSearch += "{'download_address':'"+TheBigData[i].download_address+"',";
								WxhSearch += "'ftype':'"+TheBigData[i].ftype+"',";
								WxhSearch += "'icon':'"+TheBigData[i].icon+"',";
								WxhSearch += "'name':'"+TheBigData[i].name+"',";
								WxhSearch += "'version':'"+TheBigData[i].version+"',";
								WxhSearch += "'system':'"+TheBigData[i].system+"',";
								WxhSearch += "'business_type':'"+TheBigData[i].business_type+"',";
								WxhSearch += "'keyword':'"+TheBigData[i].keyword+"',";
								WxhSearch += "'description':'"+TheBigData[i].description+"',";
								WxhSearch += "'id':'"+TheBigData[i].id+"',";
								WxhSearch += "'stype':'"+TheBigData[i].stype+"',";
								WxhSearch += "'release_time':'"+TheBigData[i].release_time+"',";
								WxhSearch += "'developer':'"+TheBigData[i].developer+"'},";
								
							}
						}
						
						WxhSearch=WxhSearch.substring(0, WxhSearch.length-1);
						WxhSearch += "]";
						//alert(WxhSearch);
						TheSearData = eval("("+WxhSearch+")");
						//alert(TheSearData.length);
					}
					//else if
					
					
					
					if(TheSearData.length < wxhitemnum)
				    {
						//alert("4");
						append(TheSearData,0,TheSearData.length);
					}
					else{
						//alert("10");
						append(TheSearData,0,wxhitemnum);
					}
					SearchPage();
				});
				 
				//查找页面
				function SearchPage(){
					$(".wxhpage").empty();
					$(".wxhpage").removeClass("allserviceresult1");
					$(".wxhpage").addClass("searchresult1");
					 wxhallnum = TheSearData.length;
					 //alert(wxhallnum);
					 wxhpagenum = Math.ceil(wxhallnum /wxhitemnum);
					 //alert(wxhpagenum);
					 $(".searchresult1").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-left'></i></a></li>");				
						for(var i = 1; i<=wxhpagenum;i++){
							if(i == 1){
								$(".searchresult1").append("<li class='changenum1 active'><a class=" + i + ">" + i + "</a></li>");
							}else{
								$(".searchresult1").append("<li class='changenum1'><a class=" + i + ">" + i + "</a></li>");
							}
							
						}
						$(".searchresult1").append("<li ><a href='javascript:void(0);'><i class='fa fa-chevron-right'></i></a></li>");
												
						//搜索服务：点击页码翻页
						$(".searchresult1 .changenum1").click(function(){
							$(".searchresult1 li").removeClass("active");
							var currentpage = $(this).find("a").html();
							$(this).addClass("active");

							var num = parseInt($("#itemnumber").val());	
							var start = parseInt(num*(currentpage-1));
							var minus = parseInt(wxhallnum-start);
							//alert("minus:"+minus);
							if(minus > num){
								var end = parseInt(start+num);
								//alert(end);
								append(TheSearData,start,end);
							}else{
								append(TheSearData,start,wxhallnum);
							}
						});
						
						//搜索服务：点击上一页
						$(".searchresult1 .fa-chevron-left").parent().click(function(){
							var currentpage = $(".searchresult1 .active").find("a").html();
							
							if(currentpage > 1){
								currentpage = Number(currentpage)-1;
								$(".searchresult1 .active").removeClass('active');
								var currentpage_s = "." + currentpage;
								$(currentpage_s).parent().addClass("active");
								
								var num = parseInt($("#itemnumber").val());	
								var start = parseInt(num*(currentpage-1));
								var end = parseInt(start+num);
								
								append(TheSearData,start,end);
							}							
						});
						
						//搜索服务：点击下一页
						$(".searchresult1 .fa-chevron-right").parent().click(function(){
							
							//alert("a");
							var currentpage = $(".searchresult1 .active").find("a").html();
							
							if(currentpage < wxhpagenum){
								currentpage = Number(currentpage)+1;
								$(".searchresult1 .active").removeClass('active');
								var currentpage_s = "." + currentpage;
								$(currentpage_s).parent().addClass("active");

								var num = parseInt($("#itemnumber").val());	
								var start = parseInt(num*(currentpage-1));
								var minus = parseInt(wxhallnum-start);
								//alert("minus:"+minus);
								if(minus > num){
									var end = parseInt(start+num);
									//alert(end);
									append(TheSearData,start,end);
								}else{
									append(TheSearData,start,wxhallnum);
								}
							}
						});
				}
				
				//当每页显示个数变化之后
				$("#itemnumber").change(function(){
					wxhitemnum = $("#itemnumber").val();
					//alert(wxhitemnum);

					//判断搜.pagination包含的是allserviceresult还是searchresult，是searchresult则向filterserviceservlet请求数据，否则向servicelistpageup请求数据
					var searchtype = $("#search-type").val();
					var searchkey = $("#searchvalue").val();
					//请求全部数据
					if($(".wxhpage").hasClass("allserviceresult1")){
						if(TheBigData.length < wxhitemnum)
						{
							append(TheBigData,0,TheBigData.length);
						}else{
							append(TheBigData,0,wxhitemnum);
						}	
						Allpage();
					
					}else{
						if(TheSearData.length < wxhitemnum)
					    {
							//alert("4");
							append(TheSearData,0,TheSearData.length);
						}
						else{
							//alert("10");
							append(TheSearData,0,wxhitemnum);
						}
						SearchPage();
					}
				});
					
					//公共添加函数
				 function append(data,start,end){
					//data=JSON.parse(data);
					//alert(data);
					//alert(start);
					//alert(num);
					$("#tab1-tbody").empty();
					for(var i=start;i<end;i++)
						{	
						//alert(i);
						//alert(data[i].download_address);
						//<img src='img/logo-splash.png' alt=''>
						
						var txtHtml="";
						txtHtml += "<tr><td class='Address'><input type='radio' name='Choose' value='"+data[i].download_address+"'/></td>";  //第1列  单选框
						if(data[i].stype != ""){
							txtHtml += "<td class='type'>"+data[i].stype+"</td>";  //第2列  服务类型
						}else{
							txtHtml += "<td class='type'>"+data[i].ftype+"</td>";  //第2列  服务类型
						}
						txtHtml += "<td class='icon'><img src='"+data[i].icon+"' style='height:35px;'></td>";  // icon
						txtHtml += "<td class='name'>"+data[i].name+"</td>";  //第3列  服务名称
						txtHtml += "<td class='varsion'>"+data[i].version+"</td>";  //第4列  版本
						txtHtml += "<td class='system'>"+data[i].system+"</td>";  //第5列  所属系统
						txtHtml += "<td class='businessType'>"+data[i].business_type+"</td>";  //第6列  业务类型
						txtHtml += "<td class='keyword'>"+data[i].keyword+"</td>";  //第7列  关键字
						var des="";
						if(data[i].description.length > 38)
						{
							des=data[i].description.substring(0, 36);
							des = des +"......";
						}else
						{
							des = data[i].description;
						}
						
						txtHtml += "<td class='description' name='"+data[i].description+"'>"+des+"</td>";  //第8列  描述信息
						txtHtml += "<td style='display:none;' class='id'>"+data[i].id+"</td>";  // ID
						txtHtml += "<td style='display:none;' class='releaseTime'>"+data[i].release_time+"</td>";  // release_time
						txtHtml += "<td class='developer'>"+data[i].developer+"</td>";  //第10列  开发者
						txtHtml += "<td style='display:none;' class='ftype'>"+data[i].ftype+"</td>";  // ID
						txtHtml += "<td style='display:none;' class='stype'>"+data[i].stype+"</td></tr>";  // ID
						
						$("#tab1-tbody").append(txtHtml);
						$("#wid-id-1").removeAttr("style");
						
						}
					
					$('input[name=Choose]').click(function(){
						
						var wxhnodeId = $('input[name=Choose]:checked').parent().parent().find(".id").text();
						//alert(wxhnodeId);
						$.post("nodeAvaileble",{
							ID : wxhnodeId
						},function(data){
							if(data=="true"){								
								alert("此服务已存在,不能部署!");
							}
						});
					});
					
					$("#tab1-tbody").find(".description").mouseover(function(e){
						//alert("a");
						var tip = "<div id='windowTip' style='display:none;text-align:left;'>"+$(this).attr("name")+"</div>";
						$(this).append(tip);
						$("#windowTip").show(600);
					}).mouseout(function(){
						$("#windowTip").remove();
					})
					
					
					;
					
				 }
									
					//转变搜索类型，动态转化html样式
					$("#search-type").change(function(){
						var searchtype = $("#search-type").val();
						if(searchtype == "servicename" || searchtype == "description"){
							$(".filtertype").empty();
							$(".filtertype").append("<input type='text' class='input-sm' id='searchvalue'/>");
							
						}else{
							$(".filtertype").empty();
							$(".filtertype").append("<select class='form-control input-sm' id='searchvalue' style='font-size:14px;display: inline-block;width: 9.5%;'></select>");

						}
					});
				 
				 
				/*动态翻页*/

				  var $validator = $("#wizard-1").validate({
				    
				    rules: {
				    	CopyNum: {
				        required: true,
				        digits:true,
				        min: 1
				      },
				        ComplicatingNum: {
				        required: true,
				        digits:true,
				        min: 1
				      },				        
				      Choose: {
				        required: true
				      }
				    },
				    
				    messages: {
				    	CopyNum: {
				    		    required: "此项不能为空！",
				    		    min: "不能使用小于1的整数",
				    		    digits:"请输入整数！"
				    		   },
				    	ComplicatingNum:{
			    		    required: "此项不能为空！",
			    		    min: "不能使用小于1的整数",
			    		    digits:"请输入整数！"
			    		   },
		    		   Choose:{
		    		   	required: "请选择至少一项！"
		    		   }
				       
				    },
				    
				    highlight: function (element) {
				      $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
				    },
				    unhighlight: function (element) {
				      $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
				    },
				    errorElement: 'div',
				    errorClass: 'ErrorInfo',
				    errorPlacement: function (error, element) {
				      if ( element.is(":radio") )
				        error.insertAfter(element.parent().parent().parent().parent());
				    else error.insertAfter(element.parent());
				      
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
				    	  $("#tab3-table").empty();
							htmlInfo="";
							var $one = $('input[name=Choose]:checked').parent().parent();
							//alert("f:"+ftype);
							//alert("s"+stype);
							htmlInfo += "<tr id='submitTr'><td class='type'>"+$one.find(".type").text()+"</td>";   //第2行 服务类型
							htmlInfo += "<td class='name'>"+$one.find(".name").text()+"</td>";   //第3行 服务名称
							htmlInfo += "<td class='varsion'>"+$one.find(".varsion").text()+"</td>";   //第4行 版本
							htmlInfo += "<td class='system'>"+$one.find(".system").text()+"</td>";   //第5行 所属系统
							htmlInfo += "<td class='businessType'>"+$one.find(".businessType").text()+"</td>";   //第6行 业务类型
							htmlInfo += "<td class='keyword'>"+$one.find(".keyword").text()+"</td>";   //第7行 关键字
							htmlInfo += "<td class='description'>"+$one.find(".description").text()+"</td>";   //第8行 描述信息
							htmlInfo += "<td style='display:none;' class='id'>"+$one.find(".id").text()+"</td>";   //第9行 ID
							htmlInfo += "<td class='developer'>"+$one.find(".developer").text()+"</td>";   //第10行 开发者
							htmlInfo += "<td style='display:none;' class='icon'>"+$one.find(".icon").text()+"</td>";   //第9行 icon
							htmlInfo += "<td style='display:none;' class='releaseTime'>"+$one.find(".releaseTime").text()+"</td>";   //第9行 icon
							
							htmlInfo += "<td class='copynum'>"+$("#CopyNum").val()+"</td>";   //副本数量
							htmlInfo += "<td style='text-align:left;'><div class='tree smart-form' style='margin-top:3px;'><ul><li><span><i class='fa fa-lg fa-plus-circle'></i>"
								        +$one.find(".name").text()+"</span><ul>";
							for(var i=0;i<$('input[name=checkbox]:checked').length;i++)
								{
								htmlInfo += "<li style='display:none'><span name='leaf'><i class='icon-leaf'></i>"
									 		+$('input[name=checkbox]:checked').eq(i).parent().parent().parent().parent().parent().next().next().text()
									 		+"</span></li>";
								}
							htmlInfo += "</ul></li></ul></div></td></tr>"; 
							//alert(htmlInfo);
							$("#tab3-table").append(htmlInfo);
							$("#wid-id-3").removeAttr("style");
							
							//TreeView	
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
				      }
                     
					//tab1下一步事件
					if($("#tab1").hasClass("active")){
						var $one = $('input[name=Choose]:checked').parent().parent();
						ftype = $one.find(".ftype").text();
						stype = $one.find(".stype").text();
						nodeId = $one.find(".id").text();
						//$("#autoDeply").attr('checked',true);
						$("#wid-id-2").attr('style','display:none;');
						
						 //判断Ftype和Stype
					    if(ftype == "可执行程序/服务端"){
					    	nodetype = "exe";
					    	//alert("type:"+type);
					    }
					    else
					    {
					    	if(stype == "Servlet")
					    	{
					    		
					    		nodetype = "Restful";
					    	//alert("type:"+type);
					    	}
					    	else if(stype == "IIS")
					    		{
					    		nodetype = "Web Site";
					    		}
					    	else if(stype == "Axis2")
					    		{
					    		nodetype = "Web Service";
					    		}		    	
					    }
					}
				    },
				    onPrevious:function (tab, navigation, index) {
				    	
				    	if($("#tab2").hasClass("active")){
				    		//alert("a");
				    		$("#manualDeply").removeAttr('checked');
							$("#autoDeply").removeAttr('checked');
							
							
				    	}
				    },
				    onTabClick: function (tab, navigation, index) { 
                    alert('on tab click disabled');
                    return false;
				    }                  
				  });
				  
				
				//弹出框
				  $(".page1").dialog({
						autoOpen : false,
						modal : true,
						/*title : */	
					});
				  
				  
				$('#Submit').click(function(){
					$(".page1").dialog("open");
					//进度条  
				    var iNow = new Date().setTime(new Date().getTime()); // now plus 5 secs
				    var iEnd = new Date().setTime(new Date().getTime() + 80 * 1000); // now plus 15 secs
				    $('#progress2').anim_progressbar({start: iNow, finish: iEnd, interval: 100});
					  
				    //var flag1="false";
				    $(".progressDeployStatus").text('清空原文件');
					  $.post("servlet/clearDownloadServlet",null,function(response){});
					  
					  var downloadurl=$("input[name='Choose']:checked").val();
                  		if(downloadurl!=null){                  		
                  		//window.open(downloadurl);
                  		//if(flag1=="false"){
                  			$(".progressDeployStatus").text('下载文件');
//                  			$.post("servlet/downloadServlet?url="+downloadurl,function(response){
                  			$.post("servlet/downloadServlet",{
                  				url : downloadurl,
                  				ID : nodeId
                  				},function(response){
                  			    //
                  				//flag1="true";
                  				$(".progressDeployStatus").text('获取文件详情');
                  				$.post("servlet/getStoreDetailServlet",null,function(response){ 
                  					
                  					$(".progressDeployStatus").text('解压文件');
                  					$.post("servlet/unZipStoreDetailServlet",null,function(response){
                  						path=response;
                  		
                  						$(".progressDeployStatus").text('发送Ajax请求');
                  					  $.post("servlet/sendMessageInAjax",{
                  						  ID:nodeId
                  					  },function(response){                  							
                  							var RelyStr =  response;
                  							//$(".submit_form").submit();
                  						$(".progressDeployStatus").text('提交中');
                  						    dosubmit(RelyStr);
                  						  	//submit();
                  							
                  						});
                  							
                  					});               					
                  				});
                  			});
                  			
                  		}	
                  	                  		                  		                  		                  		                  		                  		
					});
				
				
				 /*
				 * 最终提交
				 */
				
				 
				
				function dosubmit (RelyStr) {
					alert(RelyStr);
					//拼IP串 
				    $("span[name='leaf']").each(function(){
				    	//alert($(this).text());	
				    	ip += $(this).text();	
				    	ip += ";";
				    });
				    ip=ip.substring(0, ip.length-1);
				   // alert(ip);
				    
				    //判断Ftype和Stype
				    if(ftype == "可执行程序/服务端"){
				    	type = "exe";
				    	//alert("type:"+type);
				    }
				    else
				    {
				    	if(stype == "Servlet")
				    	{
				    		
				    	type = "webapp";
				    	//alert("type:"+type);
				    	}
				    	else if(stype == "IIS")
				    		{
				    		type = "Web Site";
				    		}
				    	else if(stype == "Axis2")
				    		{
				    		type = "Web Service";
				    		}else	type="2";		    	
				    }
				    
				    //alert("type1:"+type);
				    var $abc = $("#submitTr");			    
				    //alert("before submit");
				    $(".submit_form").ajaxSubmit({
						 
	                    type: "post",
	                    url: "servicedeployservlet",
	                    data:{
	                    	servicename : $abc.find(".name").text(),  //名字
	                    	servicetype : type,   //类型
	                    	servicedescription : $abc.find(".description").text(),  // 描述
	                    	developer : $abc.find(".developer").text(),  //开发者
	                    	paranum : "123",
                    		paralist : "234",
                			copynum : $abc.find(".copynum").text(),
                			businesstype : $abc.find(".businessType").text(),
                			filePath : path,
                			keywords : $abc.find(".keyword").text(),
                			system : $abc.find(".system").text(),
                			concurrency : $("#ComplicatingNum").val(),
            				version : $abc.find(".varsion").text(),
            				serviceid : $abc.find(".id").text(),
            				ip : ip,
            				icon : $abc.find(".icon").text(),
            				releaseTime : $abc.find(".releaseTime").text(),
            				RelyOnInfo : RelyStr
	                    },
	                    success: function (response) {
	                    	//alert(response);
	                    	$.post("UnifyAddressID",{
  								ID:nodeId
  							},function(data){
  								//alert(data);
  								var datainfo = data.split("@");
  								
  								$.post("UnifyAddressXML",{
  									ID:datainfo[0],
  									Type:datainfo[1],
  									Port:datainfo[2],	
  								},function(data){
  									$(".progressDeployStatus").text('部署完成');
  									clearInterval(vInterval);
  		                            $(vPb).children('.percent').html('<b>100%</b>');
  		                            $(vPb).children('.elapsed').html('Finished');
  		                            $(vPb).children('.pbar').children('.ui-progressbar-value').css('width', '100%');
  								});	
  							});
	                    }
	                });	
				    //alert("no");
				   
				    
	            };
					
				
				//该函数完成自动部署与手动部署的切换
				$("#autoDeply").click(function(){
					/*var str="";*/
					$("input[name='checkbox']").attr('disabled',true);
					/*str += $(this).val();
					alert(str);*/
					$(".checkbox").addClass("state-disabled");
					$("#wid-id-2").attr('style','display:none;');
				});

			
				
				//第二页的添加表
				$("#manualDeply").click(function(){
					//alert(nodetype);
					//全部节点：显示全部节点的页码
					$.ajax({
						type:"post",
						url:"getavaliblenodenum",
						data:{servicetype:nodetype},
						success:function(data){
							
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

								
								var startnum = itemnumber*(currentpage-1);
								
								$.ajax({
									type:"post",
									url:"hostlistservlet",
									data:{startnumber:startnum,itemnumber:itemnumber,servicetype:type},
									success:function(data){
										showtab2table(data);
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
									
										
									var startnum = itemnumber*(currentpage-1);
									
									$.ajax({
										type:"post",
										url:"hostlistservlet",
										data:{startnumber:startnum,itemnumber:itemnumber,servicetype:type},
										success:function(data){
											showtab2table(data);
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

									
									var startnum = itemnumber*(currentpage-1);
									
									$.ajax({
										type:"post",
										url:"hostlistservlet",
										data:{startnumber:startnum,itemnumber:itemnumber,servicetype:type},
										success:function(data){
											showtab2table(data);
										}
									});
								}
							});
						}
					});
					$.post("hostlistservlet",{
						startnumber : 0,
						itemnumber : itemnumber,
						servicetype : type
					},function(data){
						showtab2table(data);
						});
					
					$("input[name='checkbox']").attr('disabled',false);
					$(".checkbox").removeClass("state-disabled");
					$("#wid-id-2").removeAttr('style');
					
					
				});
						
				//该函数完成部署数量填写与选取的对应
				var num;
				$("#CopyNum").blur(function(){
					num=$("#CopyNum").val();
					//alert(num);
				});
				
	
	function showtab2table(data){
		data=JSON.parse(data);
		$("#tab2-tbody").empty();
		for(var i=0;i<data.hostlist.length;i++){
			var txtHtml="";
			//alert("num:"+parseFloat(data.hostlist[i].cpu.split("%")[0]));
			var cpuStr = parseFloat(data.hostlist[i].cpu.split("%")[0])+","+(100-parseFloat(data.hostlist[i].cpu.split("%")[0]));
			//alert("cpuStr"+cpuStr);
			var memoryStr = parseFloat(data.hostlist[i].memory.split("%")[0])+","+(100-parseFloat(data.hostlist[i].memory.split("%")[0]));
			var harddiskStr = parseFloat(data.hostlist[i].harddisk.split("%")[0])+","+(100-parseFloat(data.hostlist[i].harddisk.split("%")[0]));
			
			txtHtml += "<tr><td><div class='smart-form'><div class='row'><div class='col col-4' style='margin-left: 35%;'><label class='checkbox'><input type='checkbox' name='checkbox'/><i></i></label></div></div></div></td>";  //第1列  多选框
			txtHtml += "<td>"+data.hostlist[i].hostname+"</td>";  //第2列  节点名称
			txtHtml += "<td style='text-align:center;'>"+data.hostlist[i].hostip+"</td>";  //第3列  节点IP
			txtHtml += '<td>'+'<span class="sparkline" data-sparkline-type="pie" data-sparkline-offset="90" data-sparkline-piesize="21px">'+cpuStr+'</span>'+'</td>';  //第4列  CPU信息
			txtHtml += '<td>'+'<span class="sparkline" data-sparkline-type="pie" data-sparkline-offset="90" data-sparkline-piesize="21px">'+memoryStr+'</span>'+'</td>';  //第5列  内存信息
			txtHtml += '<td>'+'<span class="sparkline" data-sparkline-type="pie" data-sparkline-offset="90" data-sparkline-piesize="21px">'+harddiskStr+'</span>'+'</td>';  //第6列  硬盘信息
			txtHtml += "<td>"+data.hostlist[i].serviceNum+"</td></tr>";  //第7列  已部署服务数量
			
			$("#tab2-tbody").append(txtHtml);
			
		}
		
		$("input[name='checkbox']").click(function(){
			//alert($('input[name=checkbox]:checked').length);
			if($('input[name=checkbox]:checked').length>num){
				$(this).attr('checked',false);
			}	
		});
		
		pageSetUp();
	}
	
	jQuery.fn.anim_progressbar = function (aOptions) {
        // def values
        var iCms = 1000;
        var iMms = 60 * iCms;
        var iHms = 3600 * iCms;
        var iDms = 24 * 3600 * iCms;

        // def options
        var aDefOpts = {
            start: new Date(), // now
            finish: new Date().setTime(new Date().getTime() + 60 * iCms), // now + 60 sec
            interval: 100
        }
        var aOpts = jQuery.extend(aDefOpts, aOptions);
        vPb = this;

        // each progress bar
        return this.each(
            function() {
                var iDuration = aOpts.finish - aOpts.start;

                // calling original progressbar
                $(vPb).children('.pbar').progressbar();

                // looping process
                vInterval = setInterval(
                    function(){
                        var iLeftMs = aOpts.finish - new Date(); // left time in MS
                        var iElapsedMs = new Date() - aOpts.start, // elapsed time in MS
                            iDays = parseInt(iLeftMs / iDms), // elapsed days
                            iHours = parseInt((iLeftMs - (iDays * iDms)) / iHms), // elapsed hours
                            iMin = parseInt((iLeftMs - (iDays * iDms) - (iHours * iHms)) / iMms), // elapsed minutes
                            iSec = parseInt((iLeftMs - (iDays * iDms) - (iMin * iMms) - (iHours * iHms)) / iCms), // elapsed seconds
                            iPerc = (iElapsedMs > 0) ? iElapsedMs / iDuration * 100 : 0; // percentages

                        // display current positions and progress
                        $(vPb).children('.percent').html('<b>'+iPerc.toFixed(1)+'%</b>');
                        $(vPb).children('.elapsed').html(iMin+'m:'+iSec+'s</b>');
                        $(vPb).children('.pbar').children('.ui-progressbar-value').css('width', iPerc+'%');

                       
                    } ,aOpts.interval
                );
            }
        );
    }
	
//	$("#ui-id-2").next().click(function(){
//		location.reload();
//	}
				
				
});