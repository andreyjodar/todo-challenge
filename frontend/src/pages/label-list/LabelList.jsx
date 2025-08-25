import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import LabelService from "../../services/LabelService";
import { Card } from "primereact/card";
import { Button } from "primereact/button";
import "./LabelList.css"

const LabelList = () => {
    const [labels, setLabels] = useState([]);
    const navigate = useNavigate();
    const labelService = new LabelService();

    useEffect(() => {
        const loadLabels = async () => {
            try {
                const response = await labelService.findAll();
                setLabels(response.data.content);
            } catch (error) {
                console.error("Erro ao carregar labels:", error);
            }
        };
        loadLabels();
    }, []);

    const handleDelete = async (id) => {
        try {
            await labelService.delete(id);
            setLabels(labels.filter(label => label.id !== id));
        } catch (error) {
            console.error("Erro ao deletar label:", error);
        }
    }

    const handleEdit = (id) => {
        navigate(`/home/labels/label-form/${id}`);
    }

    const handleCreate = () => {
        navigate(`/home/labels/label-form`);
    }

        const footer = (label) => (
        <span className="action-container">
            <Button
                icon="pi pi-pencil"
                className="p-button-outlined p-button-secondary"
                onClick={() => handleEdit(label.id)}
            />
            <Button
                icon="pi pi-trash"
                className="p-button-outlined p-button-danger"
                onClick={() => handleDelete(label.id)}
                style={{ marginLeft: '.5em' }}
            />
        </span>
    );

    return(
        <main className="labellist-container">
            <div className="labellist-header">
                <h1>Label List</h1>
                <Button
                    label="Create Label"
                    icon="pi pi-plus-circle"
                    onClick={() => handleCreate()}
                />
            </div>
            <div className="cardlabel-container">
                {labels.length > 0 ? (
                    labels.map(label => (
                        <Card
                            key={label.id}
                            title={label.name}
                            subTitle={label.description}
                            footer={footer(label)} 
                            className="p-card-label"
                        />
                    ))
                ) : (
                    <div className="nolabels">
                        <p>No labels were found.</p>
                    </div>
                )}
            </div>
        </main>
    );
}

export default LabelList;