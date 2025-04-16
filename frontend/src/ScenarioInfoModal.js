import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const ScenarioInfoModal = ({ scenario, onClose }) => {
  return (
    <div className="modal show d-block" tabIndex="-1">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Scenario Info</h5>
            <button type="button" className="btn-close" onClick={onClose}></button>
          </div>
          <div className="modal-body">
            <h6>Condition Type</h6>
            <p>{scenario.conditionType}</p>
            <h6>Actuators</h6>
            <ul>
              {scenario.actuators.map(actuator => (
                <li key={actuator.id}>
                  <strong>{actuator.name}</strong>: {actuator.description}
                </li>
              ))}
            </ul>
            <h6>Sensor Comparators</h6>
            <ul>
              {scenario.sensorComparators.map(comparator => (
                <li key={comparator.id}>
                  <strong>{comparator.sensor.type}</strong>: {comparator.comparator} {comparator.value}
                </li>
              ))}
            </ul>
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-secondary" onClick={onClose}>Close</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ScenarioInfoModal;