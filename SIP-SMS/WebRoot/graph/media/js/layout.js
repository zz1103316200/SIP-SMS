﻿	//var flowNameStr="";
	function closeComponentWindow(){
			$('#addComponent').window('close');
		}

	function closeAdapterWindow(){
			$('#addAdapter').window('close');
		}

//	<li>
//	<a href="LoadDistributionStrategy.html" style="font-family:  'Microsoft YaHei';font-size:16px; ">副本分配策略管理</a>
//	</li>
	
	/*
	 * 动态添加左边框的下拉菜单列表
	 */
	function creat_li(str){
		
			var aElement=document.createElement("a");
			
			aElement.href="#";
			
			aElement.innerHTML=str;
			
			aElement.className="flow_list_option";
			
			aElement.style="font-family:  'Microsoft YaHei';font-size:16px;";
	
			var liElement=document.createElement("li");
			liElement.appendChild(aElement);
			return liElement;
	}
	
	
	function creat_ul(){
			
			var ulElement=document.createElement("ul");
			ulElement.className="sub-menu";

			
			var a_liElement=creat_li("业务流程","icon-tasks");
	
			var b_liElement=creat_li("服务流程","icon-sort-by-order");
			ulElement.appendChild(a_liElement);
			ulElement.appendChild(b_liElement);
			return ulElement;
	}
	//创建Element：<a href="javascript:;"><i class="icon-cogs"></i><span class="title">Layouts</span></a>
	function creat_a(str){
	
			var iElement=document.createElement("i");
			iElement.className="fa fa-lg fa-fw fa-gear";
			var spanElement=document.createElement("span");
			spanElement.className="menu-item-parent";
			spanElement.setAttribute("style","font-family:  'Microsoft YaHei';font-size:16px;");
			spanElement.innerHTML=str;
			var aElement=document.createElement("a");
			aElement.href="javascript:;";
			//var a_liElement=creat_li("业务流程");
			//var b_liElement=creat_li("服务流程");
			aElement.appendChild(iElement);
			aElement.appendChild(spanElement);
			return aElement;
	}
	//得到li
	function add_ul_element(str){

			var liElement=creat_li(str);
			//alert(flowNameStr);
			//if(str==flowNameStr) liElement.className="active";
			//var aElement=creat_a(str);
			//liElement.appendChild(aElement);
			
			var parent_element=document.getElementById("ul_element_list");
			
			parent_element.appendChild(liElement);
	}
	
	$(document).ready(function() {       

		$.post("../servlet/zz_flowListServlet",{sel:"zz"},function(text){
			console.log(text);
		   /***	
				动态创建左边的树状菜单
			*/
			for(var i=0;i<text.length;i++){
				add_ul_element(text[i].name);
			}		
			
		});
		//给新添加的每个流程绑定点击事件
		$(document).on("click",".flow_list_option",function(){
			console.log($(this).parent().parent());
			//$(this).parent().parent().addClass("active");
			//$(this).parent().parent().siblings().removeClass("active");
			flowName=$(this).html();
			window.location.href="../servlet/showFlowServlet?flowName="+flowName;

		});

		 //点击保存时的弹框
		$(".page2").dialog({
				autoOpen : false,
				modal : true,
				/*title : */
		});
		//点击弹框里的确定按钮保存流程
		$("#submitFlow").click(function(){
				//console.log("hello");
			 var flowName = $("#innerFlowName").val();
			 var flowDescription = $("#innerFlowDescription").val();
			  $.post("../servlet/saveFlowServlet",{submitJson:JSON.stringify(submitJson),flowName:flowName,flowDescription:flowDescription},function(text){
		        	 //window.location.href="../ServiceDeploy.html";
	            }
	            );
		        submitJson='[]';
		        location.reload();
		});	
	   //App.init();
	});
