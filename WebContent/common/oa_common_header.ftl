
<div id="topDiv">
	<div class="logo_and_nav">
		<div class="logo">
		</div>
		<div class="company-info">
			<div class="company-name">${companyName }</div>
			<div class="company-name-en">${companyNameEn }</div>
		</div>
		<div class="right-top-nav">
			<ul>
				<li  @click="showToDo()">
					<div>
						<span class="glyphicon glyphicon-user"></span>
						{{username }}
						<span class="message-num-circle">{{toDoCount}}</span>
					</div>
					<!--  
					<div style="position: absolute;top: 30px;width: 180px;background:white;display:none;">
						部门: ${dept } 职位：${role }
					</div> -->
				</li>
				<li>
					<a>个人设置</a><span class="glyphicon glyphicon-chevron-down"></span>
					<div class="submenu"
						style="position: absolute; top: 14px; border: 1px solid #ccc; background: white;">
						<div>
							<a @click="passchange">更改口令</a>
						</div>
						<div>
							<a href="${basePath}/workAgent.mvc">工作代理</a>
						</div>
						<div>
							<a @click="openHelp">Office Online 帮助</a>
						</div>
						<div>
							<a @click="openAboat">关于Office Online </a>
						</div>
					</div>
				</li>
				<li><a href="${basePath}/knowledge.mvc">知识库</a></li>
				<li><a href="${basePath}/rule.mvc">规章制度</a></li>
				<li><a @click="checkwork">打卡登记</a></li>
				<li><a @click="logout">退出</a></li>
			</ul>
			<div id="topMessage"></div>
		</div>
		<div class="current-online-info">
			当前在线人数：{{loginList.length}}
			在线人员列表：{{loginList.join(";")}}
		</div>
	</div>

	<div class="top_bottom_menu">
		<ul id="historyMenu">
			<li id="home"><span class="fa fa-home" @click="home"></span>
			</li>
			<li id="back"><span class="fa fa-arrow-left" @click="back"></span>
				
			</li>
			<li id="forward"><span class="fa fa-arrow-right" @click="back"></span>
			</li>
			<li>|</li>
		</ul>
		<ul id="menu1"></ul>
	</div>
	<my-todo-dialog ref="myTodoDialog" :title="todoDialogTitle"></my-todo-dialog>
</div>
