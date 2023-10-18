import { useContext, useEffect, useState } from "react";
import WalletContext from "../../store/wallet-context";
import classes from "./WalletHistory.module.css";
import History from "../mf-details/components/History";

const WalletHistory: React.FC = () => {
  const walletCtx = useContext(WalletContext);

  const [allHistories, setAllHistories] = useState([]);
  const [filteredHistory, setFilteredHistory] = useState([]);

  useEffect(() => {
    setAllHistories(walletCtx.histories);
    setFilteredHistory(walletCtx.histories);
  }, [walletCtx.histories]);

  const historyList = (
    <table className={classes.list}>
      <thead>
        <tr>
          <th>Type</th>
          <th>Amount</th>
          <th>Date</th>
        </tr>
      </thead>
      <tbody>
        {filteredHistory.map((history) => {
          return (
            <tr key={history.id}>
              <td>{history.description}</td>
              <td>{history.amount.toFixed(2)}</td>
              <td>{history.date}</td>
            </tr>
          );
        })}
      </tbody>
    </table>
  );

  if (
    allHistories === null ||
    allHistories === undefined ||
    allHistories.length === 0
  )
  return <h2 className={classes.noHistory}>No transaction history</h2>;

  return (
    <History
      header="Wallet"
      historyList={historyList}
      data={allHistories}
      setFilteredHistory={setFilteredHistory}
    />
  );
};

export default WalletHistory;
