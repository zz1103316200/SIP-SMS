/**
 * Include jQuery libs
 * Dependened mxClient
 */

/*
function include(url){
	document.write('<script src="'+ url +'"></script>');
}
include('./js/jquery.ui.core.js');
include('./js/jquery.ui.dragable.js');
include('./js/jquery.ui.dropable.js');
include('./js/jquery.ui.mouse.js');
include('./js/jquery.ui.widget.js');
//mxClient.include('./js/jquery-1.9.0.js');
*/

function devAttrWindow(container, width, height){
	if(!container || !width || !height){return ;}

	container.style.width = width;
	container.style.height = height;
	this.height = parseInt(container.style.height);
	
	this.div = document.createElement('div');
	container.appendChild(this.div);
	this.div.style.width = '98%';
	this.div.style.height = '99%';
	this.div.style.margin = '1% 1% 0 1%';
	this.div.style.backgroundColor = 'white';

	//标签头
	this.header = document.createElement('div');
	this.div.appendChild(this.header);
	this.header.style.width = '100%';
	this.header.style.height = '40px';
	this.header.className = 'devAttrHeader';

	var ul = document.createElement('ul');
	this.header.appendChild(ul);
	ul.className = 'nav nav-tabs';
	ul.id = 'devAttrNav';
	ul.style.height = '100%';
	ul.style.fontSize = '14px';
	ul.style.textAlign = 'center';

	//标签体
	this.body = document.createElement('div');
	this.div.appendChild(this.body);
	this.body.style.width = '100%';
	this.body.style.height = (this.height - 40 - this.height * 0.01) + 'px';
	this.body.className = 'tab-content';
	this.body.id = 'devAttrBody';
	this.body.style.border = 'thin solid #aaa';
}

devAttrWindow.prototype.constructor = devAttrWindow;


/**
 * Attributes
 */

// 弹出窗口内容的根div节点
devAttrWindow.prototype.div = null;

// 标签页首部
devAttrWindow.prototype.header = null;

// 标签页内容
devAttrWindow.prototype.body = null;

// 弹出窗容器的高度
devAttrWindow.prototype.height = null;

/**
 * Functions
 */

/**
 * 新建一个标签
 * title  : string
 * id     : string
 * active : boolean
 */
devAttrWindow.prototype.createTab = function(title, id, active){
	var tab = document.createElement('div');
	this.body.appendChild(tab);
	tab.style.height = '97%';
	tab.style.width = '100%';
	tab.id = id;
	tab.className = active ? "tab-pane fade active in" : "tab-pane fade";
	
	var className = active ? 'class="active"' : '';
	$(this.header).find('ul').append('<li '+ className +'style="width:80px;height:100%;border:1px solid #aaa;border-bottom:none;margin-right:5px;"><a style="height:100%;margin-right:0px;" href="#'+id+'" data-toggle="tab">'+ title +'</a></li>');

	return tab;
};

/**
 * 视图标签内容
 * tab : dom node
 */
devAttrWindow.prototype.viewTab = function(tab){
	var content = document.createElement('div');
	tab.appendChild(content);
	content.style.width = '98%';
	content.style.height = '99%';
	content.style.margin = '1% 1% 1% 1%';

	//设备视图
 	var viewDev = document.createElement('div');
	content.appendChild(viewDev);
	viewDev.style.width = '100%';
	viewDev.style.height = '60%';
	viewDev.style.border = '0.5px solid #ddd';
	viewDev.style.backgroundColor = '#FAFAFA';
	//设备型号
	var devVer = document.createElement('div');
	viewDev.appendChild(devVer);
	devVer.style.height = '16px';
	devVer.style.width = '60px';
	devVer.style.marginLeft = '5px';
	devVer.style.marginTop = '4px';
	devVer.innerHTML = 'AR1220';
	//设备图
	var dev = document.createElement('div');
	viewDev.appendChild(dev);
	dev.style.width = '98%';
	var devHeight = parseInt(this.body.style.height) * 0.91 * 0.6 - 20;
	dev.style.height = devHeight * 0.98 + 'px';
	dev.style.margin = '1% 1% 1% 1%';
	dev.style.border = '0.5px solid #111';
	dev.style.backgroundColor = '#fff';

	//添加设备
	this.viewDev(dev);

	//板卡视图
	var viewCard = document.createElement('div');
	content.appendChild(viewCard);
	viewCard.style.width = '100%';
	viewCard.style.height = '39%';
	viewCard.style.marginTop = '1%';
	viewCard.style.border = '0.5px solid #ddd';
	viewCard.style.backgroundColor = '#FAFAFA';

	//板卡标题
	var title = document.createElement('div');
	viewCard.appendChild(title);
	title.style.height = '16px';
	title.style.width = '100px';
	title.style.marginLeft = '5px';
	title.style.marginTop = '4px';
	title.innerHTML = 'eNSP支持的接口卡';

	var cardHeight = parseInt(this.body.style.height) * 0.90 * 0.39 - 20;

	//可用板卡
	var card = document.createElement('div');
	viewCard.appendChild(card);
	card.style.width = '63%';
	card.style.height = cardHeight * 0.98 + 'px';
	card.style.float = 'left';
	card.style.margin = '1% 1% 1% 1%';
	card.style.border = '0.5px solid #111';
	card.style.backgroundColor = '#fff';
	card.style.overflow = 'scroll';
	this.rmDrop(card, '.filled');
	//card.id = 'card-drop';

	//添加板卡
	this.viewCard(card);
	//板卡描述
	var cardDes = document.createElement('div');
	viewCard.appendChild(cardDes);
	cardDes.style.width = '34%';
	cardDes.style.height = cardHeight * 0.98 + 'px';
	cardDes.style.float = 'left';
	cardDes.style.border = '0.5px solid #ddd';
	cardDes.style.margin = '1% 1% 1% 0';
	cardDes.style.backgroundColor = '#fff';
	cardDes.style.overflow = 'scroll';

	var clearDiv = document.createElement('div');
	viewCard.appendChild(clearDiv);
	clearDiv.className = 'clear';
};

