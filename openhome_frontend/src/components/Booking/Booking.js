import React, { Component } from 'react';
import {Redirect} from 'react-router';
import './Booking.css';
import {BASE_URL} from './../../components/Configs/Configs.js';
import axios from 'axios';
import {Link} from 'react-router-dom';
class Booking extends Component {
    constructor(props){
        super(props);
        this.state = {
            display : [],
            redirectVar : ''
        }
        this.submitBooking = this.submitBooking.bind(this);
        // this.checkinChangeHandler = this.checkinChangeHandler.bind(this);
    }
    submitBooking = (e) => {
        console.log("Inside submitBooking");
        e.preventDefault();
        this.setState({
            redirectVar : <Redirect to= "/paymentDetails"/>
        })
        this.props.history.push("/paymentDetails");
    }

    componentDidMount(){
        console.log("inside componentWillMount of booking")
        var temp = {headline: "Nice Suit", address: 'san jose', price: 20, sharing_type: "private room", prop_type: "condo", beds:2, sq_ft:600, description:"It is very nice apartment", wifi: "Yes"};
        this.setState({
            display : this.state.display.concat(temp)
        });
        this.setState({
            headline : temp.headline
        });
        this.setState({
            address : temp.address
        });
        this.setState({
            price : temp.price
        });
        this.setState({
            sharing_type : temp.sharing_type
        });
        this.setState({
        prop_type : temp.prop_type
        });
        this.setState({
        beds : temp.beds
        });
        this.setState({
        sq_ft : temp.sq_ft
        });
        this.setState({
        description : temp.description
        });
        this.setState({
        wifi : temp.wifi
        });
        // console.log("display", this.state.display);
        // this.state.display.map(Item => {
        // // this.setState({
        // //     headline : Item.headline
        // // });
        // this.setState({
        //     address : Item.address
        // });
        // this.setState({
        //     price : Item.price
        // });
        // this.setState({
        //     sharing_type : Item.sharing_type
        // });
        // this.setState({
        // prop_type : Item.prop_type
        // });
        // this.setState({
        // beds : Item.beds
        // });
        // this.setState({
        // sq_ft : Item.sq_ft
        // });
        // this.setState({
        // description : Item.description
        // });
        // this.setState({
        // wifi : Item.wifi
        // });
        // })
        //axios.defaults.withCredentials = true;
        // axios.get(eventURL + 'events/' + ID)
        // .then((response) => {
        // console.log("response", response)
        // if(response.status == 200)
        // {
        // this.setState({
        // results : this.state.results.concat(response.data.events)
        // });
        // }
        // this.state.results.map(Item => {
        // this.setState({
        // eventName : Item.eventName,
        // });
        // this.setState({
        // eventId : Item.eventId,
        // });
        // this.setState({
        // orgId : Item.orgId,
        // });
        // this.setState({
        // location : Item.location,
        // });
        // this.setState({
        // date : Item.date,
        // });
        // })
        // });
        }
    render() {
        return (
            <div>
                <div className="main_cont">
                <div className="main-div5">
                <div className="carousals">
                {/* <Carousel class="carouselprop" showThumbs={false}>{photoArray}</Carousel> */}
                </div>
                <br/>
                <div>
                <h2 className="header5">{this.state.headline}</h2>
                <p className="header5 lowHeader">{this.state.address}</p>
                <br/>
                <h3 className="lowHeader bolds header5"> Description : </h3>
                <p className="lowHeader header5 desc"> {this.state.description}</p> <br/><br/>
                <table className="table tb header5">
                <thead>
                <tr>
                <th className="fonts">Property Type</th>
                <th className="fonts">Sharing Type</th>
                <th className="fonts">Bedrooms</th>
                <th className="fonts">Area(Sq.ft)</th>
                <th className="fonts">Free Wifi</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                <td className="fonts">{this.state.prop_type}</td>
                <td className="fonts">{this.state.sharing_type}</td>
                <td className="fonts">{this.state.beds}</td>
                <td className="fonts">{this.state.sq_ft}</td>
                <td className="fonts">{this.state.wifi}</td>
                </tr>
                </tbody>
                </table>
                <br/>
                </div>
                </div>

                <div className="list-owner">
                <h2>${this.state.price}</h2><p>per night</p>
                <form onSubmit={this.submitBooking}>
                <div class="form-row">
                <div class="form-group col-md-6">
                <label for="checkin">CheckIn</label>
                <input onChange = {this.checkinChangeHandler} type="date" name="checkin" class="form-control txt1 hiett" id="checkin" placeholder="mm-dd-yyyy" required/>
                </div>
                <div class="form-group col-md-6">
                <label for="checkout">Check Out</label>
                <input onChange = {this.checkoutChangeHandler} type="date" name="checkout" className="txt1 hiett form-control" id="checkout" placeholder="mm-dd-yyyy" required/>
                </div>
                </div>
                <button className="hiett roundbutton btn btn-primary">Book Now</button>
                </form>
                {/* <h2>Total</h2><p className="lowHeader">${this.state.price}</p> */}
                <br/>
                </div>
                </div>
            </div>
         );
    }
}

export default Booking;
