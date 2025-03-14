import React from "react";
            import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
            import Dashboard from "./Dashboard";
            import Settings from "./Settings";
            import ActuatorsPage from "./ActuatorsPage";

            function App() {
              return (
                <Router>
                  <div style={{ display: "flex" }}>
                    <nav style={{ width: "200px", padding: "10px", background: "#f0f0f0" }}>
                      <ul style={{ listStyleType: "none", padding: 0 }}>
                        <li>
                          <Link to="/">Dashboard</Link>
                        </li>
                        <li>
                          <Link to="/settings">Settings</Link>
                        </li>
                        <li>
                          <Link to="/actuators">ActuatorsPage</Link>
                        </li>
                      </ul>
                    </nav>
                    <div style={{ flex: 1, padding: "10px" }}>
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