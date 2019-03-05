<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
  <title>UralTransCom|CalculateRate</title>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
  <link href="resources/img/favicon.ico" rel="shortcut icon" type="image/x-icon">
  <link href="resources/css/basicStyle.css" rel="stylesheet" type="text/css" />
  <link href="resources/css/inputStyle.css" rel="stylesheet" type="text/css" />

  <script>
    function cop() {
      document.getElementById("copy").innerText = new Date().getFullYear();
    }
  </script>
</head>

<body onload="cop()">
  <div class="one">
    <h1>РАСЧЕТ СТАВОК|АВТОРИЗАЦИЯ</h1>
    <div class="train">
      <img src="resources/img/train.jpg" width="auto">
    </div>
  </div>
  <div>
    <img class="logo" src="resources/img/logo.jpg">
  </div>
  <div class="block"></div>
  <div style="position: absolute; height: 100%; width: 100%; top: 0px; left: 0px;">
    <div style="background: rgba(0, 0, 0, 0); position: fixed; z-index: 1; height: 100%; width: 100%;"></div>
    <div class="form_auth">
      <c:url value="/login" var="loginUrl" />
      <form action="${loginUrl}" method="post">
        <table>
          <tr>
            <td>
              <c:choose>
                <c:when test="${param.error != null}">
                  <h2 style="color: #364274">Неверный логин или пароль</h2>
                </c:when>
                <c:otherwise>
                  <h2 style="color: #364274">Вход в систему</h2>
                </c:otherwise>
              </c:choose>
            </td>
          </tr>
          <tr>
            <td>
              <p class="inp">
                <label>
        <input type="text" autocomplete="off" name="username" value="" placeholder="&nbsp;" required />
        <span class="label">Пользователь</span>
        <span class="border" />
        </label>
              </p>
            </td>
          </tr>
          <tr>
            <td>
              <p class="inp">
                <label>
        <input type="password" autocomplete="off" name="password" value="" placeholder="&nbsp;" required />
        <span class="label">Пароль</span>
        <span class="border" />
         </label> </p>
            </td>
          </tr>
          <tr>
            <td>
              <button type="submit" class="bot1">Войти</button>
            </td>
          </tr>
        </table>
      </form>
    </div>
  </div>
  <div align="center" class="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
  </div>
</body>

</html>