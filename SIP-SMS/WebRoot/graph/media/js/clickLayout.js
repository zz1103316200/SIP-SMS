	var flowNameStr;
//	function closeComponentWindow(){
//			$('#addComponent').window('close');
//		}
//
//	function closeAdapterWindow(){
//			$('#addAdapter').window('close');
//		}

	/*
	 * 鍔ㄦ�娣诲姞宸﹁竟妗嗙殑涓嬫媺鑿滃崟鍒楄〃
	 */
	function creat_li(str){
		
		var aElement=document.createElement("a");
		
		aElement.href="#";
		
		aElement.innerHTML=str;
		
		aElement.className="flow_list_option";
		
		aElement.style="font-family:  'Microsoft YaHei';font-size:16px;";

		//左侧栏选中
		var liElement=document.createElement("li");
		liElement.appendChild(aElement);
		if(str==flowNameStr) liElement.className="active";
		return liElement;
	}
	
	
//	function creat_ul(){
//			
//			var ulElement=document.createElement("ul");
//			ulElement.className="sub-menu";
//
//			
//			var a_liElement=creat_li("涓氬姟娴佺▼","icon-tasks");
//	
//			var b_liElement=creat_li("鏈嶅姟娴佺▼","icon-sort-by-order");
//			ulElement.appendChild(a_liElement);
//			ulElement.appendChild(b_liElement);
//			return ulElement;
//	}
	//鍒涘缓Element锛�a href="javascript:;"><i class="icon-cogs"></i><span class="title">Layouts</span></a>
//	function creat_a(str){
//	
//			var iElement=document.createElement("i");
//			iElement.className="fa fa-lg fa-fw fa-gear";
//			var spanElement=document.createElement("span");
//			spanElement.className="menu-item-parent";
//			spanElement.setAttribute("style","font-family:  'Microsoft YaHei';font-size:16px;");
//			spanElement.innerHTML=str;
//			var aElement=document.createElement("a");
//			aElement.href="javascript:;";
//			//var a_liElement=creat_li("涓氬姟娴佺▼");
//			//var b_liElement=creat_li("鏈嶅姟娴佺▼");
//			aElement.appendChild(iElement);
//			aElement.appendChild(spanElement);
//			return aElement;
//	}
	//寰楀埌li
	function add_ul_element(str){

			var liElement=creat_li(str);
			//alert(flowNameStr);
//			if(str==flowNameStr) liElement.className="active";
//			var aElement=creat_a(str);
//			liElement.appendChild(aElement);
			
			var parent_element=document.getElementById("ul_element_list");
			
			parent_element.appendChild(liElement);
	}
	
	$(document).ready(function() {       

		$.post("../servlet/zz_flowListServlet",{sel:"zz"},function(text){
			console.log(text);
		   /***	
				鍔ㄦ�鍒涘缓宸﹁竟鐨勬爲鐘惰彍鍗�
			*/
			for(var i=0;i<text.length;i++){
				add_ul_element(text[i].name);
			}		
			
		});
		//缁欐柊娣诲姞鐨勬瘡涓祦绋嬬粦瀹氱偣鍑讳簨浠�
		$(document).on("click",".flow_list_option",function(){
			console.log($(this).parent().parent());
			$(this).parent().parent().addClass("active");
			$(this).parent().parent().siblings().removeClass("active");
			flowName=$(this).html();
			window.location.href="../servlet/showFlowServlet?flowName="+flowName;

		});
		
		$("#startIcon").click(function(){
			if($(this).hasClass("txt-color-red")){
				$.post("../servlet/stopFlowServlet",{flowName:flowNameStr},function(text){
					
				});
			}
		});
		 //鐐瑰嚮淇濆瓨鏃剁殑寮规
		$(".page2").dialog({
				autoOpen : false,
				modal : true,
				/*title : */
		});
		//鐐瑰嚮寮规閲岀殑纭畾鎸夐挳淇濆瓨娴佺▼
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
