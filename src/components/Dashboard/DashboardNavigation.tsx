import { NavLink } from "react-router-dom";
import classes from "./DashboardNavigation.module.css";

const DashboardNavigation = () => {
  return (
    <header className={classes.header}>
      <nav>
        <ul>
          <li>
            <NavLink
              to="/dashboard"
              className={({ isActive }) =>
                isActive ? classes.active : undefined
              }
              end
            >
              Portfolio
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/dashboard/wishlist"
              className={({ isActive }) =>
                isActive ? classes.active : undefined
              }
            >
              <div className={classes.wishlist}>Wishlists</div>
            </NavLink>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default DashboardNavigation;