/**
 * 创建一个设备或板卡元素
 * width  : number
 * height : number
 * url    : string
 */
devAttrWindow.prototype.createImg = function(width, height, url){
	var div = document.createElement('div');
	//var img = document.createElement('img');

	div.style.width = width + 'px';
	div.style.height = height + 'px';
	/*
	img.style.width = div.style.width;
	img.style.height = div.style.height;
	img.src = url;
	*/

	//test
	div.style.backgroundColor = url;

	return div;
};

/**
 * 设置设备的界面
 * container : dom node
 */
devAttrWindow.prototype.viewDev = function(container){
	var img1 = this.createImg(300, 60, 'white');
	img1.id = 'dev1';	
	img1.className = 'card1 blank';
	img1.style.marginTop = '10px';
	img1.style.marginLeft = '10px';
	img1.style.float = 'left';
	img1.style.border = '1px solid #111';

	var img2 = this.createImg(300, 60, 'white');
	img2.style.float = 'left';
	img2.style.border = '1px solid #111';
	img2.style.marginTop = '10px';
	img2.id = 'dev2';
	img2.className = 'card1 blank';

	var clearDiv = document.createElement('div');
	clearDiv.className = 'clear';

	var img3 = this.createImg(600, 60, 'white');
	img3.style.marginTop = '80px';
	img3.style.marginLeft = '10px';
	img3.style.border = '1px solid #111';
	img3.id = 'dev3';
	img3.className = 'card2 blank';

	container.appendChild(img1);
	container.appendChild(img2);
	container.appendChild(clearDiv);
	container.appendChild(img3);

	this.drop(img1, 'card1');
	this.drop(img2, 'card1');

	this.drop(img3, 'card2');
};

/**
 * 设置板卡的界面
 * container : dom node
 */
devAttrWindow.prototype.viewCard = function(container){
	var img1 = this.createImg(100, 50, 'yellow');
	img1.id = 'card1';
	var div1 = document.createElement('div');
	div1.appendChild(img1);
	div1.id = 'card1-div';
	div1.style.marginTop = '10px';
	div1.style.marginLeft = '10px';

	var img2 = this.createImg(200, 50, 'blue');
	img2.id = 'card2';
	var div2 = document.createElement('div');
	div2.appendChild(img2);
	div2.id = 'card2-div';
	div2.style.marginTop = '10px';
	div2.style.marginLeft = '10px';

	container.appendChild(div1);
	container.appendChild(div2);

	this.drag(img1);
	this.drag(img2);
};

/**
 * 为板卡添加可拖动操作
 * selector : dom node object
 */
devAttrWindow.prototype.drag = function(selector){
	$(selector).draggable({
		revert: 'invalid',
		helper: 'clone',
		cursor: 'move'
	});
};

/**
 * 为设备添加可放置操作
 * selector : dom node
 * card     : string (card type)
 */
devAttrWindow.prototype.drop = function(selector, card){
	$(selector).droppable({
		accept:     '#' + card,
		activate:   function(event, ui){
			$('.' + card + '.blank').css('backgroundColor', 'orange');
		},
		deactivate: function(event, ui){
			$('.' + card + '.blank').css('backgroundColor', 'white');
		},
		drop:       function(event, ui){
			if($(this).hasClass('blank')){
				// 将要添加到设备上的板卡元素
				var newItem = ui.draggable;
				// 保存当前拖动的板卡的副本
				var prevItem = newItem.clone();
				// 清空id属性
				$(newItem).attr('id', '');
				// 设置表示在设备上的板卡标记
				$(newItem).addClass('filled');
				// 辅助属性，为了在移除板卡时，重新为设备添加可放置的标记
				$(newItem).attr('slot', $(this).attr('id'));

				// 使新元素适应设备大小
				$(newItem).css('margin', '0 0 0 0');
				$(newItem).css('width', $(this).css('width'));
				$(newItem).css('height', $(this).css('height'));
				
				// 清空可放置元素的提示状态
				$('.' + card + '.blank').css('backgroundColor', 'white');

				// 添加新元素到设备上，并移除可放置标记
				$(this).append(newItem);
				$(this).removeClass('blank');				

				// 将原来的节点放回板卡选择框
				$("#" + card + "-div").append(prevItem);

				// 为支持板卡元素重新绑定可拖动操作
				var dAW = new devAttrWindow();
				dAW.drag(prevItem);
			}else{
				return false;
			}
		}
	});
};

/**
 * 为设备添加可移除操作
 * selector : dom node
 * accept   : string (removeable accepted node)
 */
devAttrWindow.prototype.rmDrop = function(selector, accept){
	$(selector).droppable({
		accept:     accept,
		activate:   function(event, ui){
			$(this).css('backgroundColor', 'orange');
		},
		deactivate: function(event, ui){
			$(this).css('backgroundColor', 'white');
		},
		drop:       function(event, ui){
			var item = ui.draggable;

			// 为被移除的设备添加可放置标记
			$('#'+$(item).attr('slot')).addClass('blank');

			// 重置可放置元素的提示状态
			$(this).css('backgroundColor', 'white');
			$(this).append(item);

			// 解决删除元素后的指针不正确问题
			// 通过滞后删除之前的元素，这样每次card-div中只会有一个隐藏的多余元素
			$('.deleted').each(function(){
				$(this).remove();
			})
			$(item).removeClass().hide().addClass('deleted');
		}
	});	
};

