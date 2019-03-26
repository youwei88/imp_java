	
$(function(){
	
})

//关闭窗口
function closeWindow(divId) {
	$("#"+divId).window('close');
}


//关闭窗口
function dialogClose(divId){
	alert(divId);
	
	$("#"+divId).dialog('close');
	$('#dg').datagrid('reload');
}

//提交窗口
function submitButton(divId,roleForm){
	
	var form = $("#roleForm");
    $.ajax({  
        url:form.attr('action'),
        type:form.attr('method'),
        data:form.serialize(),  
        dataType:"json",
        success:function(data){
        	
        	if(data == 1){
        		$.messager.alert('通知','提交成功!');
        	}else{
        		$.messager.alert('通知','提交失败!');
        	}
        },
        error:function(){
        	
        	$.messager.alert('通知','提交失败!');
//        	$.messager.confirm('Confirm','Are you sure you want to delete record?',function(r){
//        	    if (r){
//        			alert('ok');
//        	    }
//        	});
        	
        }
      });
	
	$("#"+divId).dialog('close');
	$('#dg').datagrid('reload');
	
}

//准备窗口调用
function dialogWindow(divId,url,title,icon,f){
	
	//准备
	$("#"+divId).dialog({
        title: title,
        href: url,
        width:'600',
	    height:'400',
        iconCls: icon,
        modal: true,
        closed: true,
        resizable:true
        
        /*toolbar : [ {
            text : '提交',  
            iconCls : 'icon-ok',  
            handler : function() {
            	
            	var form = $("#dialogForm");
                $.ajax({  
                    url:form.attr('action'),  
                    type:form.attr('method'),
                    data:form.serialize(),  
                    dataType:"json",  
                    success:function(data){  
                        $("#dialog").dialog("close");  
                        alert("成功啦");  
                    },  
                    error:function(){  
                        $("#dialog").dialog("close");  
                        alert("出错了哦");  
                    }
                  });
            	
            	
            	
                $("#"+divId).dialog('close');
                f.datagrid('reload');
            }  
        }, '-', {
            text : '关闭',  
            iconCls : 'icon-no',  
            handler : function() {  
            	$("#"+divId).dialog('close');
            }  
        } ]*/
    });
	
	//打开窗口
	$("#"+divId).dialog("open");
	
}

	

