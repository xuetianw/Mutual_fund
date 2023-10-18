import {redirect} from 'react-router-dom'

export function getAuthToken() {
  const token = sessionStorage.getItem("token");

  // console.log("util auth token: ", token);
  return token;
}

export function tokenLoader () {
  return getAuthToken();
}

export function checkAuthLoader() {
  const token = sessionStorage.getItem("token");
  // console.log("util auth token in checkAuthLoader function: ", token);
  if(!token) {
    return redirect('/login')
  }

  return null;
}