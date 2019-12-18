import React,{Component} from "react";
import Form from 'react-bootstrap/Form';
import Button from "react-bootstrap/Button";
import {States} from '../Configs/Configs';
import {zipCodeValidity,stateValidity,cityValidity,phoneNumberValidity} from "../../utils/validation"

class Location extends Component {
constructor(props)
{
    super(props);
    this.state = {
        address: '',
        city : '',
        state: '',
        zip: ''
    }
    this.handleChange = this.handleChange.bind(this);
    this.handleSave = this.handleSave.bind(this);
}

    validateForm() {
        return this.state.address.length > 0 && this.state.city.length > 0 && this.state.state.length > 0 &&
            this.state.zip.length > 0;
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

    handleSave = (e) =>
    {
        if(zipCodeValidity(this.state.zip) && cityValidity(this.state.city)) {
            let address = '';
            for (const [key, value] of Object.entries(this.state)) {
                address += value + ",";
            }
            this.props.setProps("address", address);
        }
        this.props.setProps("locationPage",true);
        alert("Location details Saved Successfully");
    }
    render()
    {

        const states = States.map(item => {
            return <option> {item} </option>
        })

        return(
            <div>
                <h3>Verify the Location of your Rental Address</h3>
                <hr></hr>
                <div className="row">
                    <div className="form-group col-lg-8"><label>Street</label>
                        <input type="text" className="form-control form-control-lg" name="address" placeholder="Enter Street Address" onChange={ (e) => this.handleChange(e)}/>
                    </div>
                </div>
                <div className="row">
                    <div className="form-group col-lg-8"><label>Apartment Number (Optional)</label>
                        <input type="text" className="form-control form-control-lg" name="address2" placeholder="Apartment number" onChange={ (e) => this.handleChange(e)}/>
                    </div>
                </div>
                <div className="row">
                    <div className="form-group col-lg-6">
                        <label>City</label>
                        <input className="form-control form-control-lg" id="type" name="city" onChange={this.handleChange}>

                        </input>
                    </div>
                    <div className="form-group col-lg-6">
                        <label>State</label>
                        <select className="form-control form-control-lg" id="type" name="state" onChange={this.handleChange}>
                            <option value="">Select Option</option>
                            {states}
                        </select>
                    </div>
                </div>
                <div className="row">
                    <div className="form-group col-lg-6"><label>Zip Code</label>
                        <input type="text" className="form-control form-control-lg" name="zip" placeholder="Enter Zip Code" onChange={ (e) => this.handleChange(e)}/>
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

export default Location;
