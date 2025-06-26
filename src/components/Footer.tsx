import React from "react";
import logoImg from "../assets/TP_logo_gradient.png";
import ThemeToggle from "./ThemeToggle";

const Footer: React.FC = () => {
  return (
    <>
      <footer className="landing-footer">
        <p>
          © {new Date().getFullYear()} OpenPlantProtocols. A community-powered
          resource for plant tissue culture.
        </p>
        <p>
          Created by{" "}
          <a
            href="https://www.youtube.com/@TechplantChannel"
            target="_blank"
            rel="noopener noreferrer"
          >
            Techplant
          </a>
          , your friendly plant YouTuber 🌱
        </p>
      </footer>
      <ThemeToggle />
    </>
  );
};

export default Footer;
