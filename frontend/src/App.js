import { Route, Routes } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css'

import Login from "./screens/Login";
import Register from "./screens/Register";
import CreateTourPackageForm from "./screens/CreateTourPakage";
import TourPkgList from "./component/TourPkgList";

function App() {
  return (
    <div className="container">
      <Routes>
        <Route path='' element={<Login/>} />
        <Route path='login' element={<Login />} />
        <Route path='register' element={<Register/>} />
        <Route path='tour-packages' element={<CreateTourPackageForm/>} />
        <Route path='tour-packages-all' element={<TourPkgList/>}/>
      </Routes>
      <ToastContainer />
    </div>
  );
}

export default App;
