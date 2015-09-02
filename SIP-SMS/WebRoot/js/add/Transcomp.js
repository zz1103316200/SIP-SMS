$(document).ready(function(){
	
	//ѡ����������	
	$("#search-type").change(function(){
		//alert("sdf");
		var searchtype = $("#search-type").val();
		
		$(".filtertype").empty();
		$(".filtertype").append("<input type='text' class='input-sm' id='searchvalue'/>");
			
	});
	
	//�������������ʽ
	$.ajax({
		type:"post",
		url:"servlet/InputTypeList",
		success:function(data){		
			$("#inputformat").append("<option></option>");
			for(var i=0;i<data.length;i++){
				
				$("#inputformat").append("<option value='" + data[i]+"'>"+ data[i]+"</option>");
			}
		}
	});
	$("#inputformat").blur(function(){
		var inputformat = $("#inputformat").val();
		$("#outputformat").val("");
		if(inputformat == ""){
			
		}else{
			$.ajax({
				type:"post",
				url:"servlet/OutputTypeList",
				data:{inputType:inputformat},
				success:function(data){
					//alert(data);
					$("#outputformat").empty();
					$("#outputformat").append("<option></option>");
					for(var i=0;i<data.length;i++){
						
						$("#outputformat").append("<option value='" + data[i]+"'>"+ data[i]+"</option>");
					}
				}
			});
		}
	});
	
	
	//��Ϣ��״�ؼ�

	
	//�޸ĵ�����
	$(".page1").dialog({
		autoOpen : false,
		modal : true,
		title : "修改"
	});	

	//������������
	$(".page2").dialog({
		autoOpen : false,
		modal : true,
		title : "添加转换组件"
	});	
	$("#AddComp").click(function(){
		$(".page2").dialog("open");
	});
	

    /*
	 * ʵ����ݷ�ҳ����-wenyanqi
	 */
	//���ȫ�ֱ���
	//��ȡ��ǰһҳ��ʾ��������¼
	var itemnumber = $("#itemnumber").val();
	//һ���ж���ҳ
	var pagenum;
	//һ���ж�������¼
	var allitemnumber;
	
	//ȫ���ڵ㣺��ʾȫ���ڵ��ҳ��
	$.ajax({
		type:"post",
		url:"servlet/TransformerOfNum",
		success:function(data){
		//alert("pagenum="+data.transformerNum);
			allitemnumber = data.transformerNum;
			//alert(allitemnumber);
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
			//ȫ���ڵ㣺�����ҳ֮��
			$(".allnoderesult .changenum").click(function(){
				//alert("sdf");
				$(".allnoderesult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"servlet/TransformerList",
					data:{startNum:startnum,pageNum:itemnumber},
					success:function(data){
						NodeList(data);
					}
				});
			});

			//ȫ���ڵ㣺�����һҳ
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
						url:"servlet/TransformerList",
						data:{startNum:startnum,pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
				
			});
			//ȫ���ڵ㣺�����һҳ
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
						url:"servlet/TransformerList",
						data:{startNum:startnum,pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
			});
		}
	});
	
	//���ص�һҳ���
	$.ajax({
		type:"post",
		url:"servlet/TransformerList",
		data:{startNum:0,pageNum:itemnumber},
		success:function(data){
			NodeList(data);
		}
	});

	/*******************��������֮��Ľڵ���ʾ***************************/
	//�����ڵ㣺 �������ͼ��֮��
	$("#searchservice").click(function(){
		var searchtype = $("#search-type").val();
		var searchkey = $("#searchvalue").val();
		
		$(".pagination").empty();
		$(".pagination").removeClass("allnoderesult");
		$(".pagination").addClass("searchresult");
		//1 ������ڵ�������ҳ��
		$.ajax({
			type:"post",
			url:"servlet/TransformerSearchNum",
			data:{style:searchtype,value:searchkey},
			success:function(data){
				//alert(data);
				allitemnumber = data.transformerSearchNum;
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
				//�����ڵ㣺�����ҳ֮��
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
						url:"servlet/TransformerSearch",
						data:{style : searchtype,
							  value : searchkey,
							  startNum : startnum,
							  pageNum : itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				});

				//�����ڵ㣺�����һҳ
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
							url:"servlet/TransformerSearch",
							data:{style : searchtype,
							      value : searchkey,
							      startNum : startnum,
							      pageNum : itemnumber},
							success:function(data){
								NodeList(data);
							}
						});
					}
					
				});
				//�����ڵ㣺�����һҳ
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
							url:"servlet/TransformerSearch",
							data:{style : searchtype,
							      value : searchkey,
							      startNum : startnum,
							      pageNum : itemnumber},
							success:function(data){
								NodeList(data);
							}
						});
					}
				});
			}
		});
		//��ɷ�������ĵ�һҳ���
		$.ajax({
			type:"post",
			url: "servlet/TransformerSearch",
			data:{style:searchtype,
				  value:searchkey,
				  startNum:0,
				  pageNum:itemnumber},
			success: function(data){
				
				NodeList(data);
			}
		});
		
	});


	 /***************��������*********************/


	//��ÿҳ��ʾ����仯֮��
	$("#itemnumber").change(function(){
		itemnumber = $("#itemnumber").val();
		//�������ҳ��

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
		
		//�ж���.pagination�����allserviceresult����searchresult����searchresult����filterserviceservlet������ݣ�������servicelistpageup�������
		var searchtype = $("#search-type").val();
		var searchkey = $("#searchvalue").val();
		//����ȫ�����
		if($(".pagination").hasClass("allnoderesult")){
			//ȫ���ڵ㣺�����ҳ֮��
			$(".allnoderesult .changenum").click(function(){
				//alert("sdf");
				$(".allnoderesult li").removeClass("active");
				var currentpage = $(this).find("a").html();
				$(this).addClass("active");

				var itemnumber = $("#itemnumber").val();	
				var startnum = itemnumber*(currentpage-1);
				$.ajax({
					type:"post",
					url:"servlet/TransformerList",
					data:{startNum:startnum,pageNum:itemnumber},
					success:function(data){
						
						NodeList(data);
					}
				});
			});

			//ȫ�����񣺵����һҳ
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
						url:"servlet/TransformerList",
						data:{startNum:startnum,pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
				
			});
			//ȫ�����񣺵����һҳ
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
						url:"servlet/TransformerList",
						data:{startNum:startnum,pageNum:itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
			});
			$.ajax({
				type:"post",
				url:"servlet/TransformerList",
				data:{startNum:0,pageNum:itemnumber},
				success:function(data){
					//alert(data);
					NodeList(data);
				}
			});
		}else{
			//�������������
			//�����ڵ㣺�����ҳ֮��
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
					url:"servlet/TransformerSearch",
					data:{style : searchtype,
					      value : searchkey,
					      startNum : startnum,
					      pageNum : itemnumber},
					success:function(data){
						NodeList(data);
					}
				});
			});

			//�����ڵ㣺�����һҳ
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
						url:"servlet/TransformerSearch",
						data:{style : searchtype,
					          value : searchkey,
				     		  startNum : startnum,
				     		  pageNum : itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
				
			});
			//�������񣺵����һҳ
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
						url:"servlet/TransformerSearch",
						data:{style : searchtype,
					          value : searchkey,
				     		  startNum : startnum,
				     		  pageNum : itemnumber},
						success:function(data){
							NodeList(data);
						}
					});
				}
			});
			$.ajax({
				type:"post",
				url: "servlet/TransformerSearch",
				data:{style:searchtype,
				  	  value:searchkey,
				  	  startNum:0,
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
			
			txtHtml += "<tr><td class='compname'>"+data[i].TransfName+"</td>";
			txtHtml += "<td class='input'>"+data[i].InputType+"</td>";
			txtHtml += "<td class='output'>"+data[i].OutputType+"</td>";
			txtHtml += "<td class='compdes'>"+data[i].TransfDescription+"</td>";
			if(data[i].TransfStatus == "true")
			{
				txtHtml += "<td><form class='smart-form' style='width:30px;height:30px;'><label class='toggle'><input type='checkbox' name='checkbox-toggle' checked='checked'><i data-swchon-text='开' data-swchoff-text='关' class='compstatus'></i></label></form></td>";
			}else
			{
				txtHtml += "<td><form class='smart-form' style='width:30px;height:30px;'><label class='toggle'><input type='checkbox' name='checkbox-toggle'><i data-swchon-text='开' data-swchoff-text='关' class='compstatus'></i></label></form></td>";
			}
			txtHtml += "<td><a class='btn btn-primary editcomp btn-sm' ><i class='fa fa-gear' '></i>&nbsp;&nbsp;修改</a></td>";
			txtHtml += "<td><a class='btn btn-danger deletecomp btn-sm' ><i class='fa fa-times-circle'></i>&nbsp;&nbsp;卸载</a></td></tr>";

			$("#Comptable").append(txtHtml);
			
		}

			
			$(".editcomp").click(function(){

				$(".page1").dialog("open");
				var input = $(this).parent().parent().find(".input").html();
				var output = $(this).parent().parent().find(".output").html();
				var compname = $(this).parent().parent().find(".compname").html();
				var compdes = $(this).parent().parent().find(".compdes").html();
				
				$("#compName").val(compname);
				$("#compDes").val(compdes);
				$("#editSubmit").parent().attr("title",input);
				$("#editSubmit").parent().prev().attr("title",output);
				
			});
			//����ύ�޸�
			$("#editSubmit").click(function(){
				var compname = $("#compName").val();
				var compdes = $("#compDes").val();
				var input = $("#editSubmit").parent().attr("title");
				var output = $("#editSubmit").parent().prev().attr("title");
				
				$.ajax({
					type:"post",
					url:"servlet/TransformerEdit",
					data:{InputType:input,OutputType:output,TransfName:compname,TransfDescription:compdes},
					success:function(data){
						if(data=="success"){
							alert("修改成功！");
							location.reload();
						}else{
							alert("修改失败！");
						}
					}
				});
			});
		
			$(".deletecomp").click(function(){
				var input = $(this).parent().parent().find(".input").html();
				var output = $(this).parent().parent().find(".output").html();
				$.ajax({
					type:"post",
					url:'servlet/TransformerDestroy',
					data:{InputType:input,OutputType:output},
					success:function(data){
						
						if(data=="success"){
							alert("卸载成功！");
							location.reload();
						}else{
							alert("卸载失败！");
						}
					}
				})
			});
			
			//组件的开关
			$(".compstatus").click(function(){
				var aa = $(this).prev().attr("checked");
				
				if(aa=="checked"){
					status = "false";
				}else{
					status = "true";
				}
				var input = $(this).parent().parent().parent().parent().find(".input").html();
				var output = $(this).parent().parent().parent().parent().find(".output").html();
			
				$.ajax({
					type:"post",
					url:"servlet/TransformerStatusChange",
					data:{inputType:input,outputType:output,status:status},
					success:function(data){
					
						location.reload();
					}
				});
			});
	}

});