// frontend/src/ScenariosPage.js
import React, { useEffect, useState } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import './common.css';


const ScenariosPage = () => {
  const [scenarios, setScenarios] = useState([]);

  useEffect(() => {
    fetchScenarios();
  }, []);

  const fetchScenarios = async () => {
    try {
      const response = await fetch(`/scenario/all`);
      const data = await response.json();
      setScenarios(data);
    } catch (error) {
      console.error("Error fetching scenarios:", error);
    }
  };

  const switchScenarioState = async (id) => {
    try {
      await fetch(`/scenario/switchstate/${id}`, {
        method: "POST",
      });
      fetchScenarios(); // Refresh the list after switching state
    } catch (error) {
      console.error("Error switching scenario state:", error);
    }
  };


    const handleDelete = async (id) => {
      if (window.confirm('Are you sure you want to delete this scenario?')) {
        try {
          await fetch(`/scenario/${id}`, { method: 'DELETE' });
          setScenarios(scenarios.filter(scenario => scenario.id !== id));
        } catch (error) {
          console.error('Error deleting scenario:', error);
        }
      }
    };

  return (
    <div className="container mt-4">
      <h1 className="mb-4">Scenarios</h1>
      <ul className="list-group mb-4">
        {scenarios.map((scenario) => (
          <li key={scenario.id} className="list-group-item d-flex justify-content-between align-items-center">
            <div>
              <h5>{scenario.name}</h5>
              <p>{scenario.active ? "Active" : "Inactive"}</p>
            </div>
            <div>
              <button className="btn btn-secondary mr-2" onClick={() => switchScenarioState(scenario.id)}>
                Switch State
              </button>
              <button className="btn btn-danger" onClick={() => handleDelete(scenario.id)}>
                Delete
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ScenariosPage;