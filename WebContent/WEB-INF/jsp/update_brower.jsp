<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>UMC-请升级浏览器</title>
<link rel="shortcut icon" href="/favicon.ico" />

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/updatebrowser.css"
	type="text/css">
</head>
<body>
	<div class="page" id="page-compatable">

		<div class="content">
			<h2>您的浏览器太古老，该升级了~</h2>
			<p class="upgrade_desc">我们检测到您的浏览器正在使用10.0版本以下的 IE
				内核，它太古老了，既不安全，也不能完美的支持UMC上的各项功能，需要升级才能试用UMC。</p>
			<p class="upgrade_desc">
				如果您使用的是360，搜狗，猎豹等双核浏览器，也可以开启高速浏览模式，不更换浏览器直接访问UMC。</p>
			<p class="upgrade_desc">或者，升级到下面的浏览器，一劳永逸！</p>
			<ul class="browser-list">
				<li style="width: 180px; height:180px"><a class="chrome" target="_blank"
					href="http://oss.aliyuncs.com/towerfiles/ChromeStandaloneSetup.exe">
					<img alt="" src="http://p4.qhimg.com/t01a731a87b2a6336cf.png" style="width:100%">
					谷歌浏览器</a>
				</li>
				<li style="width: 180px; height:180px">
					<a class="chrome" target="_blank"
					href="http://www.firefox.com.cn/" >
					<img alt="" src="http://www.firefox.com.cn/media/img/sandstone/buttons/firefox-large.png?2013-06" style="width:93%;padding-top: 15px">
					火狐浏览器</a>
				</li>
			</ul>
			<p class="supported">
				我们也支持 <a target="_blank"
					href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie">最新的IE</a>
				等其他浏览器（排序要分先后）
			</p>
		</div>
	</div>
</html>