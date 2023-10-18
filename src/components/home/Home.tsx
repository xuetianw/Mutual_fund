import Search from "../Search/Search";
import TopBottomMF from "./components/TopBottomMF";
import classes from './Home.module.css'

const Home = () => {
  return (
    < div className={classes.home}>
      <Search />
      <TopBottomMF />
    </div>
  );
};

export default Home;
