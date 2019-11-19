import React, { Component } from 'react';
import {BrowserRouter, Route, Switch, Redirect} from 'react-router-dom';
import SignUp from './component/signup/signup.js';
import Login from './component/login/login.js';
import VerifyAccount from './component/signup/verifyaccount';
class App extends Component {

  render() {
    return (
      <div>
      <BrowserRouter>
          <Route  exact path="/signup" render ={() => (<SignUp/>)}/>
          <Route  exact path="/login" render ={() => (<Login/>)}/>
          <Route  path="/verifyaccount/:ID" render ={(match) => (<VerifyAccount {...match} />)} />
      </BrowserRouter>
      </div>
    );
  }
}

export default App;
