var E = window.wangEditor;
var editor = new E('#div1', '#div2'); // 两个参数也可以传入 elem 对象，class 选择器

editor.customConfig.uploadImgHeaders = {
    'Accept': 'text/x-json',
    'token': '123456'
};

myUpload();
myEmotions();

editor.create();

submitBlog();


function myUpload() {
    editor.customConfig.uploadImgServer = '/upload';
    //图片上传大小设置10m,spring配置文件中是限制的10m但是wangEditer默认的是限制5M
    editor.customConfig.uploadImgMaxSize = 10 * 1024 * 1024;
    // 限制一次最多上传 5 张图片
    editor.customConfig.uploadImgMaxLength = 5;

    editor.customConfig.uploadImgHooks = {
        // 如果服务器端返回的不是 {errno:0, data: [...]} 这种格式，可使用该配置
        // （但是，服务器端返回的必须是一个 JSON 格式字符串！！！否则会报错）
        customInsert: function (insertImg, result) {
            // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
            // var url = result.url
            //  insertImg(url)
            if (result.errno == 0) {
                insertImg(result.data[0]);
                alert(result.msg)
            } else {
                alert(result.msg)
            }
        }
    };
    //插入网络图片的校验
    editor.customConfig.linkImgCheck = function (src) {
        console.log(src); // 图片的链接

        return true // 返回 true 表示校验成功
        // return '验证失败' // 返回字符串，即校验失败的提示信息
    }
}

function submitBlog() {
    // 读取editor的html内容
    document.getElementById('btn1').addEventListener('click', function () {
        var blogContent = new Object();
        blogContent.content = editor.txt.html();
        blogContent.title = $("#title").val();
        $.ajax({
            url: "/user/publishBlog",
            type: "POST",
            dataType: "json", contentType: "application/json",
            data: JSON.stringify(blogContent),
            success: function (result) {
                alert(result.msg);
                window.location.href = "/blog/list";
            }
        }, false)
    })
}

