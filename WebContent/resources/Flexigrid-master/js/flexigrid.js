/*
 * Flexigrid for jQuery -  v1.1
 *
 * Copyright (c) 2008 Paulo P. Marinas (code.google.com/p/flexigrid/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 */
var mou_grid_ux = {

	/***************************************************************************
	 * 初始化grid对应setting，并缓存
	 * 
	 * @param id
	 * @param config
	 */
	iniGridConfig : function(id, config) {

		var btncfgs = {}; // 存储btn—in-cell配置

		config["grid_id"] = id;

		var _grid = $("#" + id);

		if (config.colModel) {
			var col_no = {};

			for (var i = 0; i < config.colModel.length; i++) {
				var cm = config.colModel[i];

				var thisno = i;
				var name = cm.name || cm.m_type;
				col_no[name] = thisno;

				if (cm.m_type && cm.m_type == 'radio') {
					_grid.data("SELECTFLG", "radio");
				} else if (cm.m_type && cm.m_type == 'checkbox') {
					_grid.data("SELECTFLG", "checkbox");
				}

				if (cm.buttons && cm.buttons.length > 0) {
					for (var num = 0; num < cm.buttons.length; ++num) {
						var btncfg = cm.buttons[num];
						var nameBtn = btncfg.r_name;

						btncfgs[nameBtn] = btncfg;
					}
				}
			}

			_grid.data("COL_NO", col_no);
		}

		_grid.data("BTNCFGS", btncfgs);

		//console.log("_grid.data()--\n" + JSON.stringify(_grid.data()));
	},
	/***************************************************************************
	 * 取 grid 的 name的 button 的config
	 * 
	 * @param id
	 * @param name
	 */
	getBtnInCellCfg : function(id, name) {
		var configs = $("#" + id).data("BTNCFGS");
		return configs[name];
	},
	/***************************************************************************
	 * 取 id 的grid 的 列对应的序号
	 * 
	 * @param id
	 */
	getColNo : function(id) {
		return $("#" + id).data("COL_NO");
	},

	/***************************************************************************
	 * created by nbq 2014-2-4 取CM的类型，如果没有，返回normal
	 * 
	 * @param o
	 * @returns
	 */
	getCMType : function(o) {
		return (o.m_type || 'normal');
	},
	/***************************************************************************
	 * 取grid所有选中的内容
	 * 
	 * @param id
	 * @returns
	 */
	getSelectedAllGrid : function(gridId) {
		var rtnSelected = [];

		var _grid = $("#" + gridId);
		var selectFlg = _grid.data("SELECTFLG");

		if (selectFlg && selectFlg == 'checkbox') {
			$('tbody tr td div input[type=checkbox]', _grid).each(function() {
				var _this = $(this);

				if (_this[0].checked) {
					var _td = _this.parent().parent();
					var _tr = _td.parent();

					// 取该行所有列
					var config_select = mou_grid_ux.getTdSelect(gridId, _td.attr('no'));
					var obj = mou_grid_ux.getTrObj(_tr, config_select);

					rtnSelected.push(obj);
				}
			});
		}

		if (selectFlg && selectFlg == 'radio') {
			$('tbody tr td div input[type=radio]', _grid).each(function() {
				var _this = $(this);

				if (_this[0].checked) {
					var _td = _this.parent().parent();
					var _tr = _td.parent();

					// 取该行所有列
					var config_select = mou_grid_ux.getTdSelect(gridId, _td.attr('no'));
					var obj = mou_grid_ux.getTrObj(_tr, config_select);

					rtnSelected.push(obj);
				}
			});
		}

		//console.log("-----rtnSelected----" + JSON.stringify(rtnSelected));

		return rtnSelected;
	},
	/***************************************************************************
	 * 取 grid 的所有数据
	 * 
	 * @param gridId
	 */
	getAllDataGrid : function(gridId) {

		var data = [];
		$('tbody tr', $("#" + gridId)).each(function(e) {
			var _this = $(this);
			var obj = mou_grid_ux.getTrObj(_this);
			// console.log("obj--" + JSON.stringify(obj));
			data.push(obj);
		});

		var rtnObj = {};
		rtnObj["data"] = data;
		rtnObj["gridId"] = gridId;

		return rtnObj;
	},
	/***************************************************************************
	 * 取grid的no列对应的ColModel
	 * 
	 * @param id
	 * @param no
	 */
	getColModel : function(id, no) {

		var config = $("#" + id).data("CONFIG");
		// console.log("getColModel--" + id + "--" + no + "---- " +
		// JSON.stringify(config.colModel[no]));
		return config.colModel[no];
	},
	/***************************************************************************
	 * 取grid的no列对应的select配置
	 * 
	 * @param id
	 * @param no
	 * @returns
	 */
	getTdSelect : function(id, no) {

		var config = $("#" + id).data("CONFIG");
		return config.colModel[no]["select"];
	},
	/***************************************************************************
	 * 取某行的 按照 config 配置的对象。 如果config 为空，返回该行所有列
	 * 
	 * @param tr
	 * @param config
	 *            ["snotypnam","cursno"]
	 */
	getTrObj : function(tr, config) {
		var obj = {};
		obj["tr_no"] = tr.attr("tr_no");
		var id = tr.parent().parent().attr("id");
		// console.log("grid id ------- " + id + "\n");

		var colNo = mou_grid_ux.getColNo(id);
		// console.log("grid colNo ------- " + JSON.stringify(colNo) + "\n");

		if (config) {
			// 取config中指定的列
			for (var i = 0; i < config.length; ++i) {
				var key = config[i];
				var colid = colNo[key];

				var value = "";
				if (colid != undefined) {
					value = $('div:eq(' + colid + ')', tr).text();
				}

				// console.log(key + "-- " + colid + "--" + value + "\n");

				obj[key] = value;
			}
		} else {
			// 取所有的列
			for ( var prop in colNo) {
				var key = prop;
				var colid = colNo[key];

				var value = "";
				if (colid != undefined) {
					value = $('div:eq(' + colid + ')', tr).text();
				}

				// console.log(key + "-- " + colid + "--" + value + "\n");

				obj[key] = value;
			}
		}

		return obj;
	},
	/***************************************************************************
	 * 增加一行，如果data不为空，则按照date初始化该新增的行
	 * 
	 * @param gridId
	 * @param data
	 */
	addRowGrid : function(gridId, data) {
		//console.log("addRowGrid--" + gridId);

		var _grid = $("#" + gridId);

		var rowno = _grid.data("MAX_TR_NO") + 1;
		var config = _grid.data("CONFIG");
		var colModel = config["colModel"];

		var dataTrRow = null;
		if (data) {
			dataTrRow = data;
		}

		var hdiv = $('.hDiv', _grid.parent().parent());

		var tr = this.createRow(colModel, rowno, dataTrRow, hdiv);

		$('tbody', _grid).append(tr);

		this.addRowProp(tr);

		_grid.data("MAX_TR_NO", rowno);
	},
	/***************************************************************************
	 * 创建一个trRow
	 * 
	 * @param config
	 *            该trRow的配置
	 * @param rowno
	 *            该trRow所在row的行号
	 * @param data
	 *            该trRow的默认值
	 */
	createRow : function(config, rowno, data, hdiv) {
		var tr = document.createElement('tr');
		var _tr = $(tr);

		var striped = true; // 默认隔行变色

		if (rowno % 2 && striped) {
			tr.className = 'erow';
		}

		for (var i = 0; i < config.length; ++i) {
			var colModelTmp = config[i];
			var dataCell = ''; // 默认数据为空

			if (data && colModelTmp.name) {
				dataCell = data[colModelTmp.name];
			}

			var td = this.createCell(colModelTmp, rowno, i, dataCell, hdiv);

			_tr.append(td);
		}

		_tr.attr('tr_no', rowno);

		//console.log("ct tr ------- " + JSON.stringify(_tr) + "\n");

		return tr;
	},
	/***************************************************************************
	 * 创建一个tdCell
	 * 
	 * @param config
	 *            该Cell的配置
	 * @param rowno
	 *            该Cell所在row的行号
	 * @param cellno
	 *            该cell的编号
	 * @param data
	 *            该cell的默认值
	 */
	createCell : function(config, rowno, cellno, data, hdiv) {
		var td = document.createElement('td');

		td.align = config.align || 'left';
		$(td).attr('no', cellno);

		var m_type = this.getCMType(config);
		//console.log("m_type----" + m_type);

		var dataCell = '';
		if (data) {
			dataCell = data;
		}

		if (m_type == 'normal') {
			td.innerHTML = dataCell;
		} else if (m_type == 'radio') {
			var tdThis = '<input type="radio"  name="radioname" value="' + rowno + '"/>';

			td.innerHTML = tdThis;
		} else if (m_type == 'checkbox') {
			var tdThis = '<input type="checkbox" name="checkboxname" value="' + rowno + '"/>';

			td.innerHTML = tdThis;
		} else if (m_type == 'sno') {
			td.innerHTML = (rowno + 1);
		} else if (m_type == 'link') {
			var tdThis = "<a href='#' style='color:#8B008B; font-weight:bold; text-decoration:underline'>" + dataCell + "</a>";
			td.innerHTML = tdThis;
		} else if (m_type == 'buttons') {

			var buttonsTmp = config.buttons;

			if (buttonsTmp) {

				for (var num = 0; num < buttonsTmp.length; ++num) {
					var btncfg = buttonsTmp[num];
					var textBtn = btncfg.text;
					var nameBtn = btncfg.r_name;

					var btn = $("<input>");
					btn.addClass("btn_wd1_cell ");
					$(btn).css("font-size", "12px");
					btn.css("margin-right", "5px");
					btn.attr("type", "button");
					btn.attr("value", textBtn);
					btn.attr("tr_no", rowno);
					btn.attr("btnincell", "true");
					btn.attr("r_name", nameBtn);

					$(td).append(btn);
				}
			}
		}

		//console.log("ct td ------- " + JSON.stringify(td) + "\n");

		this.addCellProp(td, hdiv);

		return td;
	},
	/***************************************************************************
	 * 对td进行封装，转化成flexigrid的样式
	 * 
	 * @param td
	 */
	addCellProp : function(td, hdiv) {
		var tdDiv = document.createElement('div');
		var n = $(td).attr('no');
		var pth = $('th:eq(' + n + ')', hdiv).get(0);
		if (pth != null) {
			$(tdDiv).css({
				textAlign : pth.align,
				width : $('div:first', pth)[0].style.width
			});
			if (pth.hidden) {
				$(td).css('display', 'none');
			}
		}

		if (td.innerHTML == '') {
			td.innerHTML = '&nbsp;';
		}
		tdDiv.innerHTML = td.innerHTML;
		$(td).empty().append(tdDiv).removeAttr('width'); // wrap content
	},
	/***************************************************************************
	 * 对tr中列元素进行行为绑定处理
	 * 
	 * @param tr
	 */
	addRowProp : function(tr) {
		// support check and radio and button click
		$('td div input', $(tr)).on('click', function(e) {
			var _this = $(this);
			var _td = _this.parent().parent();
			var _tr = _td.parent();
			var gridid = _tr.parent().parent().attr("id");
			var type = _this.attr('type');
			//console.log("type--" + type);

			if (type == 'button') {

				var rname = $(this).attr("r_name");
				var btncfg = mou_grid_ux.getBtnInCellCfg(gridid, rname);

				var callback = btncfg.callback;
				var paramConfig = btncfg.paramConfig;

				if (callback && paramConfig) {
					var obj = mou_grid_ux.getTrObj(_tr, paramConfig);
					callback(obj);
				}
			} else if (type == 'radio' || type == 'checkbox') {
				if (type == 'radio') {
					_tr.siblings().removeClass('trSelected');
					_tr.addClass('trSelected');

				} else {
					if (_this[0].checked) {
						if (!_tr.hasClass('trSelected')) {
							_tr.addClass('trSelected');
						}
					} else {
						if (_tr.hasClass('trSelected')) {
							_tr.removeClass('trSelected');
						}
					}
				}
			}
		});

		// 行单击
		$(tr).on('click', function(e) {
			var obj = (e.target || e.srcElement);
			if (obj.href || obj.type)
				return true;
			if (e.ctrlKey || e.metaKey) {
				// mousedown already took care of this case
				return;
			}
			$(this).addClass('trSelected');

			// console.log("c-----length----" + $('td div
			// input[type=checkbox]',$(this)).length);
			if ($('td div input[type=checkbox]', $(this)).length > 0) {
				$('td div input[type=checkbox]', $(this)).trigger('click');
			}

			// console.log("r-----length----" + $('td div
			// input[type=checkbox]',$(this)).length);
			if ($('td div input[type=radio]', $(this)).length > 0) {
				$('td div input[type=radio]', $(this)).trigger('click');
			}

		});
	},
	/***************************************************************************
	 * 删除grid指定的行
	 * 
	 * @param gridId
	 * @param rownos
	 */
	removeRowGrid : function(gridId, rownos) {
		//console.log("removeRowGrid--" + gridId);

		var _grid = $("#" + gridId);

		for (var i = 0; i < rownos.length; ++i) {
			var rowno = rownos[i];

			var tr = $('tr[tr_no=' + rowno + ']', _grid);
			//console.log("tr--" + tr);

			tr.remove();
			// $('tbody',_grid).append(tr);
		}
	},
	resetHeight_grid : function($parent, h) {
		$(".bDiv", $parent).height(h);
	},
	getTableH : function(grid, height) {

		var mDiv_h = $(".mDiv", grid).height() == null ? 0 : $(".mDiv", grid).height();
		var tDiv_h = $(".tDiv", grid).height() == null ? 0 : $(".tDiv", grid).height();
		var pDiv_h = $(".pDiv", grid).height();
		var vGrip_h = $(".vGrip", grid).height();
		var hDiv_h = $(".hDiv", grid).height();

		var table_h = 0;

		var navType = $.getNavigatorType();
		if ($.isIE()) {
			table_h = Math.ceil(height) - mDiv_h - tDiv_h - pDiv_h - vGrip_h - hDiv_h;
			var version = $.ieVersion();

			if (version == 8) {
				table_h = table_h - 6;
			}
		} else {
			table_h = Math.ceil(height) - Math.ceil(mDiv_h) - Math.ceil(tDiv_h) - Math.ceil(pDiv_h) - Math.ceil(vGrip_h) - Math.ceil(hDiv_h);
		}

		// var arr = [];
		// arr.push("mDiv_h--" + mDiv_h);
		// arr.push("tDiv_h--" + tDiv_h);
		// arr.push("pDiv_h--" + pDiv_h);
		// arr.push("vGrip_h--" + vGrip_h);
		// arr.push("hDiv_h--" + hDiv_h);

		// alert(arr.join("\n"));

		return table_h;
	},

	convert2ParamArray : function(jsondata) {

		var rtnArray = [];

		for ( var k in jsondata) {

			var tmp = {};
			tmp["name"] = k;
			tmp["value"] = jsondata[k];

			rtnArray.push(tmp);
		}
		return rtnArray;
	}

};

