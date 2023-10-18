import { useEffect, useState } from "react";
import classes from "./Invest.module.css";
import img from "../../imgs/invest.png";
import SearchFunction from "../Search/SearchFunction";

const URL = require("../../url");

const Invest = () => {
  const [allFunds, setAllFunds] = useState([]);

  useEffect(() => {
    const fetchitems = async () => {
      const response = await fetch(URL.searchURL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      });
      const responseData = await response.json();
      if (!response.ok) {
        throw new Error("Something went wrong in fetch all funds!");
      }

      const loadedData = [];
      console.log("all funds data :", responseData);

      for (const key in responseData) {
        loadedData.push({
          schemaId: responseData[key].schemaId,
          fundHouse: responseData[key].fundHouse,
          schemaName: responseData[key].schemaName,
          schemaCategory: responseData[key].schemaCategory,
          currPrice: responseData[key].currPrice,
          delta: responseData[key].delta,
          description: responseData[key].description,
          symbol: responseData[key].symbol,
        });
      }

      console.log("all funds after organized: ", loadedData);

      setAllFunds(loadedData);
    };

    fetchitems();
  }, []);

  const onClickHandler = (fund) => {
    let item = {
      schemaId: fund.schemaId,
      schemaName: fund.schemaName,
      fundHouse: fund.fundHouse,
    };
    window.location.href =
      "/MFDetails/" +
      item.schemaId +
      "?item=" +
      encodeURIComponent(JSON.stringify(item));
  };

  return (
    <div className={classes.fundsContainer}>
      <div className={classes.search}>
        <p>quickly find which fund you are interested in:</p>
        <SearchFunction fundData={allFunds} />
      </div>
      {allFunds.map((fund) => {
        return (
          <div className={classes.fundBox} key={fund.schemaId}>
            <div className={classes.fundNameContainer}>
              <div className={classes.fundNameInfo}>
                <div>
                  <img src={img} alt="img" />
                </div>
                <div>
                  <p className={classes.name}>
                    {fund.fundHouse} - {fund.schemaName}
                  </p>
                  <p className={classes.description}> {fund.description} </p>
                </div>
              </div>
              <div>
                <button
                  className={classes.invest}
                  onClick={onClickHandler.bind(this, fund)}
                >
                  Invest Now
                </button>
              </div>
            </div>

            <div className={classes.fundInfoContainer}>
              <div>
                <p className={classes.type}>symbol</p> <p>{fund.symbol}</p>
              </div>
              <div>
                <p className={classes.type}>category</p>
                <p>{fund.schemaCategory}</p>
              </div>
              <div>
                <p className={classes.type}>current price</p>
                <p>${fund.currPrice}</p>
              </div>
              <div>
                <p className={classes.type}>return</p>
                <p
                  className={
                    fund.delta > 0
                      ? classes.positiveReturn
                      : classes.negativeReturn
                  }
                >
                  {fund.delta > 0 ? "+" : ""}
                  {fund.delta.toFixed(2)}%
                </p>
              </div>
            </div>
          </div>
        );
      })}
    </div>
  );
};

export default Invest;
