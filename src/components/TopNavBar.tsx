import React from "react";
import logoImg from "../assets/TP_logo_gradient.png";
import { Link } from "react-router-dom";

const TopNavBar: React.FC = () => {
  return (
    <nav className="navbar">
      <div className="navbar-logo">
        <img src={logoImg} alt="Logo" />
        <span className="navbar-title">OpenPlantProtocols</span>
      </div>
      <div className="navbar-links">
        <Link to="/">Home</Link>
        <Link to="/calculator">Calculator</Link>
      </div>
    </nav>
  );
};

export default TopNavBar;
