import { useState, useEffect } from "react";
import classes from "./CurrencyEx.module.css";

const URL = require("../../url");

const DUMMY_DATA = [
  { code: "CAD", value: 1 },
  { code: "USD", value: 0.74 },
  { code: "EUR", value: 0.68 },
  { code: "CNY", value: 5.19 },
];

const CurrencyEx = () => {
  const [firstCurrencySelection, setFirstCurrencySelection] = useState("CAD");
  const [secondCurrencySelection, setSecondCurrencySelection] = useState("USD");

  const [firstCurrencyValue, setFirstCurrencyValue] = useState(1);
  const [secondCurrencyValue, setSecondCurrencyValue] = useState(0);

  const [currencyData, setCurrencyData] = useState([]);
  const [firstData, setFirstData] = useState({ code: "CAD", value: 1 });
  const [secondData, setSecondData] = useState({ code: "", value: 0 });

  useEffect(() => {
    const fetchCurrency = async () => {
      const currencyName = "CAD";
      const response = await fetch(URL.currencyURL + currencyName);
      const responseData = await response.json();
      console.log("CURRENCY: ", responseData);
      setCurrencyData(responseData);
      // setCurrencyData(DUMMY_DATA);
      const data = responseData.find((data) => data.code === "USD");
      // const data = DUMMY_DATA.find((data) => data.code === "USD");
      setSecondData(data);
      setSecondCurrencyValue(data.value);
    };

    fetchCurrency();
  }, []);

  const firstCurrencyHandler = (event) => {
    const value = event.target.value;
    setFirstCurrencySelection(event.target.value);
    // console.log(event.target.value, firstCurrencySelection)
    const data = currencyData.find((data) => data.code === value);
    setFirstData(data);
    // console.log("first data", firstData, "data: ", data);
    const exchange = (1 / data.value) * firstCurrencyValue * secondData.value;
    const secondVal = parseFloat(exchange.toFixed(3))
    console.log("secondVal: ", secondVal);
    setSecondCurrencyValue(secondVal);
  };
  const firstInputHandler = (event) => {
    const value = Number(event.target.value);
    setFirstCurrencyValue(value);
    const exchange = (1 / firstData.value) * value * secondData.value;
    const secondVal = parseFloat(exchange.toFixed(3))
    setSecondCurrencyValue(secondVal);
  };

  const secondCurrencyHandler = (event) => {
    const value = event.target.value;
    setSecondCurrencySelection(event.target.value);
    const data = currencyData.find((data) => data.code === value);
    setSecondData(data);

    const exchange = (1 / firstData.value) * firstCurrencyValue * data.value;
    setSecondCurrencyValue(exchange);
  };
  const secondInputHandler = (event) => {
    const value = Number(event.target.value);
    setSecondCurrencyValue(value);
    const exchange = (1 / secondData.value) * value * firstData.value;
    setFirstCurrencyValue(exchange);
  };

  return (
    <div className={classes.currencyExContainer}>
      <p>Currency Exchange</p>
      <div className={classes.CurrencyEx}>
        <input
          type="number"
          value={firstCurrencyValue}
          onChange={firstInputHandler}
          min='0'
        />
        <select value={firstCurrencySelection} onChange={firstCurrencyHandler}>
          {currencyData.map((data) => (
            <option
              key={data.code}
              value={data.code}
              // disabled={data.code === secondCurrencySelection ? true : false}
            >
              {data.code}
            </option>
          ))}
        </select>
      </div>

      <div className={classes.CurrencyEx}>
        <input
          type="number"
          value={secondCurrencyValue}
          onChange={secondInputHandler}
          min='0'
        />
        <select
          value={secondCurrencySelection}
          onChange={secondCurrencyHandler}
        >
          {currencyData.map((data) => (
            <option
              key={data.code}
              value={data.code}
              // disabled={data.code === firstCurrencySelection ? true : false}
            >
              {data.code}
            </option>
          ))}
        </select>
      </div>
    </div>
  );
};

export default CurrencyEx;