(function($) {
	/*
	 * jQuery 1.9 support. browser object has been removed in 1.9
	 */
	var browser = $.browser;

	if (!browser) {
		function uaMatch(ua) {
			ua = ua.toLowerCase();

			var match = /(chrome)[ \/]([\w.]+)/.exec(ua) || /(webkit)[ \/]([\w.]+)/.exec(ua) || /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(ua) || /(msie) ([\w.]+)/.exec(ua) || ua.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(ua) || [];

			return {
				browser : match[1] || "",
				version : match[2] || "0"
			};
		}
		;

		var matched = uaMatch(navigator.userAgent);
		browser = {};

		if (matched.browser) {
			browser[matched.browser] = true;
			browser.version = matched.version;
		}

		// Chrome is Webkit, but Webkit is also Safari.
		if (browser.chrome) {
			browser.webkit = true;
		} else if (browser.webkit) {
			browser.safari = true;
		}
	}

	/*
	 * ! START code from jQuery UI
	 * 
	 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about) Dual licensed
	 * under the MIT or GPL Version 2 licenses. http://jquery.org/license
	 * 
	 * http://docs.jquery.com/UI
	 */

	if (typeof $.support.selectstart != 'function') {
		$.support.selectstart = "onselectstart" in document.createElement("div");
	}

	if (typeof $.fn.disableSelection != 'function') {
		$.fn.disableSelection = function() {
			return this.bind(($.support.selectstart ? "selectstart" : "mousedown") + ".ui-disableSelection", function(event) {
				event.preventDefault();
			});
		};
	}

	/* END code from jQuery UI */

	// modified by NBQ 2012-5-31
	// 修改 p为 setting
	// 将p声明为变量，添加var声明。
	// $.addFlex = function(t, p) {
	$.addFlex = function(t, setting) {
		if (t.grid)
			return false; // return if already exist
		var p = $.extend({ // apply default properties
			grid_id : '', // grid的id
			parent_div_id:'',
			height : 200, // default height
			width : 'auto', // 宽度值，auto表示根据每列的宽度自动计算
			striped : true, // 是否显示斑纹效果，默认是奇偶交互的形式
			novstripe : false,
			minwidth : 30, // min width of columns
			minheight : 80, // min height of columns
			resizable : false, // 是否可调整大小，为false的话，将不会出现可变的拖动条
			url : false, // URL if using data from AJAX
			method : 'POST', // data sending method
			dataType : 'json', // type of data for AJAX, either
			// xml or json
			errormsg : '连接错误!',// 错误提示信息
			usepager : false, // 是否分页
			nowrap : true,// 是否不换行
			page : 1, // current page
			total : -1, // total pages
			useRp : true, // use the results per page select
			// box
			rp : 15,// 每页默认的结果数
			rpOptions : [ 10, 15, 20, 30, 50 ], // allowed
			// per-page
			// values
			title : false,// 是否包含标题
			idProperty : 'id',
			pagestat : '显示第 {from}-{to}条,共 {total} 条数据',// 显示当前页和总页面的样式
			pagetext : 'Page',
			outof : 'of',
			findtext : 'Find',
			params : [], // allow optional parameters to be
			// passed around
			procmsg : '正在处理,请稍候 ...',// 正在处理的提示信息
			query : '',
			qtype : '',
			nomsg : '没有符合条件的信息!',// 无结果的提示信息
			minColToggle : 1, // minimum allowed column to be
			// hidden
			showToggleBtn : true, // show or hide column
			// toggle popup
			hideOnSubmit : true,
			autoload : true,
			blockOpacity : 0.5,
			preProcess : false,
			addTitleToCell : false, // add a title attr to cells
			// with truncated contents
			dblClickResize : false, // auto resize column by
			// double clicking
			onDragCol : false,
			onToggleCol : false,
			onChangeSort : false,
			onDoubleClick : false,
			onSuccess : false,
			onError : false,
			onSubmit : false, // using a custom populate
			// function
			__mw : { // extendable middleware function
				// holding object
				datacol : function(p, col, val) { // middleware
					// for
					// formatting
					// data
					// columns
					var _col = (typeof p.datacol[col] == 'function') ? p.datacol[col](val) : val; // format
																									// column
																									// using
					// function
					if (typeof p.datacol['*'] == 'function') { // if
						// wildcard
						// function
						// exists
						return p.datacol['*'](_col); // run
						// wildcard
						// function
					} else {
						return _col; // return column without
						// wildcard
					}
				}
			},
			getGridClass : function(g) { // get the grid
				// class, always
				// returns g
				return g;
			},
			datacol : {}, // datacol middleware object
			// 'colkey': function(colval) {}
			colResize : true, // from:
			// http://stackoverflow.com/a/10615589
			colMove : true
		}, setting);

		// addey by nbq 2014-2-5
		// 缓存配置
		$(t).data("CONFIG", p);

		// 设置并初始化grid对应的配置信息
		mou_grid_ux.iniGridConfig($(t).attr("id"), p);

		$(t).show() // show if hidden
		.attr({
			cellPadding : 0,
			cellSpacing : 0,
			border : 0
		}) // remove padding and spacing
		.removeAttr('width'); // remove width properties
		// create grid class
		var g = {
			hset : {},
			rePosDrag : function() {
				var cdleft = 0 - this.hDiv.scrollLeft;
				if (this.hDiv.scrollLeft > 0)
					cdleft -= Math.floor(p.cgwidth / 2);
				$(g.cDrag).css({
					top : g.hDiv.offsetTop + 1
				});
				var cdpad = this.cdpad;
				var cdcounter = 0;
				$('div', g.cDrag).hide();
				$('thead tr:first th:visible', this.hDiv).each(function() {
					var n = $('thead tr:first th:visible', g.hDiv).index(this);
					var cdpos = parseInt($('div', this).width());
					if (cdleft == 0)
						cdleft -= Math.floor(p.cgwidth / 2);
					cdpos = cdpos + cdleft + cdpad;
					if (isNaN(cdpos)) {
						cdpos = 0;
					}
					$('div:eq(' + n + ')', g.cDrag).css({
						'left' : (!(browser.mozilla) ? cdpos - cdcounter : cdpos) + 'px'
					}).show();
					cdleft = cdpos;
					cdcounter++;
				});
			},
			fixHeight : function(newH) {
				newH = false;
				if (!newH)
					newH = $(g.bDiv).height();
				var hdHeight = $(this.hDiv).height();
				$('div', this.cDrag).each(function() {
					$(this).height(newH + hdHeight);
				});
				var nd = parseInt($(g.nDiv).height(), 10);
				if (nd > newH)
					$(g.nDiv).height(newH).width(200);
				else
					$(g.nDiv).height('auto').width('auto');
				$(g.block).css({
					height : newH,
					marginBottom : (newH * -1)
				});
				var hrH = g.bDiv.offsetTop + newH;
				if (p.height != 'auto' && p.resizable)
					hrH = g.vDiv.offsetTop;
				$(g.rDiv).css({
					height : hrH
				});
			},
			dragStart : function(dragtype, e, obj) { // default drag function
				// start
				if (dragtype == 'colresize' && p.colResize === true) {// column
					// resize
					$(g.nDiv).hide();
					$(g.nBtn).hide();
					var n = $('div', this.cDrag).index(obj);
					var ow = $('th:visible div:eq(' + n + ')', this.hDiv).width();
					$(obj).addClass('dragging').siblings().hide();
					$(obj).prev().addClass('dragging').show();
					this.colresize = {
						startX : e.pageX,
						ol : parseInt(obj.style.left, 10),
						ow : ow,
						n : n
					};
					$('body').css('cursor', 'col-resize');
				} else if (dragtype == 'vresize') {// table resize
					var hgo = false;
					$('body').css('cursor', 'row-resize');
					if (obj) {
						hgo = true;
						$('body').css('cursor', 'col-resize');
					}
					this.vresize = {
						h : p.height,
						sy : e.pageY,
						w : p.width,
						sx : e.pageX,
						hgo : hgo
					};
				} else if (dragtype == 'colMove') {// column header drag
					$(e.target).disableSelection(); // disable selecting the
					// column header
					if ((p.colMove === true)) {
						$(g.nDiv).hide();
						$(g.nBtn).hide();
						this.hset = $(this.hDiv).offset();
						this.hset.right = this.hset.left + $('table', this.hDiv).width();
						this.hset.bottom = this.hset.top + $('table', this.hDiv).height();
						this.dcol = obj;
						this.dcoln = $('th', this.hDiv).index(obj);
						this.colCopy = document.createElement("div");
						this.colCopy.className = "colCopy";
						this.colCopy.innerHTML = obj.innerHTML;
						if (browser.msie) {
							this.colCopy.className = "colCopy ie";
						}
						$(this.colCopy).css({
							position : 'absolute',
							'float' : 'left',
							display : 'none',
							textAlign : obj.align
						});
						$('body').append(this.colCopy);
						$(this.cDrag).hide();
					}
				}
				$('body').noSelect();
			},
			dragMove : function(e) {
				if (this.colresize) {// column resize
					var n = this.colresize.n;
					var diff = e.pageX - this.colresize.startX;
					var nleft = this.colresize.ol + diff;
					var nw = this.colresize.ow + diff;
					if (nw > p.minwidth) {
						$('div:eq(' + n + ')', this.cDrag).css('left', nleft);
						this.colresize.nw = nw;
					}
				} else if (this.vresize) {// table resize
					var v = this.vresize;
					var y = e.pageY;
					var diff = y - v.sy;
					if (!p.defwidth)
						p.defwidth = p.width;
					if (p.width != 'auto' && !p.nohresize && v.hgo) {
						var x = e.pageX;
						var xdiff = x - v.sx;
						var newW = v.w + xdiff;
						if (newW > p.defwidth) {
							this.gDiv.style.width = newW + 'px';
							p.width = newW;
						}
					}
					var newH = v.h + diff;
					if ((newH > p.minheight || p.height < p.minheight) && !v.hgo) {
						this.bDiv.style.height = newH + 'px';
						p.height = newH;
						this.fixHeight(newH);
					}
					v = null;
				} else if (this.colCopy) {
					$(this.dcol).addClass('thMove').removeClass('thOver');
					if (e.pageX > this.hset.right || e.pageX < this.hset.left || e.pageY > this.hset.bottom || e.pageY < this.hset.top) {
						// this.dragEnd();
						$('body').css('cursor', 'move');
					} else {
						$('body').css('cursor', 'pointer');
					}
					$(this.colCopy).css({
						top : e.pageY + 10,
						left : e.pageX + 20,
						display : 'block'
					});
				}
			},
			dragEnd : function() {
				if (this.colresize) {
					var n = this.colresize.n;
					var nw = this.colresize.nw;
					$('th:visible div:eq(' + n + ')', this.hDiv).css('width', nw);
					$('tr', this.bDiv).each(function() {
						var $tdDiv = $('td:visible div:eq(' + n + ')', this);
						$tdDiv.css('width', nw);
						g.addTitleToCell($tdDiv);
					});
					this.hDiv.scrollLeft = this.bDiv.scrollLeft;
					$('div:eq(' + n + ')', this.cDrag).siblings().show();
					$('.dragging', this.cDrag).removeClass('dragging');
					this.rePosDrag();
					this.fixHeight();
					this.colresize = false;
					if ($.cookies) {
						var name = p.colModel[n].name; // Store the widths in
						// the cookies
						$.cookie('flexiwidths/' + name, nw);
					}
				} else if (this.vresize) {
					this.vresize = false;
				} else if (this.colCopy) {
					$(this.colCopy).remove();
					if (this.dcolt !== null) {
						if (this.dcoln > this.dcolt)
							$('th:eq(' + this.dcolt + ')', this.hDiv).before(this.dcol);
						else
							$('th:eq(' + this.dcolt + ')', this.hDiv).after(this.dcol);
						this.switchCol(this.dcoln, this.dcolt);
						$(this.cdropleft).remove();
						$(this.cdropright).remove();
						this.rePosDrag();
						if (p.onDragCol) {
							p.onDragCol(this.dcoln, this.dcolt);
						}
					}
					this.dcol = null;
					this.hset = null;
					this.dcoln = null;
					this.dcolt = null;
					this.colCopy = null;
					$('.thMove', this.hDiv).removeClass('thMove');
					$(this.cDrag).show();
				}
				$('body').css('cursor', 'default');
				$('body').noSelect(false);
			},
			toggleCol : function(cid, visible) {
				var ncol = $("th[axis='col" + cid + "']", this.hDiv)[0];
				var n = $('thead th', g.hDiv).index(ncol);
				var cb = $('input[value=' + cid + ']', g.nDiv)[0];
				if (visible == null) {
					visible = ncol.hidden;
				}
				if ($('input:checked', g.nDiv).length < p.minColToggle && !visible) {
					return false;
				}
				if (visible) {
					ncol.hidden = false;
					$(ncol).show();
					cb.checked = true;
				} else {
					ncol.hidden = true;
					$(ncol).hide();
					cb.checked = false;
				}
				$('tbody tr', t).each(function() {
					if (visible) {
						$('td:eq(' + n + ')', this).show();
					} else {
						$('td:eq(' + n + ')', this).hide();
					}
				});
				this.rePosDrag();
				if (p.onToggleCol) {
					p.onToggleCol(cid, visible);
				}
				return visible;
			},
			switchCol : function(cdrag, cdrop) { // switch columns
				$('tbody tr', t).each(function() {
					if (cdrag > cdrop)
						$('td:eq(' + cdrop + ')', this).before($('td:eq(' + cdrag + ')', this));
					else
						$('td:eq(' + cdrop + ')', this).after($('td:eq(' + cdrag + ')', this));
				});
				// switch order in nDiv
				if (cdrag > cdrop) {
					$('tr:eq(' + cdrop + ')', this.nDiv).before($('tr:eq(' + cdrag + ')', this.nDiv));
				} else {
					$('tr:eq(' + cdrop + ')', this.nDiv).after($('tr:eq(' + cdrag + ')', this.nDiv));
				}
				if (browser.msie && browser.version < 7.0) {
					$('tr:eq(' + cdrop + ') input', this.nDiv)[0].checked = true;
				}
				this.hDiv.scrollLeft = this.bDiv.scrollLeft;
			},
			scroll : function() {
				this.hDiv.scrollLeft = this.bDiv.scrollLeft;
				this.rePosDrag();
			},
			addData : function(data) { // parse data
				// added by nbq 2014-2-4
				// 清空之前登记的选中对象
				var gridid = $(t).attr("id");

				if (p.dataType == 'json') {
					data = $.extend({
						rows : [],
						page : 0,
						total : 0
					}, data);
				}
				if (p.preProcess) {
					data = p.preProcess(data);
				}
				$('.pReload', this.pDiv).removeClass('loading');
				this.loading = false;
				if (!data) {
					$('.pPageStat', this.pDiv).html(p.errormsg);
					if (p.onSuccess)
						p.onSuccess(this);
					return false;
				}
				if (p.dataType == 'xml') {
					p.total = +$('rows total', data).text();
				} else {
					p.total = data.total;
				}
				if (p.total === 0) {
					$('tr, a, td, div', t).unbind();
					$(t).empty();
					p.pages = 1;
					p.page = 1;
					this.buildpager();
					$('.pPageStat', this.pDiv).html(p.nomsg);
					if (p.onSuccess)
						p.onSuccess(this);
					return false;
				}
				p.pages = Math.ceil(p.total / p.rp);
				if (p.dataType == 'xml') {
					p.page = +$('rows page', data).text();
				} else {
					p.page = data.page;
				}
				this.buildpager();
				// build new body
				var tbody = document.createElement('tbody');
				if (p.dataType == 'json') {
					$.each(data.rows, function(i, row) {
						var tr = document.createElement('tr');
						var jtr = $(tr);
						if (row.name)
							tr.name = row.name;
						if (row.color) {
							jtr.css('background', row.color);
						} else {
							if (i % 2 && p.striped)
								tr.className = 'erow';
						}
						if (row[p.idProperty]) {
							tr.id = 'row' + row[p.idProperty];
							jtr.attr('data-id', row[p.idProperty]);
						}

						// 增加数据选中支持
						var selected = false;

						$('thead tr:first th', g.hDiv).each(
						// add cell
						function() {
							var td = document.createElement('td');
							var idx = $(this).attr('axis').substr(3);
							var colModelTmp = p.colModel[idx];
							td.align = this.align;
							$(td).attr('no', idx); // 给每一列编号，便于后续选中追溯
							// If each row is
							// the object itself
							// (no 'cell' key)
							if (typeof row.cell == 'undefined') {
								// modified by
								// nbq 2014-1-25
								// support the
								// auto no, com
								// box, radio
								// console.log("p.colModel[idx].name----"
								// +
								// p.colModel[idx].name);
								var m_type = mou_grid_ux.getCMType(colModelTmp);
								// console.log("m_type----"
								// + m_type);
								if (m_type == 'normal') {
									var colModelName_ = colModelTmp.name;
									var inner_value = "";
									if (colModelName_.indexOf(".") < 0) {
										inner_value = row[colModelName_];
									} else {
										var names_ = colModelName_.split(".");

										var data_iHtml = row;

										for (var _iiii = 0; _iiii < names_.length - 1; ++_iiii) {
											data_iHtml = data_iHtml[names_[_iiii]];
										}

										inner_value = data_iHtml[names_[names_.length - 1]];
									}

									if (colModelTmp.condition) {
										inner_value = colModelTmp.condition[inner_value];
									}
									td.innerHTML = $.htmlDecode(inner_value);
								} else if (m_type == 'radio') {
									
									var valname = colModelTmp.valname;
									var attrval = i;
									
									var attrarr = [];
									if (valname){
										
										var valnames = valname.split(",");
										
										for (var iiiii = 0; iiiii<valnames.length; ++iiiii){
											
											var valnametmp = valnames[iiiii];
											var valtmp = row[valnametmp];
											
											attrarr.push(" " + valnametmp + "='" + valtmp + "'  ");
										}
									}
									
									var toAddAttrStr = "";
									if (attrarr.length>0){
										toAddAttrStr = attrarr.join("");
									}
									
									var tdThis = '<input type="radio"  name="radioname" value="' + attrval + '" '+ toAddAttrStr + ' />';
									if (selected == true) {
										tdThis = '<input type="radio" checked="checked" name="radioname" value="' + attrval + '" '+ toAddAttrStr + '/>';
									}

									td.innerHTML = tdThis;
								} else if (m_type == 'checkbox') {
									var tdThis = '<input type="checkbox" name="checkboxname" value="' + i + '"/>';
									if (selected == true) {
										tdThis = '<input type="checkbox" checked="checked" name="checkboxname" value="' + i + '"/>';
									}

									td.innerHTML = tdThis;
								} else if (m_type == 'sno') {
									td.innerHTML = (i + 1);
								} else if (m_type == 'link') {
									var tdThis = "<a href='#' style='color:#8B008B; font-weight:bold; text-decoration:underline'>" + $.htmlDecode(row[colModelTmp.name]) + "</a>";
									td.innerHTML = tdThis;
								} else if (m_type == 'image') {

									var value_this;
									var colModelName_ = colModelTmp.name;
									if (colModelName_.indexOf(".") < 0) {
										value_this = row[colModelName_];
									} else {
										var names_ = colModelName_.split(".");

										var data_iHtml = row;

										try {
											for (var _iiii = 0; _iiii < names_.length - 1; ++_iiii) {
												data_iHtml = data_iHtml[names_[_iiii]];
											}

											value_this = data_iHtml[names_[names_.length - 1]];
										} catch (e) {
											value_this = '';
										}
									}

									if (value_this && value_this.length > 0) {
										var tdThis = '<img id="imgurl_show" src="' + $.getSitePath() + value_this + '" style="width:100%"></div>';
									} else {
										var tdThis = "";
									}

									td.innerHTML = tdThis;
								} else if (m_type == 'buttons') {

									var buttonsTmp = colModelTmp.buttons;

									if (buttonsTmp) {

										for (var num = 0; num < buttonsTmp.length; ++num) {
											var btncfg = buttonsTmp[num];
											var textBtn = btncfg.text;
											var nameBtn = btncfg.r_name;
											var css = btncfg.css || "btn btn-xs btn-primary";

											var inner_value = row[colModelName_];

											if (btncfg["r_reltive"]) {
												var relValue = row[btncfg["r_reltive"]];

												textBtn = btncfg.text[relValue];
												css = btncfg.css[relValue] || css;
											}

											var btn = $("<input>");

											if (css) {
												btn.addClass(css);
											}

											$(btn).css("font-size", "12px");
											$(btn).css("margin-right","5px");
											btn.attr("type", "button");
											btn.attr("value", textBtn);
											btn.attr("tr_no", i);
											btn.attr("btnincell", "true");
											btn.attr("r_name", nameBtn);

											$(td).append(btn);
										}
									}
								}

							} else {
								// If the json
								// elements
								// aren't named
								// (which is
								// typical), use
								// numeric order
								var iHTML = '';
								if (typeof row.cell[idx] != "undefined") {
									iHTML = (row.cell[idx] !== null) ? row.cell[idx] : ''; // null-check
								} else {
									iHTML = row.cell[p.colModel[idx].name];
								}
								td.innerHTML = p.__mw.datacol(p, $(this).attr('abbr'), iHTML); //
							}
							var offs = td.innerHTML.indexOf('<BGCOLOR=');
							if (offs > 0) {
								$(td).css('background', textBtn.substr(offs + 7, 7));
							}

							$(td).attr('abbr', $(this).attr('abbr'));

							$(tr).append(td);
							td = null;
						});
						if ($('thead', this.gDiv).length < 1) {// handle
							for (idx = 0; idx < row.cell.length; idx++) {
								var td = document.createElement('td');
								if (typeof row.cell[idx] != "undefined") {
									td.innerHTML = (row.cell[idx] != null) ? row.cell[idx] : '';// null-check
									// for
									// Opera-browser
								} else {
									td.innerHTML = row.cell[p.colModel[idx].name];
								}
								$(tr).append(td);
								td = null;
							}
						}
						// added by nbq 2014-02-04
						// reg the tr no
						$(tr).attr('tr_no', i);
						$(t).data("MAX_TR_NO", i); // 存储目前最大行

						$(tbody).append(tr);
						tr = null;
					});
				}
				$('tr', t).unbind();
				$(t).empty();
				$(t).append(tbody);
				this.addCellProp();
				this.addRowProp();
				this.rePosDrag();
				tbody = null;
				data = null;
				i = null;
				if (p.hideOnSubmit) {
					$(g.block).remove();
				}
				this.hDiv.scrollLeft = this.bDiv.scrollLeft;
				if (browser.opera) {
					$(t).css('visibility', 'visible');
				}
				if (p.onSuccess) {
					p.onSuccess(this);
				}
			},
			changeSort : function(th) { // change sortorder
				if (this.loading) {
					return true;
				}
				$(g.nDiv).hide();
				$(g.nBtn).hide();
				if (p.sortname == $(th).attr('abbr')) {
					if (p.sortorder == 'asc') {
						p.sortorder = 'desc';
					} else {
						p.sortorder = 'asc';
					}
				}
				$(th).addClass('sorted').siblings().removeClass('sorted');
				$('.sdesc', this.hDiv).removeClass('sdesc');
				$('.sasc', this.hDiv).removeClass('sasc');
				$('div', th).addClass('s' + p.sortorder);
				p.sortname = $(th).attr('abbr');
				if (p.onChangeSort) {
					p.onChangeSort(p.sortname, p.sortorder);
				} else {
					this.populate();
				}
			},
			buildpager : function() { // rebuild pager based on new properties
				$('.pcontrol input', this.pDiv).val(p.page);
				$('.pcontrol span', this.pDiv).html(p.pages);
				var r1 = (p.page - 1) * p.rp + 1;
				var r2 = r1 + p.rp - 1;
				if (p.total < r2) {
					r2 = p.total;
				}
				var stat = p.pagestat;
				stat = stat.replace(/{from}/, r1);
				stat = stat.replace(/{to}/, r2);
				stat = stat.replace(/{total}/, p.total);
				$('.pPageStat', this.pDiv).html(stat);
			},
			populate : function() { // get latest data
				if (this.loading) {
					return true;
				}
				if (p.onSubmit) {
					var gh = p.onSubmit();
					if (!gh) {
						return false;
					}
				}
				this.loading = true;
				if (!p.url) {
					return false;
				}
				$('.pPageStat', this.pDiv).html(p.procmsg);
				$('.pReload', this.pDiv).addClass('loading');
				$(g.block).css({
					top : g.bDiv.offsetTop
				});
				if (p.hideOnSubmit) {
					$(this.gDiv).prepend(g.block);
				}
				if (browser.opera) {
					$(t).css('visibility', 'hidden');
				}
				if (!p.newp) {
					p.newp = 1;
				}

				var total = p.total;
				for (var pi = 0; pi < p.params.length; pi++) {
					if (p.params[pi]['name'] == 'reload') {
						total = -1;
						break;
					}
				}

				if (p.page > p.pages) {
					p.page = p.pages;
				}
				var param = [ {
					name : 'page',
					value : p.newp
				}, {
					name : 'rp',
					value : p.rp
				}, {
					name : 'total',
					value : total
				}, {
					name : 'sortname',
					value : p.sortname
				}, {
					name : 'sortorder',
					value : p.sortorder
				}, {
					name : 'query',
					value : p.query
				}, {
					name : 'qtype',
					value : p.qtype
				} ];
				if (p.params.length) {
					for (var pi = 0; pi < p.params.length; pi++) {

						param[param.length] = p.params[pi];
					}
				}
				$.ajax({
					type : p.method,
					url : p.url,
					data : param,
					dataType : p.dataType,
					success : function(data) {
						g.addData(data);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						try {
							if (p.onError)
								p.onError(XMLHttpRequest, textStatus, errorThrown);
						} catch (e) {
						}
					}
				});
			},
			doSearch : function() {
				p.query = $('input[name=q]', g.sDiv).val();
				p.qtype = $('select[name=qtype]', g.sDiv).val();
				p.newp = 1;
				this.populate();
			},
			changePage : function(ctype) { // change page
				if (this.loading) {
					return true;
				}
				switch (ctype) {
				case 'first':
					p.newp = 1;
					break;
				case 'prev':
					if (p.page > 1) {
						p.newp = parseInt(p.page, 10) - 1;
					}
					break;
				case 'next':
					if (p.page < p.pages) {
						p.newp = parseInt(p.page, 10) + 1;
					}
					break;
				case 'last':
					p.newp = p.pages;
					break;
				case 'input':
					var nv = parseInt($('.pcontrol input', this.pDiv).val(), 10);
					if (isNaN(nv)) {
						nv = 1;
					}
					if (nv < 1) {
						nv = 1;
					} else if (nv > p.pages) {
						nv = p.pages;
					}
					$('.pcontrol input', this.pDiv).val(nv);
					p.newp = nv;
					break;
				}
				if (p.newp == p.page) {
					return false;
				}
				if (p.onChangePage) {
					p.onChangePage(p.newp);
				} else {
					this.populate();
				}
			},
			addCellProp : function() {
				$('tbody tr td', g.bDiv).each(function() {
					var tdDiv = document.createElement('div');
					var n = $('td', $(this).parent()).index(this);
					var pth = $('th:eq(' + n + ')', g.hDiv).get(0);
					if (pth != null) {
						if (p.sortname == $(pth).attr('abbr') && p.sortname) {
							this.className = 'sorted';
						}
						$(tdDiv).css({
							textAlign : pth.align,
							width : $('div:first', pth)[0].style.width
						});
						if (pth.hidden) {
							$(this).css('display', 'none');
						}
					}
					if (p.nowrap == false) {
						$(tdDiv).css('white-space', 'normal');
					}
					if (this.innerHTML == '') {
						this.innerHTML = '&nbsp;';
					}
					tdDiv.innerHTML = this.innerHTML;
					var prnt = $(this).parent()[0];
					var pid = false;
					if (prnt.id) {
						pid = prnt.id.substr(3);
					}
					if (pth != null) {
						if (pth.process)
							pth.process(tdDiv, pid);
					}
					$(this).empty().append(tdDiv).removeAttr('width'); // wrap
					// content
					g.addTitleToCell(tdDiv);
				});
			},
			getCellDim : function(obj) {// get cell prop for editable event
				var ht = parseInt($(obj).height(), 10);
				var pht = parseInt($(obj).parent().height(), 10);
				var wt = parseInt(obj.style.width, 10);
				var pwt = parseInt($(obj).parent().width(), 10);
				var top = obj.offsetParent.offsetTop;
				var left = obj.offsetParent.offsetLeft;
				var pdl = parseInt($(obj).css('paddingLeft'), 10);
				var pdt = parseInt($(obj).css('paddingTop'), 10);
				return {
					ht : ht,
					wt : wt,
					top : top,
					left : left,
					pdl : pdl,
					pdt : pdt,
					pht : pht,
					pwt : pwt
				};
			},
			addRowProp : function() {
				// addey by nbq 2014-2-4
				// support check and radio and button click
				$('tbody tr td div input', g.bDiv).on('click', function(e) {
					var _this = $(this);
					var _td = _this.parent().parent();
					var _tr = _td.parent();
					var gridid = _tr.parent().parent().attr("id");
					var type = _this.attr('type');
					//console.log("type--" + type);

					if (type == 'button') {

						var rname = $(this).attr("r_name");
						var btncfg = mou_grid_ux.getBtnInCellCfg(gridid, rname);

						var callback = btncfg.callback;
						var paramConfig = btncfg.paramConfig;

						if (callback && paramConfig) {
							var obj = mou_grid_ux.getTrObj(_tr, paramConfig);
							callback(obj, $(this), e);
						}
					} else if (type == 'radio' || type == 'checkbox') {
						// 生成选中的对象
						if (type == 'radio') {
							_tr.siblings().removeClass('trSelected');
							_tr.addClass('trSelected');

						} else {
							if (_this[0].checked) {
								if (!_tr.hasClass('trSelected')) {
									_tr.addClass('trSelected');
								}
							} else {
								if (_tr.hasClass('trSelected')) {
									_tr.removeClass('trSelected');
								}
							}
						}
					}
				});

				// addey by nbq 2014-2-5
				// support link click
				$('tbody tr td div a', g.bDiv).on('click', function(e) {
					var _this = $(this);
					var _td = _this.parent().parent();
					var _tr = _td.parent();
					var gridid = _tr.parent().parent().attr("id");

					//console.log("grid_id--" + gridid);

					// 生成选中的对象
					var config_select = mou_grid_ux.getTdSelect(gridid, _td.attr('no'));
					var obj = mou_grid_ux.getTrObj(_tr, config_select);

					var cmTmp = mou_grid_ux.getColModel(gridid, _td.attr('no'));
					var callback = cmTmp.callback;
					if (callback) {
						callback(obj, $(this), e);
					}
				});

				$('tbody tr', g.bDiv).on('click', function(e) {
					var obj = (e.target || e.srcElement);
					if (obj.href || obj.type)
						return true;
					if (e.ctrlKey || e.metaKey) {
						// mousedown already took care of this
						// case
						return;
					}
					$(this).toggleClass('trSelected');

					// added by nbq 2014-02-04
					// check the radio or checkbox
					// console.log("c-----length----" + $('td
					// div
					// input[type=checkbox]',$(this)).length);
					if ($('td div input[type=checkbox]', $(this)).length > 0) {
						$('td div input[type=checkbox]', $(this)).trigger('click');
					}

					// console.log("r-----length----" + $('td
					// div input[type=radio]',$(this)).length);
					if ($('td div input[type=radio]', $(this)).length > 0) {
						$('td div input[type=radio]', $(this)).trigger('click');
					}
				}).on('mousedown', function(e) {
					if (e.shiftKey) {
						$(this).toggleClass('trSelected');
						g.multisel = true;
						this.focus();
						$(g.gDiv).noSelect();
					}
					if (e.ctrlKey || e.metaKey) {
						$(this).toggleClass('trSelected');
						g.multisel = true;
						this.focus();
					}
				}).on('mouseup', function(e) {
					if (g.multisel && !(e.ctrlKey || e.metaKey)) {
						g.multisel = false;
						$(g.gDiv).noSelect(false);
					}
				}).on('dblclick', function() {
					if (p.onDoubleClick) {
						p.onDoubleClick(this, g, p);
					}
				}).hover(function(e) {
					if (g.multisel && e.shiftKey) {
						$(this).toggleClass('trSelected');
					}
				}, function() {
				});
				if (browser.msie && browser.version < 7.0) {
					$(this).hover(function() {
						$(this).addClass('trOver');
					}, function() {
						$(this).removeClass('trOver');
					});
				}
			},

			combo_flag : true,
			combo_resetIndex : function(selObj) {
				if (this.combo_flag) {
					selObj.selectedIndex = 0;
				}
				this.combo_flag = true;
			},
			combo_doSelectAction : function(selObj) {
				eval(selObj.options[selObj.selectedIndex].value);
				selObj.selectedIndex = 0;
				this.combo_flag = false;
			},
			// Add title attribute to div if cell contents is truncated
			addTitleToCell : function(tdDiv) {
				if (p.addTitleToCell) {
					var $span = $('<span />').css('display', 'none'), $div = (tdDiv instanceof jQuery) ? tdDiv : $(tdDiv), div_w = $div.outerWidth(), span_w = 0;

					$('body').children(':first').before($span);
					$span.html($div.html());
					$span.css('font-size', '' + $div.css('font-size'));
					$span.css('padding-left', '' + $div.css('padding-left'));
					span_w = $span.innerWidth();
					$span.remove();

					if (span_w > div_w) {
						$div.attr('title', $div.text());
					} else {
						$div.removeAttr('title');
					}
				}
			},
			autoResizeColumn : function(obj) {
				if (!p.dblClickResize) {
					return;
				}
				var n = $('div', this.cDrag).index(obj), $th = $('th:visible div:eq(' + n + ')', this.hDiv), ol = parseInt(obj.style.left, 10), ow = $th.width(), nw = 0, nl = 0, $span = $('<span />');
				$('body').children(':first').before($span);
				$span.html($th.html());
				$span.css('font-size', '' + $th.css('font-size'));
				$span.css('padding-left', '' + $th.css('padding-left'));
				$span.css('padding-right', '' + $th.css('padding-right'));
				nw = $span.width();
				$('tr', this.bDiv).each(function() {
					var $tdDiv = $('td:visible div:eq(' + n + ')', this), spanW = 0;
					$span.html($tdDiv.html());
					$span.css('font-size', '' + $tdDiv.css('font-size'));
					$span.css('padding-left', '' + $tdDiv.css('padding-left'));
					$span.css('padding-right', '' + $tdDiv.css('padding-right'));
					spanW = $span.width();
					nw = (spanW > nw) ? spanW : nw;
				});
				$span.remove();
				nw = (p.minWidth > nw) ? p.minWidth : nw;
				nl = ol + (nw - ow);
				$('div:eq(' + n + ')', this.cDrag).css('left', nl);
				this.colresize = {
					nw : nw,
					n : n
				};
				g.dragEnd();
			},
			pager : 0
		};

		g = p.getGridClass(g); // get the grid class

		if (p.colModel) { // create model if any
			thead = document.createElement('thead');
			var tr = document.createElement('tr');

			var sumW = 0;
			
			for (var i = 0; i < p.colModel.length; i++) {
				var cm = p.colModel[i];
				var th = document.createElement('th');
				$(th).attr('axis', 'col' + i);

				if (cm) { // only use cm if its defined
					var m_type = mou_grid_ux.getCMType(cm);
					// console.log("m_type----" + m_type);

					$(th).attr('axis', 'col' + i);
					// added by nbq 2014-1-27
					// 标记该列的名字，备使用
					$(th).attr('name_cm', cm.name || m_type);
					$(th).attr('mtype_cm', m_type);

					if ($.cookies) {
						var cookie_width = 'flexiwidths/' + cm.name; // Re-Store
						// the
						// widths
						// in
						// the
						// cookies
						if ($.cookie(cookie_width) != undefined) {
							cm.width = $.cookie(cookie_width);
						}
					}

					// 2014-2-8 added by nbq
					// 支持全选、全部取消
					if (m_type == 'checkbox') {
						var tdThis = '<input type="checkbox" name="checkboxname" value="all"  style="margin-bottom: 0px; margin-top: 0px;"/>';
						th.innerHTML = tdThis;
						$(th).on('click', function() {

							var _grid = $("#" + p["grid_id"]);
							var flg = $('input', $(this))[0].checked;

							$('tbody tr td div input[type=checkbox]', _grid).each(function() {
								var _this = $(this);
								if (flg) {
									_this[0].checked = true;
								} else {
									_this[0].checked = false;
								}
							});
						});
					} else {
						if (cm.display != undefined) {
							th.innerHTML = cm.display;
						}
					}

					if (cm.name && cm.sortable) {
						$(th).attr('abbr', cm.name);
					}
					if (cm.align) {
						th.align = cm.align;
					} else {
						// added by nbq -- 2014-1-25
						// default align -- left
						th.align = "left";
					}

					var cell_w = 40;// 默认是40
					
					if (cm.width) {
						cell_w = cm.width;
					} else {
						// added by nbq -- 2014-1-25
						// cal default width of cell
						if (m_type == 'normal' || m_type == 'link') {
							cell_w = $.getFGridColWidth(cm.display);
						} else if (m_type == 'radio' || m_type == 'checkbox' || m_type == 'sno') {
							cell_w = 35;
						}
						
						if (cm.fill){
							// 填满
							//var gridW = $("#material_grid_div").width();
							var gridW = $("#"+p.grid_id).width();
							
							if (gridW == 0){
								gridW = $("#"+p.parent_div_id).width();
							}
							cell_w = gridW - sumW -12;
						}
						// console.log(cm.display + "----" +cell_w);
					}
					$(th).attr('width', cell_w);

					if ($(cm).attr('hide')) {
						th.hidden = true;
					}else{
						sumW = sumW + cell_w;
					}
					
					if (cm.process) {
						th.process = cm.process;
					}
				} else {
					th.innerHTML = "";
					$(th).attr('width', 30);
				}
				$(tr).append(th);
			}
			$(thead).append(tr);
			$(t).prepend(thead);
		} // end if p.colmodel
		// init divs
		g.gDiv = document.createElement('div'); // create global container
		g.mDiv = document.createElement('div'); // create title container
		g.hDiv = document.createElement('div'); // create header container
		g.bDiv = document.createElement('div'); // create body container
		g.vDiv = document.createElement('div'); // create grip
		g.rDiv = document.createElement('div'); // create horizontal resizer
		g.cDrag = document.createElement('div'); // create column drag
		g.block = document.createElement('div'); // creat blocker
		g.nDiv = document.createElement('div'); // create column show/hide popup
		g.nBtn = document.createElement('div'); // create column show/hide
		// button
		g.iDiv = document.createElement('div'); // create editable layer
		g.tDiv = document.createElement('div'); // create toolbar
		g.sDiv = document.createElement('div');
		g.pDiv = document.createElement('div'); // create pager container

		if (p.colResize === false) { // don't display column drag if we are
			// not using it
			$(g.cDrag).css('display', 'none');
		}

		if (!p.usepager) {
			g.pDiv.style.display = 'none';
		}
		g.hTable = document.createElement('table');
		g.gDiv.className = 'flexigrid';
		if (p.width != 'auto') {
			g.gDiv.style.width = p.width + (isNaN(p.width) ? '' : 'px');
		}
		// add conditional classes
		if (browser.msie) {
			$(g.gDiv).addClass('ie');
		}
		if (p.novstripe) {
			$(g.gDiv).addClass('novstripe');
		}
		$(t).before(g.gDiv);
		$(g.gDiv).append(t);
		// set toolbar
		if (p.buttons) {
			g.tDiv.className = 'tDiv';
			var tDiv2 = document.createElement('div');
			tDiv2.className = 'tDiv2';
			for (var i = 0; i < p.buttons.length; i++) {
				var btn = p.buttons[i];
				if (!btn.separator) {
					var btnDiv = document.createElement('div');
					btnDiv.className = 'fbutton';
					btnDiv.innerHTML = ("<div><span>") + (btn.hidename ? "&nbsp;" : btn.name) + ("</span></div>");
					if (btn.bclass) {
						$('span', btnDiv).addClass(btn.bclass)
					}
					
					
					
					if (btn.bimage) // if bimage defined, use its string as an
					// image url for this buttons style (RS)
					{
						$('span', btnDiv).css('background', 'url(' + btn.bimage + ') no-repeat center left');
						$('span', btnDiv).css('paddingLeft', 20);
					}

					if (btn.tooltip) // add title if exists (RS)
					{
						$('span', btnDiv)[0].title = btn.tooltip;
					}

					btnDiv.onpress = btn.onpress;
					$(btnDiv).attr('title', btn.title);
					$(btnDiv).attr('name', btn.name);
					if (btn.id) {
						btnDiv.id = btn.id;
					}

					if (btn.onpress) {
						$(btnDiv).click(function() {
							// this.onpress(this.id || this.name, g.gDiv);
							this.onpress($(this), g);
						});
					}
					$(tDiv2).append(btnDiv);
					if (browser.msie && browser.version < 7.0) {
						$(btnDiv).hover(function() {
							$(this).addClass('fbOver');
						}, function() {
							$(this).removeClass('fbOver');
						});
					}
				} else {
					$(tDiv2).append("<div class='btnseparator'></div>");
				}
			}
			$(g.tDiv).append(tDiv2);
			$(g.tDiv).append("<div style='clear:both'></div>");
			$(g.gDiv).prepend(g.tDiv);
		}
		g.hDiv.className = 'hDiv';

		// Define a combo button set with custom action'ed calls when clicked.
		if (p.combobuttons && $(g.tDiv2)) {
			var btnDiv = document.createElement('div');
			btnDiv.className = 'fbutton';

			var tSelect = document.createElement('select');
			$(tSelect).change(function() {
				g.combo_doSelectAction(tSelect);
			});
			$(tSelect).click(function() {
				g.combo_resetIndex(tSelect);
			});
			tSelect.className = 'cselect';
			$(btnDiv).append(tSelect);

			for (i = 0; i < p.combobuttons.length; i++) {
				var btn = p.combobuttons[i];
				if (!btn.separator) {
					var btnOpt = document.createElement('option');
					btnOpt.innerHTML = btn.name;

					if (btn.bclass)
						$(btnOpt).addClass(btn.bclass).css({
							paddingLeft : 20
						});
					if (btn.bimage) // if bimage defined, use its string as an
						// image url for this buttons style (RS)
						$(btnOpt).css('background', 'url(' + btn.bimage + ') no-repeat center left');
					$(btnOpt).css('paddingLeft', 20);

					if (btn.tooltip) // add title if exists (RS)
						$(btnOpt)[0].title = btn.tooltip;

					if (btn.onpress) {
						btnOpt.value = btn.onpress;
					}
					$(tSelect).append(btnOpt);
				}
			}
			$('.tDiv2').append(btnDiv);
		}

		$(t).before(g.hDiv);
		g.hTable.cellPadding = 0;
		g.hTable.cellSpacing = 0;
		$(g.hDiv).append('<div class="hDivBox"></div>');
		$('div', g.hDiv).append(g.hTable);
		var thead = $("thead:first", t).get(0);
		if (thead)
			$(g.hTable).append(thead);
		thead = null;
		if (!p.colmodel)
			var ci = 0;
		$('thead tr:first th', g.hDiv).each(function() {
			var thdiv = document.createElement('div');
			if ($(this).attr('abbr')) {
				$(this).click(function(e) {
					if (!$(this).hasClass('thOver'))
						return false;
					var obj = (e.target || e.srcElement);
					if (obj.href || obj.type)
						return true;
					g.changeSort(this);
				});
				if ($(this).attr('abbr') == p.sortname) {
					this.className = 'sorted';
					thdiv.className = 's' + p.sortorder;
				}
			}
			if (this.hidden) {
				$(this).hide();
			}
			if (!p.colmodel) {
				$(this).attr('axis', 'col' + ci++);
			}

			// if there isn't a default width, then the column
			// headers don't match
			// i'm sure there is a better way, but this at least
			// stops it failing
			if (this.width == '') {
				this.width = 100;
			}

			$(thdiv).css({
				textAlign : this.align,
				width : this.width + 'px'
			});

			thdiv.innerHTML = this.innerHTML;
			$(this).empty().append(thdiv).removeAttr('width').mousedown(function(e) {
				g.dragStart('colMove', e, this);
			}).hover(function() {
				if (!g.colresize && !$(this).hasClass('thMove') && !g.colCopy) {
					$(this).addClass('thOver');
				}
				if ($(this).attr('abbr') != p.sortname && !g.colCopy && !g.colresize && $(this).attr('abbr')) {
					$('div', this).addClass('s' + p.sortorder);
				} else if ($(this).attr('abbr') == p.sortname && !g.colCopy && !g.colresize && $(this).attr('abbr')) {
					var no = (p.sortorder == 'asc') ? 'desc' : 'asc';
					$('div', this).removeClass('s' + p.sortorder).addClass('s' + no);
				}
				if (g.colCopy) {
					var n = $('th', g.hDiv).index(this);
					if (n == g.dcoln) {
						return false;
					}
					if (n < g.dcoln) {
						$(this).append(g.cdropleft);
					} else {
						$(this).append(g.cdropright);
					}
					g.dcolt = n;
				} else if (!g.colresize) {
					var nv = $('th:visible', g.hDiv).index(this);
					var onl = parseInt($('div:eq(' + nv + ')', g.cDrag).css('left'), 10);
					var nw = jQuery(g.nBtn).outerWidth();
					var nl = onl - nw + Math.floor(p.cgwidth / 2);
					$(g.nDiv).hide();
					$(g.nBtn).hide();
					// modifedy by nbq
					// 2014-01-27
					// 鼠标划过复选、单选时，不显示显隐按钮
					var mtype_cm = $(this).attr("mtype_cm");
					if (mtype_cm == "normal" || mtype_cm == "link") {
						$(g.nBtn).css({
							'left' : nl,
							top : g.hDiv.offsetTop
						}).show();
					}

					var ndw = parseInt($(g.nDiv).width(), 10);
					$(g.nDiv).css({
						top : g.bDiv.offsetTop
					});
					if ((nl + ndw) > $(g.gDiv).width()) {
						$(g.nDiv).css('left', onl - ndw + 1);
					} else {
						$(g.nDiv).css('left', nl);
					}
					if ($(this).hasClass('sorted')) {
						$(g.nBtn).addClass('srtd');
					} else {
						$(g.nBtn).removeClass('srtd');
					}
				}
			}, function() {
				$(this).removeClass('thOver');
				if ($(this).attr('abbr') != p.sortname) {
					$('div', this).removeClass('s' + p.sortorder);
				} else if ($(this).attr('abbr') == p.sortname) {
					var no = (p.sortorder == 'asc') ? 'desc' : 'asc';
					$('div', this).addClass('s' + p.sortorder).removeClass('s' + no);
				}
				if (g.colCopy) {
					$(g.cdropleft).remove();
					$(g.cdropright).remove();
					g.dcolt = null;
				}
			}); // wrap content
		});
		// set bDiv
		g.bDiv.className = 'bDiv';
		$(t).before(g.bDiv);
		$(g.bDiv).css({
			height : (p.height == 'auto') ? 'auto' : p.height + "px"
		}).scroll(function(e) {
			g.scroll();
		}).append(t);
		if (p.height == 'auto') {
			$('table', g.bDiv).addClass('autoht');
		}
		// add td & row properties
		g.addCellProp();
		g.addRowProp();
		// set cDrag only if we are using it
		if (p.colResize === true) {
			var cdcol = $('thead tr:first th:first', g.hDiv).get(0);
			if (cdcol !== null) {
				g.cDrag.className = 'cDrag';
				g.cdpad = 0;
				g.cdpad += (isNaN(parseInt($('div', cdcol).css('borderLeftWidth'), 10)) ? 0 : parseInt($('div', cdcol).css('borderLeftWidth'), 10));
				g.cdpad += (isNaN(parseInt($('div', cdcol).css('borderRightWidth'), 10)) ? 0 : parseInt($('div', cdcol).css('borderRightWidth'), 10));
				g.cdpad += (isNaN(parseInt($('div', cdcol).css('paddingLeft'), 10)) ? 0 : parseInt($('div', cdcol).css('paddingLeft'), 10));
				g.cdpad += (isNaN(parseInt($('div', cdcol).css('paddingRight'), 10)) ? 0 : parseInt($('div', cdcol).css('paddingRight'), 10));
				g.cdpad += (isNaN(parseInt($(cdcol).css('borderLeftWidth'), 10)) ? 0 : parseInt($(cdcol).css('borderLeftWidth'), 10));
				g.cdpad += (isNaN(parseInt($(cdcol).css('borderRightWidth'), 10)) ? 0 : parseInt($(cdcol).css('borderRightWidth'), 10));
				g.cdpad += (isNaN(parseInt($(cdcol).css('paddingLeft'), 10)) ? 0 : parseInt($(cdcol).css('paddingLeft'), 10));
				g.cdpad += (isNaN(parseInt($(cdcol).css('paddingRight'), 10)) ? 0 : parseInt($(cdcol).css('paddingRight'), 10));
				$(g.bDiv).before(g.cDrag);
				var cdheight = $(g.bDiv).height();
				var hdheight = $(g.hDiv).height();
				$(g.cDrag).css({
					top : -hdheight + 'px'
				});
				$('thead tr:first th', g.hDiv).each(function() {
					var cgDiv = document.createElement('div');
					$(g.cDrag).append(cgDiv);
					if (!p.cgwidth) {
						p.cgwidth = $(cgDiv).width();
					}
					$(cgDiv).css({
						height : cdheight + hdheight
					}).mousedown(function(e) {
						g.dragStart('colresize', e, this);
					}).dblclick(function(e) {
						g.autoResizeColumn(this);
					});
					if (browser.msie && browser.version < 7.0) {
						g.fixHeight($(g.gDiv).height());
						$(cgDiv).hover(function() {
							g.fixHeight();
							$(this).addClass('dragging');
						}, function() {
							if (!g.colresize) {
								$(this).removeClass('dragging');
							}
						});
					}
				});
			}
		}
		// add strip
		if (p.striped) {
			$('tbody tr:odd', g.bDiv).addClass('erow');
		}
		if (p.resizable && p.height != 'auto') {
			g.vDiv.className = 'vGrip';
			$(g.vDiv).mousedown(function(e) {
				g.dragStart('vresize', e);
			}).html('<span></span>');
			$(g.bDiv).after(g.vDiv);
		}
		if (p.resizable && p.width != 'auto' && !p.nohresize) {
			g.rDiv.className = 'hGrip';
			$(g.rDiv).mousedown(function(e) {
				g.dragStart('vresize', e, true);
			}).html('<span></span>').css('height', $(g.gDiv).height());
			if (browser.msie && browser.version < 7.0) {
				$(g.rDiv).hover(function() {
					$(this).addClass('hgOver');
				}, function() {
					$(this).removeClass('hgOver');
				});
			}
			$(g.gDiv).append(g.rDiv);
		}
		// add pager
		if (p.usepager) {
			g.pDiv.className = 'pDiv';
			g.pDiv.innerHTML = '<div class="pDiv2"></div>';
			$(g.bDiv).after(g.pDiv);
			var html = ' <div class="pGroup"> <div class="pFirst pButton"><span></span></div><div class="pPrev pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pcontrol">'
					+ p.pagetext
					+ ' <input type="text" size="4" value="1" /> '
					+ p.outof
					+ ' <span> 1 </span></span></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pNext pButton"><span></span></div><div class="pLast pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pReload pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pPageStat"></span></div>';
			$('div', g.pDiv).html(html);
			$('.pReload', g.pDiv).click(function() {
				g.populate();
			});
			$('.pFirst', g.pDiv).click(function() {
				g.changePage('first');
			});
			$('.pPrev', g.pDiv).click(function() {
				g.changePage('prev');
			});
			$('.pNext', g.pDiv).click(function() {
				g.changePage('next');
			});
			$('.pLast', g.pDiv).click(function() {
				g.changePage('last');
			});
			$('.pcontrol input', g.pDiv).keydown(function(e) {
				if (e.keyCode == 13) {
					g.changePage('input');
				}
			});
			if (browser.msie && browser.version < 7)
				$('.pButton', g.pDiv).hover(function() {
					$(this).addClass('pBtnOver');
				}, function() {
					$(this).removeClass('pBtnOver');
				});
			if (p.useRp) {
				var opt = '', sel = '';
				for (var nx = 0; nx < p.rpOptions.length; nx++) {
					if (p.rp == p.rpOptions[nx])
						sel = 'selected="selected"';
					else
						sel = '';
					opt += "<option value='" + p.rpOptions[nx] + "' " + sel + " >" + p.rpOptions[nx] + "&nbsp;&nbsp;</option>";
				}
				$('.pDiv2', g.pDiv).prepend("<div class='pGroup'><select name='rp'>" + opt + "</select></div> <div class='btnseparator'></div>");
				$('select', g.pDiv).change(function() {
					if (p.onRpChange) {
						p.onRpChange(+this.value);
					} else {
						p.newp = 1;
						p.rp = +this.value;
						g.populate();
					}
				});
			}
			// add search button
			if (p.searchitems) {
				$('.pDiv2', g.pDiv).prepend("<div class='pGroup'> <div class='pSearch pButton'><span></span></div> </div>  <div class='btnseparator'></div>");
				$('.pSearch', g.pDiv).click(function() {
					$(g.sDiv).slideToggle('fast', function() {
						$('.sDiv:visible input:first', g.gDiv).trigger('focus');
					});
				});
				// add search box
				g.sDiv.className = 'sDiv';
				var sitems = p.searchitems;
				var sopt = '', sel = '';
				for (var s = 0; s < sitems.length; s++) {
					if (p.qtype === '' && sitems[s].isdefault === true) {
						p.qtype = sitems[s].name;
						sel = 'selected="selected"';
					} else {
						sel = '';
					}
					sopt += "<option value='" + sitems[s].name + "' " + sel + " >" + sitems[s].display + "&nbsp;&nbsp;</option>";
				}
				if (p.qtype === '') {
					p.qtype = sitems[0].name;
				}
				$(g.sDiv).append("<div class='sDiv2'>" + p.findtext + " <input type='text' value='" + p.query + "' size='30' name='q' class='qsbox' /> " + " <select name='qtype'>" + sopt + "</select></div>");
				// Split into separate selectors because of bug in jQuery 1.3.2
				$('input[name=q]', g.sDiv).keydown(function(e) {
					if (e.keyCode == 13) {
						g.doSearch();
					}
				});
				$('select[name=qtype]', g.sDiv).keydown(function(e) {
					if (e.keyCode == 13) {
						g.doSearch();
					}
				});
				$('input[value=Clear]', g.sDiv).click(function() {
					$('input[name=q]', g.sDiv).val('');
					p.query = '';
					g.doSearch();
				});
				$(g.bDiv).after(g.sDiv);
			}
		}
		$(g.pDiv, g.sDiv).append("<div style='clear:both'></div>");
		// add title
		if (p.title) {
			g.mDiv.className = 'mDiv';
			g.mDiv.innerHTML = '<div class="ftitle">' + p.title + '</div>';
			$(g.gDiv).prepend(g.mDiv);
			if (p.showTableToggleBtn) {
				$(g.mDiv).append('<div class="ptogtitle" title="Minimize/Maximize Table"><span></span></div>');
				$('div.ptogtitle', g.mDiv).click(function() {
					$(g.gDiv).toggleClass('hideBody');
					$(this).toggleClass('vsble');
				});
			}
		}
		// setup cdrops
		g.cdropleft = document.createElement('span');
		g.cdropleft.className = 'cdropleft';
		g.cdropright = document.createElement('span');
		g.cdropright.className = 'cdropright';
		// add block
		g.block.className = 'gBlock';
		var gh = $(g.bDiv).height();
		var gtop = g.bDiv.offsetTop;
		$(g.block).css({
			width : g.bDiv.style.width,
			height : gh,
			background : 'white',
			position : 'relative',
			marginBottom : (gh * -1),
			zIndex : 1,
			top : gtop,
			left : '0px'
		});
		$(g.block).fadeTo(0, p.blockOpacity);
		// add column control
		if ($('th', g.hDiv).length) {
			g.nDiv.className = 'nDiv';
			g.nDiv.innerHTML = "<table cellpadding='0' cellspacing='0'><tbody></tbody></table>";
			$(g.nDiv).css({
				marginBottom : (gh * -1),
				display : 'none',
				top : gtop
			}).noSelect();
			var cn = 0;
			$('th div', g.hDiv).each(function() {
				// added by nbq 2014-1-27
				// 去掉列显隐中的复选框、多选框
				var mtype_cm = $(this).parent().attr("mtype_cm");
				if (mtype_cm == "normal") {
					var kcol = $("th[axis='col" + cn + "']", g.hDiv)[0];
					var chk = 'checked="checked"';
					if (kcol.style.display == 'none') {
						chk = '';
					}
					$('tbody', g.nDiv).append('<tr><td class="ndcol1"><input type="checkbox" ' + chk + ' class="togCol" value="' + cn + '" /></td><td class="ndcol2">' + this.innerHTML + '</td></tr>');
				}

				cn++;
			});
			if (browser.msie && browser.version < 7.0)
				$('tr', g.nDiv).hover(function() {
					$(this).addClass('ndcolover');
				}, function() {
					$(this).removeClass('ndcolover');
				});
			$('td.ndcol2', g.nDiv).click(function() {
				if ($('input:checked', g.nDiv).length <= p.minColToggle && $(this).prev().find('input')[0].checked)
					return false;
				return g.toggleCol($(this).prev().find('input').val());
			});
			$('input.togCol', g.nDiv).click(function() {
				if ($('input:checked', g.nDiv).length < p.minColToggle && this.checked === false)
					return false;
				$(this).parent().next().trigger('click');
			});
			$(g.gDiv).prepend(g.nDiv);
			$(g.nBtn).addClass('nBtn').html('<div></div>').attr('title', 'Hide/Show Columns').click(function() {
				$(g.nDiv).toggle();
				return true;
			});
			if (p.showToggleBtn) {
				$(g.gDiv).prepend(g.nBtn);
			}
		}
		// add date edit layer
		$(g.iDiv).addClass('iDiv').css({
			display : 'none'
		});
		$(g.bDiv).append(g.iDiv);
		// add flexigrid events
		$(g.bDiv).hover(function() {
			$(g.nDiv).hide();
			$(g.nBtn).hide();
		}, function() {
			if (g.multisel) {
				g.multisel = false;
			}
		});
		$(g.gDiv).hover(function() {
		}, function() {
			$(g.nDiv).hide();
			$(g.nBtn).hide();
		});
		// add document events
		$(document).mousemove(function(e) {
			g.dragMove(e);
		}).mouseup(function(e) {
			g.dragEnd();
		}).hover(function() {
		}, function() {
			g.dragEnd();
		});
		// browser adjustments
		if (browser.msie && browser.version < 7.0) {
			$('.hDiv,.bDiv,.mDiv,.pDiv,.vGrip,.tDiv, .sDiv', g.gDiv).css({
				width : '100%'
			});
			$(g.gDiv).addClass('ie6');
			if (p.width != 'auto') {
				$(g.gDiv).addClass('ie6fullwidthbug');
			}
		}
		g.rePosDrag();
		g.fixHeight();
		// make grid functions accessible
		t.p = p;
		t.grid = g;
		// load data
		if (p.url && p.autoload) {
			g.populate();
		}
		return t;
	};

	var docloaded = false;
	$(document).ready(function() {
		docloaded = true;
	});
	$.fn.flexigrid = function(p) {
		return this.each(function() {
			if (!docloaded) {
				$(this).hide();
				var t = this;
				$(document).ready(function() {
					$.addFlex(t, p);
				});
			} else {
				$.addFlex(this, p);
			}
		});
	}; // end flexigrid
	$.fn.flexReload = function(setting) { // function to reload grid
		return this.each(function() {
			if (this.grid && this.p.url) {

				if (setting.params) {
					this.p.params = setting.params;
				}

				this.grid.populate();
			}
		});
	}; // end flexReload
	$.fn.flexOptions = function(p) { // function to update general options
		return this.each(function() {
			if (this.grid)
				$.extend(this.p, p);
		});
	}; // end flexOptions
	$.fn.flexToggleCol = function(cid, visible) { // function to reload grid
		return this.each(function() {
			if (this.grid)
				this.grid.toggleCol(cid, visible);
		});
	}; // end flexToggleCol
	$.fn.flexAddData = function(data) { // function to add data to grid
		return this.each(function() {
			if (this.grid)
				this.grid.addData(data);
		});
	};
	$.fn.noSelect = function(p) { // no select plugin by me :-)
		var prevent = (p === null) ? true : p;
		if (prevent) {
			return this.each(function() {
				if (browser.msie || browser.safari)
					$(this).bind('selectstart', function() {
						return false;
					});
				else if (browser.mozilla) {
					$(this).css('MozUserSelect', 'none');
					$('body').trigger('focus');
				} else if (browser.opera)
					$(this).bind('mousedown', function() {
						return false;
					});
				else
					$(this).attr('unselectable', 'on');
			});
		} else {
			return this.each(function() {
				if (browser.msie || browser.safari)
					$(this).unbind('selectstart');
				else if (browser.mozilla)
					$(this).css('MozUserSelect', 'inherit');
				else if (browser.opera)
					$(this).unbind('mousedown');
				else
					$(this).removeAttr('unselectable', 'on');
			});
		}
	}; // end noSelect
	$.fn.flexSearch = function(p) { // function to search grid
		return this.each(function() {
			if (this.grid && this.p.searchitems)
				this.grid.doSearch();
		});
	}; // end flexSearch
	$.fn.selectedRows = function(p) { // Returns the selected rows as an
		// array, taken and adapted from
		// http://stackoverflow.com/questions/11868404/flexigrid-get-selected-row-columns-values
		var arReturn = [];
		var arRow = [];
		var selector = $(this.selector + ' .trSelected');

		$(selector).each(function(i, row) {
			arRow = [];
			var idr = $(row).data('id');
			$.each(row.cells, function(c, cell) {
				var col = cell.abbr;
				var val = cell.firstChild.innerHTML;
				if (val == '&nbsp;')
					val = ''; // Trim the content
				var idx = cell.cellIndex;

				arRow.push({
					Column : col, // Column identifier
					Value : val, // Column value
					CellIndex : idx, // Cell index
					RowIdentifier : idr
				// Identifier of this row element
				});
			});
			arReturn.push(arRow);
		});
		return arReturn;
	};
})(jQuery);

