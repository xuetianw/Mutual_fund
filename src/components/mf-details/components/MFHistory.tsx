import { useContext, useEffect, useState } from "react";
import classes from "../stylesheets/MFHistory.module.css";
import AuthContext from "../../../store/auth-context";
import History from "./History";

const URL = require("../../../url");

const MFHistory = () => {
  const authCtx = useContext(AuthContext);
  // const [filterDate, setFilterDate] = useState("7days");
  const [fundData, setFundData] = useState([]);
  const [filteredHistory, setFilteredHistory] = useState([]);

  // const filterSelectionHandler = (event) => {
  //   setFilterDate(event.target.value);
  // };

  useEffect(() => {
    const fetchHistory = async () => {
      const response = await fetch(URL.mfHistoryURL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: authCtx.token,
        },
      });
      const responseData = await response.json();
      if (!response.ok) {
        throw new Error("Something went wrong when fetching mf history");
      }

      console.log("mf history responseData: ", responseData);
      const loadedData = [];

      for (const key in responseData) {
        loadedData.push({
          historyId: responseData[key].id,
          schemaId: responseData[key].mfDetail.schemaId,
          date: responseData[key].localDate,
          fundHouse: responseData[key].mfFundHouse,
          schemaName: responseData[key].mfName,
          units: responseData[key].mfUnits,
          type: responseData[key].type.description,
          fillePrice: responseData[key].mfDetail.currPrice,
        });
      }
      let reverseData = loadedData.reverse();
      setFundData(reverseData);
      setFilteredHistory(reverseData);
    };

    fetchHistory();
  }, [authCtx.token]);

  // const filteredHistory = dummyData.filter((history) => {
  //   let historyDate = new Date(history.transactionDate);
  //   let preDate = new Date();
  //   switch (filterDate) {
  //     case "7days":
  //       preDate.setDate(preDate.getDate() - 7);
  //       break;
  //     case "oneMonth":
  //       preDate.setMonth(preDate.getMonth() - 1);
  //       break;
  //     case "6months":
  //       preDate.setMonth(preDate.getMonth() - 6);
  //       break;
  //   }
  //   if (filterDate === "all") return true;
  //   return historyDate >= preDate;
  // });

  const onClikeHandler = (data) => {
    console.log("clicked", data);
    let item = {
      schemaId: data.schemaId,
      schemaName: data.schemaName,
      fundHouse: data.fundHouse
    };
    console.log("item: ", item);
    window.location.href = '/MFDetails/' + item.schemaId + '?item=' + encodeURIComponent(JSON.stringify(item))
  }

  const historyList = (
    <table>
      <thead>
        <tr>
          <th>Type</th>
          <th>Fund House</th>
          <th>Schema Name</th>
          <th>Filled Quantity </th>
          <th>Filled Price </th>
          <th>Transaction Date </th>
        </tr>
      </thead>
      <tbody>
        {filteredHistory.map((history) => {
          const type = history.type;
          const typeCap = type.charAt(0).toUpperCase() + type.slice(1)
          console.log("history: ", history)
          return (
          <tr key={history.historyId} onClick={onClikeHandler.bind(this, history)}>
            <td>{typeCap}</td>
            <td>{history.fundHouse}</td>
            <td>{history.schemaName}</td>
            <td>{history.units}</td>
            <td>{history.fillePrice.toFixed(2)}</td>
            <td>{history.date}</td>
          </tr>
        )})}
      </tbody>
    </table>
  );

  if (fundData === null || fundData === undefined || fundData.length === 0)
    return <h2 className={classes.noHistory}>No transaction history</h2>;
    
  return (
    <History
      header="Mutual Funds"
      historyList={historyList}
      data={fundData}
      setFilteredHistory={setFilteredHistory}
    />
  );
  // else {
  //   return (
  //     <div className={classes.historyContainer}>
  //       <form className={classes.selection} onSubmit={filterHandler}>
  //         {/* <select
  //           className={classes.selectChoice}
  //           value={filterDate}
  //           onChange={filterSelectionHandler}
  //         >
  //           <option value="7days"> 7 days ago</option>
  //           <option value="oneMonth"> one month ago</option>
  //           <option value="6months"> six months ago</option>
  //           <option value="all"> all histories</option>
  //         </select> */}
  //       </form>
  //       {historyList}
  //     </div>
  //   );
  // }
};

export default MFHistory;
