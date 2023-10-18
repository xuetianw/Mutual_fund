import { useFormik } from "formik";
import * as yup from "yup";
import { Button, TextField } from "@mui/material";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../stylesheets/registration.scss";

import IconButton from "@mui/material/IconButton";
import InputAdornment from "@mui/material/InputAdornment";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import axios from "axios";
import Modal from "../../UI/Modal";
import ValidateOTP from "./ValidateOTP";

const URL = require("../../../url");

export default function Registration() {
  const [showPassword, setShowPassword] = useState(false);
  const [serverRespError, setServerRespError] = useState("");
  const navigate = useNavigate();
  const [modelIsVisible, setModelIsVisible] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);
  const [isEmailSend, setIsEmailSend] = useState(false);
  const [message, setMessage] = useState("");
  // const [otpInput, setOtpInput] = useState("");

  const hideModelHandler = () => {
    setModelIsVisible(false);
  };

  const emailSendHandler = () => {
    setIsEmailSend(true);
  }

  // const showModelHandler = () => {
  //   setModelIsVisible(true);
  // };

  const togglePassword = () => setShowPassword((show) => !show);
  const SIZE = [300, 350, 380];
  const validationSchema = yup.object({
    first_name: yup.string().required("First Name is required"),
    last_name: yup.string().required("Last Name is required"),
    dob: yup.date().required("Date is required"),
    email: yup
      .string()
      .email("Enter a valid email")
      .required("Email is required"),
    password: yup
      .string()
      .matches(/[0-9]/, "Password requires a number")
      .matches(/[a-z]/, "Password requires a lowercase letter")
      .matches(/[A-Z]/, "Password requires an uppercase letter")
      .matches(/[^\w]/, "Password requires a symbol")
      .required("Enter your password"),
    confirm: yup
      .string()
      .oneOf([yup.ref("password")], 'Must match "password" field value')
      .required("Reenter your password"),
  });

  type inputProps = {
    first_name: string;
    last_name: string;
    dob: string;
    email: string;
    password: string;
    confirm: string;
  };
  const sendPostRequest = async (values: inputProps) => {
    let reMapNames = {
      fname: values.first_name,
      lname: values.last_name,
      date_of_birth: values.dob,
      email: values.email,
      pwd: values.password,
    };
    // alert(JSON.stringify(reMapNames, null, 2));
    // localhost:8080//register
    // `http://localhost:${PORT}/register`
    console.log("registor URL: ", URL.registerURL);
    try {
      setServerRespError("");
      const resp = await axios.post(URL.registerURL, reMapNames);
      console.log("register response data: ", resp.data);

      if (resp.data.message === "User email already exist") {
        alert(resp.data.message);
        return;
      }

      

      if (resp?.data?.status_error) {
        setServerRespError(resp.data.status_error);
        setIsSuccess(false);
        setMessage("Could not register, please try again");
      } else {
        setIsSuccess(true);
        setMessage("Successfully registered");
        if (
          resp.data.message.includes("an email was not sent to you successfully")
        ) {
          // alert(resp.data.message)
          console.log("go inside the not send email")
          setIsEmailSend(false);
          setMessage("Successfully registered. Could not send OTP to your email due to: Mail server connection failed");
        } else {
          setIsEmailSend(true);
        }
      }
    } catch (err) {
      // Handle Error Here
      console.log("Error in register: ");
      // alert("Error in catch....");
      console.error(err);
      setIsSuccess(false);
      setMessage("Could not register, please try again");
    }
    setModelIsVisible(true);
    console.log("message is: ", message);
    console.log("email is send: ", isEmailSend);
  };

  const buttonHandler = () => {
    hideModelHandler();
    if (isSuccess) {
      navigate("/login");
    }
  };

  const formik = useFormik({
    initialValues: {
      first_name: "",
      last_name: "",
      dob: "",
      email: "",
      password: "",
      confirm: "",
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      sendPostRequest(values);
    },
  });

  return (
    <>
      {modelIsVisible && (
        <>
          {isSuccess && !isEmailSend && (
            <Modal onClose={hideModelHandler}>
              <p>{message}</p>
              <button type="button" onClick={emailSendHandler}>
                ok
              </button>
            </Modal>
          )}
          {isSuccess && isEmailSend && (
            <ValidateOTP
              onClose={hideModelHandler}
              email={formik.values.email}
              password={formik.values.password}
            />
          )}
          {!isSuccess && (
            <Modal onClose={hideModelHandler}>
              <p>{message}</p>
              <button type="button" onClick={buttonHandler}>
                ok
              </button>
            </Modal>
          )}
        </>
      )}
      <section id="container-registration">
        <form onSubmit={formik.handleSubmit}>
          {serverRespError && (
            <div className="container-server-error">
              {serverRespError}. Try Again
            </div>
          )}
          <h2>Create your account</h2>
          <div id="container-name">
            <TextField
              InputLabelProps={{ shrink: true }}
              margin="normal"
              id="first_name"
              name="first_name"
              label="First Name"
              size="small"
              value={formik.values.first_name}
              sx={{
                width: SIZE,
              }}
              onChange={(e) => {
                formik.handleChange(e);
                formik.setFieldTouched("first_name", false, false);
              }}
              error={
                formik.touched.first_name && Boolean(formik.errors.first_name)
              }
              helperText={formik.touched.first_name && formik.errors.first_name}
            />
            <TextField
              InputLabelProps={{ shrink: true }}
              margin="normal"
              id="last_name"
              name="last_name"
              label="Last Name"
              size="small"
              value={formik.values.last_name}
              sx={{
                width: SIZE,
              }}
              onChange={(e) => {
                formik.handleChange(e);
                formik.setFieldTouched("last_name", false, false);
              }}
              error={
                formik.touched.last_name && Boolean(formik.errors.last_name)
              }
              helperText={formik.touched.last_name && formik.errors.last_name}
            />
          </div>
          <div id="container-dob-email">
            <DatePicker
              label="Date of Birth"
              slotProps={{
                textField: {
                  InputLabelProps: { shrink: true },
                  name: "dob",
                  id: "dob",
                  error: Boolean(formik.errors.dob),
                  helperText: formik.touched.dob && formik.errors.dob,
                },
              }}
              sx={{
                width: SIZE,
              }}
              onChange={(value) => {
                // Get only the YYYY-MM-DD
                formik.setFieldValue("dob", JSON.stringify(value).slice(1, 11));
              }}
            />
            <TextField
              InputLabelProps={{ shrink: true }}
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
          </div>

          <div id="container-password">
            <section className="passsword-req">
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
                error={
                  formik.touched.password && Boolean(formik.errors.password)
                }
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
              <div className="pass-desc">
                <div>Your password must contain:</div>
                <ul>
                  <li>'Password requires a number'</li>
                  <li>'Password requires a lowercase letter'</li>
                  <li>'Password requires an uppercase letter'</li>
                  <li>'Password requires a symbol'</li>
                </ul>
              </div>
            </section>
            {/* Confirm password */}
            <TextField
              InputLabelProps={{ shrink: true }}
              margin="normal"
              id="confirm"
              name="confirm"
              label="Confirm Password"
              size="small"
              type={showPassword ? "text" : "password"}
              value={formik.values.confirm}
              sx={{
                width: SIZE,
              }}
              onChange={(e) => {
                formik.handleChange(e);
                formik.setFieldTouched("password", false, false);
              }}
              error={formik.touched.confirm && Boolean(formik.errors.confirm)}
              helperText={formik.touched.confirm && formik.errors.confirm}
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
          </div>

          <Button
            id="submit-button"
            color="primary"
            variant="contained"
            type="submit"
            sx={{
              width: SIZE,
            }}
          >
            Register
          </Button>
        </form>
      </section>
    </>
  );
}