// 封装FLEXIGRID外部接口方法
;
(function($) {
	$.extend({
		/***********************************************************************
		 * 取grid选中的行的内容
		 * 
		 * @param gridId
		 */
		getSelectedAllGrid : function(gridId) {
			var allSelected = mou_grid_ux.getSelectedAllGrid(gridId);
			// console.log("allSelected--\n" + JSON.stringify(allSelected));
			return allSelected;
		},
		/***********************************************************************
		 * 取 grid 的所有数据
		 * 
		 * @param gridId
		 */
		getAllDataGrid : function(gridId) {
			return mou_grid_ux.getAllDataGrid(gridId);
		},
		/***********************************************************************
		 * 增加一行，如果data不为空，则按照date初始化该新增的行
		 * 
		 * @param gridId
		 * @param data
		 */
		addRowGrid : function(gridId, data) {
			mou_grid_ux.addRowGrid(gridId, data);
		},
		/***********************************************************************
		 * 删除grid选中的行
		 * 
		 * @param gridId
		 * @param rownos
		 */
		removeSelectedRowGrid : function(gridId) {

			var selected = mou_grid_ux.getSelectedAllGrid(gridId);
			//console.log("selected--" + selected);
			var row_no_s = [];

			for (var i = 0; i < selected.length; ++i) {
				var rowno = selected[i]["tr_no"];
				row_no_s.push(rowno);
			}

			if (row_no_s.length == 0) {
				return;
			}

			//console.log("toBeDelte--" + row_no_s.join(","));

			mou_grid_ux.removeRowGrid(gridId, row_no_s);
		}
	});
})(jQuery);
