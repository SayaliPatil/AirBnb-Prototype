import React, { Component } from 'react';
import {BrowserRouter, Route, Switch, Redirect} from 'react-router-dom';
import SignUp from './component/signup';
class App extends Component {
  render() {
    return (
      <div>
      <BrowserRouter>
        <Switch>
             <Route  exact path="/signup" render ={() => (<SignUp/>)}/>
        </Switch>
      </BrowserRouter>
      </div>
    );
  }
}

export default App;