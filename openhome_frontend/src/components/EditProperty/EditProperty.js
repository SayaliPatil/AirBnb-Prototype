import React,{Component} from 'react';
import axios from "axios";
import {BASE_URL} from "../Configs/Configs";
// import Header from "../header/header";
import {startEndDateValidity} from "../../utils/validation";

class EditProperty extends Component{
    constructor(props)
    {
        super(props);
        this.state = {
            property: {},
            display: [],
            availabledays: [],
            monday: ''
        }
    }

    updateProperty = (e) => {

        if(startEndDateValidity(this.state.startdate,this.state.enddate)) {
            console.log("Inside updateProperty");

            var data = {
                id: this.state.property_id,
                startdate: this.state.startdate,
                enddate: this.state.enddate,
                weekdayprice: this.state.weekdayprice,
                weekendprice: this.state.weekendprice,
                availabledays: this.state.availabledays.join(";")
            }

            axios.patch(BASE_URL + '/updateProperty', data)
                .then((result) => {
                    // alert("Sending property");
                    if (result.status === 200) {
                        alert('Property Updated Successfully');
                        this.setState({uploaded: true});
                    }
                    else
                    {
                        alert('Property edit failed. Please Retry');
                        e.preventDefault();
                    }
                });
        }
        else
        {
            e.preventDefault();
        }
    }

    checkbox = (e) =>
    {
        // alert("checkbox clicked");
        const target = e.target;
        let index = this.state.availabledays.indexOf(target.name);
        if (index > -1) {
            alert("present");
            this.state.availabledays.splice(index, 1);
        }
        else {
            alert("absent");
            this.state.availabledays.push(target.name);
        }
    }

    handleChange = (e) =>
    {
        const target = e.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    // getChecked = (name) => {
    //      return this.state.availabledays.indexOf(name)>-1
    // }

    componentDidMount(){
        console.log("inside componentDidMount of booking")
        var id = this.props.match.params.id;
        axios.get(BASE_URL + '/api/booking/' + id)
            .then((response) => {
                console.log("response", response)
                if(response.status == 200)
                {
                    this.setState({
                        display : this.state.display.concat(response.data)
                    });
                }
                this.state.display.map(Item => {
                    this.setState({
                        property_id : Item.id
                    })
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
                        weekdayprice : Item.weekdayprice,
                    });
                    this.setState({
                        weekendprice : Item.weekendprice,
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
                        startdate : Item.startdate.split("T")[0]
                    });
                    this.setState({
                        enddate : Item.enddate.split("T")[0]
                    });
                    this.setState({
                        availabledays: Item.availabledays.split(";")
                    })
                })

            });
    }
    render()
    {
        return (
            <div>
                {/*<Header/>*/}
                <div className="main_cont">
                    <div className="main-div5">
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
                        {/*<form onSubmit={this.updateProperty}>*/}
                        <h2>${this.state.price}</h2><p>per night</p>
                            <div className="form-row">
                                <div className="form-group col-lg-6">
                                    <h5><label>Weekday Price ($)</label></h5>
                                    <input value={this.state.weekdayprice} type="text" className="form-control form-control-lg" placeholder="$" name="weekdayprice" onChange={this.handleChange}/>
                                </div>
                                <div className="form-group col-lg-6">
                                    <h5><label>Weekend Price ($)</label></h5>
                                    <input value={this.state.weekendprice} type="text" className="form-control form-control-lg" placeholder="$" name="weekendprice" onChange={this.handleChange}/>
                                </div>
                            </div>
                            <div className="form-row">
                                <div className="form-group col-md-6">
                                    <h5><label htmlFor="checkin">Available from</label></h5>
                                    <input onChange={this.handleChange} type="date" data-date=""
                                           // data-date-format="DD MMMM YYYY"
                                           value={this.state.startdate} name="startdate"
                                           // min={this.state.startdate} max={this.state.enddate}
                                           className="form-control txt1 hiett" id="checkin" placeholder="yyy-mm-dd"
                                           required/>
                                </div>
                                <div className="form-group col-md-6">
                                    <h5><label htmlFor="checkout">Available till</label></h5>
                                    <input onChange={this.handleChange} type="date" data-date=""
                                           // data-date-format="DD MMMM YYYY"
                                           value={this.state.enddate} name="enddate"
                                           // min={this.state.startdate} max={this.state.enddate}
                                           className="txt1 hiett form-control" id="checkout" placeholder="yyyy-mm-dd"
                                           required/>
                                </div>
                            </div>
                            <h5><label>Available Days</label></h5>
                            <div>
                                <div className="form-group">
                                    <div className="form-check-inline">
                                        <input className="form-check-input" type="checkbox" name="monday" onClick={this.checkbox} />
                                        <label className="form-check-label" htmlFor="monday">
                                            Monday
                                        </label>
                                    </div>
                                    <div className="form-check-inline">
                                        <input className="form-check-input" type="checkbox" name="tuesday" onClick={this.checkbox} />
                                        <label className="form-check-label" htmlFor="tuesday">
                                            Tuesday
                                        </label>
                                    </div>
                                    <div className="form-check-inline">
                                        <input className="form-check-input" type="checkbox" name="wednesday" onClick={this.checkbox} />
                                        <label className="form-check-label" htmlFor="wednesday">
                                            Wednesday
                                        </label>
                                    </div>
                                    <div className="form-check-inline">
                                        <input className="form-check-input" type="checkbox" name="thursday" onClick={this.checkbox} />
                                        <label className="form-check-label" htmlFor="thursday">
                                            Thursday
                                        </label>
                                    </div>
                                    <div className="form-check-inline">
                                        <input className="form-check-input" type="checkbox" name="friday" onClick={this.checkbox} />
                                        <label className="form-check-label" htmlFor="friday">
                                            Friday
                                        </label>
                                    </div>
                                    <div className="form-check-inline">
                                        <input className="form-check-input" type="checkbox" name="saturday" onClick={this.checkbox} />
                                        <label className="form-check-label" htmlFor="saturday">
                                            Saturday
                                        </label>
                                    </div>
                                    <div className="form-check-inline">
                                        <input className="form-check-input" type="checkbox" name="sunday" onClick={this.checkbox} />
                                        <label className="form-check-label" htmlFor="sunday">
                                            Sunday
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <button className="hiett roundbutton btn btn-primary"
                                    onClick={(e) => this.updateProperty(e)}>
                                Edit Property
                            </button>
                            <br />
                            <button type="button" className="btn btn-danger"
                                    onClick={(e) => this.submit(e)}>Delete Property
                            </button>
                        {/*</form>*/}
                        <br/>
                    </div>
                </div>
            </div>
        )
    }
}

export default EditProperty;