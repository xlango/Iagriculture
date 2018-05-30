var loginModel=window.document.getElementById("loginModel");
var registModel=window.document.getElementById("registModel");
var logout=window.document.getElementById("logout");
var loginBtn=window.document.getElementById("loginBtn");
var registBtn=window.document.getElementById("registBtn");
var logoutBtn=window.document.getElementById("logoutBtn");
var search=window.document.getElementById("search");
search.onkeyup=doSearch;
loginBtn.onclick=doLogin;
registBtn.onclick=doRegist;
logoutBtn.onclick=doLogout;
function getUserInfo() {
    $.ajax({
        type: "GET",
        url: "/api/user/info",
        dataType: "text",
        data: {},
        success: function (r) {
            var data = JSON.parse(r);
            if (data.code == 0) {
                registModel.style.display="none";
                loginModel.style.display="none";
                logout.style.display="block";
            } else {
                /*swal("加载失败", data.msg, "warning");*/
            }
        }, error: function () {
            swal("Ajax错误", "与服务器断开连接", "warning");
        }
    });
}
var csessionid;
var sig;
var scene="nc_login";
var nc_token = ["FFFF0N00000000005BE9", (new Date()).getTime(), Math.random()].join(':');
var NC_Opt =
    {
        renderTo: "#your-dom-id",
        appkey: "FFFF0N00000000005BE9",
        scene: "nc_login",
        token: nc_token,
        customWidth: 300,
        trans:{"key1":"code0"},
        elementID: ["usernameID"],
        is_Opt: 0,
        language: "cn",
        isEnabled: true,
        timeout: 3000,
        times:5,
        apimap: {
            // 'analyze': '//a.com/nocaptcha/analyze.jsonp',
            // 'get_captcha': '//b.com/get_captcha/ver3',
            // 'get_captcha': '//pin3.aliyun.com/get_captcha/ver3'
            // 'get_img': '//c.com/get_img',
            // 'checkcode': '//d.com/captcha/checkcode.jsonp',
            // 'umid_Url': '//e.com/security/umscript/3.2.1/um.js',
            // 'uab_Url': '//aeu.alicdn.com/js/uac/909.js',
            // 'umid_serUrl': 'https://g.com/service/um.json'
        },
        callback: function (data) {
            window.console && console.log(nc_token);

            window.console && console.log(data.csessionid);
            csessionid=data.csessionid;
            window.console && console.log(data.sig);
            sig=data.sig;

        }
    }
var nc = new noCaptcha(NC_Opt);
nc.upLang('cn', {
    _startTEXT: "请按住滑块，拖动到最右边",
    _yesTEXT: "验证通过",
    _error300: "哎呀，出错了，点击<a href=\"javascript:__nc.reset()\">刷新</a>再来一次",
    _errorNetwork: "网络不给力，请<a href=\"javascript:__nc.reset()\">点击刷新</a>",
});

function doSearch() {
    var search=window.document.getElementById("search");
    var fondCount=window.document.getElementById("fondCount");
    var fondText=window.document.getElementById("fondText");
    /* alert(search.value);*/
    $.ajax({
        url:"/api/search/"+search.value, //请求验证页面
        type:"GET", //请求方式 可换为post 注意验证页面接收方式
        data:{
        },//取得表文本框数据，作为提交数据 注意前面的 user 此处格式 key=value 其他方式请参考ajax手册
        dataType:"json",
        success: function(data)
        {   //请求成功时执行操作
            var courseHtml = "";
            var noteHtml="";
            var data_2=data.data;
            fondCount.innerText=data_2.length+"  results:";
            fondText.innerText="“"+search.value+"”";
            console.log(data_2.length);
            if(data.code==0){
                for(var i=0;i<data_2.length;i++){
                    if (data_2[i].type=="note"){
                        noteHtml+="<div class=\"hr-line-dashed\"></div>\n" +
                            "                                <div class=\"search-result\">\n" +
                            "                                    <h3><a onclick='showDetail("+data_2[i].type+")' >"+data_2[i].title+"</h3>\n" +
                            "                                </div>";
                    }else{
                        courseHtml+="<div class=\"hr-line-dashed\"></div>\n" +
                            "                                <div class=\"search-result\">\n" +
                            "                                    <h3><a onclick='showVideo("+data_2[i].id+")'>"+data_2[i].title+"</h3>\n" +
                            "                                </div>";
                    }

                }
                $("#noteResult").html(noteHtml);
                $("#courseResult").html(courseHtml);
            }else {

            }
        },
        error:function(){
            console.log("出现错误");
        }
    });
}
function doLogin() {

    /*alert("请求登陆");*/
    $.ajax({
        type:"GET",
        url:"/api/user/login",
        dataType:"text",
        data:{name:$("#name").val(),
            password:$("#password").val(),
            sessionId:csessionid,
            sig:sig,
            Token:nc_token,
            scene:scene
        },
        success:function (r) {
            var data=JSON.parse(r);
            /* console.log(data.msg);*/
            if (data.code == 0) {

                registModel.style.display="none";
                loginModel.style.display="none";
                logout.style.display="block";
            } else {
                swal({
                        title: "登陆失败？",
                        text: "取消重新登陆或者尝试找回密码！",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "找回密码！",
                        cancelButtonText: "重新登陆！",
                        closeOnConfirm: false,
                        closeOnCancel: false
                    },
                    function(isConfirm){
                        if (isConfirm) {
                            window.location.href="/password_reset";
                        } else {
                            window.location.reload();
                        }
                    });

            }
        },error:function () {
            swal("Ajax错误","与服务器断开连接","warning");
        }
    });
}

function doRegist() {

    $.ajax({
        type:"POST",
        url:"/api/user/",
        dataType:"text",
        data:{
            name:$("#name_2").val(),
            email:$("#email").val(),
            plainPassword:$("#regist_password").val(),
            roleId:4
        },
        success:function (r) {
            var data=JSON.parse(r);
            console.log(data);
            if (data.code == 0) {
                swal("注册成功","请您去邮箱激活账号","success");
            } else {
                swal("错误信息！", data.msg,"error");
            }
        }
    });


}
function doLogout() {

    $.ajax({
        type:"GET",
        url:"/api/user/signout",
        dataType:"text",
        data:{
        },
        success:function (r) {
            var data=JSON.parse(r);
            console.log(data);
            if (data.code == 0) {
                registModel.style="";
                loginModel.style="";
                logout.style.display="none";
            } else {
                swal("错误信息！", data.msg,"error");
            }
        }
    });
}
//start login-register
function showRegisterForm(){
    $('.loginBox').fadeOut('fast',function(){
        $('.registerBox').fadeIn('fast');
        $('.login-footer').fadeOut('fast',function(){
            $('.register-footer').fadeIn('fast');
        });
        $('.modal-title').html('Register with');
    });
    $('.error').removeClass('alert alert-danger').html('');

}
function showLoginForm(){
    $('#loginModal .registerBox').fadeOut('fast',function(){
        $('.loginBox').fadeIn('fast');
        $('.register-footer').fadeOut('fast',function(){
            $('.login-footer').fadeIn('fast');
        });

        $('.modal-title').html('Login with');
    });
    $('.error').removeClass('alert alert-danger').html('');
}
function openLoginModal(){
    showLoginForm();
    setTimeout(function(){
        $('#loginModal').modal('show');
    }, 230);

}
function openRegisterModal(){
    showRegisterForm();
    setTimeout(function(){
        $('#loginModal').modal('show');
    }, 230);

}
function clearActive() {
    for (var i=1;i<8;i++){
        window.document.getElementById("li"+i).removeAttribute("class");
    }
}