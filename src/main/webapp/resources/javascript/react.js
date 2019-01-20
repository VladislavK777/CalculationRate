'use strict';

ReactDOM.render(
  <table class="table_report">
    <tr>
      <td class="td_report">
        <div id="popup">
          <div id="popup_bg" />
          <div id="main" class="form">
            <p for="inp" class="inp">
              <label>
                <input type="text" id="road" placeholder="&nbsp;" onKeyUp={searchfield('road')}/>
                <span class="label">Дорога назначения:</span>
                <span class="border" />
              </label>
            </p>
            <br />
            <p for="inp" class="inp">
              <label>
                <input type="text" id="inp" placeholder="&nbsp;" />
                <span class="label">Список станций:</span>
                <span class="border" />
              </label>
            </p>
            <br />
            <p for="inp" class="inp">
              <label>
                <input type="text" id="inp" placeholder="&nbsp;" />
                <span class="label">Группа объемов:</span>
                <span class="border" />
              </label>
            </p>
            <br />
            <p for="inp" class="inp">
              <label>
                <input type="text" id="station" placeholder="&nbsp;" onKeyUp={searchfield('station')}/>
                <span class="label">Станция возврата:</span>
                <span class="border" />
              </label>
            </p>
            <p>
              <input type="button" value="Сохранить"  class="bot1" />
            </p>
          </div>
        </div>
      </td>
    </tr>
  </table>,
  document.getElementById("root")
);