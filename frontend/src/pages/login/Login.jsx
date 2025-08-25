import React, { useState } from "react";
import { InputText } from 'primereact/inputtext';
import { Password } from 'primereact/password';
import { Button } from 'primereact/button';
import AuthenticationService from "../../services/AuthenticationService";
import { useNavigate } from "react-router-dom";
import './Login.css';

const Login = () => {
    const authenticationService = new AuthenticationService();
    const [user, setUser] = useState({ email: '', password: '' });
    const navigate = useNavigate();

    const handleChange = (e) => {
        setUser({ ...user, [e.target.name]: e.target.value });
    }

    const handleBackClick = () => {
        navigate(-1);
    }

    const login = async () => {
        try {
            const response = await authenticationService.login(user);
            console.log(response.data);

            if (response.status === 200 && response.data.token) {
            localStorage.setItem("user", JSON.stringify(response.data.userResponse));
            
            localStorage.setItem("token", response.data.token);

            navigate("/home");
            } else {
            alert("Erro ao fazer login");
            }
        } catch (error) {
            console.log(error);
            alert(error.response.data.mensagem);
        }
    };

    return (
        <main className="login-container">
            <div className="login-card">
                <div className="p-field">
                    <label>Email</label>
                    <InputText value={user.email} name="email" onChange={handleChange} />
                </div>
                <div className="p-field">
                    <label>Password</label>
                    <Password value={user.password} name="password" onChange={handleChange} />
                </div>
                <br />
                <Button label="Login" icon="pi pi-sign-in" onClick={login} />
                <Button className="second-button" label="Back" icon="pi pi-arrow-circle-left" onClick={handleBackClick}/>
            </div>
        </main>
    );
}

export default Login;