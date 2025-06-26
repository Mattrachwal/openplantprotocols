import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import App from "./App";
import "./index.css";
import "./fonts.css";

const container = document.getElementById("root");
if (container) {
  const root = ReactDOM.createRoot(container); // createRoot(container!) if you use TypeScript
  root.render(
    <React.StrictMode>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </React.StrictMode>
  );
}
