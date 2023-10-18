import AuthContext from "./auth-context";
import PortfolioContext from "./portfolio-context";
import {useState, useEffect, useContext} from 'react';

const URL = require("../url")

const PortfolioContextProvider = (props) => {
  const authCtx = useContext(AuthContext);

  const [portfolio, setportfolio] = useState([]);

  useEffect(() => {
    const id = sessionStorage.getItem("id");
    console.log("id: ", id);
    const fetchDate = async () => {
      const response = await fetch(URL.portfoliosURL + id, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authentication: authCtx.token
        }
      });

      const responseData = await response.json();

      console.log("portfolio data: ", responseData);

      const loadedportfolio = [];
      for (const key in responseData) {
        loadedportfolio.push({
          schemaId: responseData[key].mfDetail.schemaId,
          currency: responseData[key].currency,
          customerId: responseData[key].customerId,
          mfFundHouse: responseData[key].mfDetail.fundHouse,
          mfName: responseData[key].mfDetail.schemaName,
          mfUnitsAvailable:
            responseData[key].mfUnitsAvailable,
          portfolioId: responseData[key].portfolioId,
          transactionDate:
            responseData[key].transactionDate,
          currPrice: responseData[key].mfDetail.currPrice,
        });
      }

      console.log("portfolio data after organiza: ", loadedportfolio)
      setportfolio(loadedportfolio);
    };

    fetchDate();
  }, [authCtx.token]);

  const sellMFHandler = () => {

  }

  const portfolioContext = {
    portfolio: portfolio,
    sellMF: sellMFHandler,
  };

  return ( 
    <PortfolioContext.Provider value={portfolioContext}>
      {props.children}
    </PortfolioContext.Provider>
   );
}
 
export default PortfolioContextProvider;