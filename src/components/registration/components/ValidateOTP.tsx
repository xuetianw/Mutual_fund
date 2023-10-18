import { useState } from "react";
import classes from "../stylesheets/ValidateOTP.module.css";
import { useNavigate } from "react-router-dom";
import Modal from "../../UI/Modal";

const URL = require("../../../url");

const ValidateOTP = ({ email, password, onClose }) => {
  const [otpInput, setOtpInput] = useState("");
  const navigate = useNavigate();

  const otpInputHandler = (event) => {
    setOtpInput(event.target.value);
  };
  const reshendHandler = async (event) => {
    const response = await fetch(URL.resendOTP + email, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    });
    const responseData = await response.json();
    console.log("response for resend OTP", responseData);
  };


  const otpHandler = async (event) => {
    event.preventDefault();
    const otpValidationDate = {
      otp: otpInput,
      email: email,
      password: password,
    };

    console.log("otpValidationDate: ", otpValidationDate);
    const response = await fetch(URL.validateOTP, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(otpValidationDate),
    });

    if (!response.ok) {
      alert("otp didn't match, please try again");
      setOtpInput("");
      return;
    }

    const responseDate = await response.json();
    console.log("responseDate: ", responseDate);
    alert("You have successfully verified your email");
    onClose();
    navigate("/login");
  };

  return (
    <Modal onClose={onClose}>
      <form className={classes.notice} onSubmit={otpHandler}>
        <h2>Verify Your Account</h2>
        <label htmlFor="otp">
          In order to start using MFUOnline, you need to verify your email by
          enter the OTP we have sent to {email}
        </label>
        <input
          type="text"
          name="otp"
          value={otpInput}
          onChange={otpInputHandler}
        ></input>
        <button
          type="button"
          className={classes.resend}
          onClick={reshendHandler}
        >Resend OTP</button>
        <button type="submit" className={classes.verifyEmail} >Verify email</button>
      </form>
    </Modal>
  );
};

export default ValidateOTP;
