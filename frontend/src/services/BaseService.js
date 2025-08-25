import api from "../config/AxiosConfig";

class BaseService {

    constructor(endPoint) {
        this.endPoint = endPoint;
        this.api = api;
    }

    async create(data){
        const resposta = await this.api.post(this.endPoint, data);
        return resposta;
    }

    async update(data) {
        const resposta = await this.api.put(this.endPoint, data);
        return resposta;
    }

    async delete(id){
        const resposta = await this.api.delete(`${this.endPoint}/${id}`);
        return resposta;
    }

    async findAll(){
        try {
            const response = await this.api.get(this.endPoint);
            return response;
        } catch (error) {
            console.log(error);
        }
      
    }

}
export default BaseService;