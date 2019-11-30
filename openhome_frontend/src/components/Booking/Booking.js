import React, { Component } from 'react';
import {Redirect} from 'react-router';
import { Carousel } from 'react-responsive-carousel';
import "react-responsive-carousel/lib/styles/carousel.min.css";
import './Booking.css';
import PaymentDetails  from'./../Payment/paymentDetails.js';
import {BASE_URL} from './../../components/Configs/Configs.js';
import axios from 'axios';
import {Link} from 'react-router-dom';
class Booking extends Component {
    constructor(props){
        super(props);
        this.state = {
            display : [],
            redirectVar : '',
            selected: {},
        }
        this.submitBooking = this.submitBooking.bind(this);
    }
    submitBooking = (e) => {
        console.log("Inside submitBooking");
        e.preventDefault();
        this.setState({
            redirectVar : true,

        })
        this.props.history.push({
            pathname: '/placeOrder',
            state: { detail: this.state.selected }
        })
    }

    componentWillMount(){
        console.log("inside componentDidMount of booking")
        var id = localStorage.getItem("propid")
        axios.get(BASE_URL + '/api/booking/' + id)
         .then((response) => {
            console.log("response", response)
            if(response.status == 200)
            {
                this.setState({
                    display : this.state.display.concat(response.data)
                });
                this.setState({
                  selected : response.data,
                })
            }
            this.state.display.map(Item => {
                this.setState({
                    headline : Item.headline,
                });
                this.setState({
                    address : Item.address,
                });
                this.setState({
                    price : Item.price,
                });
                this.setState({
                    wifi : Item.wifi,
                });
                this.setState({
                    beds : Item.beds,
                });
                this.setState({
                    sharingtype : Item.sharingtype
                });
                this.setState({
                    proptype : Item.proptype
                });
                this.setState({
                    sqft : Item.sqft
                });
                this.setState({
                    description : Item.description
                });
                this.setState({
                    startdate : this.props.location.state.detail.startdate
                });
                this.setState({
                    enddate : this.props.location.state.detail.enddate
                });
                this.setState({
                    images : Item.images
                });
            })

        });
    }
    render() {
        if(this.state.images != undefined) {
            let imagesArray = this.state.images.split(";");
            var photoArray = imagesArray.map((value) => {
                return(
                    // <p><img  className="photo" src={value}></img></p>
                    <img src={value} className="photo"/>
                )
            })
        }else
            console.log("undefined images")
            this.state.selected.userSelectedStartDate = this.props.location.state.detail.startdate;
            this.state.selected.userSelectedEnddate = this.props.location.state.detail.enddate;
            // this.state.selected.property_unique_id = this.props.location.state.detail.id
            //
        return (
            <div>
                <div className="main_cont">
                <div className="main-div5">
                <div className="carousals">
                <Carousel class="carouselprop carousel-slider" showThumbs={false}>{photoArray}</Carousel>
                </div>
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
                <td className="fonts">{this.state.proptype}</td>
                <td className="fonts">{this.state.sharingtype}</td>
                <td className="fonts">{this.state.beds}</td>
                <td className="fonts">{this.state.sqft}</td>
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
                <input onChange = {this.checkinChangeHandler} type="date" data-date="" data-date-format="DD MMMM YYYY" value={this.state.startdate} name="checkin" min={this.state.startdate} max={this.state.enddate} className="form-control txt1 hiett" id="checkin" placeholder="yyy-mm-dd" required/>
                </div>
                <div class="form-group col-md-6">
                <label for="checkout">Check Out</label>
                <input onChange = {this.checkoutChangeHandler} type="date" data-date="" data-date-format="DD MMMM YYYY" value={this.state.enddate} name="checkout" min={this.state.startdate} max={this.state.enddate}  className="txt1 hiett form-control" id="checkout" placeholder="yyyy-mm-dd" required/>
                </div>
                </div>
                <button className="hiett roundbutton btn btn-primary">
                Book Now</button>
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
