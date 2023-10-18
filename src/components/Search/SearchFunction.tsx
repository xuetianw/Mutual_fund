import React, { useState, useEffect } from "react";
import SearchIcon from "@mui/icons-material/Search";

import classes from "./SearchFunction.module.css";

const SearchFunction = (props) => {
  const [value, setValue] = useState("");
  const [searchBy, setSearchBy] = useState("schemaName");

  const inputHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValue(event.target.value);
  };

  const searchHandler = (event) => {
    console.log(event.target.value);
    setSearchBy(event.target.value);
  };

  const onSearchItemClick = (item) => {
    console.log("clicked", item);
    window.location.href =
      "/MFDetails/" +
      item.schemaId +
      "?item=" +
      encodeURIComponent(JSON.stringify(item));
  };

  return (
    <div className={classes.searchContainer}>
      <div className={classes.searchBox}>
        <div className={classes.searchInner}>
          <div className={classes.searchIcon}>
            <SearchIcon />
          </div>
          <input
            type="text"
            value={value}
            placeholder="Search..."
            onChange={inputHandler}
          />

          <select
            className={classes.selectChoice}
            value={searchBy}
            onChange={searchHandler}
          >
            <option value="schemaName">MF Name</option>
            <option value="schemaCategory">MF Type</option>
            <option value="fundHouse">Fund house</option>
          </select>
          {/* <button onClick={() => onSearch(value)}>Search</button> */}
        </div>
        <div className={classes.dropdown}>
          {props.fundData
            .filter((item) => {
              const searchTerm = value.toLowerCase();
              const searchName = item[searchBy].toLowerCase();
              // console.log(search);
              // const searchName = item.MF_name.toLowerCase();
              // console.log(data.indexOf(item[searchBy]));
              return (
                searchTerm && searchName.includes(searchTerm)
                // && searchName !== searchTerm
              );
            })
            .slice(0, 20) // only show 20 items
            .map((item) => (
              <div
                className={classes.dropdownRow}
                key={item.schemaId}
                onClick={() => onSearchItemClick(item)}
              >
                <div>
                  {item.schemaName} | {item.schemaCategory} | {item.fundHouse}
                </div>
              </div>
            ))}
        </div>
      </div>
    </div>
  );
};

export default SearchFunction;
