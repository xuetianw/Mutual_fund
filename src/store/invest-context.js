import React from "react";

const InvestContext =  React.createContext({
  wishLists: [],
  portfolios: [],
  addWishlist: () => {},
  withdrawWishList: () => {},
  addPortfolio: () => {}
})

export default InvestContext;