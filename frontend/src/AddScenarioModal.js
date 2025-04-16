import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const AddScenarioModal = ({ onClose, onScenarioAdded }) => {
  const [actuators, setActuators] = useState([]);
  const [sensors, setSensors] = useState([]);
  const [scenarioName, setScenarioName] = useState('');
  const [selectedActuators, setSelectedActuators] = useState([]);
  const [sensorComparators, setSensorComparators] = useState([]);
  const [selectedSensor, setSelectedSensor] = useState('');
  const [selectedComparator, setSelectedComparator] = useState('=');
  const [value, setValue] = useState('');
  const [conditionType, setConditionType] = useState('ALL'); // Default conditionType

  useEffect(() => {
    fetchActuators();
    fetchSensors();
  }, []);

  const fetchActuators = async () => {
    try {
      const response = await fetch(`/actuator/all`);
      const data = await response.json();
      setActuators(data);
    } catch (error) {
      console.error("Error fetching actuators:", error);
    }
  };

  const fetchSensors = async () => {
    try {
      const response = await fetch(`/sensor/all`);
      const data = await response.json();
      setSensors(data);
    } catch (error) {
      console.error("Error fetching sensors:", error);
    }
  };

  const handleAddSensorComparator = () => {
    if (selectedSensor && selectedComparator && value) {
      const newComparator = {
        sensor: { id: selectedSensor },
        comparator: selectedComparator,
        value: parseFloat(value),
      };
      setSensorComparators([...sensorComparators, newComparator]);
      setSelectedSensor('');
      setSelectedComparator('=');
      setValue('');
    }
  };

  const handleActuatorChange = (id) => {
    setSelectedActuators((prev) =>
      prev.includes(id) ? prev.filter((actuatorId) => actuatorId !== id) : [...prev, id]
    );
  };

  const handleAddScenario = async () => {
    const newScenario = {
      name: scenarioName,
      actuators: selectedActuators.map((id) => ({ id })),
      sensorComparators,
      conditionType, // Include conditionType
    };

    try {
      const response = await fetch(`/scenario/add`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(newScenario),
      });

      if (response.ok) {
        const savedScenario = await response.json();
        onScenarioAdded(savedScenario);
        onClose();
      } else {
        console.error("Error adding scenario");
      }
    } catch (error) {
      console.error("Error adding scenario:", error);
    }
  };

  return (
    <div className="modal show d-block" tabIndex="-1">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Add Scenario</h5>
            <button type="button" className="btn-close" onClick={onClose}></button>
          </div>
          <div className="modal-body">
            <h6>Scenario Name</h6>
            <input
              type="text"
              className="form-control mb-3"
              value={scenarioName}
              onChange={(e) => setScenarioName(e.target.value)}
              placeholder="Scenario Name"
            />
            <h6>Actuators</h6>
            <div className="mb-3">
              {actuators.map((actuator) => (
                <div key={actuator.id} className="form-check">
                  <input
                    className="form-check-input"
                    type="checkbox"
                    value={actuator.id}
                    id={`actuator-${actuator.id}`}
                    onChange={() => handleActuatorChange(actuator.id)}
                  />
                  <label className="form-check-label" htmlFor={`actuator-${actuator.id}`}>
                    {actuator.name}
                  </label>
                </div>
              ))}
            </div>
            <h6>Condition Type</h6>
            <select
              className="form-select mb-3"
              value={conditionType}
              onChange={(e) => setConditionType(e.target.value)}
            >
              <option value="ALL">ALL</option>
              <option value="ANY">ANY</option>
              <option value="NONE">NONE</option>
            </select>
            <h6>Sensor Comparators</h6>
            <div className="mb-3">
              <select
                className="form-select mb-2"
                value={selectedSensor}
                onChange={(e) => setSelectedSensor(e.target.value)}
              >
                <option value="">Select Sensor</option>
                {sensors.map((sensor) => (
                  <option key={sensor.id} value={sensor.id}>
                    {sensor.type}
                  </option>
                ))}
              </select>
              <select
                className="form-select mb-2"
                value={selectedComparator}
                onChange={(e) => setSelectedComparator(e.target.value)}
              >
                <option value="=">=</option>
                <option value="!=">!=</option>
                <option value=">">&gt;</option>
                <option value="<">&lt;</option>
                <option value=">=">&gt;=</option>
                <option value="<=">&lt;=</option>
              </select>
              <input
                type="number"
                className="form-control mb-2"
                value={value}
                onChange={(e) => setValue(e.target.value)}
                placeholder="Value"
              />
              <button
                type="button"
                className="btn btn-secondary"
                onClick={handleAddSensorComparator}
              >
                Add Sensor Comparator
              </button>
            </div>
            <ul>
              {sensorComparators.map((comparator, index) => (
                <li key={index}>
                  Sensor ID: {comparator.sensor.id}, Comparator: {comparator.comparator}, Value: {comparator.value}
                </li>
              ))}
            </ul>
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-secondary" onClick={onClose}>
              Close
            </button>
            <button type="button" className="btn btn-primary" onClick={handleAddScenario}>
              Add Scenario
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddScenarioModal;