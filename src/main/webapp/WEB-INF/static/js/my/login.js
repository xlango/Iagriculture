$(document).ready(function(){	
	$('#login').click(function (event) {
		var url='/iagriculture/user/dologin';
		var sendData={'name':$('#name').val(),
				      'pwd':$('#password').val()};
		$.ajax({
			url:url,
			data:sendData,
			type:'POST',
			dataType: "text",
			success:function(d){
				var rdata=JSON.parse(d);
				if(rdata.code==0){
					alert('登录成功');
					toIndex();
				}else{
					alert('登录失败');
				}
			}		
		});		
	});			
});

function checkeUsername(){
	var url1='/iagriculture/user/getuser';
	var sendData1={'name':$('#name').val()};
	$.ajax({
		url:url1,
		data:sendData1,
		type:'GET',
		dataType: "text",
		success:function(d){
			var rdata=JSON.parse(d);				
			if(rdata.code==0){
				alert('账号可用');
			}else{
				alert('账号不可用');
			}
		}		
	});			
  };
  
function toIndex() {

	$(location).attr('href', 'http://localhost:8080/iagriculture/index/main');//跳转至主界面
	}

//发送设备号
function sendDevnum(){
	alert(1);
	var url1='/iagriculture/index/sendMSG';
	var sendData1={'data':$('#input-data').val(),
			       'mac':$('#input-mac').val()};
	$.ajax({
		url:url1,
		data:sendData1,
		type:'GET',
		dataType: "text",
		success:function(d){
			var rdata=JSON.parse(d);				
			if(rdata.code==0){
				alert('发送成功');
			}else{
				alert('发送失败');
			}
		}		
	});			
  };
  
//请求时间
  function getNowtime(){

  	var url1='/iagriculture/index/nowtime';
  	$.ajax({
  		url:url1,
  		//data:sendData1,
  		type:'GET',
  		dataType: "text",
  		success:function(d){
  			var rdata=JSON.parse(d);				 			
  			  alert(rdata.data.name);
                   //$('#staticnowtime').html(rdata.data.name); 	
                   $("#staticnowtime").val("这是赋值");
  		}		
  	});			
    };