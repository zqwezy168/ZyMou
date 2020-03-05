var paras = document.getElementsByTagName("p");
for (var index = 0; index < paras.length; index++) {
    var element = paras[index].getAttribute("title");
    if(element){
        paras[index].setAttribute("title","改过的 zy 的标题");
        // alert(paras[index].getAttribute("title"));
    }
    
}

function showPic(imgObj) {
    if(!document.getElementById("img_default")) return false;
    var source = imgObj.getAttribute("href");
    var placeholder = document.getElementById("img_default");
    placeholder.setAttribute("src",source);

    if(document.getElementById("description")){
        var p_title = document.getElementById("description");
        //<p> 元素本身的nodevalue属性是一个空值，而真正需要的是<p>元素所包含的文本的值
        //包含<p>元素的文本是另一种节点，是<p>元素的第一个节点
        //因此要获取的是 p_title 的第一个节点的 nodevalue值
        if(p_title.firstChild.nodeType == 3){
            p_title.firstChild.nodeValue = imgObj.getAttribute("title")?imgObj.getAttribute("title"):"";
        }
    }
    return true;
}

function popUP(winUrl){
    mywindow = window.open(winUrl,"popup","width=960,height=1080");
    mywindow.focus();
}

//window.onload 在文档被加载完就会调用，而 document 又是window 的一个属性
//如果文档没有加载完，documen getElementsByTagName 方法调用不完整

function prepareLinks(){
    if(!document.getElementsByTagName) return false;//如果不支持 getElementsByTagName 则返回
    var links = document.getElementsByTagName("a");
    for (var i = 0; i < links.length; i++) {
        if(links[i].getAttribute("class") == "popup"){
            links[i].onclick = function(){
                popUP(this.getAttribute("href"));
                return false;
            }
        }
    }
}

function prepareGallery(){
    if(!document.getElementById) return false;
    if(!document.getElementsByTagName) return false;
    if(!document.getElementById("imagegallery")) return false;
    var gallery = document.getElementById("imagegallery");
    var links = gallery.getElementsByTagName("a");
    for (var i = 0; i < links.length; i++) {
        links[i].onclick = function(){
            return showPic(this)?false : true;
        }
        
        // links[i].onkeypress = links[i].onclick;
    }
}

function addLoadEvent(func) {
    var oldonload = window.onload;//将现有的事件处理函数的值存入变量中
    if (typeof window.onload != 'function') {
        window.onload = func;//如果这个事件处理函数没有绑定任何函数，就把新函数添加给它
    } else {
        window.onload = function() {
            oldonload();
            func();//如果已经绑定了函数，就把新函数追加到现有指令的末尾
      }
    }
}

function androidAndJs(){
    var temp = document.getElementById("js_test");
    temp.onclick = function(){
        window.AndroidWebView.showInfoFromJs("从 js 来的消息");
    }
}


var p_title = document.getElementById("description");
function showInfoFromJava(msg){
    p_title.firstChild.nodeValue = msg;
}

var lp = document.getElementById("location_p");
function getLocation(){
    p_title.onclick = function(){
        if(navigator.geolocation){
            //浏览器支持geolocation
               
            navigator.geolocation.getCurrentPosition(showPosition);

         }else{
             //浏览器不支持geolocation
             lp.innerHTML="浏览器不支持geolocation"
         }
    }
}

addLoadEvent(prepareLinks);
addLoadEvent(prepareGallery);
addLoadEvent(androidAndJs);
addLoadEvent(getLocation);

function showPosition(position)
  {
    lp.innerHTML="Latitude: " + position.coords.latitude + 
  "<br />Longitude: " + position.coords.longitude;   
  }