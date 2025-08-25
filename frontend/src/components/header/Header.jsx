import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom'; 
import { Button } from 'primereact/button';
import PrivateNavigation from './PrivateNavigation';
import './Header.css';

const Header = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [isLoggedin, setIsLoggedin] = useState(false);

    const handleLoginClick = () => {
        navigate('/login');
    };

    const handleUserForm = () => {
        navigate('/user-form');
    }

    const handleLogoutClick = () => {
        localStorage.removeItem("user");
        localStorage.removeItem('token');
        setIsLoggedin(false);
        navigate('/');
    };

    const checkLoginStatus = () => {
        const user = localStorage.getItem("user");
        setIsLoggedin(!!user);
    };

    useEffect(() => {
        checkLoginStatus();
        window.addEventListener('storage', checkLoginStatus);

        return () => {
            window.removeEventListener('storage', checkLoginStatus);
        };
    }, [location.pathname]); 

    return (
        <header className="header-container">
            <div className="brand-container">
                <Button onClick={() => navigate('/')} link>
                    <h2>ToDo List</h2>
                </Button>
            </div>
            {isLoggedin && <PrivateNavigation />}
            <div className="auth-actions">
                {!isLoggedin ? (
                    <Button icon="pi pi-sign-in" onClick={handleLoginClick} />
                ) : (
                    <div className="auth-admin">
                        <Button icon="pi pi-sign-out" onClick={handleLogoutClick} />
                        <Button icon="pi pi-user-plus" onClick={handleUserForm} />
                    </div>
                )}
            </div>
        </header>
    );
};

export default Header;