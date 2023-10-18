import { Outlet, useNavigate } from "react-router-dom";
// import { useContext } from "react";

import DashboardNavigation from "./DashboardNavigation";
import WalletSummary from "./WalletSummary";
import classes from "./Dashboard.module.css";
// import AuthContext from "../../store/auth-context";

const Dashboard: React.FC = () => {
  // const authCtx = useContext(AuthContext);
  // console.log("Token in dashboard stored in authCtx: ", authCtx.token);
  const navigate = useNavigate();

  const investHandler = () => {
    navigate("/invest")
  }

  return (
    <>
      <div className={classes.summary}>
        <h2>
          Conveniently buy and sell mutual funds with MFUOnline anytime,
          anywhere.
        </h2>
        <button className={classes.invest} onClick={investHandler}>Invest Now {">"}</button>
      </div>
      <div className={classes.dashboard}>
        <div className={classes.navigation}>
          <DashboardNavigation />
          <main>
            <Outlet />
          </main>
        </div>
        <div>
          <WalletSummary />
        </div>
      </div>
    </>
  );
};

export default Dashboard;
