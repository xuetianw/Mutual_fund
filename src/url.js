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
// const url = "http://localhost";
// const url = "http://35.183.144.38";


// const authPort = '8005';
// const walletPort = '8460';
// const searchPort = '8461';
// const wishlistPort = '8412';
const porfolioPort = '8081'
//
// const gatewayPort = "8765";
// const gateway =`${url}:${gatewayPort}`
//
// module.exports = {
//    // authUrl: "http://localhost:8461/authenticateV2",
//   authUrl: `${url}:${authPort}/authenticateV2`,
//   registerURL: `${url}:${authPort}/registerV2`,
//   validateUrl: `${url}:${authPort}/validateTokenV2`,
//   validateOTP: `${url}:${authPort}/validateOTP`,
//   checkOTPvalidation: `${url}:${authPort}/isOTPVerified?email=`,
//   resendOTP: `${url}:${authPort}/sendOTP?email=`,
//   // deleteUser: `${url}:${authPort}/email?email=`,
//
//   createWalletURL: `${url}:${walletPort}/wallet/create`,
//   balanceURL: `${url}:${walletPort}/wallet/balance`,
//   historiesURL: `${url}:${walletPort}/wallet/history`,
//   transactURL: `${url}:${walletPort}/wallet/transact`,
//   currencyURL: `${url}:${walletPort}/wallet/rates/`,
//
//   searchURL: `${url}:${searchPort}/mf/get/all`,
//   getDetailsURL: `${url}:${searchPort}/mf/get/details`,
//   getTopFiveURL: `${url}:${searchPort}/mf/get/top5`,
//   getBottomFiveURL: `${url}:${searchPort}/mf/get/bottom5`,
//
//   addToWishlistURL: `${url}:${wishlistPort}/wishlist/add`,
//   removeFromWishlistURL: `${url}:${wishlistPort}/wishlist/remove?wishlistId=`,
//   wishlistExistURL: `${url}:${wishlistPort}/wishlist/exists?customerId=`,
//   wishlistCustomerURL: `${url}:${wishlistPort}/wishlist/getbycustomerid?customerId=`,
//   wishListSchemaURL: `${url}:${wishlistPort}/wishlist/exists?mfSchemaId=`,
//
//   portfoliosURL: `${url}:${porfolioPort}/portfolios/customers/`,
//   buyMFURL: `${url}:${porfolioPort}/muf/purchase_muf`,
//   withdrawMFURL: `${url}:${porfolioPort}/muf/withdraw_muf`,
//   mfHistoryURL: `${url}:${porfolioPort}/muf/getCustomerMFHistory`,
// };

// const url = "http://localhost";
const url = "http://35.183.238.7";

const gatewayPort = "8765";
const gateway =`${url}:${gatewayPort}`
const mfservice = 'mutualfund'

module.exports = {
  // authUrl: "http://localhost:8461/authenticateV2",
  authUrl: `${gateway}/auth/authenticateV2`,
  registerURL: `${gateway}/auth/registerV2`,
  validateUrl: `${gateway}/auth/validateTokenV2`,
  validateOTP: `${gateway}/auth/validateOTP`,
  checkOTPvalidation: `${gateway}/auth/isOTPVerified?email=`,
  resendOTP: `${gateway}/auth/sendOTP?email=`,
  deleteUser: `${gateway}/auth/email?email=`,

  createWalletURL: `${gateway}/wallet/wallet/create`,
  balanceURL: `${gateway}/wallet/wallet/balance`,
  historiesURL: `${gateway}/wallet/wallet/history`,
  transactURL: `${gateway}/wallet/wallet/transact`,
  currencyURL: `${gateway}/wallet/wallet/rates/`,

  searchURL: `${gateway}/mutualfund/mf/get/all`,
  getDetailsURL: `${gateway}/mutualfund/mf/get/details`,
  // getTopFiveURL: `${url}:${searchPort}/mf/get/top5`,
  getTopFiveURL: `${gateway}/mutualfund/mf/get/top5`,
  getBottomFiveURL: `${gateway}/mutualfund/mf/get/bottom5`,
  // getBottomFiveURL: `http://localhost:8765/mutualfund/mf/get/top5`,

  addToWishlistURL: `${gateway}/wishlist/wishlist/add`,
  removeFromWishlistURL: `${gateway}/wishlist/wishlist/remove?wishlistId=`,
  wishlistExistURL: `${gateway}/wishlist/wishlist/exists?customerId=`,
  wishlistCustomerURL: `${gateway}/wishlist/wishlist/getbycustomerid?customerId=`,
  wishListSchemaURL: `${gateway}/wishlist/wishlist/exists?mfSchemaId=`,

  portfoliosURL: `${gateway}/portfolio/portfolios/customers/`,
  buyMFURL: `${gateway}/portfolio/muf/purchase_muf`,
  withdrawMFURL: `${gateway}/portfolio/muf/withdraw_muf`,
  mfHistoryURL: `${gateway}/portfolio/muf/getCustomerMFHistory`,
};
