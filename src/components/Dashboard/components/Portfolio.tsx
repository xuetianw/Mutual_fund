import { useEffect, useState, useContext } from "react";
import "../stylesheets/Portfolio.css";
import PortfolioTab from "./PortfolioTab";
import PortfolioContext from "../../../store/portfolio-context";

const URL = require("../../../url");

const Portfolio = () => {
  const portfolioCtx = useContext(PortfolioContext);
  // const portfolio = portfolioCtx.portfolio;
  const [portfolio, setPortfolio] = useState([]);

  useEffect(() => {
    setPortfolio(portfolioCtx.portfolio)
  }, [portfolioCtx.portfolio])

  console.log("portfolio inside portfolio component: ", portfolio);

  // Map data to Portfolio Tab
    const res= portfolio.map((data) => {
     return (
       <div key={data.portfolioId}>
         <PortfolioTab data={data} />
       </div>
     );
   });

  
  // console.log(res);
  // Return result
  return <div className="portfolio">{res}</div>;
};

export default Portfolio;
