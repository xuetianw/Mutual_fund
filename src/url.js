// const url = "http://54.145.166.44";
// const port = "8080";
//
// module.exports = {
//   authUrl: `${url}:${port}/auth/authenticateV2`,
//   registerURL: `${url}:${port}/auth/registerV2`,
//   validateUrl: `${url}:${port}/auth/validateTokenV2`,
//   validateOTP: `${url}:${port}/auth/validateOTP`,
//   checkOTPvalidation: `${url}:${port}/auth/isOTPVerified?email=`,
//   resendOTP: `${url}:${port}/auth/sendOTP?email=`,
//
//   createWalletURL: `${url}:${port}/wallet/wallet/create`,
//   balanceURL: `${url}:${port}/wallet/wallet/balance`,
//   historiesURL: `${url}:${port}/wallet/wallet/history`,
//   transactURL: `${url}:${port}/wallet/wallet/transact`,
//   currencyURL: `${url}:${port}/wallet/wallet/rates/`,
//
//   searchURL: `${url}:${port}/ms/mf/get/all`,
//   getDetailsURL: `${url}:${port}/ms/mf/get/details`,
//   getTopFiveURL: `${url}:${port}/ms/mf/get/top5`,
//   getBottomFiveURL: `${url}:${port}/ms/mf/get/bottom5`,
//
//   addToWishlistURL: `${url}:${port}/wishlist/wishlist/add`,
//   removeFromWishlistURL: `${url}:${port}/wishlist/wishlist/remove?wishlistId=`,
//   wishlistExistURL: `${url}:${port}/wishlist/wishlist/exists?customerId=`,
//   wishlistCustomerURL: `${url}:${port}/wishlist/wishlist/getbycustomerid?customerId=`,
//   wishListSchemaURL: `${url}:${port}/wishlist/wishlist/exists?mfSchemaId=`,
//
//   portfoliosURL: `${url}:${port}/portfolio/portfolios/customers/`,
//   buyMFURL: `${url}:${port}/portfolio/muf/purchase_muf`,
//   withdrawMFURL: `${url}:${port}/portfolio/muf/withdraw_muf`,
//   mfHistoryURL: `${url}:${port}/portfolio/muf/getCustomerMFHistory`,
// };
//
//



// Note: below is test in local:
const url = "http://localhost";

const authPort = '8005';
const walletPort = '8460';
const searchPort = '8461';
const wishlistPort = '8412';
const porfolioPort = '8081'

module.exports = {
   // authUrl: "http://localhost:8461/authenticateV2",
  authUrl: `${url}:${authPort}/authenticateV2`,
  registerURL: `${url}:${authPort}/registerV2`,
  validateUrl: `${url}:${authPort}/validateTokenV2`,
  validateOTP: `${url}:${authPort}/validateOTP`,
  checkOTPvalidation: `${url}:${authPort}/isOTPVerified?email=`,
  resendOTP: `${url}:${authPort}/sendOTP?email=`,

  createWalletURL: `${url}:${walletPort}/wallet/create`,
  balanceURL: `${url}:${walletPort}/wallet/balance`,
  historiesURL: `${url}:${walletPort}/wallet/history`,
  transactURL: `${url}:${walletPort}/wallet/transact`,
  currencyURL: `${url}:${walletPort}/wallet/rates/`,

  searchURL: `${url}:${searchPort}/mf/get/all`,
  getDetailsURL: `${url}:${searchPort}/mf/get/details`,
  getTopFiveURL: `${url}:${searchPort}/mf/get/top5`,
  getBottomFiveURL: `${url}:${searchPort}/mf/get/bottom5`,

  addToWishlistURL: `${url}:${wishlistPort}/wishlist/add`,
  removeFromWishlistURL: `${url}:${wishlistPort}/wishlist/remove?wishlistId=`,
  wishlistExistURL: `${url}:${wishlistPort}/wishlist/exists?customerId=`,
  wishlistCustomerURL: `${url}:${wishlistPort}/wishlist/getbycustomerid?customerId=`,
  wishListSchemaURL: `${url}:${wishlistPort}/wishlist/exists?mfSchemaId=`,

  portfoliosURL: `${url}:${porfolioPort}/portfolios/customers/`,
  buyMFURL: `${url}:${porfolioPort}/muf/purchase_muf`,
  withdrawMFURL: `${url}:${porfolioPort}/muf/withdraw_muf`,
  mfHistoryURL: `${url}:${porfolioPort}/muf/getCustomerMFHistory`,
};
