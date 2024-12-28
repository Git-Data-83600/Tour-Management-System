import React from 'react';
import { config } from '../services/config';
import '../App.css';

const TourPkgItem = ({ tour }) => {
  const { name, description, price, duration, startDate, endDate, image } = tour;
  const imageUrl = `${config.serverUrl}/tour-packages/image/${image}`;

  return (
    <div className="card mb-4 shadow-lg border-0 rounded-lg">
      <img
        src={imageUrl}
        alt={name}
        className="card-img-top"
        style={{
          height: '300px',
          objectFit: 'cover',
          borderTopLeftRadius: '0.25rem',
          borderTopRightRadius: '0.25rem',
        }}
        onError={(e) => e.target.src = '/path/to/default-image.jpg'}
      />
      <div className="card-body">
        <h5 className="card-title">{name}</h5>
        <p className="card-text">{description}</p>
        <div className="d-flex justify-content-between">
          <span><strong>Price:</strong> ${price}</span>
          <span><strong>Duration:</strong> {duration} days</span>
        </div>
        <div className="d-flex justify-content-between mt-2">
          <span><strong>Start Date:</strong> {new Date(startDate).toLocaleDateString()}</span>
          <span><strong>End Date:</strong> {new Date(endDate).toLocaleDateString()}</span>
        </div>

        <div className="mt-3 text-center">
          <button className="btn btn-dark w-100">
            Book Now
          </button>
        </div>
      </div>
    </div>
  );
};

export default TourPkgItem;
