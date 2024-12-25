import { Route, Routes } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css'

import Login from "./screens/Login";
import Register from "./screens/Register";

function App() {
  return (
    <div className="container">
      <Routes>
        <Route path='' element={<Login/>} />
        <Route path='login' element={<Login />} />
        <Route path='register' element={<Register/>} />
      </Routes>
      <ToastContainer />
    </div>
  );
}

export default App;
