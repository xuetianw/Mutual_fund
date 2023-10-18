import { useState} from 'react';
import AuthContext from './auth-context'; 

const AuthContextProvider = (props) => {
  let sessionToken = sessionStorage.getItem('token');
  const [token, setToken] = useState(sessionToken);

  const userIsLoggedIn = !!token;

  const loginHandler = (token) => {
    setToken(token);
  }

  const logoutHandler = () => {
    setToken(null);
  }

  const contextValue = {
    token: token,
    isLoggedIn: userIsLoggedIn,
    login: loginHandler,
    logout: logoutHandler,
  };

  return (
    <AuthContext.Provider value={contextValue}>
      {props.children}
    </AuthContext.Provider>
  );
};

export default AuthContextProvider;
