import { Route, Routes } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css'

import Login from "./screens/Login";
import Register from "./screens/Register";
import CreateTourPackageForm from "./screens/CreateTourPakage";
import TourPkgList from "./component/TourPkgList";
import GuideTourPackage from "./screens/GuideTourPackage";
import UserList from "./component/UserList";
import BookingList from "./component/BookingList";

function App() {
  return (
    <div className="container">
      <Routes>
        <Route path='' element={<TourPkgList/>} />
        <Route path='login' element={<Login />} />
        <Route path='register' element={<Register/>} />
        <Route path='tour-packages' element={<CreateTourPackageForm/>} />
        <Route path='tour-packages-all' element={<TourPkgList/>}/>
        <Route path="guide-tour-packages" element={<GuideTourPackage/>}/>
        <Route path="users" element={<UserList/>}/>
        <Route path="bookings" element={<BookingList/>}/>
      </Routes>
      <ToastContainer />
    </div>
  );
}

export default App;
