import { useState } from "react";
import "../stylesheets/PortfolioTab.css";
import { AiOutlineArrowDown, AiOutlineArrowUp } from "react-icons/ai";

const PortfolioTab = ({ data }: any) => {
  const [isExtended, setIsExtended] = useState(false);

  const clickExtendBtnHandler = () => {
    setIsExtended((prev) => !prev);
  };

  const onClikeHandler = (data) => {
    console.log("clicked", data);
    let item = {
      schemaId: data.schemaId,
      schemaName: data.mfName,
      fundHouse: data.mfFundHouse
    };
    window.location.href = '/MFDetails/' + item.schemaId + '?item=' + encodeURIComponent(JSON.stringify(item))
  }

  const generatedRows = () => {
    const price = Number(data.currPrice);
    const totalPrice = (data.mfUnitsAvailable * price).toFixed(2);
    console.log("totoalPrice: ", totalPrice)
    if(data.mfUnitsAvailable <= 0){
      return null;
    }
    return (
      <tr key={data.portfolioId}>
        <td>{data.mfFundHouse}</td>
        <td>{data.mfName}</td>
        <td>{data.mfUnitsAvailable}</td>
        <td>${data.currPrice}</td>
        <td>${totalPrice}</td>
        <td>{data.transactionDate}</td>
      </tr>
    );
  };

  return (
    <>
      <div className="porfolio-container">
        <div className="portfolio-tab-header">
          <label>{data.mfFundHouse}</label>
          <label
            onClick={() => {
              clickExtendBtnHandler();
            }}
          >
            {!isExtended && <AiOutlineArrowDown />}
          </label>
          <label
            onClick={() => {
              clickExtendBtnHandler();
            }}
          >
            {isExtended && <AiOutlineArrowUp />}
          </label>
        </div>
        {isExtended && (
          <div className="portfolio-tab-section">
            <table onClick={onClikeHandler.bind(this, data)}>
              <thead>
                <tr>
                  <td>Fund House</td>
                  <td>Fund Name</td>
                  <td>Units</td>
                  <td>Price / Unit</td>
                  <td>Total Price</td>
                  <td>Transaction Date</td>
                </tr>
              </thead>
              <tbody>{generatedRows()}</tbody>
            </table>
            <hr></hr>
            {/* <div className='portfolio-tab-container'>
                <label>Revenue</label>
                <label>{`$${data.revenue}`}</label>
            </div>
            <div className='portfolio-tab-container'>
                <label>Loss</label>
                <label>{`$${data.loss}`}</label>
            </div> */}
          </div>
        )}
      </div>
    </>
  );
};

export default PortfolioTab;
