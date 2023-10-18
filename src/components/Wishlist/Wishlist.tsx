import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import classes from "./Wishlist.module.css";

const URL = require("../../url");

const Wishlist = () => {
  const [wishlistFunds, setWishlistFunds] = useState([]);

  const clickHandler = (fund) => {
    let item = {
      schemaId: fund.mfSchemaId,
      schemaName: fund.mfName,
      fundHouse: fund.mfFundHouse,
    };

    window.location.href =
      "/MFDetails/" +
      item.schemaId +
      "?item=" +
      encodeURIComponent(JSON.stringify(item));
    // navigate(`/MFDetails/${item}`)
  };

  const removeHandler = async (fund) => {
    await fetch(URL.removeFromWishlistURL + fund.wishlistId, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    });
    setWishlistFunds((prevFunds) =>
      prevFunds.filter((prefund) => prefund.wishlistId !== fund.wishlistId)
    );
  };

  useEffect(() => {
    const fetchitems = async () => {
      const id = sessionStorage.getItem("id");
      const response = await fetch(URL.wishlistCustomerURL + id);
      console.log("wishlist response", response)
      const responseData = await response.json();
      if (!response.ok) {
        throw new Error("fetch wishlist data went wrong!");
      }

      console.log("wishlist response data: ", responseData);

      const loadedData = [];
      console.log("responseData in wishlist: ", responseData);

      for (const key in responseData) {
        console.log(key);
        loadedData.push({
          mfName: responseData[key].mfName,
          mfFundHouse: responseData[key].mfFundHouse,
          mfSchemaId: responseData[key].mfSchemaId,
          wishlistId: responseData[key].wishlistId,
        });
      }

      console.log("wishlist data: ", loadedData);

      setWishlistFunds(loadedData);
    };

    fetchitems();
  }, []);

  const wishlist = (
    <ul className={classes.lists}>
      {wishlistFunds.map((fund) => (
        <div key={fund.mfSchemaId} className={classes.fundContainer}>
          <li className={classes.fund} onClick={clickHandler.bind(null, fund)}>
            <div>{fund.mfFundHouse} </div>
            <div>{fund.mfName} </div>
          </li>
          <button
            className={classes.removeBtn}
            onClick={removeHandler.bind(null, fund)}
          >
            remove
          </button>
        </div>
      ))}
    </ul>
  );

  return <>{wishlist}</>;
};

export default Wishlist;
