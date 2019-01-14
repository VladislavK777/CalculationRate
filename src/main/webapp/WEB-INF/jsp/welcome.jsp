<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" type="text/javascript"></script>
</head>
<body>
<script>
    var word = "";

    function searchGo(el, evt) {
        word = $(el).val();
        var window = $("#listResult");
        $.ajax({
            url: 'search/station?station=' + word,
            cache: false,
            success: function(response) {
                for (var i in response) {
                    $('#listResult').append($('<option>', {
                        value: response[i],
                        text: response[i]
                    }));
                }
            }
        });
    }

    $(document).ready(function(){
            $("*:not(.resultdropdown)").click(function() {
                    $(".resultdropdown").html("");
            });
        });

    $(function() {
      $('#listResult').on('change', function() {
        result = $('#listResult :selected').text();
        $('#input').val(result);
      });
    });

</script>

    <form id="searchform" action="rate" method="get">
        <input type="text" id="input" list="list" autocomplete='off' name="station_from" onkeyup="javascript:searchGo(this,event)"/>
            <label for="list">
                <datalist id="list">
                    <select class="resultdropdown" id="listResult">
                    </select>
                </datalist>
            </label>
    </form>


</body>
</html>