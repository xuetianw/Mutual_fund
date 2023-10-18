import {useContext} from 'react';
import { Link } from "react-router-dom";
import classes from "./WalletSummary.module.css";
import WalletContext from '../../store/wallet-context';

const WalletSummary = () => {
  const walletCtx = useContext(WalletContext);

  const walletBalance = walletCtx.walletBalance;
  const balance = `$ ${walletBalance.toFixed(2)}`;

  return (
    <div className={classes.walletSummary}>
      <div>
        <div className={classes.wallet}><h2> Wallet Summary</h2></div>
      </div>
      <div className={classes.balance}>
        <p>Current Balance:</p>
        <span>{balance}</span>
      </div>
      <Link to="walletDetails">
        <div className={classes.walletdetail}> Wallet Details</div>
      </Link>
    </div>
  );
};

export default WalletSummary;
