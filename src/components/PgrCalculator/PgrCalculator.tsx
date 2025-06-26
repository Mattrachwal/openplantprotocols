import React, { useState } from "react";

type PGRRow = {
  id: number;
  name: string;
  mw: number;
  desiredConc: string;
  unit: "µM" | "mg/L";
  stockConc: string;
  unitStock: "mg/mL" | "mg/L" | "µM";
};

const defaultPGRs = [
  { name: "BAP", mw: 225.25 },
  { name: "NAA", mw: 186.21 },
  { name: "IBA", mw: 203.24 },
  { name: "TDZ", mw: 220.2 },
  { name: "GA3", mw: 346.38 },
  { name: "Kinetin", mw: 215.23 },
];

const PgrCalculator: React.FC = () => {
  const [finalVolume, setFinalVolume] = useState("250");
  const [totalVolumeUl, setTotalVolumeUl] = useState(0);
  const [rows, setRows] = useState<PGRRow[]>([
    {
      id: 1,
      name: "BAP",
      mw: 225.25,
      desiredConc: "",
      unit: "µM",
      stockConc: "",
      unitStock: "mg/mL",
    },
  ]);
  const [results, setResults] = useState<Record<number, string>>({});

  const handleChange = (id: number, key: keyof PGRRow, value: string) => {
    setRows((prev) =>
      prev.map((row) => (row.id === id ? { ...row, [key]: value } : row))
    );
  };

  const handleAddRow = () => {
    const id = Date.now();
    setRows((prev) => [
      ...prev,
      {
        id,
        name: "Custom",
        mw: 0,
        desiredConc: "",
        unit: "µM",
        stockConc: "",
        unitStock: "mg/mL",
      },
    ]);
  };

  const handleCalculate = () => {
    const volume = parseFloat(finalVolume) / 1000; // mL to L
    if (isNaN(volume) || volume <= 0) return;

    const newResults: Record<number, string> = {};
    let totalUl = 0;

    rows.forEach((row) => {
      const desired = parseFloat(row.desiredConc);
      const stock = parseFloat(row.stockConc);
      const mw = parseFloat(String(row.mw));

      if (isNaN(desired) || isNaN(stock) || isNaN(mw) || stock <= 0) {
        newResults[row.id] = "⚠ Invalid input";
        return;
      }

      // Convert desired conc to mg/L
      const desiredMgL = row.unit === "µM" ? (desired * mw) / 1000 : desired;

      // Normalize stock conc to mg/mL
      let normalizedStock = stock;
      if (row.unitStock === "mg/L") {
        normalizedStock = stock / 1000;
      } else if (row.unitStock === "µM") {
        normalizedStock = (stock * mw) / 1000;
      }

      const volumeToAddMl = (desiredMgL * volume) / normalizedStock;
      const volumeToAddUl = volumeToAddMl * 1000;

      totalUl += volumeToAddUl;

      newResults[row.id] = `${volumeToAddUl.toFixed(
        2
      )} µL (${volumeToAddMl.toFixed(4)} mL)`;
    });

    setResults(newResults);
    setTotalVolumeUl(totalUl);
  };

  const exportCsv = () => {
    const header = [
      "PGR",
      "MW",
      "Desired Conc",
      "Unit",
      "Stock Conc",
      "Stock Unit",
      "Volume to Add",
    ];
    const lines = rows.map((row) => [
      row.name,
      row.mw,
      row.desiredConc,
      row.unit,
      row.stockConc,
      row.unitStock,
      results[row.id] || "--",
    ]);

    const csvContent = [header, ...lines]
      .map((line) => line.join(","))
      .join("\n");

    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const url = URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", "pgr_calculation.csv");
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  return (
    <div className="calc-outer">
      <h1 className="calc-title">PGR Calculator</h1>

      <div className="volume-row">
        <label>
          Final Volume to Prepare (mL):
          <input
            type="number"
            value={finalVolume}
            onChange={(e) => setFinalVolume(e.target.value)}
          />
        </label>
      </div>

      <div className="table-container">
        <div className="table-header">
          <span>PGR</span>
          <span>MW</span>
          <span>Desired Conc</span>
          <span>Unit</span>
          <span>Stock Conc</span>
          <span>Stock Unit</span>
          <span>Volume to Add</span>
          <span></span>
        </div>

        {rows.map((row) => (
          <div className="table-row" key={row.id}>
            <select
              value={row.name}
              onChange={(e) => {
                const selected = defaultPGRs.find(
                  (p) => p.name === e.target.value
                );
                handleChange(row.id, "name", e.target.value);
                handleChange(row.id, "mw", selected?.mw ?? 0);
              }}
            >
              {defaultPGRs.map((p) => (
                <option key={p.name} value={p.name}>
                  {p.name}
                </option>
              ))}
              <option value="Custom">Custom</option>
            </select>

            <input
              type="number"
              value={row.mw}
              onChange={(e) => handleChange(row.id, "mw", e.target.value)}
              placeholder="MW"
            />

            <input
              type="number"
              value={row.desiredConc}
              onChange={(e) =>
                handleChange(row.id, "desiredConc", e.target.value)
              }
              placeholder="Desired"
            />

            <select
              value={row.unit}
              onChange={(e) =>
                handleChange(row.id, "unit", e.target.value as "µM" | "mg/L")
              }
            >
              <option value="µM">µM</option>
              <option value="mg/L">mg/L</option>
            </select>

            <input
              type="number"
              value={row.stockConc}
              onChange={(e) =>
                handleChange(row.id, "stockConc", e.target.value)
              }
              placeholder="Stock"
            />

            <select
              value={row.unitStock}
              onChange={(e) =>
                handleChange(
                  row.id,
                  "unitStock",
                  e.target.value as "mg/mL" | "mg/L" | "µM"
                )
              }
            >
              <option value="mg/mL">mg/mL</option>
              <option value="mg/L">mg/L</option>
              <option value="µM">µM</option>
            </select>

            <div className="result-cell">{results[row.id] || "--"}</div>

            <button
              className="delete-btn"
              onClick={() => setRows(rows.filter((r) => r.id !== row.id))}
            >
              ✕
            </button>
          </div>
        ))}
      </div>

      <div className="total-summary">
        <strong>Total Volume to Add:</strong> {totalVolumeUl.toFixed(2)} µL
      </div>

      <div className="actions">
        <button onClick={handleAddRow}>+ Add Another PGR</button>
        <button onClick={handleCalculate}>🧮 Calculate</button>
        <button onClick={() => exportCsv()}>⬇ Export CSV</button>
        <button onClick={() => window.print()}>🖨 Print</button>
      </div>
    </div>
  );
};

export default PgrCalculator;
