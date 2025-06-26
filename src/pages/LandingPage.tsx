import React from "react";
import TopNavBar from "../components/TopNavBar";
import Footer from "../components/Footer";

const LandingPage: React.FC = () => {
  return (
    <div className="landing-root">
      <TopNavBar></TopNavBar>

      <main className="landing-main">
        <h1 className="headline">Open Plant Protocols</h1>
        <p className="subhead">
          A modern toolkit for calculating, documenting, and sharing plant
          tissue culture methods. Built for scientists, hobbyists, and labs of
          all sizes.
        </p>
        <div className="cta">
          <a href="/calculator" className="cta-button">
            Try the PGR Calculator
          </a>
        </div>
        <p className="coming-soon">
          🔬 Protocol sharing & community submissions coming soon.
        </p>
      </main>
      <Footer></Footer>
    </div>
  );
};

export default LandingPage;
