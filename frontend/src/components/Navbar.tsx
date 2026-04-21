import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Navbar() {
  const navigate = useNavigate();
  const { user, isAuthenticated, logout } = useAuth();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <header className="navbar">
      <div className="navbar-brand">
        <Link to="/">School Management App</Link>
      </div>

      <div className="navbar-actions">
        {isAuthenticated && user ? (
          <>
            <span className="navbar-user">
              {user.firstName} {user.lastName} ({user.role})
            </span>
            <button className="logout-button" onClick={handleLogout}>
              Logout
            </button>
          </>
        ) : (
          <Link to="/login" className="button-link">
            Login
          </Link>
        )}
      </div>
    </header>
  );
}

export default Navbar;