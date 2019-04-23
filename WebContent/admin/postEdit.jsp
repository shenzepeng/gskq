<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:if test="#attr.post!=null && #attr.post.post_id!=0">编辑</s:if><s:else>添加</s:else>离岗信息</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript">
$(document).ready(function(){
	 var num = /^\d+$/;
	 $("#addBtn").bind('click',function(){
		if($("#paramsPost\\.post_date1").val()==''){
			alert('离岗起始日期不能为空');
			return;
		}
		if($("#paramsPost\\.post_lesson1").val()=='0'){
			alert('离岗起始上午/下午不能为空');
			return;
		}
		if($("#paramsPost\\.post_date2").val()==''){
			alert('离岗截止日期不能为空');
			return;
		}
		if($("#paramsPost\\.post_lesson2").val()=='0'){
			alert('离岗截止上午/下午不能为空');
			return;
		}
		if($("#paramsPost\\.post_reason").val()==''){
			alert('离岗原因不能为空');
			return;
		}
		
		$("#paramsPost\\.post_id").val(0);
		$("#info").attr('action','Admin_addPost.action').submit();
		 
	 });
	 
	 $("#editBtn").bind('click',function(){
		if($("#paramsPost\\.post_date1").val()==''){
			alert('离岗起始日期不能为空');
			return;
		}
		if($("#paramsPost\\.post_lesson1").val()=='0'){
			alert('离岗起始上午/下午不能为空');
			return;
		}
		if($("#paramsPost\\.post_date2").val()==''){
			alert('离岗截止日期不能为空');
			return;
		}
		if($("#paramsPost\\.post_lesson2").val()=='0'){
			alert('离岗截止上午/下午不能为空');
			return;
		}
		if($("#paramsPost\\.post_reason").val()==''){
			alert('离岗原因不能为空');
			return;
		}
		$("#info").attr('action','Admin_savePost.action').submit();
			 
	});
	 
});
</script>
<style type="text/css">
</style>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">离岗管理&gt;&gt;<s:if test="#attr.post!=null && #attr.post.post_id!=0">编辑</s:if><s:else>添加</s:else>离岗信息</span>
</div>
<form id="info" name="info" action="Admin_addPost.action" method="post">   
<s:hidden id="paramsPost.post_id" name="paramsPost.post_id" value="%{#attr.post.post_id}" /> 
<table width="800" align="center" cellpadding="0" cellspacing="0" style="margin-top:10px;margin-bottom:10px;">
  <tr> 
     <td height="24">
       <Table border="0" cellspacing="0" cellpadding="0" align="center" width="100%"> 
            <TR>
              <TD height="24" class="edittitleleft">&nbsp;</TD>
              <TD class="edittitle"><s:if test="#attr.post!=null && #attr.post.post_id!=0">编辑</s:if><s:else>添加</s:else>离岗信息</TD>
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
          <td width="35%" align="right" style="padding-right:5px"><font color="red">*</font> 离岗起始：</td>
          <td width="65%">
           <s:textfield name="paramsPost.post_date1" id="paramsPost.post_date1" 
					 onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="%{#attr.post.post_date1Desc}"/>
          </td>
        </tr>   
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 上午/下午：</td>
          <td>
             <s:select list="#{'1':'上午','2':'下午' }" id="paramsPost.post_lesson1" name="paramsPost.post_lesson1" value="%{#attr.post.post_lesson1}"
		      		listKey="key" listValue="value" 
		      		headerKey="0" headerValue="请选择" class="selectstyle" cssStyle="width:155px">
		      </s:select>
          </td> 
        </tr>
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 离岗截止：</td>
          <td>
           <s:textfield name="paramsPost.post_date2" id="paramsPost.post_date2" 
					 onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="%{#attr.post.post_date2Desc}"/>
          </td>
        </tr>   
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 上午/下午：</td>
          <td>
             <s:select list="#{'1':'上午','2':'下午' }" id="paramsPost.post_lesson2" name="paramsPost.post_lesson2" value="%{#attr.post.post_lesson2}"
		      		listKey="key" listValue="value" 
		      		headerKey="0" headerValue="请选择" class="selectstyle" cssStyle="width:155px">
		      </s:select>
          </td> 
        </tr>
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 离岗原因：</td>
          <td>
              <s:textfield name="paramsPost.post_reason" id="paramsPost.post_reason" value="%{#attr.post.post_reason}"/>
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
          	<s:if test="#attr.post!=null && #attr.post.post_id!=0">
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