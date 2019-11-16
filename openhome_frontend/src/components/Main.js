import React, {Component} from 'react';
import {Route} from 'react-router-dom';
import Home from './Home/Home';
import Properties from './Properties/Properties';
class Main extends Component {
    render(){
        return(
            <div>
                <Route path="/home" component={Home}/>
                <Route path="/properties" component={Properties}/>
            </div>
        )
    }
}
//Export The Main Component
export default Main;