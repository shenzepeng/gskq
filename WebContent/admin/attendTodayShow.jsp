<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>本日签到信息</title>
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
   document.info.action="Admin_listAttendTodays.action";
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
       alert("请至少选择一个要删除的本日签到！");
       return false;
    }
    if(confirm('确认删除吗!?'))
    {
       document.info.action="Admin_delAttends.action?paramsAttend.ids="+ids;
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
  document.info.action="Admin_listAttendTodays.action";
  document.info.submit();
}
function ChangePage(pagenum)
{
  document.getElementById("pageNo").value=pagenum;
  document.info.action="Admin_listAttendTodays.action";
  document.info.submit();
}
</script>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">员工签到&gt;&gt;本日签到查询</span>
</div>
<form name="info" id="info" action="Admin_listAttendTodays.action" method="post">
<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
  <tr><td colspan="2" height="10px">&nbsp;</td></tr>
  <tr>
    <td width="">本日签到列表</td>
    <td width="" align="right">
      <s:if test="#attr.admin.user_type==2">
            姓名：
      <input type="text" id="paramsAttend.real_name" name="paramsAttend.real_name" value="${paramsAttend.real_name}" class="inputstyle" style="width:100px"/>&nbsp;
            部门：
      <s:select list="#attr.depts" name="paramsAttend.dept_id" value="%{#attr.paramsAttend.dept_id}"
      		listKey="dept_id" listValue="dept_name" headerKey="0" headerValue="请选择" class="selectstyle" cssStyle="width:100px">
      </s:select>&nbsp;
            签到情况：
      <s:select list="#{'1':'未签到', '2':'已签到', '3':'迟签到', '4':'请假', '5':'离岗', '6':'早退' }" name="paramsAttend.attend_type" value="%{#attr.paramsAttend.attend_type}"
      		listKey="key" listValue="value" headerKey="0" headerValue="请选择" class="selectstyle" cssStyle="width:100px">
      </s:select>&nbsp;&nbsp;
      <input type="button" value="搜索" onclick="serch();" class="btnstyle"/>
      </s:if>
    </td>
  </tr>
  <tr><td colspan="2" height="2px"></td></tr>  
</table>

<table width="95%" align="center" class="table_list" cellpadding="0" cellspacing="0">
   <tr class="listtitle">
     <td width="" align="center">序号</td>
     <td width="" align="center">员工编号</td>
     <td width="" align="center">姓名</td>
     <td width="" align="center">性别</td>
     <td width="" align="center">部门</td>
     <td width="" align="center">上班签到</td>
     <td width="" align="center">下班签到</td>
   </tr>
   <s:if test="#attr.holiday_note==null">
   <s:if test="#attr.attends!=null && #attr.attends.size()>0"> 
   <s:iterator value="#attr.attends" id="attend" status="status">
   <tr class="<s:if test='(#status.index + 1)%2==0'>list1</s:if><s:else>list0</s:else>" onmouseover="tr_mouseover(this)" onmouseout="tr_mouseout(this)"> 
     <td width="" align="center"><s:property value="#status.index+#attr.paramsAttend.start+1"/></td>
     <td width="" align="center"><s:property value="#attend.user_no"/></td>
     <td width="" align="center"><s:property value="#attend.real_name"/></td>
     <td width="" align="center"><s:property value="#attend.user_sexDesc"/></td>
     <td width="" align="center"><s:property value="#attend.dept_name"/></td>
   	 <s:if test="#attr.ap==1 && #attend.attend_type1==1 && #attr.admin.user_type==1">
   	 <td width="" align="center"> 
   	 	<s:a href="Admin_addAttend.action?paramsAttend.attend_lesson=1">点击签到</s:a>
   	 </td>
   	 </s:if>
   	 <s:else>
   	 <td width="" align="center" style="background-color:<s:property value='#attend.attend_type1Color'/>"> 
   	 	<s:property value="#attend.attend_type1Desc"/>
   	 </td>
   	 </s:else>
     <s:if test="#attr.ap==2 && #attend.attend_type2==1 && #attr.admin.user_type==1">
     <td width="" align="center"> 
    	<s:a href="Admin_addAttend.action?paramsAttend.attend_lesson=2">点击签到</s:a>
     </td>
     </s:if>
     <s:elseif test="#attr.ap==2 && #attend.attend_type2!=1">
     <td width="" align="center" style="background-color:<s:property value='#attend.attend_type2Color'/>"> 
    	<s:property value="#attend.attend_type2Desc"/>
     </td>
     </s:elseif>
     <s:else>
     <td width="" align="center">——</td>
     </s:else>
   </tr> 
   </s:iterator>
   </s:if>
   <s:else>
   <tr>
     <td height="60" colspan="9" align="center"><b>&lt;不存在本日签到信息&gt;</b></td>
   </tr>
   </s:else>
   </s:if>
   <s:else>
   <tr>
     <td height="60" colspan="9" align="center"><b>&lt;今日为节假日（<s:property value="#attr.holiday_note"/>）&gt;</b></td>
   </tr>
   </s:else>
</table>

<jsp:include page="page.jsp"></jsp:include>
</form> 
</body>
</html>