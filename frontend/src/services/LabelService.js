import BaseService from "./BaseService";

class LabelService extends BaseService {

    constructor(){
        super("/labels");
    }

    async findById(id) {
        return this.api.get(`${this.endPoint}/${id}`);
    }

    async update(id, data) {
        return this.api.put(`${this.endPoint}/${id}`, data);
    }
}

export default LabelService;