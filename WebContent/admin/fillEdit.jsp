<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:if test="#attr.fill!=null && #attr.fill.fill_id!=0">编辑</s:if><s:else>添加</s:else>补签信息</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript">
$(document).ready(function(){
	 var num = /^\d+$/;
	 $("#addBtn").bind('click',function(){
		if($("#paramsFill\\.fill_date").val()==''){
			alert('补签日期不能为空');
			return;
		}
		if($("#paramsFill\\.fill_lesson").val()=='0'){
			alert('上午/下午不能为空');
			return;
		}
		if($("#paramsFill\\.fill_reason").val()==''){
			alert('补签原因不能为空');
			return;
		}
		
		$("#paramsFill\\.fill_id").val(0);
		$("#info").attr('action','Admin_addFill.action').submit();
		 
	 });
	 
	 $("#editBtn").bind('click',function(){
		if($("#paramsFill\\.fill_date").val()==''){
			alert('补签日期不能为空');
			return;
		}
		if($("#paramsFill\\.fill_lesson").val()=='0'){
			alert('上午/下午不能为空');
			return;
		}
		if($("#paramsFill\\.fill_reason").val()==''){
			alert('补签原因不能为空');
			return;
		}
		$("#info").attr('action','Admin_saveFill.action').submit();
			 
	});
	 
});
</script>
<style type="text/css">
</style>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">补签管理&gt;&gt;<s:if test="#attr.fill!=null && #attr.fill.fill_id!=0">编辑</s:if><s:else>添加</s:else>补签信息</span>
</div>
<form id="info" name="info" action="Admin_addFill.action" method="post">   
<s:hidden id="paramsFill.fill_id" name="paramsFill.fill_id" value="%{#attr.fill.fill_id}" /> 
<table width="800" align="center" cellpadding="0" cellspacing="0" style="margin-top:10px;margin-bottom:10px;">
  <tr> 
     <td height="24">
       <Table border="0" cellspacing="0" cellpadding="0" align="center" width="100%"> 
            <TR>
              <TD height="24" class="edittitleleft">&nbsp;</TD>
              <TD class="edittitle"><s:if test="#attr.fill!=null && #attr.fill.fill_id!=0">编辑</s:if><s:else>添加</s:else>补签信息</TD>
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
          <td width="35%" align="right" style="padding-right:5px"><font color="red">*</font> 补签日期：</td>
          <td width="65%">
           <s:textfield name="paramsFill.fill_date" id="paramsFill.fill_date" 
					 onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="%{#attr.fill.fill_dateDesc}"/>
          </td>
        </tr>   
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 上午/下午：</td>
          <td>
             <s:select list="#{'1':'上午','2':'下午' }" id="paramsFill.fill_lesson" name="paramsFill.fill_lesson" value="%{#attr.fill.fill_lesson}"
		      		listKey="key" listValue="value" 
		      		headerKey="0" headerValue="请选择" class="selectstyle" cssStyle="width:155px">
		      </s:select>
          </td> 
        </tr>
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 补签原因：</td>
          <td>
              <s:textfield name="paramsFill.fill_reason" id="paramsFill.fill_reason" value="%{#attr.fill.fill_reason}"/>
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
          	<s:if test="#attr.fill!=null && #attr.fill.fill_id!=0">
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