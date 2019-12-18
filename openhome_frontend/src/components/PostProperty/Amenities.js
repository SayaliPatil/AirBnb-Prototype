import React,{Component} from 'react';
import Button from "react-bootstrap/Button";

class Amenities extends Component{
    constructor(props)
    {
        super(props);

        this.state = {
            parking: '',
            wifi: '',
            extra: []
        }

        this.handleSave = this.handleSave.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    validateForm() {
        return this.state.parking.length > 0 && this.state.wifi.length > 0;
    }

    handleSave = (e) =>
    {
        for (const [key, value] of Object.entries(this.state)) {
            // console.log(`There are ${key} ${value}`);
            this.props.setProps(key,value);
        }
        this.props.setProps("ameneties",true);
        alert("Amenities details Saved Successfully");
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

    checkbox = (e) =>
    {
        const target = e.target;
        if(target.checked)
            this.state.extra.push(target.name);
    }


    render()
    {

        return(
            <div>
                <h3>Select Amenities Available</h3>
                <hr></hr>
                <div className="row">
                    <div className="form-group col-lg-6">
                        <label>Parking</label>
                        <select className="form-control form-control-lg" name="parking" onChange={this.handleChange}>
                            <option value="">Select Option</option>
                            <option value="na">Not Available</option>
                            <option value="free">Available (Free)</option>
                            <option value="paid">Available (Paid)</option>
                        </select>
                    </div>

                    {this.state.parking == "paid" ? <div className="form-group col-lg-6"><label>Cost for parking</label>
                        <input type="text" className="form-control form-control-lg" name="parkingprice" placeholder="Parking Cost" onChange={ (e) => this.handleChange(e)}/>
                    </div> : ""}
                </div>
                <div className="row">
                    <div className="form-group col-lg-6">
                        <label>Free Wifi</label>
                        <select className="form-control form-control-lg" name="wifi" onChange={this.handleChange}>
                            <option value="">Select Option</option>
                            <option value="no">Not Available</option>
                            <option value="yes">Available (Free)</option>
                        </select>
                    </div>
                </div>
                <div className="form-group col-lg-6">Please Check Other Amenities available</div>
                <div className="form-group row">
                    <div className="form-group col-lg-2">
                    </div>
                    <div className="form-group col-lg-6">
                        <div className="form-check">
                            <input className="form-check-input" type="checkbox" name="onsiteLaundry" onClick={this.checkbox} />
                                <label className="form-check-label" htmlFor="onsiteLaundry">
                                    Onsite Laundry
                                </label>
                        </div>
                        <div className="form-check">
                            <input className="form-check-input" type="checkbox" name="cityview" onClick={this.checkbox} />
                            <label className="form-check-label" htmlFor="cityview">
                                City View
                            </label>
                        </div>
                        <div className="form-check">
                            <input className="form-check-input" type="checkbox" name="smoking" onClick={this.checkbox} />
                            <label className="form-check-label" htmlFor="smoking">
                                Smoking Free
                            </label>
                        </div>
                    </div>
                </div>

                <Button
                    className = "btn btn-primary"
                    block
                    disabled={!this.validateForm()}
                    type="button"
                    onClick={ (e) => this.handleSave(e) }
                >
                    Save
                </Button>
            </div>
        )
    }
}

export default Amenities;
