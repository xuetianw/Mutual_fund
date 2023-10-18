import Button from "@mui/material/Button";
import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "../../../store/auth-context";

export default function Logout() {
  const navigate = useNavigate();
    const authCtx = useContext(AuthContext);


  let toggleLogout = () => {
    if (sessionStorage.getItem("token") !== null) {
      sessionStorage.removeItem("token");
    }

    authCtx.logout();
    // return redirect("/");    --> not work
    console.log("Logout triggered")
    navigate("/");
  };

  return (
    <Button
      onClick={toggleLogout}
      color="primary"
      variant="contained"
      type="submit"
      // sx={{
      //     width: [110,115,120],
      // }}
    >
      Logout
    </Button>
  );
}
