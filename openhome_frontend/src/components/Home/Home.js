import React, { Component } from 'react';
import {BASE_URL} from './../../components/Configs/Configs.js';
import axios from 'axios';
import {Redirect} from 'react-router';
import {Link} from 'react-router-dom';
import './Home.css';
class Home extends Component {
    constructor(props){
    super(props);
    this.state = {
        address : "",
        startdate : "",
        enddate : "",
        listed: []
    }
    this.addressChangeHandler = this.addressChangeHandler.bind(this);
    this.startdateChangeHandler = this.startdateChangeHandler.bind(this);
    this.enddateChangeHandler = this.enddateChangeHandler.bind(this);
    this.submitSearch = this.submitSearch.bind(this);
    this.handleSlider1 = this.handleSlider1.bind(this);
    this.handleSlider2 = this.handleSlider2.bind(this);
    }
    addressChangeHandler = (e) => {
        this.setState({
            address : e.target.value
        })
    }
    startdateChangeHandler = (e) => {
        this.setState({
            startdate : e.target.value
        })
    }
    enddateChangeHandler = (e) => {
        this.setState({
            enddate : e.target.value
        })
    }
    handleSlider1 = (e) => {
        this.setState({
            price1 : e.target.value
        })
    }
    handleSlider2 = (e) => {
        this.setState({
            price2 : e.target.value
        })
    }
    submitSearch = (e) => {
        var headers = new Headers();
        e.preventDefault();
        const data = {
            address : this.state.address,
            startdate : this.state.startdate,
            enddate : this.state.enddate,
        }
        // axios.defaults.withCredentials = true;
        axios.post(`${BASE_URL}/api/results`, data)
            .then(response => {
                // this.setState({
                //     listed:this.state.listed.concat(response.data)
                // })
                console.log("Prop details : " + JSON.stringify(response.data));
                console.log("prop status : "+ JSON.stringify(response.status));
                if(JSON.stringify(response.status) == 200){
                    this.setState({
                        redirectVar : <Redirect to= "/properties"/>
                })
            }     
        });  
    }

    render() { 
        return ( 
            <div>
             {this.state.redirectVar}
            <div className="bgnd">
                <form className="form-inline1" onSubmit={this.submitSearch}>
                    <input onChange = {this.locationChangeHandler} className="start" type="text"  name="location" placeholder="Location?" required></input>
                    <input onChange = {this.startdateChangeHandler} type="date" className="start" name="startdate" placeholder="MM/DD/YYYY" required/>
                    <input onChange = {this.enddateChangeHandler} type="date" className="start" name="enddate" placeholder="MM/DD/YYYY" required/>
                    <div className="drop">
                    <div class="form-group1">
                        {/* <label className="field-label">Gender</label> */}
                        <select class="start"  onChange = {this.genderChangeHandler}>
                        <option value="Select">Sharing Type</option>
                            <option value="private room">private room</option>
                            <option value="entire place">entire place</option>
                        </select>
                    </div>
                    </div>
                    <div className="drop1">
                    <div class="form-group1">
                        {/* <label className="field-label">Gender</label> */}
                        <select className="start"  onChange = {this.genderChangeHandler}>
                        <option value="Select">Property Type</option>
                            <option value="house">house</option>
                            <option value="townhouse">townhouse</option>
                            <option value="condo">condo</option>
                            <option value="apartment">apartment</option>
                        </select>
                    </div>
                    </div>
                    <div className="drop2">
                    <div class="form-group1">
                        {/* <label className="field-label">Gender</label> */}
                        <select class="start"  onChange = {this.genderChangeHandler}>
                        <option value="Select">Free Wifi</option>
                            <option value="available">available</option>
                            <option value="not available">not available</option>
                        </select>
                    </div>
                    </div>
                    <input onChange = {this.keywordChangeHandler} className="start top4" type="text"  name="keyword" placeholder="keyword?"></input>
                    <div className="slider1 start1">
                        <span className="slidetxt">Min Price: {this.state.price1}</span>
                        <input class="slider" type="range" min="0" max="1000" step="1" value={this.state.price1} onChange={this.handleSlider1}/>
                    </div>
                    <div className="slider2 start2">
                        <span className="slidetxt">Max Price: {this.state.price2}</span>
                        <input class="slider" type="range" min="0" max="1000" step="1" value={this.state.price2} onChange={this.handleSlider2}/>
                    </div>
                    <button class="butsize btn btn-primary">Submit</button>
                </form>
            </div>
            </div>
         );
    }
}
 
export default Home;