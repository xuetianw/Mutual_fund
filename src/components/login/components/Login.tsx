import { useFormik } from "formik";
import * as yup from "yup";
import { Button, TextField } from "@mui/material";
import { useContext, useState } from "react";
import { redirect, useNavigate } from "react-router-dom";

import IconButton from "@mui/material/IconButton";
import InputAdornment from "@mui/material/InputAdornment";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";

import "../stylesheets/login.scss";
import axios from "axios";
import AuthContext from "../../../store/auth-context";
import Modal from "../../UI/Modal";
import ValidateOTP from "../../registration/components/ValidateOTP";
// TODO: ADD axios calls
// If data sent -> but password wrong -> response would be 200 ok but now
// repsonse data would be -> status: 200  someobject: "passwordwrong"
// change errorState to "password invalid", "email does not exsist", etc

const URL = require("../../../url.js");

export default function Login() {
  const [showPassword, setShowPassword] = useState(false);
  const [isOTPVarified, setIsOTPVarified] = useState(false);
  const [modelIsVisible, setModelIsVisible] = useState(false);

  const togglePassword = () => setShowPassword((show) => !show);
  const [serverRespError, setServerRespError] = useState("");
  const authCtx = useContext(AuthContext);

  const navigate = useNavigate();

  const SIZE = [300, 400, 500];
  type loginProps = {
    email: string;
    password: string;
  };

  const hideModelHandler = () => {
    setModelIsVisible(false);
  };

  const sendGetRequest = async (values: loginProps) => {
    try {
      const response = await fetch(
        URL.checkOTPvalidation + formik.values.email,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      const responseData = await response.json();
      console.log(responseData);
      if (responseData.message === "true") {
        setIsOTPVarified(true);
        setModelIsVisible(false);
      } else if(responseData.message === "false"){
        setIsOTPVarified(false);
        setModelIsVisible(true);
        return;
      }
    } catch (err) {
      console.error("Verification error: ", err);
    }

    try {
      setServerRespError("");
      const resp = await axios.post(URL.authUrl, {
        ...values,
      });
      let data = resp.data;
      console.log("Login response data: ", resp.data);
      sessionStorage.setItem("token", data?.token);
      const token = sessionStorage.getItem("token");
      if (resp?.data?.status_error) {
        setServerRespError(resp.data.status_error);
      }

      const idResp = await axios.post(URL.validateUrl, {
        token,
      });
      console.log("Id response data: ", idResp.data);
      sessionStorage.setItem("id", idResp.data?.id);
      if (idResp?.data?.status_error) {
        setServerRespError(idResp.data.status_error);
      }
    } catch (err) {
      // Handle Error Here
      console.error("login error: ", err);
      alert("sorry, you could not login, please try again later");
    }

    // try{
    //   const token = sessionStorage.getItem("token");
    //   const walletResp = await axios.post(URL.createWalletURL, {
    //     token: token,
    //   });
    //   console.log("Wallet response: ", walletResp.data);
    // } catch(err) {
    //   console.error(err);
    // }

    const token = sessionStorage.getItem("token");
    const id = sessionStorage.getItem("id");
    authCtx.login(token);

    console.log("test login set token in sessionStorage: ", token); // haven't rerender
    console.log("test login set token in auth context: ", authCtx.token);
    console.log("test login set id: ", id);

    navigate("/dashboard");

    // return redirect('/dashboard')
  };

  const validationSchema = yup.object({
    email: yup
      .string()
      .email("Enter a valid email")
      .required("Email is required"),
    password: yup.string().required("Enter your password"),
  });

  const formik = useFormik({
    initialValues: {
      email: "",
      password: "",
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      sendGetRequest(values);
      // alert(JSON.stringify(values, null, 2));
    },
  });

  return (
    <section id="container-login">
      {modelIsVisible && (
        <ValidateOTP
          email={formik.values.email}
          password={formik.values.password}
          onClose={hideModelHandler}
        />
      )}
      <form onSubmit={formik.handleSubmit}>
        {serverRespError && (
          <div className="container-server-error">
            {serverRespError}. Try Again
          </div>
        )}
        <h2>Sign in to your account</h2>
        <TextField
          InputLabelProps={{ shrink: true }}
          margin="normal"
          id="email"
          name="email"
          label="Email"
          size="small"
          value={formik.values.email}
          sx={{
            width: SIZE,
          }}
          onChange={(e) => {
            formik.handleChange(e);
            formik.setFieldTouched("email", false, false);
          }}
          error={formik.touched.email && Boolean(formik.errors.email)}
          helperText={formik.touched.email && formik.errors.email}
        />
        <TextField
          InputLabelProps={{ shrink: true }}
          margin="normal"
          id="password"
          name="password"
          label="Password"
          size="small"
          type={showPassword ? "text" : "password"}
          value={formik.values.password}
          sx={{
            width: SIZE,
          }}
          onChange={(e) => {
            formik.handleChange(e);
            formik.setFieldTouched("password", false, false);
          }}
          error={formik.touched.password && Boolean(formik.errors.password)}
          helperText={formik.touched.password && formik.errors.password}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton
                  aria-label="toggle password visibility"
                  onClick={togglePassword}
                  edge="end"
                >
                  {showPassword ? <VisibilityOff /> : <Visibility />}
                </IconButton>
              </InputAdornment>
            ),
          }}
        />

        <Button
          color="primary"
          variant="contained"
          type="submit"
          sx={{
            width: SIZE,
          }}
        >
          Login
        </Button>
      </form>
    </section>
  );
}
