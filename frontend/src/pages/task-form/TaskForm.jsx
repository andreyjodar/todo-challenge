import React, { useEffect, useState } from "react";
import LabelService from "../../services/LabelService";
import TaskService from "../../services/TaskService";
import { useNavigate, useParams } from "react-router-dom";
import { AutoComplete } from "primereact/autocomplete";
import { Calendar } from "primereact/calendar";
import { InputText } from "primereact/inputtext";
import "./TaskForm.css"
import { Button } from "primereact/button";

const TaskForm = () => {
    const labelService = new LabelService();
    const taskService = new TaskService();

    const [taskId, setTaskId] = new useState(null);
    const [deadline, setDeadline] = useState(null);
    const [title, setTitle] = new useState('');
    const [description, setDescription] = new useState('');
    const [filteredStatus, setFilteredStatus] = useState([]);
    const [status, setStatus] = new useState('');
    
    const [statusOptions] = new useState(["TODO", "DOING", "DONE"]);
    const [selectedLabels, setSelectedLabels] = useState([]);
    const [availableLabels, setAvailableLabels] = useState([]);
    const [filteredLabels, setFilteredLabels] = useState([]);

    const [isEditing, setIsEditing] = new useState(false);
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        const loadLabels = async () => {
            try {
                const response = await labelService.findAll();
                if(response.data && response.data.content) {
                    setAvailableLabels(response.data.content);
                }
            } catch (error) {
                console.error("Error loading labels:", error);
            }
        }
        loadLabels();
        const loadTask = async () => {
            try {
                if(id) {
                    setIsEditing(true);
                    const response = await taskService.findById(id);
                    const task = response.data;
                    setTaskId(task.id); 
                    setTitle(task.title);
                    setDescription(task.description);
                    setStatus(task.status);
                    setDeadline(parseDate(task.deadline));
                    setSelectedLabels(task.labels);
                }
            } catch (error) {
                console.error("Error loading task:", error);
            }
        }
        loadTask();
    }, [id]);

    function parseDate(dateString) {
        if (!dateString) {
            return null;
        }
        
        const date = new Date(`${dateString}T00:00:00Z`);
        return date;
    }

    const searchLabels = (event) => {
        const query = event.query.toUpperCase();
        let _filteredLabels = [];

        for (let label of availableLabels) {
            if (label.name.toUpperCase().indexOf(query) !== -1) {
                _filteredLabels.push(label);
            }
        }
        setFilteredLabels(_filteredLabels);
    };

    const searchStatus = (event) => {
        const query = event.query.toLowerCase();
        const filtered = statusOptions.filter(status => status.toLowerCase().startsWith(query));
        setFilteredStatus(filtered);
    };

    const createTask = async () => {
        try {
            const labelsId = selectedLabels.map(label => label.id);
            const task = { title, description, status, deadline, labelsId };
            await taskService.create(task);
            navigate("/home/tasks");
        } catch (error) {
            console.error("Error loading task:", error);
        }
    }

    const updateTask = async () => {
        try {
            const labelsId = selectedLabels.map(label => label.id);
            const formattedDeadline = deadline ? deadline.toISOString().split('T')[0] : null;

            const task = { 
                title, 
                description, 
                status, 
                deadline: formattedDeadline, 
                labelsId 
            };

            await taskService.update(taskId, task);
            navigate("/home/tasks");
        } catch (error) {
            console.error("Error updating task:", error);
        }
    }

    const handleBackClick = () => {
        navigate("/home/tasks");
    }

    const handleDelete = async () => {
        try {
            await taskService.delete(id);
            navigate("/home/tasks");
        } catch (error) {
            console.error("Error deleting task:", error);
        }
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        if(isEditing) {
            updateTask();
        } else {
            createTask();
        }
    }

    return(
        <main className="taskform-container">
            <form onSubmit={handleSubmit} className="task-form">
                <div className="p-field">
                    <label>Title</label>
                    <InputText 
                        id="title" 
                        value={title} 
                        onChange={(e) => setTitle(e.target.value)}
                    />
                </div>
                <div className="p-field">
                    <label>Description</label>
                    <InputText 
                        id="description" 
                        value={description} 
                        onChange={(e) => setDescription(e.target.value)}
                    />
                </div>                
                <div className="p-field">
                    <label>Status</label>
                    <AutoComplete 
                        id="status"
                        value={status}
                        suggestions={filteredStatus}
                        completeMethod={searchStatus}
                        onChange={(e) => setStatus(e.target.value)} 
                        dropdown
                    />
                </div>
                <div className="p-field">
                    <label>Deadline</label>
                    <Calendar 
                        id="deadline" 
                        value={deadline}
                        onChange={(e) => setDeadline(e.target.value)}
                        minDate={new Date()}
                        dateFormat="dd/mm/yy"
                        showIcon
                    />
                </div>
                <div className="p-field">
                    <label>Labels</label>
                    <AutoComplete 
                        value={selectedLabels}
                        suggestions={filteredLabels}
                        completeMethod={searchLabels}
                        field="name"
                        multiple
                        onChange={(e) => setSelectedLabels(e.value)}
                    />
                </div>
                <br />
                <Button 
                    type="submit" 
                    label={isEditing ? "Update Task" : "Create Task"} 
                    icon="pi pi-plus-circle" 
                />

                {isEditing && (
                    <Button
                        type="button"
                        label="Delete Task"
                        className="p-button-outlined p-button-danger"
                        icon="pi pi-trash"
                        onClick={handleDelete}
                    />
                )}
                
                <Button 
                    type="button"
                    className="second-button" 
                    label="Back" 
                    icon="pi pi-arrow-circle-left" 
                    onClick={handleBackClick} 
                />
            </form>
        </main>
    );
}

export default TaskForm;