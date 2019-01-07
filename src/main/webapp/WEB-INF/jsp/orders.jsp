<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <script src="/js/jquery.min.js" type="text/javascript" language="Javascript"></script>
</head>

<body>
<script>
var suggest_count = 0;
var searchq = "";
function select_popup(num) {
     num--;
     $("#result table tr").removeClass("active");
     $("#result table tr").eq(num).addClass("active");
}
var popup_counter=0;
function keydown(el,evt) {
     if (!evt) var evt = window.event;
     var key = evt.keyCode || evt.which;
     if (key==13) // Enter
     {
          if (popup_counter<1) { document.getElementById("searchform").onsubmit=function() { return true }; }
          window.location=$("#result table tr").eq(popup_counter-1).find("td").find("a").attr("href");
     }
     else if (key == 27) // esc
     {
          $(".resultdropdown").html("");
          popup_counter=0;
     }
}
function search(el, evt) {
     if (!evt) var evt = window.event;
     var key = evt.keyCode || evt.which;
     if (key==0 || key==8 || ( key > 45 && key < 112) || (key > 123))
     {
        suggest_count++;
        var offset = $(el).offset();
        var top = offset.top+15;
        var left = offset.left;
        searchq = $(el).val();
        setTimeout("searchGo ("+top+","+left+","+suggest_count+")",300);
      }
      else if (key==40 && popup_counter<$("#result table tr").size()) // Down
      {
         document.getElementById("searchform").onsubmit=function() {return false};
         popup_counter++;
         select_popup(popup_counter);
      }
      else if (key==38 && popup_counter>0) // Up
      {
          popup_counter--;
          select_popup(popup_counter);
      }
}

function searchGo(top, left, count) {
      if (count == suggest_count)
     {
           var window = $("#result");
           window.css('left', left).css('top', top).css('z-index', '10005');

           $.ajax({
                url: '/calculaterate/search?station='+searchq,
                cache: false,
                success: function(html) {
                     window.html(html);
                }
           });
      }
}
</script>

<form id="searchform" action='/calculaterate/search'>
    <input class="text_input"  autocomplete='off' name="station" onkeydown="keydown(this, event)"  onkeyup="javascript:search(this,event)"/>
    <input  type="submit" value="Поиск"/>
    </form>

    <div class="resultdropdown" id="result" style="position:absolute"></div>

<script type="text/javascript" language="Javascript">
//<![CDATA[
    $(document).ready(function(){
        $("*:not(.resultdropdown)").click(function() {
                $(".resultdropdown").html("");
        });
    });
//]]>
</script>
</body>
</html>