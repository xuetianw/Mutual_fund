import { useState } from "react";

import classes from "../stylesheets/History.module.css";

const History = ({ header, historyList, data, setFilteredHistory }) => {
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());

  let today = new Date();
  let todayFormat = today.toISOString().split("T")[0];
  let histories = data;
  let oldestDate = histories[histories.length - 1].date;

  const startDateHandler = (event) => {
    setStartDate(event.target.value);
  };

  const endDateHandler = (event) => {
    setEndDate(event.target.value);
  };

  const filterHandler = (event) => {
    event.preventDefault();
    let filtered = data.filter((history) => {
      let historyDate = new Date(history.date);
      console.log(
        "startDate type: ",
        typeof startDate,
        "historyDate type: ",
        typeof historyDate
      );
      // console.log(
      //   "test date: history date",
      //   historyDate,
      //   "startDate",
      //   new Date(startDate),
      //   historyDate >= new Date(startDate)
      // );
      return (
        historyDate >= new Date(startDate) && historyDate <= new Date(endDate)
      );
    });
    setFilteredHistory(filtered);
    console.log("filtered: ", filtered);
  };

  return (
    <div className={classes.historyContainer}>
      <form className={classes.selection} onSubmit={filterHandler}>
        <h2>{header} Transaction History</h2>
        <div className={classes.filter}>
          <div className={classes.dateSelection}>
            <label htmlFor="startDate">Start Date:</label>
            <input
              type="date"
              name="startDate"
              // min={oldestDate}
              // max={todayFormat}
              // value={oldestDate}
              required
              onChange={startDateHandler}
            />
          </div>
          <div className={classes.dateSelection}>
            <label htmlFor="endDate">End Date:</label>
            <input
              type="date"
              name="endDate"
              // min={oldestDate}
              // max={todayFormat}
              // value={todayFormat}
              required
              onChange={endDateHandler}
            />
          </div>

          <button>filter</button>
        </div>

        {/* <select
          className={classes.selectChoice}
          value={filterDate}
          onChange={filterSelectionHandler}
        >
          <option value="7days"> 7 days ago</option>
          <option value="oneMonth"> one month ago</option>
          <option value="6months"> six months ago</option>
          <option value="all"> all histories</option>
        </select> */}
      </form>
      {historyList}
    </div>
  );
};

export default History;
