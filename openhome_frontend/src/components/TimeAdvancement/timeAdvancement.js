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
import Header from './../header/header.js';
import { Button} from 'react-bootstrap';
import DateTimePicker from 'react-datetime-picker';
import './timeset.css'

class TimeAdvancement extends Component {
  constructor(props){
    super(props);
    this.state = {
      showPickyDateTime: true,
      date: new Date(),
    }
  }
  onChange = date => {
          this.setState({ date });
          console.log("date:" + date);
      }
clickHandler() {
  const data = {
              	"timeZone" : "America/Los_Angeles",
              	"jobType" : "check-in",
                "date" : this.state.date,
                "setDate" : this.state.date,
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
        UTIL.saveTimeDetails(this.state.date);
        alert("Time advanced successfully");
      })
  .catch(error => {
    console.log("Error : " + error);
  });
}

  render() {
    let {
      showPickyDateTime,
      date,
      month,
      year,
      hour,
      minute,
      second,
      meridiem
    } = this.state;

    return (
              <div style={{ margin: '0 auto', width: '80%' }}>
              <Header/>
                    <div style={{ marginTop: '10px'  , width: '100%'}}>
                          <DateTimePicker
                          onChange={this.onChange}
                          value={this.state.date}
                          format={"yyyy/MM/dd HH:mm:ss"}
                      />
                    </div>
                    <Button type="button" className="btn btn-primary timebutton" onClick={() => this.clickHandler()}>Submit</Button>
                    <br/><br/>
                    <Link to='/home'><u>Go to HomePage </u></Link>
                  </div>
           );
  }
}
export default TimeAdvancement;
