<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考勤时间信息</title>
<link href="css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var tempClassName="";
function tr_mouseover(obj) 
{ 
	tempClassName=obj.className;
	obj.className="list_mouseover";
}
function tr_mouseout(obj)      
{ 
	obj.className=tempClassName;
}      
function CheckAll(obj) 
{
	var checks=document.getElementsByName("chkid");
    for (var i=0;i<checks.length;i++)
	{
	    var e = checks[i];
	    e.checked = obj.checked;
	}
    
}


function serch()
{
   document.info.action="Admin_listConfigs.action";
   document.info.submit();
}
function del()
{
	var checks=document.getElementsByName("chkid");
    var ids="";
	for (var i=0;i<checks.length;i++)
    {
        var e = checks[i];
		if(e.checked==true)
		{
		  if(ids=="")
		  {
		    ids=ids+e.value;
		  }
		  else
		  {
		    ids=ids+","+e.value;
		  }
		}
    }
    if(ids=="")
    {
       alert("请至少选择一个要删除的考勤时间！");
       return false;
    }
    if(confirm('确认删除吗!?'))
    {
       document.info.action="Admin_delConfigs.action?paramsConfig.ids="+ids;
       document.info.submit();
    }
    
}
function GoPage()
{
  var pagenum=document.getElementById("goPage").value;
  var patten=/^\d+$/;
  if(!patten.exec(pagenum))
  {
    alert("页码必须为大于0的数字");
    return false;
  }
  document.getElementById("pageNo").value=pagenum;
  document.info.action="Admin_listConfigs.action";
  document.info.submit();
}
function ChangePage(pagenum)
{
  document.getElementById("pageNo").value=pagenum;
  document.info.action="Admin_listConfigs.action";
  document.info.submit();
}
$(document).ready(function(){
	$("#saveConfig").bind("click",function(){
		var aQuery = $("#info").serialize();
		$.post('Admin_saveConfig.action',aQuery,
			function(responseObj) {
					if(responseObj.success) {	
						alert("更新成功");
					}else  if(responseObj.err.msg){
						 alert('失败：'+responseObj.err.msg);
					}else{
						 alert('失败：服务器异常！');
					}	
		 },'json');
	});
});

</script>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">考勤时间管理&gt;&gt;考勤时间配置</span>
</div>
<form name="info" id="info" action="Admin_listConfigs.action" method="post">
<table width="95%" align="center" cellpadding="0" cellspacing="0">
  <tr><td colspan="2" height="10px">&nbsp;</td></tr>
  <tr>
    <td width="">考勤时间配置</td>
    <td width="" align="right">
    </td>
  </tr>
  <tr><td colspan="2" height="2px"></td></tr>  
</table>
<table width="95%" align="center" class="table_list" cellpadding="0" cellspacing="0">
   <tr class="listtitle">
     <td width="" align="center">上班考勤时间</td>
     <td width="" align="center">下班考勤时间</td>
     <td width="" align="center">操作</td>
   </tr>
   <s:if test="#attr.configs!=null && #attr.configs.size()>0">
   <s:iterator value="#attr.configs" id="config" status="status">
   <tr class="<s:if test='(#status.index + 1)%2==0'>list1</s:if><s:else>list0</s:else>" onmouseover="tr_mouseover(this)" onmouseout="tr_mouseout(this)"> 
     <td width="" align="center">
     	<input type="hidden" name="paramsConfig.config_id" id="paramsConfig.config_id" value='<s:property value="#config.config_id"/>'/>
     	<s:textfield name="paramsConfig.config_date1" id="paramsConfig.config_date1" cssStyle="text-align:center"
					 value="%{#config.config_date1}"  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm'})"/>
     </td>
     <td width="" align="center">
     	<s:textfield name="paramsConfig.config_date2" id="paramsConfig.config_date2" cssStyle="text-align:center"
					 value="%{#config.config_date2}"  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm'})"/>
     </td>
     <td width="" align="center">
       <s:a href="javascript:void(0)" id="saveConfig">更新</s:a>
     </td>
   </tr> 
   </s:iterator>
   </s:if>
   <s:else>
   <tr>
     <td height="60" colspan="5" align="center"><b>&lt;不存在考勤时间信息&gt;</b></td>
   </tr>
   </s:else>
   
</table>
</form> 
</body>
</html>