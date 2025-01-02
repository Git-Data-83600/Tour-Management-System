import React, { useState } from 'react';
import axios from 'axios';
import { config } from '../services/config';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

function CreateTourPackageForm() {
  const [tourPkgName, setTourPkgName] = useState('');
  const [tourPkgDescription, setTourPkgDescription] = useState('');
  const [tourPkgPrice, setTourPkgPrice] = useState('');
  const [tourPkgDuration, setTourPkgDuration] = useState('');
  const [tourPkgStartDate, setTourPkgStartDate] = useState('');
  const [tourPkgEndDate, setTourPkgEndDate] = useState('');
  const [file, setFile] = useState(null);
  const [responseMessage, setResponseMessage] = useState('');

  const navigate = useNavigate();

  
  const handleTourPkgNameChange = (e) => setTourPkgName(e.target.value);
  const handleTourPkgDescriptionChange = (e) => setTourPkgDescription(e.target.value);
  const handleTourPkgPriceChange = (e) => setTourPkgPrice(e.target.value);
  const handleDurationChange = (e) => setTourPkgDuration(e.target.value);  
  const handleStartDateChange = (e) => setTourPkgStartDate(e.target.value);  
  const handleEndDateChange = (e) => setTourPkgEndDate(e.target.value);
  const handleFileChange = (e) => setFile(e.target.files[0]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!tourPkgName || !tourPkgDescription || !tourPkgPrice || !tourPkgDuration || !tourPkgStartDate || !tourPkgEndDate || !file) {
      setResponseMessage('Please fill in all fields and select an image.');
      return;
    }

    
    const tourPkgData = {
      name: tourPkgName,
      description: tourPkgDescription,
      price: parseFloat(tourPkgPrice),
      duration: parseInt(tourPkgDuration),
      startDate: tourPkgStartDate,
      endDate: tourPkgEndDate
    };

    const formData = new FormData();
    formData.append('tourPkgData', JSON.stringify(tourPkgData)); // Convert object to JSON string
    formData.append('image', file);

    try {
      const token = sessionStorage['token'];
      const user = JSON.parse(sessionStorage['user']);
     
      const guidId = user.id;
      const response = await axios.post(`${config.serverUrl}/tour-packages/${guidId}`, formData, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'multipart/form-data',
        },
      });
      toast.success(response.data.message);
      navigate('/guide-tour-packages');
    } catch (error) {
      toast.error('Something Went Wrong..!');
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="text-center mb-4">Create Tour Package</h2>
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="card p-4 shadow-sm">
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="tourPkgName" className="form-label">Tour Package Name</label>
                <input
                  type="text"
                  id="tourPkgName"
                  className="form-control"
                  value={tourPkgName}
                  onChange={handleTourPkgNameChange}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="tourPkgDescription" className="form-label">Tour Package Description</label>
                <textarea
                  id="tourPkgDescription"
                  className="form-control"
                  value={tourPkgDescription}
                  onChange={handleTourPkgDescriptionChange}
                  rows="4"
                  placeholder="Enter a description for the tour package"
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="tourPkgPrice" className="form-label">Tour Package Price</label>
                <input
                  type="number"
                  id="tourPkgPrice"
                  className="form-control"
                  value={tourPkgPrice}
                  onChange={handleTourPkgPriceChange}
                  required
                  min="0"
                  step="0.01"
                  placeholder="Enter price of the tour package"
                />
              </div>

              <div className="mb-3">
                <label htmlFor="duration" className="form-label">Tour Duration (in days)</label>
                <input
                  type="number"
                  id="duration"
                  className="form-control"
                  value={tourPkgDuration}
                  onChange={handleDurationChange}
                  required
                  min="1"
                  placeholder="Enter duration of the tour"
                />
              </div>

              <div className="mb-3">
                <label htmlFor="startDate" className="form-label">Start Date</label>
                <input
                  type="date"
                  id="startDate"
                  className="form-control"
                  value={tourPkgStartDate}
                  onChange={handleStartDateChange}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="endDate" className="form-label">End Date</label>
                <input
                  type="date"
                  id="endDate"
                  className="form-control"
                  value={tourPkgEndDate}
                  onChange={handleEndDateChange}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="file" className="form-label">Upload Image</label>
                <input
                  type="file"
                  id="file"
                  className="form-control"
                  onChange={handleFileChange}
                  required
                />
              </div>

              <div className="d-grid">
                <button type="submit" className="btn btn-primary">Create Tour Package</button>
              </div>
            </form>

            {responseMessage && (
              <div className="mt-3">
                <div className={`alert ${responseMessage.includes('success') ? 'alert-success' : 'alert-danger'}`} role="alert">
                  {responseMessage}
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default CreateTourPackageForm;
