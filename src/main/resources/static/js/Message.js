$(function () {

    $('.text-area-input').click(function () {
        if ($(this).val() == '请输入评论内容......') {
            $(this).css({
                color: '#000000'
            }).val('')
        }
    });

    //离开的时候
    $('.text-area-input').blur(function () {
        var iNum = $(this).val().length;
        var fixedLength = 20;//固定长度
        if (iNum < fixedLength) {
            $('.text-area-input-length span').html(fixedLength - iNum);
        } else {
            $(this).val($(this).val().substr(0, fixedLength - 1));//截取长度
            $('.text-area-input-length span').html(iNum - fixedLength);
        }
    });

    //按下的时候
    $('.text-area-input').keydown(function () {
        var iNum = $(this).val().length;
        var fixedLength = 20;//固定长度
        if (iNum < fixedLength) {
            $('.text-area-input-length span').html(fixedLength - iNum);
        } else {
            $(this).val($(this).val().substr(0, fixedLength - 1));//截取长度
            $('.text-area-input-length span').html(iNum - fixedLength);
        }
    });


    $('.text-area-bottom a').click(function () {
        var star = $('input[name=star]:checked').val();
        var content = $('textarea[name=content]').val();
        alert(content);
    });

    $('.text-area-star label').click(function () {
        var labelLength = $('.text-area-star label').length;
        for (var i = 0; i < labelLength; i++) {
            if (this == $('.text-area-star label').get(i)) {
                $('.text-area-star label').eq(i).addClass('red');
            } else {
                $('.text-area-star label').eq(i).removeClass('red');
            }
        }
    });


});

function order(ele){
    let url;
    url = "/ajax/main/getOrderedMsg";
    let json;
    let method = ele.getAttribute("choose");
    console.log(method)
    $.ajax({
        url:url,
        type:"GET",
        data:{method:method},
        contentType:"application/x-www-form-urlencoded;charset=UTF-8",
        success:function (result){
            $(".msg").html(result)
            alert("选择完毕")
        },
        error: function (jqXHR, textStatus, errorThrown){
            alert('新建或修改错误!');
        }
    })
}
/*
对消息进行点赞
 */
function chAgree(ele){
    let url = "/ajax/agree";
    let id = ele.parentNode.parentNode.parentNode.parentNode.getAttribute("id");
    console.log(id)
    $.ajax({
        url:url,
        type:"GET",
        //其中用户的id自己后端自己从session里面取
        data:{"msgid":id},
        success: function (result){
            let sp = ele.parentNode.lastElementChild;
            sp.textContent = "点赞（"+result+"）"
        },
        error: function (jqXHR, textStatus, errorThrown){
            alert('新建或修改错误!');
        }
    })

}

/*
对消息进行评论，此处只打开下拉框

 */
function chComment(ele){
    //消息的id

    let id = ele.parentNode.parentNode.parentNode.parentNode.getAttribute("id");

    let aim = ele.parentNode.parentNode.parentNode.lastElementChild.lastElementChild;

    let url = "/ajax/getComments"
    let method = "agreeNum"
    $.ajax({
        url:url,
        type:"GET",
        data:{method:method,msgid:id},
        success:function (result){
            let tmp = document.createElement("div");

            aim.innerHTML = result
            console.log(result)

            console.log(aim)

            alert("选择完毕")
        },
        error: function (jqXHR, textStatus, errorThrown){
            alert('新建或修改错误!');
        }
    })

    aim.parentNode.setAttribute("style","");

}

$(document).ready(
    $("#send").on('click',function() {
        console.log("send")

            var content=$("#content").html();


            //判断选择的是否是图片格式
            var imgPath = $(".select_Img").val();
            console.log(imgPath)
            var start  = imgPath.lastIndexOf(".");//最后一次.出现的索引
            var postfix = imgPath.substring(start,imgPath.length).toUpperCase();//文件格式
            if(imgPath!==""){

                if(postfix!==".PNG"&&postfix!==".JPG"&&postfix!==".GIF"&&postfix!==".JPEG"){
                    alert("图片格式需为png,gif,jpeg,jpg格式");
                }else{
                    $(".item_msg").append("<div class='col-sm-12 col-xs-12 message' > <img src='img/icon.png' class='col-sm-2 col-xs-2' style='border-radius: 50%'><div class='col-sm-10 col-xs-10'><span style='font-weight: bold;''>Jack.C</span> <br><small class='date' style='color:#999'>刚刚</small><div class='msg_content'>"+content+"<img class='mypic' onerror='this.src='../static/img/bg_1.jpg' src='file:///"+imgPath+"' ></div></div></div>");
                }
            }else{
                $(".item_msg").append("<div class='col-sm-12 col-xs-12 message' > <img src='img/icon.png' class='col-sm-2 col-xs-2' style='border-radius: 50%'><div class='col-sm-10 col-xs-10'><span style='font-weight: bold;''>Jack.C</span> <br><small class='date' style='color:#999'>刚刚</small><div class='msg_content'>"+content+"</div></div></div>");
            }

            var data = new FormData($("#myForm")[0]);
            url = "/ajax/submitMessage"
            file = $(".select_Img")[0].files[0];
            console.log(file)

            if(!window.FormData) {
                alert('your brower is too old');
                return false;
            }
            var formDatas = new FormData();

            formDatas.append("file",file);
            formDatas.append("content",content);
            console.log(file);

            $.ajax({
                url : url,
                data:formDatas,
                type:"POST",
                processData: false,
                contentType: false,
                async:false,
                success:function (result){
                    if (result == "login")
                    {
                        alert("请先登录");
                        save_method = 'login';
                        $("#login_model").modal("show");
                    }

                    console.log("success");
                    alert(result)
                },
                error: function (jqXHR, textStatus, errorThrown) {

                    alert('新建或修改错误！');
                }
            })



    }),

)

function submitComment(ele)
{
    let aim = ele.parentNode.firstElementChild;
    //发出的评论内容
    let content = aim.textContent;
    //评论的消息的id
    let msgid = ele.parentNode.parentNode.parentNode.parentNode.getAttribute("id");
    $.ajax({
        url : "/ajax/makeComments",
        data: {msgid:msgid,content:content},
        type:"POST",
        success:function (result){
            console.log("success");
            alert(result)
        },
        error: function (jqXHR, textStatus, errorThrown) {

            alert(msgid)
            alert(content)
            alert('新建或修改错误！');
        }
    })
    console.log(content)
    console.log(id)

}