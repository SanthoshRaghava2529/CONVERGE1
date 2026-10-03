import { useState } from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Navbar() {
  const navigate = useNavigate();
  const { isAuthenticated, logout, user } = useAuth();
  const [open, setOpen] = useState(false);

  const handleLogout = () => {
    logout();
    setOpen(false);
    navigate("/");
  };

  const linkClass = ({ isActive }) =>
    `text-sm font-medium ${
      isActive ? "text-indigo-600" : "text-slate-600 hover:text-indigo-600"
    }`;

  return (
    <header className="sticky top-0 z-50 border-b border-slate-200 bg-white/95 backdrop-blur">
      <div className="mx-auto flex h-16 max-w-7xl items-center justify-between px-4 sm:px-6">
        <Link to="/" className="flex flex-col" onClick={() => setOpen(false)}>
          <span className="text-xl font-black tracking-tight text-slate-900">
            CONVERGE
          </span>
          <span className="text-[10px] font-medium text-indigo-600">
            FIND WHAT EVERYONE AGREES ON.
          </span>
        </Link>

        <nav className="hidden items-center gap-7 md:flex">
          <NavLink className={linkClass} to="/">
            Home
          </NavLink>
          <NavLink className={linkClass} to="/explore">
            Explore
          </NavLink>
          <NavLink className={linkClass} to="/groups">
            Group Converge
          </NavLink>
          {isAuthenticated && (
            <>
              <NavLink className={linkClass} to="/bookings">
                Bookings
              </NavLink>
              <NavLink className={linkClass} to="/saved">
                Saved
              </NavLink>
            </>
          )}
        </nav>

        <div className="flex items-center gap-3">
          {isAuthenticated ? (
            <>
              <Link
                to="/profile"
                className="hidden rounded-lg px-3 py-2 text-sm font-semibold text-slate-700 hover:bg-slate-100 sm:block"
              >
                {user?.fullName || "Profile"}
              </Link>
              <button
                onClick={handleLogout}
                className="hidden rounded-lg bg-slate-900 px-4 py-2 text-sm font-semibold text-white hover:bg-slate-800 sm:block"
              >
                Logout
              </button>
            </>
          ) : (
            <>
              <Link
                to="/login"
                className="hidden px-3 py-2 text-sm font-semibold text-slate-700 sm:block"
              >
                Login
              </Link>
              <Link
                to="/register"
                className="rounded-lg bg-indigo-600 px-4 py-2 text-sm font-semibold text-white hover:bg-indigo-700"
              >
                Sign Up
              </Link>
            </>
          )}

          <button
            className="rounded-lg border border-slate-200 px-3 py-2 text-sm font-semibold md:hidden"
            onClick={() => setOpen((value) => !value)}
            type="button"
          >
            Menu
          </button>
        </div>
      </div>

      {open && (
        <div className="border-t border-slate-200 bg-white px-4 py-4 md:hidden">
          <div className="flex flex-col gap-3">
            <Link to="/" onClick={() => setOpen(false)}>
              Home
            </Link>
            <Link to="/explore" onClick={() => setOpen(false)}>
              Explore
            </Link>
            <Link to="/groups" onClick={() => setOpen(false)}>
              Group Converge
            </Link>
            {isAuthenticated ? (
              <>
                <Link to="/bookings" onClick={() => setOpen(false)}>
                  Bookings
                </Link>
                <Link to="/saved" onClick={() => setOpen(false)}>
                  Saved
                </Link>
                <Link to="/profile" onClick={() => setOpen(false)}>
                  Profile
                </Link>
                <button className="text-left font-semibold" onClick={handleLogout}>
                  Logout
                </button>
              </>
            ) : (
              <Link to="/login" onClick={() => setOpen(false)}>
                Login
              </Link>
            )}
          </div>
        </div>
      )}
    </header>
  );
}

export default Navbar;
