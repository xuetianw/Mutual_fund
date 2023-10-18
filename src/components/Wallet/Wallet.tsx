import { useState, useContext } from "react";

import Modal from "../UI/Modal";
import Transaction from "./Transaction";
import WalletHistory from "./WalletHistory";
import classes from "./Wallet.module.css";
import WalletContext from "../../store/wallet-context";


const Wallet: React.FC = () => {
  const [modelIsVisible, setModelIsVisible] = useState(false);
  const [transactionType, setTransactionType] = useState("");
  const walletCtx = useContext(WalletContext);

  const hideModelHandler = () => {
    setModelIsVisible(false);
  };

  const showModelHandler = () => {
    setModelIsVisible(true);
  };

  const addHandler = () => {
    showModelHandler();
    setTransactionType("Deposit");
  };

  const withdrawHandler = () => {
    showModelHandler();
    setTransactionType("Withdraw");
  };

  const walletBalance = Number(walletCtx.walletBalance);
  const balance = `$${walletBalance.toFixed(2)}`;

  return (
    <>
      {modelIsVisible && (
        <Modal onClose={hideModelHandler}>
          <Transaction
            transactionType={transactionType}
            onCancel={hideModelHandler}
          />
        </Modal>
      )}
      <div className={classes.wallet}>
        <h1>Wallet Information</h1>
        <div className={classes.balance}>
          <div>wallet balance: <span>{balance}</span></div>
          <div className={classes.actions}>
            <button className={classes.withdraw} onClick={withdrawHandler}>
              withdraw
            </button>
            <button className={classes.add} onClick={addHandler}>
              deposit
            </button>
          </div>
        </div>
      </div>

      <WalletHistory />
    </>
  );
};

export default Wallet;
