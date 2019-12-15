// Add utility methods
import { createBrowserHistory } from 'history';
export const history = createBrowserHistory();

const headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};

export const saveUserDetails = (data, type) => {
    console.log("user details : ",data);
        localStorage.setItem('currentUser',JSON.stringify(data.email));
        localStorage.setItem('ID',JSON.stringify(data.id));
        localStorage.setItem('role',JSON.stringify(data.user_role));
        localStorage.setItem('first_name',JSON.stringify(data.first_name));
};

export const getUserDetails=()=>{
    console.log("currentUSer:",localStorage.currentUser);
  if(localStorage.currentUser){
    var userdetail = JSON.parse(localStorage.currentUser);
    return (userdetail?userdetail:null);
  }
  else{
      return null;
  }
}
// export const getUserFirstName=()=>{
//   if(localStorage.first_name){
//     var userdetail = JSON.parse(localStorage.first_name);
//     return (userdetail?userdetail:null);
//   }
//   else{
//     return null;
//   }
// }

export const getUserRole=()=>{
  if(localStorage.role){
    var userrole = JSON.parse(localStorage.role);
    return (userrole?userrole:null);
  }
  else{
    return null;
  }
}

export const getUserFirstName=()=>{
  if(localStorage.first_name){
    var userdetail = JSON.parse(localStorage.first_name);
    return (userdetail?userdetail:null);
  }
  else{
    return null;
  }
}

export const getUserIdDetails=()=> {
	if(localStorage.ID) {
		return (localStorage.ID);
	}
	else {
		return null;
	}
}

export const deleteUserDetails = () => {
    localStorage.clear();
};

export const getUserHTTPHeader = function(){
    var header = {
        ...headers,
        servertoken:localStorage.userServertoken?localStorage.userServertoken:null
    }
    return header;
};
