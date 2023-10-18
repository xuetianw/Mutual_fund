import { useState, useContext } from "react";
import classes from "./Transaction.module.css";
import WalletContext from "../../store/wallet-context";

const Transaction = ({ transactionType, onCancel }) => {
  const [amount, setAmount] = useState(0);
  const walletCtx = useContext(WalletContext);

  const amountChangeHanlder = (event) => {
    setAmount(event.target.value);
  };

  const submitHandler = async (event) => {
    event.preventDefault();

    if (transactionType === "Deposit") {
      walletCtx.addAmount(amount);
    } else if (transactionType === "Withdraw") {
      walletCtx.withdrawAmount(amount);
    }

    onCancel();
  };

  return (
    <form className={classes.form} onSubmit={submitHandler}>
      <p className={classes.inputValue}>
        <label htmlFor="amount">{transactionType} Amount:</label>
        <input
          type="number"
          step={0.01}
          required
          onChange={amountChangeHanlder}
        />
      </p>
      <div className={classes.actions}>
        <button type="button" onClick={onCancel}>
          Cancel
        </button>
        <button>Submit</button>
      </div>
    </form>
  );
};

export default Transaction;
