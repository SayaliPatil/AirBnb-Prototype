import React, {Component} from 'react';
import {Route} from 'react-router-dom';
import Home from './Home/Home';
import Properties from './Properties/Properties';
import Booking from './Booking/Booking';
class Main extends Component {
    render(){
        return(
            <div>
                <Route path="/home" component={Home}/>
                <Route path="/properties" component={Properties}/>
                <Route path="/booking" component={Booking}/>
            </div>
        )
    }
}
//Export The Main Component
export default Main;