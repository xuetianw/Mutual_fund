import { useState, useEffect, useContext } from "react";

import WalletContext from "./wallet-context";
import AuthContext from "./auth-context";

const URL = require("../url");

const WalletProvider = (props) => {
  const authCtx = useContext(AuthContext);
  const [balance, setBalance] = useState(0);
  const [histories, setHistories] = useState([]);
  const token = authCtx.token;
  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(URL.balanceURL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ token: token }),
      });
      const responseData = await response.json();
      console.log("balance data: ", responseData)
      const responseBalance = parseFloat(responseData.balance);
      if (responseBalance) {
        setBalance(Number(responseBalance));
      } else {
        setBalance(0);
      }
    };

    fetchData();
  }, [token]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(URL.historiesURL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ token: token }),
      });
      const responseData = await response.json();
      console.log("histories data: ", responseData);
      const loadedHistories = [];

      for (const key in responseData) {
        loadedHistories.push({
          id: responseData[key].id,
          amount: parseFloat(responseData[key].amount),
          typeId: responseData[key].typeId,
          date: responseData[key].date,
          description: responseData[key].description,
        });
      }

      const historyLimit = loadedHistories.reverse();

      setHistories(historyLimit);
    };

    fetchData();
  }, [token]);

  const historyTracker = async (amount, type) => {
    let currentDate = new Date();
    const date = currentDate.toISOString().split("T")[0];

    const updatedHistory = {
      id: Math.random(),
      amount: parseFloat(amount),
      description: type,
      date: date,
    };
    let typeId = type === "Deposit" ? 1 : 2;
    const postHistory = {
      amount: parseFloat(amount),
      typeId: typeId,
      token: token,
    };
    setHistories((previousHistories) => {
      return [updatedHistory, ...previousHistories];
    });

    const response = await fetch(URL.transactURL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(postHistory),
    });
    // window.location.reload();
  };

  const addAmountHandler = (amount) => {
    // console.log("type amount: ", Number(amount));
    // console.log("type balance: ", typeof balance);
    setBalance(balance + Number(amount));
    historyTracker(amount, "Deposit");
  };

  const withdrawAmountHandler = (amount) => {
    const newBalance = balance - Number(amount);
    if(newBalance < 0) {
      alert("Sorry, you don't have enough money to withdraw")
    } else {
      setBalance(balance - Number(amount));
      historyTracker(amount, "Withdraw");
    }
  };

  const buyMFHandler = (amount) => {
    const newBalance = balance - amount;
    setBalance(newBalance);
  }

  const sellMFHandler = (amount) => {
    const newBalance = balance + amount;
    setBalance(newBalance);
  }

  const walletContext = {
    histories: histories,
    walletBalance: balance,
    addAmount: addAmountHandler,
    withdrawAmount: withdrawAmountHandler,
    buyMF: buyMFHandler,
    sellMF: sellMFHandler
  };

  return (
    <WalletContext.Provider value={walletContext}>
      {props.children}
    </WalletContext.Provider>
  );
};

export default WalletProvider;
