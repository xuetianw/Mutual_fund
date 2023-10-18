import { RouterProvider, createBrowserRouter } from "react-router-dom";
import HomePage from "./pages/HomePage";
import RootLayout from "./pages/RootLayout";
import DashboardPage from "./pages/DashboardPage";
import PortfolioPage from "./pages/PortfolioPage";
import WishlistPage from "./pages/WishlistPage";
import WalletDetailsPage from "./pages/WalletDetailsPage";
import MFDetails from "./components/mf-details/components/MFDetails";
import TopBottomMF from "./components/home/components/TopBottomMF";
import Login from "./components/login/components/Login";
import Registration from "./components/registration/components/Registration";
import { checkAuthLoader, tokenLoader } from "./util/auth";
import InvestPage from "./pages/InvestPage";
import MFHistoryPage from "./pages/MFHistoryPage";

const App: React.FC = () => {
  const router = createBrowserRouter([
    {
      path: "/",
      element: <RootLayout />,
      id: "root",
      // loader: tokenLoader,
      children: [
        { index: true, element: <HomePage /> },
        {
          path: "dashboard",
          element: <DashboardPage />,
          loader: checkAuthLoader,
          children: [
            { index: true, element: <PortfolioPage /> },
            { path: "wishlist", element: <WishlistPage /> },
          ],
        },
        {
          path: "/dashboard/walletDetails",
          element: <WalletDetailsPage />,
          loader: checkAuthLoader,
        },
        { path: "/MFDetails/:item", element: <MFDetails/> },
        { path: "/topbottom", element: <TopBottomMF /> },
        { path: "/login", element: <Login /> },
        { path: "/registration", element: <Registration /> },
        { path: "/invest", element: <InvestPage /> },
        { path: "/mfHistory", element: <MFHistoryPage /> },
        { path: "/logout" },
      ],
    },
  ]);
  return <RouterProvider router={router} />;
};

export default App;
