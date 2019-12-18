import React,{Component} from 'react';
import Button from "react-bootstrap/Button";

class Pricing extends Component{

    constructor(props)
    {
        super(props);

        this.state = {
            startdate : '',
            enddate : '',
            availabledays : [],
            weekendprice : '',
            weekdayprice : ''
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSave = this.handleSave.bind(this);
    }

    validateForm() {
        return this.state.startdate.length > 0 && this.state.enddate.length > 0 && this.state.availabledays.length > 0
            && this.state.weekdayprice.length > 0 && this.state.weekendprice.length > 0;
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
            this.state.availabledays.push(target.name);
        else
        {
            let index = this.state.availabledays.indexOf(target.name);
            if (index > -1) {
                this.state.availabledays.splice(index, 1);
            }
        }
    }

    handleSave = (e) =>
    {
        for (const [key, value] of Object.entries(this.state)) {
            // console.log(`There are ${key} ${value}`);
            if(key !== "availabledays")
                this.props.setProps(key,value);
            else {
                this.props.setProps(key,value.join(";"));
            }
        }
        this.props.setProps("pricingPage",true);
        alert("Pricing details Saved Successfully");
    }

    render()
    {
        return(
            <div>
                <h3>Availability and Pricing</h3>
                <hr></hr>
                <div className="form-row">
                    <div className="col-4"><label>From</label>
                        <input type="date" name='startdate' className="form-control form-control-lg" placeholder="From Date" onChange={this.handleChange}/>
                    </div>
                    <div className="col-4"><label>To</label>
                        <input type="date" name='enddate' className="form-control form-control-lg" placeholder="To Date" onChange={this.handleChange}/>
                    </div>
                </div>
                <div className="form-group">What days of the week is the property available?</div>
                <div className="form-group row">
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
                <h5>How much do you want to charge</h5>
                <div className="row">
                    <div className="form-group col-lg-6"><label>Weekday Price </label>
                        <input type="text" className="form-control form-control-lg" placeholder="$" name="weekdayprice" onChange={this.handleChange}/>
                    </div>
                    <div className="form-group col-lg-6"><label>Weekend Price </label>
                        <input type="text" className="form-control form-control-lg" placeholder="$" name="weekendprice" onChange={this.handleChange}/>
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
                {/*<button type="button" className="btn btn-primary" onClick={ (e) => this.handleSave(e)}>Save</button>*/}

            </div>
        )
    }
}

export default Pricing;
