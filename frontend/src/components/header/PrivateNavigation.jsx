import React from 'react';
import { Button } from 'primereact/button';
import { useNavigate } from 'react-router-dom';
import './PrivateNavigation.css';

const PrivateNavigation = () => {
    const navigate = useNavigate();

    const handleHomeClick = () => {
        navigate('/home');
    };

    const handleProfileClick = () => {
        navigate('/profile');
    };

    return (
        <nav className='navigation-container'>
            <Button label='Home' onClick={() => handleHomeClick()} link />
        </nav>
    );
};

export default PrivateNavigation;