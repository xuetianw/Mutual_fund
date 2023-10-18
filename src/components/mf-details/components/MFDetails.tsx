import Chart from "chart.js/auto";
import "../stylesheets/MFDetails.css";
import { CategoryScale } from "chart.js";
import { useEffect, useState, useContext } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import LineChart from "./Chart";
import { fetchMFDetails } from "../../../apis/mf";
import { fetchAddToWishlist } from "../../../apis/mf";
import { fetchIsExistInWishlist } from "../../../apis/mf";
import Modal from "./Modal";
import AuthContext from "../../../store/auth-context";
import WalletContext from "../../../store/wallet-context";
import PortfolioContext from "../../../store/portfolio-context";

const MFDetails = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  console.log("queryParams in MFDetails after search", queryParams);
  const props = JSON.parse(decodeURIComponent(queryParams.get("item")));
  console.log("props: ", props);
  Chart.register(CategoryScale);

  const URL = require("../../../url");
  // const location = useLocation();
  // console.log("location: ", location);

  const initState = {
    schemaId: 0,
    fundHouse: "",
    schemaName: "",
    schemaCategory: "",
    description: "",
    currPrice: 0,
    delta: 0,
    symbol: "",
    mfDetailHistory: [
      {
        id: 1,
        date: "",
        schemaId: 1,
        nav: 0,
      },
    ],
  };

  const [chartData, setChartData] = useState(initState);
  const [isDataLoaded, setIsDataLoaded] = useState(false);
  const [isBuyTab, setIsBuyTab] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isExistInWishlist, setIsExistinWishList] = useState(false);
  const [mfQuantity, setMFQuantity] = useState(0);
  const [orderTotal, setOrderTotal] = useState(0);
  const portfolioCtx = useContext(PortfolioContext);
  // const [portfolioFund, setportfolioFund] = useState();

  //Get token
  // const token: string | null = sessionStorage.getItem("token");
  const authCtx = useContext(AuthContext);
  const walletCtx = useContext(WalletContext);
  const token = authCtx.token;

  let portfolio = portfolioCtx.portfolio;
  console.log("check portfolio:  ", portfolio);
  console.log("check props.schemaId:  ", props.schemaId);
  let mf = portfolio.find((item) => {
    return item.schemaId === props.schemaId;
  });
  console.log("check if portfolio have the mf: ", mf);
  let units = 0;
  if (mf) {
    units = mf.mfUnitsAvailable;
  }

  const navigate = useNavigate();
  let selectedBuyTab = isBuyTab
    ? { backgroundColor: "#F4E285" }
    : { backgroundColor: "#EDEDED" };
  let selectedSellTab = !isBuyTab
    ? { backgroundColor: "#F4E285" }
    : { backgroundColor: "#EDEDED" };
  const buyTabHandler = () => {
    setIsBuyTab((isBuyTabSelected) => {
      return isBuyTabSelected ? isBuyTabSelected : !isBuyTabSelected;
    });
  };

  const sellTabHandler = () => {
    setIsBuyTab((isBuyTabSelected) => {
      return isBuyTabSelected ? !isBuyTabSelected : isBuyTabSelected;
    });
  };

  const submitHandler = async (event) => {
    event.preventDefault();
    console.log("order total: ", orderTotal);

    let url = URL.buyMFURL;
    // check validation
    if (isBuyTab) {
      if (walletCtx.walletBalance < orderTotal) {
        alert("Sorry, you don't have enough money to buy");
        return;
      } else {
        walletCtx.buyMF(orderTotal);
      }
    } else {
      if (mf == null) {
        alert("You don't have this mutual fund to sell");
        return;
      }
      if (mf.mfUnitsAvailable < mfQuantity) {
        alert("You don't have enough values of this mutual fund");
        return;
      }
      walletCtx.sellMF(orderTotal);
      url = URL.withdrawMFURL;
    }

    const responses = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: authCtx.token,
      },
      body: JSON.stringify({
        customerId: sessionStorage.getItem("id"),
        schemaId: props.schemaId,
        units: mfQuantity,
      }),
    });

    // const responseData = await responses.json();

    console.log("Response data after transaction: ", responses);

    // const response = await fetch()

    // console.log("url in MF details: ", url);
    // Display modal based upon result
    setIsModalOpen(true);
  };
  // Navigate to portfolio page
  const portfolioHandler = () => {
    navigate("/dashboard");
  };
  // Add to wish list
  const addToWishListHandler = () => {
    if (authCtx.isLoggedIn) {
      const obj = {
        token: token,
        mfSchemaId: props.schemaId,
        mfName: props.schemaName,
        mfFundHouse: props.fundHouse,
      };
      console.log("obj: ", obj);
      fetchAddToWishlist(URL.addToWishlistURL, obj).catch((err) =>
        // alert(err)
        console.log(
          "Some error has occured while trying to add to wishlist",
          err
        )
      );
      // window.location.reload();
      setIsExistinWishList(true);
    } else {
      navigate("/login");
    }
  };

  // Handle MF input field
  const mfQuantityHandler = (e: any) => {
    const val = e.target.value;
    if (val < 0) {
      alert("Value can not be negative");
      e.target.value = 0;
      return;
    }

    setOrderTotal(Number.parseFloat((val * chartData.currPrice).toFixed(2)));
    setMFQuantity(val);
  };

  // Fetch mutual fund data
  useEffect(() => {
    // Fetch MF details
    const obj = { schemaId: props.schemaId };
    console.log("object: ", obj);
    fetchMFDetails(URL.getDetailsURL, obj)
      .then((data) => {
        setChartData(data);
        setIsDataLoaded(true);
      })
      .catch((err) => console.log(err));
    // Check if MF is existed in wish list of not

    const customerId = sessionStorage.getItem("id");
    console.log("customer id: ", customerId);
    fetchIsExistInWishlist(
      `${URL.wishlistExistURL}${customerId}&mfSchemaId=${props.schemaId}`
    )
      .then((data) => {
        console.log(
          "response data inside MFDetails for wishlist exists: ",
          data
        );

        if (data.doesMutualFundExist) setIsExistinWishList(true);
        else setIsExistinWishList(false);
      })
      .catch((err) => console.log("Not in wishlist"));
  }, [URL.getDetailsURL, URL.wishlistExistURL, props.schemaId, token]);

  // Color indicators for growth rate
  let colorInd = "";
  let growth = "";
  if (chartData.delta < 0) {
    growth = chartData.delta + "";
    colorInd = "red";
  } else {
    growth = `+${chartData.delta}`;
    colorInd = "green";
  }
  return (
    <>
      {isModalOpen && <Modal setIsModalOpen={setIsModalOpen} />}

      <div id="mf-details-container">
        <div>
          <div className="mf-details-header-price">
            <div className="mf-title-wrapper">
              <p id="mf-details-name">{`${chartData.symbol} - ${chartData.schemaName}`}</p>
            </div>

            <p id="mf-details-price">
              {`$${chartData.currPrice}`} (
              <label
                style={{ color: colorInd, fontSize: "small" }}
              >{`${growth}%`}</label>
              )
            </p>
          </div>
          <div className="mf-details-wrapper">
            <div className="mf-details-chart">
              {isDataLoaded && (
                <LineChart chartData={chartData} schemaId={props.schemaId} />
              )}
            </div>

            <div className="mf-details-operation">
              <div className="mf-details-btn-wrapper">
                {authCtx.isLoggedIn && (
                  <div className="mf-functions">
                    <button
                      className="mf-details-btn"
                      style={selectedBuyTab}
                      onClick={() => buyTabHandler()}
                    >
                      Buy
                    </button>
                    <button
                      className="mf-details-btn"
                      style={selectedSellTab}
                      onClick={() => sellTabHandler()}
                    >
                      Sell
                    </button>
                  </div>
                )}
              </div>
              {authCtx.isLoggedIn && <hr></hr>}

              <form onSubmit={submitHandler}>
                <div id="quantity">
                  <p>Quantity</p>
                  <input
                    id="mf-details-input"
                    type="number"
                    min={0}
                    onChange={(e: any) => mfQuantityHandler(e)}
                    required
                  />
                </div>
                <br></br>

                <div className="mf-details-transaction-fields">
                  <label>Price / Unit:</label>
                  <label>{`$${chartData.currPrice}`}</label>
                </div>
                <br></br>

                <div className="mf-details-transaction-fields">
                  <label>Order Total:</label>
                  <label>{`$${orderTotal}`}</label>
                </div>
                <br></br>

                {authCtx.isLoggedIn && (
                  <div className="mf-details-transaction-fields">
                    <label>Owning Quantity: </label>
                    <label> {units} </label>
                  </div>
                )}
                <br></br>

                {authCtx.isLoggedIn && (
                  <div className="mf-details-transaction-fields">
                    <label>Wallet Balance: </label>
                    <label> ${walletCtx.walletBalance.toFixed(2)}</label>
                  </div>
                )}
                <br></br>
                {authCtx.isLoggedIn && <button className="btn">Submit</button>}
              </form>
              <div className="format-btn">
                <button className="btn" onClick={() => portfolioHandler()}>
                  View Portfolio
                </button>

                <button
                  className={isExistInWishlist ? "disabledBtn" : "btn"}
                  onClick={() => addToWishListHandler()}
                  disabled={isExistInWishlist ? true : false}
                >
                  {isExistInWishlist && "Already in Wishlist"}
                  {!isExistInWishlist && "Add To Wish List"}
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default MFDetails;
