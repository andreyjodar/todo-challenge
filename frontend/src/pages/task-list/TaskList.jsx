import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import TaskService from "../../services/TaskService";
import { Card } from "primereact/card";
import { Button } from "primereact/button";
import "./TaskList.css"

const TaskList = () => {
    const [tasks, setTasks] = new useState([]);
    const navigate = new useNavigate();
    const taskService = new TaskService();

    useEffect(() => {
        const loadTasks = async () => {
            try {
                const response = await taskService.findAll();
                setTasks(response.data.content); 
            } catch (error) {
                console.error("Error loading tasks:", error);
            }
        };
        loadTasks();
    }, []);
    
    const handleCreate = () => {
        navigate(`/home/tasks/task-form`);
    }

    const handleTask = (id) => {
        navigate(`/home/tasks/task-form/${id}`);
    }

    const header = (task) => (
        <div className="status-info">
            <h3>{task.status}</h3>
        </div>
    );

    const formatDate = (dateString) => {
        if (!dateString) {
            return "N/A"; 
        }
        
        let date;
        if (dateString.includes('T')) {
            date = new Date(`${dateString}Z`);
        } else {
            date = new Date(dateString);
        }
        
        const options = {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit'
        };
        
        return new Intl.DateTimeFormat('pt-BR', options).format(date);
    }

    const footer = (task) => (
        <div className="taskcard-footer">
            <div className="taskcard-info">
                <div className="deadline-container">
                    <i class="pi pi-calendar-clock"></i>
                    <p>{formatDate(task.deadline)}</p>
                </div>
                <p>{task.description}</p>
                {labels(task.labels)}
            </div>
        </div>
    );

    const labels = (labels) => (
        <div className="labels-container">
            {labels.length > 0 ? (
                labels.map(label => (
                    <div className="task-label">
                        {label.name}
                    </div>
                ))
            ) : (
                <></>
            )}
        </div>
    );

    return(
        <main className="tasklist-container">
            <div className="tasklist-header">
                <h1>Task List</h1>
                <Button
                    label="Create Task"
                    icon="pi pi-plus-circle"
                    onClick={() => handleCreate()}
                />
            </div>
            <div className="cardtask-container">
                {tasks.length > 0 ? (
                    tasks.map(task => (
                        <Card 
                            key={task.id}
                            header={header(task)}
                            title={task.title}
                            subTitle={task.author.name}
                            footer={footer(task)}
                            className="p-card-task"
                            onClick={() => handleTask(task.id)}
                        />
                    ))
                ) : (
                    <div className="notasks">
                        <p>No tasks were found</p>
                    </div>
                )}
            </div>
        </main>
    );

}

export default TaskList;