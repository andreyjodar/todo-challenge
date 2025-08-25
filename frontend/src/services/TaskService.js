import BaseService from "./BaseService";

class TaskService extends BaseService {

    constructor(){
        super("/tasks");
    }

    async findById(id) {
        return this.api.get(`${this.endPoint}/${id}`);
    } 

    async update(id, data) {
        return this.api.put(`${this.endPoint}/${id}`, data);
    }
}

export default TaskService;