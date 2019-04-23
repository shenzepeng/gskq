<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考勤信息统计</title>
<link href="css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="highcharts/js/modules/exporting.js"></script>
<script type="text/javascript" src="js/Common.js"></script>
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
   document.info.action="Admin_listAttendTemps.action";
   document.info.submit();
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
  document.info.action="Admin_listAttendTemps.action";
  document.info.submit();
}
function ChangePage(pagenum)
{
  document.getElementById("pageNo").value=pagenum;
  document.info.action="Admin_listAttendTemps.action";
  document.info.submit();
}
var chart1 = null;
$(document).ready(function(){
	var attend_month = $("#paramsAttend\\.attend_month");
	var real_name = $("#paramsAttend\\.real_name");
	var dept_id = $("#paramsAttend\\.dept_id");
	
	//设置默认为上一个月
	function toPair(str){
		if(Number(str)<10){
			return "0"+str;
		}else{
			return str;
		}
	}
	function toYmd(date){
		var year=date.getFullYear();
	 	var month=toPair(date.getMonth()+1);
	 	//var day=toPair(date.getDate());
	 	return year+"-"+month;
	}
	var monthNow=new Date();
 	var monthLast = new Date(monthNow.getFullYear(), monthNow.getMonth()-1); 
 	var monthLastV = toYmd(monthLast);
 	attend_month.val(monthLastV);
 	
 	function loadChart(){
 		if(chart1!=null){
 			try {
 				chart1.destroy();
			} catch (e) {
				// TODO: handle exception
			}
		}
 		$("#month tr:gt(0)").remove();
 		$("#week tr:gt(2)").remove();
	    $("#op").show();
	    
	    var postParams={
	    		'paramsAttend.attend_month':attend_month.val(),
	    		'paramsAttend.real_name':real_name.val(),
	    		'paramsAttend.dept_id':dept_id.val()
	    };
	    
	    $.post('Admin_listAttendTemps.action',postParams,
  				function(responseObj) {
  						if(responseObj.success) {
  							var x=responseObj.data.x;
  						    var y=responseObj.data.y;
  						    var attendTemps=responseObj.data.attendTemps;
  						    var attendsMap=responseObj.data.attendsMap;
  						    var holidaysMap=responseObj.data.holidaysMap;
  						    var week_before=responseObj.data.week_before;
  						    var week_middle=responseObj.data.week_middle;
  						    var week_after=responseObj.data.week_after;
  						    
  						    //判断是否为单用户
  						    if(attendsMap==null){
  						    	//所有用户情况统计
  						    	$("#month").show();
  						    	$("#week").hide();
  						    	$("#month tr:gt(0)").remove();
  						    	for(var i=0;i<attendTemps.length;i++){
  						    		var className="list1";
  						    		if((i+1)%2==0)className="list0";
  						    		var tr_new = "<tr class='"+className+"' onmouseover='tr_mouseover(this)' onmouseout='tr_mouseout(this)'>";
  						    		tr_new += "<td width='' align='center'>"+attendTemps[i].real_name+"</td>";
  						    		tr_new += "<td width='' align='center'>"+attendTemps[i].dept_name+"</td>";
  						    		tr_new += "<td width='' align='center'>"+attendTemps[i].attend_type1_days+"</td>";
  						    		tr_new += "<td width='' align='center'>"+attendTemps[i].attend_type2_days+"</td>";
  						    		tr_new += "<td width='' align='center'>"+attendTemps[i].attend_type3_days+"</td>";
  						    		tr_new += "<td width='' align='center'>"+attendTemps[i].attend_type6_days+"</td>";
  						    		tr_new += "<td width='' align='center'>"+attendTemps[i].attend_type4_days+"</td>";
  						    		tr_new += "<td width='' align='center'>"+attendTemps[i].attend_type5_days+"</td>";
  						    		tr_new += "</tr>";
  						    		$("#month").append(tr_new);
  						    	}
  						    }else{
  						    	//单个用户情况统计
  						    	$("#week").show();
  						    	$("#month").hide();
  						    	$("#week tr:gt(2)").remove();
  						    	
  						    	//标记设计
     							var markers = {
  						    		"空":"",
     								"未签到":'<span style="color:red;font-weight:bold">✘</span>',
     								"已签到":'<span style="color:green;font-weight:bold">✔</span>',
     								"迟签到":'<span style="color:silver;font-weight:bold">✔</span>',
     								"请假":'<span style="color:silver;font-weight:bold">△</span>',
     								"离岗":'<span style="color:silver;font-weight:bold">◇</span>',
     								"节假日":'<span style="color:green;font-weight:bold">☆</span>',
     								"早退":'<span style="color:silver;font-weight:bold">✔</span>'
     							};
  						    	
  						    	var week_all=[];
  						    	for(var i=0;i<(week_before+week_middle+week_after);i++){
  						    		if(i<week_before || i>(week_before+week_middle-1)){
  						    			week_all[i]="00";
  						    		}else{
  						    			week_all[i]=i-week_before+1;
  						    			week_all[i]=week_all[i]<10?"0"+week_all[i]:week_all[i]+"";
  						    		}
  						    	}
  						    	
  						    	attendsMap["00_1"]="空";
  						    	attendsMap["00_2"]="空";
  						    	var tr_new1 = "<tr class='listtitle2'>";
  						    	var tr_new2 = "<tr>";
  						    	for(var i=0;i<week_all.length;i++){
  						    		//if(!attendsMap[week_all[i]+"_1"])attendsMap[week_all[i]+"_1"]="未签到";
  						    		//if(!attendsMap[week_all[i]+"_2"])attendsMap[week_all[i]+"_2"]="未签到";
  						    		if(!attendsMap[week_all[i]+"_1"])attendsMap[week_all[i]+"_1"]="空";
  						    		if(!attendsMap[week_all[i]+"_2"])attendsMap[week_all[i]+"_2"]="空";
  						    		tr_new1 += "<td width='' align='center' colspan='2'>"+week_all[i].replace("00","")+"&nbsp;</td>";
  						    		if(!holidaysMap[week_all[i]]){
  						    			tr_new2 += "<td width='' align='center'>"+markers[attendsMap[week_all[i]+"_1"]]+"&nbsp;</td>";
  	  						    		tr_new2 += "<td width='' align='center'>"+markers[attendsMap[week_all[i]+"_2"]]+"&nbsp;</td>";
  						    		}else{
  						    			tr_new2 += "<td colspan='2' width='' align='center' title='"+holidaysMap[week_all[i]]+"'>"+markers[attendsMap[week_all[i]]]+"&nbsp;</td>";
  						    		}
  						    		
  						    		if((i+1)%7==0){
  						    			tr_new1 += "</tr>";
  						    			tr_new2 += "</tr>";
  	  						    		$("#week").append(tr_new1);
  	  						    		$("#week").append(tr_new2);
	  	  						    	tr_new1 = "<tr class='listtitle2'>";
	  	  						    	tr_new2 = "<tr>";
  						    		}
  						    		
  						    	}
  						    	
  						    }
  						    
  						    var options1 = getDisasterOptions();
	  						options1.chart.renderTo="Chart";
	  						options1.chart.borderWidth=0;
	  						options1.title.text="考勤情况统计（"+attend_month.val()+"）";
	  						options1.xAxis.max = x.length+4;
	  						options1.xAxis.labels.formatter = function(){  
	  							var arr = x;  
	  							if(this.value>=arr.length || this.value<0)return "";
	  							return arr[this.value]; 
	  						}
	  						options1.yAxis.title.text="累计天数（天）";
	  						options1.series=[{
	  							name:'考勤情况统计',
	  							data:y
	  						}];
	  						options1.plotOptions.column.dataLabels.formatter = function(){  
								return this.y; 
							} 
	  						
	  						var pieData = [];
	  						for(var i=0;i<x.length;i++){
	  							pieData[i] = {
	  									name:x[i],
	  									x:i,
	  									y:y[i]
	  								};
	  						}
	  						options1.series[options1.series.length]={
  								type: 'pie',
  								name: 'Total consumption',
  								zIndex:1000,
  								data:pieData,
  								center: ["75%", 100],
  								size: 100,
  								showInLegend: false,
  								dataLabels: {
  									enabled: true,
  									formatter:function(){  
  										return this.point.name+"<br/>"+to2bits(this.percentage)+"%";  
  									} 
  								}
  							};
	  						
	  						options1.tooltip.formatter = function(){  
								if(this.series.name=="Total consumption"){
									return '<b>'+ this.point.name +'</b><br/>累计: '+ this.y+"天"; 
								}
								var arr = x;  
								return '<b>'+ arr[this.x] +'</b><br/>'+'累计: '+ this.y+"天";
							} 
	  						
							options1.legend.enabled=false;
							options1.plotOptions.column.pointWidth=20;
							
							$("#op").hide();
					  		chart1 = new Highcharts.Chart(options1);
	  						
  						}else if(responseObj.err.msg){
  							 alert('失败：'+responseObj.err.msg);
  							 $("#op").hide();
  						}else{
  							 alert('失败：服务器异常！');
  							 $("#op").hide();
  						}	
  		},'json');
 	}
 	
 	$("#search").bind("click",function(){
 		if(attend_month.val()==""){
 			alert("月份不能为空");
 			return false;
 		}
 		if(attend_month.val()>toYmd(new Date())){
 			alert("不能大于当前月份");
 			return false;
 		}
 		loadChart();
 	});
 	
 	loadChart();
});
</script>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">考勤统计&gt;&gt;考勤信息统计</span>
</div>
<form name="info" id="info" action="Admin_listAttendTemps.action" method="post">
<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
  <tr><td colspan="2" height="10px">&nbsp;</td></tr>
  <tr>
    <td width=""></td>
    <td width="" align="right">
            月份：
      <input type="text" name="paramsAttend.attend_month" id="paramsAttend.attend_month" 
					 onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" style="width:100px"/>&nbsp;
      <s:if test="#attr.admin.user_type==2">
            姓名：
      <input type="text" id="paramsAttend.real_name" name="paramsAttend.real_name" value="${paramsAttend.real_name}" class="inputstyle" style="width:100px"/>&nbsp;
            部门：
      <s:select list="#attr.depts" id="paramsAttend.dept_id" name="paramsAttend.dept_id" value="%{#attr.paramsAttend.dept_id}"
      		listKey="dept_id" listValue="dept_name" headerKey="0" headerValue="请选择" class="selectstyle" cssStyle="width:100px">
      </s:select>&nbsp;
      </s:if>
      <input type="button" value="搜索" id="search" class="btnstyle"/>
    </td>
  </tr>
  <tr><td colspan="2" height="2px"></td></tr>  
