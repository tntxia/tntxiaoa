<div class="bannerDiv">
	<div class="logo_and_nav">
		<div class="logo">
		</div>
		<div class="company-info">
			<div class="company-name"></div>
			<div class="company-name-en"></div>
		</div>
	</div>
</div>


<div class="login-nav" align="right">
		<a href="/admin/index.mvc" target="_blank">OA管理</a>
		| <a href="http://www.onksz.com" target="_blank">公司主页</a>
		| <a href="http://www.baidu.com" target="_blank">百度搜索</a>
</div>
		
<div class="login-banner">
	OA登陆
</div>
<form name="form1" method="post" action="${basePath }/login.do">
	<div class="loginDiv">
		<div class="item-row">
			<label>用户名：</label><div><input class="loginInput" name="user_id" type="text" id="user_id" size="20"></div>
		</div>
		<div class="item-row">
			<label>密　码：</label><div><input  class="loginInput" type="password" name="password" size="20"></div>
	    </div>
    </div>
</form>
<div class="login-button-div">
   	<button id="loginBtn">登陆</button>
</div>
