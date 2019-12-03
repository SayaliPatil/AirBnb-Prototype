import React,{Component} from 'react';

class Amenities extends Component{
    constructor(props)
    {
        super(props);

        this.state = {
            bookingoptions : 'Instant'
        }

        this.handleSave = this.handleSave.bind(this);
        this.handleOptionChange = this.handleOptionChange.bind(this);
    }


    handleSave = (e) =>
    {
        this.props.setOptions(this.state.bookingoptions);
    }

    handleOptionChange = (changeEvent) => {
        this.setState({
            bookingoptions: changeEvent.target.value
        })
    }


    render()
    {

        return(
            <div>
                <h3>Select a Booking options</h3>
                <hr></hr>
                <div className="form-check">
                    <label className="form-check-label">
                        <input type="radio" className="form-check-input" name="optradio" value="Instant"
                               checked={this.state.bookingoptions === 'Instant'} onChange={ (e) => this.handleOptionChange(e)} />Instant Booking
                    </label>
                </div>
                <div className="form-check">
                    <label className="form-check-label">
                        <input type="radio" className="form-check-input" name="optradio" value="Review"
                               checked={this.state.bookingoptions === 'Review'} onChange={ (e) => this.handleOptionChange(e)} />24 Hrs Review
                    </label>
                </div>


            </div>
        )
    }
}

export default Amenities;