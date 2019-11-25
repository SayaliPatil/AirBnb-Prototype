import React, { Component } from 'react';
import {Redirect} from 'react-router';
import './Properties.css';
import {BASE_URL} from './../../components/Configs/Configs.js';
import axios from 'axios';
import {Link} from 'react-router-dom';
class Properties extends Component {
    constructor(props) {
        super(props);
        this.state = { 
           propertiesList : [],
           current : 1,
           itemsPerPage : 2,
           activePage: 1
         }
         this.showBooking = this.showBooking.bind(this);
         this.clickHandler = this.clickHandler.bind(this);
    }

    clickHandler(event) {
        this.setState({
            current: Number(event.target.id)
        });
      }

    // componentWillMount() {
    //     console.log("photos : ", this.state.propertiesList);
    // }
    componentDidMount(){
        console.log("photos : ", this.state.propertiesList[0]);
        axios.get(`${BASE_URL}/api/allproperties`,)
        .then((response) => {
                console.log("inside componentDidMount of Properties :", JSON.stringify(response.data))
                this.setState({
                    propertiesList: response.data
                })
        });
        // var list = [
        //     {headline: "nice suit", address: 'san jose', startdate: 11/12/19, enddate: 11/12/19, price: 20},
        //     {headline: "beautiful place", address: 'san bruno', startdate: 11/12/19, enddate: 11/12/19, price: 20},
        //     {headline: "wonderful house", address: 'santa clara', startdate: 11/12/19, enddate: 11/12/19, price: 20}
        // ];
        // this.setState({propertiesList:list})
    }
    showBooking = (ID ,e) => {
        console.log("ID : " + ID);  
        e.preventDefault();
        localStorage.setItem("propid", ID);
        this.setState({
            redirectVar : <Redirect to= "/booking"/>
        })
    }
    render() { 
        const { current, itemsPerPage } = this.state;
        const indexOfLastPage = current * itemsPerPage;
        const indexOfFirstPage = indexOfLastPage - itemsPerPage;
        const currentTodos = this.state.propertiesList.slice(indexOfFirstPage, indexOfLastPage);
        console.log("Number of properties : " + this.state.propertiesList.length);
        
        const pageNumbers = [];
        for (let i = 1; i <= Math.ceil(this.state.propertiesList.length / itemsPerPage); i++) {
            pageNumbers.push(i);
        }
        const showPageNumbers1 = pageNumbers.map(number => {
            return (
              <li class="page-item active"
                key={number}
                id={number}
                onClick={this.clickHandler}
                className="nums"
              >
          {number}
              </li>
            );
          });

        console.log("all properties ", this.state.propertiesList);
        // if(this.state.propertiesList != null) {
            let propDetails = currentTodos.map(propertyItem => {
                console.log("property headline :", propertyItem.headline)
                return (
                    <div className="prop">
                    <div class="row">
                    <div class="col-sm-5" className="backColor5_list">                  
                    {/* <img src={propertyItem.photos} className="mediao"/> */}
                    </div>
                    <div class="col-sm-7" className="backColor_dash_list">  
                    <p className="headline_list"><Link to="/booking" onClick = {this.showBooking.bind(this, propertyItem.id)}>{propertyItem.headline}</Link></p>                   
                    <ul class="list-inline">
                    <li>Address : {propertyItem.address}</li> 
                    <li>StartDate : {propertyItem.startdate}</li>
                    <li>EndDate : {(propertyItem.enddate).toString()}</li>                
                    </ul>
                    <br/>
                    <ul class="list-inline">
                    <li>Price : ${propertyItem.price} per night</li>
                    </ul>
                    </div>
                    </div>
                    </div>              
                  );
        });    
    // }
    return (
        <div>
        {this.state.redirectVar}
            <div className="noresult">
                <h>{this.state.propertiesList.length} RESULTS FOUND</h></div>
                <div className="prop_pagi">
                <nav aria-label="Page navigation example">
                <ul class="pagination">
                {showPageNumbers1}
                </ul>
                </nav>
                </div>
                <div>
                    {propDetails}
                </div>
            <div></div>
        </div>
    );
}
}
export default Properties;