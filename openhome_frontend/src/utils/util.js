// Add utility methods
import { createBrowserHistory } from 'history';
import error_icon from './../images/error_icon.jpg';
import info_icon from './../images/info_icon.png';
// import success_icon from './../images/success_icon.jpg';
export const history = createBrowserHistory();

const headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};

export const saveServerToken = (userdata, servertoken, type) => {
    console.log("saveServerToken",userdata);
        localStorage.setItem('currentUser',JSON.stringify(userdata));
        localStorage.setItem('userServertoken',servertoken);
};

export const getUserDetails=()=>{
  if(localStorage.currentUser){
    var userdetail = JSON.parse(localStorage.currentUser);
    return (userdetail?userdetail:null);
  }
  else{
    return null;
  }

}
export const getServerTokenDetails=()=> {
	if(localStorage.userServertoken) {
		return (localStorage.userServertoken);
	}
	else {
		return null;
	}
}

export const deleteServerToken = () => {
    localStorage.removeItem('currentUser');
      alert("user will be logged out");
      history.push('/');
};

export const getUserHTTPHeader = function(){
    var header = {
        ...headers,
        servertoken:localStorage.userServertoken?localStorage.userServertoken:null
    }
    return header;
};
