	
		//用一个变量设定File的id
		var text1;
		//totalJson 每一个控件拖出来 都放到totaoJson里面 
		var totalJson='[]';
		//submitJson 最后过滤过Json以后，把切实有用的Json控件传到后台
		var submitJson='[]';
        var evt; 
		var cell;
    	var editor;
		var graph;
    	var model;
    	var label;
    	var image;
    	var sidebar;
        //这个是弹出的框
		var showWindow;
		
		var showGraph;
		
		var showBackground;
		
		function n_add_type(){
	        var svg_container = document.getElementById("graphContainer").children[0];
	        //生成渐变
	        svg_container.appendChild(n_type_generator("component",'#000000','#27A9E3'));
	        svg_container.appendChild(n_type_generator("control",'#E02222','#E02222'));
	        svg_container.appendChild(n_type_generator("switch",'#FFB748','#FFB748'));
	    }
	    function n_processor(){
	        //检查是否已经添加了渐变
	        /*
	        if(!window.n_added_type){
	            n_add_type();
	            window.n_added_type = true
	        }
			*/
	        var main_container = document.getElementById("graphContainer").children[0].children[0].children[1];
	        //找到最后生成的方块的位置
	        var svg_container = main_container.children[main_container.children.length-4];
	        //从DOM中找到最后生成的方块的位置名字
	        var name = main_container.children[main_container.children.length-3].children[0].children[0].children[0].children[0].children[0].children[0].children[0].innerHTML;
	        if(name == 'HTTP'||name == 'FTP'||name == 'JDBC'||name == 'File'||name == 'JMS'||name == 'SFTP'||name == 'SMTP'||name == 'SSL'||name == 'Servlet'||name == 'TCP'||name == 'UDP'||name == 'Java'){
	            //svg_container.children[0].setAttribute("fill","url(#mx-gradient-"+"component"+")");
	            svg_container.children[0].setAttribute("fill","#27A9E3");
	        }else if(name == 'JavaToJson'||name == 'JsonToJava'||name == 'JavaToXml'||name == 'XmlToJava'||name == 'JavaToJms'||name == 'JmsToJava'||name == 'JavaToHttp'||name == 'HttpToJava'||name == 'JavaToString'||name == 'StringToJava'||name == 'FileToByteArray'||name == 'FileToString'||name == 'ByteArrayToString'||name == 'XSLT'){
	            //svg_container.children[0].setAttribute("fill","url(#mx-gradient-"+"component"+")");
	            svg_container.children[0].setAttribute("fill","#FFB748");
	        }else if(name == 'ALL'||name == 'Choice'||name == 'FirstSuccessful'||name == 'Splitter'||name == 'CollectionAggregator'||name == 'MessageChunkAggregator'){
	        	svg_container.children[0].setAttribute("fill","#E02222");
	        }
	    }
	    function n_type_generator(name,top_color,bottom_color){
	        var elem_lg = document.createElementNS("http://www.w3.org/2000/svg","linearGradient");
	        elem_lg.setAttribute("id","mx-gradient-" + name);
	        elem_lg.setAttribute("x1","0%");
	        elem_lg.setAttribute("y1","0%");
	        elem_lg.setAttribute("x2","0%");
	        elem_lg.setAttribute("y2","100%");
	        var elem_stop_top = document.createElementNS("http://www.w3.org/2000/svg","stop");
	        elem_stop_top.setAttribute("style","stop-color:"+top_color);
	        elem_stop_top.setAttribute("offset", "0%");
	        elem_lg.appendChild(elem_stop_top);
	        var elem_stop_bottom = document.createElementNS("http://www.w3.org/2000/svg","stop");
	        elem_stop_bottom.setAttribute("style","stop-color:"+bottom_color);
	        elem_stop_bottom.setAttribute("offset", "100%");
	        elem_lg.appendChild(elem_stop_bottom);
	        return elem_lg;
	    }		
		
		function callback(msg)
		{
			document.getElementById("file1").outerHTML = document.getElementById("file1").outerHTML;
			document.getElementById("msg").innerHTML = "<font color='red'>"+msg+"</font>";
		}
		//处理字符串，得到组件类型
	    function getCellType(fullStr){
			var strValue=fullStr;
			var start=strValue.indexOf('">')+2;//返回数字
            var end=strValue.indexOf('</');
            var realValue=strValue.substring(start,end);
			return realValue+"";
		}
	  //处理字符串，得到组件类型
	    function getCellTypeFromImage(fullStr){
			var strValue=fullStr;
			var start=strValue.indexOf('business/')+9;//返回数字
            var end=strValue.indexOf('.png');
            var realValue=strValue.substring(start,end);
			return realValue+"";
		}		
		//这个是创造元素，
		//根据页面所给ID来得到json的ID
		function getElement(id){
			var elementId=id;
			totalJson=eval(totalJson);
			for(var i=0;i<totalJson.length;i++){
				
				if(totalJson[i].id==elementId){
					return i+"";
				}
			}
			return "";
			
		}

		//双击图标弹出的框
		function createElements(div,yuanlai,fileId){
			var type=getCellType(yuanlai).split(",")[0];
			var compType=getCellType(yuanlai).split(",")[1];
			var index=getElement(fileId);

			//一般情况下不会new 而是load 	
				if(index == ""){
				        //new 控件
					//alert("error!");
				}else{  
				        totalJson=eval(totalJson);

						
						if(compType=="1"){
							if(type=="Choice"){
								
							}else if(type=="Resequence"){
							    //createResequenceElements(div,yuanlai,fileId);
							}else if(type=="Splitter"){
							    createSplitterElements(div,yuanlai,fileId);
							}else if(type=="Aggregator"){
							    createAggregatorElements(div,yuanlai,fileId);
							}else if(type=="Option"){
							    createOptionElements(div,yuanlai,fileId);
							}
						}else if(compType=="2"){
//							$.post("../servlet/getInterOfSerServlet",{name:type},function(text){
//							console.log(text);
//							var str="<form  class='form-horizontal' style='width:80%;margin-left:10%;font-size:16px;font-family:'Microsoft YaHei''>";
//							str+="<div class='form-group'><label class='control-label col-md-5'>服务名:</label><div class='col-md-6'><input type='text' disabled='true' class='form-control' id='name"+fileId+"' value='"+text.serviceName+"'   /></input></div></div>";
//							str+= "<div class='form-group'><label class='control-label col-md-5'>接口:</label><div class='col-md-6'><select class='form-control input-sm  col-md-6' style='font-size:14px' id='interfaceName"+fileId+"'>";
//							
//							for(var optionvalue in text.interfaceName){
//								console.log(text.interfaceName[optionvalue].interfaceName);
//								str+= "<option value='" + text.interfaceName[optionvalue].name +"'>" + text.interfaceName[optionvalue].name + "</option>";
//							}
//							str+= "</select></div></div>";
//							str+="<div class='form-group'><label class='control-label col-md-5'></label><div class=' col-md-6'><button type='button' style='color:green;' class='btn btn-success' onclick=Submit("+fileId+",'-1')>确定</button></div></div></form>";
//							//console.log(str);
//							div.innerHTML=str;
//						});
						}else if(compType=="3"){
							$.post("../servlet/getProtocolPropertiesServlet",{name:type},function(text){
								var str="<form  class='form-horizontal' style='width:80%;margin-left:10%;font-size:16px;font-family:'Microsoft YaHei';'>";
								
								console.log(text.length);
								for(var i=0;i<text.length;i++){
									
									if(text[i].type=="input"){
										str+="<div class='form-group'><label class='control-label col-md-5'>"+text[i].name+"</label><div class='col-md-6'><input type='text' class='form-control' id='property"+(i+1)+fileId+"' value='"+totalJson[index]["property"+(i+1)]+"'   /></input></div></div>";
									}else if(text[i].type=="select"){
										//alert(text[i].option.length);
										//str+="<div class='control-group'><label class='control-label'>模式:</label><div class='controls'><select id='property"+(i+1)+fileId+"' ><option value='request-response' selected>request-response</option><option value='one-way'>one-way</option></select></div></div>";
										var optionValues = text[i].option.split(",");
										str+= "<div class='form-group'><label class='control-label col-md-5'>"+text[i].name+":</label><div class='col-md-6'><select class='form-control input-sm  col-md-6' style='font-size:14px' id='property" +(i+1)+fileId+"'>";

										for(var optionvalue in optionValues){
											str+= "<option value='" + optionValues[optionvalue] +"'>" + optionValues[optionvalue] + "</option>";
										}
										str+= "</select></div></div>";
									}
									
								}
								str+="<div class='form-group'><label class='control-label col-md-5'></label><div class=' col-md-6'><button type='button' style='color:green;' class='btn btn-success' onclick=Submit("+fileId+","+text.length+")>确定</button></div></div></form>";
								div.innerHTML=str;
							});
							
						}				    
				}
				
		}
		
		function createSplitterElements(div,yuanlai,fileId){
			var index=getElement(fileId);
			//alert("index:"+index)
			totalJson=eval(totalJson);
			var str="<form  class='form-horizontal' style='width:80%;margin-left:10%;font-size:16px;font-family:'Microsoft YaHei';'>";

			str+="<div class='form-group'><label class='control-label col-md-5'>expression:</label><div class='col-md-6'><input type='text' class='form-control' id='expression"+fileId+"' value='"+totalJson[index].expression+"'   /></input></div></div>";
					//alert(text[i].option.length);
					//str+="<div class='control-group'><label class='control-label'>模式:</label><div class='controls'><select id='property"+(i+1)+fileId+"' ><option value='request-response' selected>request-response</option><option value='one-way'>one-way</option></select></div></div>";
					
			str+="<div class='form-group'><label class='control-label col-md-5'></label><div class=' col-md-6'><button type='button' style='color:green;' class='btn btn-success' onclick=SplitterSubmit("+fileId+")>确定</button></div></div></form>";
			div.innerHTML=str;
		}
		
		function createAggregatorElements(div,yuanlai,fileId){
			var index=getElement(fileId);
			//alert("index:"+index)
			totalJson=eval(totalJson);
			var str="<form  class='form-horizontal' style='width:80%;margin-left:10%;font-size:16px;font-family:'Microsoft YaHei';'>";

			str+="<div class='form-group'><label class='control-label col-md-5'>timeout:</label><div class='col-md-6'><input type='text' class='form-control' id='timeout"+fileId+"' value='"+totalJson[index].timeout+"'   /></input></div></div>";
			str+="<div class='form-group'><label class='control-label col-md-5'>storePrefix:</label><div class='col-md-6'><input type='text' class='form-control' id='storePrefix"+fileId+"' value='"+totalJson[index].storePrefix+"'   /></input></div></div>";
					//alert(text[i].option.length);
					//str+="<div class='control-group'><label class='control-label'>模式:</label><div class='controls'><select id='property"+(i+1)+fileId+"' ><option value='request-response' selected>request-response</option><option value='one-way'>one-way</option></select></div></div>";
					
			str+="<div class='form-group'><label class='control-label col-md-5'></label><div class=' col-md-6'><button type='button' style='color:green;' class='btn btn-success' onclick=AggregatorSubmit("+fileId+")>确定</button></div></div></form>";
			div.innerHTML=str;
		}
		
		function createOptionElements(div,yuanlai,fileId){
			var index=getElement(fileId);
			//alert("index:"+index)
			totalJson=eval(totalJson);
			var str="<form  class='form-horizontal' style='width:80%;margin-left:10%;font-size:16px;font-family:'Microsoft YaHei';'>";

			str+="<div class='form-group'><label class='control-label col-md-5'>option:</label><div class='col-md-6'><input type='text' class='form-control' id='option"+fileId+"' value='"+totalJson[index].option+"'   /></input></div></div>";
					//alert(text[i].option.length);
					//str+="<div class='control-group'><label class='control-label'>模式:</label><div class='controls'><select id='property"+(i+1)+fileId+"' ><option value='request-response' selected>request-response</option><option value='one-way'>one-way</option></select></div></div>";
					
			str+="<div class='form-group'><label class='control-label col-md-5'></label><div class=' col-md-6'><button type='button' style='color:green;' class='btn btn-success' onclick=OptionSubmit("+fileId+")>确定</button></div></div></form>";
			div.innerHTML=str;
		}
		
		//直接可视化得到的event初始化为json
	    function initElementStart(id,strObj,obj){

	    	    //alert(strObj.compType);
	    	    var fileContent;
	    	    var realValue=strObj.realType;
	    	    var compType = strObj.compType;
	    	    if(strObj.compType=="1"){
					//fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+strObj.type+'","compType":"1" }]';					
	    	    	if(realValue=="Splitter"){
					   fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+realValue+'","expression":"'+strObj.expression+'","compType":"'+compType+'"}]';
				   }else if(realValue=="Aggregator"){
					   fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+realValue+'","storePrefix":"'+strObj.storePrefix+'","timeout":"'+strObj.timeout+'","compType":"'+compType+'"}]';
				   }else if(realValue=="Option"){
					   fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+realValue+'","option":"'+strObj.option+'","compType":"'+compType+'"}]';
				   }else{
					   fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+realValue+'","compType":"'+compType+'"}]';
				   }
	    	    }else if(strObj.compType=="2"){
	    	    	//12月之前fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+strObj.type+'","compType":"2","name":"'+strObj.name+'","interfaceName":"'+strObj.interfaceName+'"}]';
	    	    	fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+realValue+'","compType":"2" }]';
	    	    }else{
	    	    	fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+realValue+'","compType":"3","property1":"'+strObj.property1+'","property2":"'+strObj.property2+'","property3":"'+strObj.property3+'","property4":"'+strObj.property4+'", "property5":"'+strObj.property5+'","property6":"'+strObj.property6+'","property7":"'+strObj.property7+'","property8":"'+strObj.property8+'","property9":"'+strObj.property9+'","property10":"'+strObj.property10+'"}]';
	    	    }
	    	    var jsonFile= eval( fileContent );
				totalJson=eval(totalJson);
				totalJson[totalJson.length]=jsonFile[0];

		}
		
		//这个是拖放控件的时候
		function initElement(id,value,obj){
			//alert(id);
			var strValue=value+"";
				
			   var realValue=getCellType(strValue).split(",")[0];
			   var compType=getCellType(strValue).split(",")[1];
			   var fileContent;
			   if(compType=="1"){
				   //fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+realValue+'","compType":"'+compType+'"}]';
				   if(realValue=="Splitter"){
					   fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+realValue+'","expression":"","compType":"'+compType+'"}]';
				   }else if(realValue=="Aggregator"){
					   fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+realValue+'","storePrefix":"","timeout":"","compType":"'+compType+'"}]';
				   }else if(realValue=="Option"){
					   fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+realValue+'","option":"","compType":"'+compType+'"}]';
				   }else{
					   fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+realValue+'","compType":"'+compType+'"}]';
				   }
			   }else if(compType=="2"){
				   //12月之前fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+realValue+'","compType":"'+compType+'","name":"","interfaceName":""}]';
				   fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+realValue+'","compType":"'+compType+'"}]';
			   }else{
				   fileContent= '[{ "id": "'+id+'","type":"vertex","realType":"'+realValue+'","compType":"'+compType+'","property1":"","property2":"","property3":"","property4":"", "property5":"","property6":"","property7":"","property8":"","property9":"","property10":""}]';
			   }
				var jsonFile= eval( fileContent );
				totalJson=eval(totalJson);
				totalJson[totalJson.length]=jsonFile[0];

		}
		
		//当点击确定时触发 
		function Submit(fileId,num){
			var array = new Array(10);
			console.log(num);
			var index=getElement(fileId);
			if(num=="-1"){

				totalJson[index].name=document.getElementById("name"+fileId).value;
				totalJson[index].interfaceName=document.getElementById("interfaceName"+fileId).value;
			}else{
				for(var i=0;i<10;i++){
					array[i]="";
				}
				for(var i=0;i<num;i++){
					console.log("property"+(i+1)+fileId);
					if(document.getElementById("property"+(i+1)+fileId).value!=null){
						array[i]=document.getElementById("property"+(i+1)+fileId).value;
					}
				}
				
				totalJson[index].property1=array[0];
				totalJson[index].property2=array[1];
				totalJson[index].property3=array[2];
				totalJson[index].property4=array[3];
				totalJson[index].property5=array[4];
				totalJson[index].property6=array[5];
				totalJson[index].property7=array[6];
				totalJson[index].property8=array[7];
				totalJson[index].property9=array[8];
				totalJson[index].property10=array[9];
			}
			
			//alert(JSON.stringify(totalJson));
			showGraph.setEnabled(true);
			showWindow.destroy();
			mxEffects.fadeOut(showBackground, 50, true, 10, 30, true);
		}
		
		function SplitterSubmit(fileId){
			
			var echoName=document.getElementById("expression"+fileId).value;
		    var index=getElement(fileId);
			totalJson[index].expression=echoName;
			//alert(JSON.stringify(totalJson));
			
			
			showGraph.setEnabled(true);
			showWindow.destroy();
			mxEffects.fadeOut(showBackground, 50, true, 10, 30, true);
		}
		
		function AggregatorSubmit(fileId){
			
			var timeout=document.getElementById("timeout"+fileId).value;
			var storePrefix=document.getElementById("storePrefix"+fileId).value;
		    var index=getElement(fileId);
			totalJson[index].timeout=timeout;
			totalJson[index].storePrefix=storePrefix;
			//alert(JSON.stringify(totalJson));
			
			
			showGraph.setEnabled(true);
			showWindow.destroy();
			mxEffects.fadeOut(showBackground, 50, true, 10, 30, true);
		}
		
		function OptionSubmit(fileId){
			
			var option=document.getElementById("option"+fileId).value;
		    var index=getElement(fileId);
			totalJson[index].option=option;
			//alert(JSON.stringify(totalJson));
			
			
			showGraph.setEnabled(true);
			showWindow.destroy();
			mxEffects.fadeOut(showBackground, 50, true, 10, 30, true);
		}
		
		function changevalue(obj){
			document.getElementById("file2").value=obj.value;
		}
		
		function getFullPath(obj){
			
			if(obj)
			{
			//ie
				if (window.navigator.userAgent.indexOf("MSIE")>=1)
				{
					obj.select();
					return document.selection.createRange().text;
				}
			//firefox
				else if(window.navigator.userAgent.indexOf("Firefox")>=1)
				{
					if(obj.files)
					{
						return obj.files.item(0).getAsDataURL();
					}
					return obj.value;
				}
			return obj.value;
			
		}
		} 
		
		function getJavaPath(des){
			//alert(des.toString().substring(0,11));
			if(des.toString().substring(0,11) == "C:\\fakepath"){
				des=des.substring(11,des.length);
			}
			//alert("des:"+des);
			return des;
		}
		
		function javaSubmit(fileId){
		    
			//var javaName=document.getElementById("file1").value;
			//var fileinput = document.getElementById("file1");
			//alert(getFullPath(fileinput));
			
			//alert(document.getElementById('file1').value);
			//var file_upl = document.getElementById('file1'); 			
		    //alert(getFullPath(file_upl));
			var allName=document.getElementById("javaName"+fileId).value;
			var description1=document.getElementById("file"+fileId).value;
			//var fileName = document.getElementById('file1').value;
			//alert(description1);
			var javaPath =getJavaPath(description1);
			//var javaPath = "C:\upload"+description1.substring(11,description1.length);
			//alert(javaPath);
			var index=getElement(fileId);
			totalJson[index].javaName=allName;
			
			totalJson[index].javaPath=javaPath;
			//alert(JSON.stringify(totalJson));
			showGraph.setEnabled(true);
			showWindow.destroy();
			mxEffects.fadeOut(showBackground, 50, true, 10, 30, true);
		}
		
		
	  function createJavaElements(div,yuanlai,fileId){
			//alert(JSON.stringify(totalJson));   
			var index=getElement(fileId);
			if(index == ""){
			        //new 控件
					var name="组件名:<input type='text' id='javaName"+fileId+"'    />";
					var path="路径:<input type='text' id='javaPath"+fileId+"'    />";
					var button="<button onclick=javaSubmit("+fileId+")> 确定</button>";
					var str=name+"<br />"+path+"<br /><br />"+button;
					alert(yuanlai+str);
					div.innerHTML=yuanlai+str;
					alert(div.innerHTML);
					var allContent= '[{ "id": "'+fileId+'","type":"vertex", "javaName": "","javaPath": "" }]';
					var jsonAll= eval( allContent );
					totalJson=eval(totalJson);
					
					totalJson[totalJson.length]=jsonAll[0];
					
					alert(JSON.stringify(totalJson));			
			}else{ 
			        totalJson=eval(totalJson);	
					var name="<div class='control-group'><label class='control-label'>组件名:</label><div class='controls'><input type='text' id='javaName"+fileId+"'  value='"+totalJson[index].javaName+"' /></input></div></div>";
					//var path="<div class='control-group'><label class='control-label'>路径:</label><div class='controls'><input type='text' id='javaPath"+fileId+"'  value='"+totalJson[index].javaPath+"' /></input></div></div>";
					
					var upload="<div class='control-group'><label class='control-label'>选择文件:</label><div class='controls'><form action='upload.jsp' id='form1' name='form1' encType='multipart/form-data'  method='post' target='hidden_frame' ><input type='file' id='file"+fileId+"' name='file1' size='50'></input><input type='submit' value='上传文件'><span id='msg'></span></input><iframe name='hidden_frame' id='hidden_frame' style='display:none'></iframe>"
					+"</form></div></div>";
					
					//var hidden="<div class='control-group'><label class='control-label'>选择文件:</label><div class='controls'><input type='hidden' id='file2' name='filePath' value=''></input></div></div>";
					var button="<div class='form-actions'><button class='btn blue' onclick=javaSubmit("+fileId+")>确定</button></div>";
					
					var str=name+upload+button;
					var inner=yuanlai+str;
					div.innerHTML=str;
			}			   
		}
			
		 // 创建右键下拉菜单
		function createPopupMenu(editor,graph, menu, cell, evt)  
        {  
            if (cell != null)  
            {  

            	console.log(cell.getId());
            	var jsonIndex = getElement(cell.getId());
            	menu.addItem('删除', null, function()  //可以加图片
                {  
                    //mxUtils.alert('删除');   
            		totalJson.splice(jsonIndex,1);
                    editor.execute('delete');
                    //mxUtils.alert('删除');  
                }); 
//                menu.addItem('复制', null, function()  //可以加图片
//                {  
//                	
//                	editor.execute('copy');
//                	console.log(cell.getId());
//                    //mxUtils.alert('复制');  
//                });
//                menu.addItem('粘贴', null, function()  //可以加图片
//                {  
//                	editor.execute('paste');
//                	console.log(cell.getId());
//                    //mxUtils.alert('粘贴');  
//                }); 
//                menu.addItem('剪切', null, function()  //可以加图片
//                {  
//                    editor.execute('cut');
//                    //mxUtils.alert('剪切');  
//                }); 
                menu.addSeparator();
                 menu.addItem('属性', null, function()  //可以加图片
                {  
                    //editor.execute('cut');
                    var content = document.createElement('div');
                    //alert(this);
					var str=graph.convertValueToString(cell);
					//alert(str);
					createElements(content,str,cell.getId());					
					showWindow=	showModalWindow(graph, '属性', content, 600, 403);
                }); 
            }  
            else  
            {  
            	/*
                menu.addItem('No-Cell Item', 'editors/images/image.gif', function()  
                {  
                    mxUtils.alert('MenuItem2');  
                });
				*/  
            }   
        };  
		/**;
			btnArray[]={delete,cut,copy,paste,SendJava,sumit,deploy,start,getLog};
		*/
        //顶部的四个按钮
		function addToolbarButton(editor, toolbar, action, label, image,cla, isTransparent)
		{
			var button = document.createElement('button');
			button.className=cla;
//			if(cla=="btn btn-danger"){
//				button.disabled=true;
//			}
			button.style.fontSize = '10';
			if (image != null)
			{
				var img = document.createElement('img');
				img.setAttribute('src', image);
				img.style.width = '16px';
				img.style.height = '16px';
				img.style.verticalAlign = 'middle';
				img.style.marginRight = '2px';
				button.appendChild(img);
			}
	
			if (isTransparent)
			{
				button.style.background = 'transparent';
				button.style.color = '#FFFFFF';
				button.style.border = 'none';
			}
			mxEvent.addListener(button, 'click', function(evt)
			{
				editor.execute(action);
			});
			mxUtils.write(button, label);
			toolbar === null ? '' : toolbar.appendChild(button);
			
		};
		//使按钮不可用
		function addToolbarButton_click(editor, toolbar, action, label, image,cla, isTransparent)
		{
			var button = document.createElement('button');
			button.className=cla;
			//if(cla=="btn btn-danger"){
				button.disabled=true;
			//}
			button.style.fontSize = '10';
			if (image != null)
			{
				var img = document.createElement('img');
				img.setAttribute('src', image);
				img.style.width = '16px';
				img.style.height = '16px';
				img.style.verticalAlign = 'middle';
				img.style.marginRight = '2px';
				button.appendChild(img);
			}
	
			if (isTransparent)
			{
				button.style.background = 'transparent';
				button.style.color = '#FFFFFF';
				button.style.border = 'none';
			}
			mxEvent.addListener(button, 'click', function(evt)
			{
				editor.execute(action);
			});
			mxUtils.write(button, label);
			toolbar === null ? '' : toolbar.appendChild(button);
			
		};

		//原始的弹框
		function showModalWindow(graph, title, content, width, height)
		{
			var background = document.createElement('div');
			background.style.position = 'absolute';
			background.style.left = '0px';
			background.style.top = '0px';
			background.style.right = '0px';
			background.style.bottom = '0px';
			background.style.background = 'black';
			mxUtils.setOpacity(background, 50);
			document.body.appendChild(background);
			showBackground=background;
			if (mxClient.IS_IE)
			{
				new mxDivResizer(background);
			}
			
			var x = Math.max(0, document.body.scrollWidth/2-width/3);
	//		var y = Math.max(35, (document.body.scrollHeight ||
		//				document.documentElement.scrollHeight)/2-height*2/3);
			var y = 110;
			var wnd = new mxWindow(title, content, x, y, width, true, true);
			wnd.setClosable(true);
			
			// Fades the background out after after the window has been closed
			wnd.addListener(mxEvent.DESTROY, function(evt)
			{
				graph.setEnabled(true);
				mxEffects.fadeOut(background, 50, true, 
					10, 30, true);
			});
			
			graph.setEnabled(false);
			graph.tooltipHandler.hide();
			wnd.setVisible(true);
			return wnd;
		};
		
		

		//右侧图标
		function addSidebarIcon(graph, sidebar, label, image,drag_length,drag_width,type,inter)
		{
			// Function that is executed when the image is dropped on
			// the graph. The cell argument points to the cell under
			// the mousepointer if there is one.
			var strValue = "";

			var funct = function(graph, evt, cell, x, y)
			{
				var parent = graph.getDefaultParent();
				var model = graph.getModel();
				
				var v1 = null;
				
				model.beginUpdate();
				try
				{
					// NOTE: For non-HTML labels the image must be displayed via the style
					// rather than the label markup, so use 'image=' + image for the style.
					// as follows: v1 = graph.insertVertex(parent, null, label,
					// pt.x, pt.y, 120, 120, 'image=' + image);
										
					// Adds the ports at various relative locations
					//这里是进进出出能连线的四个端点 看我把它注释两个

				//	var port = graph.insertVertex(v1, null, 'Trigger', 0, 0.25, 16, 16,
				//			'port;image=editors/images/overlays/flash.png;align=right;imageAlign=right;spacingRight=18', true);
				//	port.geometry.offset = new mxPoint(-6, -8);
					if(inter!=null){
						//var v_length;
						var v_width = 36;
						var inters = inter.split(",");
						if(inters.length>2){
							v_width = inters.length*36;
							label = label.replace("height:100","height:"+50*inters.length);
						}else{
							v_width = 75;
						}
						
						if(inters.length==1){
							v1 = graph.insertVertex(parent, null, label, x, y, drag_length, v_width);
							strValue = v1.value+"";
							//拖放的一瞬间 把拖放的元素实例化出来
							initElement(v1.id,v1.value,v1);
							//alert(v1.value);
							//本来是false 我改成true试试 整个变蓝 但是不能连 还是false吧
							v1.setConnectable(true);
							
							// Presets the collapsed size
							v1.geometry.alternateBounds = new mxRectangle(0, 0, 120, 40);
						}else{
							v1 = graph.insertVertex(parent, null, label, x, y, drag_length, v_width);
							strValue = v1.value+"";
							//拖放的一瞬间 把拖放的元素实例化出来
							initElement(v1.id,v1.value,v1);
							//alert(v1.value);
							//本来是false 我改成true试试 整个变蓝 但是不能连 还是false吧
							v1.setConnectable(true);
							
							// Presets the collapsed size
							v1.geometry.alternateBounds = new mxRectangle(0, 0, 120, 40);
							
							for(var i=0;i<inters.length;i++){
								var tag_num = (1/(inters.length+1)).toFixed(2);
								console.log(tag_num);
								var port = graph.insertVertex(v1, null, 'Input_'+inters[i], 0, tag_num*(i+1), 16, 16,
										'port;image=editors/images/overlays/check.png;align=right;imageAlign=right;spacingRight=18', true);
									port.geometry.offset = new mxPoint(-6, -4);
								var port = graph.insertVertex(v1, null, 'Output_'+inters[i], 1, tag_num*(i+1), 16, 16,
										'port;image=editors/images/overlays/pencil.png;spacingLeft=18', true);
									port.geometry.offset = new mxPoint(-8, -4);
							}
						}
						
						
						
							//下面测试port的各种属性 
						   // alert(port.getId()+" portId  ");
							//alert(port.getStyle()+"  portStyle");
							//alert(port.getParent().getId()+"  parentId");
							//alert(port.getValue()+" value");
							//alert(port.getChildCount()+" childCount");
							//alert(port.isVertex()+" vertext");
							//alert(port.isConnectable()+" portIsConnectable");
							
						//	var port = graph.insertVertex(v1, null, 'Error', 1, 0.25, 16, 16,
						//			'port;image=editors/images/overlays/error.png;spacingLeft=18', true);
						//	port.geometry.offset = new mxPoint(-8, -8);

						
					}else{
						v1 = graph.insertVertex(parent, null, label, x, y, drag_length, drag_width);
						strValue = v1.value+"";
						//拖放的一瞬间 把拖放的元素实例化出来
						initElement(v1.id,v1.value,v1);
						//alert(v1.value);
						//本来是false 我改成true试试 整个变蓝 但是不能连 还是false吧
						v1.setConnectable(true);
						
						// Presets the collapsed size
						v1.geometry.alternateBounds = new mxRectangle(0, 0, 120, 40);
						var port = graph.insertVertex(v1, null, 'Input', 0, 0.5, 16, 16,
								'port;image=editors/images/overlays/check.png;align=right;imageAlign=right;spacingRight=18', true);
							port.geometry.offset = new mxPoint(-6, -4);
						var port = graph.insertVertex(v1, null, 'Output', 1, 0.5, 16, 16,
								'port;image=editors/images/overlays/pencil.png;spacingLeft=18', true);
							port.geometry.offset = new mxPoint(-8, -4);
					}
					
					
				}
				finally
				{
					model.endUpdate();
				}
				
				graph.setSelectionCell(v1);
				$("#testStart").attr("id","component"+v1.id);
				//n_processor();
			}
			
			// Creates the image which is used as the sidebar icon (drag source)
			//var realValue=getCellType(strValue);
			var img = document.createElement('img');
			var compType = getCellType(label).split(",")[0];
			//alert("image:"+image);
			img.setAttribute('src', image);
			img.style.width = '48px';
			img.style.height = '48px';
			img.title = 'This is a '+compType+' component';
			var liElement = creat_li_sidebar(type);
			//alert("type____"+getCellTypeFromImage(image));
			var pElement = creat_p_sidebar(compType);
			liElement.appendChild(img);
			liElement.appendChild(pElement);
			
			var dragElt = document.createElement('div');
			dragElt.style.border = 'dashed black 1px';
			dragElt.style.width = drag_length+'px';
			dragElt.style.height = drag_width+'px';
			  					
			// Creates the image which is used as the drag icon (preview)
			var ds = mxUtils.makeDraggable(img, graph, funct, dragElt, 0, 0, true, true);
			ds.setGuidesEnabled(true);
		};
		
		//右侧折叠框
		function creat_p_sidebar(str){
			
			var pElement = document.createElement("span");
			pElement.innerHTML = str;

			return pElement;
		}
		
		//右侧折叠框
		function creat_li_sidebar(num){
				
				var liElement = document.createElement("li");
				var aElement = document.createElement("a");
				aElement.setAttribute("href", "#");
				aElement.style="font-family:  'Microsoft YaHei';font-size:16px; ";
				liElement.appendChild(aElement);

				var ulElement = $(".modelType"+num).get(0);
				ulElement.appendChild(liElement);
				
				return aElement;
		}
	   function update_g_style(num){
		   if(num!=0){
				var main_container = document.getElementById("graphContainer").children[0].children[0].children[1];
		        //找到最后生成的方块的位置
				main_container.children[1].setAttribute("transform","scale(1) translate(155 155)");
				for(var j=1;j<num-1;j++){
					main_container.children[1+4*j].setAttribute("transform","scale(1) translate("+(150*(j+1)-13)+" "+137+")");
				}
				main_container.children[4*(num-1)+1].setAttribute("transform","scale(1) translate("+(150*(num)+5)+" "+155+")");
			}
	   }
	   
	   //点保存时检查json是否有属性值没填
	   function checkSubmitJson(json){
		   $.post("../servlet/getProtocolListServlet",{},function(text){
			   var tag="true";
			   for(var i=0;i<json.length;i++){
				   if(json[i].compType=="2"){
//					   if(json[i].name==""){
//						   alert("请填写"+json[i].realType+"的名称");
//						   tag="false";
//					   }else if(json[i].interfaceName==""){
//						   alert("请选择"+json[i].realType+"的接口");
//						   tag="false";
//					   }
				   }else if(json[i].compType=="3"){
					   alert(json[i].realType);
					   var propertyLength = text[json[i].realType].length;
					   if(json[i]["property"+propertyLength]==""){
						   alert("请填写"+json[i].realType+"的属性");
						   tag="false";
					   } 
				   }
			   }
			   if(tag=="true"){
				   for (var key in graph.getModel().cells){   
					     // alert("123");
						  var tmp = graph.getModel().getCell(key);
						  if(tmp.isEdge()){
						  //不知道为什么 target指向vertex 而不是inputPort 所以+1 确保指向inputPort
					            var target=parseInt(tmp.target.getId())+1;
								var edgeContent= '[{ "id": "'+tmp.getId()+'","type":"edge","sourceId": "'+tmp.source.getId()+'", "targetId":"'+target+'"}]';
								submitJson=eval(submitJson);
								edgeContent=eval(edgeContent);
								submitJson[submitJson.length]=edgeContent[0];
						  }else if(tmp.getValue()=="Input"||tmp.getValue()=="Output"){
								var portContent= '[{ "id": "'+tmp.getId()+'","type":"'+tmp.getValue()+'","parentId": "'+tmp.getParent().getId()+'" }]';
								portContent=eval(portContent);
								submitJson=eval(submitJson);
								submitJson[submitJson.length]=portContent[0];
						  }  else {
							if(key>1){
	                         var index=getElement(tmp.getId());
							   submitJson=eval(submitJson);
							   submitJson[submitJson.length]=totalJson[index];
							}
						  }
						  
			            }
			      	    //alert(JSON.stringify(submitJson));
			      		console.log(JSON.stringify(submitJson));
			      		$(".page2").dialog("open");
			   }
		   });
	   }
		
	   //已有工程的可是化
		function funct1(graph, evt, cell){
				
		        var parent = graph.getDefaultParent();
				var model = graph.getModel();
			    //var layout = new mxHierarchicalLayout(graph);
			    //var organic = new mxFastOrganicLayout(graph);
				model.beginUpdate();
			      try
				{
				    
        		   var obj = new Object(40);
        		   var objd = new Object(80);
        		   var obje=new Object(30);
        		   var inport = new Array(40);
        		   var outport = new Array(40);
        		   for(var i=0;i<40;i++){
        			   inport[i]=1; 
        			   outport[i]=1; 
        		   }
			       var num=str.length;
			       var tag = 0;
			       console.log(str);
			       if(num<3){
			       		num=0;
			       }
			       //图片不对的原因跟图片大小有关，width和height的值必须跟图片对应起来
			       var imgObj = new Image(); //新建一个图片对象
			       for(var i=0;i<num;i++){
					   //var id=parseInt(json[i].id)+1;
			    	   //console.log(str[i].pictureURl);
			    	   if(str[i].type=="vertex"){
			    		   
				    	   imgObj.src = "images/icons48/business/"+str[i].pictureURl;//将图片的src属性赋值给新建的图片对象的src
				           var id = parseInt(str[i].id);
				          
				           var s_hetght = 100;
				           var d_height = 75;
				           
				           if(str[i].compType=="2"){
				        	   var portSize = parseInt(str[i].portSize);
				        	   if(portSize>2){
				        		   s_hetght = 50*portSize;
					        	   d_height =36*portSize;
				        	   }
				        	   obj[id]= graph.insertVertex(parent, null, '<h1 style="margin:0px;font-size:1em;display:none;">'+str[i].realType+","+str[i].compType+'</h1>'+
										'<img src="images/icons48/business/'+str[i].pictureURl+'" style="width:100;height:'+s_hetght+';max-width:none;">'+
										'<br><input id="testStart" type="text" size="12" value="'+str[i].realType+
									    '"></input>',str[i]._x, str[i]._y, 75, d_height);
				           }else{
				        	   obj[id]= graph.insertVertex(parent, null, '<h1 style="margin:0px;font-size:1em;display:none;">'+str[i].realType+","+str[i].compType+'</h1>'+
										'<img src="images/icons48/business/'+str[i].pictureURl+'" style="width:100;height:'+s_hetght+';max-width:none;">',str[i]._x, str[i]._y, 75, d_height);
				           }
				           //alert("_x"+str[i]._x);
						  
							tag++;
						   initElementStart(obj[id].id, str[i],obj[id]);
						 
					        obj[id].setConnectable(false);
						    obj[id].geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
			    	   }
			       }
			       
			       for(var k=0;k<num;k++){
			    	   //alert(str[k].type);
			    	   if(str[k].type=="Input"){
			    		   var id = parseInt(str[k].id);
			    		   var parentId = parseInt(str[k].parentId);
			    		  
			    		   if(str[k].portName!=""){
			    			   var size = getPortSize(str,str[k].parentId);
			    			   //alert(size);
			    			   var tag_num = (1/(size+1)).toFixed(2);
			    			  
			    			   objd[id] = graph.insertVertex(obj[parentId], null, 'Input_'+str[k].portName, 0, tag_num*inport[parentId], 16, 16,
										'port;image=editors/images/overlays/check.png;align=right;imageAlign=right;spacingRight=18', true);
			    			   inport[parentId]++;
			    		   }else{
			    			   objd[id] = graph.insertVertex(obj[parentId], null, 'Input', 0, 0.5, 16, 16,
										'port;image=editors/images/overlays/check.png;align=right;imageAlign=right;spacingRight=18', true);
			    		   }
			    		  
							objd[id].geometry.offset = new mxPoint(-6, -4);
			    	   }else if(str[k].type=="Output"){
			    		   var id = parseInt(str[k].id);
			    		   var parentId = parseInt(str[k].parentId);
			    		  
			    		   if(str[k].portName!=""){
			    			   var size = getPortSize(str,str[k].parentId);
			    			   var tag_num = (1/(size+1)).toFixed(2);
			    			   objd[id] = graph.insertVertex(obj[parentId], null, 'Output_'+str[k].portName, 1, tag_num*outport[parentId], 16, 16,
										'port;image=editors/images/overlays/check.png;align=right;imageAlign=right;spacingRight=18', true);
			    			   outport[parentId]++;
			    		   }else{
			    			   objd[id] = graph.insertVertex(obj[parentId], null, 'Output', 1, 0.5, 16, 16,
										'port;image=editors/images/overlays/check.png;align=right;imageAlign=right;spacingRight=18', true);
			    		   }
			    		  
							objd[id].geometry.offset = new mxPoint(-6, -4);
			    	   }else if(str[k].type=="edge"){
			    		   //console.log(str[k].sourceId);
			    		   var sourceId = parseInt(str[k].sourceId);
			    		   var targetId = parseInt(str[k].targetId);
			    		   var edgeId = parseInt(str[k].id);
			    		   obje[edgeId]=graph.insertEdge(parent, null, '', objd[sourceId], objd[targetId]);
			    	   } else if(str[k].type=="vertex"){
			    		   
			    	   }else{
			    		   alert("逆向生成error");
			    	   }
			       }
			         
				}
				finally
				{
					// Updates the display
					//layout.execute(parent);
					graph.getModel().endUpdate();
				}
				
				//update_g_style(num);

		}
		function funct2(graph, evt, cell){
			
	        var parent = graph.getDefaultParent();
			var model = graph.getModel();
		    //var layout = new mxHierarchicalLayout(graph);
		    //var organic = new mxFastOrganicLayout(graph);
			model.beginUpdate();
		      try
			{
		    	  var v1 = graph.insertVertex(parent, null, '<h1 style="margin:0px;font-size:1em;display:none;">hello</h1>'+
							'<img src="images/icons48/business/iFRtop.ico" style="width:100;height:180;max-width:none;">',150, 150, 75, 75);
						 
					        v1.setConnectable(false);
						    v1.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
			    var v2 = graph.insertVertex(parent, null, '<h1 style="margin:0px;font-size:1em;display:none;">hello</h1>'+
						'<img src="images/icons48/business/iFRtop.ico" style="width:100;height:100;max-width:none;">',350, 100, 75, 75);
					 
				        v2.setConnectable(false);
					    v2.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
			    var v3 = graph.insertVertex(parent, null, '<h1 style="margin:0px;font-size:1em;display:none;">hello</h1>'+
						'<img src="images/icons48/business/TaskVo.png" style="width:100;height:80;max-width:none;">',350, 250, 75, 75);
					 
				        v3.setConnectable(false);
					    v3.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
			    var v4 = graph.insertVertex(parent, null, '<h1 style="margin:0px;font-size:1em;display:none;">hello</h1>'+
						'<img src="images/icons48/business/class1.png" style="width:100;height:80;max-width:none;">',550, 100, 75, 75);
					 
				        v4.setConnectable(false);
					    v4.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
				var e1 = graph.insertEdge(parent, null, '', v1, v2);
				var e2 = graph.insertEdge(parent, null, '', v1, v3);
				var e3 = graph.insertEdge(parent, null, '', v2, v4);
//		    	  var v1 = graph.insertVertex(parent, null, '开始', 550, 50, 50, 50, "shape=ellipse;perimeter=ellipsePerimeter;");
//		    	      v1.setConnectable(false);
//				      v1.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var v2 = graph.insertVertex(parent, null, '监听', 550, 150, 50, 50, "shape=rhombus;perimeter=ellipsePerimeter;");
//		    	      v2.setConnectable(false);
//				      v2.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var v3 = graph.insertVertex(parent, null, '初始化', 550, 250, 50, 50, "rounded=true;perimeter=ellipsePerimeter;arcSize=20;");
//	    	      v3.setConnectable(false);
//			      v3.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var v4 = graph.insertVertex(parent, null, '部署', 450, 250, 50, 50, "rounded=true;perimeter=ellipsePerimeter;arcSize=20;");
//	    	      v4.setConnectable(false);
//			      v4.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var v5 = graph.insertVertex(parent, null, '停止', 650, 250, 50, 50, "rounded=true;perimeter=ellipsePerimeter;arcSize=20;");
//	    	      v5.setConnectable(false);
//			      v5.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var v6 = graph.insertVertex(parent, null, '发送命令', 450, 150, 50, 50, "rounded=true;perimeter=ellipsePerimeter;arcSize=20;");
//	    	      v6.setConnectable(false);
//			      v6.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var v7 = graph.insertVertex(parent, null, '结束', 550, 350, 50, 50, "shape=ellipse;perimeter=ellipsePerimeter;");
//	    	      v7.setConnectable(false);
//			      v7.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var e1 = graph.insertEdge(parent, null, '', v1, v2);
//			      var e2 = graph.insertEdge(parent, null, '', v6, v2);
//			      var e3 = graph.insertEdge(parent, null, '初始化', v2, v3);
//			      var e4 = graph.insertEdge(parent, null, '部署', v2, v4);
//			      var e5 = graph.insertEdge(parent, null, '停止', v2, v5);
//			      var e6 = graph.insertEdge(parent, null, '', v5, v7);
//			      var e7 = graph.insertEdge(parent, null, '', v3, v7);
//			      var e8 = graph.insertEdge(parent, null, '持续监控', v4, v6);
//		    	  var v1 = graph.insertVertex(parent, null, '管理员', 150, 300, 50, 50, "shape=actor;perimeter=ellipsePerimeter;");
//	    	      v1.setConnectable(false);
//			      v1.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var v2 = graph.insertVertex(parent, null, '仿真控制', 550, 150, 75, 75, "shape=ellipse;perimeter=ellipsePerimeter;");
//	    	      v2.setConnectable(false);
//			      v2.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var v3 = graph.insertVertex(parent, null, '情保服务', 550, 450, 75, 75, "shape=ellipse;perimeter=ellipsePerimeter;");
//	    	      v3.setConnectable(false);
//			      v3.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var v4 = graph.insertVertex(parent, null, '初始化', 850, 50, 75, 75, "shape=ellipse;perimeter=ellipsePerimeter;");
//	    	      v4.setConnectable(false);
//			      v4.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var v5 = graph.insertVertex(parent, null, '开始', 850, 150, 75, 75, "shape=ellipse;perimeter=ellipsePerimeter;");
//	    	      v5.setConnectable(false);
//			      v5.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var v6 = graph.insertVertex(parent, null, '退出', 850, 250, 75, 75, "shape=ellipse;perimeter=ellipsePerimeter;");
//	    	      v6.setConnectable(false);
//			      v6.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var v7 = graph.insertVertex(parent, null, '雷达情报服务', 850, 350, 75, 75, "shape=ellipse;perimeter=ellipsePerimeter;");
//	    	      v7.setConnectable(false);
//			      v7.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var v8 = graph.insertVertex(parent, null, '电抗情报服务', 850, 450, 75, 75, "shape=ellipse;perimeter=ellipsePerimeter;");
//	    	      v8.setConnectable(false);
//			      v8.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var v9 = graph.insertVertex(parent, null, '技侦情报服务', 850, 550, 75, 75, "shape=ellipse;perimeter=ellipsePerimeter;");
//	    	      v9.setConnectable(false);
//			      v9.geometry.alternateBounds = new mxRectangle(0, 0, 75, 25);
//			      var e1 = graph.insertEdge(parent, null, '', v1, v2);
//			      var e2 = graph.insertEdge(parent, null, '', v1, v3);
//			      var e3 = graph.insertEdge(parent, null, '', v2, v4);
//			      var e4 = graph.insertEdge(parent, null, '', v2, v5);
//			      var e5 = graph.insertEdge(parent, null, '', v2, v6);
//			      var e6 = graph.insertEdge(parent, null, '', v3, v7);
//			      var e7 = graph.insertEdge(parent, null, '', v3, v8);
//			      var e8 = graph.insertEdge(parent, null, '', v3, v9);
			      //var e9 = graph.insertEdge(parent, null, '', v1, v4);
			      
			}
			finally
			{
				// Updates the display
				//layout.execute(parent);
				graph.getModel().endUpdate();
			}
			
			//update_g_style(num);

	}
		//得到业务组件有几个端口
		function getPortSize(str,parentId){
			var size = 0;
			//alert(str.length);
			for(var i=0;i<str.length;i++){
				if(str[i].id==parentId)
				size=str[i].portSize;
			}
			return size;
		}
		
		function configureStylesheet(graph)
		{
			var style = new Object();
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_RECTANGLE;
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
			style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
			style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_MIDDLE;
			style[mxConstants.STYLE_GRADIENTCOLOR] = '#41B9F5';
			style[mxConstants.STYLE_FILLCOLOR] = '#8CCDF5';
			style[mxConstants.STYLE_STROKECOLOR] = '#1B78C8';
			style[mxConstants.STYLE_FONTCOLOR] = '#000000';
			style[mxConstants.STYLE_ROUNDED] = true;
			style[mxConstants.STYLE_OPACITY] = '80';
			style[mxConstants.STYLE_FONTSIZE] = '12';
			style[mxConstants.STYLE_FONTSTYLE] = 0;
			style[mxConstants.STYLE_IMAGE_WIDTH] = '48';
			style[mxConstants.STYLE_IMAGE_HEIGHT] = '48';
			graph.getStylesheet().putDefaultVertexStyle(style);

			// NOTE: Alternative vertex style for non-HTML labels should be as
			// follows. This repaces the above style for HTML labels.
			/*var style = new Object();
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_LABEL;
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
			style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_TOP;
			style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
			style[mxConstants.STYLE_IMAGE_ALIGN] = mxConstants.ALIGN_CENTER;
			style[mxConstants.STYLE_IMAGE_VERTICAL_ALIGN] = mxConstants.ALIGN_TOP;
			style[mxConstants.STYLE_SPACING_TOP] = '56';
			style[mxConstants.STYLE_GRADIENTCOLOR] = '#7d85df';
			style[mxConstants.STYLE_STROKECOLOR] = '#5d65df';
			style[mxConstants.STYLE_FILLCOLOR] = '#adc5ff';
			style[mxConstants.STYLE_FONTCOLOR] = '#1d258f';
			style[mxConstants.STYLE_FONTFAMILY] = 'Verdana';
			style[mxConstants.STYLE_FONTSIZE] = '12';
			style[mxConstants.STYLE_FONTSTYLE] = '1';
			style[mxConstants.STYLE_ROUNDED] = '1';
			style[mxConstants.STYLE_IMAGE_WIDTH] = '48';
			style[mxConstants.STYLE_IMAGE_HEIGHT] = '48';
			style[mxConstants.STYLE_OPACITY] = '80';
			graph.getStylesheet().putDefaultVertexStyle(style);*/

			style = new Object();
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_SWIMLANE;
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
			style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
			style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_TOP;
			style[mxConstants.STYLE_FILLCOLOR] = '#FF9103';
			style[mxConstants.STYLE_GRADIENTCOLOR] = '#F8C48B';
			style[mxConstants.STYLE_STROKECOLOR] = '#E86A00';
			style[mxConstants.STYLE_FONTCOLOR] = '#000000';
			style[mxConstants.STYLE_ROUNDED] = true;
			style[mxConstants.STYLE_OPACITY] = '80';
			style[mxConstants.STYLE_STARTSIZE] = '30';
			style[mxConstants.STYLE_FONTSIZE] = '16';
			style[mxConstants.STYLE_FONTSTYLE] = 1;
			graph.getStylesheet().putCellStyle('group', style);
			
			style = new Object();
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_IMAGE;
			style[mxConstants.STYLE_FONTCOLOR] = '#774400';
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
			style[mxConstants.STYLE_PERIMETER_SPACING] = '6';
			style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_LEFT;
			style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_MIDDLE;
			style[mxConstants.STYLE_FONTSIZE] = '10';
			style[mxConstants.STYLE_FONTSTYLE] = 2;
			style[mxConstants.STYLE_IMAGE_WIDTH] = '16';
			style[mxConstants.STYLE_IMAGE_HEIGHT] = '16';
			graph.getStylesheet().putCellStyle('port', style);
			
			style = graph.getStylesheet().getDefaultEdgeStyle();
			style[mxConstants.STYLE_LABEL_BACKGROUNDCOLOR] = '#FFFFFF';
			style[mxConstants.STYLE_STROKEWIDTH] = '2';
			style[mxConstants.STYLE_ROUNDED] = true;
			style[mxConstants.STYLE_EDGE] = mxEdgeStyle.EntityRelation;
		};
