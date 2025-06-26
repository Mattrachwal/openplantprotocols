import React from "react";
import PgrCalculator from "../components/PgrCalculator/PgrCalculator";
import TopNavBar from "../components/TopNavBar";
import Footer from "../components/Footer";

const PgrCalculatorPage: React.FC = () => {
  return (
    <div className="landing-root">
      <TopNavBar></TopNavBar>
      <main className="landing-main">
        <PgrCalculator />
      </main>
      <Footer></Footer>
    </div>
  );
};

export default PgrCalculatorPage;
