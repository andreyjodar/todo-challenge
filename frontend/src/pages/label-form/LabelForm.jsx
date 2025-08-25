import React, { useState, useEffect } from "react";
import "./LabelForm.css"
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { useNavigate, useParams } from "react-router-dom";
import LabelService from "../../services/LabelService";

const LabelForm = () => {
    const labelService = new LabelService();
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const navigate = useNavigate();
    const { id } = useParams();
    const [isEditing, setIsEditing] = useState(false);
    const [labelId, setLabelId] = useState(null);

    useEffect(() => {
        const loadLabel = async () => {
            if (id) {
                try {
                    const response = await labelService.findById(id);
                    const label = response.data;
                    setName(label.name);
                    setDescription(label.description);
                    setLabelId(id);
                    setIsEditing(true);
                } catch (error) {
                    console.error("Error loading label:", error);
                }
            }
        };
        loadLabel();
    }, [id]);

    const handleBackClick = () => {
        navigate(-1);
    }

    const createLabel = async () => {
        const labelData = { name, description };
        try {
            await labelService.create(labelData);
            navigate("/home/labels"); 
        } catch (error) {
            console.error("Error creating label:", error);
        }
    }

    const updateLabel = async () => {
        const labelData = { name, description };
        try {
            await labelService.update(labelId, labelData);
            navigate("/home/labels");
        } catch (error) {
            console.error("Error updating label:", error);
        }
    }

    const handleSubmit = (event) => {
        event.preventDefault(); 
        if (isEditing) {
            updateLabel();
        } else {
            createLabel();
        }
    }

    return(
        <main className="labelform-container">
            <form onSubmit={handleSubmit} className="label-form">
                <div className="p-field">
                    <label htmlFor="name">Name</label>
                    <InputText id="name" value={name} onChange={(e) => setName(e.target.value)} />
                </div>
                <div className="p-field">
                    <label htmlFor="name">Description</label>
                    <InputText id="description" value={description} onChange={(e) => setDescription(e.target.value)} />
                </div>
                <br />
                
                <Button 
                    type="submit" 
                    label={isEditing ? "Update Label" : "Create Label"} 
                    icon="pi pi-tags" 
                />
                
                <Button 
                    className="second-button" 
                    label="Back" 
                    icon="pi pi-arrow-circle-left" 
                    onClick={handleBackClick} 
                />
            </form>
        </main>
    );
}

export default LabelForm;