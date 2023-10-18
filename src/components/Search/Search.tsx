import React, { useState, useEffect } from "react";
import SearchIcon from "@mui/icons-material/Search";
import img from "./../../imgs/img.png";
import classes from "./Search.module.css";
import SearchFunction from "./SearchFunction";
import CurrencyEx from "../Currency/CurrencyEx";

const URL = require("../../url");

const Search: React.FC = () => {
  const [fundData, setFundData] = useState([]);

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
       throw new Error("Something went wrong!");
      }

      const loadedData = [];
      console.log(responseData);

      for (const key in responseData) {
        loadedData.push({
          schemaId: responseData[key].schemaId,
          fundHouse: responseData[key].fundHouse,
          schemaName: responseData[key].schemaName,
          schemaCategory: responseData[key].schemaCategory,
        });
      }

      console.log(loadedData);
      setFundData(loadedData);
    };

    fetchitems();
  }, []);

 
  return (
    <div className={classes.search}>
      <div className={classes.notice}>
        <h1>Help us find you the right mutual fund</h1>
        <p>What kind of mutual fund are you specifically looking for?</p>
      </div>

      <SearchFunction fundData={fundData} />
      
      <div className={classes.infos}>
        <div className={classes.info}>
          <p className={classes.text1}>
            Conveniently buy and sell mutual funds with MFUOnline anytime,
            anywhere
          </p>
          <p>
            MFUOnline offers M+ mutual funds in various different currencies and
            different risk levels
          </p>
        </div>
        <CurrencyEx />
        <img className={classes.img} src={img} alt="img" />
      </div>
    </div>
  );
};

export default Search;
