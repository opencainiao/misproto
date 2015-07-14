var obj = {};
		obj["newpage"] =  "http://www.baidu.com";
		obj["type"] = "success";
		obj["title"]="成功测试";
		obj["message"] = "新增内容成功111111111111111111新增内容成功111111111111新增内容成功111111111111新增内容成功111111111111111111";
			
		//$.alertMsg(obj);
		
		//$.alertSuccess("成功测试",obj["message"] );
		$.alertSuccess("成功测试",obj["message"] ,obj["newpage"]);