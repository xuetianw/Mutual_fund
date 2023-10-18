import { useNavigate } from "react-router-dom";
import fetchTopBottom5 from "../../../apis/mf";
import "../stylesheets/TopBottomMF.css";
import { JsxElement } from "typescript";
import MFCard from "./MFCard";
import { MF } from "../../../types/MFType";
import { createElement, useEffect, useState } from "react";

const URL = require("../../../url");

const TopBottomMF = () => {
  const [top5Data, setTop5Data] = useState([]);
  const [bottom5Data, setBottom5Data] = useState([]);
  const navigate = useNavigate();

  // Event handler
  const clickHandler = (fund) => {
    // Change back to specific schemaId later
    //navigate(`details/${schemaId}`)
    // navigate(`/details`)

    console.log("fund in top button funds: ", fund);

    let item = {
      schemaId: fund.id,
      schemaName: fund.name,
      fundHouse: fund.fundHouse,
    };

    window.location.href =
      "/MFDetails/" +
      item.schemaId +
      "?item=" +
      encodeURIComponent(JSON.stringify(item));
  };

  useEffect(() => {
    // Top 5 data
    fetchTopBottom5(URL.getTopFiveURL)
      .then((data) => {
        console.log("data fetched in top five funds", data);
        const mappedData = data.map((mf: any) => {
          return {
            id: mf.schemaId,
            symbol: mf.symbol,
            name: mf.schemaName,
            category: mf.schemaCategory,
            growth: mf.delta,
            price: mf.currPrice,
            fundHouse: mf.fundHouse,
          };
        });
        const top5 = mappedData.map((mappedMf: any) => {
          return (
            <div
              key={mappedMf.id}
              className="card-item"
              onClick={clickHandler.bind(this, mappedMf)}
            >
              <MFCard {...mappedMf} />
            </div>
          );
        });
        setTop5Data(top5);
      })
      .catch((err) => {
        console.log("error in Top bottom MF");
      });

    // Bottom 5 data
    fetchTopBottom5(URL.getBottomFiveURL)
      .then((data) => {
        const mappedData = data.map((mf: any) => {
          return {
            id: mf.schemaId,
            symbol: mf.symbol,
            name: mf.schemaName,
            category: mf.schemaCategory,
            growth: mf.delta,
            price: mf.currPrice,
            fundHouse: mf.fundHouse,
          };
        });
        const bottom5 = mappedData.map((mappedMf: any) => {
          return (
            <div
              key={mappedMf.id}
              className="card-item"
              onClick={clickHandler.bind(this, mappedMf)}
            >
              <MFCard {...mappedMf} />
            </div>
          );
        });
        setBottom5Data(bottom5);
      })
      .catch((err) => {
        console.log("error in bottom 5 MF");
      });
  }, []);

  return (
    <>
      <div className="container">
        <div className="info">Top 5 performing mutual funds</div>
        <div className="horizontal-style">{top5Data}</div>
        <hr></hr>
        <div className="info">Bottom 5 performing mutual funds</div>
        <div className="horizontal-style">{bottom5Data}</div>
      </div>
    </>
  );
};

export default TopBottomMF;
