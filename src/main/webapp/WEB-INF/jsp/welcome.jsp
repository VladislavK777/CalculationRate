<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>UralTransCom|DynamicDistributionPark</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="resources/style.css" rel="stylesheet" type="text/css"/>
    <link rel="shortcut icon" href="resources/favicon.ico" type="image/x-icon">
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js">
    </script>

    <!-- Копирайт -->
    <script>
        function cop() {
            document.getElementById("copy").innerText = new Date().getFullYear();
        }
    </script>


</head>
<style>
        /* Настрйка вкладок*/
        /* Стили секций с содержанием */
        .tabs > section {
            display: none;
            max-width: 100%;
            padding: 15px;
            background: #fff;
            border: 1px solid #ddd;
        }
        .tabs > section > p {
            margin: 0 0 5px;
            line-height: 1.5;
            color: #383838;
            /* прикрутим анимацию */
            -webkit-animation-duration: 1s;
            animation-duration: 1s;
            -webkit-animation-fill-mode: both;
            animation-fill-mode: both;
            -webkit-animation-name: fadeIn;
            animation-name: fadeIn;
        }
        /* Прячем чекбоксы */
        .tabs > input {
            display: none;
            position: absolute;
        }
        /* Стили переключателей вкладок (табов) */
        .tabs > label {
            display: inline-block;
            margin: 0 0 -1px;
            padding: 15px 25px;
            font-weight: 600;
            text-align: center;
            color: #aaa;
            border: 0px solid #ddd;
            border-width: 1px 1px 1px 1px;
            background: #f1f1f1;
            border-radius: 3px 3px 0 0;
        }
        /* Шрифт-иконки от Font Awesome в формате Unicode */
        .tabs > label:before {
            font-family: fontawesome;
            font-weight: normal;
            margin-right: 10px;
        }
        /* Изменения стиля переключателей вкладок при наведении */
        .tabs > label:hover {
            color: #888;
            cursor: pointer;
        }
        /* Стили для активной вкладки */
        .tabs > input:checked + label {
            color: #555;
            border-top: 1px solid #364274;
            border-bottom: 1px solid #fff;
            background: #fff;
        }
        /* Активация секций с помощью псевдокласса :checked */
        #tab1:checked ~ #content-tab1,
        #tab2:checked ~ #content-tab2,
        #tab3:checked ~ #content-tab3,
        #tab4:checked ~ #content-tab4,
        #tab5:checked ~ #content-tab5,
        #tab6:checked ~ #content-tab6 {
            display: block;
        }
        @media screen and (max-width: 1500px) {
            .tabs > label {
                font-size: 12px;
                width: 180px;
            }
            .tabs > label:before {
                margin: 0;
                font-size: 12px;
            }
        }
</style>

<body onload="cop()">

