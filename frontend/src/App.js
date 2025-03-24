import React from "react";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import Dashboard from "./Dashboard";
import Settings from "./Settings";
import ActuatorsPage from "./ActuatorsPage";
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

function App() {
  return (
    <Router>
      <div className="d-flex app-container">
        <nav className="bg-light border-right" style={{ width: "200px", padding: "10px" }}>
          <ul className="nav flex-column">
            <li className="nav-item">
              <Link className="nav-link" to="/">Dashboard</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/settings">Settings</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/actuators">ActuatorsPage</Link>
            </li>
          </ul>
        </nav>
        <div className="flex-grow-1 p-3">
          <Routes>
            <Route exact path="/" element={<Dashboard />} />
            <Route path="/settings" element={<Settings />} />
            <Route path="/actuators" element={<ActuatorsPage />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;