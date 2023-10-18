import React from 'react';

const PortfolioContext = React.createContext({
  portfolio: [],
  sellMF: (amount) => {}
})

export default PortfolioContext;