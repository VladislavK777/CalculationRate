"use strict";

class Lable extends React.Component {
  handleChange = e => {
    this.props.onSearch(e.target.id);
  };
  render() {
    return (
      <div id="popup">
        <div id="popup_bg" />
        <div id="main" className="form">
          <p className="inp">
            <label>
              <input
                type="text"
                id={this.props.id}
                placeholder="&nbsp;"
                onChange={this.handleChange.bind(this)}
              />
              <span className="label">{this.props.name}</span>
              <span className="border" />
            </label>
          </p>
        </div>
      </div>
    );
  }
}

ReactDOM.render(
  <div>
    <Lable name="1" id="roadFrom" onSearch={search} />
    <Lable name="2" onSearch={search} />
    <Lable name="3" onSearch={search} />
    <Lable name="4" id="stationTo" onSearch={search} />
  </div>,
  document.getElementById("root")
);
