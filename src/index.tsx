import React from "react";
import ReactDOM from "react-dom/client";

import "./index.css";
import App from "./App";
import WalletProvider from "./store/WalletProvider";
import { LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import AuthContextProvider from "./store/AuthContextProvider";
import PortfolioContextProvider from "./store/PortfolioContextProvider";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <AuthContextProvider>
    <WalletProvider>
      <PortfolioContextProvider>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <App />
        </LocalizationProvider>
      </PortfolioContextProvider>
    </WalletProvider>
  </AuthContextProvider>
);
