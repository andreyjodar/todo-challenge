import React from "react";
import "./Home.css";
import { Card } from "primereact/card";
import { useNavigate } from "react-router-dom";

const Home = () => {
    const navigate = useNavigate();

    const handleTasks = () => {
        navigate("/home/tasks");
    }

    const handleLabels = () => {
        navigate("/home/labels");
    }

    const taskIcon = <i className="pi pi-list" style={{ marginRight: '.5em' }}></i>;
    const labelIcon = <i className="pi pi-tags" style={{ marginRight: '.5em' }}></i>;

    return(
       <main className="home-container">
            <section className="sections-container">
                <Card
                    title="Tasks"
                    subTitle="Manage Your Tasks and Activities"
                    header={taskIcon}
                    onClick={handleTasks}
                    className="p-card-menu"
                />
                <Card
                    title="Labels"
                    subTitle="Manage System Labels (Admin)"
                    header={labelIcon}
                    onClick={handleLabels}
                    className="p-card-menu"
                />
            </section>
       </main> 
    );
}

export default Home;