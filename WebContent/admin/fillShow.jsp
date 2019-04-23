<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工补签信息</title>
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
   document.info.action="Admin_listFills.action";
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
       alert("请至少选择一个要删除的员工补签！");
       return false;
    }
    if(confirm('确认删除吗!?'))
    {
       document.info.action="Admin_delFills.action?paramsFill.ids="+ids;
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
  document.info.action="Admin_listFills.action";
  document.info.submit();
}
function ChangePage(pagenum)
{
  document.getElementById("pageNo").value=pagenum;
  document.info.action="Admin_listFills.action";
  document.info.submit();
}
</script>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">员工签到&gt;&gt;员工补签查询</span>
</div>
<form name="info" id="info" action="Admin_listFills.action" method="post">
<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
  <tr><td colspan="2" height="10px">&nbsp;</td></tr>
  <tr>
    <td width="">员工补签列表</td>
    <td width="" align="right">
            日期：
      <input type="text" name="paramsFill.fill_date" id="paramsFill.fill_date" 
					 value="${paramsFill.fill_dateDesc}"  
					 onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:100px"/>&nbsp;
      <s:if test="#attr.admin.user_type==2">
            姓名：
      <input type="text" id="paramsFill.real_name" name="paramsFill.real_name" value="${paramsFill.real_name}" class="inputstyle" style="width:100px"/>&nbsp;
            部门：
      <s:select list="#attr.depts" name="paramsFill.dept_id" value="%{#attr.paramsFill.dept_id}"
      		listKey="dept_id" listValue="dept_name" headerKey="0" headerValue="请选择" class="selectstyle" cssStyle="width:100px">
      </s:select>&nbsp;
      </s:if>
             审核状态：
      <s:select list="#{'1':'待审核', '2':'审核通过', '3':'审核未通过' }" name="paramsFill.fill_flag" value="%{#attr.paramsFill.fill_flag}"
      		listKey="key" listValue="value" headerKey="0" headerValue="请选择" class="selectstyle" cssStyle="width:100px">
      </s:select>&nbsp;&nbsp;
      <input type="button" value="搜索" onclick="serch();" class="btnstyle"/>&nbsp;
      <input type="button" value="删除" onclick="del();" class="btnstyle"/>
    </td>
  </tr>
  <tr><td colspan="2" height="2px"></td></tr>  
</table>
<table width="95%" align="center" class="table_list" cellpadding="0" cellspacing="0">
   <tr class="listtitle">
     <td width="40" align="center"><input type="checkbox" onclick="CheckAll(this);" style="vertical-align:text-bottom;" title="全选/取消全选"/></td>
   	 <td width="" align="center">序号</td>
     <td width="" align="center">补签日期</td>
     <td width="" align="center">上午/下午</td>
     <td width="" align="center">员工编号</td>
     <td width="" align="center">姓名</td>
     <td width="" align="center">部门</td>
     <td width="" align="center">原因</td>
     <td width="" align="center">状态</td>
     <td width="" align="center">操作</td>
   </tr>
   <s:if test="#attr.fills!=null && #attr.fills.size()>0"> 
   <s:iterator value="#attr.fills" id="fill" status="status">
   <tr class="<s:if test='(#status.index + 1)%2==0'>list1</s:if><s:else>list0</s:else>" onmouseover="tr_mouseover(this)" onmouseout="tr_mouseout(this)"> 
     <td width="" align="center"><s:checkbox name="chkid" fieldValue="%{#fill.fill_id}" cssStyle="vertical-align:text-bottom;"/></td>
     <td width="" align="center"><s:property value="#status.index+#attr.paramsFill.start+1"/></td>
     <td width="" align="center"><s:property value="#fill.fill_dateDesc"/></td>
     <td width="" align="center"><s:property value="#fill.fill_lessonDesc"/></td>  
     <td width="" align="center"><s:property value="#fill.user.user_no"/></td>
     <td width="" align="center"><s:property value="#fill.user.real_name"/></td>
     <td width="" align="center"><s:property value="#fill.user.dept.dept_name"/></td>
     <td width="" align="center"><s:property value="#fill.fill_reason"/>&nbsp;</td>  
     <td width="" align="center"><s:property value="#fill.fill_flagDesc"/></td>  
     <td width="" align="center">&nbsp;
        <s:if test="#fill.fill_flag==1 && #attr.admin.user_type==1">
     	<s:a href="Admin_editFill.action?paramsFill.fill_id=%{#fill.fill_id}">编辑</s:a>
     	</s:if>
     	<s:if test="#fill.fill_flag==1 && #attr.admin.user_type==2">
     	<s:a href="Admin_assessFill.action?paramsFill.fill_id=%{#fill.fill_id}&paramsFill.fill_flag=2">审核通过</s:a>
     	<br/>&nbsp;<s:a href="Admin_assessFill.action?paramsFill.fill_id=%{#fill.fill_id}&paramsFill.fill_flag=3">审核驳回</s:a>
     	</s:if>
     </td>  
   </tr> 
   </s:iterator>
   </s:if>
   <s:else>
   <tr>
     <td height="60" colspan="10" align="center"><b>&lt;不存在员工补签信息&gt;</b></td>
   </tr>
   </s:else>
   
</table>
<jsp:include page="page.jsp"></jsp:include>
</form> 
</body>
</html>