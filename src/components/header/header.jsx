import React, { useState, useEffect } from "react";
import {
  AppBar,
  Tabs,
  Tab,
  Typography,
  Toolbar,
  Button,
  Menu,
  MenuItem,
  Avatar,
  IconButton
} from "@material-ui/core";
import { Link, useLocation, useHistory } from "react-router-dom";
import "./header.css";
import { useGoogleLogin } from "@react-oauth/google";
import GoogleServiceSingleton from "../../services/google-service-singleton";
import {useUserInfoSession} from "./user-context";

const Header = ({ tabs, onShowTab }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userInfo, setUserInfo] = useState(null);
  const [anchorEl, setAnchorEl] = useState(null);
  const [menuOpen, setMenuOpen] = useState(false);
  const { userInfoSession, updateUserInfoSession, removeUserInfoSession } = useUserInfoSession();

  const history = useHistory();

  useEffect(() => {
    if (userInfoSession) {
      setUserInfo(userInfoSession);
      setIsLoggedIn(true);
      setMenuOpen(false);
      setAnchorEl(null);
    }
  }, [userInfoSession]);

  const login = useGoogleLogin({
    onSuccess: (tokenResponse) => {
      GoogleServiceSingleton.getUserInfo(tokenResponse.access_token).then(
        (userInfo) => {
          setUserInfo(userInfo);
          setIsLoggedIn(true);
          updateUserInfoSession(userInfo);
        }
      );
    },
  });

  const handleLogout = () => {
    removeUserInfoSession();
    setIsLoggedIn(false);
    history.push("/");
  };

  const handleManage = () => {
    history.push("/manage");
  };

  const handleBook = () => {
    history.push("/");
  };

  const handleHelp = () => {
    history.push("/help");
  };

  const handleMenuOpen = (event) => {
    setMenuOpen(true);
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setMenuOpen(false);
    setAnchorEl(null);
  };

  const toolbarStyle = {
    backgroundColor: "#01377D",
  };

  const location = useLocation();
  const currentTab = tabs.findIndex((tab) => tab.path === location.pathname);

  const handleChange = (event, newValue) => {
    // We can navigate to the selected tab using your routing mechanism
    // For example, using react-router-dom:
    // history.push(tabs[newValue].path);
  };

  const LoginMenuHTMLComponent = () => {
    return (
        <Menu
            anchorEl={anchorEl}
            open={Boolean(anchorEl)}
            onClose={handleMenuClose}
            getContentAnchorEl={null}
            anchorOrigin={{
              vertical: "bottom",
              horizontal: "center",
            }}
            transformOrigin={{
              vertical: "top",
              horizontal: "center",
            }}
            PaperProps={{
              style: {
                backgroundColor: "white",
                color: "white",
              },
            }}
        >
          <MenuItem
              onClick={handleBook}
              style={{ color: "#001631" }}
          >
            Book Train
          </MenuItem>
          <MenuItem
              onClick={handleManage}
              style={{ color: "#001631" }}
          >
            Manage Bookings
          </MenuItem>
          <MenuItem
              onClick={handleHelp}
              style={{ color: "#001631" }}
          >
            Help
          </MenuItem>
          <MenuItem
              onClick={handleLogout}
              style={{ color: "#001631" }}
          >
            Sign Out
          </MenuItem>
        </Menu>
    )
  }

  return (
    <AppBar position="static">
      <Toolbar style={toolbarStyle}>
      <a href={process.env.PUBLIC_URL + "/BonVoyage.png"} target="_blank" rel="noopener noreferrer">
        <img
            src={process.env.PUBLIC_URL + "/BonVoyage.png"}
            alt="Logo"
            style={{ maxHeight: "50px", marginRight: "10px" }}
        />
        </a>

        <Typography variant="h6" style={{ marginRight: "50px" }}>
          {`Rail Express`}
        </Typography>
        <Tabs
          value={currentTab}
          onChange={handleChange}
          style={{ flex: 1, justifyContent: "center" }}
        >
          {tabs.map((tab, index) => {
            if (tab.show !== false) {
              return (
                <Tab
                  key={index}
                  label={tab.label}
                  component={Link}
                  to={tab.path}
                />
              );
            }
            return null;
          })}
        </Tabs>
        {isLoggedIn ? (
          <div>
            {/* <IconButton sx={{ p: 0 }}>
              <Avatar alt={`${userInfo?.given_name}`} src={`${userInfo?.picture}`} />
            </IconButton> */}
            <Button
              style={{ backgroundColor: "#001631", color: "white", border: "1px solid white" }}
              onClick={handleMenuOpen}
            >
              {`${userInfo?.given_name}`}
            </Button>
            {menuOpen && <LoginMenuHTMLComponent />}
          </div>
        ) : (
          <Button
            style={{ backgroundColor: "black", color: "white" }}
            onClick={() => login()}
          >
            Login/Sign Up
          </Button>
        )}
      </Toolbar>
    </AppBar>
  );
};

export default Header;
