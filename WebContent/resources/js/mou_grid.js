;
(function($) {
	$.extend({
				/***************************************************************
				 * 删除对象所在的行
				 */
				deltr_mou : function() {

					var data = [];

					var $tr = $(this).parents("tr");
					var sno_u = $tr.attr("sno_u");

					var $tbody = $tr.parents("tbody");

					data.push(JSON.stringify($tbody.data("data_jsons")));

					delete $tbody.data("data_jsons")[sno_u];

					$tr.remove();

					data.push(JSON.stringify($tbody.data("data_jsons")));
					//alert(data.join("\n===============\n"));
				},

				/***************************************************************
				 * 向parent添加一行
				 * 
				 */
				addtr_mou_json_delete : function(jsondata, $parent, callback,
						toShow_in_grid, hide_in_grid ) {

					//alert(JSON.stringify(jsondata));
					// 1.设置sno_mou 该对象的序号
					var snomax = $parent.data("sno_max") || 0;
					snomax += 1;
					jsondata["sno_inner"] = snomax;

					// 2.设置序号+1
					$parent.data("sno_max", snomax);

					// 3.添加具体的一行
					var trStr = "<tr sno_u=" + snomax + "><td>"
							+ jsondata["sno_inner"] + "</td>";

					// var data = []

					for (var k = 0; k < toShow_in_grid.length; k++) {

						var key = toShow_in_grid[k];

						if (hide_in_grid.indexOf(key) >= 0) {
							trStr = trStr + "<td style='display:none'>"
									+ jsondata[key] + "</td>";
						} else {
							trStr = trStr + "<td>" + jsondata[key] + "</td>";
						}
						// data.push(k + "-->" + jsondata[k]);
					}

					if (arguments.length == 6){
						trStr += "<td><input type='button' class='del_mou' style='display:none' sno_u='"
							+ snomax
							+ "' value='删除'></input><input type='button' class='update_mou' sno_u='"
							+ snomax + "' value='修改'></input></td></tr>";
					}else{
						trStr += "<td><input type='button' class='del_mou' sno_u='"
							+ snomax
							+ "' value='删除'></input><input type='button' class='update_mou' sno_u='"
							+ snomax + "' value='修改'></input></td></tr>";
					}
					
					var $trStr = $(trStr);
					
					$trStr.find(".del_mou").click($.deltr_mou).end().appendTo(
							$parent);
					$trStr.find(".update_mou").bind('click', function() {
						callback(snomax);
					});

					// 4.将该对象存储进去
					if (!$parent.data("data_jsons")) {
						$parent.data("data_jsons", {});
					}

					$parent.data("data_jsons")[snomax] = jsondata;

					// alert(data.join("\n"));
				},

				/***************************************************************
				 * 向parent添加一行，序号不变
				 * 
				 */
				addtr_mou_json_update : function(jsondata, $parent, callback,
						toShow_in_grid, hide_in_grid) {

					// 1.设置sno_mou 该对象的序号
					var sno_this = jsondata["sno_inner"];

					// .添加具体的一行
					var trStr = "<tr sno_u=" + sno_this + "><td>"
							+ jsondata["sno_inner"] + "</td>";

					// var data = []
					for (var k = 0; k < toShow_in_grid.length; k++) {

						var key = toShow_in_grid[k];

						if (hide_in_grid.indexOf(key) >= 0) {
							trStr = trStr + "<td style='display:none'>"
									+ jsondata[key] + "</td>";
						} else {
							trStr = trStr + "<td>" + jsondata[key] + "</td>";
						}
						// data.push(k + "-->" + jsondata[k]);
					}

					if (arguments.length == 6){
						trStr += "<td><input type='button' class='del_mou' style='display:none' sno_u='"
							+ sno_this
							+ "' value='删除'></input><input type='button' class='update_mou' sno_u='"
							+ sno_this + "' value='修改' /></td></tr>";
					}else{
						trStr += "<td><input type='button' class='del_mou' sno_u='"
							+ sno_this
							+ "' value='删除'></input><input type='button' class='update_mou' sno_u='"
							+ sno_this + "' value='修改' /></td></tr>";
					}
					
					var $trStr = $(trStr);

					// 2.在原来行的后面添加一行
					var toDel = $("tr[sno_u=" + sno_this + "]", $parent);
					$trStr.find(".del_mou").click($.deltr_mou).end()
							.insertAfter(toDel);
					$trStr.find(".update_mou").bind('click', function() {
						callback(sno_this);
					});

					// 3.删除原来的行
					toDel.remove();

					// 4.将该对象存储进去
					if (!$parent.data("data_jsons")) {
						$parent.data("data_jsons", {});
					}

					$parent.data("data_jsons")[sno_this] = jsondata;

					// alert(data.join("\n"));
				},

				/***************************************************************
				 * 用一个JSON对象数组，初始化grid
				 */
				init_grid_with_array_json : function(jsonArray, $parent,toShow_in_grid,hide_in_grid) {

					if (jsonArray && jsonArray.length > 0) {
						for (var k = 0; k < jsonArray.length; ++k) {
							var inputObj = jsonArray[k];
							var $tbody = $parent;
							$.init_grid_tr_with_json_mou(inputObj, $parent,
									pupUpNcpUpdate,toShow_in_grid,hide_in_grid);
						}
					}
				},
				/***************************************************************
				 * 用已有的数据，初始化grid（由于编号可能有跳号，所以封装该单独的方法，计算max序号）
				 * 
				 */
				init_grid_tr_with_json_mou : function(jsondata, $parent,
						callback,toShow_in_grid,hide_in_grid) {

					var sno_jsondata = parseInt(jsondata["sno_inner"]);

					// 1.设置$parent.data("sno_max")
					var snomax = $parent.data("sno_max") || 0;

					if (sno_jsondata > snomax) {
						snomax = sno_jsondata;
					}
					$parent.data("sno_max", snomax);

					// 2.添加具体的一行
					var trStr = "<tr sno_u=" + sno_jsondata + "><td>"
							+ jsondata["sno_inner"] + "</td>";

					for (var k = 0; k < toShow_in_grid.length; k++) {

						var key = toShow_in_grid[k];

						if (hide_in_grid.indexOf(key) >= 0) {
							trStr = trStr + "<td style='display:none'>"
									+ jsondata[key] + "</td>";
						} else {
							trStr = trStr + "<td>" + jsondata[key] + "</td>";
						}
						// data.push(k + "-->" + jsondata[k]);
					}
					

					trStr += "<td>"
							+ "<input type='button' class='del_mou' value='删除'></input>"
							+ "<input type='button' class='update_mou' sno_u='"
							+ sno_jsondata + "' value='修改'></input></td></tr>";

					var $trStr = $(trStr);
					$trStr.find(".del_mou").click($.deltr_mou).end().appendTo(
							$parent);

					$trStr.find(".update_mou").bind('click', function() {
						callback(snomax);
					});

					// 3.将该对象存储进去
					if (!$parent.data("data_jsons")) {
						$parent.data("data_jsons", {});
					}

					$parent.data("data_jsons")[sno_jsondata] = jsondata;
				},
				/***************************************************************
				 * 取弹出页面的各项数据
				 */
				get_data_from_popup_mou : function(index) {
					var innerfields = layer
							.getChildFrame('#innerfields', index).val();
					// alert(JSON.stringify(innerfields));

					var fields = JSON.parse(innerfields);

					var inputObj = {};

					for ( var k in fields) {
						inputObj[fields[k]] = layer.getChildFrame(
								'#' + fields[k], index).val();
					}

					inputObj["sno_inner"] = _toUpdate["sno_inner"];

					return inputObj;
				},
				/***************************************************************
				 * 初始化弹出页面的各项内容
				 */
				init_data_in_popup_mou : function() {
					// var aa = [];
					// aa.push("parent._toUpdate\n" +
					// JSON.stringify(parent._toUpdate));
					var toUpdate1 = parent._toUpdate;

					for ( var k in toUpdate1) {
						// aa.push("#"+k + "-->" + toUpdate1[k]);
						if ($("#" + k)) {
							$("#" + k).val(toUpdate1[k]);
						}
					}

					// alert(aa.join("\n"));
				},

				/************************************************************/
				
				/***************************************************************
				 * 向parent添加一行(只能删除的行，且没有任何附加功能)
				 * 
				 */
				addtr_mou_json_only_delete : function(jsondata, $parent, 
						toShow_in_grid, hide_in_grid, notsaveflg) {

					//alert(JSON.stringify(jsondata));
					// 1.设置sno_mou 该对象的序号
					var snomax = $parent.data("sno_max") || 0;
					snomax += 1;
					jsondata["sno_inner"] = snomax;

					// 2.设置序号+1
					$parent.data("sno_max", snomax);

					// 3.添加具体的一行
					var trStr = "<tr sno_u=" + snomax + "><td>"
							+ jsondata["sno_inner"] + "</td>";
					
					if (hide_in_grid.indexOf("sno_u") >= 0){
						trStr = "<tr sno_u=" + snomax + " ><td style='display:none'>"
							+ jsondata["sno_inner"] + "</td>";
					}

					// var data = []

					for (var k = 0; k < toShow_in_grid.length; k++) {

						var key = toShow_in_grid[k];

						if (hide_in_grid.indexOf(key) >= 0) {
							trStr = trStr + "<td style='display:none'>"
									+ jsondata[key] + "</td>";
						} else {
							trStr = trStr + "<td>" + jsondata[key] + "</td>";
						}
						// data.push(k + "-->" + jsondata[k]);
					}
					
					var idx_nobutton = hide_in_grid.indexOf("buttonall");
					
					if (idx_nobutton >= 0){
						trStr += "</tr>";
					}else{
						
						if (arguments.length  == 4){
							trStr += "<td><span class='del_mou btn btn-sm btn-danger' sno_u='"
								+ snomax
								+ "' >删除</span></td></tr>";
						}else{
							if (notsaveflg){
								trStr += "<td>&nbsp;</td></tr>";
							}
						}
					}

					var $trStr = $(trStr);
					
					if (idx_nobutton < 0){
						$trStr.find(".del_mou").click($.deltr_mou).end().appendTo(
								$parent);
					}else{
						$trStr.appendTo($parent);
					}

					// 4.将该对象存储进去
					if (!$parent.data("data_jsons")) {
						$parent.data("data_jsons", {});
					}
					
					if (!$parent.data("data_jsons_notcount")) {
						$parent.data("data_jsons_notcount", {});
					}

					if (arguments.length  == 4){
						$parent.data("data_jsons")[snomax] = jsondata;
					}else{
						if (!notsaveflg){
							$parent.data("data_jsons")[snomax] = jsondata;
						}else{
							$parent.data("data_jsons_notcount")[snomax] = jsondata;;
						}
					}

					// alert(data.join("\n"));
				},
				
				/***************************************************************
				 * 删除所有行
				 */
				clear_grid_mou : function($tbody) {

					var data = $tbody.data("data_jsons");
					
					if (data){
						//alert(JSON.stringify(data));
						
						$tbody.data("sno_max",0) ;
						
						for(var key in data)
					    {
							delete data[key];
					    }
						
						//alert(JSON.stringify(data));

						$('tr',$tbody).each(function(){
							var $tr = $(this);
							
							if ($tr.attr("sno_u")){
								$tr.remove();
							}
						})
					}
				},
				
				/***************************************************************
				 * 向parent添加一个特殊的行
				 *  colModel : [ "aa":{
			     *	m_type : 'radio',
				 *	}] ,
				 *
				 * btnconfig : { 'del':{hide:true},'modify':{hide:true, callback:func}}
				 */
				addtr_mou_json_only_delete_special : function(jsondata, $parent, 
						toShow_in_grid, hide_in_grid, colModel,btnconfig) {

					//alert(JSON.stringify(jsondata));
					// 1.设置sno_mou 该对象的序号
					var snomax = $parent.data("sno_max") || 0;
					snomax += 1;
					jsondata["sno_inner"] = snomax;

					// 2.设置序号+1
					$parent.data("sno_max", snomax);

					// 3.添加具体的一行
					var trStr = "<tr sno_u=" + snomax + "><td>"
							+ jsondata["sno_inner"] + "</td>";
					
					if (hide_in_grid.indexOf("sno_u") >= 0){
						trStr = "<tr sno_u=" + snomax + " ><td style='display:none'>"
							+ jsondata["sno_inner"] + "</td>";
					}

					// var data = []

					for (var k = 0; k < toShow_in_grid.length; k++) {

						var key = toShow_in_grid[k];
						var value = jsondata[key] || "";
						
						var type = typeof value;
						if (type != "object" && type == "string") {
							value = $.htmlDecode(value);
						}
						
						var valueHtml = value;
						
						var modelKey = colModel[key];
						if (modelKey){
							
							var mtype = modelKey["m_type"];
							
							if (mtype == "select_s"){
								
								var optionname = key;
								
								if (modelKey["name"]){
									if (modelKey["name"]["key"]){
										var namekey = modelKey["name"]["key"];
										optionname = $.htmlDecode(jsondata[namekey]);
									}
								}
								
								var selectH = '<select name="'+ optionname +'" style="width: 100%">';
								
								if (modelKey["attr"]){
									var attr_html = " ";
									
									for (var i=0; i<modelKey["attr"].length; ++i){
										
										var attrname = modelKey["attr"][i];
										//var attrvalue = $.htmlDecode(jsondata[attrname]);
										var attrvalue = jsondata[attrname];
										
										attr_html = attr_html + "attr_" + attrname + '="' + attrvalue + '" ';
									}
									
									selectH = '<select name="'+ optionname +'"' + attr_html + 'style="width: 100%">';
								}
								
								
								// 下拉单选
								if (value && value.length > 0){
									
									var optionH = "";
									if (modelKey["addempty"]){
										optionH = '<option value =""></option>';
									}
									
									for (var j = 0; j<value.length; ++j){
										
										var textitem = value[j][modelKey["text"]];
										var valueitem = value[j][modelKey["value"]];
										
										var showtextitem = $.htmlDecode(textitem);
										
										if (jsondata["checked"] && jsondata["checked"]== valueitem){
											optionH = optionH + '<option value ="'+ valueitem +'" selected="selected">'+ showtextitem +'</option>';
										}else{
											optionH = optionH + '<option value ="'+ valueitem +'">'+ showtextitem +'</option>';
										}
									}
									
									valueHtml = selectH + optionH + "</select>";
								}else{
									if (modelKey["addempty"]){
										var optionH = '<option value =""></option>';
									}
								}
								
								valueHtml = selectH + optionH + "</select>";
							}
						}
						

						if (hide_in_grid.indexOf(key) >= 0) {
							trStr = trStr + "<td style='display:none'>"
									+ valueHtml + "</td>";
						} else {
							trStr = trStr + "<td>" + valueHtml + "</td>";
						}
						// data.push(k + "-->" + jsondata[k]);
					}

					// 设置按钮行
					
					if (btnconfig["hide"]){
						trStr += "<td class='hidden'>";
					}else{
						trStr += "<td>";
					}
					
					if (btnconfig["del"] && btnconfig["del"]["hide"]){
						trStr += "<span class='del_mou btn btn-sm btn-danger' style='display:none' sno_u='"
							+ snomax
							+ "' >删除</span>";
					} else{
						trStr += "<td><span class='del_mou btn btn-sm btn-danger' sno_u='"
							+ snomax
							+ "' >删除</span>";
					}
					
					// 增加修改按钮
					var m_text = "修改";
					if (btnconfig["modify"]["text"]){
						m_text = btnconfig["modify"]["text"];
					}
					if (btnconfig["modify"] && btnconfig["modify"]["hide"]){
						trStr += "<span class='update_mou btn btn-sm btn-primary' style='display:none' sno_u='"
							+ snomax + "'>" + m_text + "</span>";
					}else{
						trStr += "<span class='update_mou btn btn-sm btn-primary' sno_u='"
							+ snomax + "'>" + m_text + "</span>";
					}
					trStr += "</td></tr>";
					
					var $trStr = $(trStr);
					
					$trStr.find(".del_mou").click($.deltr_mou).end().appendTo(
							$parent);
					
					$trStr.find(".update_mou").bind('click', function() {
						if (btnconfig["modify"] && btnconfig["modify"]["callback"]){
							btnconfig["modify"]["callback"](snomax);
						}
					});

					// 4.将该对象存储进去
					if (!$parent.data("data_jsons")) {
						$parent.data("data_jsons", {});
					}

					$parent.data("data_jsons")[snomax] = jsondata;

					// alert(data.join("\n"));
				}
				
				
			});
})(jQuery);
