<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String base = request.getContextPath();
	request.setAttribute("base", base);
	/* String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ base + "/"; */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限分配</title>

<link href="${base}/css/style.min.css" rel="stylesheet" media="screen">

<%-- <link rel="stylesheet" type="text/css" href="${base}/js/easyUI/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${base}/js/easyUI/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${base}/js/easyUI/themes/demo.css">

<script type="text/javascript" src="${base}/js/easyUI/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${base}/js/easyUI/jquery.easyui.min.js"></script> --%>

</head>
<body>
	<%-- <div class="easyui-panel" style="padding:5px">
		<ul id="tt" class="easyui-tree" data-options="url:'${base}/user/getOpertion',method:'get',animate:true,checkbox:true"></ul>
	</div>
	<script type="text/javascript">
		function getChecked(){
			var nodes = $('#tt').tree('getChecked');
			var s = '';
			for(var i=0; i<nodes.length; i++){
				if (s != '') s += ',';
				s += nodes[i].text;
			}
			alert(s);
		}
	</script> --%>
	 <!-- 3 setup a container element -->
  <div id="jstree">
    <!-- in this example the tree is populated from inline HTML -->
    <ul>
      <li>Root node 1
        <ul>
          <li id="child_node_1">Child node 1</li>
          <li>Child node 2</li>
        </ul>
      </li>
      <li>Root node 2</li>
    </ul>
  </div>
  <button>demo button</button>
<script type="text/javascript" src="${base}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${base}/js/jstree.min.js"></script>
<script>
  $(function () {
    // 6 create an instance when the DOM is ready
    $('#jstree').jstree();
    // 7 bind to events triggered on the tree
    $('#jstree').on("changed.jstree", function (e, data) {
      console.log(data.selected);
    });
    // 8 interact with the tree - either way is OK
    $('button').on('click', function () {
      $('#jstree').jstree(true).select_node('child_node_1');
      $('#jstree').jstree('select_node', 'child_node_1');
      $.jstree.reference('#jstree').select_node('child_node_1');
    });
  });
  </script>
</body>

</html>