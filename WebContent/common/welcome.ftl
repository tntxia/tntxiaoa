<div class="main_sec">
<div id="welcomeDiv">
		<div id="logininfo">
			
        	当前登陆用户：<strong>{{username }}</strong>,
        职位：<strong>{{role }}</strong>,所在部门：<strong>{{dept}}</strong>,
        登陆时间：{{loginTime}}</span></font><font color="#990000" size="2"><br>
                </font><font size="2"> 
                <br>在线用户人数：
                
                	当前在线共<span class='red'>{{loginList.length}}</span>人;
                <span style="padding-left:10px;" v-for="l in loginList">{{l}}</span>
                <br>
          
          </div><br>
		</div>
        
          
    <p><font size="2" color="#213e9b"><strong>公告 >> </strong></font></p>
<table width="98%" id="pubTable"
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3"
                        align="center" bordercolorlight="#7f9db9" 
                        border="1">
  <tr bgcolor="#d3d8eb" > 
    <td width="43" height="24"> <div align="left"><font size="2"> #</font>&nbsp;</div></td>
    <td width="837" height="24"> <div align="left"><font size="2"> 公告主题</font>&nbsp;</div></td>
    <td width="132" height="24"> <div align="left"><font size="2">发布时间</font>&nbsp;</div></td>
    <td width="116" height="24"> <div align="left"><font size="2">发布人</font>&nbsp;</div></td>
  </tr>
  
  <tr style="cursor:hand;" 
   onMouseOver="this.bgColor='#B5DAFF'" onMouseOut="this.bgColor='#ffffff'" v-for="(r,index) in list"> 
    <td width="43" height="24">
    	<a :href="getUrl(r.id)" target="_blank">{{index+1}} </a>
    &nbsp;</td>
    <td width="837" height="24"> <div align="left">
    	<a :href="getUrl(r.id)" target="_blank">{{r.titel }}</a>&nbsp;</div>
    </td>
    <td width="132" height="24"> <div align="left">{{r.fvdate }}&nbsp;</div></td>
    <td width="116" height="24"> <div align="left">{{r.username }}&nbsp;</div></td>
  </tr>
  
</table>
</div>
</div>
