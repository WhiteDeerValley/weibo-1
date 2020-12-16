

$(document).ready(

    $('#loginBtn').on('click',function(){
        //事件处理程序
        save_method = 'login';
        $("#login_model").modal("show");
        $("#passWordAgain").css("display","none");
    }),
    $("#logupBtn").on('click',function(){

        save_method = 'logup';
        $("#passWordAgain").css("display","");
        $("#login_model").modal("show")
    }),
    $("#loginCertain_btn").on('click',function() {

        console.log(save_method)
        if (save_method === 'login') {
            let url = "/ajax/main/login"
            console.log(url)
            $.ajax({
                url: url,
                type: "POST",
                data: $('#form').serialize(),
                success: function (result) {

                    //如果成功，隐藏弹出框并重新加载数据
                    if (result === "success") {
                        alert('成功登陆');
                        //模块框消失
                        $('#login_model').modal('hide');
                        $("#passWord").val("");
                        $("#passWordAgain").val("");
                        $("#userName").val("");

                        reload_head();


                    } else {
                        alert('登陆失败');
                    }

                },
                error: function (jqXHR, textStatus, errorThrown) {

                    alert('新建或修改错误！');
                }
            })
        }

        else if (save_method === 'logup') {

            //如果格式全部都匹配
            if(document.getElementById("userName").validity.patternMismatch === false && document.getElementById("passWord").validity.patternMismatch === false && document.getElementById("passWordAgain").validity.patternMismatch === false){

                //前后两次输入密码相同
                if($("#passWord").val() === $("#passWordAgain").val())
                {
                    url = "/ajax/main/logup";
                    $.ajax({
                        url:url,
                        type: "POST",
                        data: $('#form').serialize(),
                        //ajax成功
                        success: function (result){
                            //注册失败
                            if (result === "error")
                            {
                                alert('已存在同名用户或存在非法字符');
                                $("#passWord").val("");
                                $("#passWordAgain").val("")

                            }
                            else if(result === "success")
                            {
                                console.log("Success");
                                $('#login_model').modal('hide');
                                $("#passWord").val("");
                                $("#passWordAgain").val("");
                                $("#userName").val("");


                            }
                        },
                        error:function (jqXHR, textStatus, errorThrown){
                            alert('新建或修改错误!');
                        }
                    })

                }
                else
                {
                    alert("前后两次输入密码不相同,请重新输入");
                    $("#passWord").val("");
                    $("#passWordAgain").val("")
                }
            }
            else
            {
                alert('输入格式不正确，请检查后重新输入');
                $("#passWord").val("");
                $("#passWordAgain").val("")

            }
        }
    })
)
function reload_head() {
    $.ajax({
        url:"/ajax/load/head",
        type:"GET",
        dataType:"text",
        success:function (data){
            $("#nickName").text(data);
        }
    })
}
function reload_right(){
    $.ajax({
        url:"/ajax/load/right",
        type:"GET",
        contentType:"application/x-www-form-urlencoded;charset=UTF-8",
        success:function (result){
            $("").html(result)
            alert("选择完毕")
        },
        error: function (jqXHR, textStatus, errorThrown){
            alert('新建或修改错误!');
        }
    })
}