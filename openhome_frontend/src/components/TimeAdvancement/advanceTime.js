import React,{ Component } from 'react';
// import './checkin.css';
import {history} from "./../../utils/util";
import * as UTIL from './../../utils/util';
import {Redirect} from 'react-router';
import * as VALIDATION from './../../utils/validation';
import {BASE_URL} from './../../components/Configs/Configs.js';
import {Link} from 'react-router-dom';
import axios from 'axios';
import PickyDateTime from 'react-picky-date-time';
import  { Datetime } from 'react-datetime';
import Header from './../header/header.js';
import { Button} from 'react-bootstrap';

class AdvanceTime extends Component {
  constructor(props){
    super(props);
    this.state = {
      showPickyDateTime: true,
      date: '30',
      month: '01',
      year: '2000',
      hour: '03',
      minute: '10',
      second: 0,
      meridiem: 'PM',
      dateTime: '',
      dateInUTC: '',
      date: "1990-06-05",
      format: "YYYY-MM-DD",
      inputFormat: "DD/MM/YYYY",
      mode: "date"

    }
  }
  onClose() {}

  onYearPicked(res) {
    const { year } = res;
    this.setState({ year: year});
  }

  onMonthPicked(res) {
    const { month, year } = res;
    this.setState({ year: year, month: month});
  }

  onDatePicked(res) {
    const { date, month, year } = res;
    this.setState({ year: year, month: month, date: date });
  }

  onResetDate(res) {
    let { date, month, year } = res;
    this.setState({ year: year, month: month, date: date });
  }

  onResetDefaultDate(res) {
    let { date, month, year } = res;
    this.setState({ year: year, month: month, date: date });
  }

  onSecondChange(res) {
    this.setState({ second: parseInt(res.value) });
  }

  onMinuteChange(res) {
    this.setState({ minute: res.value });
  }

  onHourChange(res) {
    this.setState({ hour: res.value });
  }

  onMeridiemChange(res) {
    this.setState({ meridiem: res });
  }

  onResetTime(res) {
    this.setState({
      second: res.clockHandSecond.value,
      minute: res.clockHandMinute.value,
      hour: res.clockHandHour.value
    });
  }

  onResetDefaultTime(res) {
    this.setState({
      second: res.clockHandSecond.value,
      minute: res.clockHandMinute.value,
      hour: res.clockHandHour.value
    });
  }

  onClearTime(res) {
    this.setState({
      second: res.clockHandSecond.value,
      minute: res.clockHandMinute.value,
      hour: res.clockHandHour.value
    });
  }
clickHandler() {
  this.state.dateInUTC = this.state.year +"-" +this.state.month +"-"+ this.state.date +" " +this.state.hour +":" +this.state.minute +":"+this.state.second;
  const data = {
              	"timeZone" : "America/Los_Angeles",
              	"jobType" : "check-in",
                "setDate" : this.state.dateInUTC,
              }
  fetch(`${BASE_URL}/scheduleCheckInOutJob`, {
         method: 'POST',
         mode: 'cors',
         headers: { ...UTIL.getUserHTTPHeader(),'Content-Type': 'application/json' },
         body: JSON.stringify(data)
       }).then(response => {
          console.log("Status Code : ",response);
          if(response.status==200) {
          return response.json();
        }
      }).then(result => {
        console.log("Time advancement :",result);
        alert("Time advanced successfully");
      })
  .catch(error => {
    console.log("Error : " + error);
  });
}
handleChange = (newDate) => {
    console.log("newDate", newDate);
    return this.setState({date: newDate});
  }

  render() {
    const {date, format, mode, inputFormat} = this.state;
    // let {
    //   showPickyDateTime,
    //   date,
    //   month,
    //   year,
    //   hour,
    //   minute,
    //   second,
    //   meridiem
    // } = this.state;

    return (
              <div style={{ margin: '0 auto', width: '80%' }}>
              <Header/>
                    <div style={{ marginTop: '10px' }}>
                    <Datetime />
                    </div>
                  </div>
           );
  }
}
export default AdvanceTime;
