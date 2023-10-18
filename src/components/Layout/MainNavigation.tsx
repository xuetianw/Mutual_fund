import { NavLink } from "react-router-dom";
import classes from "./MainNavigation.module.css";
import Logout from "../login/components/Logout";
import { useContext } from "react";
import AuthContext from "../../store/auth-context";

const MainNavigation: React.FC = () => {
  const authCtx = useContext(AuthContext);
  const isLoggedIn = authCtx.isLoggedIn;
  // console.log("MainNavigation token: ", authCtx.token)

  return (
    <header className={classes.header}>
      <NavLink
        to="/"
        className={({ isActive }) => (isActive ? classes.active : undefined)}
        end
      >
        <div className={classes.logo}>MFUOnline</div>
      </NavLink>
      <nav>
        <ul>
          {isLoggedIn && (
            <li>
              <NavLink
                to="/dashboard"
                className={({ isActive }) =>
                  isActive ? classes.active : undefined
                }
              >
                <div className={classes.dashboard}>Dashboard</div>
              </NavLink>
            </li>
          )}
          {isLoggedIn && (
            <li>
              <NavLink
                to="/invest"
                className={({ isActive }) =>
                  isActive ? classes.active : undefined
                }
              >
                <div className={classes.dashboard}>Invest</div>
              </NavLink>
            </li>
          )}
          {isLoggedIn && (
            <li>
              <NavLink
                to="/mfHistory"
                className={({ isActive }) =>
                  isActive ? classes.active : undefined
                }
              >
                <div className={classes.dashboard}>History</div>
              </NavLink>
            </li>
          )}
          {!isLoggedIn && (
            <li>
              <NavLink
                to="/login"
                className={({ isActive }) =>
                  isActive ? classes.active : undefined
                }
              >
                <div className={classes.login}>Login</div>
              </NavLink>
            </li>
          )}
          {!isLoggedIn && (
            <li>
              <NavLink
                to="/registration"
                className={({ isActive }) =>
                  isActive ? classes.active : undefined
                }
              >
                <div className={classes.login}>Register</div>
              </NavLink>
            </li>
          )}
          {isLoggedIn && (
            <li>
              <Logout />
            </li>
          )}
        </ul>
      </nav>
    </header>
  );
};

export default MainNavigation;
