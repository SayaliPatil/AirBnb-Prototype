import React from 'react';
import logo from './logo.svg';
import Main from './components/Main';
import './App.css';
import {BrowserRouter} from 'react-router-dom';


function App() {
  return (
    <BrowserRouter>
        <div>
          {/* App Component Has a Child Component called Main*/}
          <Main/>
        </div>
      </BrowserRouter>
  );
}

export default App;
