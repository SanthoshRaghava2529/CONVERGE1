import { BrowserRouter, Route, Routes } from "react-router-dom";
import Navbar from "./components/Navbar";
import ProtectedRoute from "./components/ProtectedRoute";
import Home from "./pages/Home";
import Explore from "./pages/Explore";
import ExperienceDetails from "./pages/ExperienceDetails";
import Login from "./pages/Login";
import Register from "./pages/Register";
import GroupConverge from "./pages/GroupConverge";
import GroupRoom from "./pages/GroupRoom";
import Profile from "./pages/Profile";
import Bookings from "./pages/Bookings";
import BookingConfirmation from "./pages/BookingConfirmation";
import Saved from "./pages/Saved";

function App() {
  return (
    <BrowserRouter>
      <div className="min-h-screen bg-slate-50">
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/explore" element={<Explore />} />
          <Route path="/experience/:id" element={<ExperienceDetails />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route
            path="/groups"
            element={
              <ProtectedRoute>
                <GroupConverge />
              </ProtectedRoute>
            }
          />
          <Route
            path="/groups/:groupCode"
            element={
              <ProtectedRoute>
                <GroupRoom />
              </ProtectedRoute>
            }
          />
          <Route
            path="/profile"
            element={
              <ProtectedRoute>
                <Profile />
              </ProtectedRoute>
            }
          />
          <Route
            path="/bookings"
            element={
              <ProtectedRoute>
                <Bookings />
              </ProtectedRoute>
            }
          />
          <Route
            path="/bookings/:bookingReference"
            element={
              <ProtectedRoute>
                <BookingConfirmation />
              </ProtectedRoute>
            }
          />
          <Route
            path="/saved"
            element={
              <ProtectedRoute>
                <Saved />
              </ProtectedRoute>
            }
          />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
