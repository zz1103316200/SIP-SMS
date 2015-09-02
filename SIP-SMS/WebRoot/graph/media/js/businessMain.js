function main(container, toolbar, sidebar)
		{
			// Checks if the browser is supported
			// 浏览器兼容检测  
			if (!mxClient.isBrowserSupported())
			{
				// Displays an error message if the browser is not supported.
				mxUtils.error('浏览器不支持!', 200, false);
			}
			else
			{
				// Assigns some global constants for general behaviour, eg. minimum
				// size (in pixels) of the active region for triggering creation of
				// new connections, the portion (100%) of the cell area to be used
				// for triggering new connections, as well as some fading options for
				// windows and the rubberband selection.
				// 定义全局变量，如用于触发建立新的连接的活动区域的最小尺寸（以像素为单位），该部分（100％）的小区区域被用于触发新的连接，以及一些窗口和“下拉菜菜单选择 
				mxConstants.MIN_HOTSPOT_SIZE = 16;
				mxConstants.DEFAULT_HOTSPOT = 1;
				
				// Enables guides
				// 显示导航线 
				mxGraphHandler.prototype.guidesEnabled = true;

			    // Alt disables guides
				// Alt 键禁用导航线  
			    mxGuide.prototype.isEnabledForEvent = function(evt)
				{
					return !mxEvent.isAltDown(evt);
				};

				// Enables snapping waypoints to terminals
				// 自动导航目标 
				mxEdgeHandler.prototype.snapToTerminals = true;

				// Workaround for Internet Explorer ignoring certain CSS directives
				// IE浏览器样式修复
				if (mxClient.IS_QUIRKS)
				{
					document.body.style.overflow = 'auto';
					new mxDivResizer(container);
					//new mxDivResizer(outline);
					new mxDivResizer(toolbar);
					new mxDivResizer(sidebar);
					//new mxDivResizer(status);
				}
				
				// Creates a wrapper editor with a graph inside the given container.
				// The editor is used to create certain functionality for the
				// graph, such as the rubberband selection, but most parts
				// of the UI are custom in this example.
				
				// 创建图形编辑器
				var editor = new mxEditor();
				var graph = editor.graph;
				var model = graph.getModel();
				
				//注释掉不能连接
				editor.graph.setConnectable(true);
				// Disable highlight of cells when dragging from toolbar
				// 启用高亮工具栏
				graph.setDropEnabled(false);
				//graph.setConnectable(true); 
				//设置后可以整体移动
				//graph.setPanning(true);
				
				//graph.panningHandler.useLeftButtonForPanning = true;
				// Uses the port icon while connections are previewed
				// 连接预览
				graph.connectionHandler.getConnectImage = function(state)
				{
					return new mxImage(state.style[mxConstants.STYLE_IMAGE], 16, 16);
				};
				//添加连线时，触发事件
				graph.addEdge = function(edge, parent, source, target, index){  
					//alert(source.getParent().getId());
					//alert(target.getValue());
					var sourceId = getElement(source.getParent().getId());
					var targetId = getElement(target.getParent().getId());
					
					var arge=arguments;
					var that=this;
					
					var totalJson = null;
					if(totalJson && totalJson[sourceId].compType=="2" && totalJson[targetId].compType=="2"){
						
						var copymxGraph=mxGraph;
						var inportName;
						if(target.getValue().length>6){
							
							inportName = target.getValue().substring(6);
				
						}
						var outportName;
						if(source.getValue().length>7){
							outportName = source.getValue().substring(7);
							
						}
						//console(inportName);
						//console(outportName);
						$.post("../servlet/checkCompServlet",{sourceName:totalJson[sourceId].realType,targetName:totalJson[targetId].realType,inportName:inportName,outportName:outportName},function(text){
							if(text=="2"){
								alert("转换格式错误！");
							}else if(text=="3"){
								alert("协议转换错误！");
							}else{
								mxGraph.prototype.addEdge.apply(that, arge);
							}
						});
					}else{
						mxGraph.prototype.addEdge.apply(this, arguments);
					}
//					value = mxUtils.createXmlDocument().createElement('transicion');        
//					value.setAttribute('type', 'hello');   
//					value.setAttribute('label', 'Tarea');   
//					edge.value = value;
					
//					if (isEdge(edge)){
//						   remove(getTerminal(edge, true));
//						   remove(getTerminal(edge, false));
//						}
					
					
					// "supercall"
					};
				// Centers the port icon on the target port
				// 显示中心端口图标
				graph.connectionHandler.targetConnectImage = true;

				// Does not allow dangling edges
				 // 禁止连接线晃动 
				graph.setAllowDanglingEdges(false);
				
				//不知道是啥 设置上再说
				graph.setPortsEnabled(false);
				// 启动鼠标悬停提示.. 
                var rubberband = new mxRubberband(graph);  
                var keyHandler = new mxKeyHandler(graph);  
				// 安装一个自定义的工具提示单元格..  
//                graph.getTooltipForCell = function(cell)  
//                {  
//                    return 'Doubleclick and right- or shiftclick';  
//                }  
                  
                // 安装右键点击处理程序..  
                graph.panningHandler.factoryMethod = function(menu, cell, evt)  
                {  
                    return createPopupMenu(editor,graph, menu, cell, evt);  
                };  
				
				// Sets the graph container and configures the editor
				//设置图形容器，并配置编辑器
				editor.setGraphContainer(container);
				var config = mxUtils.load(
					'editors/config/keyhandler-commons.xml').
						getDocumentElement();
				editor.configure(config);
				
				showGraph=graph;
				// Defines the default group to be used for grouping. The
				// default group is a field in the mxEditor instance that
				// is supposed to be a cell which is cloned for new cells.
				// The groupBorderSize is used to define the spacing between
				// the children of a group and the group bounds.

                // 设置默认组   
                // groupBorderSize 设置图形和它的子元素的边距。 
				var group = new mxCell('Group', new mxGeometry(), 'group');
				group.setVertex(true);
				group.setConnectable(true);
				
				editor.defaultGroup = group;
				editor.groupBorderSize = 20;

				// Disables drag-and-drop into non-swimlanes.
				// 目标是否有效 
				graph.isValidDropTarget = function(cell, cells, evt)
				{
					return this.isSwimlane(cell);
				};
				
				// Disables drilling into non-swimlanes.
				// 是否根元素
				graph.isValidRoot = function(cell)
				{
					return this.isValidDropTarget(cell);
				}

				// Does not allow selection of locked cells
				// 是否可以被选中
				graph.isCellSelectable = function(cell)
				{
					return !this.isCellLocked(cell);
				};


				// Returns a shorter label if the cell is collapsed and no
				// label for expanded groups
				// 返回元素 
				graph.getLabel = function(cell)
				{
					var tmp = mxGraph.prototype.getLabel.apply(this, arguments); // "supercall"
					
					if (this.isCellLocked(cell))
					{
						// Returns an empty label but makes sure an HTML
						// element is created for the label (for event
						// processing wrt the parent label)
						return '';
					}
					else if (this.isCellCollapsed(cell))
					{
						var index = tmp.indexOf('</h1>');
						
						if (index > 0)
						{
							tmp = tmp.substring(0, index+5);
						}
					}
					
					return tmp;
				}

				// 禁用HTML的泳道标签，避免冲突   
                // 判断是否为泳道标签
				graph.isHtmlLabel = function(cell)
				{
					return !this.isSwimlane(cell);
				}
				// Enables new connections
				//这是双击事件
				graph.dblClick = function(evt, cell)
				{
					// Do not fire a DOUBLE_CLICK event here as mxEditor will
					// consume the event and start the in-place editor.
					// 如果不是双击事件，编辑器会自动处理

					if (this.isEnabled() &&
						!mxEvent.isConsumed(evt) &&
						cell != null &&
						this.isCellEditable(cell))
					{
						if (this.model.isEdge(cell) ||
							!this.isHtmlLabel(cell))
						{
							this.startEditingAtCell(cell);
						}
						else
						{
							//liukaiDouble(graph);

							var content = document.createElement('div');
							
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							var child = document.createElement('div');
							
							content.appendChild(child);
							var dAW = new devAttrWindow(child, 800, 600);
							var viewTab = dAW.createTab('视图', 'devView', true);
							dAW.viewTab(viewTab);
							
							var configTab = dAW.createTab('配置', 'devConfig', false);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							
							//alert(this.toString());
							var str=this.convertValueToString(cell);
							//cell有一个自己的id 很爽 用来区分
							createElements(content,str,cell.getId());
							showWindow=	showModalWindow(this, '属性', content, 800, 600);
						}
					}

					// Disables any default behaviour for the double click
					 // 禁用任何默认双击行为 
					//mxEvent.consume(evt);
				};
				
				// Adds all required styles to the graph (see below)
				// 配置样式 
				configureStylesheet(graph);
												
				//下边这个是左边栏里的各种可以拖拉拽的控件
				$.post("../servlet/ComponentListServlet","",function(text){
					//alert(text);
					//console.log(text);
					//75表示的是里面的浅蓝块的颜色，width和height表示的是图片的大小
					for(var i=0;i<text.length;i++){
						addSidebarIcon(graph, sidebar,
								'<h1 style="margin:0px;display:none;">'+text[i].name+','+text[i].type+'</h1>'+
								'<img src="images/icons48/business/'+text[i].url+'" style="width:100;height:100;max-width:none;">'
							    ,
								'images/icons48/business/'+text[i].url,75,75,text[i].type,text[i].inter);
					}
				});
                //在h2里加个数字是为了区分，不同的组件
			    addSidebarIcon(graph, sidebar,
					'<h1 style="margin:0px;display:none;">Start,1</h1>'+
					'<img src="images/icons48/business/Start.png" style="width:85;height:85;max-width:none;">'
				    ,
					'images/icons48/business/Start.png',60,60,1);
				addSidebarIcon(graph, sidebar,
					'<h1 style="margin:0px;display:none;">End,1</h1>'+
					'<img src="images/icons48/business/End.png" style="width:85;height:85;max-width:none;">'
				    ,
					'images/icons48/business/End.png',60,60,1);
				addSidebarIcon(graph, sidebar,
						'<h1 style="margin:0px;display:none;">Choice,1</h1>'+
						'<img src="images/icons48/business/Choice.png" style="width:85;height:85;max-width:none;">'
					    ,
						'images/icons48/business/Choice.png',60,60,1);
				addSidebarIcon(graph, sidebar,
						'<h1 style="margin:0px;display:none;">Resequence,1</h1>'+
						'<img src="images/icons48/business/ALL.png" style="width:85;height:85;max-width:none;">'
					    ,
						'images/icons48/business/ALL.png',60,60,1);
				addSidebarIcon(graph, sidebar,
						'<h1 style="margin:0px;display:none;">Splitter,1</h1>'+
						'<img src="images/icons48/business/Splitter.png" style="width:85;height:85;max-width:none;">'
					    ,
						'images/icons48/business/Splitter.png',60,60,1);
				addSidebarIcon(graph, sidebar,
						'<h1 style="margin:0px;display:none;">Aggregator,1</h1>'+
						'<img src="images/icons48/business/CollectionAggregator.png" style="width:85;height:85;max-width:none;">'
					    ,
						'images/icons48/business/CollectionAggregator.png',60,60,1);
				addSidebarIcon(graph, sidebar,
						'<h1 style="margin:0px;display:none;">Option,1</h1>'+
						'<img src="images/icons48/business/FirstSuccessful.png" style="width:85;height:85;max-width:none;">'
					    ,
						'images/icons48/business/FirstSuccessful.png',60,60,1);
				addSidebarIcon(graph, sidebar,
						'<h1 style="margin:0px;display:none;">类名,1</h1>'+
						'<img src="images/icons48/business/class1.png" style="width:85;height:85;max-width:none;">'
					    ,
						'images/icons48/business/class1.png',60,60,1);
				addSidebarIcon(graph, sidebar,
						'<h1 style="margin:0px;display:none;">类名,1</h1>'+
						'<img src="images/icons48/business/iFRtop.ico" style="width:95;height:125;max-width:none;">'
					    ,
						'images/icons48/business/iFRtop.ico',70,70,1);
				addSidebarIcon(graph, sidebar,
						'<h1 style="margin:0px;display:none;">类名,1</h1>'+
						'<img src="images/icons48/business/iFRtop.ico" style="width:95;height:125;max-width:none;">'
					    ,
						'images/icons48/business/iFRtop.ico',70,70,1);
				// Displays useful hints in a small semi-transparent box.
				// 在一个半透明的窗口中显示提示图标
//				var hints = document.createElement('div');
//				hints.style.position = 'absolute';
//				hints.style.overflow = 'hidden';
//				hints.style.width = '230px';
//				hints.style.bottom = '56px';
//				hints.style.height = '76px';
//				hints.style.right = '20px';
//				
//				hints.style.background = 'black';
//				hints.style.color = 'white';
//				hints.style.fontFamily = 'Arial';
//				hints.style.fontSize = '10px';
//				hints.style.padding = '4px';
				
				// Creates a new DIV that is used as a toolbar and adds
				// toolbar buttons.
				// 创建一个新的DIV容器作为一个工具栏，添加工具栏按钮。 
				var spacer = document.createElement('div');
				spacer.style.display = 'inline';
				spacer.style.padding = '8px';
				
			   //	addToolbarButton(editor, toolbar, 'groupOrUngroup', '(Un)group', 'images/group.png');
				
				// Defines a new action for deleting or ungrouping
				editor.addAction('groupOrUngroup', function(editor, cell)
				{
					cell = cell || editor.graph.getSelectionCell();
					if (cell != null && editor.graph.isSwimlane(cell))
					{
						editor.execute('ungroup', cell);
					}
					else
					{
						editor.execute('group');
					}
				});
				
				
				//顶部按钮的调用
				addToolbarButton(editor, toolbar, 'deploy', '保存', 'images/flowDeploy.png','btn btn-primary');
				editor.addAction('deploy',function(editor,cell){
				//checkSubmitJson(totalJson);	
				 $.post("../servlet/getProtocolListServlet",{},function(text){
					   var tag="true";
					   for(var i=0;i<totalJson.length;i++){
						   if(totalJson[i].compType=="2"){
//							   if(totalJson[i].name==""){
//								   alert("请填写"+totalJson[i].realType+"的名称");
//								   tag="false";
//								   break;
//							   }else if(totalJson[i].interfaceName==""){
//								   alert("请选择"+totalJson[i].realType+"的接口");
//								   tag="false";
//								   break;
//							   }
						   }else if(totalJson[i].compType=="3"){

							   var propertyLength = text[totalJson[i].realType].length;
							   if(totalJson[i]["property"+propertyLength]==""){
								   alert("请填写"+totalJson[i].realType+"的属性");
								   tag="false";
								   break;
							   } 
						   }
					   }
					   if(tag=="true"){
						   for (var key in graph.getModel().cells){   
							     // alert("123");
								  var tmp = graph.getModel().getCell(key);
								  //alert(tmp.getValue());
								  if(tmp.isEdge()){
								  //不知道为什么 target指向vertex 而不是inputPort 所以+1 确保指向inputPort
									  	console.log(tmp.target);
							            var target=parseInt(tmp.target.getId());
										var edgeContent= '[{ "id": "'+tmp.getId()+'","type":"edge","sourceId": "'+tmp.source.getId()+'", "targetId":"'+target+'"}]';
										submitJson=eval(submitJson);
										edgeContent=eval(edgeContent);
										submitJson[submitJson.length]=edgeContent[0];
								  }else if(tmp.getValue()=="Input"||tmp.getValue()=="Output"){
										var portContent= '[{ "id": "'+tmp.getId()+'","type":"'+tmp.getValue()+'","portName":""'+',"parentId": "'+tmp.getParent().getId()+'" }]';
										portContent=eval(portContent);
										submitJson=eval(submitJson);
										submitJson[submitJson.length]=portContent[0];
								  } else if((tmp.getValue()!=null)&&(tmp.getValue().toString().substring(0,5)=="Input")){
										var portContent= '[{ "id": "'+tmp.getId()+'","type":"Input","portName":"'+tmp.getValue().substring(6)+'","parentId": "'+tmp.getParent().getId()+'" }]';
										portContent=eval(portContent);
										submitJson=eval(submitJson);
										submitJson[submitJson.length]=portContent[0];
								  } else if((tmp.getValue()!=null)&&(tmp.getValue().toString().substring(0,6)=="Output")){
										var portContent= '[{ "id": "'+tmp.getId()+'","type":"Output","portName":"'+tmp.getValue().substring(7)+'","parentId": "'+tmp.getParent().getId()+'" }]';
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
			        //window.location.href="../servlet/SubmitBusinessDServlet?submitJson="+JSON.stringify(submitJson);
				});
				
				
				addToolbarButton(editor, toolbar, 'register', '注册', 'images/flowSwitch.png','btn btn-warning');
				editor.addAction('register',function(editor,cell){
						$.post("../flowexecute",{selection:flowNameStr},function(text){
				     }
				     );
				});
				
				addToolbarButton_click(editor, toolbar, 'run', '运行', 'images/flowStart.png','btn btn-success' );
				editor.addAction('run',function(editor,cell){
					
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
			        
			       window.location.href="../servlet/SubmitBusinessServlet?submitJson="+JSON.stringify(submitJson);
				});
				
				addToolbarButton_click(editor, toolbar, 'deleteFlow', '删除', 'images/flowSwitch.png','btn btn-danger');
				editor.addAction('deleteFlow',function(editor,cell){
					//var flowName = $("#ul_element_list li[class=active]").children().eq(0).children().eq(1).html();
					//alert(flowName);
					$.post("../servlet/deleteFlowServlet",{flowName:flowNameStr},function(text){
				     }
				     );
					location.reload();
				});
				
				// Creates the outline (navigator, overview) for moving
				// around the graph in the top, right corner of the window.
				// 图形窗口的右上角的周围创建导航提示。
				//var outln = new mxOutline(graph, outline);

				// To show the images in the outline, uncomment the following code
				//outln.outline.labelsVisible = true;
				//outln.outline.setHtmlLabels(true);
				
				// Fades-out the splash screen after the UI has been loaded.
				// 淡出了启动后，屏幕的UI已经被加载   

//				var splash = document.getElementById('splash');
//				if (splash != null)
//				{
//					try
//					{
//						mxEvent.release(splash);
//						mxEffects.fadeOut(splash, 100, true);
//					}
//					catch (e)
//					{
//					
//						// mxUtils is not available (library not loaded)
//						splash.parentNode.removeChild(splash);
//					}
//				}
				funct2(graph, evt, cell);
				
			}			
		};