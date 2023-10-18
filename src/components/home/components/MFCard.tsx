import "../stylesheets/MFCard.css";
import { MF } from "../../../types/MFType";

const MFCard: React.FC<MF> = (props: MF) => {
  const growthFieldAttr =
    props.growth < 0 ? "negative-growth" : "positive-growth";
  return (
    <div className="mf-card-container">
      <div className="inner-card">
        <label>{props.symbol}</label>
        <label>{props.name}</label>
        <label>{props.category}</label>
      </div>
      <div className="inner-card">
        <label>${props.price}</label>
        <label id={growthFieldAttr}>{props.growth}%</label>
      </div>
    </div>
  );
};

export default MFCard;
