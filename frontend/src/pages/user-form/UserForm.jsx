import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { InputText } from 'primereact/inputtext';
import { Password } from 'primereact/password';
import { AutoComplete } from 'primereact/autocomplete';
import { Button } from 'primereact/button';
import { Messages } from 'primereact/messages';
import UserService from '../../services/UserService';
import RoleService from '../../services/RoleService';
import './UserForm.css';

const UserForm = () => {
    const userService = new UserService();
    const roleService = new RoleService();

    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [selectedRoles, setSelectedRoles] = useState([]);
    
    const [availableRoles, setAvailableRoles] = useState([]);
    const [filteredRoles, setFilteredRoles] = useState([]);

    const msgs = React.useRef(null);

    useEffect(() => {
        const loadRoles = async () => {
            try {
                const response = await roleService.findAll();

                if (response.data && response.data.content) {
                    setAvailableRoles(response.data.content);
                } else {
                    console.error("Formato de resposta inesperado:", response.data);
                    if(msgs.current) {
                        msgs.current.show({
                            severity: 'warn',
                            summary: 'Aviso',
                            detail: 'O formato da resposta da API de roles está incorreto.',
                            life: 3000
                        });
                    }
                }
            } catch (error) {
                console.error("Erro ao carregar roles:", error);
                msgs.current.show({
                    severity: 'error',
                    summary: 'Erro',
                    detail: 'Não foi possível carregar as roles.',
                    life: 3000
                });
            }
        };
        loadRoles();
    }, [roleService]);

    const handleBackClick = () => {
        navigate("..");
    }

    const searchRoles = (event) => {
        const query = event.query.toUpperCase();
        let _filteredRoles = [];

        for (let role of availableRoles) {
            if (role.name.toUpperCase().indexOf(query) !== -1) {
                _filteredRoles.push(role);
            }
        }
        setFilteredRoles(_filteredRoles);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!name || !email || !password || selectedRoles.length === 0) {
            msgs.current.show({
                severity: 'error',
                summary: 'Validação',
                detail: 'Por favor, preencha todos os campos.',
                life: 3000
            });
            return;
        }

        const rolesId = selectedRoles.map(role => role.id);
        
        const userRequest = {
            name,
            email,
            password,
            rolesId
        };
        
        try {
            await userService.create(userRequest);
            msgs.current.show({
                severity: 'success',
                summary: 'Sucesso',
                detail: 'Usuário criado com sucesso!',
                life: 3000
            });

            setName('');
            setEmail('');
            setPassword('');
            setSelectedRoles([]);
            navigate("/home");
        } catch (error) {
            console.error("Erro ao criar usuário:", error);
            const errorMessage = error.response?.data?.message || "Erro ao criar usuário.";
            msgs.current.show({
                severity: 'error',
                summary: 'Erro',
                detail: errorMessage,
                life: 3000
            });
        }
    };

    return (
        <main className="userform-container">
            <Messages ref={msgs} />
            <form onSubmit={handleSubmit} className="user-form">
                
                <div className="p-field">
                    <label htmlFor="name">Name</label>
                    <InputText 
                        id="name" 
                        value={name} 
                        onChange={(e) => setName(e.target.value)} />
                </div>
                
                <div className="p-field">
                    <label htmlFor="email">Email</label>
                    <InputText 
                        id="email" 
                        value={email} 
                        onChange={(e) => setEmail(e.target.value)} />
                </div>
                
                <div className="p-field">
                    <label htmlFor="password">Password</label>
                    <Password 
                        id="password" 
                        value={password} 
                        onChange={(e) => setPassword(e.target.value)} 
                        feedback={false} />
                </div>
                
                <div className="p-field">
                    <label htmlFor="roles">Roles</label>
                    <AutoComplete
                        value={selectedRoles}
                        suggestions={filteredRoles}
                        completeMethod={searchRoles}
                        field="name"
                        multiple
                        onChange={(e) => setSelectedRoles(e.value)}
                    />
                </div>
                <br />
                <Button 
                    type="submit" 
                    label="Create User" 
                    icon="pi pi-check"
                />
                <Button 
                    className="second-button" 
                    label="Back" 
                    icon="pi pi-arrow-circle-left" onClick={handleBackClick}/>
            </form>
        </main>
    );
};

export default UserForm;