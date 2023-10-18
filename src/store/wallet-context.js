import React from 'react';

const WalletContext = React.createContext({
  histories: [],
  walletBalance: 0,
  addAmount: (amount) => {},
  withdrawAmount: (amount) => {},
  buyMF: (amount) => {},
  sellMF: (amount) => {}
});

export default WalletContext;

