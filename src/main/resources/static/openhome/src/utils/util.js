// Add utility methods
import { createBrowserHistory } from 'history';
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
    localStorage.removeItem('userServertoken');
    localStorage.removeItem('propertyLocation');
    localStorage.removeItem('propertyHeadline');
    localStorage.removeItem('propertyDescription');
    localStorage.removeItem('propertyArea');
    localStorage.removeItem('propertyType');
    localStorage.removeItem('bedroomNumber');
    localStorage.removeItem('bathroomNumber');
    localStorage.removeItem('accomodation');
    localStorage.removeItem('bookingType');
    localStorage.removeItem('propertyPricing');
    localStorage.removeItem('availabilityStartDate');
    localStorage.removeItem('availabilityEndDate');
    localStorage.removeItem('nightStay');
    localStorage.removeItem('propertyid');
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