</table>

<table id="month" style="display:none" width="95%" align="center" class="table_list" cellpadding="0" cellspacing="0">
   <tr class="listtitle">
     <td width="" align="center">姓名</td>
     <td width="" align="center">部门</td>
     <td width="" align="center">未签到/天</td>
     <td width="" align="center">已签到/天</td>
     <td width="" align="center">迟签到/天</td>
     <td width="" align="center">早退/天</td>
     <td width="" align="center">请假/天</td>
     <td width="" align="center">离岗/天</td>
   </tr>
</table>

<table id="week" style="display:none" width="95%" align="center" class="table_list" cellpadding="0" cellspacing="0">
   <tr>
     <td width="" align="center" colspan="14">
     	<span style="color:red;font-weight:bold">✘</span>：未签到&nbsp;&nbsp;
     	<span style="color:green;font-weight:bold">✔</span>：已签到&nbsp;&nbsp;
     	<span style="color:silver;font-weight:bold">✔</span>：迟签到&nbsp;&nbsp;
     	<span style="color:silver;font-weight:bold">✔</span>：早退&nbsp;&nbsp;
     	<span style="color:silver;font-weight:bold">△</span>：请假&nbsp;&nbsp;
     	<span style="color:silver;font-weight:bold">◇</span>：离岗&nbsp;&nbsp;
     	<span style="color:green;font-weight:bold">☆</span>：节假日
     </td>
   </tr>
   <tr class="listtitle">
     <td width="" align="center" colspan="2">星期一</td>
     <td width="" align="center" colspan="2">星期二</td>
     <td width="" align="center" colspan="2">星期三</td>
     <td width="" align="center" colspan="2">星期四</td>
     <td width="" align="center" colspan="2">星期五</td>
     <td width="" align="center" colspan="2">星期六</td>
     <td width="" align="center" colspan="2">星期日</td>
   </tr>
   <tr>
     <td width="" align="center">上午</td>
     <td width="" align="center">下午</td>
     <td width="" align="center">上午</td>
     <td width="" align="center">下午</td>
     <td width="" align="center">上午</td>
     <td width="" align="center">下午</td>
     <td width="" align="center">上午</td>
     <td width="" align="center">下午</td>
     <td width="" align="center">上午</td>
     <td width="" align="center">下午</td>
     <td width="" align="center">上午</td>
     <td width="" align="center">下午</td>
     <td width="" align="center">上午</td>
     <td width="" align="center">下午</td>
   </tr>
</table>

<table width="95%" style="margin-top:5px" align="center" class="table_list" cellpadding="0" cellspacing="0">
   <tr>
   	<td id="Chart" width="98%" height="300px" align="center">
   		<span id="op" style="display:none;><img src="images/loading004.gif"/></span>
   	</td>
   </tr>
</table>
</form> 
</body>
</html>