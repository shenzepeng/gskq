<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:if test="#attr.leave!=null && #attr.leave.leave_id!=0">编辑</s:if><s:else>添加</s:else>请假信息</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript">
$(document).ready(function(){
	 var num = /^\d+$/;
	 $("#addBtn").bind('click',function(){
		if($("#paramsLeave\\.leave_type").val()=='0'){
			alert('请假类型不能为空');
			return;
		}
		if($("#paramsLeave\\.leave_date1").val()==''){
			alert('请假起始日期不能为空');
			return;
		}
		if($("#paramsLeave\\.leave_lesson1").val()=='0'){
			alert('请假起始上午/下午不能为空');
			return;
		}
		if($("#paramsLeave\\.leave_date2").val()==''){
			alert('请假截止日期不能为空');
			return;
		}
		if($("#paramsLeave\\.leave_lesson2").val()=='0'){
			alert('请假截止上午/下午不能为空');
			return;
		}
		if($("#paramsLeave\\.leave_reason").val()==''){
			alert('请假原因不能为空');
			return;
		}
		
		$("#paramsLeave\\.leave_id").val(0);
		$("#info").attr('action','Admin_addLeave.action').submit();
		 
	 });
	 
	 $("#editBtn").bind('click',function(){
		if($("#paramsLeave\\.leave_type").val()=='0'){
			alert('请假类型不能为空');
			return;
		}
		if($("#paramsLeave\\.leave_date1").val()==''){
			alert('请假起始日期不能为空');
			return;
		}
		if($("#paramsLeave\\.leave_lesson1").val()=='0'){
			alert('请假起始上午/下午不能为空');
			return;
		}
		if($("#paramsLeave\\.leave_date2").val()==''){
			alert('请假截止日期不能为空');
			return;
		}
		if($("#paramsLeave\\.leave_lesson2").val()=='0'){
			alert('请假截止上午/下午不能为空');
			return;
		}
		if($("#paramsLeave\\.leave_reason").val()==''){
			alert('请假原因不能为空');
			return;
		}
		$("#info").attr('action','Admin_saveLeave.action').submit();
			 
	});
	 
});
</script>
<style type="text/css">
</style>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">请假管理&gt;&gt;<s:if test="#attr.leave!=null && #attr.leave.leave_id!=0">编辑</s:if><s:else>添加</s:else>请假信息</span>
</div>
<form id="info" name="info" action="Admin_addLeave.action" method="post">   
<s:hidden id="paramsLeave.leave_id" name="paramsLeave.leave_id" value="%{#attr.leave.leave_id}" /> 
<table width="800" align="center" cellpadding="0" cellspacing="0" style="margin-top:10px;margin-bottom:10px;">
  <tr> 
     <td height="24">
       <Table border="0" cellspacing="0" cellpadding="0" align="center" width="100%"> 
            <TR>
              <TD height="24" class="edittitleleft">&nbsp;</TD>
              <TD class="edittitle"><s:if test="#attr.leave!=null && #attr.leave.leave_id!=0">编辑</s:if><s:else>添加</s:else>请假信息</TD>
              <TD class="edittitleright">&nbsp;</TD>
            </TR>
        </TABLE>
     </td>
   </tr>
   <tr>
     <td height="1" bgcolor="#8f8f8f"></td>
   </tr>
   <tr>
     <td>
     <table width="100%" align="center" cellpadding="1" cellspacing="1" class="editbody">
        <tr>
          <td width="35%" align="right" style="padding-right:5px"><font color="red">*</font> 请假类型：</td>
          <td width="65%">
             <s:select list="#{'1':'年假', '2':'病假', '3':'事假'}" id="paramsLeave.leave_type" name="paramsLeave.leave_type" value="%{#attr.leave.leave_type}"
		      		listKey="key" listValue="value" 
		      		headerKey="0" headerValue="请选择" class="selectstyle" cssStyle="width:155px">
		      </s:select>
          </td> 
        </tr>
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 请假起始：</td>
          <td>
           <s:textfield name="paramsLeave.leave_date1" id="paramsLeave.leave_date1" 
					 onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="%{#attr.leave.leave_date1Desc}"/>
          </td>
        </tr>   
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 上午/下午：</td>
          <td>
             <s:select list="#{'1':'上午','2':'下午' }" id="paramsLeave.leave_lesson1" name="paramsLeave.leave_lesson1" value="%{#attr.leave.leave_lesson1}"
		      		listKey="key" listValue="value" 
		      		headerKey="0" headerValue="请选择" class="selectstyle" cssStyle="width:155px">
		      </s:select>
          </td> 
        </tr>
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 请假截止：</td>
          <td>
           <s:textfield name="paramsLeave.leave_date2" id="paramsLeave.leave_date2" 
					 onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="%{#attr.leave.leave_date2Desc}"/>
          </td>
        </tr>   
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 上午/下午：</td>
          <td>
             <s:select list="#{'1':'上午','2':'下午' }" id="paramsLeave.leave_lesson2" name="paramsLeave.leave_lesson2" value="%{#attr.leave.leave_lesson2}"
		      		listKey="key" listValue="value" 
		      		headerKey="0" headerValue="请选择" class="selectstyle" cssStyle="width:155px">
		      </s:select>
          </td> 
        </tr>
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 请假原因：</td>
          <td>
              <s:textfield name="paramsLeave.leave_reason" id="paramsLeave.leave_reason" value="%{#attr.leave.leave_reason}"/>
          </td>
        </tr>   
     </table>
     </td>
   </tr>  
   <tr>
     <td>
       <table width="100%" align="center" cellpadding="0" cellspacing="0" class="editbody">
        <tr class="editbody">
          <td align="center" height="30">
          	<s:if test="#attr.leave!=null && #attr.leave.leave_id!=0">
          	<input type="button" id="editBtn" Class="btnstyle" value="编 辑"/> 
          	</s:if>
          	<s:else>
          	<input type="button" id="addBtn" Class="btnstyle" value="添 加" />
          	</s:else>
            &nbsp;<label style="color:red">${tip}</label>
          </td>
        </tr>
      </table>
     </td>
   </tr>
</table>
</form>
</body>
</html>