<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>${sysname}</title>  
    <style type="text/css">
			body {
			   margin: 0;
			   font-size: 20px;
			}
			
			.formcontainer{
				 margin-top:40px;
			}
			   
			.center_c {
				  border: 1px solid threedshadow;
				  border-radius: 10px 10px 10px 10px;
				  width:800px;
				  height:400px;
			}
			
			.control-label{
				 font-size: 18px;
			}
			 
			#loginTitle {
			    padding-top:20px;
			    padding-left: 10px;
			    padding-right: 10px;
			}
			
			#loginFooter {
			    border-top: 1px solid threedlightshadow;
			    border-bottom: 1px solid threedlightshadow;
			    margin-top:40px;
			    margin-left: 10px;
			    margin-right: 10px;
			    text-align: center;
			    padding-top:15px;
			    padding-bottom:15px;
			}
			
			h1 {
			    border-bottom: 1px solid threedlightshadow;
			    font-size: 40px;
			    margin: 0 0 0.6em;
			}
			
			td input{
				margin:10px  15px;
			}
			
			.xubox_layer{
				margin-top: 120px;
			}
			
    </style>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/admin/main.css"/>
   
   	<jsp:include page="/WEB-INF/jsp/include/common_css.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/include/common_js.jsp"></jsp:include>
	
    <link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/resources/layer/skin/layer.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/admin/login/user_login.js"></script>
  
  </head>
  
  <body>

  	<input type="hidden" name="ctx" value="${ctx}" />
  	
  	<div class="center_c">
		<div id="loginTitle">
			<h1 id="titleText"><span style="width:30px">&nbsp;</span>LOGIN</h1>
		</div>
		<div class="formcontainer">
			<form name="frm1" class="form-horizontal">
			<table style="margin: 0 auto">
				<tbody>
					<tr>
						<td>用户名</td>
						<td><input type="text" class="input-large"  id="ure" name="ure"  style="height:30px"  /></td>	
						<td><span class="error" id="ure_err"></span></td>
					</tr>
					<tr >
						<td>密&nbsp;&nbsp;&nbsp;码</td>
						<td><input type="password" class="input-large"  id="password" name="password"  style="height:30px"  /></td>	
						<td><span class="error" id="ure_err"></span></td>
					</tr>
					<tr >
						<td>验证码</td>
						<td><input type="text" class="input-large"  id="vcode" name="vcode"  style="height:30px"  /></td>	
						<td>
							<div class="validateCodeDiv"   >
						      <table width="100%" border="0" cellspacing="0" cellpadding="0">
							        <tr>
							          <td height="60">
							            <img src="${ctx}/ValCode/getValCode" id="imgVcode"/>
							          </td>
							          <td align="center" valign="middle" height="20" style="color:blue">点图片换一张</td>
							        </tr>
						      </table>
					   		</div>
						</td>
						<td><span class="error" id="ure_err"></span></td>
					</tr>
				</tbody>
			</table>
			</form>
		</div>
		<div id="loginFooter">
			<button type="submit"  id="submitBtn" class="btn-info btn-large" style="font-size:25px">登陆系统</button>
		</div>
	</div>
  </body>
</html>