<div>
    <div class="tabs">
            <input id="tab1" type="radio" name="tabs" checked>
            <label for="tab1" title="1">1</label>

            <input id="tab2" type="radio" name="tabs">
            <label for="tab2" title="2">2</label>

            <input id="tab3" type="radio" name="tabs">
            <label for="tab3" title="3">3</label>

            <input id="tab4" type="radio" name="tabs">
            <label for="tab4" title="4">4</label>

            <input id="tab5" type="radio" name="tabs">
            <label for="tab5" title="5">5</label>

            <input id="tab6" type="radio" name="tabs">
            <label for="tab6" title="6">6</label>

            <section id="content-tab1">
                <table>
                    <tbody>
                        <tr>
                            <th>Дорога</th>
                            <th>Станция</th>
                            <th>Объем</th>
                            <th>Станция возврата</th>
                        </tr>
                        <c:forEach items="${mapReturnStations}" var="total">
                            <c:forEach items="${total.value}" var="setting">
                             <tr>
                                <input value='${setting.getId()}' type="hidden" />
                                <td>${total.key}</td>
                                <td>${setting.getIdStationString()}</td>
                                <td>${setting.getVolumeGroupsString()}</td>
                                <td>${setting.getNameStationReturn()}</td>
                             </tr>
                            </c:forEach>
                        </c:forEach>
                    </tbody>
                </table>

             </section>
             <section id="content-tab2">
                <table>
                    <tbody>
                        <tr>
                            <th>Порядок</th>
                            <th>Дорога</th>
                            <th>Станция</th>
                            <th>Объем</th>
                            <th>Станция отправления</th>
                            <th>Станция назначения</th>
                            <th>Груз</th>
                            <th>Класс груза</th>
                            <th>Тип рейса</th>
                            <th>Расстояние</th>
                            <th>Дней</th>
                            <th>Ставка</th>
                            <th>Тариф</th>
                        </tr>
                        <c:forEach items="${mapReturnException}" var="total">
                            <c:forEach items="${total.value}" var="setting">
                             <tr>
                                <input value='${setting.getId()}' type="hidden" />
                                <td>${setting.getNum()}</td>
                                <td>${total.key}</td>
                                <td>${setting.getIdStationString()}</td>
                                <td>${setting.getVolumeGroupsString()}</td>
                                <td>${setting.getStationFrom().getNameStation()}</td>
                                <td>${setting.getStationTo().getNameStation()}</td>
                                <td>${setting.getCargo().getNameCargo()}</td>
                                <td>${setting.getCargoTypeString()}</td>
                                <td>${setting.getRouteType()}</td>
                                <td>${setting.getDistance()}</td>
                                <td>${setting.getCountDays()}</td>
                                <td>${setting.getRate()}</td>
                                <td>${setting.getTariff()}</td>
                             </tr>
                            </c:forEach>
                        </c:forEach>
                    </tbody>
                </table>
            </section>
            <section id="content-tab3">
                <table>
                    <tbody>
                        <tr>
                            <th>Порядок</th>
                            <th>Дорога</th>
                            <th>Станция</th>
                            <th>Объем</th>
                            <th>Станция отправления</th>
                            <th>Станция назначения</th>
                            <th>Груз</th>
                            <th>Класс груза</th>
                            <th>Тип рейса</th>
                            <th>Расстояние</th>
                            <th>Дней</th>
                            <th>Ставка</th>
                            <th>Тариф</th>
                        </tr>
                        <c:forEach items="${mapBeginningException}" var="total">
                            <c:forEach items="${total.value}" var="setting">
                                 <tr>
                                    <input value='${setting.getId()}' type="hidden" />
                                    <td>${setting.getNum()}</td>
                                    <td>${total.key}</td>
                                    <td>${setting.getIdStationString()}</td>
                                    <td>${setting.getVolumeGroupsString()}</td>
                                    <td>${setting.getStationFrom().getNameStation()}</td>
                                    <td>${setting.getStationTo().getNameStation()}</td>
                                    <td>${setting.getCargo().getNameCargo()}</td>
                                    <td>${setting.getCargoTypeString()}</td>
                                    <td>${setting.getRouteType()}</td>
                                    <td>${setting.getDistance()}</td>
                                    <td>${setting.getCountDays()}</td>
                                    <td>${setting.getRate()}</td>
                                    <td>${setting.getTariff()}</td>
                                 </tr>
                            </c:forEach>
                        </c:forEach>
                    </tbody>
                </table>
            </section>
            <section id="content-tab4">
                <table>
                    <tbody>
                        <tr>
                            <th>От</th>
                            <th>До</th>
                            <th>Коэффициент</th>
                        </tr>
                        <c:forEach items="${listBorderDistance}" var="setting">
                         <tr>
                            <input value='${setting.getId()}' type="hidden" />
                            <td>${setting.getDistanceFrom()}</td>
                            <td>${setting.getDistanceTo()}</td>
                            <td>${setting.getCoefficient()}</td>
                         </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </section>
            <section id="content-tab5">
                <table>
                    <tbody>
                        <tr>
                            <th>Имя</th>
                            <th>Значение</th>
                        </tr>
                            <c:forEach items="${listLoadUnload}" var="setting">
                                 <tr>
                                    <input value='${setting.getId()}' type="hidden" />
                                    <td>${setting.getName()}</td>
                                    <td>${setting.getValue()}</td>
                                 </tr>
                            </c:forEach>
                    </tbody>
                </table>
            </section>
            <section id="content-tab6">
                <table>
                    <tbody>
                        <tr>
                            <th>Объем</th>
                            <th>Доходность</th>
                        </tr>
                        <c:forEach items="${listYield}" var="setting">
                             <tr id="context">
                                <input id="id" value='${setting.getId()}' type="hidden" />
                                <td><input id="volumeGroup" value='${setting.getVolumeGroup()}' disabled /></td>
                                <td><input id="yield" value='${setting.getYield()}' disabled /></td>
								<td><button onclick="edit('context')">ОК</button></td>
                             </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </section>

    </div>
</div>
<script>
function edit(id) {
	var context = document.getElementById(id);
	var yield = context.querySelector("#yield");
	yield.disabled = false;
	var value1 = volumeGroup.value;
	var value2 = yield.value;
	var td = document.createElement("td");
	var bt = document.createElement("input");
	bt.value = "Update";
	bt.id = "bt";
	bt.type="submit";
	bt.setAttribute('onclick', 'update(\'context\')');
	td.appendChild(bt);
	context.appendChild(td);
}

function update(id) {
	var context = document.getElementById(id);
	var value1 = context.querySelector("#volumeGroup").value;
	var value2 = context.querySelector("#yield").value;
	var id = context.querySelector("#id").value;

	$.ajax({
			url: 'settings/update',
			datatype : 'json',
			type : "put",
			contentType : "application/json",
			data : JSON.stringify({
				id : id,
				volumeGroup : value1,
				yield : value2
			}),
			success: function(response) {
				context.querySelector("#yield").disabled = true;
				context.querySelector("#bt").type = 'hidden';
			}
		});
}

</script>

<div align="center" class="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
</div>

</body>
</html>