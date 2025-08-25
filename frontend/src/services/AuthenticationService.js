import BaseService from "./BaseService";

class AuthenticationService extends BaseService {

    constructor(){
        super("/auth");
    }

    async login(data){
        const response = await this.api.post(`${this.endPoint}/login`, data);
        return response;
    }
}

export default AuthenticationService;