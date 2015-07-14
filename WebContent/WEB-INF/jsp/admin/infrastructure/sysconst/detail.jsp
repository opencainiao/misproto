<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<jsp:include page="/WEB-INF/jsp/include/common_css.jsp"></jsp:include>

</head>

<body>
	<div class="container-fluid inlineone" style="margin-top: 30px">
		<div class="col form-horizontal center-block " style="width: 400px">
							<div class="form-group ">
					<label for="val" class="col-sm-3 control-label"> 常量值 </label>
					<div>
						<input type="text" class="form-control" id="val"
							name="val" value="${sysconst.val}" readonly>
					</div>
				</div>
				<div class="form-group ">
					<label for="dspval" class="col-sm-3 control-label"> 常量显示值 </label>
					<div>
						<input type="text" class="form-control" id="dspval"
							name="dspval" value="${sysconst.dspval}" readonly>
					</div>
				</div>
				<div class="form-group ">
					<label for="typename" class="col-sm-3 control-label"> 常量类型 </label>
					<div>
						<input type="text" class="form-control" id="typename"
							name="typename" value="${sysconst.typename}" readonly>
					</div>
				</div>
				<div class="form-group ">
					<label for="valordernum" class="col-sm-3 control-label"> 常量值顺序号 </label>
					<div>
						<input type="text" class="form-control" id="valordernum"
							name="valordernum" value="${sysconst.valordernum}" readonly>
					</div>
				</div>
				<div class="form-group ">
					<label for="type" class="col-sm-3 control-label"> 常量类型码 </label>
					<div>
						<input type="text" class="form-control" id="type"
							name="type" value="${sysconst.type}" readonly>
					</div>
				</div>
		</div>
	</div>
</body>
</html>