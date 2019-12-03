import React,{Component} from "react";
import {States} from "../Configs/Configs";
import Button from "react-bootstrap/Button";

class Description extends Component {
    constructor(props)
    {
        super(props);
        this.state = {
            propname : '',
            propdesc : '',
            proptype : '',
            sharingtype: '',
            bed : '',
            bath : '',
            capacity : '',
            area: ''
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSave = this.handleSave.bind(this);
    }

    validateForm() {
        return this.state.propname.length > 0 && this.state.propdesc.length > 0 && this.state.proptype.length > 0 &&
            this.state.sharingtype.length > 0 && this.state.bed.length > 0 && this.state.bath.length > 0 &&
            this.state.capacity.length >0 && this.state.area.length > 0;
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
        for (const [key, value] of Object.entries(this.state)) {
            // console.log(`There are ${key} ${value}`);
            this.props.setProps(key,value);
        }
    }

    render()
    {
        let num = ['0','1','2','3','4','5','6','7','8','9','10'];
        const numList = num.map(item => {
            return <option> {item} </option>
        })

        const capacityList = num.slice(1,11).map(item => {
            return <option> {item} </option>
        })
        return(
            <div>
                <h3>Describe your Property</h3>
                <hr></hr>
                <div className="form-group col-lg-8"><label>Property Name</label>
                    <input type="text" className="form-control form-control-lg" placeholder="Enter Property Name" name="propname" onChange={this.handleChange}/>
                </div>
                <div className="form-group col-lg-12">
                    <label>Description</label>
                    <textarea className="form-control form-control-lg" rows="5" id="description" placeholder="Enter description" name="propdesc" onChange={this.handleChange}></textarea>
                </div>
                <div className="row">
                    <div className="form-group col-lg-6">
                        <label>Property Type</label>
                        <select className="form-control form-control-lg" id="type" name="proptype" onChange={this.handleChange}>
                            <option value="">Select</option>
                            <option value="Appartment">Appartment</option>
                            <option value="Villa">Villa</option>
                            <option value="House">House</option>
                        </select>
                    </div>
                    <div className="form-group col-lg-6">
                        <label>Sharing Type</label>
                        <select className="form-control form-control-lg" id="type" name="sharingtype" onChange={this.handleChange}>
                            <option value="">Select</option>
                            <option value="Villa">Villa</option>
                            <option value="House">House</option>
                        </select>
                    </div>
                </div>

            <div className="row">
                <div className="form-group col-lg-6"><label>Bed</label>
                    <select className="form-control form-control-lg"  name="bed" onChange={this.handleChange}>
                    <option value="">Select</option>
                        {numList}
                    </select>
                </div>
                <div className="form-group col-lg-6"><label>Bath</label>
                    <select className="form-control form-control-lg"  name="bath" onChange={this.handleChange}>
                    <option value="">Select</option>
                    {numList}
                    </select>
                </div>
            </div>
                <div className="form-group col-lg-6"><label>Accomodates</label>
                    <select className="form-control form-control-lg"  name="capacity" onChange={this.handleChange}>
                    <option value="">Select</option>
                    {capacityList}
                    </select>
                </div>
                <div className="form-group col-lg-6"><label>Area (in sqft)</label>
                    <input type="text" className="form-control form-control-lg" placeholder="Enter Property Area" name="area" onChange={this.handleChange}/>
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

                {/*<button type="button" className="btn btn-primary" onClick={(e) => this.handleSave(e)}>Save</button>*/}

            </div>
        )
    }
}

export default Description;